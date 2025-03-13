package me.pgthinker.utils;

import cn.hutool.core.date.DateUtil;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.pgthinker.config.MinioProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Project: me.pgthinker.utils
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 21:14
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtil {

    private final MinioProperties minioProperties;
    private MinioClient minioClient;

    /**
     * 初始化minio客户端
     */
    @PostConstruct
    public void init() {
        // 如果开启了minio，则初始化minio客户端
        try {
            log.info("Minio Initialize........................");
            minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
            createBucket(minioProperties.getBucketName());
            log.info("Minio Initialize........................successful");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化minio配置异常: 【{}】", e.fillInStackTrace());
        }
    }

    /**
     * 判断bucket是否存在
     */
    @SneakyThrows(Exception.class)
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建bucket
     */
    @SneakyThrows(Exception.class)
    public void createBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows(Exception.class)
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 上传文件
     * 返回可以直接预览文件的URL
     */
    public String uploadFile(MultipartFile file) {
        try {
            //如果存储桶不存在则创建
            if (!bucketExists(minioProperties.getBucketName())) {
                createBucket(minioProperties.getBucketName());
            }
            //保证文件不重名
            String fileName = file.getOriginalFilename();
            String objectName = fileName + "-" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return getPreviewFileUrl(objectName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件异常: 【{}】", e.fillInStackTrace());
            return "文件上传异常:" + e.getMessage();
        }
    }

    /**
     * 删除文件
     */
    @SneakyThrows(Exception.class)
    public void deleteFile(String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).build());
    }


    /**
     * 获取minio文件的下载或者预览地址
     * 取决于调用本方法的方法中的PutObjectOptions对象有没有设置contentType
     *
     * @param fileName: 文件名
     */
    @SneakyThrows(Exception.class)
    public String getPreviewFileUrl(String fileName) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioProperties.getBucketName())
                        .object(fileName)
                        .expiry(24, TimeUnit.HOURS)
                        .build());
    }
}

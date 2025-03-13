-- 插入部门数据
INSERT INTO department (name)
VALUES ('技术部'),
       ('市场部'),
       ('人力资源部');

-- 插入用户数据 密码123123
INSERT INTO "user" (username, password, department_id)
VALUES ('admin', '$2a$10$IbExSQZmZKjwe9I.N8U39.gxgQfSIDMS.Eo9Fb0DocZqYn8FdT0VC', 1),
       ('tech_lead', '$2a$10$IbExSQZmZKjwe9I.N8U39.gxgQfSIDMS.Eo9Fb0DocZqYn8FdT0VC', 1),
       ('sales_manager', '$2a$10$IbExSQZmZKjwe9I.N8U39.gxgQfSIDMS.Eo9Fb0DocZqYn8FdT0VC', 2),
       ('hr_specialist', '$2a$10$IbExSQZmZKjwe9I.N8U39.gxgQfSIDMS.Eo9Fb0DocZqYn8FdT0VC', 3);

-- 插入角色数据
INSERT INTO role (name, description)
VALUES ('admin', '系统管理员，具有所有权限'),
       ('leader', '技术团队负责人'),
       ('sale', '销售'),
       ('hr', '管理公司人事'),
       ('staff', '员工');

-- 插入部门角色数据
INSERT INTO department_role (department_id, role_id)
VALUES (1, 1), -- 技术部管理员
       (1, 2), -- 技术部技术主管
       (2, 3), -- 市场部销售经理
       (3, 4); -- 人力资源 HR

-- 插入权限数据
INSERT INTO permission (name, description)
VALUES ('upload_file', '允许上传支持库'),
       ('agent_chat', '智能对话');

-- 插入角色权限数据
INSERT INTO role_permission (role_id, permission_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2), -- 技术主管可以查看报表和编辑文档
       (3, 1),
       (3, 2), -- 销售经理可以查看报表和审批流程
       (4, 2);
-- HR 仅管理用户

-- 插入用户角色数据
INSERT INTO user_role (user_id, role_id)
VALUES (1, 1), -- admin 作为管理员
       (2, 2), -- tech_lead 作为技术主管
       (3, 3), -- sales_manager 作为销售经理
       (4, 4); -- hr_specialist 作为 HR

-- 插入知识库数据
INSERT INTO knowledge_base (id, name, description)
VALUES (gen_random_uuid(), '技术文档', '技术部的内部文档'),
       (gen_random_uuid(), '市场分析', '市场部的分析报告'),
       (gen_random_uuid(), '人事规章', '人力资源部的相关制度');

-- -- 插入文档数据
-- INSERT INTO document_entity (file_name, path, uploader, base_id)
-- VALUES ('架构设计.pdf', '/docs/tech/', 2, (SELECT id FROM knowledge_base WHERE name = '技术文档')),
--        ('市场数据.xlsx', '/docs/market/', 3, (SELECT id FROM knowledge_base WHERE name = '市场分析')),
--        ('员工手册.docx', '/docs/hr/', 4, (SELECT id FROM knowledge_base WHERE name = '人事规章'));



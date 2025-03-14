CREATE EXTENSION IF NOT EXISTS vector;
-- 部门表
CREATE TABLE department
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);
-- 用户表
CREATE TABLE "user"
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(255)       NOT NULL,
    department_id BIGINT             NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE
);


-- 角色表
CREATE TABLE role
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- 部门角色表
CREATE TABLE department_role
(
    id            BIGSERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL,
    UNIQUE (department_id, role_id),
    FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

-- 权限表
CREATE TABLE permission
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- 角色权限表
CREATE TABLE role_permission
(
    id            BIGSERIAL PRIMARY KEY,
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    UNIQUE (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission (id) ON DELETE CASCADE
);

-- 用户角色表
CREATE TABLE user_role
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

-- 知识库
CREATE TABLE knowledge_base
(
    id          varchar(32)  PRIMARY KEY       NOT NULL,
    name        varchar(100) NOT NULL,
    description TEXT,
    creator BIGINT NOT NULL
);


-- 文档表
CREATE TABLE document_entity
(
    id        BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(512) NOT NULL,
    path      VARCHAR(512) NOT NULL,
    uploader  BIGINT       NOT NULL,
    base_id   varchar(32)         NOT NULL,
    FOREIGN KEY (base_id) REFERENCES knowledge_base (id) on DELETE CASCADE
);

drop table knowledge_base;
drop table document_entity;

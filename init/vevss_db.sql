# 创建t_user表
CREATE TABLE t_user
(
    id           INT UNSIGNED AUTO_INCREMENT COMMENT '用户id',
    username     VARCHAR(50) UNIQUE KEY NOT NULL COMMENT '用户名',
    password     CHAR(60)               NOT NULL COMMENT '密码',
    phone_number CHAR(11) UNIQUE KEY COMMENT '手机号',
    province     VARCHAR(3) COMMENT '省份',
    score        SMALLINT UNSIGNED COMMENT '分数',
    subject      TINYINT COMMENT '理=1/文=0',
    created      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT check_user_subject CHECK (subject = 0 OR subject = 1)
);

# 创建t_role表
CREATE TABLE t_role
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '角色id',
    name        VARCHAR(20) UNIQUE KEY NOT NULL COMMENT '角色名',
    description VARCHAR(255)           NOT NULL COMMENT '角色描述',
    created     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

#创建t_user_role表
CREATE TABLE t_user_role
(
    user_id INT UNSIGNED NOT NULL COMMENT '用户id',
    role_id INT UNSIGNED NOT NULL COMMENT '角色id',
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_userId FOREIGN KEY (user_id) REFERENCES t_user (id),
    CONSTRAINT fk_user_role_roleId FOREIGN KEY (role_id) REFERENCES t_role (id)
);

#创建t_permission表
CREATE TABLE t_permission
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '权限id',
    name        VARCHAR(20)  NOT NULL COMMENT '权限名',
    url         VARCHAR(255) NOT NULL COMMENT '权限url',
    description VARCHAR(255) NOT NULL COMMENT '权限描述',
    created     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

#创建t_role_permission表
CREATE TABLE t_role_permission
(
    role_id       INT UNSIGNED NOT NULL COMMENT '角色id',
    permission_id INT UNSIGNED NOT NULL COMMENT '权限id',
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permission_roleId FOREIGN KEY (role_id) REFERENCES t_role (id),
    CONSTRAINT fk_role_permission_permissionId FOREIGN KEY (permission_id) REFERENCES t_permission (id)
);

#专业门类表
Drop TABLE if exists t_category;
CREATE TABLE t_category
(
    id      INT UNSIGNED AUTO_INCREMENT COMMENT '门类代码',
    name    varchar(10) UNIQUE KEY NOT NULL COMMENT '门类名称',
    created datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);

#专业类别表
drop table if exists t_class;
CREATE TABLE t_class
(
    id      INT UNSIGNED AUTO_INCREMENT COMMENT '专业类代码',
    name    varchar(10) UNIQUE KEY NOT NULL COMMENT '名称',
    created datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);

#专业门类-专业类别关联表
drop table if exists t_category_class;
CREATE TABLE t_category_class
(
    category_id INT UNSIGNED NOT NULL COMMENT '门类代码',
    class_id    INT UNSIGNED NOT NULL COMMENT '专业类别',
    PRIMARY KEY (category_id, class_id),
    CONSTRAINT FOREIGN KEY fk_category_class_categoryId (category_id) REFERENCES t_category (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY fk_category_class_classId (class_id) REFERENCES t_class (id)
);

#专业表
drop table if exists t_major;
CREATE TABLE t_major
(
    id      int unsigned auto_increment comment 'id',
    code    varchar(8) UNIQUE KEY  NOT NULL COMMENT '专业代码',
    name    varchar(20) UNIQUE KEY NOT NULL COMMENT '专业名称',
    created datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);
#专业-专业类别关联表
drop table if exists t_class_major;
CREATE TABLE t_class_major
(
    class_id INT UNSIGNED NOT NULL COMMENT '专业类代码',
    major_id INT UNSIGNED NOT NULL COMMENT '专业代码',
    PRIMARY KEY (class_id, major_id),
    CONSTRAINT FOREIGN KEY fk_class_major_classId (class_id) REFERENCES t_class (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY fk_class_major_majorId (major_id) REFERENCES t_major (id)
);

#学校表
drop table if exists t_school;
CREATE TABLE t_school
(
    id         int unsigned auto_increment comment 'id',
    code       varchar(8) UNIQUE KEY  NOT NULL COMMENT '院校代码',
    name       varchar(30) UNIQUE KEY NOT NULL COMMENT '院校名称',
    province   varchar(3) COMMENT '所在省份',
    department varchar(11) COMMENT '所属部门',
    img_url    varchar(255) COMMENT '院校图片地址',
    mold       varchar(24) COMMENT '院校类型',
    evaluation float(2, 1) COMMENT '院校评价，0.0-5.0',
    created    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);

# 数线
CREATE TABLE t_score_line
(
    id            int unsigned auto_increment comment 'id',
    school_name   varchar(30)       NOT NULL COMMENT '学校名称',
    year          smallint unsigned NOT NULL COMMENT '年份',
    batch         tinyint           NOT NULL COMMENT '录取批次',
    subject       tinyint           NOT NULL COMMENT '文0/理1',
    total_score   smallint unsigned NOT NULL COMMENT '总分',
    chinese       tinyint unsigned COMMENT '语文',
    math          tinyint unsigned COMMENT '数学',
    comprehensive smallint unsigned COMMENT '理科综合/文科综合',
    created       datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated       datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY fk_score_line_schoolName (school_name) REFERENCES t_school (name),
    -- 检查文/理科是否为0或1
    CONSTRAINT check_subject CHECK (subject = 0 OR subject = 1),
    -- 检查总分是否在0-750之间
    CONSTRAINT chk_total_score CHECK (total_score BETWEEN 0 AND 750),
    -- 检查语文是否在0-150之间
    CONSTRAINT chk_chinese CHECK (chinese BETWEEN 0 AND 150),
    -- 检查数学是否在0-150之间
    CONSTRAINT chk_math CHECK (math BETWEEN 0 AND 150),
    -- 检查理科综合/文科综合是否在0-300之间
    CONSTRAINT chk_comprehensive CHECK (comprehensive BETWEEN 0 AND 300)
);

#插入system用户数据
INSERT INTO t_user (username, password, phone_number, province, score, subject)
VALUES ('system', '$2a$10$akh36WsghbiuqJY7UJFu.uRXqnW63zgKt8tSSYa5uK7SWV8VU5S02', '12345678901', '北京', 750, 1);

#插入role数据
INSERT INTO t_role (name, description)
VALUES ('ROLE_SYSTEM', '系统管理员'),
       ('ROLE_USER', '普通用户');

#插入user_role数据
INSERT INTO t_user_role (user_id, role_id)
SELECT t_user.id, t_role.id
FROM t_user,
     t_role
WHERE t_user.username = 'system'
  AND t_role.name = 'ROLE_SYSTEM';

#插入permission数据
INSERT INTO t_permission (name, url, description)
VALUES ('category:add', '/category', '添加专业门类'),
       ('category:delete', '/category/{name}', '通过名称删除专业门类'),
       ('category:update', '/category', '更新专业门类'),
#        专业类
       ('class:add', '/class', '添加专业类别'),
       ('class:delete', '/class/{name}', '通过名称删除专业类别'),
       ('class:update', '/class/{name}', '通过名称更新专业类别'),
#        专业
       ('major:add', '/major', '添加专业'),
       ('major:delete', '/major/name/{name}', '通过名称删除专业'),
       ('major:delete', '/major/code/{code}', '通过代码删除专业'),
       ('major:update', '/major', '更新专业'),
#        学校
       ('school:add', '/school', '添加学校'),
       ('school:delete', '/school/name/{name}', '通过名称删除学校'),
       ('school:delete', '/school/code/{code}', '通过代码删除学校'),
       ('school:update', '/school', '通过名称更新学校'),
#        分数线
       ('score_line:add', '/score_line', '添加分数线'),
       ('score_line:delete', '/score_line/{name}', '通过名称删除分数线'),
       ('score_line:update', '/score_line/{name}', '通过名称更新分数线'),
       ('score_line:query', '/score_line', '查询分数线'),
#        权限
       ('permission:all', '/permission/**', '对权限的所有操作'),
#        角色
       ('role:all', '/role/**', '对角色的所有操作'),
#        用户
       ('user:get', '/user/all', '获取所有用户'),
       ('user:get', '/user/{username}', '通过名称获取用户'),
       ('user:delete', '/user/{username}', '通过名称删除用户'),
       ('user:update', '/user', '更新用户'),
#        初始化数据
       ('data:initialize', '/data/initialize', '初始化数据');


# 插入role_permission数据
INSERT INTO t_role_permission (role_id, permission_id)
SELECT t_role.id, t_permission.id
FROM t_role,
     t_permission
WHERE t_role.name = 'ROLE_USER'
  AND t_permission.name = 'user:update';
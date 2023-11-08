create table user
(
    id                int unsigned auto_increment comment '用户编号'
        primary key,
    userName          varchar(255)                        not null comment '用户昵称',
    avatarUrl         varchar(1023)                       null comment '用户头像',
    userAccount       varchar(255)                        not null comment '用户账号',
    userPassword      varchar(255)                        not null comment '用户密码（加密）',
    userEmail         varchar(255)                        null comment '用户邮箱',
    isDelete          tinyint   default 0                 null comment '用户状态（默认为 0 正常，1 为已注销）',
    createTime        timestamp default CURRENT_TIMESTAMP null comment '用户创建时间',
    updateTime        timestamp default CURRENT_TIMESTAMP null comment '用户最近一次登录时间',
    loginNumber       int       default 0                 null comment '用户登录次数',
    stepsNumber       int       default 0                 null comment '用户步步条数',
    stepsGroupsNumber int       default 0                 null comment '用户步步集个数',
    userRole          tinyint   default 0                 null comment '用户角色（默认为 0 用户，1 为管理员，2 为会员）',
    constraint userAccount
        unique (userAccount)
)
    comment '用户信息';

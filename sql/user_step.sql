create database StepCapsule;
use StepCapsule;

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

# create table step
# (
#     stepId         int unsigned auto_increment comment '步步编号'
#         primary key,
#     id             int unsigned                                                                                                                            null comment '用户编号',
#     stepCreateTime timestamp default CURRENT_TIMESTAMP                                                                                                     null comment '步步创建时间',
#     realTime       varchar(255)                                                                                                                            null comment '工作实际完成时间',
#     weather        varchar(32)                                                                                                                             null comment '天气',
#     stepType       enum ('书籍', '电影', '电视剧', '动漫', '游戏', '艺术', '技能', '旅行', '学业', '工作', '美食', '体育', '时刻', '成长', '习惯', '其他') null comment '类型',
#     mainContent    varchar(255)                                                                                                                            null comment '内容主体',
#     description    varchar(255)                                                                                                                            null comment '描述',
#     thought        varchar(255)                                                                                                                            null comment '心得',
#     constraint step_ibfk_1
#         foreign key (id) references user (id)
# )
#     comment '步步信息';
#
# create index id
#     on step (id);

create table step
(
    stepId         int unsigned auto_increment comment '步步编号'
        primary key,
    id             int unsigned                                                                                                                            null comment '用户编号',
    stepCreateTime timestamp default CURRENT_TIMESTAMP                                                                                                     null comment '步步创建时间',
    realTime       varchar(255)                                                                                                                            null comment '工作实际完成时间',
    weather        varchar(32)                                                                                                                             null comment '天气',
    stepType       enum ('书籍', '电影', '电视剧', '动漫', '游戏', '艺术', '技能', '旅行', '学业', '工作', '美食', '体育', '时刻', '成长', '习惯', '其他') null comment '类型',
    mainContent    varchar(255)                                                                                                                            null comment '内容主体',
    description    varchar(255)                                                                                                                            null comment '描述',
    thought        varchar(255)                                                                                                                            null comment '心得',
    constraint step_ibfk_1
        foreign key (id) references user (id)
)
    comment '步步信息';

create index id
    on step (id);

CREATE DATABASE ctg_activity DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ctg_activity;

# 组件表

CREATE TABLE `ctg_component`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `comp_template_id`          bigint(20)      NOT NULL DEFAULT 0 COMMENT '组件模板ID',
    `comp_name`                 varchar(128)    NOT NULL COMMENT '组建名称',
    `comp_title`                varchar(128)    NOT NULL DEFAULT '' COMMENT '组件标题',
    `comp_type_id`              int(10)         NOT NULL DEFAULT 0 COMMENT '组件类型',
    `business_type_id`          int(10)         NOT NULL DEFAULT 0 COMMENT 'app_secret',
    `language_type`             int(4)          NOT NULL DEFAULT 0 COMMENT '组建语言 0.通用 1.中文 2.英文',
    `comp_animation`            varchar(1024)   DEFAULT NULL COMMENT '动画配置 Object',
    `comp_css`                  varchar(1024)   DEFAULT NULL COMMENT '样式配置 Object',
    `comp_wap_css`              varchar(1024)   DEFAULT NULL COMMENT '样式配置 Object',
    `comp_option`               varchar(4196)   DEFAULT NULL COMMENT '选项配置 Object',
    `comp_props`                varchar(1024)   DEFAULT NULL COMMENT '参数配置 Object',
    `comp_event`                varchar(1024)   DEFAULT NULL COMMENT '触发事件配置',
    `preview_chart`             varchar(255)    DEFAULT NULL COMMENT '预览图存放路径',
    `terminal_type`             int(4)          NOT NULL DEFAULT 0  COMMENT '适配终端 0.通用 1.pc 2.手机',
    `use_scene`                 varchar(255)    DEFAULT NULL COMMENT '使用场景',
    `comp_remark`               varchar(512)    DEFAULT NULL COMMENT '组件介绍',
    `comp_link`                 varchar(1024)   DEFAULT NULL COMMENT '跳转链接',
    `is_template`               tinyint(1)      DEFAULT 0 COMMENT ' 0-组件 1-组件模板',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 1 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `ix_comp_type_id` (`comp_type_id`),
    INDEX `ix_business_type_id` (`business_type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '组件表';

# 组建类型表
CREATE TABLE `ctg_component_type`
(
    `id`                        int(10)         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `comp_type_name`            varchar(128)    NOT NULL DEFAULT '' COMMENT '组件类型名称',
    `is_confirmation`           tinyint(1)      NOT NULL DEFAULT 0 COMMENT '是否需要二次确认 0.不需要 1.需要',
    `show_rule`                 tinyint(1)      NOT NULL DEFAULT 0 COMMENT '是否需要配置 规则参数（套餐/领卡） 0:不包含 1:包含',
    `use_scene`                 varchar(255)    DEFAULT NULL COMMENT '使用场景',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 0 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '组建类型表';

# 图层表
create table ctg_canvas
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT comment '图层ID',
    `canvas_name`               varchar (128)   not null default '' comment '名称',
    `canvas_template_id`        bigint(20)      not null default 0 comment '图层库模版ID',
    `business_type_id`          int(10)         not null default 0 comment '业务类型编号,详情见业务类型表',
    `canvas_html`               varchar(1024)   DEFAULT NULL comment '图层布局Html代码片 ',
    `canvas_layout`             int(4)          not null default 0 comment '布局类型 0:左右布局 1:上下布局 2:平铺 ....',
    `show_index`                int(10)         not null default 0 comment '显示顺序',
    `canvas_option`             varchar (512)   not null default '' comment '参数配置',
    `preview_chart`             varchar (255)   not null default '' comment '预览图存放路径',
    `use_scene`                 varchar(255)    DEFAULT NULL comment '使用场景',
    `canvas_css`                varchar(1024)   DEFAULT NULL comment '样式配置 ',
    `canvas_wap_css`            varchar(1024)   DEFAULT NULL comment '样式配置 ',
    `placeholder`               varchar(1024)   default null comment '占位符和组件对应的替换关系',
    `config_status`             tinyint(1)      NOT NULL DEFAULT 0 COMMENT '未配置，未配置0, 1：已配置',
    `terminal_type`             int(4)          not null default 0 comment '0:通用 1:pc 2:手机',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index `ix_canvas_template_id` (`canvas_template_id`),
    index `ix_business_type_id` (`business_type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment = '图层表';

# 图层模板表
create table ctg_canvas_template
(
    `id`                        bigint(20)      not null AUTO_INCREMENT comment '图层库模版ID',
    `canvas_name`               varchar (128)   not null default '' comment '名称',
    `business_type_id`          int(10)         not null default 0 comment '业务类型编号,详情见业务类型表',
    `canvas_template_html`      varchar(1024)   DEFAULT NULL comment '图层布局Html代码片',
    `canvas_template_layout`    INT(4)          not null default 0 comment '布局类型 0:左右布局 1:上下布局 2:平铺 ....',
    `canvas_template_css`       varchar(1024)   DEFAULT NULL comment '样式配置 ',
    `canvas_template_wap_css`   varchar(1024)   DEFAULT NULL comment '样式配置 ',
    `terminal_type`             INT(4)          not null default 0 comment ' 0:通用 1:pc 2:手机 ',
    `canvas_option`             varchar (512)   not null default '' comment '参数配置',
    `preview_chart`             varchar (255)   not null default '' comment '预览图存放路径',
    `placeholder`               varchar(1024)   default null comment '占位符和组件对应的替换关系',
    `use_scene`                 varchar(255)    DEFAULT NULL comment '使用场景',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 1 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index `ix_business_type_id` (`business_type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment = '图层模板表';

# 图层类型表
create table ctg_business_type
(
    `id`                        int(10)         not null AUTO_INCREMENT comment '图层类型ID',
    `business_type_name`        varchar(128)    not null default '' comment '模版类型名',
    `show_rule`                 tinyint(1)      not null default 0 comment '是否需要显示配置 规则参数页面（套餐/领卡） 0:不需要 1:需要',
    `rule_config_url`           varchar(512)    not null default '' comment ' 配置页面地址',
    `relate_comp_status`        tinyint(1)      not null default 0 comment '是否需要强关联同业务类型图层和组件 0:不需要 1:需要',
    `action_code`               varchar(128)    not null default '' comment '后端接口action编码',
    `use_scene`                 varchar(255)    DEFAULT NULL COMMENT '使用场景',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 1 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index `ix_action_code` (`action_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment = '图层类型表';

# 图层-组件关系配置表
create table ctg_canvas_comp_config
(
    `id`                        bigint(20)      not null AUTO_INCREMENT comment '配置ID',
    `canvas_id`                 bigint(20)      not null comment '图层ID',
    `comp_id`                   bigint(20)      not null comment '组件ID',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index `ix_canvas_id` (`canvas_id`),
    index `ix_comp_id` (`comp_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment = '图层-组件关系配置表';

# 模板表
CREATE TABLE `ctg_activity_template`
(
    `id`                        int(10)         NOT NULL AUTO_INCREMENT COMMENT '活动模板ID',
    `name`                      varchar(128)    NOT NULL DEFAULT '' COMMENT '活动模版名称',
    `copy_template_name`        varchar(128)    NOT NULL DEFAULT '' COMMENT 'copy的活动模版名称',
    `activity_id`               int(10)         NOT NULL DEFAULT 0 COMMENT '活动ID',
    `reference_code`            varchar(255)    NOT NULL DEFAULT '' COMMENT '同一个活动不同的中英文版本编号相同 (前端生产的唯一字符串)',
    `preview_chart`             varchar(512)    DEFAULT NULL COMMENT '预览图存放路径',
    `use_scene`                 varchar(255)    DEFAULT NULL COMMENT '使用场景',
    `activity_type`             int(4)          NOT NULL DEFAULT 0 COMMENT '0:正式  1:预热',
    `theme_method`              int(4)          NOT NULL DEFAULT 0 COMMENT '0:模版  1:活动',
    `language_type`             int(4)          NOT NULL DEFAULT 0 COMMENT '0:通用 1:中文 2:英文',
    `terminal_type`             int(4)          NOT NULL DEFAULT 0 COMMENT '0:通用 1:pc 2:手机',
    `status`                    int(4)          NOT NULL DEFAULT 0 COMMENT '0:编辑中 1:上架',
    `route_url`                 varchar(255)    DEFAULT NULL COMMENT '路由地址',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 1 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`name`),
    index (`activity_id`),
    index (`reference_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '活动模板';

# 单页模板表
CREATE TABLE `ctg_activity_page_template`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '单页模版唯一Id标示',
    `active_template_id`        int(10)         NOT NULL COMMENT '活动/活动模板ID',
    `point`                     varchar(255)    NOT NULL DEFAULT '' COMMENT '埋点',
    `page_name`                 varchar(255)    NOT NULL DEFAULT '' COMMENT '同一个活动不同的中英文版本编号相同 (前端生产的唯一字符串)',
    `page_title`                varchar(255)    NOT NULL DEFAULT '' COMMENT '模版名称',
    `template_type`             int(4)          NOT NULL COMMENT '模版类型',
    `page_html`                 varchar(1024)   DEFAULT NULL COMMENT '主代码Html代码片',
    `page_css`                  varchar(1024)   DEFAULT NULL COMMENT '样式配置',
    `page_wap_css`              varchar(1024)   DEFAULT NULL COMMENT '样式配置',
    `page_option`               varchar(1024)   DEFAULT NULL COMMENT '选项配置',
    `page_props`                varchar(1024)   DEFAULT NULL COMMENT '样式配置',
    `parent_page_id`            bigint(20)      NOT NULL COMMENT '父级页面Id  0为跟页面',
    `step_index`                int(10)         NOT NULL DEFAULT 0 COMMENT '配置顺序',
    `preview_chart`             varchar(255)    DEFAULT NULL COMMENT '预览图存放路径',
    `template_remark`           varchar(255)    DEFAULT NULL COMMENT '介绍',
    `route_url`                 varchar(255)    DEFAULT NULL COMMENT '路由地址',
    `enabled`                   tinyint(1)      NOT NULL DEFAULT 1 COMMENT '0.未启用 1.启用中',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`page_name`),
    index (`active_template_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '活动单页模板';

# 单页模板-画布关系表
CREATE TABLE `ctg_page_template_canvas_config`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '唯一Id',
    `active_template_id`        int(11)         DEFAULT NULL COMMENT '活动/活动模板ID',
    `page_id`                   bigint(20)      NOT NULL COMMENT '单页模版唯一Id标示',
    `canvas_id`                 bigint(20)      NOT NULL COMMENT '画布Id',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    index (`active_template_id`),
    index (`page_id`),
    index (`canvas_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '单页模板-画布关系';

CREATE TABLE `ctg_activity_entry`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`                      varchar(255)    DEFAULT NULL COMMENT '活动名称',
    `cipher`                    varchar(255)    DEFAULT NULL COMMENT '活动密钥',
    `start_time`                timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动开始时间',
    `end_time`                  timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动结束时间',
    `code`                      varchar(64)     NOT NULL COMMENT '活动code',
    `duty_dept`                 varchar(1024)   DEFAULT NULL COMMENT '负责部门',
    `assist_dept`               varchar(1024)   NOT NULL DEFAULT '' COMMENT '配合部门',
    `rcv_start_time`            timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领卡开始时间',
    `rcv_end_time`              timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领卡结束时间',
    `activity_label`            varchar(64)     NOT NULL DEFAULT '' COMMENT '活动标签',
    `activity_index_url`        varchar(255)    NOT NULL DEFAULT '' COMMENT '活动url',
    `description`               varchar(1024)   DEFAULT '' COMMENT '活动描述',
    `activity_status`           int(10)         NOT NULL DEFAULT -1 COMMENT '-1.编辑中 0.待审核/审核中 1.审核通过 2.已驳回 3.活动已上线 4.活动部分下线 5.活动全部下线',
    `pre_publish_url`           varchar(255)    DEFAULT '' COMMENT '预发布url',
    `pre_publish_type`          int(10)         NOT NULL DEFAULT 0 COMMENT '发布形式 0.自动发布 1.预发布 ',
    `pre_test_user`             varchar(64)     DEFAULT '' COMMENT '预发布测试IP 1.欣网 2.美洲 3.全部',
    `activity_template_id`      bigint(20)      NOT NULL DEFAULT 1 COMMENT '活动模板id',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `ix_activity_code` (`code`)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT = '活动基本信息';

CREATE TABLE `ctg_activity_platform`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `activity_id`               bigint(20)      NOT NULL COMMENT '活动ID',
    `file_url`                  varchar(255)    NOT NULL COMMENT '文件路径',
    `count`                     int(10)         NOT NULL default 0 COMMENT '上传的渠道数量',
    `ext_platform`              tinyint(1)      NOT NULL DEFAULT 0 COMMENT '是否保留渠道 0.否 1.是',
    `ext_end_time`              timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT  '保留渠道截止时间',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT = '活动推广渠道';

CREATE TABLE `ctg_activity_platform_detail`
(
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `activity_id`               bigint(20)      NOT NULL COMMENT '活动ID',
    `platform_id`               bigint(20)      NOT NULL COMMENT 'platform id',
    `platform_name`             varchar(128)    NOT NULL DEFAULT '' COMMENT '推广渠道名称',
    `start_time`                timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '渠道开始时间',
    `end_time`                  timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '渠道结束时间',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT = '活动推广渠道明细';

CREATE TABLE `ctg_canvas_template_comp_config` (
    `id`                        bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `canvas_template_id`        bigint(20)      NOT NULL COMMENT '图层模板ID',
    `comp_id`                   bigint(20)      NOT NULL COMMENT '组件ID',
    `creator`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '创建者ID',
    `updater`                   bigint(20)      NOT NULL DEFAULT 0 COMMENT '修改者ID',
    `is_deleted`                tinyint(1)      NOT NULL DEFAULT 0 COMMENT '软删除，默认0, 1：删除',
    `create_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `ix_canvas_template_id` (`canvas_template_id`),
    KEY `ix_comp_id` (`comp_id`)
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8 COMMENT='图层模板-组件关系配置表';
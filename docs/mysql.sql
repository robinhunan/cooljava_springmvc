

DROP TABLE IF EXISTS `code_test`;

CREATE TABLE `code_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `value` varchar(100) DEFAULT NULL COMMENT '数据值',
  `label` varchar(100) DEFAULT NULL COMMENT '标签名',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` int(10) DEFAULT NULL COMMENT '排序（升序）',
  `status` char(1) DEFAULT '0' COMMENT '可用状态：0可用 1不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成测试';

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(100) DEFAULT NULL COMMENT '数据值',
  `label` varchar(100) DEFAULT NULL COMMENT '标签名',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` int(10) DEFAULT NULL COMMENT '排序（升序）',
  `status` char(1) DEFAULT '0' COMMENT '可用状态：0可用 1不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='字典表';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`value`,`label`,`type`,`description`,`sort`,`status`) values (null,'java','1','lang','1',0,'0');
insert  into `sys_dict`(`id`,`value`,`label`,`type`,`description`,`sort`,`status`) values (null,'python','python编程语言','lang','系统语言',0,'0');
insert  into `sys_dict`(`id`,`value`,`label`,`type`,`description`,`sort`,`status`) values (null,'php','php','lang','系统语言',5,'0');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `href` varchar(255) DEFAULT NULL COMMENT 'url',
  `spread` char(1) DEFAULT '0' COMMENT '是否展开，1展开 0不展开',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `is_top` varchar(1) DEFAULT '0' COMMENT '是否是顶级菜单 1：是 0 不是',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `levels` int(10) DEFAULT NULL COMMENT '层级',
  `type` varchar(12) DEFAULT NULL COMMENT '菜单类型：0：目录菜单 1：权限菜单',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `sys_menu` */
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,0,'Cool后台管理系统','','','1',1,'0','0',0,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,1,'系统管理','fa-desktop','','0',1,'1','0',1,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,1,'内容管理','fa-file-word-o','','0',2,'1','0',1,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,2,'我的面板','fa-television','','0',2,'0','0',2,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,4,'个人信息','fa-vcard-o','/user/personInfo.do','0',1,'0','0',3,'0','user:personInfo');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,4,'修改密码','fa-gear','/user/pwd.do','0',2,'0','0',3,'0','user:pwd');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,2,'系统设置','fa-cogs',NULL,'0',3,'0','0',2,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'用户管理','fa-user-o','/user/list.do','0',1,'0','0',3,'0','');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'角色管理','fa-user-circle','/role/list.do','0',2,'0','0',3,'0','role:list');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'菜单管理','fa-window-restore','/menu/list.do','0',3,'0','0',3,'0','menu:list');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,3,'网站管理','fa-window-maximize',NULL,'0',1,'0','0',2,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,11,'友链管理','fa-link','/friendLink/list.do','0',7,'0','0',3,'0','');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,2,'系统监控','fa-eye','','0',3,'0','0',2,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,13,'连接池监视','fa-chain-broken','/druid/index.html','0',2,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'文件管理','fa-file-word-o','/res/ckfinder/_samples/standalone_v1.html','0',5,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,11,'会员管理','fa-users','/user/list.do','0',2,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'字典管理','fa-file-o','/dict/list.do','0',6,'0','0',3,'0','dict:list');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,2,'系统工具','fa-steam-square','','0',4,'0','0',2,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,18,'代码生成','fa-cc','/codeGenerator/form.do','0',0,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,18,'生成测试','fa-hand-peace-o','/test/list.do','0',2,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,18,'定时任务','fa-hourglass-2','/quartz/list.do','0',3,'0','0',3,'0',NULL);
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,12,'新增','','','0',0,'0','0',4,'1','flink:add');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,12,'删除','','','0',5,'0','0',4,'1','flink:delete');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,12,'修改','','','0',0,'0','0',4,'1','flink:update');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,7,'机构管理','fa-university','/org/list.do','0',4,'0','0',3,'0','');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,8,'增加用户','',NULL,'0',1,'0','0',4,'0','user:add');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,8,'查看用户',NULL,NULL,'0',2,'0','0',4,'0','user:list');
insert  into `sys_menu`(`id`,`pid`,`title`,`icon`,`href`,`spread`,`sort`,`is_top`,`del_flag`,`levels`,`type`,`permission`) values (null,18,'报表管理','fa-bar-chart-o','/chart/list.do','0',4,'0','0',3,'0','');

/*Table structure for table `sys_org` */

DROP TABLE IF EXISTS `sys_org`;

CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NOT NULL COMMENT '父级id',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `type` char(1) NOT NULL COMMENT '机构类型,0:公司 1：部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Data for the table `sys_org` */

insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,-1,'北京诚科技有限公司','1','0');
insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,1,'开发部','1','1');
insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,1,'法务部','2','1');
insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,1,'行政部','3','1');
insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,2,'前端开发组','1','1');
insert  into `sys_org`(`id`,`pid`,`name`,`sort`,`type`) values (null,2,'后台开发组','1','1');

/*Table structure for table `sys_quartz` */

DROP TABLE IF EXISTS `sys_quartz`;

CREATE TABLE `sys_quartz` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `JOB_NAME` varchar(50) DEFAULT NULL COMMENT '定时器名称',
  `JOB_GROUP` varchar(50) DEFAULT NULL COMMENT '所属组',
  `CLASS_PATH` varchar(50) DEFAULT NULL COMMENT '类路径',
  `CRON_STR` varchar(50) DEFAULT NULL COMMENT 'cron表达式',
  `STATE` varchar(1) DEFAULT NULL COMMENT '状态',
  `MARK` varchar(100) DEFAULT '0' COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='调度任务表';

/*Data for the table `sys_quartz` */

insert  into `sys_quartz`(`ID`,`JOB_NAME`,`JOB_GROUP`,`CLASS_PATH`,`CRON_STR`,`STATE`,`MARK`) values (null,'测试定时器','第一组','com.quartz.TestQuartz','0/5 * * * * ?','1','每隔5分运行一次');
insert  into `sys_quartz`(`ID`,`JOB_NAME`,`JOB_GROUP`,`CLASS_PATH`,`CRON_STR`,`STATE`,`MARK`) values (null,'测试定时器2','第一组','com.quartz.Test2Quartz','0/2 * * * * ?','1','每2秒打印一次');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `useable` varchar(64) DEFAULT '0' COMMENT '是否可用 0：可用  1：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */
insert  into `sys_role`(`id`,`name`,`useable`) values (null,'超级管理员','0');
insert  into `sys_role`(`id`,`name`,`useable`) values (null,'内容管理员','0');


/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(255) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(255) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','2');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','3');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','4');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','5');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','6');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','7');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','8');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','9');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','10');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','11');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','12');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','13');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','14');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','15');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','16');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','17');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','18');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','19');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','20');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','21');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','22');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','23');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','24');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','25');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','26');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','27');
insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','28');


/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `status` char(1) DEFAULT '0' COMMENT '可用状态：0可用 1不可用',
  `name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`login_name`,`pwd`,`img`,`email`,`sex`,`mobile`,`status`,`name`,`role_id`,`org_id`) values (null,'admin','21232f297a57a5a743894a0e4a801fc3','/upload/zhuyubing.jpg','bd@mooche.com',NULL,'15011111111','0','A先生',1,2);
insert  into `sys_user`(`id`,`login_name`,`pwd`,`img`,`email`,`sex`,`mobile`,`status`,`name`,`role_id`,`org_id`) values (null,'test','e10adc3949ba59abbe56e057f20f883e','','bd@mooche.com',NULL,'15751123456','0','test',2,2);

/*Table structure for table `web_flink` */

DROP TABLE IF EXISTS `web_flink`;

CREATE TABLE `web_flink` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `webname` varchar(255) DEFAULT NULL COMMENT '网站名称',
  `alink` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `email` varchar(255) DEFAULT NULL COMMENT '站长邮箱',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `dispos` varchar(255) DEFAULT NULL COMMENT '展示位置',
  `content` varchar(4000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='友情链接';

/*Data for the table `web_flink` */

insert  into `web_flink`(`id`,`webname`,`alink`,`email`,`addtime`,`dispos`,`content`) values (null,'新浪','http://www.sina.com.cn','bd@mooche.com','2019-08-27 15:15:40','首页','很好');


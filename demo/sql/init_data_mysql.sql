INSERT INTO SYS_RESOURCE
(SORT_ID,CODE,P_CODE,TEXT,URL,TYPE,EXPANDED,LEAF,CREATE_DATE,CREATE_BY,MODIFY_DATE,MODIFY_BY)
VALUES
(0,'systemMana','','系统管理',NULL,0,1,0,now(),'system',now(),'system'),
(1,'resConfig','systemMana','权限资源配置',NULL,1,0,1,now(),'system',now(),'system'),
(2,'userRoleConfig','systemMana','用户角色配置',NULL,1,0,1,now(),'system',now(),'system'),
(3,'roleResConfig','systemMana','角色资源配置',NULL,1,0,1,now(),'system',now(),'system'),
(4,'processMana','','流程管理',NULL,0,1,0,now(),'system',now(),'system'),
(5,'sys_todoWork','processMana','待办任务',NULL,1,0,1,now(),'system',now(),'system'),
(6,'sys_doneWork','processMana','已办任务',NULL,1,0,1,now(),'system',now(),'system'),
(7,'procList','processMana','流程列表',NULL,1,0,1,now(),'system',now(),'system'),
(8,'test','','测试',NULL,0,0,0,now(),'system',now(),'system'),
(9,'workflowTest','test','流程测试',NULL,0,0,0,now(),'system',now(),'system'),
(10,'leaveApply','workflowTest','请假申请',NULL,1,0,1,now(),'system',now(),'system'),
(11,'extTest','test','EXT测试',NULL,0,0,0,now(),'system',now(),'system'),
(12,'registForm','extTest','注册表单',NULL,1,0,1,now(),'system',now(),'system'),
(13,'userListGrid','extTest','用户列表',NULL,1,0,1,now(),'system',now(),'system')

INSERT INTO sys_user_info(person_code,last_name,PASSWORD,email,locked) VALUES('admin','admin','ICy5YqxZB1uWSwcVLSNLcA==','heshaowei_code@163.com','0')

INSERT INTO sys_role(role_code,role_name,remark) VALUES('admin','超级管理员','超级管理员')

INSERT INTO sys_user_role_relation(person_code,role_code) VALUES('admin','admin')

INSERT INTO SYS_ROLE_RES_RELATION
(
	ROLE_CODE,
	RES_CODE,
	CREATE_DATE,
	CREATE_BY,
	MODIFY_DATE,
	MODIFY_BY
)
SELECT 'admin',sr.CODE,now(),'system',now(),'system' FROM SYS_RESOURCE sr
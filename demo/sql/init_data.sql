INSERT INTO SYS_RESOURCE
(SORT_ID,CODE,P_CODE,[TEXT],[URL],[TYPE],[EXPANDED],LEAF,CREATE_DATE,CREATE_BY,MODIFY_DATE,MODIFY_BY)
VALUES
(0,'systemMana','','系统管理',NULL,0,1,0,GETDATE(),'system',GETDATE(),'system'),
(1,'resConfig','systemMana','权限资源配置',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(2,'userRoleConfig','systemMana','用户角色配置',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(3,'roleResConfig','systemMana','角色资源配置',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(4,'processMana','','流程管理',NULL,0,1,0,GETDATE(),'system',GETDATE(),'system'),
(5,'sys_todoWork','processMana','待办任务',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(6,'sys_doneWork','processMana','已办任务',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(7,'procList','processMana','流程列表',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(8,'test','','测试',NULL,0,0,0,GETDATE(),'system',GETDATE(),'system'),
(9,'workflowTest','test','流程测试',NULL,0,0,0,GETDATE(),'system',GETDATE(),'system'),
(10,'leaveApply','workflowTest','请假申请',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system')

DELETE FROM SYS_ROLE_RES_RELATION WHERE ROLE_CODE = 'admin'
INSERT INTO SYS_ROLE_RES_RELATION
(
	ROLE_CODE,
	RES_CODE,
	CREATE_DATE,
	CREATE_BY,
	MODIFY_DATE,
	MODIFY_BY
)
SELECT 'admin',sr.CODE,GETDATE(),'system',GETDATE(),'system' FROM SYS_RESOURCE sr
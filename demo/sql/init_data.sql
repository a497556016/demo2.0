INSERT INTO SYS_RESOURCE
(SORT_ID,CODE,P_CODE,[TEXT],[URL],[TYPE],[EXPANDED],LEAF,CREATE_DATE,CREATE_BY,MODIFY_DATE,MODIFY_BY)
VALUES
(0,'systemMana','','ϵͳ����',NULL,0,1,0,GETDATE(),'system',GETDATE(),'system'),
(1,'resConfig','systemMana','Ȩ����Դ����',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(2,'userRoleConfig','systemMana','�û���ɫ����',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(3,'roleResConfig','systemMana','��ɫ��Դ����',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(4,'processMana','','���̹���',NULL,0,1,0,GETDATE(),'system',GETDATE(),'system'),
(5,'sys_todoWork','processMana','��������',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(6,'sys_doneWork','processMana','�Ѱ�����',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(7,'procList','processMana','�����б�',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system'),
(8,'test','','����',NULL,0,0,0,GETDATE(),'system',GETDATE(),'system'),
(9,'workflowTest','test','���̲���',NULL,0,0,0,GETDATE(),'system',GETDATE(),'system'),
(10,'leaveApply','workflowTest','�������',NULL,1,0,1,GETDATE(),'system',GETDATE(),'system')

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
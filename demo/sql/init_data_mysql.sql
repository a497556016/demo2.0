INSERT INTO SYS_RESOURCE
(SORT_ID,CODE,P_CODE,TEXT,URL,TYPE,EXPANDED,LEAF,CREATE_DATE,CREATE_BY,MODIFY_DATE,MODIFY_BY)
VALUES
(0,'systemMana','','ϵͳ����',NULL,0,1,0,now(),'system',now(),'system'),
(1,'resConfig','systemMana','Ȩ����Դ����',NULL,1,0,1,now(),'system',now(),'system'),
(2,'userRoleConfig','systemMana','�û���ɫ����',NULL,1,0,1,now(),'system',now(),'system'),
(3,'roleResConfig','systemMana','��ɫ��Դ����',NULL,1,0,1,now(),'system',now(),'system'),
(4,'processMana','','���̹���',NULL,0,1,0,now(),'system',now(),'system'),
(5,'sys_todoWork','processMana','��������',NULL,1,0,1,now(),'system',now(),'system'),
(6,'sys_doneWork','processMana','�Ѱ�����',NULL,1,0,1,now(),'system',now(),'system'),
(7,'procList','processMana','�����б�',NULL,1,0,1,now(),'system',now(),'system'),
(8,'test','','����',NULL,0,0,0,now(),'system',now(),'system'),
(9,'workflowTest','test','���̲���',NULL,0,0,0,now(),'system',now(),'system'),
(10,'leaveApply','workflowTest','�������',NULL,1,0,1,now(),'system',now(),'system'),
(11,'extTest','test','EXT����',NULL,0,0,0,now(),'system',now(),'system'),
(12,'registForm','extTest','ע���',NULL,1,0,1,now(),'system',now(),'system'),
(13,'userListGrid','extTest','�û��б�',NULL,1,0,1,now(),'system',now(),'system')

INSERT INTO sys_user_info(person_code,last_name,PASSWORD,email,locked) VALUES('admin','admin','ICy5YqxZB1uWSwcVLSNLcA==','heshaowei_code@163.com','0')

INSERT INTO sys_role(role_code,role_name,remark) VALUES('admin','��������Ա','��������Ա')

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
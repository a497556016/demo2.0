Ext.define('Demo.Test.Workflowtest.LeaveApply.FormPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		me.labelWidth = 100;
		this.lastName = Ext.create('Ext.form.field.Text',{
			fieldLabel : '姓名',
			labelWidth : me.labelWidth,
			columnWidth : '0.49',
			name : 'lastName',
			margin : '5 5 5 5',
			value : lastName,
			readOnly : true,
			fieldStyle : 'background-color : #eee;'
		});
		this.personCode = Ext.create('Ext.form.field.Text',{
			fieldLabel : '员工编码',
			labelWidth : me.labelWidth,
			columnWidth : '0.49',
			name : 'personCode',
			margin : '5 5 5 5',
			value : personCode,
			readOnly : true,
			fieldStyle : 'background-color : #eee;'
		});
		this.leaveReason = Ext.create('Ext.form.field.TextArea',{
			fieldLabel : '请假原因',
			labelWidth : me.labelWidth,
			columnWidth : '0.98',
			name : 'leaveReason',
			margin : '5 5 5 5'
		});
		this.startDate = Ext.create('Ext.form.field.Date',{
			fieldLabel : '开始时间',
			labelWidth : me.labelWidth,
			columnWidth : '0.49',
			name : 'startDate',
			format : 'Y-m-d',
			margin : '5 5 5 5',
			editable : false,
			listeners : {
				select : function(){
					var task = new Ext.util.DelayedTask(function(){
					   me.endDate.expand();
					});
					task.delay(100);
					me.endDate.setMinValue(me.startDate.getValue());
				}
			}
		});
		this.endDate = Ext.create('Ext.form.field.Date',{
			fieldLabel : '结束时间',
			labelWidth : me.labelWidth,
			columnWidth : '0.49',
			name : 'endDate',
			format : 'Y-m-d',
			margin : '5 5 5 5',
			editable : false
		});
		this.mainFieldSet = Ext.create('Ext.form.FieldSet',{
			region : 'center',
			layout : 'column',
			margin : '0 0 0 0',
			items : [me.lastName,me.personCode,me.startDate,me.endDate,me.leaveReason]
		});
		Ext.applyIf(config,{
			layout: 'border',
			items : [me.mainFieldSet],
			buttonAlign : 'center',
			fbar : [{
				text : '提交',
				handler : function(){
					me.submitProcess();
				}
			}]
		});
		this.callParent(arguments);
	},
	submitProcess : function(){
		var me = this;
		var leaveApplyInfo = me.getForm().getValues();
		requestBody('/leaveApply/auth/submitProcess.action',leaveApplyInfo,function(data){
			if(data.success){
				alert('流程提交成功！流水号：'+data.resultInfo.id);
			}else{
				alert(data.message);
			}
		});
	}
});

Ext.define('Demo.Test.Workflowtest.LeaveApply.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		var me = this;
		this.formPanel = Ext.create('Demo.Test.Workflowtest.LeaveApply.FormPanel',{
			title : '请假申请表单',
			titleAlign : 'center',
			region : 'center'
		});
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.formPanel],
			listeners : {
				afterrender : function(){
					var width = me.getWidth();
					var marWidth = (width-600)/2;
					me.formPanel.mainFieldSet.margin = '0 '+marWidth+' 0 '+marWidth;
				}
			}
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.Test.Workflowtest.LeaveApply.MainViewport',{
		id : 'leaveApply'
	});
});
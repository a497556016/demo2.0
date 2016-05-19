Ext.define('Demo.Test.Form',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		
		this.username = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户名',
			lanelWidth : 60,
			width : 150,
			name : 'name',
			allowBlank : false
		});
		this.password = Ext.create('Ext.form.field.Text',{
			fieldLabel : '密码',
			lanelWidth : 60,
			width : 150,
			name : 'password',
			allowBlank : false,
			inputType : 'password'
		});
		Ext.applyIf(config,{
			layout : 'form',
			items : [this.username,this.password],
			fbar : [{
				text : '确认',
				icon : '/demo/style/icons/application_add.png',
				handler : function(){
					var params = me.getForm().getValues();
					console.log(params);
				}
			},'-',{
				text : '重置',
				handler : function(){
					me.getForm().reset();
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){

	Ext.create('Demo.Test.Form',{
		title : '测试表单',
		renderTo : 'form_panel',
		width : 500
	});
});
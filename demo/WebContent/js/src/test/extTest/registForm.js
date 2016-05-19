Ext.define('Demo.Test.ExtTest.RegistForm',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		this.userId = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户ID',
			labelWidth : 60,
			name : 'userId',
			allowBlank : false
		});
		this.password = Ext.create('Ext.form.field.Text',{
			fieldLabel : '密码',
			labelWidth : 60,
			name : 'password',
			inputType : 'password',
			allowBlank : false
		});
		this.email = Ext.create('Ext.form.field.Text',{
			fieldLabel : '邮箱',
			labelWidth : 60,
			vtype : 'email',
			name : 'email',
			allowBlank : false
		});
		this.birthday = Ext.create('Ext.form.field.Date',{
			fieldLabel : '生日日期',
			labelWidth : 60,
			name : 'birthday',
			format : 'Y-m-d',
			allowBlank : false,
			editable : false
		});
		this.sex = Ext.create('Ext.form.FieldContainer',{
			fieldLabel : '性别',
			labelWidth : 60,
			defaultType: 'radiofield',
			defaults: {
                flex: 1
            },
            layout: 'hbox',
			items : [{
				boxLabel  : '男',
                name      : 'sex',
                inputValue: '1',
                checked : true,
                width : 100
			},{
				boxLabel  : '女',
                name      : 'sex',
                inputValue: '0',
                checked : false,
                width : 100
			}]
		});
		this.educational = Ext.create('Ext.form.field.ComboBox',{
			fieldLabel : '学历',
			labelWidth : 60,
			name : 'educational',
			store : Ext.create('Ext.data.Store',{
				fields : ['name','value'],
				data : [['博士','博士'],['研究生','研究生'],['本科','本科']]
			}),
			displayField : 'name',
			valueField : 'value',
			allowBlank : false,
			editable : false
		});
		this.submitBtn = Ext.create('Ext.button.Button',{
			text : '提交',
			formBind : true,
			handler : function(){
				me.submitFunc();
			}
		});
		this.resetBtn = Ext.create('Ext.button.Button',{
			text : '重置',
			handler : function(){
				me.getForm().reset();
			}
		});
		Ext.applyIf(config,{
			layout : 'form',
			width : 500,
			items : [this.userId,this.password,this.email,this.birthday,this.sex,this.educational],
			fbar : [this.submitBtn,this.resetBtn]
		});
		this.callParent(arguments);
	},
	submitFunc : function(){
		var me = this;
		var params = me.getForm().getValues();
		alert(params);
		if(userDatas){
			userDatas.push(params);
			console.log(userDatas);
		}
		if(me.doAfterSubmit){
			me.doAfterSubmit();
		}
	}
});

if(isRegistPage){
	Ext.onReady(function(){
		Ext.create('Demo.Test.ExtTest.RegistForm',{
			title : '用户注册',
			renderTo : 'form_content'
		});
	});
}
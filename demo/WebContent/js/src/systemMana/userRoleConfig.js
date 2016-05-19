Ext.define('Demo.UserRoleConfig.UserQueryPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		this.personCode = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户名',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
			name : 'personCode'
		});
		this.lastName = Ext.create('Ext.form.field.Text',{
			fieldLabel : '姓名',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
			name : 'lastName'
		});
		Ext.applyIf(config,{
			layout : 'column',
			items : [this.personCode,this.lastName],
			buttonAlign : 'center',
			buttons : [{
				text : '查询',
				handler : function(){
					me.search();
				}
			},'-',{
				text : '重置',
				handler : function(){
					
				}
			}]
		});
		this.callParent(arguments);
	},
	search : function(){
		var me = this;
		this.store = me.up('viewport').userListGrid.getStore();
		me.store.loadPage(1);
	}
});

Ext.define('Demo.UserRoleConfig.UserListGrid',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		this.store = Ext.create('Ext.data.Store',{
			proxy : {
				type : 'ajax',
				url : basePath + '/userRoleConfig/auth/queryUserInfos.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			}
		});
		
		Ext.applyIf(config,{
			rowLines : true,
			columnLines : true,
			selModel: 'checkboxmodel',
		   
			columns : [
				{xtype : 'rownumberer',width : 35},
				{text : 'ID',dataIndex : 'id'},
				{
					text : '用户名',dataIndex : 'personCode'
				},
				{
					text : '密码',dataIndex : 'password',hidden : true
				},
				{
					text : '姓名',dataIndex : 'lastName'
				},
				{
					text : '邮箱',dataIndex : 'email',width : 250,
					renderer : function(v){
						return '<a href="mailto:'+v+'">'+v+'</a>';
					}
				}
			],
		    dockedItems: [{
		    	xtype: 'toolbar',
		    	dock : 'top',
		    	items : [{
		    		text : '添加用户',
		    		handler : function(){
						Ext.create('Demo.UserRoleConfig.UserEditWind',{
							type : 'add',
							grid : me
						}).show();	    		
		    		}
		    	},'-',{
		    		text : '编辑用户',
		    		handler : function(){
		    			var record = me.getSelectionModel().getLastSelected();
						Ext.create('Demo.UserRoleConfig.UserEditWind',{
							type : 'edit',
							userData : record.getData(),
							grid : me
						}).show();	   		
		    		}
		    	},'-',{
		    		text : '删除用户',
		    		handler : function(){
						me.deleteUser();		    		
		    		}
		    	},'-',{
		    		text : '分配角色',
		    		handler : function(){
								    		
		    		}
		    	},'-']
		    },{
		        xtype: 'pagingtoolbar',
		        store: me.store, // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
		    }]
		});
		this.callParent(arguments);
	},
	deleteUser : function(){
		var me = this;
		var records = me.getSelectionModel().getSelection();
		var ids = new Array();
		Ext.each(records,function(record){
			ids.push(record.get('id'));
		});
		confirm('是否删除？',function(obj){
			if(obj=='yes'){
				requestBody('/userRoleConfig/auth/deleteUser.action',ids,function(data){
					if(data.success){
						alert('删除成功！');
						me.store.reload();
					}else{
						alert('删除失败！');
					}
				});
			}
		});
	}
});

Ext.define('Demo.UserRoleConfig.UserEditWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.type = config.type;
		me.userData = config.userData;
		this.form = Ext.create('Demo.UserRoleConfig.UserEditForm',{
			type : me.type
		});
		Ext.applyIf(config,{
			title : me.type=='add'?'添加用户':'编辑用户',
			modal : true,
			closeAction : 'hide',
			layout : 'fit',
			items : [this.form],
			buttonAlign : 'right',
			buttons : [{
				text : '确认',
				handler : function(){
					var form = me.form.getForm();
					if(form.isValid()){
						if(me.type=='add'){
							me.addUser();
						}else if(me.type=='edit'){
							me.modifyUser();
						}
					}else{
						alertFormErrorMsg(form)
					}
				}
			},'-',{
				text : '取消',
				handler : function(){
					me.close();
				}
			}],
			listeners : {
				afterrender : function(){
					me.form.getForm().setValues(me.userData);
				}
			}
		});
		this.callParent(arguments);
	},
	addUser : function(){
		var me = this;
		var userData = me.form.getForm().getValues();
		console.log(userData);
		request('/userRoleConfig/auth/addUser.action',userData,function(data){
			if(data.success){
				alert('添加成功！',function(){
					me.close();	
				});	
				me.grid.getStore().reload();
			}else{
				alert('添加失败！');
			}
		},{maskTarget : me});
	},
	modifyUser : function(){
		var me = this;
		var userData = me.form.getForm().getValues();
		if(userData.password==''){
			delete userData.password;
		}
		console.log(userData);
		requestBody('/userRoleConfig/auth/modifyUser.action',userData,function(data){
			if(data.success){
				alert('修改成功！',function(){
					me.close();	
					me.grid.getStore().reload();
				});	
			}else{
				alert('修改失败！');
			}
		},{maskTarget : me});
	}
});

Ext.define('Demo.UserRoleConfig.UserEditForm',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		me.type = config.type;
		this.userId = Ext.create('Ext.form.field.Hidden',{
			name : 'id'
		});
		this.personCode = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户名',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
			name : 'personCode',
			allowBlank : false,
			regex : /^\w{4,20}$/,
			regexText : '只能为英文和数字，且长度在4到20位！'
		});
		this.password = Ext.create('Ext.form.field.Text',{
			fieldLabel : '密码',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
//			inputType : 'password',
			name : 'password',
			allowBlank : me.type=='add'?false:true,
			regex : /^\S{4,16}$/,
			regexText : '只能输入英文、数字，且必须包含英文和数字！'
		});
		this.lastName = Ext.create('Ext.form.field.Text',{
			fieldLabel : '姓名',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
			name : 'lastName',
			allowBlank : false,
			regex : /^[a-zA-Z0-9\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]+$/,
			regexText : '必须为中文字母数字'
		});
		this.email = Ext.create('Ext.form.field.Text',{
			fieldLabel : '邮箱',
			labelWidth : 60,
			columnWidth : '0.25',
			margin : '5 5 5 5',
			vtype : 'email',
			name : 'email',
			allowBlank : false
		});
		Ext.applyIf(config,{
			layout : 'form',
			items : [this.userId,this.personCode,this.password,this.lastName,this.email]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.UserRoleConfig.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		this.userQueryPanel = Ext.create('Demo.UserRoleConfig.UserQueryPanel',{
			region : 'north'
		});
		this.userListGrid = Ext.create('Demo.UserRoleConfig.UserListGrid',{
			region : 'center'
		});
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.userQueryPanel,this.userListGrid]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.UserRoleConfig.MainViewport',{
		id : 'userRoleConfig'
	});
});
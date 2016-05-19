Ext.application({
    name: 'Sencha',
    launch: function() {
		var view = Ext.create('Ext.navigation.View', {
		    fullscreen: true,
		    items: [{
	            title: '用户登录',
	            layout : 'vbox',
	            items : [{
	            	flex : 1
	            },{
	            	flex : 3,
	            	items : [{
		            	xtype : 'textfield',
		            	label : '用户名',
		            	name : 'username',
		            	id : 'username'
		            },{
		            	xtype : 'passwordfield',
		            	label : '密码',
		            	name : 'password',
		            	id : 'password'
		            },{
		            	xtype :'button',
		            	text : '登录',
		            	handler : function(){
		            		var mask = Ext.create('Ext.LoadMask',{
		            			message : '加载中...'
		            		});
		            		view.add(mask);
		            		var username = Ext.getCmp('username').getValue();
							var password = Ext.getCmp('password').getValue();
							Ext.Ajax.request({
								url : basePath + '/system/login.action',
								params : {
									personCode :　username,
									password : password
								},
								success : function(response, opts){
									view.remove(mask);
									var obj = Ext.decode(response.responseText);
									window.location.href = basePath + '/mobile_main.jsp'
									/*if(obj.success){
									 	
									}else{
										Ext.Msg.confirm('提示','登录失败！');
									}*/
								}
							});
							
		            	}
		            }]
	            }]
	        }]
		});
    }
});



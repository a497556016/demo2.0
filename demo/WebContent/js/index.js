Ext.onReady(function(){
	Ext.create('Ext.window.Window',{
		id : 'main_login_wind',
		width : 300,
//		height : 200,
		layout : 'form',
		items : [{
			id : 'username',
			name : 'personCode',
			xtype : 'textfield',
			fieldLabel : '用户名',
			labelWidth : 60,
			width : 150,
			margin : '5 5 5 5'
		},{
			id : 'password',
			name : 'password',
			xtype : 'textfield',
			inputType : 'password',
			fieldLabel : '密码',
			labelWidth : 60,
			width : 150,
			margin : '5 5 5 5'
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '登录',
			icon : iconPath('login'),
			handler : function(obj){
				login();
//				window.location.href="grid.jsp";
			}
		},{
			text : '重置',
			icon : iconPath('reset'),
			handler : function(){
				Ext.getCmp('username').reset();
				Ext.getCmp('password').reset();
			}
		}]
	}).show();
	Ext.getCmp('username').on('specialkey',function(field, e){
		// e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
        // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
        if (e.getKey() == e.ENTER) {
            login();
        }
	});
	Ext.getCmp('password').on('specialkey',function(field, e){
        if (e.getKey() == e.ENTER) {
            login();
        }
	});
});

function login(){
	var username = Ext.getCmp('username').getValue();
	var password = Ext.getCmp('password').getValue();
	request('/system/login.action',{
		personCode :　username,
		password : password
	},function(data){
		if(data.success){
			window.location.href = basePath + "/web/main.action";
		}else{
			alert('登录失败！');
		}
	},{maskTarget : Ext.getCmp('main_login_wind')});
}
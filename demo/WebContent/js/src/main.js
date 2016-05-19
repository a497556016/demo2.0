Ext.define('Demo.Main.LeftTreeMenu',{
	extend : 'Ext.tree.Panel',
	constructor : function(config){
		this.store = Ext.create('Ext.data.TreeStore', {
			proxy: {
	            type: 'ajax',
	            url : basePath + '/system/getUserMenu.action',
	            reader: {
	                type : 'json'
	            }
	        },
		    root: {
		        expanded: true,
		        children: [
		            
		        ]
		    },
		    autoLoad : true
		});
		Ext.applyIf(config,{
			title: '菜单栏目',
		    width: 200,
		    store: this.store,
		    rootVisible: false,
		    listeners : {
		    	itemclick : function(panel, record, item, index, e, eOpts){
		    		var me = this;
		    		this.type = record.get('type');
		    		this.code = record.get('code');
		    		this.tabPanel = panel.up('viewport').centerTabPanel;
		    		if(me.type=='1'){
		    			var newtab =Ext.getCmp('tab_'+me.code);
		    			if(!newtab){
			    			newtab = me.tabPanel.add({
			    				id : 'tab_'+me.code,
			    				title : record.get('text'),
			    				html : '<iframe frameborder="0"  border="0" marginwidth="0" marginheight="0" scrolling="no" width="100%" height="100%" src="'+basePath + '/web/'+me.code+'.action"></iframe>',
			    				closable : true,
			    				closeAction : 'destroy'
			    			});
		    			}
		    			me.tabPanel.setActiveTab(newtab);
		    		}
		    	}
		    }
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.Main.CenterTabPanel',{
	extend : 'Ext.tab.Panel',
	constructor : function(config){
		
		Ext.applyIf(config,{
			items: [{
		        title: '主页'
		    }]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.Main.TopTitlePanel',{
	extend : 'Ext.panel.Panel',
	constructor : function(config){
		
		Ext.applyIf(config,{
			html : '',
			bbar : ['欢迎用户：<span style="color:blue;">'+lastName+'</span> 登录系统','->',{
				text : '登出',
				handler : function(){
					window.location.href=basePath + "/system/logout.action";
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.Main.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		this.leftTreeMenu = Ext.create('Demo.Main.LeftTreeMenu',{
			region : 'west',
			split : true
		});
		this.centerTabPanel = Ext.create('Demo.Main.CenterTabPanel',{
			region : 'center'
		});
		this.topTitlePanel = Ext.create('Demo.Main.TopTitlePanel',{
			region : 'north'
		});
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.leftTreeMenu,this.centerTabPanel,this.topTitlePanel]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.Main.MainViewport',{
		id : 'main_page'
	});
});
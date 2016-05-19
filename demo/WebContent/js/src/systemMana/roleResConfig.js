Ext.define('Demo.SystemMana.RoleResConfig.QueryPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		this.roleName = Ext.create('Ext.form.field.Text',{
			fieldLabel : '角色名称',
			labelWidth : 60,
			width : 160,
			name : 'roleName',
			margin : '5 5 5 5'
		});
	
		Ext.applyIf(config,{
			layout : {
				type : 'table',
				columns : 1
			},
			items : [this.roleName],
			buttonAlign : 'center',
			fbar : [{
				text : '查询',
				handler : function(){
					me.search();
				}
			},'-',{
				text : '重置',
				handler : function(){
					me.getForm().reset();
				}
			}]
		});
		this.roleName.on('specialkey',function(field,e){
			if (e.getKey() == e.ENTER) {
	            me.search();
	        }
		},me);
		this.callParent(arguments);
	},
	search : function(){
		var me = this;
		var formParams = me.getForm().getValues();
		var roleListPanel = me.up('viewport').roleListPanel;
		var store = roleListPanel.getStore();
		Ext.apply(store.getProxy().extraParams,formParams);
		store.loadPage(1);
	}
});

Ext.define('Demo.SystemMana.RoleResConfig.RoleListPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		this.store = Ext.create('Ext.data.Store',{
			fields : [],
			proxy : {
				type : 'ajax',
				url : basePath + '/roleResConfig/auth/queryRoles.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			}
		});
		this.selModel = Ext.create('Ext.selection.CheckboxModel',{
			
		});
		this.editPlugin = Ext.create('Ext.grid.plugin.CellEditing',{
			clicksToEdit : 1
		});
		Ext.applyIf(config,{
			columnLines : true,
			rowLines : true,
			plugins : [this.editPlugin],
			columns : [{
				xtype : 'rownumberer'
			},{
				text : 'ID',
				dataIndex : 'id'
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.SystemMana.RoleResConfig.ResourceTree',{
	extend : 'Ext.tree.Panel',
	constructor : function(config){
		var me = this;
		this.store = Ext.create('Ext.data.TreeStore', {
			proxy: {
	            type: 'ajax',
	            url : basePath + '/system/getAllMenu.action',
	            reader: {
	                type : 'json'
	            }
	        },
		    root: {
		        expanded: true,
		        children: []
		    },
		    autoLoad : true,
		    listeners : {
		    	load : function(store){
		    		var node = me.getRootNode();
		    		me.setParentNodeChecked(node);
		    		node.eachChild(function(child) {  
						child.set('checked',false);  
						me.fireEvent('checkchange', child, false);  
					});
		    	}
		    }
		});
		Ext.applyIf(config,{
			rootVisible: false,
		    listeners : {
		    	itemclick : function(panel, record, item, index, e, eOpts){
		    		
		    	},
		    	checkchange : function(node, checked, eOpts){
					node.expand();  
					node.set('checked',checked); 
					me.setParentNodeChecked(node);
					
					node.eachChild(function(child) {  
						child.set('checked',checked);  
						me.fireEvent('checkchange', child, checked);  
					});
				}
		    }
		});
		this.callParent(arguments);
	},
	setParentNodeChecked : function(node){
		var ME = this;
		var parentNode = node.parentNode;
		if(parentNode!=null){
			var c = false;
			parentNode.eachChild(function(child){
				if(child.get('checked')){
					c = true;
				}
			});
			parentNode.set('checked',c);
			ME.setParentNodeChecked(parentNode);
		}
	}
});

Ext.define('Demo.SystemMana.RoleResConfig.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		var me = this;
		this.queryPanel = Ext.create('Demo.SystemMana.RoleResConfig.QueryPanel',{
			title : '查询条件',
			region : 'north'
		});
		this.roleListPanel = Ext.create('Demo.SystemMana.RoleResConfig.RoleListPanel',{
			title : '角色列表',
			region : 'center'
		});
		this.resourceTree = Ext.create('Demo.SystemMana.RoleResConfig.ResourceTree',{
			title : '资源分配',
			width : 300,
			region : 'east'
		});
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.queryPanel,this.roleListPanel,this.resourceTree]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.SystemMana.RoleResConfig.MainViewport',{
		id : 'role_res_config'		
	});
});
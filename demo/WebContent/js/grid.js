Ext.define('Demo.Test.Grid',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		
		this.store1 = Ext.create('Ext.data.Store',{
			proxy : {
				type : 'ajax',
				url : '/demo/js/gridData.json',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true,
			pageSize : 100
		});
		this.selModel1 = Ext.create('Ext.selection.CheckboxModel',{
//			mode : 'SINGLE'
		});
		this.eidtPlugin = Ext.create('Ext.grid.plugin.CellEditing',{
			clicksToEdit : 1
		});
		Ext.applyIf(config,{
			store : this.store1,
			selModel : this.selModel1,
			plugins : [this.eidtPlugin],
			columnLines : true,
			rowLines : true,
			columns : [{
				xtype : 'rownumberer'
			},{
				text : 'ID',
				dataIndex : 'id',
				editor : {
					xtype : 'numberfield'
				}
			},{
				text : '用户名',
				dataIndex : 'username',
				editor : {
					xtype : 'textfield',
					regex : /^\w{3,4}$/
				}
			},{
				text : '邮箱',
				dataIndex : 'username',
				editor : {
					xtype : 'textfield',
					vtype : 'email'
				}
			},{
				xtype : 'checkcolumn',
				text : '是否怎么',
				dataIndex : 'isdd'
			},{
				text : '操作咧',
				xtype : 'actioncolumn',
				items : [{
					icon : '/demo/style/icons/application_delete.png',
					handler : function(){
						Ext.Msg.alert('','删除');
					
					}
				}]
			}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: this.store, // same store GridPanel is using
		        displayInfo: true
			}],
			tbar : [{
				text : '批量删除',
				handler : function(){
					Ext.Msg.confirm('提示','确定是否删除?',function(obj){
						//获取选择的数据
						var records = me.selModel.getSelection();
						console.log(records);
						if('yes'==obj){
							Ext.Msg.alert('提示','删除成功！');
						}
						
					});
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.Test.Grid',{
		renderTo : Ext.getBody(),
		height : 600
	});
	
});
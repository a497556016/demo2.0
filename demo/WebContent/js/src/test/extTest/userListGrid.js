var userDatas = new Array();

Ext.define('Demo.Test.ExtTest.UserListGrid',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		this.store = Ext.create('Ext.data.Store',{
			fields : [
				{name : 'userId',type : 'string'},
				{name : 'password',type : 'string'},
				{name : 'sex',type : 'string'},
				{name : 'birthday',type : 'string',convert : function(v){
					if(typeof(v)!='string'){
						return Ext.Date.format(v,'Y-m-d');
					}else{
						return v;
					}
				}},
				{name : 'email',type : 'string'},
				{name : 'educational',type : 'string'}
			],
			data : userDatas,
			sorters : [{
				property : 'birthday',
				direction : 'DESC'
			}],
			autoLoad : false,
			listeners : {
				load : function(store){
					store.each(function(record){
						if(Ext.Date.parse(record.get('birthday'),'Y-m-d')-Ext.Date.parse('1990-01-01','Y-m-d')>0){
							me.getView().addRowCls(record,'red');
							me.selModel.select(record,true);
						}
					});
					
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
			columns : [
				{xtype : 'rownumberer',width : 35},
				{text : '用户ID',dataIndex : 'userId',width : 100,
				editor : {
					xtype : 'textfield',
					allowBlank : false
				}},
				{text : '密码',dataIndex : 'password',width : 100,
				editor : {
					xtype : 'textfield',
					inputType : 'password',
					allowBlank : false
				},
				renderer : function(){
					return '***';
				}},
				{text : '性别',dataIndex : 'sex',width : 100,
				editor : {
					xtype : 'combobox',
					store : {
						xtype : 'store',
						fields : ['name','value'],
						data : [['男','1'],['女','0']]
					},
					displayField : 'name',
					valueField : 'value',
					editable : false
				},
				renderer : function(v){
					if(v=='1'){
						return '男';
					}else if(v=='0'){
						return '女';
					}else{
						return '';
					}
				}},
				{text : '生日',dataIndex : 'birthday',width : 100,
				editor : {
					xtype : 'datefield',
					format : 'Y-m-d',
					editable : false
				}},
				{text : '邮箱',dataIndex : 'email',width : 100,
				editor : {
					xtype : 'textfield',
					vtype : 'email',
					allowBlank : false
				}},
				{text : '学历',dataIndex : 'educational',width : 100,
				editor : {
					xtype : 'combobox',
					store : {
						xtype : 'store',
						fields : ['name','value'],
						data : [['博士','博士'],['研究生','研究生'],['本科','本科']]
					},
					displayField : 'name',
					valueField : 'value',
					editable : false
				}},
				{
					xtype : 'actioncolumn',
					align : 'center',
					text : '操作',
					items : [{
						icon : '../style/icons/application_edit.png',
						tooltip : '更新',
						handler : function(grid, rowIndex, colIndex){
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.confirm('提示','是否更新用户：'+rec.get('userId')+'的数据？',function(obj){
								if(obj=='yes'){
									Ext.Msg.alert('提示','更新成功！');
								}
							});
						}
					},'-',{
						icon : '../style/icons/application_delete.png',
						tooltip : '删除',
						handler : function(grid, rowIndex, colIndex){
							var rec = grid.getStore().getAt(rowIndex);
							Ext.Msg.confirm('提示','是否删除用户：'+rec.get('userId')+'的数据？',function(obj){
								if(obj=='yes'){
									Ext.Msg.alert('提示','删除成功！');
								}
							});
						}
					}]
				}
			],
			bbar : [{
				xtype : 'pagingtoolbar',
				store : this.store,
				displayInfo: true
			}],
			tbar : [{
				text : '批量更新',
				icon : '../style/icons/application_edit.png',
				handler : function(){
					console.log(me.getSelDatas());
				}
			},'-',{
				text : '批量删除',
				icon : '../style/icons/application_delete.png',
				handler : function(){
					console.log(me.getSelDatas());
				}
			},'-',{
				text : '添加用户',
				icon : '../style/icons/application_add.png',
				handler : function(){
					var wind = Ext.create('Ext.window.Window',{
						title : '添加用户',
						layout : 'fit',
						modal : true,
						items : [Ext.create('Demo.Test.ExtTest.RegistForm',{
							doAfterSubmit : function(){
								me.store.loadPage(1);
								wind.close();
							}
						})]
					}).show();
				}
			}]
		});
		this.callParent(arguments);
	},
	getSelDatas : function(){
		var me = this;
		var selDatas = new Array();
		var selRecords = me.selModel.getSelection();
		Ext.each(selRecords,function(record){
			selDatas.push(record.getData());
		});
		return selDatas;
	}
});

Ext.onReady(function(){
	Ext.Ajax.request({
		url : '../js/src/test/extTest/userData.json',
		success : function(response){
			var data = Ext.decode(response.responseText);
			userDatas = data.rows;
			var userListGrid = Ext.create('Demo.Test.ExtTest.UserListGrid',{
				renderTo : Ext.getBody()
			});
			userListGrid.getStore().loadPage(1);
		}
	});
});
Ext.define('Demo.Workflow.ProcMana.QueryPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var ME = this;
		this.businessKey = Ext.create('Ext.form.field.Text',{
			fieldLabel : '流水号',
			labelWidth : 60,
			width : 180,
			name : 'businessKey',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5'
		});
		this.procInstName = Ext.create('Demo.common.Combobox',{
			fieldLabel : '流程定义名称',
			labelWidth : 90,
			width : 280,
			name : 'procDefName',
			params : {'sqlLabel':'queryProcessName'},
			editable : false,
			haveFirstSel : true,
			value : '',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5'
		});
		this.procDefName = Ext.create('Ext.form.field.Text',{
			fieldLabel : '流程名称',
			labelWidth : 60,
			width : 250,
			name : 'procInstName',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5'
		});
		Ext.applyIf(config,{
			collapsible : true,
			layout : {
				type : 'table',
				columns : 4
			},
			items : [this.businessKey,this.procInstName,this.procDefName],
			buttonAlign : 'center',
			fbar : [{
				text : '查询',
				icon : iconPath('query'),
				formBind : true,
				handler : function(){
					ME.search();
				}
			},'-',{
				xtype : 'resetbtn'
			}]
		});
		this.callParent(arguments);
	},
	search : function(){
		var ME = this;
		var listTaskPanel = ME.up('viewport').listTaskPanel;
		var store = listTaskPanel.getStore();
		var proxy = store.getProxy();
		var params = ME.getForm().getValues();
		Ext.apply(proxy.extraParams,params);
		store.reload();
	}
});

Ext.define('Demo.Workflow.ProcMana.ListTaskPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		this.store = Ext.create('Ext.data.Store',{
			fields : ['id'],
			proxy : {
				type : 'ajax',
				url : basePath + '/workflow/procMana_queryProcessList.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true
		});
		this.selModel = Ext.create('Ext.selection.CheckboxModel',{
			
		});
		this.editorPlugins = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 2,
            listeners : {
            	beforeedit : function(editor, context, eOpts){
            		var record = context.record;
            	},
            	edit : function(editor, context, eOpts){
            		var store = context.store;
            		var record = context.record;
            		var rowIdx = context.rowIdx;
            	}
            }
        });
		Ext.applyIf(config,{
			rowLines : true,
			columnLines : true,
			plugins : [this.editorPlugins],
			columns : [{
				xtype : 'rownumberer',
				width : 30
			},{
				text : '流水号',
				dataIndex : 'businessKey',
				width : 150
			},{
				text : '流程名称',
				dataIndex : 'procInstName',
				width : 160
			},{
				text : '流程定义名称',
				dataIndex : 'procDefName',
				width : 160
			},{
				text : '是否结束',
				dataIndex : 'activi',
				xtype : 'booleancolumn',
				trueText: '否',
            	falseText: '是',
            	width : 60
			},{
				text : '当前审核节点',
				dataIndex : 'curActivityName'
			},{
				text : '流程启动时间',
				dataIndex : 'startTime',
				align : 'center',
				width : 150
			},{
				text : '流程结束时间',
				dataIndex : 'endTime',
				align : 'center',
				width : 150
			},{
				text : '流程发起人',
				xtype : 'templatecolumn',
				tpl : '{startUserId} {startUserName}',
				align : 'center',
				width : 150
			},{
				text : '管理流程',
				xtype : 'actioncolumn',
				items : [{
					icon: iconPath('jumpProcess'),  // Use a URL in the icon config
	                tooltip: '流程任务推送',
	                handler: function(grid, rowIndex, colIndex) {
	                	var rec = grid.getStore().getAt(rowIndex);
	                	var activityListPanel = Ext.create('Demo.Workflow.ProcMana.ActivityListPanel',{
                			procInstId : rec.get('procInstId'),
                			curActivityId : rec.get('curActivityId'),
                			procListGrid : grid,
                			height : 250,
                			width : 250
                		});
	                	var wind = Ext.create('Ext.window.Window',{
	                		modal : true,
	                		items : [activityListPanel]
	                	});
	                	wind.show();
	                }
				},' ',{
					icon: iconPath('delete'),  // Use a URL in the icon config
	                tooltip: '删除流程',
	                handler: function(grid, rowIndex, colIndex) {
	                	confirm('是否确定删除该流程？',function(obj){
	                		if(obj == 'yes'){
	                			var rec = grid.getStore().getAt(rowIndex);
			                	var procInstId = rec.get('procInstId');
			                	request('/workflow/procMana_deleteProcInst.action',{
			                		procInstId : procInstId
			                	},function(data){
			                		if(data.success){
			                			grid.getStore().reload();
			                			alert('删除成功！');
			                		}else{
			                			alert('删除失败！');
			                		}
			                	});
	                		}
	                	});
	                	
	                }
				},' ',{
					icon: iconPath('viewProcess'),  // Use a URL in the icon config
	                tooltip: '查看流程图片',
					handler : function(grid, rowIndex, colIndex){
						var rec = grid.getStore().getAt(rowIndex);
	                	var procDefId = rec.get('procDefId'),procInstId = rec.get('procInstId');
	                	showProcDiagramViewer(procDefId,procInstId);
					}
				}],
				align : 'center'
			}],
			listeners : {
				
			},
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: this.store,   // GridPanel中使用的数据
		        displayInfo: true,
		        plugins : Ext.create('Demo.common.PageSel',{})
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.Workflow.ProcMana.ActivityListPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var ME = this;
		this.selModel = Ext.create('Ext.selection.CheckboxModel',{
//			mode : 'MULTI'
		});
		this.store = Ext.create('Ext.data.Store',{
			fields : ['id','name'],
			proxy : {
				type : 'ajax',
				url : basePath + '/workflow/procMana_getProcAllActivity.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				},
				extraParams : {
					procInstId : config.procInstId
				}
			},
			autoLoad : true,
			listeners : {
				load : function(store){
					store.each(function(record,index){
						if(record.get('id')==config.curActivityId){
							ME.getView().addRowCls(index,'light-row');
						}
					});
				}
			}
		});
		
		this.userId = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户ID',
			labelWidth : 60,
			width : 200,
			name : 'userId',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.jump(field.getValue());
                    }
                }
			},
			margin : '5 5 5 5'
		});
		this.userWind = Ext.create('Ext.window.Window',{
			modal :true,
			closeAction : 'hide',
			items : [{
				xtype : 'form',
				layout : {
					type : 'table',
					columns : 1
				},
				items : [this.userId],
				bbar : [{
					text : '确认',
					handler : function(obj){
						ME.jump(obj.up('form').getForm().getValues().userId);
					}
				}] 
			}]
		});
		Ext.applyIf(config,{
			columnLines : true,
			rowLines : true,
//			height : 200,
			columns : [{
				xtype : 'rownumberer',
				width : 30
			},{
				text : 'ID',
				dataIndex : 'id'
			},{
				text : '名称',
				dataIndex : 'name'
			}],
			tbar : [{
				text : '推送',
				iconCls : 'Anchor',
				handler : function(){
					var record = ME.selModel.getLastSelected();
					if(record/*&&record.get('id')!=ME.curActivityId*/){
						ME.userWind.show();
						ME.userId.focus();
					}else{
						alert('请选择一个节点进行推送！');
					}
				}
			}]
		});
		this.callParent(arguments);
	},
	jump : function(userId){
		var ME = this;
		var sel = ME.selModel.getLastSelected();
		request('/workflow/procMana_jumpTask.action',{
			procInstId : ME.procInstId,
			activityId : sel.get('id'),
			userId : userId
		},function(data){
			if(data.success){
				alert('推送成功！');
				ME.up('window').close();
				ME.userWind.close();
				ME.procListGrid.store.reload();
			}else{
				alert('推送失败！');
			}
		});
	}
});

Ext.define('Demo.Workflow.ProcMana.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		this.queryPanel = Ext.create('Demo.Workflow.ProcMana.QueryPanel',{
			id : config.id + '_queryPanel',
			region : 'north'
		});
		this.listTaskPanel = Ext.create('Demo.Workflow.ProcMana.ListTaskPanel',{
			id : config.id + '_listTaskPanel',
			region : 'center'
		});
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.queryPanel,this.listTaskPanel]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.Workflow.ProcMana.MainViewport',{
		id : 'procMana'
	});
});
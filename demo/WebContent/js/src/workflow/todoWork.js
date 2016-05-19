Ext.define('Demo.Workflow.TodoWork.QueryPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var ME = this;
		this.userId = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户ID',
			labelWidth : 60,
			width : 180,
			name : 'userId',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5',
			hidden : config.type=='user'
		});
		this.businessKey = Ext.create('Ext.form.field.Text',{
			fieldLabel : '流水号',
			labelWidth : 60,
			width : 250,
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
		/*this.procInstName = Ext.create('Demo.common.Combobox',{
			fieldLabel : '流程名称',
			labelWidth : 60,
			width : 250,
			name : 'procInstName',
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
		});*/
		this.processRemark = Ext.create('Ext.form.field.Text',{
			fieldLabel : '摘要',
			labelWidth : 60,
			width : 250,
			name : 'processRemark',
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
//			height : 100,
			layout : {
				type : 'table',
				columns : 4
			},
			items : [this.userId,this.businessKey/*,this.procInstName*/,this.processRemark],
			buttonAlign : 'center',
			fbar : [{
				text : '查询',
				icon : iconPath('query'),
				formBind : true,
				handler : function(){
					ME.search();
				}
			},'-',{
				text : '重置'
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

Ext.define('Demo.Workflow.TodoWork.ListTaskPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		this.store = Ext.create('Ext.data.Store',{
			fields : ['id'],
			proxy : {
				type : 'ajax',
				url : basePath + '/workflow/todoWork_queryUserTodoTasklist.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true
		});
		parent.todoTaskStore = this.store;
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
				text : '任务ID',
				dataIndex : 'id',
				hidden : true
			},{
				text : '流水号',
				dataIndex : 'businessKey',
				width : 120,
				align : 'center',
				editor : {xtype : 'textfield',readOnly : true,selectOnFocus : true},
				renderer : function(val){
					return '<a class="businessKey" href="javascript:;" data-qtitle="提示" data-qwidth="180" data-qtip="双击可复制">'+val+'</a>';
				}
			},{
				text : '流程名称',
				dataIndex : 'procDefName',
				width : 130
			},{
				text : '摘要',
				dataIndex : 'processRemark',
				width : 260,
				renderer : renderTips
			},{
				text : '任务名称',
				dataIndex : 'name'
			},{
				text : '任务到达时间',
				dataIndex : 'createTime',
				align : 'center',
				width : 135
			},{
				text : '流程发起人',
				xtype : 'templatecolumn',
				tpl : '{procStartUserId} {procStartUserName}',
				align : 'center',
				width : 135
			},{
				text : '流程发起时间',
				dataIndex : 'procStartTime',
				align : 'center',
				width : 135
			},{
				text : '流程追踪',
				xtype : 'actioncolumn',
				items : [{
					icon: iconPath('viewProcess'),  // Use a URL in the icon config
	                tooltip: '查看流程图片',
	                handler: function(grid, rowIndex, colIndex) {
	                	var rec = grid.getStore().getAt(rowIndex);
	                	var procDefId = rec.get('procDefId'),procInstId = rec.get('procInstId');
	                	showProcDiagramViewer(procDefId,procInstId);
	                	
//	                    
	                }
				}],
				align : 'center'
			}],
			listeners : {
				cellclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
					var businessKey = e.getTarget(".businessKey");
					var procDefId = record.get('procDefId');
					if(businessKey){
						var url = '';
						if(procDefId.indexOf('VrmProcess')>-1){
							url = vrmProcess_check_url;
						}
						url += '?taskId='+record.get('id')+'&procInstId='+record.get('procInstId')
						+'&taskDefKey='+record.get('taskDefKey')
						+'&businessKey='+record.get('businessKey');
						parent.addTabPanel(null,'审批任务:'+record.get('name'),url);
					}
				}
			},
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: this.store,   // GridPanel中使用的数据
		        displayInfo: true
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.Workflow.TodoWork.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		this.queryPanel = Ext.create('Demo.Workflow.TodoWork.QueryPanel',{
			id : config.id + '_queryPanel',
			region : 'north',
			type : config.type
		});
		this.listTaskPanel = Ext.create('Demo.Workflow.TodoWork.ListTaskPanel',{
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


Ext.define('Demo.Workflow.DoneWork.QueryPanel',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var ME = this;
		this.userId = Ext.create('Ext.form.field.Text',{
			fieldLabel : '用户ID',
			labelWidth : 60,
			columnWidth : '0.15',
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
			labelWidth : 45,
			columnWidth : '0.2',
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
			columnWidth : '0.3',
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
		this.minTaskEndTime = Ext.create('Ext.form.field.Date',{
			fieldLabel : '任务处理时间',
			labelWidth : 90,
			columnWidth : '0.17',
			name : 'minTaskEndTime',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5',
			format : 'Y-m-d'
		});
		this.maxTaskEndTime = Ext.create('Ext.form.field.Date',{
			fieldLabel : '-',
			labelWidth : 15,
			columnWidth : '0.11',
			name : 'maxTaskEndTime',
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5',
			format : 'Y-m-d',
			labelSeparator : ''
		});
		this.processRemark = Ext.create('Ext.form.field.Text',{
			fieldLabel : '摘要',
			labelWidth : 30,
			columnWidth : '0.35',
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
		this.taskType = Ext.create('Demo.common.LocalCombobox',{
			fieldLabel : '任务类型',
			labelWidth : 60,
			columnWidth : '0.15',
			name : 'taskType',
			data : [['--请选择--',''],['我发起的','0'],['我办理的','1']],
			listeners : {
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                        ME.search();
                    }
                }
			},
			margin : '5 5 5 5',
			editable : false,
			value : ''
		});
		Ext.applyIf(config,{
			collapsible : true,
//			height : 130,
			layout : {
				type : 'column'
			},
			items : [this.userId,this.businessKey/*,this.procInstName*/,this.processRemark,this.minTaskEndTime,this.maxTaskEndTime,this.taskType],
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

Ext.define('Demo.Workflow.DoneWork.ListTaskPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var ME = this;
		this.store = Ext.create('Ext.data.Store',{
			fields : ['id','taskId',{
				name : 'businessKey',
				type : 'string'
			},{
				name : 'procInstName',
				type : 'string'
			}],
			proxy : {
				type : 'ajax',
				url : basePath + '/workflow/doneWork_queryUserDoneTasklist.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true,
			sorters : [{
				property: 'procInstName',
         		direction: 'DESC'
			}]
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
				text : '任务ID',
				dataIndex : 'taskId',
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
				dataIndex : 'taskName'
			},{
				text : '当前处理人',
				xtype : 'templatecolumn',
				tpl : '{taskAcceptPersonId} {taskAcceptPersonName}',
				align : 'center',
				width : 135
			},{
				text : '当前处理步骤',
				dataIndex : 'curTaskName'
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
				text : '任务到达时间',
				dataIndex : 'taskStartTime',
				align : 'center',
				width : 135
			},{
				text : '任务处理时间',
				dataIndex : 'taskEndTime',
				align : 'center',
				width : 135
			},{
				text : '任务用时',
				dataIndex : 'taskDuration',
				width : 100,
				align : 'right',
				renderer : msTimeRenderer
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
							url = vrmProcess_show_url;
						}
						url += '?taskId='+record.get('taskId')+'&procInstId='+record.get('procInstId')
						+'&taskDefKey='+record.get('taskDefKey')
						+'&curTaskDefKey='+record.get('curTaskDefKey')
						+'&businessKey='+record.get('businessKey');
						parent.addTabPanel(null,'查看信息:'+record.get('taskName'),url);
					}
				},
				sortchange : function(ct, column, direction, eOpts){
					console.log(ct, column, direction, eOpts);
					var dataIndex = column.dataIndex;
//					ME.un('sortchange',eOpts.sortchange);
				}
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

Ext.define('Demo.Workflow.DoneWork.MainViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		this.queryPanel = Ext.create('Demo.Workflow.DoneWork.QueryPanel',{
			id : config.id + '_queryPanel',
			region : 'north',
			type : config.type
		});
		this.listTaskPanel = Ext.create('Demo.Workflow.DoneWork.ListTaskPanel',{
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


Ext.define('Demo.common.ResetBtn',{
	extend : 'Ext.button.Button',
	alias: 'widget.resetbtn',
	constructor : function(config){
		var ME = this;
		this.bindForm = config.bindForm;
		Ext.applyIf(config,{
			text : '重置',
			icon : iconPath('reset'),
			handler : function(){
				if(ME.bindForm){
					ME.bindForm.getForm().reset();
				}else if(ME.up('form')){
					ME.up('form').getForm().reset();
				}
			}
		});
		this.callParent(arguments);
	}
});

Ext.define('Demo.common.BasicCombobox',{
	extend : 'Ext.form.field.ComboBox',
	alias: 'widget.opbasiccombobox',
	constructor : function(config){
		var ME = this;
		this.selectFirst = config.selectFirst;
		this.afterLoadFunc = config.afterLoadFunc;
		this.store = Ext.create('Ext.data.Store',{
	    	fields : ['name','value'],
	    	proxy : {
	    		type : 'ajax',
	    		url : basePath + '/common/basicInfo.action',
	    		extraParams : {type : config.basicType},
	    		reader : {
	    			type: 'json',
	 	            rootProperty: 'rows'
	    		}
	    	},
			autoLoad : true,
			listeners : {
				load : function(store,records,successful){
					if(config.haveFirstSel){
						store.insert(0,{name : '--请选择--',value : ''})
					}
					if(ME.selectFirst){
	    				var count = store.getCount();
	    				if(count>0){
	    					var value = store.getAt(0).getData().value;
	    					ME.setValue(value);
	    				}
					}
					if(ME.afterLoadFunc){
						ME.afterLoadFunc(store,records);
					}
				}
			}
	    });
		Ext.applyIf(this.listeners,config.listeners);
		Ext.applyIf(config,{
			displayField: 'name',
		    valueField: 'value',
		    store : this.store,
		    validator : function(value){
		    	if(!ME.allowBlank&&(Ext.String.trim(value).length==0||value.indexOf('请选择')>-1)){
		    		return '请选择一项';
		    	}else{
		    		return true;
		    	}
		    }
		});
		this.callParent(arguments);
	},
	getDiaplayField : function(){
		var value = this.getValue();
		var name = '';
			this.store.each(function(record){
				if(record.data.value==value){
					name = record.data.name;
				}
			});
			return name;
	}
});
	
Ext.define('Demo.common.Combobox',{
	extend : 'Ext.form.field.ComboBox',
	alias: 'widget.opcombobox',
	constructor : function(config){
		var ME = this;
		this.selectFirst = config.selectFirst;
		this.afterLoadFunc = config.afterLoadFunc;
		this.listeners = {
			load : function(store,records,successful){
				if(successful){
					if(config.haveFirstSel){
						store.insert(0,{name : '--请选择--',value : ''})
					}
					
					if(ME.value&&ME.value!=''){
						ME.setValue(ME.value);
					}
					else if(ME.selectFirst){
						var count = store.getCount();
	    				if(count>0){
	    					var value = store.getAt(0).getData().value;
	    					ME.setValue(value);
	    					var rs = new Array();
	    					rs.push(store.getAt(0));
	    					ME.fireEvent('select',ME,rs);
	    				}
	    				ME.selectFirst = false;
					}
				}
				if(config.setGridValue){
					config.setGridValue(records);
				}
				
				if(ME.afterLoadFunc){
					ME.afterLoadFunc(store,records);
				}
			},
			beforeload : function(store,operation,opts){
				if(config.beforeLoad){
					config.beforeLoad(store,operation,opts);
				}
			}
		};
		Ext.applyIf(this.listeners,config.listeners);
		this.store = Ext.create('Ext.data.Store',{
	    	fields : ['name','value','data'],
	    	proxy : {
	    		type : 'ajax',
	    		url : basePath + '/combo/'+(config.target?config.target:'queryBySqlLabel')+'.action',
	    		extraParams : config.params?config.params:{},
	    		reader : {
	    			type: 'json',
	 	            rootProperty: 'rows'
	    		}
	    	},
			autoLoad : config.autoLoad?config.autoLoad:true,
			listeners : this.listeners
	    })
		Ext.applyIf(config,{
			displayField: config.displayField?config.displayField:'name',
		    valueField: config.valueField?config.valueField:'value',
		    store : this.store,
		    validator : function(value){
		    	if(!ME.allowBlank&&(Ext.String.trim(value).length==0||value.indexOf('请选择')>-1)){
		    		return '请选择一项';
		    	}else{
		    		return true;
		    	}
		    }
		});
		this.callParent(arguments);
	},
	getDiaplayField : function(){
		var value = this.getValue();
		var name = '';
		this.store.each(function(record){
			if(record.data.value==value){
				name = record.data.name;
			}
		});
		return name;
	},
	getNameValue : function(){
		var name = this.getValue();
		var value = '';
			this.store.each(function(record){
				if(record.data.name==name){
					value = record.data.value;
				}
			});
			return value;
		},
		getSelected : function(){
			var value = this.getValue();
			var re;
			this.store.each(function(record){
				if(record.data.value==value){
					re = record;
				}
			});
			return re;
		},
		checkInputValue : function(){
			var value = this.getValue();
			var flag = false;
			this.store.each(function(record){
				if(record.data.value==value){
					flag = true;
				}
			});
			return flag;
		},
		setSelectFirst : function(v){
			this.selectFirst = v;
		}
});
	
Ext.define('Combobox', {
	 extend: 'Ext.data.Model',
	 fields: [
	     {name: 'name', value: 'string'},
	     {name: 'value',  type: 'string'}
	     ]
});
Ext.define('Demo.common.LocalCombobox',{
	extend : 'Ext.form.field.ComboBox',
	alias: 'widget.localcombobox',
	constructor : function(config){
		var ME = this;
		this.selectFirst = config.selectFirst;
		this.store = null;
		if(config.data){
	//				if(config.haveFirstSel){
	//					Ext.Array.insert(config.data,0,[{name : '--请选择--',value : ''}]);
	//				}
			this.store = Ext.create('Ext.data.Store',{
				model : "Combobox",
				data : config.data,
		    	autoLoad : true,
	    		listeners : {
	    			load : function(store,records,successful){
	    				if(config.haveFirstSel){
							store.insert(0,{name : '--请选择--',value : ''})
						}
	    				ME.loadedFunc(store,records,successful);
	    			},
	    			refresh : function(store,opts){
	    				if(config.haveFirstSel){
							store.insert(0,{name : '--请选择--',value : ''})
						}
	    			}
	    		}
			});
		}else{
			this.store = Ext.create('Ext.data.Store',{
				model : "Combobox",
				proxy : {
		    		type : 'ajax',
		    		url : basePath + '/data/combobox_data.json',
		    		reader : {
		    			type: 'json',
		 	            rootProperty: config.target
		    		}
		    	},
		    	autoLoad : true,
	    		listeners : {
	    			load : function(store,records,successful){
	    				ME.loadedFunc(store,records,successful);
	    			}
	    		}
			});
		}
		
		
		Ext.applyIf(config,{
			displayField: 'name',
		    valueField: 'value',
		    queryMode: 'local',
		    store : this.store
		});
		this.callParent(arguments);
	},
	getDiaplayField : function(){
		var value = this.getValue();
		var name = '';
		this.store.each(function(record){
			if(record.data.value==value){
				name = record.data.name;
			}
		});
		return name;
	},
	loadedFunc : function(store,records,successful){
		var ME = this;
		if(ME.value!=null&&ME.value != undefined){
			ME.setValue(ME.value);
		}
		else if(ME.selectFirst){
			var count = store.getCount();
			if(count>0){
				var value = store.getAt(0).getData().value;
				ME.setValue(value);
			}
		}
		
	}
});
	
	
Ext.define('Demo.common.PageSel',{
	extend: 'Ext.plugin.Abstract',
	alias: 'plugin.pagesel',
	setCmp: function(target) {
		
	    this.callParent(arguments);
	//	        console.log(target);
	    this.target = target;
	    this.pageCombobox = Ext.create('Demo.common.LocalCombobox',{
			width : 50,
			data : [['10',10],['25',25],['30',30],['50',50],['100',100]],
			value : 25,
			editable : false
		});
	    var items = ['-','每页显示',this.pageCombobox,'条'];
	    Ext.each(items,function(item){
	    	target.items.push(item);
	    });
	    this.pageCombobox.on('select',this.comSelected,this,{grid : target})
	   
	},
	comSelected : function(combo, records, eOpts){
		var ME = this;
		var lastSelected = records[records.length-1];
		var selValue = lastSelected.get('value');
	//	    	eOpts.grid.getStore().getProxy().setExtraParam('limit',selValue);
		eOpts.grid.getStore().pageSize = selValue;
		eOpts.grid.getStore().loadPage(1);
	//	    	console.log(ME.target.findParentByType('grid'));
    }
});
	
Ext.define('Demo.common.ComboMonthDate',{
	extend : 'Ext.form.field.ComboBox',
	monthData : new Array(),
	value : null,
	constructor : function(config){
		var ME = this;
		this.minDate = config.minDate;
		this.store = Ext.create('Ext.data.Store',{
			fields : ['name','value'],
			data : ME.monthData
		});
		this.initFunc();
		
		Ext.applyIf(config,{
			
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'value'
	
		});
		this.callParent(arguments);
	},
	initFunc : function(){
		var ME = this;
		var curDate = this.getCurDate();
		this.value = curDate;
		ME.setDatas(curDate)
		
	},
	getCurDate : function(){
		var curDate = Ext.Date.format(new Date(),'Y-m');
		if(this.minDate){
			var d = Ext.Date.add(new Date(this.minDate+'-01'),Ext.Date.MONTH,5);
			curDate = Ext.Date.format(d,'Y-m')
		}
		console.log(curDate);
		return curDate;
	},
	setDatas : function(date){
		var ME = this;
		var year = parseInt(date.split('-')[0]);
		var month = parseInt(date.split('-')[1]);
		ME.monthData = new Array();
		for(var i=-5;i<6;i++){
			var m = month+i;
			var y = year;
			if(m<1){
				m = 12+m;
				y = y-1;
			}
			if(m>12){
				m = m-12;
				y = y+1;
			}
			
			ME.monthData.push({
				name : y+'-'+(m<10?'0'+m:m),
				value : y+'-'+(m<10?'0'+m:m)
			})
		}
		ME.getStore().removeAll();
		ME.getStore().loadData(ME.monthData);
		ME.value = date;
	},
	setMinDate : function(minDate){
		this.minDate = minDate;
		var curDate = this.getCurDate();
			
		this.setDatas(curDate);
		this.setValue(curDate);
	}
});

Ext.define('Demo.ux.ApproveGrid',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var ME = this;
		this.taskDefKey = config.taskDefKey;
		this.curTaskDefKey = config.curTaskDefKey;//如果是已办任务，表示该任务当前的审核节点
		this.isCheck = config.isCheck;
		this.store = Ext.create('Ext.data.Store',{
			sorters : [{
				property : 'id',
				direction : 'ASC'
			}],
			fields : ['id'],
			proxy : {
				type : 'ajax',
				url : basePath + '/workflow/queryApproveInfo.action',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				},
				extraParams : {
					businessKey : config.businessKey
				}
			},
			autoLoad : config.businessKey?true:false,
			listeners : {
				load : function(store,records){
					if(ME.isCheck){
						store.each(function(record){
							if(ME.taskDefKey==record.get('approveCode')){
								record.set('remark','');
							}
						});
					}
					if(records.length>0){
						ME.up('fieldset').show();
					}
				}
			}
		});
		this.editorPlugins = Ext.create('Ext.grid.plugin.CellEditing', {
	        clicksToEdit: 1,
	        listeners : {
	        	beforeedit : function(editor, context, eOpts){
	        		var record = context.record;
	        		if(ME.taskDefKey==record.get('approveCode')&&ME.isCheck){
	        			return true;
	        		}else{
	        			return false;
	        		}
	        	},
	        	edit : function(editor, context, eOpts){
	        		var store = context.store;
	        		var record = context.record;
	        		var rowIdx = context.rowIdx;
	        	}
	        }
	    });
		Ext.applyIf(config,{
	//				title : '审核意见',
			rowLines : true,
			columnLines : true,
			plugins : [this.editorPlugins],
			columns : [
				{xtype : 'rownumberer',width : 35},
				{header : 'ID',dataIndex : 'id',hidden : true},
				{header : '流水号',dataIndex : 'businessKey',hidden : true},
				{header : '审批步骤编码',dataIndex : 'approveCode',hidden : true},
				{header : '审批步骤',dataIndex : 'approveName',width : 250},
				{header : '审批角色',dataIndex : 'approveRole',hidden : true},
				{header : '审批人',xtype : 'templatecolumn',tpl : '{lobNumber} {lastName}',width : 130,align : 'center'},
				{header : '结论',dataIndex : 'type',width : 60,align : 'center'},
				{header : '审批意见',dataIndex : 'remark',editor : {xtype : 'textfield'},flex : 1,renderer : function(val,metaData,record){
					if((ME.taskDefKey==record.get('approveCode'))
					&&(val==null||val=='')){
						return '<span style="font-style:italic;color:blue;">此处填写审批意见</span>';
					}
					if(ME.curTaskDefKey==record.get('approveCode')){
						return '<span style="font-style:italic;color:blue;"><--当前审核节点</span>';
					}
					return val;
				}},
				{header : '审批日期',dataIndex : 'lastUpdate',width : 150,align : 'center'},
				{header : '审批用时',dataIndex : 'duration',renderer : msTimeRenderer}
			]
		});
		this.callParent(arguments);
	},
	getMessage : function(){
		var ME = this;
		var msg = '';
		ME.store.each(function(record){
			if(record.get('approveCode')==ME.taskDefKey){
				msg = record.get('remark');
			}
		});
		return msg;
	}
});
	
/**
 * 文件上传窗体
 */
Ext.define('Demo.ux.FileWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var ME = this;
		this.fileType = config.fileType;
		this.url = config.url;
		this.fileform = Ext.create('Ext.form.Panel',{
			items : [{
				xtype : 'fileuploadfield',
				labelWidth : 75,
				name : 'file',
				fieldLabel : '文件路径',
				width : 250,
				margin : '5 5 5 5',
				allowBlank : false,
				validator : function(value){
					for(var i in ME.fileType){
						var type = ME.fileType[i];
						if(value.length>0&&value.indexOf(type)>-1){
							return true;
						}else{
							return "请选择文件类型为"+type+"格式的文件进行上传！";
						}
					}
					return true;
				}
			}]
		});
		Ext.applyIf(config,{
			title : '上传文件',
			layout : 'fit',
			modal : true,
			items : [ME.fileform],
			bbar : [{
				text : '确认',
				icon : iconPath('submit'),
				handler : function(){
					var f = ME.fileform.getForm();
					if(f.isValid()){
						f.submit({
							url : basePath + ME.url,
							params : f.getValues(),
							waitMsg : '文件上传中，请稍后。。。',
							success : function(fp, o){
								alert('文件已经上传成功！');
								ME.close();
							},
							failure : function(form,action){
								alert('文件上传失败！');
								ME.close();
							}
						});
					}else{
						showFormErrors(f);
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});
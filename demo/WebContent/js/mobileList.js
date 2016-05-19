Ext.define('Demo.Main.ListData',{
	extend : 'Ext.List',
	config : {
		store: {
	        fields: ['text','icon'],
	        data: [
	            {text: 'Lawrence',icon : ''},
	            {text: 'Kansas',icon : ''},
	            {text: 'University',icon : ''},
	            {text: 'Potter Lake',icon : ''},
	            {text: 'Kansas1',icon : ''},
	            {text: 'University1',icon : ''},
	            {text: 'Potter Lake1',icon : ''},
	            {text: 'Kansas2',icon : ''},
	            {text: 'University2',icon : ''},
	            {text: 'Potter Lake2',icon : ''},
	            {text: 'Kansas3',icon : ''},
	            {text: 'University3',icon : ''},
	            {text: 'Potter Lake3',icon : ''}
	        ]
	    },
	
	    itemTpl: '<div style="margin:10 10 10 10;"><img src="'+basePath+'{icon}"/>{text}</div>',
        
        itemCls: 'my-dataview-item',
        scrollable: {
        	direction: 'vertical'
      	}
	}
});

Ext.define('Demo.Main.Form',{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			items : [{
				xtype : 'formpanel',
				items : [{
					xtype : 'textfield',
					name : 'text',
					label : '姓名'
				},{
					xtype : 'datepickerfield',
					name : 'birthday',
					label : '生日',
					dateFormat : 'Y-m-d',
					value : new Date()
				},{
					layout : 'hbox',
					items : [{
						flex : 1,
						xtype : 'button',
						text : '提交',
						handler : function(){
						}
					},{
						flex : 1,
						xtype : 'button',
						text : '重置',
						handler : function(){
							me.reset();
						}
					}]
				}]
			}]
		});
		this.callParent(arguments);
	}
});

Ext.application({
	name : 'DemoList',
	launch : function(){
		
		
		var list = Ext.create('Demo.Main.ListData',{
			title : '主页',
		    listeners: {
	            select: function(view, record) {
	            	var form = Ext.create('Demo.Main.Form',{
						
					});
					
	            	viewport.push(form);
	            	form.setValues(record.getData());
	            }
	        }
		});
		var viewport = Ext.Viewport.add({
			xtype: 'navigationview',
			items : [list]
		});
	}
});
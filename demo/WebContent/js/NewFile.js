Ext.define('Demo.Test.MianViewport',{
	extend : 'Ext.container.Viewport',
	constructor : function(config){
		var me = this;
		
		this.northPanel = Ext.create('Ext.panel.Panel',{
			region : 'north',
			layout : 'fit',
			html : '上',
			bbar : [{
				text : 'dsd',
				handler : function(){
					alert('?');
				}
			}]
		});
		this.centerPanel = Ext.create('Ext.tab.Panel',{
			region : 'center',
			height : 100,
			layout : 'fit',
			items : [{
				title : '主页',
				html : '主页'
			}]
		});
		this.treePanel = Ext.create('Ext.tree.Panel', {
		    rootVisible: false,
		    store: {
		        
		        root: {
		            children: [{
		                text: 'Europe, ME, Africa',
		                code : '000000011111',
		                mtype: 'Territory',
		                children: [{
		                    text: 'UK of GB & NI',
		                    mtype: 'Country',
		                    children: [{
		                        text: 'London',
		                        mtype: 'City',
		                        leaf: true
		                    }]
		                }]
		            }, {
		                text: 'North America',
		                mtype: 'Territory',
		                children: [{
		                    text: 'USA',
		                    mtype: 'Country',
		                    children: [{
		                        text: 'Redwood City',
		                        mtype: 'City',
		                        leaf: true
		                    }]
		                }]
		            }]
		        }
		    },
		    listeners : {
		    	itemclick : function(panel, record, item, index, e, eOpts){
		    		console.log(record);	
		    		if(record.get('mtype')=='City'){
		    			var tab = Ext.create('Ext.tab.Tab',{
		    				title : record.get('text'),
		    				html : 'sdsds'
		    			});
		    			me.centerPanel.add(tab);
		    			me.centerPanel.setActiveTab(tab);
		    		}
		    	}
		    }
		});
		this.westPanel = Ext.create('Ext.panel.Panel',{
			region : 'west',
			width : 300,
			layout : 'fit',
			title : '菜单',
			items : [this.treePanel]
		});
		
		Ext.applyIf(config,{
			renderTo : Ext.getBody(),
			layout : 'border',
			items : [this.northPanel,this.centerPanel,this.westPanel]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	Ext.create('Demo.Test.MianViewport',{
	
	});
	
});
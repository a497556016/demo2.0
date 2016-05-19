Ext.application({
    name: 'MainPage',
    launch: function() {
    	Ext.Viewport.add({
        	xtype : 'tabpanel',
        	tabBarPosition: 'bottom',
        	defaults: {
		        styleHtmlContent: true
		    },
//		    layout: {
//                type: 'card',
//                animation: {
//                    type: 'fade'
//                }
//            },
        	items : [{
        		title : 'Home',
        		html : '<iframe style="margin : 0 0 0 0;" width=100% height="100%" frameborder="no" border="0" src="'+basePath+'/mobile_list.jsp"></iframe>',
		      	listeners : {
		      		activate : function(tab){
		      			
		      		}
		      	}
        	},{
        		title : 'MyCenter',
        		active : true,
        		html : '<iframe width=100% height="100%" frameborder="no" border="0" src="http://www.baidu.com"></iframe>'
        	},{
        		title : 'Config',
        		html : 'dsad'
        	}]
        });
    }
});






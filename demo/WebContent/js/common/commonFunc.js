var request = function(url,params,successFunc,opts){
	var me = this;
	this.maskTarget = opts?opts.maskTarget:null;
	this.mask = null;
	if(this.maskTarget){
		this.maskTarget = typeof(this.maskTarget)=='string'?Ext.get(this.maskTarget).component:this.maskTarget
	}else{
		this.maskTarget = Ext.getBody().component
	}
	this.mask = new Ext.LoadMask({
	    msg    : 'Please wait...',
	   	target : this.maskTarget
	});
	this.mask&&this.mask.show();
	
	Ext.Ajax.request({
		method : 'post',
		url : basePath + url,
		params : params,
		success : function(response){
			me.mask&&me.mask.hide();
			if(successFunc&&response.responseText.length>0){
				var data = Ext.decode(response.responseText);
				successFunc(data);
			}
		},
		failure : function(response){
			me.mask&&me.mask.hide();
			Ext.Msg.alert('提示', '<span style="color:red;">系统异常！请稍后重试！</span>');
		}
	});
}

var requestBody = function(url,params,successFunc,opts){
	var me = this;
	this.maskTarget = opts?opts.maskTarget:null;
	this.mask = null;
	if(this.maskTarget){
		this.maskTarget = typeof(this.maskTarget)=='string'?Ext.get(this.maskTarget).component:this.maskTarget
	}else{
		this.maskTarget = Ext.getBody().component
	}
	this.mask = new Ext.LoadMask({
	    msg    : 'Please wait...',
	   	target : this.maskTarget
	});
	this.mask&&this.mask.show();
	
	Ext.Ajax.request({
		method : 'post',
		url : basePath + url,
		jsonData : params,
		timeout : 60*1000,
		headers : {'Content-Type' : 'application/json'},
		success : function(response){
			me.mask&&me.mask.hide();
			if(successFunc&&response.responseText.length>0){
				var data = Ext.decode(response.responseText);
				successFunc(data);
			}
		},
		failure : function(response){
			me.mask&&me.mask.hide();
			Ext.Msg.alert('提示', '<span style="color:red;">系统异常！请稍后重试！</span>');
		}
	});
	
}

var confirm = function(msg,fn){
	Ext.Msg.confirm('确认提示',msg,fn);
}

var alert = function(msg,fun){
	Ext.Msg.show({
	    title: '提示',
	    msg: msg,
	    minWidth: 200,
	    minHeight: 120,
	    buttons: Ext.Msg.OK,
//	    multiline: true,
	    fn: fun,
	    icon: Ext.window.MessageBox.INFO,
	    cls : 'centerAlign'
	});
}

var iconPath = function(iconCode){
	return basePath + '/system/getIcon.action?iconCode='+iconCode;
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

var qtip = function(value,width){
	return '<div data-qtip="'+value+'" data-qwidth="'+(width?width:180)+'">'+value+'</div>';
}

var renderTips = function(val){
	return qtip(val,150);
}

var showProcDiagramViewer = function(procDefId,procInstId){
	var url = basePath + "/diagram-viewer/index.html?processDefinitionId="+procDefId+"&processInstanceId="+procInstId;
	var mask = new Ext.LoadMask({
		msg : "加载中，请稍后...",
		target : Ext.getBody().component
	});
	mask.show();
    var imgWind = Ext.create('Ext.window.Window',{
    	layout : 'fit',
    	height : 300,
    	width : 300,
    	maximizable : true,
    	html : '<iframe id="show_proc_img_" frameborder="no" border="0" width="100%" height="100%" src="'+url+'"></iframe>',
    	listeners : {
    		afterrender : function(obj){
    			var iframe = document.getElementById("show_proc_img_");
    			var i = 0;
    			var task = Ext.TaskManager.start({
				     run: function(){
				     	i++;
				     	if(i>=20){
				     		obj.show();
				     		mask.hide();
				     		Ext.TaskManager.stop(task);
				     		console.log("任务已停止");
				     	}
        				var win = iframe.contentWindow, 
						    doc = win.document, 
						    html = doc.documentElement, 
						    body = doc.body; 
						if(win.loadComplete){
							
							Ext.TaskManager.stop(task);
				     		console.log("任务已停止");
							var height = Math.max( body.scrollHeight, body.offsetHeight, 
           							html.clientHeight, html.scrollHeight, html.offsetHeight ); 
           					var width = Math.max(body.scrollWidth, body.offsetWidth, 
           							html.clientWidth, html.scrollWidth, html.offsetWidth);
           					obj.setHeight(height+10);
           					obj.setWidth(width+10);
           					obj.center();
           					obj.show();
           					mask.hide();
						}
        			},
				    interval: 500
				 });
				 
    			
    		}
    	}
    }).show().hide();
}

function msTimeRenderer(val){
	var day = new Number(val/(1000*60*60*24));//天数
	day = parseInt(day.toFixed(3));
	var hour = (val-1000*60*60*24*day)/(1000*60*60);//小时数
	hour = parseInt(hour);
	var minute = (val-(1000*60*60*hour+1000*60*60*24*day))/(1000*60);//分钟数
	minute = parseInt(minute)
	if(val&&val!=0&&day==0&&hour==0&&minute==0){
		return '小于1分钟';
	}
	if(!val||val==0){
		return '';
	}
	return (day?day+'天':'')+(hour?hour+'小时':'')+(minute?minute+'分钟':'');
}

var alertFormErrorMsg = function(form){
	var result = true;
	form.getFields().each(function(field){
		console.log(field);
		if($.trim(field.lastActiveError.length)>0){
			alert(field.fieldLabel+":"+field.lastActiveError);
			result = false;
			return;
		}
	});
	console.log('验证结果：'+result);
	return result;
}
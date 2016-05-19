angular.module('mainApp.service', [])
.factory('items',function(){
	return {
		getItems : function(){
			var items = window.localStorage['items'];
			if(items){
				return angular.fromJson(items);
			}else{
				return [[{
					title : '请假流程',
					sref : 'tabs.vacation'
				},{
					title : '每日签到',
					sref : 'tabs.writeOn'
				},{
					title : '我的工作',
					sref : 'tabs.myWork'
				},{
					title : '报销流程',
					sref : ''
				}],[{
					title : '请假流程2',
					sref : ''
				},{
					title : '报销流程2',
					sref : ''
				},{
					title : '报销流程2',
					sref : ''
				},{
					title : '报销流程2',
					sref : ''
				}]]
			}
		}
	}
})
.factory('baseData',function(){
	var vacationTypes = [{
		name : '事假',
		value : '事假'
	},{
		name : '年假',
		value : '年假'
	},{
		name : '病假',
		value : '病假'
	},{
		name : '产假',
		value : '产假'
	}];
	return {
		getVacationTypes : function(){
			return vacationTypes;
		}
	}
})

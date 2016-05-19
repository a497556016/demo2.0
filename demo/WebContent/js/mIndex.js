angular.module('mainApp',['ionic'])
.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
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
					title : '报销流程',
					sref : ''
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
.config(function($stateProvider, $urlRouterProvider){
	$stateProvider
	.state('tabs',{
		url : '/tab',
		abstract: true,
		templateUrl : 'm_tabs.action'
	})
	.state('tabs.home', {
      url: "/home",
      views: {
        'home-tab': {
          templateUrl: "m_home.action",
          controller: 'HomeTabCtrl'
        }
      }
    })
    .state('tabs.vacation', {
      url: "/vacation",
      views: {
        'home-tab': {
          templateUrl: "m_vacation.action",
          controller: 'VacationCtrl'
        }
      }
    })
    .state('tabs.writeOn', {
      url: "/writeOn",
      views: {
        'home-tab': {
          templateUrl: "m_writeOn.action",
          controller: 'VacationCtrl'
        }
      }
    })
    .state('tabs.about', {
      url: "/about",
      views: {
        'about-tab': {
          templateUrl: "m_about.action"
        }
      }
    })
    .state('tabs.config', {
      url: "/config",
      views: {
        'config-tab': {
          templateUrl: "m_config.action"
        }
      }
    })
	$urlRouterProvider.otherwise("/tab/home");
})
.controller('HomeTabCtrl',function($scope,$ionicModal,$ionicSlideBoxDelegate,$ionicActionSheet,items){
	$scope.myActiveSlide = 0;
	$scope.items = items.getItems();
	var slideIndex = 0;
	setInterval(function(){
		slideIndex++;
		$ionicSlideBoxDelegate.slide(slideIndex%3,2000);
	},5000);
	$scope.showBottomMenu = function(){
		// 显示操作表
	   	$ionicActionSheet.show({
		 	buttons: [
			   	{ text: '<b>Share</b> This' },
			   	{ text: 'Move' },
			],
			destructiveText: '删除',
			titleText: '操作栏目',
			cancelText: '取消',
			buttonClicked: function(index) {
			   	return true;
			},
			destructiveButtonClicked : function(a){
				console.log(a);
			
			}
	   	});
	}
})
.controller('VacationCtrl',function($scope,$ionicPopup,$ionicModal,$location,baseData){
	$scope.vacationTypes = baseData.getVacationTypes();
	$scope.vacation = {
		vacationType : '',
		vacationBeginDate : '2016-05-12 08:30',
		vacationEndDate : '',
		vacationReason : '2323'
	};
	$ionicModal.fromTemplateUrl('vt-modal.html', {
    	scope: $scope
  	}).then(function(modal) {
    	$scope.vtModal = modal;
  	});
  	
	
	$scope.submitVacationData = function(){
		console.log($scope.vacation);
		$ionicPopup.alert({
	       title: '提示',
	       template: '保存成功！'
	    }).then(function(){
	    	$location.path('/tab/home');
	    });
	}
});

function setdate(obj){
	if(!$(obj).attr('isPicker')){
		$(obj).attr('isPicker','true');
		$(obj).scroller('destroy').scroller({
			preset : 'datetime',
			stepMinute : 5,
			minDate: new Date(2012,3,10,9,22), 
			maxDate: new Date(2014,7,30,15,44),
			theme : 'default',
			mode : 'mixed',
			display : 'bottom',
			lang : 'zh',
			dateFormat : 'yyyy-mm-dd'
		}).scroller('show');
	}
}

angular.module('mainApp',['ionic','mainApp.controller','mainApp.service'])
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
          controller: 'WriteOnCtrl'
        }
      }
    })
    .state('tabs.listWriteData', {
      url: "/listWriteData",
      views: {
        'home-tab': {
          templateUrl: "m_myWork.action",
          controller: 'MyWorkCtrl'
        }
      }
    })
    .state('tabs.myWork', {
      url: "/myWork",
      views: {
        'home-tab': {
          templateUrl: "m_myWork.action",
          controller: 'MyWorkCtrl'
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
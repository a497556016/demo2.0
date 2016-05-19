angular.module('mainApp.controller', [])
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
			   	{ text: '打赏作者' }
			],
			destructiveText: '滚',
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
	
	
	$scope.resetForm = function(){
		
	}
})

.controller('WriteOnCtrl',function($scope,$ionicSideMenuDelegate,$ionicPopup){
	$scope.writeItem = [{
		index : 0,
		text : '考勤打卡'
	},{
		index : 1,
		text : '打卡记录查询'
	}];
	$scope.title = $scope.writeItem[0].text;
	$scope.basePath = basePath;
	$scope.contentUrl = $scope.basePath+'/web/m_writeOn_content.action';
	$scope.selectItem = function(item){
		$scope.title = item.text;
		//替换页面
		if(item.index==0){ 
			$scope.contentUrl = $scope.basePath+'/web/m_writeOn_content.action';
		}
		if(item.index==1){
			$scope.contentUrl = $scope.basePath+'/web/m_writeOn_listData.action';
		}
	}
	$scope.toggleLeft = function() { 
   		$ionicSideMenuDelegate.toggleLeft();
  	};
  	
})

.controller('MyWorkCtrl',function($scope){
	
})


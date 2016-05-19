angular.module('loginApp',['ionic'])
.controller('LoginContrl',function($scope,$http,$ionicModal,$ionicPopup,$location){
	$ionicModal.fromTemplateUrl('loginModal.html',{
		scope : $scope
	}).then(function(modal){
		modal.show();
	});
	$scope.user = {
		username : '',
		password : ''
	}
	$scope.login = function(){
		$http.get('/demo/system/login.action?personCode='+$scope.user.username+'&password='+$scope.user.password).success(function(response){
			if(response.success){
				$ionicPopup.alert({
					title : '提示',
					template : '登陆成功！'
				}).then(function(){
					window.location.href = '/demo/web/m_toMIndex.action';
				});
			}else{
				$ionicPopup.alert({
					title : '提示',
					template : '登陆失败！'
				});
			}
		});
	}
})
var app = angular.module("app",[]);

//app.controller("jsonpull", function($scope, $http){
//	$http.get("test.json").success(function(data){
//		$scope.info = data;
//	})
//});


app.controller("startcntrl",function($scope,$http){
	$scope.init = function () {
		$http({
			method : 'POST',
			url : 'OperationServlet?action=show'
		}).success(function(data){
			$scope.info = data;
			console.log(data);
		});
		
	}
	
	$scope.deletebook = function(id){
		$http({
			method : 'POST',
			url : 'OperationServlet?action=delete&deleteId='+id
			
		}).success(function(){
			window.location.reload();
			//init();
		});
	}
	
});
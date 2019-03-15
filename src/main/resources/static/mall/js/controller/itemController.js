 //控制层 
app.controller('itemController' ,function($scope,$controller   ,itemService){	  //
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemService.findAll().success(
			function(response){

				$scope.list=response;
				for(entity in $scope.list){
				    $scope.list[entity].stars = [false,false,false,false,false];
				    for(var i = 0; i< $scope.list[entity].cartThumbnail;i++){
				        $scope.list[entity].stars[i] = true;
				    }
				}
			}			
		);
	}    

	$scope.range = function(n) {
        return new Array(n);
    }

	//分页
	$scope.findPage=function(page,rows){
		itemService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	

	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	

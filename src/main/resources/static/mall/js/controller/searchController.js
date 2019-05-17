app.controller('searchController', function($scope,$controller,searchService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.search = function(){
        $scope.specMapOfRows = {};  //初始化新建一个map
       // var options=$("#select option:selected");  //获取选中的项
       //pageNo pageSize  category
       $scope.searchMap.pageNo = $scope.paginationConf.currentPage;
       $scope.searchMap.pageSize = $scope.paginationConf.itemsPerPage;
       $scope.searchMap.category = $("#select option:selected").text();
        searchService.search($scope.searchMap).success(
            function(response){
                $scope.paginationConf.totalItems = response.total;
                $scope.resultMap = response;
                $scope.rating($scope.resultMap.rows);
                $scope.constructRows($scope.resultMap.rows)
            }
        );
    }


    $scope.rating = function(list){
            	for(entity in list){
            		 list[entity].stars = [false,false,false,false,false];
            		 for(var i = 0; i< list[entity].cartThumbnail;i++){
            		      list[entity].stars[i] = true;
            		}
            	}
     }



    //定义赋值函数
     $scope.constructRows = function(list){ //list 是查出来的rows
                //specMapOfRows
                for(entity in list){   ///遍历list里的每个object,主要是取出每个object的specMap
                      for( key in list[entity].specMap); // 遍历map里面的每一个键值对
                      {
                        	if($scope.specMapOfRows[key]){ //存在当前的规格对应的数据，取出来不为空不为undefined
                        	    var valueSet  = $scope.specMapOfRows[key];
                        	    valueSet.push(list[entity].specMap[key]);
                        	}else {
                                var valueSet = [];
                                valueSet.push(list[entity].specMap[key]);
                                $scope.specMapOfRows[key] = valueSet;
                        	}
                      }
                }
     }



});
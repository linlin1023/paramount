app.controller('searchController', function($scope,$controller,$location,searchService,itemService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.searchMap = {};
    $scope.searchByText = function(page, row){
         var keywords = $location.search()['keywords'];
         if(keywords == "" || keywords == null){
            keywords = $scope.keywords;
         }
        $scope.specMapOfRows = {};  //初始化新建一个map

        $scope.searchMap.keywords = keywords;
        itemService.searchByText(page,  row, $scope.searchMap).success(
            function(response){
                $scope.paginationConf.totalItems = response.total;
                $scope.resultMap = response;
                $scope.rating($scope.resultMap.rows);
                $scope.constructRows($scope.resultMap.rows)
            }
        );
    }

    $scope.clearParamFirst = function(){
        $location.search()['keywords'] ="";
        $scope.searchByText($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
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
                      if(list[entity].spec == null || list[entity].spec == undefined || list[entity].spec == "")
                        continue;
                      let specMap = JSON.parse(list[entity].spec);
                      for( key in specMap); // 遍历map里面的每一个键值对
                      {
                        	if($scope.specMapOfRows[key]){ //存在当前的规格对应的数据，取出来不为空不为undefined
                        	    var valueSet  = $scope.specMapOfRows[key];
                        	    valueSet.push(specMap[key]);
                        	}else {
                                var valueSet = [];
                                valueSet.push(specMap[key]);
                                $scope.specMapOfRows[key] = valueSet;
                        	}
                      }
                }
     }



});
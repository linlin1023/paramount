app.controller('searchController', function($scope,searchService){

    $scope.search = function(){
        var options=$("#select option:selected");  //获取选中的项
        alert(options.val());   //拿到选中项的值
        alert(options.text());   //拿到选中项的文本
        searchService.search($scope.searchMap).success(
            function(response){
                $scope.resultMap = response;
                $scope.rating($scope.resultMap.rows)
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
});
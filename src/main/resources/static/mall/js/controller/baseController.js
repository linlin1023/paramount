app.controller("baseController",function($scope,cartService){
	// 分页的配置的信息
	$scope.paginationConf = {
		 currentPage: 1, // 当前页数
		 totalItems: 10, // 总记录数
		 itemsPerPage: 10, // 每页显示多少条记录
		 perPageOptions: [10, 20, 30, 40, 50],// 显示多少条下拉列表
		 onChange: function(){ // 当页码、每页显示多少条下拉列表发生变化的时候，自动触发了
			$scope.reloadList();// 重新加载列表
		 }
	}; 
	
	$scope.reloadList = function(){
		// $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
		//$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
		$scope.searchByText($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}



    // 定义方法：获取JSON字符串中的某个key对应值的集合
    	$scope.jsonToString = function(jsonStr,key){
    		// 将字符串转成JSOn:
    		var jsonObj = JSON.parse(jsonStr);

    		var value = "";
    		for(var i=0;i<jsonObj.length;i++){

    			if(i>0){
    				value += ",";
    			}

    			value += jsonObj[i][key];
    		}
    		return value;
    	}

    		// 从集合中查询某个名称的值是否存在
        	$scope.searchObjectByKey = function(list,keyName,keyValue){
        		for(var i=0;i<list.length;i++){
        			if(list[i][keyName] == keyValue){
        				return list[i];
        			}
        		}

        		return null;
        	}


        	$scope.findCartList=function(){
        		cartService.findCartList().success(
        			function(response){
        				$scope.cartList=response.orderItemList;
        				$scope.calculateCartTotalNum($scope.cartList);
        				$scope.calculateCartTotalPrice($scope.cartList);
        			}
        		);
        	}

            $scope.cartTotalItems = 0;
            $scope.cartTotalPrice = 0.00;

        	$scope.calculateCartTotalNum = function(cartList){
        	    if(cartList == undefined || cartList == null)
        	        return;
                for( let i = 0; i < cartList.length; i++){
                    $scope.cartTotalItems += cartList[i].num;
                }
        	}

        	$scope.calculateCartTotalPrice = function(cartList){
        	    if(cartList == undefined || cartList == null)
                    	        return;
        	    for( let i = 0; i < cartList.length; i++){
        	        $scope.cartTotalPrice += cartList[i].price;
        	    }
        	    $scope.cartTotalPrice.toFixed(2);
        	}

        	$scope.saveToCartList = function(itemId, num){
        	    cartService.saveToCartList();
        	}


});
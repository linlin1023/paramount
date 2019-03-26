 //控制层 
app.controller('itemController' , function($scope,$controller   ,itemService){	  //
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemService.findAll().success(
			function(response){

				$scope.list=response;
				$scope.rating($scope.list)
				$scope.randomSixteenProducts();

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

    $scope.randomSixteenProducts = function(){
        $scope.list16 = [];
        var num = $scope.list.length;
        var randomList = []
        for (var ii = 0 ; ii < 16; ii++){
            var temp;
            while(1){
                temp = $scope.getRandomInt(num);
                if(randomList[temp] != 1)
                        break;
            }
            randomList[temp] = 1;
            $scope.list16[ii] = $scope.list[temp]
        }

    }

    $scope.getRandomInt = function(max) {
      return Math.floor(Math.random() * Math.floor(max));
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


    $scope.findNewArrival = function(){
        itemService.findNewArrival().success(
              function(response){
                   $scope.listOfNewArrival=response;
                   $scope.rating($scope.listOfNewArrival);
                   $scope.pairList();
              }

        );


    }

    $scope.range = function(n) {
        return new Array(n);
    }

    $scope.pairList = function(){
        $scope.listOfNewArrivalPair = [];
        var num = $scope.listOfNewArrival.length;
        if(num%2 == 1){
            num = num - 1;
        }
        for(var i = 0; i < num ; i= i+2){
                var paired = {"first": $scope.listOfNewArrival[i], "second" :$scope.listOfNewArrival[i+1]};
                $scope.listOfNewArrivalPair.push(paired);
        }

    }

    $scope.$on('ngRepeatFinished', function( ngRepeatFinishedEvent ) {
        $('.new-product-active').owlCarousel({
        	loop:true,
        	autoplay:false,
        	nav:true,
        	navText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
        	responsive:{
        		0:{
        			items:1
        		},
        		767:{
        			items:2
        		},
        		991:{
        			items:3
        		},
        		1200:{
        			items:4
        		}
        	}
        })
    })
});

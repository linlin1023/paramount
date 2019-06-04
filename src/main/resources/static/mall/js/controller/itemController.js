 //控制层 
app.controller('itemController' , function($scope,$location,$controller,itemService, contentService){	  //
	
	$controller('baseController',{$scope:$scope});//继承

	/*$scope.goToShop = function(){
	     $location.href='shop.html?keywords='+ $scope.keywords;
	}*/

    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemService.findAll().success(
			function(response){

				$scope.list=response;
				$scope.rating($scope.list)
				$scope.randomSixteenProducts();
                $scope.setSpecialProduct($scope.list);
			}			
		);
	}

    $scope.setSpecialProduct = function(list){
        var index = $scope.getRandomInt(list.length);
        $scope.specialProduct = list[index];
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
				$scope.entity.specificationItems = JSON.parse($scope.entity.spec?$scope.entity.spec:"" );
				$scope.rating([$scope.entity])
			}
		);				
	}

	$scope.findOneFromRequestId=function(){
    		var id = $location.search()['id'];
    		$scope.findOne(id)
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
    });

    $scope.$on('ngRepeatFinishedTopseller',function(ngRepeatFinishedTopsellerEvent){
         $('.single-product-items-active').owlCarousel({
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
            		1000:{
            			items:1
            		}
            	}
            })
    });



    $scope.findTopSeller = function(num){
        itemService.findTopSeller(num).success(function(response){
                $scope.topSellerList = response;
                $scope.rating($scope.topSellerList);
                $scope.pairFive($scope.topSellerList);
        });
    }

    $scope.pairFive = function(list){
        $scope.pairOfFiveList = [];
        var num = list.length;
        for(var i = 0; i < num; i  = i + 5){
            $scope.pairOfFiveList.push({"first": list[i], "second": list[i+1], "third": list[i+2], "fourth":list[i+3], "fifth": list[i+4]});
        }
    }



    $scope.findAllAdvertisement = function(){
         contentService.findAll().success(
            function(response){
                $scope.allAdvertisementList = response;
                $scope.populateAdvs();
            }
         );
    }

    $scope.rolling = []; // 9
    $scope.topRightFirst = [];   //10
    $scope.topRightSecond = [];  //11
    $scope.middle = [];  //12
    $scope.bottomLeft = [];  //13
    $scope.bottomRight = [];  //14
    $scope.populateAdvs = function(){
        for(var entity in $scope.allAdvertisementList ){
            var categoryId = $scope.allAdvertisementList[entity].categoryId;
            var advItem = $scope.allAdvertisementList[entity]
            if(categoryId == 9)
                $scope.rolling.push(advItem);
            else if(categoryId == 10)
                $scope.topRightFirst.push(advItem);
            else if(categoryId == 11)
                $scope.topRightSecond.push(advItem);
            else if(categoryId == 12)
                $scope.middle.push(advItem);
            else if(categoryId == 13)
                $scope.bottomLeft.push(advItem);
            else if(categoryId == 14)
                $scope.bottomRight.push(advItem);


        }
    }
});

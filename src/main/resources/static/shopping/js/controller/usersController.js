 //控制层
app.controller('usersController' ,function($scope,$controller,$location ,uploadService ,usersService){

	$controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
	$scope.findAll=function(){
		usersService.findAll().success(
			function(response){
				$scope.list=response;
			}
		);
	}

	//分页
	$scope.findPage=function(page,rows){
		usersService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
	}

	//查询实体
	$scope.findOne=function(id){
        $scope.entity = {headPic:{}};
        $scope.entity.headPic = {};
		usersService.findOne(id).success(
        	function(response){
        	    console.log(response);
        		$scope.entity= response;
        	}
        );
	}


	//保存
	$scope.save=function(){
		// 再添加之前，获得富文本编辑器中的内容。

		var serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=usersService.update( $scope.entity ); //修改
		}else{
			serviceObject=usersService.add( $scope.entity  );//增加
		}
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询
		        	alert(response.message);
		        	location.href="users.html";
				}else{
					alert(response.message);
				}
			}
		);
	}


	//批量删除
	$scope.dele=function(){
		//获取选中的复选框
		usersService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}
			}
		);
	}

	$scope.searchEntity={};//定义搜索对象

	//搜索
	$scope.search=function(page,rows){
		usersService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
	}

	// $scope.entity={goods:{},goodsDesc:{},itemList:[]}

	$scope.uploadFile = function(){
		// 调用uploadService的方法完成文件的上传
		uploadService.uploadFile().success(function(response){
			if(response.flag){
				// 获得url
				$scope.entity.headPic =  response.message;
			}else{
				alert(response.message);
			}
		});
	}

	// 获得了image_entity的实体的数据{"color":"褐色","url":"http://192.168.209.132/group1/M00/00/00/wKjRhFn1bH2AZAatAACXQA462ec665.jpg"}
	//$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
	$scope.entity={};
/*
	$scope.add_image_entity = function(){
		$scope.entity.headPic=$scope.image_entity;
	}*/

	$scope.statusList =
	[
    	{
    		"id":0,
    		"name":"inactive"
    	},
    	{
    		"id":1,
    		"name":"active"
    	}
    ];

    $scope.status = ['inactive','active'];



	// 假设定义一个查询的实体：searchEntity, page当前页码，rows一页有几条纪录
	$scope.search = function(page,rows){
		// 向后台发送请求获取数据:
		usersService.search(page,rows,$scope.searchEntity).success(function(response){
			$scope.paginationConf.totalItems = response.total; //total item number
			$scope.list = response.rows;
		});
	}

});

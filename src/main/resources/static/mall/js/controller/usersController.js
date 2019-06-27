 //控制层
app.controller('usersController' ,function($scope,$controller,$location ,uploadService ,usersService){

	$controller('baseController',{$scope:$scope});//继承



    $('#emailinput').on('input propertychange', function() {
       $scope.emailVerificationCode = null;
       $scope.verificationCodeEmail = null;
    });

    $('#phoneinput').on('input propertychange', function() {
        $scope.phoneVerificationCode = null;
        $scope.verificationCodePhone = null;
    });

    $scope.login = function(obj){
        $(obj).attr("disabled", true);
        //alert("login");
        if($scope.username == null || $scope.username == undefined ){
            $scope.usernameMessage = "Please enter the username or email ";
        }
        if($scope.password == null || $scope.password == undefined ) {
            $scope.passwordMessage = "Please enter the password";
        }
        if($scope.username.includes("@")){
            ///email
            $scope.user.email = $scope.username;

        }else {
            $scope.user.username = $scope.username;
        }
        $scope.user.password = $scope.password;
        usersService.login($scope.user).success(function(response){
                //response is TbUser Object
                if(response != null){
                    sessionStorage.setItem("username", response.username);
                    sessionStorage.setItem("email", response.email);
                    window.location.href = "index.html";
                    return;
                }

         });
    }


	$scope.userRegister = function(){
	    if($scope.emailVerificationCode == "" || $scope.emailVerificationCode == undefined){
            $scope.emailVerificationCodeMessage = "Please input verification code";
            return ;
        }

        if($scope.phoneVerificationCode == "" || $scope.phoneVerificationCode == undefined){
            $scope.phoneVerificationCodeMessage = "Please input verificaiton code";
            return ;
        }
        if($scope.emailVerificationCode != $scope.verificationCodeEmail ){
             if($scope.verificationCodeEmail == null){
                $scope.emailVerificationCodeMessage = "Verification code is expired";
             }else{
                $scope.emailVerificationCodeMessage = "Verification code is wrong";
             }
             return ;
        }
        if($scope.phoneVerificationCode != $scope.verificationCodePhone ){
            if($scope.verificationCodePhone == null){
               $scope.phoneVerificationCodeMessage = "Verification code is expired";
            }else{
               $scope.phoneVerificationCodeMessage = "Verification code is wrong";
            }
             return ;
        }
        if($scope.emailVerificationCode == $scope.verificationCodeEmail &&  $scope.phoneVerificationCode == $scope.verificationCodePhone){
                $scope.verificationCodeEmail= null;
                $scope.verificationCodePhone = null;
                usersService.saveUser($scope.user).success(
                    function(response){
                        if(response.success){
                               $scope.user = {};
                               $scope.emailVerificationCode = null;
                               $scope.phoneVerificationCode = null;
                               window.location.href = "login.html";
                               return true;
                        }
                        else
                            $scope.messageForSave = response.message;
                    }
                );
        }

	}

    $scope.user = {};
    $scope.verificationCodeEmail = null;
    $scope.verificationCodePhone = null;

	$scope.verifyEmail = function(){
	       var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            $scope.emailErrorMessage =  null;
	       if($scope.user.email == null || $scope.user.email == undefined ){
                $scope.emailErrorMessage = "Please input email address";
                return;
	       }else{
	            $scope.emailErrorMessage = null;
	            if(re.test(String($scope.user.email).toLowerCase())){
                    usersService.sendVerificationEmail($scope.user.email).success(
                        function(response){
                            $scope.verificationCodeEmail =response;
                        }
                    );
	            }else {
                    $scope.emailErrorMessage = "This is not a validate email";
                    return;
	            }
	       }
	}

	$scope.verifyPhone = function(){
	     var re = /^(((\+?64\s*[-\.]?[3-9]|\(?0[3-9]\)?)\s*[-\.]?\d{3}\s*[-\.]?\d{4})|((\+?64\s*[-\.\(]?2\d{1}[-\.\)]?|\(?02\d{1}\)?)\s*[-\.]?\d{3}\s*[-\.]?\d{3,5})|((\+?64\s*[-\.]?[-\.\(]?800[-\.\)]?|[-\.\(]?0800[-\.\)]?)\s*[-\.]?\d{3}\s*[-\.]?(\d{2}|\d{5})))$/;
         //var re = /^(\+?64|0)2\d{7,9}$/;
         $scope.phoneErrorMessage = null;
	     if($scope.user.phone == null || $scope.user.phone == undefined ){
              $scope.phoneErrorMessage = "Please input phone number";
              return;
    	 }else{
    	      $scope.phoneErrorMessage = null;
    	      if(re.test(String($scope.user.phone))){
                  usersService.sendVerificationPhone($scope.user.phone).success(
                        function(response){
                             $scope.verificationCodePhone =response;
                        }
                  );
    	      }else {
                  $scope.phoneErrorMessage = "This is not a validate phone number";
                  return;
    	      }
    	 }

	}

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

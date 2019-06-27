//服务层
app.service('usersService',function($http){

    this.login = function(user){
        return $http.post("../../register/login", user);
    }

    this.sendVerificationEmail = function(email){
        //alert(email);
        return $http.get("../../register/verificationCodeEmail?email="+email);
    }

    this.sendVerificationPhone = function(phone){
        return $http.post("../../register/verificationCodePhone", phone);
    }

    this.saveUser = function(user){
        return $http.post("../../register/saveUser", user );
    }

	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../../users/findAll');
	}
	//分页
	this.findPage=function(page,rows){
		return $http.get('../../shopping/tbuser/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../../shopping/tbuser/findOne?id='+id);
	}
	//增加
	this.add=function(entity){
		return  $http.post('../../shopping/tbuser/add',entity );
	}
	//修改
	this.update=function(entity){
		return  $http.post('../../shopping/tbuser/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../../shopping/tbuser/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){//shopping/tbuser/search
		return $http.post('../../shopping/tbuser/search?page='+page+"&rows="+rows, searchEntity);
	}
});

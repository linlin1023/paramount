//服务层
app.service('itemService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../shopping/item/findAll');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../shopping/item/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../shopping/item/findOne?id='+id);
	}

	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../shopping/item/search?page='+page+"&rows="+rows, searchEntity);
	}

		//搜索
    	this.searchByText=function(page,rows,keywords){
    		return $http.post('../shopping/item/searchByText?page='+page+"&rows="+rows, keywords);
    	}

	this.findNewArrival=function(){
	    return $http.get('../shopping/item/findNew');
	}
	this.findTopSeller = function(num){
	    return $http.get('../shopping/item/findTopSeller?num='+num);
	}
});

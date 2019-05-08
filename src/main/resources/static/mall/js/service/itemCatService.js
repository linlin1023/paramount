//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../shopping/itemCat/findAll');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../shopping/itemCat/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../shopping/itemCat/findOne?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../shopping/itemCat/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../shopping/itemCat/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../shopping/itemCat/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../shopping/itemCat/search?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findByParentId = function(parentId){
		return $http.get("../shopping/itemCat/findByParentId?parentId="+parentId);
	}
});

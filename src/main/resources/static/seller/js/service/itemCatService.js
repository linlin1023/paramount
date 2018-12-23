//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../../shopping/itemCat/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../../shopping/itemCat/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../../shopping/itemCat/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../../shopping/itemCat/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../../shopping/itemCat/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../../shopping/itemCat/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../../shopping/itemCat/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findByParentId = function(parentId){
		return $http.get("../../shopping/itemCat/findByParentId.do?parentId="+parentId);
	}
});

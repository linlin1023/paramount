//服务层
app.service('goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../../shopping/goods/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../../shopping/goods/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../../shopping/goods/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../../shopping/goods/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../../shopping/goods/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../../shopping/goods/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../../shopping/goods/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});

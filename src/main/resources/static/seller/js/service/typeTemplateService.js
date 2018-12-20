//服务层
app.service('typeTemplateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../../shopping/typeTemplate/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../../shopping/typeTemplate/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../../shopping/typeTemplate/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../../shopping/typeTemplate/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../../shopping/typeTemplate/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../../shopping/typeTemplate/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../../shopping/typeTemplate/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findBySpecList=function(id){
		return $http.get('../../shopping/typeTemplate/findBySpecList.do?id='+id);
	}

	this.selectTypeTemplateOptions = function(){
	    return $http.get('../../shopping/typeTemplate/selectTypeTemplateOptions.do');
	}
});

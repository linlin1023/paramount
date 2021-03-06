//服务层
app.service('typeTemplateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../typeTemplate/findAll');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../typeTemplate/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
	    if(id == undefined) return;
		return $http.get('../typeTemplate/findOne?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../typeTemplate/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../typeTemplate/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../typeTemplate/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../typeTemplate/search?page='+page+"&rows="+rows, searchEntity);
	}
	this.findBySpecList=function(id){
	    if(id == undefined) return;
    	return $http.get('../../shopping/typeTemplate/findBySpecList?id='+id);
    }
    this.selectTypeTemplateOptions = function(){
   	    return $http.get('../../shopping/typeTemplate/selectTypeTemplateOptions');
   	}
});

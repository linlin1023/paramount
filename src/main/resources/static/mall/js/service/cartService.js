//服务层
app.service('cartService',function($http){

	this.findCartList=function(){
		return $http.get('../shopping/cart/findCartList');
	}

	this.saveToCartList = function(itemId, num){
	    return $http.post('../shopping/cart/addGoodsToCartList?itemId=' + itemId + '&num=' + num); //Long itemId,  Integer num
	}

});

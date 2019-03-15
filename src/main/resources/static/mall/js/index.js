$(function(){
    // 执行代码
    findCategoriesByParentId(0);
    findFeatureProduct(4);
});


var singleFeatureProduct='';


var findFeatureProduct = function(num){
    var $featureProductContainer = $("#feature-product-container-llp");
    var content ="";
    for(var ir = 0; ir < 4; ir++)
        content += singleFeatureProduct;

}

var findCategoriesByParentId=function(parentId){
    $.ajax({
            url:"../shopping/itemCat/findByParentId",
            type:"get",
            data: "parentId=" + parentId,
            success:function(result){

                var options = '<option value="" id="allcategory">All categories</option>';
                var categoriesList = '';
                for(var i in result){
                        options += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
                        var number = parseInt(i) + 1;
                        categoriesList += '<li><a href="#"><img src="img/menu-l/'+ number +'.png" alt="" /> '+result[i].name+'</a></li>'
                }
                $("#select").html(options);
                $("#categoriesSiderBar").html(categoriesList);
           }});


}

var getTopSeller=function(num){
          $.ajax({
                 url:"../shopping/item/findTopSeller",
                 type:"get",
                 data:"num="+ num,
                 success:function(result){

                         var $container = $("#scoll-product-container-llp")
                         var $option = $("#scoll-product-llp");
                         var options = "";
                         $container.html("");
                         for(var i in result){
                               $option.find("#scoll-img-llp").first().attr("src",result[i].image);
                               $option.find("#scoll-name-llp").first().html('<h4><a href="product-details.html">'+ result[i].title+'</a></h4>');
                               var rates = "";
                               for(var ra = 1; ra <= parseInt(result[i].cartThumbnail);ra++){
                                       rates += '<a href="#"><i class="fa fa-star"></i></a>';
                               }
                               for(var ra = 1; ra <= (5 - parseInt(result[i].cartThumbnail));ra++){
                                       rates += '<a href="#"><i class="fa fa-star-o"></i></a>';
                               }
                               $option.find("#scoll-rate-llp").first().html(rates);

                               $option.find("#scoll-price-llp").first().html("$" + result[i].price);
                               alert($option.prop("outerHTML"))
                               options += $option.prop("outerHTML");
                         }
                        $container.html(options);
                 }

          });



}
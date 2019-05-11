app.service("searchService", function($http){
    this.search = function(searchMap){
        return  $http.post("../shopping/itemsearch/search", searchMap );
    }
});
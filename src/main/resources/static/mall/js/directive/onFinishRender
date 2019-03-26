app.directive('onFinishRender', function($timeout){
    return {
        restrict:'A',
        link: function(scope, element, attr){   //link 中当scope中的$last也就是最后一条数据加载完毕后出发"ngRepeatFinished"
            if(scope.$last === true){
                $timeout(
                    function(){
                        scope.$emit('ngRepeatFinished'); //这个相当于trigger,  会触发定义的时间监听
                    }
                );
            }
        }

    }

});
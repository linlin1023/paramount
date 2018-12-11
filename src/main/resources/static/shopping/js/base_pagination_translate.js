// 定义模块:
var app = angular.module("pinyougou",['pagination','pascalprecht.translate'])
    .config(['$translateProvider',function($translateProvider){
        var lang = window.localStorage.lang || 'en';
        $translateProvider.preferredLanguage(lang);
        $translateProvider.useStaticFilesLoader({
            prefix: '/js/i18n/',
            suffix: '.json'
        });
    }]);
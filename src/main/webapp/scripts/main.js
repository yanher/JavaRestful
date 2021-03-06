var angularAPP = angular.module('angularAPP', ['ui.router', 'ui.grid', 'ngStorage', 'ui.bootstrap','UserServiceModule','HostServiceModule']);

angularAPP.run(['$rootScope', '$state', '$stateParams', '$localStorage', function($rootScope, $state, $stateParams, $localStorage) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$localStorage = $localStorage;
    $rootScope.showLoader = {
        show: false
    };
    $state.go('login');
}]);

angularAPP.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $stateProvider.
    state('login', {
        views: {
            'login': {
                templateUrl: 'ngviews/login_new.html',
                controller: ['$rootScope', '$scope', '$state', '$localStorage', 'UserService', function($rootScope, $scope, $state, $localStorage, UserService) {
                    if ($rootScope.user) {
                        $state.go('main');
                    };
                    $scope.login = function() {
                        UserService.login($scope.userName, $scope.password).success(function(data, status) {
                            var user = data[0];
                            if (typeof(user)!="undefined") {
                                $rootScope.user = {
                                    name: user.name,
                                    pwd: user.pwd
                                    /*canRecall: user.permission.search(',recall,') > -1,
                                    canUpload: user.permission.search(',upload,') > -1,
                                    canCredit: user.permission.search(',credit,') > -1*/
                                };
                                $localStorage.user = $rootScope.user;
                                $state.go('main');
                            } else {
                                alert('User name or password is incorrect.');
                            };
                        }).error(function(data, status) {
                            alert('Failed to login.'); //hardcode
                        });
                    }
                }]
            }
        }
    }).
    state('main', {
        views: {
        	'topbar': {
                templateUrl: 'ngviews/topbar.html',
                controller: ''
            },
            'sidebar': {
                templateUrl: 'ngviews/sidebar.html',
                controller: ''
            },
            'main': {
                templateUrl: 'ngviews/mainbody.html',
                controller: ''
            }
        }
    })
}]);

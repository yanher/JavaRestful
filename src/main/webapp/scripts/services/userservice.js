angular.module('UserServiceModule', []).factory('UserService', ['$http', 'Host', function($http, Host) {
    var userPath = Host.userPathNew();
    var loginGet = function(name, pwd) {
        return $http({
            method: 'get',
            url: userPath + 'loginGet?name='+name+'&pwd='+pwd //hardcode
        });
    };
    var loginPost = function(name, pwd) {
        return $http({
            method: 'post',
            url: userPath + 'loginPost', //hardcode
         //   headers: {'Content-type': 'application/json;charset=UTF-8'},
         //   headers:{'Content-type': 'application/x-www-form-urlencoded'},           
            headers: {'Content-type': 'application/json;charset=UTF-8'},
            data: {
                name: name,
                pwd: pwd
            }
        });
    };
    var changePwd = function(id, pwd) {
        return $http({
            method: 'post',
            url: userPath + 'ChangePwd', //hardcode
            data: {
                id: id,
                password: pwd
            }
        });
    };
    
    var login = function(name, pwd) {
        return $http({
            method: 'post',
            url: userPath , //hardcode
            headers: {'Content-type': 'application/json;charset=UTF-8'},
            data: {
                name: name,
                pwd: pwd
            }
        });
    };
    
    return {
        loginGet: function(name, pwd) {
            return loginGet(name, pwd);
        },
        loginPost: function(name, pwd) {
            return loginPost(name, pwd);
        },
        changePwd: function(id, pwd) {
            return changePwd(id, pwd);
        },
        login: function(name, pwd) {
            return login(name, pwd);
        }
    };  
}]);

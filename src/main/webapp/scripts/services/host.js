angular.module('HostServiceModule', []).factory('Host', function() {
    var path = function() {
        return 'http://localhost:8090/JavaRestful/api/an/';
    };
    var userPath = function() {
        return 'http://localhost:8090/JavaRestful/api/an/';
    };
    var invoicePath = function() {
        return 'http://localhost:51014/Accounting/Invoice.asmx/';
    };
    return {
        path: function() {
            return path();
        },
        userPath: function() {
            return userPath();
        },
        invoicePath: function() {
            return invoicePath();
        }
    }
});

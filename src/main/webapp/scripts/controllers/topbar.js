angular.module('TopbarControllerModule', []).controller('TopbarController', ['$rootScope', '$scope', '$timeout', '$localStorage', '$state', 'UserService', 'InvoiceService', 'Utility', function($rootScope, $scope, $timeout, $localStorage, $state, UserService, InvoiceService, Utility) {
    $scope.showState = {
        saved: false,
        fileOK: true,
        uploaded: false
    };
    $scope.uploadType = '';
    $scope.setUploadType = function(type) {
        $scope.uploadType = type;
    };
    $scope.logoff = function() {
        delete $rootScope.user;
        $state.go('login');
    };
    $scope.changePwd = function() {
        UserService.changePwd($rootScope.user.id, $scope.password).success(function(data, status) {
            alert('Succeeded.');
            $('#changePwd').modal('hide');
        }).error(function(data, status) {});
    };
    $scope.updateData = function() {
        delete $rootScope.allVATContracts;
        $state.go('main.vat', {}, {
            reload: true
        });
    };
    //the contracts that can be used to clean accounts
    $scope.availabeChargedContracts = [];
    $scope.analyzeFile = function(data) {
        $scope.unequalRecords = [];
        var uploadData = angular.fromJson(data.uploadData);
        $scope.showState.uploaded = true;
        if ($scope.uploadType == 'VAT') {

        } else if ($scope.uploadType == 'BT') {

        } else {
            alert('Upload type is not set.');
        };
        for (var i = 0; i < uploadData.length; i++) {
            var c = uploadData[i];
            $scope.availabeChargedContracts.push(c);
        };

        $scope.unequalRecords = $scope.availabeChargedContracts.filter(function(v) {
            return v.price_s1 !== v.price_s2;
        });

        $scope.availableTotal = $scope.availabeChargedContracts.map(function(v) {
            return v.invoice_amt;
        }).reduce(function(pv, cv) {
            return (pv || 0) + (cv || 0);
        }, 0);
        $scope.showState.fileOK = true;
    };
    $scope.cancelUpload = function() {
        $timeout(function() {
            $scope.unequalRecords = [];
            $scope.availabeChargedContracts = [];
            $scope.showState.fileOK = true;
            $scope.showState.uploaded = false;
            $('.progress-bar.ng-binding').css({
                'width': '0px',
                'overflow': 'hidden'
            });
            if ($scope.showState.saved) {
                $scope.showState.saved = false;
                $scope.updateData();
            };
        }, 500);
    };
    $scope.saveContracts = function() {
        $rootScope.showLoader.show = true;
        InvoiceService.saveContracts($scope.availabeChargedContracts, $scope.uploadType).success(function(data, status) {
            $rootScope.showLoader.show = false;
            var result = angular.fromJson(data.d);
            if (result.ok) {
                $scope.showState.saved = true;
            } else {
                alert('Failed to save data.');
            };
        }).error(function(data, status) {
            alert('Failed to transfer data.');
        });
    };
    $scope.badFileHandler = function() {
        $scope.showState.fileOK = false;
    };
}]);

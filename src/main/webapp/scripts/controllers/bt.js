angular.module('BTControllerModule', []).controller('BTController', ['$rootScope', '$scope', '$timeout', '$localStorage', '$state', 'UserService', 'InvoiceService', 'Utility', 'uiGridConstants', function($rootScope, $scope, $timeout, $localStorage, $state, UserService, InvoiceService, Utility, uiGridConstants) {
    $('[data-toggle="popover"]').popover({
        html: true,
        container: 'body'
    }).on('hidden.bs.popover', function() {
        //need '$timeout' here, otherwise it won't work
        $timeout(function() {

        }, 100);
    });
    //since 'ng-click' not work for popover here, use traditional jquery way.
    $('body').off('click', '#recallInvoice').on('click', '#recallInvoice', function() {
        $scope.recallInvoice();
    });
    $scope.gridOptions = {
        showGridFooter: true,
        showColumnFooter: true,
        enableFiltering: true,
        columnDefs: [{
            field: 'CONTRACT_NUMBER',
            displayName: 'Contract',
            enableHiding: false,
            width: '14%'
        }, {
            field: 'INVOICE_ORDER',
            displayName: 'Order',
            width: '6%',
            enableHiding: false
        }, {
            field: 'CUSTOMER',
            displayName: 'Customer',
            width: '9%',
            enableHiding: false
        }, {
            field: 'DEALER',
            //width: '12%',
            displayName: 'Dealer',
            enableHiding: false
        }, {
            field: 'PRODUCT',
            width: '9%',
            displayName: 'Product',
            enableHiding: false
        }, {
            field: 'INVOICE_NUMBER',
            width: '12%',
            displayName: 'Invoice',
            enableHiding: false
        }, {
            field: 'INVOICE_AMT',
            aggregationType: uiGridConstants.aggregationTypes.sum,
            width: '11%',
            displayName: 'Amount',
            sortingAlgorithm: Utility.sortingRule,
            cellTemplate: '<div class="ui-grid-cell-contents text-right">{{COL_FIELD | number:2}}</div>',
            enableHiding: false
        }, {
            field: 'INVOICE_DATE',
            displayName: 'Date',
            enableHiding: false,
            cellTemplate: '<div class="ui-grid-cell-contents">{{COL_FIELD | date:"yyyy-MM-dd"}}</div>',
            width: '9%'
        }, {
            displayName: 'Remark',
            field: 'REMARK',
            //width: '8%',
            enableHiding: false
        }, {
            displayName: 'Detail',
            field: 'ID',
            width: '8%',
            cellTemplate: '<div class="ui-grid-cell-contents text-center"><span><a href="#" data-toggle="modal" data-target="#detailVAT" ng-click="grid.appScope.detailVAT(COL_FIELD)">Detail</a></span></div>',
            enableSorting: false,
            enableFiltering: false,
            enableHiding: false
        }],
        data: $rootScope.allBTContracts && $rootScope.allBTContracts.filter(function(val) {
            return val.OBJECT_STATUS === 10;
        }) || [],
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            $scope.gridApi.core.on.filterChanged($scope, function() {
                var grid = this.grid;
                if (grid.columns[0].filters[0].term) {
                    $scope.gridOptions.useExternalFiltering = true;
                    var cs = grid.columns[0].filters[0].term;
                    $scope.gridOptions.data = $rootScope.allBTContracts.filter(function(val) {
                        return cs.indexOf(val.CONTRACT_NUMBER) > -1 || val.CONTRACT_NUMBER.indexOf(cs) > -1;
                    });
                } else {
                    $scope.gridOptions.useExternalFiltering = false;
                    $scope.gridOptions.data = $rootScope.allBTContracts && $rootScope.allBTContracts.filter(function(val) {
                        return val.OBJECT_STATUS === 10;
                    }) || [];
                }
            });
        }
    };

    $scope.detailVAT = function(id) {
        $scope.theContract = $rootScope.allBTContracts.filter(function(val) {
            return val.ID === id;
        })[0];
    };

    $scope.recallInvoice = function() {
        $rootScope.showLoader.show = true;
        InvoiceService.recallInvoice($scope.theContract.FROM_FILE_ID, $scope.theContract.ROW_ID, $rootScope.user.id, $rootScope.user.name).success(function(data, status) {
            $scope.theContract.OBJECT_STATUS = 20;
            $rootScope.showLoader.show = false;
            $('#detailVAT').modal('hide');
            $timeout(function() {
                $scope.gridOptions.data = $rootScope.allBTContracts.filter(function(val) {
                    return val.OBJECT_STATUS === 10;
                })
            }, 100);
        }).error(function(data, status) {
            alert('error');
        });
    };
}]);

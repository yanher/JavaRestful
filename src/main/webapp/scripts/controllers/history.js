angular.module('HistoryControllerModule', []).controller('HistoryController', ['$rootScope', '$scope', '$timeout', '$localStorage', '$state', 'UserService', 'InvoiceService', 'Utility', function($rootScope, $scope, $timeout, $localStorage, $state, UserService, InvoiceService, Utility) {
    $('[data-toggle="popover"]').popover({
        html: true,
        container: 'body'
    }).on('hidden.bs.popover', function() {
        //need '$timeout' here, otherwise it won't work
        $timeout(function() {

        }, 100);
    });
    //since 'ng-click' not work for popover here, use traditional jquery way.
    $('body').off('click', '#deleteFile').on('click', '#deleteFile', function() {
        $scope.deleteFile();
    });
    $scope.gridOptions = {
        enableFiltering: true,
        columnDefs: [{
            field: 'UPLOAD_DATE',
            displayName: 'Upload Time',
            enableHiding: false,
            cellTemplate: '<div class="ui-grid-cell-contents">{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</div>'
                //width: '8%'
        }, {
            field: 'ORIGINAL_NAME',
            displayName: 'File Name',
            enableHiding: false
                //width: '7%'
        }, {
            field: 'TYPE',
            displayName: 'Type',
            enableHiding: false,
            width: '10%'
        }, {
            field: 'ACTIVE',
            displayName: 'Active',
            cellTemplate: '<div class="ui-grid-cell-contents text-right">{{COL_FIELD}}</div>',
            enableHiding: false,
            width: '10%'
        }, {
            field: 'RECALLED',
            displayName: 'Recalled',
            cellTemplate: '<div class="ui-grid-cell-contents text-right">{{COL_FIELD}}</div>',
            enableHiding: false,
            width: '10%'
        }, {
            field: 'USER_NAME',
            displayName: 'Upload User',
            enableHiding: false
                //width: '19%'
        }, {
            name: 'DETAIL',
            field: 'ID',
            //width: '12%',
            displayName: 'Detail',
            enableSorting: false,
            enableFiltering: false,
            enableHiding: false,
            cellTemplate: '<div class="ui-grid-cell-contents text-center"><span><a href="#" data-toggle="modal" data-target="#detailFile" ng-click="grid.appScope.detailFile(COL_FIELD, row)">Detail</a></span></div>'
        }],
        data: [],
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
        },
        rowTemplate: '<div ng-click="grid.appScope.clickRow(grid, row)" ng-class="{ \'bg-warning\': true }">' +
            '<div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
            '</div>'
    };
    $scope.gridOptionsRecall = {
        enableFiltering: true,
        columnDefs: [{
            name: 'RECALLDATE',
            field: 'RECALL_DATE',
            enableHiding: false,
            displayName: 'Recall Time',
            cellTemplate: '<div class="ui-grid-cell-contents">{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</div>',
        }, {
            name: 'CONTRACTNUMBER',
            field: 'CONTRACT_NUMBER',
            displayName: 'Contract',
            enableHiding: false,
        }, {
            name: 'INVOICEAMT',
            field: 'INVOICE_AMT',
            displayName: 'Invoice Amt',
            sortingAlgorithm: Utility.sortingRule,
            cellTemplate: '<div class="ui-grid-cell-contents text-right">{{COL_FIELD | number:2}}</div>',
            enableHiding: false,
        }, {
            name: 'USERNAME',
            field: 'USER_NAME',
            displayName: 'Recall User',
            enableHiding: false,
        }, {
            name: 'DETAIL',
            field: 'ID',
            displayName: 'Detail',
            enableSorting: false,
            enableFiltering: false,
            enableHiding: false,
            cellTemplate: '<div class="ui-grid-cell-contents text-center"><span><a href="#" data-toggle="modal" data-target="#detailRecall" ng-click="grid.appScope.detailRecall(row)">Detail</a></span></div>'
        }],
        data: [],
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
        }
    };
    $rootScope.showLoader.show = true;
    $scope.getHistory = function() {
        InvoiceService.getHistory().success(function(data, status) {
            $rootScope.showLoader.show = false;
            var fs = angular.fromJson(data.d).files;
            $scope.recalls = angular.fromJson(data.d).recalls;
            $scope.gridOptions = {
                data: fs
            };
            $scope.gridOptionsRecall = {
                data: $scope.recalls
            };
        }).error(function(data, status) {});
    };


    $scope.detailFile = function(fileId, row) {
        $scope.availabeChargedContracts = [];

        $scope.uploader = row.entity.USER_NAME;
        $scope.uploadTime = row.entity.UPLOAD_DATE;
        $scope.deleted = row.entity.ACTIVE === 0;
        $scope.deleteTime = row.entity.RECALL_DATE;
        $scope.deleteUser = row.entity.RECALL_USER_NAME;
        $rootScope.showLoader.show = true;

        InvoiceService.analyzeFile(fileId).success(function(data, status) {
            $rootScope.showLoader.show = false;
            $scope.uploadFileID = fileId;
            $scope.availabeChargedContracts = angular.fromJson(data.d);
            $scope.unequalRecords = $scope.availabeChargedContracts.filter(function(v) {
                return v.price_s1 !== v.price_s2;
            });
            $scope.availableTotal = $scope.availabeChargedContracts.map(function(v) {
                return v.invoice_amt;
            }).reduce(function(pv, cv) {
                return (pv || 0) + (cv || 0);
            }, 0);
        }).error(function(data, status) {})
    };

    $scope.detailRecall = function(row) {
        $scope.theContract = row.entity;
        $scope.deptailPaymentsByContract = $scope.recalls.filter(function(val) {
            return val.ID == row.entity.ID;
        });
    };

    $scope.deleteFile = function() {
        $rootScope.showLoader.show = true;
        InvoiceService.deleteFile($scope.uploadFileID, $rootScope.user.id, $rootScope.user.name).success(function(data, status) {
            $rootScope.showLoader.show = false;
            $('#detailFile').modal('hide');
            $rootScope.allVATContracts.forEach(function(ele, ind) {
                if (ele.FROM_FILE_ID === $scope.uploadFileID) {
                    ele.OBJECT_STATUS = 20;
                };
            });
            $rootScope.allBTContracts.forEach(function(ele, ind) {
                if (ele.FROM_FILE_ID === $scope.uploadFileID) {
                    ele.OBJECT_STATUS = 20;
                };
            });
            $scope.getHistory();
        }).error(function(data, status) {})
    };
    $scope.getHistory();
}]);

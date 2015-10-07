angular.module('InvoiceServiceModule', []).factory('InvoiceService', ['$http', 'Host', function($http, Host) {
    var GetDBName = function() {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'GetDBName',
            data: {
                postMark: 'this is used to force return json format'
            }
        });
    };

    var AnalyzeFile = function(id) {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'AnaylizeFile',
            data: {
                id: id
            }
        });
    };

    var SaveContracts = function(uploadData, uploadType) {
        var m = '';
        if (uploadType == 'VAT') {
            m = 'SaveVATContracts';
        } else if (uploadType == 'BT') {
            m = 'SaveBTContracts';
        };
        return $http({
            method: 'post',
            url: Host.invoicePath() + m,
            data: {
                uploadData: uploadData
            }
        });
    };

    var GetAllContracts = function() {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'GetAllContracts',
            data: {
                postMark: 'this is used to force return json format'
            }
        });
    };

    var GetHistory = function() {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'GetHistory',
            data: {
                postMark: 'this is used to force return json format'
            }
        });
    };

    var RecallInvoice = function(fileId, rowId, userId, userName) {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'RecallInvoice',
            data: {
                fileId: fileId,
                rowId: rowId,
                userId: userId,
                userName: userName
            }
        });
    };

    var DeleteFile = function(id, userId, userName) {
        return $http({
            method: 'post',
            url: Host.invoicePath() + 'DeleteFile',
            data: {
                id: id,
                userId: userId,
                userName: userName
            }
        });
    };

    return {
        getDBName: function() {
            return GetDBName();
        },
        analyzeFile: function(id) {
            return AnalyzeFile(id);
        },
        saveContracts: function(uploadData, uploadType) {
            return SaveContracts(uploadData, uploadType);
        },
        getAllContracts: function() {
            return GetAllContracts();
        },
        getHistory: function() {
            return GetHistory();
        },
        recallInvoice: function(fileId, rowId, userId, userName) {
            return RecallInvoice(fileId, rowId, userId, userName);
        },
        deleteFile: function(id, userId, userName) {
            return DeleteFile(id, userId, userName);
        }
    };
}]);

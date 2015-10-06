var directiveModule = angular.module('DirectiveModule', ['ngFileUpload']);
directiveModule.directive('uploader', ['Upload', 'Host', '$localStorage', function(Upload, Host, $localStorage) {
    return {
        restrict: 'AE',
        transclude: true,
        template: '<div class="progress">' + '<div class="progress-bar" role="progressbar" aria-valuenow="{{valueNow}}" aria-valuemin="0" aria-valuemax="100" style="width: {{valueNow}}%;" ng-bind="fileName"></div>' + '</div>' + '<button class="btn btn-default" ngf-select accept="{{fileType}}" ngf-change="upload($files)"><div ng-transclude></div></button>',
        scope: {
            fileid: '=',
            callback: '=',
            handleerror: '='
        },
        link: function(scope, element, attrs) {
            scope.fileType = attrs.filetype;
            scope.upload = function(files) {
                if (files && files.length) {
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        scope.valueNow = 0;
                        Upload.upload({
                            url: Host.path() + 'UploadHandler.ashx',
                            method: 'POST',
                            params: {
                                'userID': $localStorage.user.id,
                                'userName': $localStorage.user.name
                            },
                            file: file
                        }).progress(function(evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            scope.valueNow = progressPercentage;
                            scope.fileName = evt.config.file.name;
                            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                        }).success(function(data, status, headers, config) {
                            scope.fileid = data.fileID;
                            if (scope.callback) {
                                scope.callback(data)
                            };
                            console.log('file ' + config.file.name + ' uploaded. Response: ' + data);
                        }).error(function(data, status) {
                            if (scope.handleerror) {
                                scope.handleerror(data, status);
                            };
                        });
                    }
                }
            };
        }
    }
}]);
directiveModule.directive('downloader', ['$http', 'Host', function($http, Host) {
    return {
        restrict: 'AE',
        scope: {},
        transclude: true,
        template: '<a href=""><span ng-transclude></span></a>',
        link: function(scope, element, attrs) {
            element.bind('click', function(event) {
                if (attrs.fileid) {
                    var url = Host.path() + 'DownloadHandler.ashx?id=' + attrs.fileid + '&type=' + attrs.filetype + '&iscredit=' + attrs.iscredit;
                    window.open(url, '_blank', '');
                } else {
                    console.log('File id need to be given for downloading.')
                };
            });
        }
    }
}]);

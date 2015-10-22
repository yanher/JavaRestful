<!DOCTYPE html>
<html lang="en" ng-app="angularAPP">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>angular</title>
    <link href="styles/bootstrap-3.3.5/css/bootstrap.css" rel="stylesheet" />
    <link href="styles/angular-ui-grid/angular-ui-grid/angular-ui-grid.css" rel="stylesheet" />
    <link href="styles/main.css" rel="stylesheet" />
    <link href="styles/login.css" rel='stylesheet' type='text/css' />
</head>

<body>
    <div class="login"><div ui-view="login"></div></div>  
    <div ui-view="main"></div>
    <!-- <div id="mydiv" ng-show="showLoader.show">
        <img src="images/ajax-loader.gif" class="ajax-loader" />
    </div> -->

    <script src="scripts/vendor/jquery/jquery-1.11.2.js"></script>
    <script src="styles/bootstrap-3.3.5/js/bootstrap.js"></script>
    <script src="scripts/vendor/angular/angular.js"></script>
    <script src="scripts/vendor/angular/angular-ui-router.js"></script>
    <script src="scripts/vendor/angular/angular-ui-grid.js"></script>
    <script src="scripts/vendor/angular/ng-storage.js"></script>
    <script src="scripts/vendor/angular/ng-file-upload.js"></script>
    <script src="scripts/vendor/angular/ui-bootstrap-tpls-0.13.0.min.js"></script>
    <script src="scripts/services/userservice.js"></script>
    <script src="scripts/services/host.js"></script>
    <script src="scripts/main.js"></script>

</body>

</html>
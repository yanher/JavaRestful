<!DOCTYPE html>
<html lang="en" ng-app="angularAPP">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>YHG SYSTEM</title>
    <link href="styles/bootstrap-3.3.5/css/bootstrap.css" rel="stylesheet" />
    <link href="styles/angular-ui-grid/angular-ui-grid/angular-ui-grid.css" rel="stylesheet" />
    <link href="styles/main.css" rel="stylesheet" />  
        <link href="styles/mainbody.css" rel="stylesheet" />
<link href="styles/Metro-UI/css/metro.css" rel="stylesheet">
<link href="styles/Metro-UI/css/metro-icons.css" rel="stylesheet">
<link href="styles/Metro-UI/css/metro-responsive.css" rel="stylesheet">
<link href="styles/Metro-UI/css/metro-schemes.css" rel="stylesheet">  
    <link rel="icon" sizes="16x16" href="images/volvo_tag.png" type="image/png" />
</head>

<body>
    <div class="login"><div ui-view="login"></div></div>  
    <div class="container-fluid">
       <div class="row">
          <div class="col-lg-12" ui-view="topbar"></div>
       </div>   
       <div class="row">
          <div class="col-lg-2" ui-view="sidebar"></div>
          <div class="col-lg-10" ui-view="main"></div>
       </div>
    </div>
    <!-- <div class="bottom"><div ui-view="statusbar"></div></div> -->
    <script src="scripts/vendor/jquery/jquery-1.11.2.js"></script>
    <script src="scripts/datatable/js/jquery.dataTables.js"></script>
<script src="styles/Metro-UI/js/metro.js"></script>
<script src="styles/Metro-UI/js/prettify/run_prettify.js"></script>
<script src="styles/Metro-UI/js/ga.js"></script>
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
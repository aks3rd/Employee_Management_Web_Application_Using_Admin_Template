<!doctype html>
<html>
<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>DatabaseInstallation</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="./css/bootstrap/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/styles.css">

<script src='/stylefour/js/ajaxsix.js'></script>
<style>
.databaseInstallationSection
{
display:block;
padding:10px;
margin:5px;
width:600px;
background-color: rgba(255,0,0,0.1);
align-items: center;
margin:auto;
}
</style>
<script>
function DatabaseInstallation()
{
this.databaseName="";
this.serverName="";
this.portNumber=0;
this.dbUserId="";
this.dbPassword="";
this.adUserId="";
this.adPassword="";
}
function server()
{
if(validate())
{
 var databaseName=$$$("#databaseName").val();
 var serverName=$$$("#serverName").val();
 var portNumber=$$$("#portNumber").val();
 var dbUserId=$$$("#dbUserId").val();
 var dbPassword=$$$("#dbPassword").val();
 var adUserId=$$$("#adUserId").val();
 var adPassword=$$$("#adPassword").val();
 var data=new DatabaseInstallation();
 data.databaseName=databaseName;
 data.serverName=serverName;
 data.portNumber=portNumber;
 data.dbUserId=dbUserId;
 data.dbPassword=dbPassword;
 data.adUserId=adUserId;
 data.adPassword=adPassword;
 console.log(data);
 //alert("Value of num1 : "+num1+",Value of num2 : "+num2);
 $$$.postJSON({

  "url":"/hr/install",
  "success":function(responseJSON){
    //alert("Success chala");
    window.location.replace("login.jsp");
    //In this section we are forward the request to index.jsp
  },
  "queryParameter":data,
  "exception":function(exception){
    $$$("#installationErrorSection").html(exception);  
  },
  "faild":function(){
   alert("Unable to process request");
  }
 });

}
else
{

}
}
function validate()
{
 var isValid=true;
 document.getElementById('installationErrorSection').innerHTML='';
 document.getElementById('databaseNameErrorSection').innerHTML='';
 document.getElementById('serverNameErrorSection').innerHTML='';
 document.getElementById('portNumberErrorSection').innerHTML='';
 document.getElementById('dbUserIdErrorSection').innerHTML='';
 document.getElementById('dbPasswordErrorSection').innerHTML='';
 document.getElementById('adUserIdErrorSection').innerHTML='';
 document.getElementById('adPasswordErrorSection').innerHTML='';
 document.getElementById('adRePasswordErrorSection').innerHTML='';

 var myform=document.getElementById('installationForm');
 var databaseName=myform.databaseName.value;
 var serverName=myform.serverName.value;
 var portNumber=myform.portNumber.value;
 var dbUserId=myform.dbUserId.value;
 var dbPassword=myform.dbPassword.value;
 var adUserId=myform.adUserId.value;
 var adPassword=myform.adPassword.value;
 var adRePassword=myform.adRePassword.value;

 var number='0123456789';
 var valChar='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ';
 if(databaseName.length==0)
 {
  isValid=false;
  document.getElementById('databaseNameErrorSection').innerHTML='Database Name Required';
 }
 for(var i=0;i<databaseName.length;i++)
 {
  if((valChar+number).indexOf(databaseName.charAt(i))==-1)
  {
   isValid=false;
   document.getElementById('databaseNameErrorSection').innerHTML='Invalid Database Name';
   break;
  }
 }
 if(serverName.length==0)
 {
  isValid=false;
  document.getElementById('serverNameErrorSection').innerHTML='ServerName Required';
 }
 for(var i=0;i<serverName.length;i++)
 {
  if(valChar.indexOf(serverName.charAt(i))==-1)
  {
   isValid=false;
   document.getElementById('serverNameErrorSection').innerHTML='Invalid Server Name';
   break;
  }
 }
 if(portNumber.length==0)
 {
  isValid=false;
  document.getElementById('portNumberErrorSection').innerHTML='Port Number Required';
 }
 for(var i=0;i<portNumber.length;i++)
 {
  if(number.indexOf(portNumber.charAt(i))==-1)
  {
   isValid=false;
   document.getElementById('portNumberErrorSection').innerHTML='Invalid PortNumber';
   break;
  }
 }
 if(dbUserId.length==0)
 {
  isValid=false;
  document.getElementById('dbUserIdErrorSection').innerHTML='Required';
 }
 if(dbPassword.length==0)
 {
  isValid=false;
  document.getElementById('dbPasswordErrorSection').innerHTML='Required';
 }
 if(adUserId.length==0)
 {
  isValid=false;
  document.getElementById('adUserIdErrorSection').innerHTML='Required';
 }
 if(adPassword.length==0)
 {
  isValid=false;
  document.getElementById('adPasswordErrorSection').innerHTML='Required';
 }
 if(adRePassword.length==0)
 {
  isValid=false;
  document.getElementById('adRePasswordErrorSection').innerHTML='Required';
 }
 if((adPassword.length>0) && (adRePassword.length>0))
 {
  //alert(adPassword+","+adRePassword);
  if(adPassword!=adRePassword)
  {
   //alert("chala");
   isValid=false;
   document.getElementById('adRePasswordErrorSection').innerHTML="Password doesn't match";
  }
 }
 return isValid;
}

</script>
</head>
<body class="bg-gradient-primary">
  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              
                  <div class="col-lg-12 p-5 databaseInstallationSection" id='databaseInstallationSection' name='databaseInstallationSection' >
                    <h1 class="h4 text-center  text-gray-900 mb-4">Welcome In Database Installation...</h1>

<form id='installationForm'  method='post'>
	<div class="form-group">
		<span class="errorSection" id="installationErrorSection"></span><br>
		<label for="databaseName">Database Name : </label>
		<input type="text" class="form-control" id="databaseName" name="databaseName">
		<span class="errorSection" id="databaseNameErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="serverName">Server :</label>
		<input type="text" class="form-control" id="serverName" name="serverName"  >
		<span class="errorSection" id="serverNameErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="portNumber">Port Number :</label>
		<input type="text" class="form-control" id="portNumber" name="portNumber"  >
		<span class="errorSection" id="portNumberErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="dbUserId">User Id :</label>
		<input type="text" class="form-control" id="dbUserId" name="dbUserId"  >
		<span class="errorSection" id="dbUserIdErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="dbPassword">Password :</label>
		<input type="password" class="form-control" id="dbPassword" name="dbPassword"  >
		<span class="errorSection" id="dbPasswordErrorSection"></span>
	</div>
	<h3>Administrator...</h3>
	<div class="form-group">
		<label for="dbUserId">User Id :</label>
		<input type="text" class="form-control" id="adUserId" name="adUserId"  >
		<span class="errorSection" id="adUserIdErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="adPassword">Password :</label>
		<input type="password" class="form-control" id="adPassword" name="adPassword"  >
		<span class="errorSection" id="adPasswordErrorSection"></span>
	</div>
	<div class="form-group">
		<label for="adRePassword">Re-Entered Password :</label>
		<input type="password" class="form-control" id="adRePassword" name="adRePassword"  >
		<span class="errorSection" id="adRePasswordErrorSection"></span>
	</div>
	<div class="form-group">
		<center><button type="button" onclick="server()"  class="btn btn-success" id="installButton" >Install</button><center>
	</div>
</form>
		  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>


  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

</body>
</html>

function loginValidation()
{
//alert("LoginValidation chala");
$$$.postJSON({
 "url":"service/Login/loginValidation",
 "success":function(responseJSON){
   $$$("#userProfileName").html(responseJSON.username);
   //alert("login success");
 },
 "exception":function(exception){
   //alert(exception);
   window.location.href="login.jsp";
   //window.location.replace("login.jsp");
 },
 "faild":function(){
   //alert("Unable to process");
   window.location.replace("login.jsp");
 }
});
}
window.addEventListener("load",loginValidation);


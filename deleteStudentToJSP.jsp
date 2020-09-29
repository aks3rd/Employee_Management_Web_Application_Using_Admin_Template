<jsp:useBean id='studentBean' scope='request' class='com.thinking.machines.beans.StudentBean'/>
<jsp:setProperty name='studentBean' property='*'/>
<jsp:forward page='service/Student/deleteStudent'/>

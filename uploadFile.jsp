<jsp:include page='/MasterPageTopSection.jsp' />
<jsp:useBean id='messageBean' scope='request' class='com.thinking.machines.beans.MessageBean' />
<jsp:useBean id='errorBean' scope='request' class='com.thinking.machines.beans.ErrorBean' />
<jsp:useBean id='studentBean' scope='request' class='com.thinking.machines.beans.StudentBean' />


          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">File Upload</h1>
            <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>
          </div>
         <div>
         <center>
           <h1>Welcome to file upload  web application</h1><br/><br/>
           <h3>Single File Upload</h3>
	   <form action="service/UploadFile/upload2" method="post" enctype="multipart/form-data" >
                First Name : <input type="text" name="firstName" id="firstName"><br>
		Select File : <input type="file" name="singlefile" id="singlefile" >
		<button type="submit" name="submitButton1">Upload</button>
	   </form><br/><br/><br/>
           <h3>Multiple File Upload</h3>
	   <form action="service/UploadFile/upload" method="post" enctype="multipart/form-data" >
		Select Files : <input type="file" name="multiplefile" id="multiplefile" multiple >
		<button type="submit" name="submitButton">Upload</button>
	   </form><br><br><br>
           <form action="service/Test/sam" method="post">
		<button type="submit" name="testButton">Test</button>
	   </form>
        </center>
        </div>


<jsp:include page='/MasterPageBottomSection.jsp'/>

<jsp:include page='/MasterPageTopSection.jsp' />
<jsp:useBean id='messageBean' scope='request' class='com.thinking.machines.beans.MessageBean' />
<jsp:useBean id='errorBean' scope='request' class='com.thinking.machines.beans.ErrorBean' />
<jsp:useBean id='studentBean' scope='request' class='com.thinking.machines.beans.StudentBean' />
<jsp:include page='/StudentJS.jsp' />

<script>

 function errorBeanModalShow()
 {
   //alert("Chala");
   var x=<jsp:getProperty name='errorBean' property='success'/>;
   var y;
   if(x==true)
   {
    //alert("true");
    var type="<jsp:getProperty name='errorBean' property='type'/>";
    if(type.toUpperCase()=="delete".toUpperCase())
    {
      var message="<jsp:getProperty name='errorBean' property='message'/>";
      $$$("#servletNotificationSection").html(message);
      $("#notificationModal").modal('show');
    }
    if(type.toUpperCase()=="add".toUpperCase() || type.toUpperCase()=="edit".toUpperCase())
    {
      var message="<jsp:getProperty name='errorBean' property='message'/>";
      var rollNumber=<jsp:getProperty name='studentBean' property='rollNumber'/>;
      //alert(rollNumber);
      var iii=0;
      while(iii<viewModel.students.length)
      {
        if(viewModel.students[iii].rollNumber==rollNumber)
        {
          var tr=document.getElementById("ixix"+iii);
          selectRow(iii,tr);
          tr.scrollIntoView(false);
        }
        iii++;
      }

    }
   }
   if(x==false)
   {
    //alert("false");
    var type="<jsp:getProperty name='errorBean' property='type'/>";
    if(type.toUpperCase()=="delete".toUpperCase())
    {
      var message="<jsp:getProperty name='errorBean' property='message'/>";
      $$$("#servletNotificationSection").html(message);
      $("#notificationModal").modal('show');
    }
    if(type.toUpperCase()=="add".toUpperCase())
    {
      //alert("add wala");
      var s=new Student();
      s.rollNumber=<jsp:getProperty name='studentBean' property='rollNumber'/>;
      s.name="<jsp:getProperty name='studentBean' property='name'/>";
      s.gender="<jsp:getProperty name='studentBean' property='gender'/>";
      s.dateOfBirth="<jsp:getProperty name='studentBean' property='dateOfBirth'/>";
      s.address="<jsp:getProperty name='studentBean' property='address'/>";
      s.indian=<jsp:getProperty name='studentBean' property='indian'/>;
      s.city=<jsp:getProperty name='studentBean' property='cityCode'/>;
      //alert(s.rollNumber+","+s.name+","+s.gender+","+s.address+","+s.indian+","+s.city);
      displayAddErrorForm(s);
      document.getElementById("addFormErrorSection").innerHTML="<jsp:getProperty name='errorBean' property='message'/>";
    }
    if(type.toUpperCase()=="edit".toUpperCase())
    {
      //alert("edit wala");
      var s=new Student();
      s.rollNumber=<jsp:getProperty name='studentBean' property='rollNumber'/>;
      s.name="<jsp:getProperty name='studentBean' property='name'/>";
      s.gender="<jsp:getProperty name='studentBean' property='gender'/>";
      s.dateOfBirth="<jsp:getProperty name='studentBean' property='dateOfBirth'/>";
      s.address="<jsp:getProperty name='studentBean' property='address'/>";
      s.indian=<jsp:getProperty name='studentBean' property='indian'/>;
      s.city=<jsp:getProperty name='studentBean' property='cityCode'/>;
      //alert(s.rollNumber+","+s.name+","+s.gender+","+s.address+","+s.indian+","+s.city);
      displayEditErrorForm(s);
      document.getElementById("addFormErrorSection").innerHTML="<jsp:getProperty name='errorBean' property='message'/>";
    }

   <jsp:setProperty name='errorBean' property='message' value=" " />
   <jsp:setProperty name='errorBean' property='success' value='false' />
   }

 }
 window.addEventListener('load',errorBeanModalShow);
</script>
<div id="tableGrid">
          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Students</h1>

          <!-- Students List Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Student List</h6>
            </div>
            <!-- <div class="card-body"> -->
              

          <!-- Here Bootstrap Table Starts -->
		<!-- gridTableSection -->
		<div class="gridTableSection table-responsive" id="tableContent">
			<table id="studentTable" class="table table-bordered " > <!--table-striped -->
				<thead class="thead-light  shadow ">
					<tr>
						<th scope="col">S.No.</th>
						<th scope="col">Roll Number</th>
						<th scope="col">Name</th>
						<th scope="col">Gender</th>
						<th scope="col">DOB</th>
						<th scope="col">City</th>
						<th scope="col">Edit</th>
						<th scope="col">Delete</th>
						<th scope="col">Details</th>
					</tr>
				</thead>
				<tbody id="studentViewGrid">

				</tbody>
			</table>
		</div>
          <!-- Here Bootstrap Table Ends -->

              
           <!-- </div> -->
          </div>
</div>
<jsp:include page='/StudentDetailsSection.jsp'/>
<jsp:include page='/StudentAddSection.jsp'/>
<jsp:include page='/StudentEditSection.jsp'/>
<jsp:include page='/StudentDeleteSection.jsp'/>

<jsp:include page='/MasterPageBottomSection.jsp' />

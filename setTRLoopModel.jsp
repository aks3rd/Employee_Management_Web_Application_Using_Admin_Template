<jsp:include page='/MasterPageTopSection.jsp' />
<script>
function EmployeeService()
{
 this.getEmployees=function(success,failier){
   $$$.postJSON({
    "url":"getEmployees",
    "success":success,
    "exception":failier,
    "faild":failier
   });
 }
}
function DataModel()
{
 this.employees=[];
 this.mode='view';
 var outer=this;
 function EmployeeTableModel()
 {
   this.getRows=function(){
     return outer.employees.length;
   }
   this.getColumns=function(){
     return 9;
   }
   this.getColumnTitle=function(columnNumber){
     if(columnNumber==0)
     {
       return "S.No.";
     }
     if(columnNumber==1)
     {
       return "Employee Id";
     }
     if(columnNumber==2)
     {
       return "Name";
     }
     if(columnNumber==3)
     {
       return "Gender";
     }
     if(columnNumber==4)
     {
       return "DOB";
     }
     if(columnNumber==5)
     {
       return "Designation";
     }
     if(columnNumber==6)
     {
       return "Edit";
     }
     if(columnNumber==7)
     {
       return "Delete";
     }
     if(columnNumber==8)
     {
       return "Details";
     }
   }
   this.getValueAt=function(rowNumber,columnNumber){
     if(columnNumber==0)
     {
       return (rowNumber+1);
     }
     if(columnNumber==1)
     {
       return outer.employees[rowNumber].employeeId;
     }
     if(columnNumber==2)
     {
       return outer.employees[rowNumber].name;
     }
     if(columnNumber==3)
     {
       img=document.createElement("img");
       if(outer.employees[rowNumber].gender=='M')
       {
        img.src='css/images/male_icon.png';
       }
       else
       {
        img.src='css/images/female_icon.png';
       }
       return img;
     }
     if(columnNumber==4)
     {
       return outer.employees[rowNumber].dateOfBirth;
     }
     if(columnNumber==5)
     {
       return outer.employees[rowNumber].designationTitle;
     }
     if(columnNumber==6)
     {
       img=document.createElement("img");
       img.src='css/images/edit_icon.png';
       return img;
     }
     if(columnNumber==7)
     {
       img=document.createElement("img");
       img.src='css/images/delete_icon.png';
       return img;
     }
     if(columnNumber==8)
     {
       img=document.createElement("img");
       img.src='css/images/details_icon.png';
       return img;
     }
   }
 }
 this.employeeTableModel=new EmployeeTableModel();
}

var dataModel=new DataModel();

function pageOnLoad()
{
  $$$.setModel(dataModel);
  getEmployees();
  $$$.setTRLoopModel(dataModel);
  $$$.setControlModel(dataModel);
}
function getEmployees()
{
  var employeeService=new EmployeeService();
  var success=function(response){
   
   dataModel.employees=response;
   //alert("DataModel.employees : "+JSON.stringify(dataModel.employees));
  }
  var failier=function(exception){
   alert(exception);
  }
  employeeService.getEmployees(success,failier);
}
window.addEventListener("load",pageOnLoad);



</script>

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Employees</h1>
          <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the <a target="_blank" href="https://datatables.net">official DataTables documentation</a>.</p>
<div id="viewSection" tm-if='mode=="view"'>
          <!-- Employees List Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Employees List using tm-for and tm-if</h6>
            </div>
            <!-- <div class="card-body"> -->
              

          <!-- Here Bootstrap Table Starts -->
		<!-- gridTableSection -->
		<div class="gridTableSection table-responsive" id="tableContent">
			<table id="employeeTable" class="table table-bordered table-striped"  tableModel="employeeTableModel" >
				<thead class="thead-light  shadow ">
					<tr>
						<th scope="col">S.No.</th>
						<th scope="col">Employee Id</th>
						<th scope="col">Name</th>
						<th scope="col">Gender</th>
						<th scope="col">DOB</th>
						<th scope="col">Designation</th>
						<th scope="col">Edit</th>
						<th scope="col">Delete</th>
						<th scope="col">Details</th>
					</tr>
				</thead>
				<tbody id="studentViewGrid">
					<tr tm-for="e in employees">
						<td scope="col">{{{}}}</td>
						<td scope="col">{{{e.employeeId}}}</td>
						<td scope="col">{{{e.name}}}</td>
						<td scope="col">{{{e.gender}}}</td>
						<td scope="col">{{{e.dateOfBirth}}}</td>
						<td scope="col">{{{e.designationTitle}}}</td>
						<td scope="col">{{{}}}</td>
						<td scope="col">{{{}}}</td>
						<td scope="col">{{{}}}</td>						
					</tr>

				</tbody>
			</table>
		</div>
          <!-- Here Bootstrap Table Ends -->

              
           <!-- </div> -->
          </div>
                <button type='button' id='addButton' onclick="dataModel.mode='add'">ADD</button>&nbsp;&nbsp;
                <button type='button' id='editButton' onclick="dataModel.mode='edit'">EDIT</button>

</div>

<div id="addSection" tm-if="mode=='add'">
  <h1>Add Section</h1>
  <button type='button' id='addSubmitButton'  onclick="dataModel.mode='view'">Save</button>
</div>
<div id="editSection" tm-if="mode=='edit'">
  <h1>Edit Section</h1>
  <button type='button' id='editSubmitButton' onclick="dataModel.mode='view'">Update</button>
</div>
<jsp:include page='/MasterPageBottomSection.jsp' />

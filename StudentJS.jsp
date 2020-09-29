<%@taglib uri='/WEB-INF/tlds/mytags.tld' prefix='tm'%>
<jsp:useBean id='messageBean' scope='request' class='com.thinking.machines.beans.MessageBean' />

<jsp:useBean id='errorBean' scope='request' class='com.thinking.machines.beans.ErrorBean' />
<jsp:useBean id='studentBean' scope='request' class='com.thinking.machines.beans.StudentBean' />
<script>
function City()
{
 this.code=0;
 this.name="";
}
function Student()
{
 this.rollNumber=0;
 this.name="";
 this.gender="";
 this.dateOfBirth="";
 this.address="";
 this.indian=false;
 this.city=null;
}

function createEditIconClickedHandler(index,row)
{
 return function()
 {
  displayEditSection(index,row);
 };
}
function createDeleteIconClickedHandler(index,row)
{
 return function()
 {
  displayDeleteConfirmationSection(index,row);
 };
}
function createDetailsIconClickedHandler(index,row)
{
 return function()
 {
  displayDetailsSection(index,row);
 };
}

function createGoToViewButtonClickedHandler()
{
 return function()
 {
  goToViewSection();
 };
}

function createRowClickedHandler(index,row)
{
 return function(){
 selectRow(index,row);
 };
}
function selectRow(index,row)
{
 if(index==viewModel.selectedStudentIndex) return;
 if(viewModel.selectedRow!=null)
 {
  viewModel.selectedRow.style.background="white";
  viewModel.selectedRow.style.color="black";
 }
 viewModel.selectedStudentIndex=index;
 viewModel.selectedRow=row;
 row.style.background="lightgray";
 row.style.color="black";
}

function searchStudent(studentName)
{
 studentName.style.color='black'
 var sn=studentName.value.trim().toUpperCase();
 if(sn.length==0)return;
 var i=0;
 while(i<viewModel.students.length)
 {
  if(viewModel.students[i].name.toUpperCase().startsWith(sn))break;
  i++;
 }
 if(i==viewModel.students.length)
 {
  studentName.style.color='red';
  return;
 }
 else
 {
  var tr=document.getElementById('ixix'+i)
  selectRow(i,tr);
  tr.scrollIntoView(false);
 }
}

function searchStudentByButtonClickedAction()
{
 studentName=document.getElementById('searchBox');
 searchStudent(studentName);
}


function goToViewSection()
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='block';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='none';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='none';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='none';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='none';
 resetAddForm();
 resetEditForm();
}
function resetAddForm()
{
 document.getElementById("addSectionForm").reset();
 document.getElementById('addFormErrorSection').innerHTML='';
 document.getElementById('addSectionRollNumberError').innerHTML='';
 document.getElementById('addSectionNameError').innerHTML='';
 document.getElementById('addSectionDateOfBirthError').innerHTML='';
 document.getElementById('addSectionGenderError').innerHTML=' ';
 document.getElementById('addSectionCityError').innerHTML='';
 document.getElementById('addSectionAddressError').innerHTML='';    
}
function resetEditForm()
{
 document.getElementById("editSectionForm").reset();
 document.getElementById('editSectionRollNumberError').innerHTML='';
 document.getElementById('editSectionNameError').innerHTML='';
 document.getElementById('editSectionDateOfBirthError').innerHTML='';
 document.getElementById('editSectionGenderError').innerHTML=' ';
 document.getElementById('editSectionCityError').innerHTML='';
 document.getElementById('editSectionAddressError').innerHTML='';    
}

function displayDetailsSection(index,row)
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='none';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='block';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='none';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='none';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='none';

 var student=viewModel.students[index];
 var rollNumber=document.getElementById("detailsSectionRollNumber");
 rollNumber.value=student.rollNumber;
 var name=document.getElementById("detailsSectionName");
 name.value=student.name;
 if(student.gender=='M')
 {
  var gender=document.getElementById("detailsSectionMale");
  gender.checked=true;
 }
 else
 {
  var gender=document.getElementById("detailsSectionFemale");
  gender.checked=true;
 }
 var newdate = student.dateOfBirth.split('-').reverse().join('-');
 var date = new Date(newdate);
 var currentDate = date.toISOString().slice(0,10);
 var dateOfBirth=document.getElementById("detailsSectionDateOfBirth");
 dateOfBirth.value=currentDate;
 // dateOfBirth.valueAsDate=new Date(student.dateOfBirth);
 var address=document.getElementById("detailsSectionAddress");
 address.value=student.address;
 if(student.indian==true)
 {
  var indian=document.getElementById("detailsSectionIndian");
  indian.checked=true;
 }
 else
 {
  var indian=document.getElementById("detailsSectionIndian");
  indian.checked=false;
 }
 var cityCode=document.getElementById("detailsSectionCity");
 cityCode.value=student.city.code;
 // var cityName=document.getElementById('editSectionCityName');
 // cityName.value=student.city.name;
 var button=document.getElementById("detailsSectionGoToViewButton");
 button.onclick=createGoToViewButtonClickedHandler();
}

function displayAddSection()
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='none';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='none';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='block';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='none';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='none';

 var addGoToButton=document.getElementById('addSectionGoToViewButton');
 addGoToButton.onclick=createGoToViewButtonClickedHandler();
 //var submitButton=document.getElementById('addSectionSubmitButton');
 //submitButton.onclick=createAddButtonClickedHandler();
}

function displayAddErrorForm(object)
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='none';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='none';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='block';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='none';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='none';


 var student=object;
 var rollNumber=document.getElementById('addSectionRollNumber');
 rollNumber.value=student.rollNumber;
 var name=document.getElementById('addSectionName');
 name.value=student.name;
 if(student.gender=='M')
 {
  var gender=document.getElementById('addSectionMale');
  gender.checked=true;
 }
 else
 {
  var gender=document.getElementById('addSectionFemale');
  gender.checked=true;
 }
 var newdate = student.dateOfBirth.split('-').reverse().join('-');
 var date = new Date(newdate);
 var convertedStartDate = new Date(student.dateOfBirth);
 var currentDate = convertedStartDate.toISOString().slice(0,10);
 var dateOfBirth=document.getElementById('addSectionDateOfBirth');
 dateOfBirth.value=currentDate;
 // dateOfBirth.valueAsDate=new Date(student.dateOfBirth);
 var address=document.getElementById('addSectionAddress');
 address.value=student.address;
 if(student.indian==true)
 {
  var indian=document.getElementById('addSectionIndian');
  indian.checked=true;
 }
 else
 {
  var indian=document.getElementById('addSectionIndian');
  indian.checked=false;
 }
 var cityCode=document.getElementById('addSectionCity');
 cityCode.value=student.city;
 // var cityName=document.getElementById('addSectionCityName');
 // cityName.value=student.city.name;

 var addGoToButton=document.getElementById('addSectionGoToViewButton');
 addGoToButton.onclick=createGoToViewButtonClickedHandler();
}


function displayEditSection(index,row)
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='none';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='none';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='none';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='block';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='none';


 var student=viewModel.students[index];
 var rollNumber=document.getElementById('editSectionRollNumber');
 rollNumber.value=student.rollNumber;
 var name=document.getElementById('editSectionName');
 name.value=student.name;
 if(student.gender=='M')
 {
  var gender=document.getElementById('editSectionMale');
  gender.checked=true;
 }
 else
 {
  var gender=document.getElementById('editSectionFemale');
  gender.checked=true;
 }
 var newdate = student.dateOfBirth.split('-').reverse().join('-');
 var date = new Date(newdate);
 var currentDate = date.toISOString().slice(0,10);
 var dateOfBirth=document.getElementById('editSectionDateOfBirth');
 dateOfBirth.value=currentDate;
 // dateOfBirth.valueAsDate=new Date(student.dateOfBirth);

 var address=document.getElementById('editSectionAddress');
 address.value=student.address;
 if(student.indian==true)
 {
  var indian=document.getElementById('editSectionIndian');
  indian.checked=true;
 }
 else
 {
  var indian=document.getElementById('editSectionIndian');
  indian.checked=false;
 }
 var cityCode=document.getElementById('editSectionCity');
 cityCode.value=student.city.code;
 // var cityName=document.getElementById('editSectionCityName');
 // cityName.value=student.city.name;
 var editGoToButton=document.getElementById('editSectionGoToViewButton');
 editGoToButton.onclick=createGoToViewButtonClickedHandler();
 //var submitButton=document.getElementById('editSectionSubmitButton');
 //submitButton.onclick=createEditButtonClickedHandler();

}


function displayDeleteConfirmationSection(index,row)
{
 var grid;
 grid=document.getElementById('tableGrid');
 grid.style.display='none';
 var studentDetailsSection=document.getElementById('studentDetailsSection');
 studentDetailsSection.style.display='none';
 var studentAddSection=document.getElementById('studentAddSection');
 studentAddSection.style.display='none';
 var studentEditSection=document.getElementById('studentEditSection');
 studentEditSection.style.display='none';
 var studentDeleteSection=document.getElementById('studentDeleteSection');
 studentDeleteSection.style.display='block';

 var student=viewModel.students[index];
 var rollNumber=document.getElementById('deleteSectionRollNumber');
 rollNumber.value=student.rollNumber;
 var name=document.getElementById('deleteSectionName');
 name.value=student.name;
 if(student.gender=='M')
 {
  var gender=document.getElementById('deleteSectionMale');
  gender.checked=true;
 }
 else
 {
  var gender=document.getElementById('deleteSectionFemale');
  gender.checked=true;
 }
 var newdate = student.dateOfBirth.split('-').reverse().join('-');
 var date = new Date(newdate);
 var currentDate = date.toISOString().slice(0,10);
 var dateOfBirth=document.getElementById('deleteSectionDateOfBirth');
 dateOfBirth.value=currentDate;
 // dateOfBirth.valueAsDate=new Date(student.dateOfBirth);
 var address=document.getElementById('deleteSectionAddress');
 address.value=student.address;
 if(student.indian==true)
 {
  var indian=document.getElementById('deleteSectionIndian');
  indian.checked=true;
 }
 else
 {
  var indian=document.getElementById('deleteSectionIndian');
  indian.checked=false;
 }
 var cityCode=document.getElementById('deleteSectionCity');
 cityCode.value=student.city.code;
 // var cityName=document.getElementById('editSectionCityName');
 // cityName.value=student.city.name;
 var deleteGoToButton=document.getElementById('deleteSectionGoToViewButton');
 deleteGoToButton.onclick=createGoToViewButtonClickedHandler();
 //var submitButton=document.getElementById('deleteSectionSubmitButton');
 //submitButton.onclick=createDeleteButtonClickedHandler();
}




function ViewModel()
{
 this.students=[];
 this.cities=[];
 this.selectedRow=null;
 this.selectedStudentIndex=-1;
}
var viewModel=new ViewModel();

function initView()
{

 var c;
 <tm:TableIterator table='city' name='city' orderBy='name'>
  c=new City();
  c.code=${city.code};
  c.name='${city.name}';
  viewModel.cities[${index}]=c;
 </tm:TableIterator> 

 var s;
 <tm:TableIterator table='student_view' name='student' orderBy='name'>
  s=new Student();
  s.rollNumber=${student.rollNumber};
  s.name='${student.name}';
  s.gender='${student.gender}';
  s.dateOfBirth='${student.dateOfBirth}';
  s.address='${student.address}';
  s.indian=${student.indian};
  c=new City();
  c.code=${student.cityCode};
  c.name='${student.cityName}';
  s.city=c;
  viewModel.students[${index}]=s;
 </tm:TableIterator>



 var addFormCityCode=document.getElementById("addSectionCity");
 var editFormCityCode=document.getElementById("editSectionCity");
 var deleteFormCityCode=document.getElementById("deleteSectionCity");
 var detailsFormCityCode=document.getElementById("detailsSectionCity");
 var ci=0;
 var option;
 while(ci<viewModel.cities.length)
 {
  option=document.createElement("option");
  option.value=viewModel.cities[ci].code;
  option.text=viewModel.cities[ci].name;
  addFormCityCode.appendChild(option);
  option=document.createElement("option");
  option.value=viewModel.cities[ci].code;
  option.text=viewModel.cities[ci].name;
  editFormCityCode.appendChild(option);
  option=document.createElement("option");
  option.value=viewModel.cities[ci].code;
  option.text=viewModel.cities[ci].name;
  deleteFormCityCode.appendChild(option);
  option=document.createElement("option");
  option.value=viewModel.cities[ci].code;
  option.text=viewModel.cities[ci].name;
  detailsFormCityCode.appendChild(option);
  ci++;
 }


 var grid=document.getElementById('studentViewGrid');
 var i=0;
 var tr,td,img,textNode,anchor;
 while(i<viewModel.students.length)
 {
  tr=document.createElement("tr");
  td=document.createElement("td");
  textNode=document.createTextNode((i+1)+".");
  td.appendChild(textNode);
  td.scope="row";
  tr.appendChild(td);

  td=document.createElement("td");
  textNode=document.createTextNode(viewModel.students[i].rollNumber);
  td.appendChild(textNode);
  tr.appendChild(td);

  td=document.createElement("td");
  textNode=document.createTextNode(viewModel.students[i].name);
  td.appendChild(textNode);
  tr.appendChild(td);

  td=document.createElement("td");
  img=document.createElement("img");
  if(viewModel.students[i].gender=='M')
  {
   img.src='/stylethree/images/icons/male_icon.png';
  }
  else
  {
   img.src='/stylethree/images/icons/female_icon.png';
  }
  td.appendChild(img);
  tr.appendChild(td);

  td=document.createElement("td");
  textNode=document.createTextNode(viewModel.students[i].dateOfBirth);
  td.appendChild(textNode);
  tr.appendChild(td);

  td=document.createElement("td");
  textNode=document.createTextNode(viewModel.students[i].city.name);
  td.appendChild(textNode);
  tr.appendChild(td);

  td=document.createElement("td");
  img=document.createElement("img");
  img.src='/stylethree/images/icons/edit_icon.png';
  img.onclick=createEditIconClickedHandler(i,tr);
  td.appendChild(img);
  tr.appendChild(td);

  td=document.createElement("td");
  img=document.createElement("img");
  img.src='/stylethree/images/icons/delete_icon.png';
  img.onclick=createDeleteIconClickedHandler(i,tr);
  td.appendChild(img);
  tr.appendChild(td);
 
  td=document.createElement("td");
  img=document.createElement("img");
  img.src='/stylethree/images/icons/details_icon.png';
  img.onclick=createDetailsIconClickedHandler(i,tr);
  td.appendChild(img);
  tr.appendChild(td);
  tr.style.cursor='pointer';
  tr.id='ixix'+i;
  tr.onclick=createRowClickedHandler(i,tr);
  grid.appendChild(tr);
  i++;
 }
}



function validate(form)
{
 var isValid=true;
 var errorform;
 if(form=='addSectionForm')
 {
  errorform='add';
 }
 if(form=='editSectionForm')
 {
  errorform='edit';
 }
 document.getElementById(errorform+'SectionRollNumberError').innerHTML='';
 document.getElementById(errorform+'SectionNameError').innerHTML='';
 document.getElementById(errorform+'SectionDateOfBirthError').innerHTML='';
 document.getElementById(errorform+'SectionGenderError').innerHTML=' ';
 document.getElementById(errorform+'SectionCityError').innerHTML='';
 document.getElementById(errorform+'SectionAddressError').innerHTML='';
 var myform=document.getElementById(form);
 var rollNumber=myform.rollNumber.value;
 var name=myform.name.value;
 var dob=myform.dateOfBirth.value;
 var gender=myform.gender.value;
 var address=myform.address.value;
 var city=myform.cityCode.value;
 var number='0123456789';
 var valChar='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ';
 if(rollNumber.length==0)
 {
  isValid=false;
  document.getElementById(errorform+'SectionRollNumberError').innerHTML='Roll Number Required';
 }
 for(var i=0;i<rollNumber.length;i++)
 {
  if(number.indexOf(rollNumber.charAt(i))==-1)
  {
   isValid=false;
   document.getElementById(errorform+'SectionRollNumberError').innerHTML='Invalid RollNumber';
   break;
  }
 }
 if(name.length==0)
 {
  isValid=false;
  document.getElementById(errorform+'SectionNameError').innerHTML='Name Required';
 }
 for(var i=0;i<name.length;i++)
 {
  if(valChar.indexOf(name.charAt(i))==-1)
  {
   isValid=false;
   document.getElementById(errorform+'SectionNameError').innerHTML='Invalid Name';
   break;
  }
 }
 if(dob.length==0)
 {
  isValid=false;
  document.getElementById(errorform+'SectionDateOfBirthError').innerHTML='Date of Birth Required';
 }
 if(gender!='M'&&gender!='F')
 {  
  isValid=false;
  document.getElementById(errorform+'SectionGenderError').innerHTML='Select Gender';
 } 
 if(city==-1) 
 { 
  isValid=false;
  document.getElementById(errorform+'SectionCityError').innerHTML='Select City';
 }
 if(address.length==0)
 {
  isValid=false;
  document.getElementById(errorform+'SectionAddressError').innerHTML='Address Required';
 }
 return isValid;
}

   function createAddButtonClickedHandler()
   {
    return function()
    {
     addStudent();
    };
   }
   function createDeleteButtonClickedHandler()
   {
    return function()
    {
     deleteStudent();
    };
   }
   function createEditButtonClickedHandler()
   {
    return function()
    {
     editStudent();
    };
   }

   function addStudent()
   {
     var result=validate("addSectionForm");
     if(result)
     {
       alert("Add huaa");
     }
     else
     {
      alert("ADD section me error");
      return;
     }
   }
   function editStudent()
   {
     var result=verify("editSectionForm");
     if(result)
     {
      alert("edit ke liye request gyi");
     }
     else
     {
      alert("edit section me error");
     }
   }
   function deleteStudent()
   {
     alert("Delete HUaa");
   }


window.addEventListener('load',initView);

</script>

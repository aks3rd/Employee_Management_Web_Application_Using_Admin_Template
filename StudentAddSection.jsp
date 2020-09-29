<!-- StudentAddSection  division starts here -->

<div  id='studentAddSection' name='studentAddSection' style="display:none;padding:5px;margin:5px;">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Add Student </h1>

          <!-- Students List Example -->
          <div class="card shadow mb-4">
            
             <div class="card-body"> 
              

          <!-- Here Student ADD Form Starts -->




<form action='addStudentToJSP.jsp' id='addSectionForm' onsubmit="return validate('addSectionForm')" method='post'>
<!--	<center><h1>Add Form</h1></center> -->
	<div class="form-group">
		<span class="errorSection" id="addFormErrorSection"></span><br>
		<label for="addSectionRollNumber">Roll Number :</label>
		<input type="text" class="form-control" id="addSectionRollNumber" name="rollNumber">
		<span class="errorSection" id="addSectionRollNumberError"></span>
	</div>
	<div class="form-group">
		<label for="addSectionName">Name :</label>
		<input type="text" class="form-control" id="addSectionName" name="name"  >
		<span class="errorSection" id="addSectionNameError"></span>
	</div>
	<div class="form-group">
		<label for="addSectionGender">Gender :</label>
		<span class="errorSection" id="addSectionGenderError"></span>
	</div>
	<div class="form-group form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="addSectionMale" value='M' >
		<label class="form-check-label" for="addSectionMale">Male</label>
	</div>
	<div class="form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="addSectionFemale" value='F' >
		<label class="form-check-label" for="addSectionFemale">Female</label>
	</div>
	<div class="form-group">
		<label for="addSectionDateOfBirth">Date Of Birth :</label>
		<input type="date" class="form-control" id="addSectionDateOfBirth" name="dateOfBirth"  >
		<span class="errorSection" id="addSectionDateOfBirthError"></span>
	</div>
	<div class="form-group">
		<label for="addSectionAddress">Address :</label>
		<textarea class="form-control" id="addSectionAddress" rows="3" name='address' ></textarea>
		<span class="errorSection" id="addSectionAddressError"></span>
	</div>
	<div class="form-group form-check form-check-inline">
		<label class="form-check-label" for="addSectionIndian">Indian :</label>&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="checkbox" id="addSectionIndian" name="indian" value="true">
		<span class="errorSection" id="addSectionIndianError"></span>
	</div>
	<div class="form-group">
		<label for="addSectionCity">City :</label>
		<select class="form-control" id="addSectionCity" name='cityCode' >
			<option value='-1'>&lt;Select City&gt;</option>
		</select>
		<span class="errorSection" id="addSectionCityError"></span>
	</div>
<!--        <button type="button" class="btn btn-outline-danger">Go To View Section</button> -->
	<div class="form-group">
		<center>
			<button type="submit" class="btn btn-success" id="addSectionSubmitButton" >Save</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger" id="addSectionGoToViewButton" >Go To View Section</button>
		</center>
	</div>

</form>



          <!-- Here Student ADD Form Ends -->

              
            </div> 
          </div>


</div>

<!-- StudentAddSection  division ends here -->

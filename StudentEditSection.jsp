<!-- StudentEditSection division starts here -->

<div  id='studentEditSection' name='studentEditSection' style="display:none;padding:5px;margin:5px;">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Update Details</h1>

          <!-- Students List Example -->
          <div class="card shadow mb-4">
            
             <div class="card-body"> 
              

          <!-- Here Student Edit Form Starts -->




<form action='editStudentToJSP.jsp' id='editSectionForm' onsubmit="return validate('editSectionForm')" method='post'>
<!--	<center><h1>Update Form</h1></center> -->
	<div class="form-group">
		<label for="editSectionRollNumber">Roll Number :</label>
		<input type="text" class="form-control" id="editSectionRollNumber" name="rollNumber" readonly >
		<span class="errorSection" id="editSectionRollNumberError"></span>
	</div>
	<div class="form-group">
		<label for="editSectionName">Name :</label>
		<input type="text" class="form-control" id="editSectionName" name="name"  >
		<span class="errorSection" id="editSectionNameError"></span>
	</div>
	<div class="form-group">
		<label for="editSectionGender">Gender :</label>
		<span class="errorSection" id="editSectionGenderError"></span>
	</div>
	<div class="form-group form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="editSectionMale" value='M' >
		<label class="form-check-label" for="editSectionMale">Male</label>
	</div>
	<div class="form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="editSectionFemale" value='F' >
		<label class="form-check-label" for="editSectionFemale">Female</label>
	</div>
	<div class="form-group">
		<label for="editSectionDateOfBirth">Date Of Birth :</label>
		<input type="date" class="form-control" id="editSectionDateOfBirth" name="dateOfBirth"  >
		<span class="errorSection" id="editSectionDateOfBirthError"></span>
	</div>
	<div class="form-group">
		<label for="editSectionAddress">Address :</label>
		<textarea class="form-control" id="editSectionAddress" rows="3" name='address'  ></textarea>
		<span class="errorSection" id="editSectionAddressError"></span>
	</div>
	<div class="form-group form-check form-check-inline">
		<label class="form-check-label" for="editSectionIndian">Indian :</label>&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="checkbox" id="editSectionIndian" name="indian" value="true" >
		<span class="errorSection" id="editSectionIndianError"></span>
	</div>
	<div class="form-group">
		<label for="editSectionCity">City :</label>
		<select class="form-control" id="editSectionCity" name='cityCode' >
			<option value='-1'>&lt;Select City&gt;</option>
		</select>
		<span class="errorSection" id="editSectionCityError"></span>
	</div>
<!--        <button type="button" class="btn btn-outline-danger">Go To View Section</button> -->
	<div class="form-group">
		<center>
			<button type="submit" class="btn btn-success" id="editSectionSubmitButton" >Update</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger" id="editSectionGoToViewButton" >Go To View Section</button>
		</center>
	</div>

</form>


          <!-- Here Student Edit Form Ends -->

              
            </div> 
          </div>


</div>

<!-- StudentEditSection division ends here -->

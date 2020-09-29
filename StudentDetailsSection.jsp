<!-- StudentDetailsSection division starts here -->

<div  id='studentDetailsSection' name='studentDetailsSection' style="display:none;padding:5px;margin:5px;">
          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Student Details </h1>

          <!-- Students List Example -->
          <div class="card shadow mb-4">
            
             <div class="card-body"> 
              

          <!-- Here Student Details Form Starts -->


<form>
<!--	<center><h1>Stdudent Details </h1></center> -->
	<div class="form-group">
		<label for="detailsSectionRollNumber">Roll Number :</label>
		<input type="text" class="form-control" id="detailsSectionRollNumber" name="rollNumber" readonly >
	</div>
	<div class="form-group">
		<label for="detailsSectionName">Name :</label>
		<input type="text" class="form-control" id="detailsSectionName" name="name" readonly >
	</div>
	<div class="form-group">
		<label for="detailsSectionGender">Gender :</label>
	</div>
	<div class="form-group form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="detailsSectionMale" value='M' disabled>
		<label class="form-check-label" for="detailsSectionMale">Male</label>
	</div>
	<div class="form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="detailsSectionFemale" value='F' disabled>
		<label class="form-check-label" for="detailsSectionFemale">Female</label>
	</div>
	<div class="form-group">
		<label for="detailsSectionDateOfBirth">Date Of Birth :</label>
		<input type="date" class="form-control" id="detailsSectionDateOfBirth" name="dateOfBirth" readonly >
	</div>
	<div class="form-group">
		<label for="detailsSectionAddress">Address :</label>
		<textarea class="form-control" id="detailsSectionAddress" rows="3" name='address' readonly ></textarea>
	</div>
	<div class="form-group form-check form-check-inline">
		<label class="form-check-label" for="detailsSectionIndian">Indian :</label>&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="checkbox" id="detailsSectionIndian" name="indian" value="true">
	</div>
	<div class="form-group">
		<label for="detailsSectionCity">City :</label>
		<select class="form-control" id="detailsSectionCity" name='cityCode' disabled>
			<option value='-1'>&lt;Select City&gt;</option>
		</select>
	</div>
<!--        <button type="button" class="btn btn-outline-danger">Go To View Section</button> -->
	<div class="form-group">
		<center><button type="button" class="btn btn-danger" id="detailsSectionGoToViewButton" >Go To View Section</button></center>
	</div>

</form>


          <!-- Here Student Details Form Ends -->

              
            </div> 
          </div>

</div>

<!-- StudentDetailsSection division ends here -->

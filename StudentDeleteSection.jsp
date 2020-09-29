<!-- StudentDeleteSection division starts here -->

<div  id='studentDeleteSection' name='studentDeleteSection' style="display:none;padding:5px;margin:5px;">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800"> Delete Confirmation Section </h1>

          <!-- Students List Example -->
          <div class="card shadow mb-4">
            
             <div class="card-body"> 
              

          <!-- Here Student Delete Confirmation Section Starts -->


<form action='deleteStudentToJSP.jsp' method='post'>
<!--	<center><h1>Delete Confirmation Section</h1></center> -->
	<div class="form-group">
		<label for="deleteSectionRollNumber">Roll Number :</label>
		<input type="text" class="form-control" id="deleteSectionRollNumber" name="rollNumber" readonly >
	</div>
	<div class="form-group">
		<label for="deleteSectionName">Name :</label>
		<input type="text" class="form-control" id="deleteSectionName" name="name" readonly >
	</div>
	<div class="form-group">
		<label for="deleteSectionGender">Gender :</label>
	</div>
	<div class="form-group form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="deleteSectionMale" value='M' disabled>
		<label class="form-check-label" for="deleteSectionMale">Male</label>
	</div>
	<div class="form-check form-check-inline">&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="radio" name="gender" id="deleteSectionFemale" value='F' disabled>
		<label class="form-check-label" for="deleteSectionFemale">Female</label>
	</div>
	<div class="form-group">
		<label for="deleteSectionDateOfBirth">Date Of Birth :</label>
		<input type="date" class="form-control" id="deleteSectionDateOfBirth" name="dateOfBirth" readonly >
	</div>
	<div class="form-group">
		<label for="deleteSectionAddress">Address :</label>
		<textarea class="form-control" id="deleteSectionAddress" rows="3" name='address' readonly ></textarea>
	</div>
	<div class="form-group form-check form-check-inline">
		<label class="form-check-label" for="deleteSectionIndian">Indian :</label>&nbsp;&nbsp;&nbsp;
		<input class="form-check-input" type="checkbox" id="deleteSectionIndian" name="indian" value="true">
	</div>
	<div class="form-group">
		<label for="deleteSectionCity">City :</label>
		<select class="form-control" id="deleteSectionCity" name='cityCode' disabled>
			<option value='-1'>&lt;Select City&gt;</option>
		</select>
	</div>
<!--        <button type="button" class="btn btn-outline-danger">Go To View Section</button> -->
	<div class="form-group">
		<center>
			<button type="submit" class="btn btn-success" id="deleteSectionSubmitButton" >Delete</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger" id="deleteSectionGoToViewButton" >Go To View Section</button>
		</center>
	</div>

</form>

          <!-- Here Student Delete Confirmation Section Ends -->

              
            </div> 
          </div>

</div>

<!-- StudentDeleteSection division ends here -->

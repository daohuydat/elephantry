<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
function isNumberKey(txt, evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 46 || charCode == 45) {
    	return false;
    } else {
        if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    }
    return true;
}
function isDouble(txt, evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 45) {
		return false;
	}
    if (charCode == 46) {
    	if (txt.value.indexOf('.') === -1) {
            return true;
        } else {
            return false;
        }
    } else {
        if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    }
    return true;
}
</script>

<div class="right_col" role="main">
<div class="clearfix"></div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="btn btn-success" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: center;">File
						format is not valid!</h4>
				</div>
				<div class="modal-body">
					<p id="errMess" style="text-align: center;"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	<c:if test="${modalPackageStatus eq 'ok'}">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Create Recommendation</h2>
					
					<ul class="nav navbar-right panel_toolbox">
						
					</ul>
					<a title="" href="" id="datasetDemo"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>	</a>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<form id="uploadForm"
						action="${pageContext.request.contextPath}/${language}/portal/file/processUpload"
						method="post" enctype="multipart/form-data">
							<input type="text" id="txtRecordCount" name="txtRecordCount"
								style="display: none;" />
							<div class="file-upload" >
								<div class="file-select">
									<div class="file-select-button" id="fileName">Choose File</div>
									<div class="file-select-name" id="noFile">No file
										chosen...</div>
									<input type="file" name="file" id="file" accept=".csv" required />
									
								</div>
							</div>
							
						<div class="row" style="margin-left: 3px;">
							<h4>
								Validation Process <span id="vstatus"></span>
							</h4>
							<div class="progress">
								<div id="vbar" class="progress-bar progress-bar-success"
									data-transitiongoal="0"></div>
							</div>
						</div>
						<div class="row" style="margin-left: 3px;">
							<h4>
								Upload Process <span id="status"></span>
							</h4>
							<div class="progress">
								<div id="pbar" class="progress-bar progress-bar-success"
									data-transitiongoal="0"></div>
							</div>
						</div>
						<div class="row">

							<div class="col-md-6 col-sm-6 col-xs-12" style="width: 60%">
								<h4>Recommendation Name <span style="color:red;" class="required">*</span></h4>
								<input id="txtRecommendationName"
									class="form-control col-md-7 col-xs-12" type="text"
									name="txtRecommendationName" required="required" maxlength="500">
							</div>

						</div>
						<br />
						<div class="row"
							style="font-size: 16px; font-weight: 500; margin-left: 0px;">
							<div style="position: relative;">
								<input id="submitCheck" name="submitCheck" type="checkbox"
									style="width: 20px; height: 20px; position: relative; top: 6px;" />&nbsp;
								&nbsp; Do not run after create recommendation
							</div>
							&nbsp; &nbsp;
						</div>
						<div class="row" id="divTimerCheckBox"
							style="font-size: 16px; font-weight: 500; margin-left: 0px;  display: none;">
							<div style="position: relative;">
								<input id="timeCheck" name="timeCheck" type="checkbox"
									style="width: 20px; height: 20px; position: relative; top: 6px;" />&nbsp;
								&nbsp; Set timer for recommendation
							</div>
							&nbsp; &nbsp;
						</div>
						<div id="divTimer" class="row" style="display: none;">
							<div class="col-md-6 col-sm-6 col-xs-12" style="width: 30%;">
								<h4>Timer</h4>
								<input type="text" id="txtDate" name="txtDate"
									class="form-control col-md-7 col-xs-12" readonly="readonly" required="required"/>
							</div>
						</div>
						<br /> <br />
						<div class="row"
							style="font-size: 16px; font-weight: 500; margin-left: 0px;">
							
								<a style="text-decoration: underline; cursor: pointer;" data-toggle="collapse" data-target="#advanceDiv">Advanced</a>
						
							&nbsp; &nbsp;
						</div>
						<br /> <br />
						<div id="advanceDiv" class="row collapse" aria-expanded="false">
							<div class="row">
								<div class="col-md-6 col-sm-6 col-xs-12" style="width: 30%;">
									<h4 style="margin-left: 10px;">Number of Item <span style="color:red;" class="required">*</span></h4>
									<input style="margin-left: 10px;" type="text" id="txtNumOfItem" name="txtNumOfItem"
										class="form-control col-md-7 col-xs-12" value="10" onkeypress="return isNumberKey(this, event);" required="required" maxlength="10"/>
								</div>
								<div class="col-md-6 col-sm-6 col-xs-12" style="width: 30%;">
									<h4>Threshold <span style="color:red;" class="required">*</span></h4>
									<input type="text" id="txtThreshold" name="txtThreshold"
										class="form-control col-md-7 col-xs-12" value="${threshold}" onkeypress="return isDouble(this, event);" required="required" maxlength="10"/>
										<label id="thresholdMessage" style="float: right; color: red; display: none;">Threshold must be a decimal and not greater than 1</label>
								</div>
							</div>
							<br /> <br />
							<div class="row"
							style="font-size: 16px; font-weight: 500; margin-left: 10px;">
							<div style="position: relative;">
								<input id="runEvaluation" name="runEvaluation" type="checkbox"
									style="width: 20px; height: 20px; position: relative; top: 6px;" />&nbsp;
								&nbsp; Run Evaluation
							</div>
						</div>
						</div>
						<br /> <br />
						<div class="row">
							<input id="btnCreate" name="submit" class="btn btn-success"
								value="Create Recommendation" type="submit" disabled="disabled" />
						</div>
					</form>
				</div>
			</div>
		</div>
	<div class="clearfix"></div>
	</div>
	</c:if>
	<c:if test="${modalPackageStatus eq 'overPackage1'}">
		<div class="alert alert-warning alert-dismissible fade in" role="alert" style="text-align: center;">
	        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
	        </button>
	        <h2>You have used up 1 free Recommendation of Free Trial package already. Please upgrade your package to create more Recommendation!</h2>
        </div>
        <div class="text-center">
	        <a href="<c:url value='/${language}/portal/account/packageDetail' />">
			<button class="btn btn-success"><i class="fa fa-credit-card"></i>  Go to upgrade package page</button>
			</a>
        </div>
	</c:if>
	<c:if test="${modalPackageStatus eq 'overPackage2'}">
		<div class="alert alert-warning alert-dismissible fade in" role="alert" style="text-align: center;">
	        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
	        </button>
	        <h2>You have used up 30 free Recommendation of Standard package already. Please upgrade your package to create more Recommendation!</h2>
        </div>
        <div class="text-center">
	        <a href="<c:url value='/${language}/portal/account/packageDetail' />">
			<button class="btn btn-success"><i class="fa fa-credit-card"></i>  Go to upgrade package page</button>
			</a>
        </div>
	</c:if>
	<c:if test="${modalPackageStatus eq 'expiredPackage2'}">
		<div class="alert alert-warning alert-dismissible fade in" role="alert" style="text-align: center;">
	        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
	        </button>
	        <h2>Your account expired today. Please extend your account or upgrade your package to create more Recommendation and keep on use ours services!</h2>
        </div>
        <div class="text-center">
	        <a href="<c:url value='/${language}/portal/account/packageDetail' />">
			<button class="btn btn-success"><i class="fa fa-credit-card"></i>  Go to upgrade package page</button>
			</a>
        </div>
	</c:if>
	<c:if test="${modalPackageStatus eq 'expiredPackage3'}">
		<div class="alert alert-warning alert-dismissible fade in" role="alert" style="text-align: center;">
	        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
	        </button>
	        <h2>Your account expired today. Please extend your account or upgrade your package to create more Recommendation and keep on use ours services!</h2>
        </div>
        <div class="text-center">
	        <a href="<c:url value='/${language}/portal/account/packageDetail' />">
			<button class="btn btn-success"><i class="fa fa-credit-card"></i>  Go to upgrade package page</button>
			</a>
        </div>
	</c:if>
</div>
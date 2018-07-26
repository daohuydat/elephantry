<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- page content -->
<div class="right_col" role="main">
          
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Feedback <small></small></h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">

                    <form class="form-horizontal form-label-left" action="${pageContext.request.contextPath}/${language}/portal/home/feedback" method="POST">
               
                      <div class="item form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12" for="email">Feedback message <span style="color:red;" class="required">*</span>
                        </label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <textarea id="txtfeedback" name="txtfeedback" class="form-control" rows="10" placeholder="Help us more complete" maxlength="2000"></textarea>
                        </div>
                      </div>
                      
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-md-offset-2">
                          <button id="sendFeedback" type="submit" class="btn btn-success" disabled="disabled">Send Feedback</button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
<script>
var successMessage = '${successMessage}';
</script>
        <!-- /page content -->
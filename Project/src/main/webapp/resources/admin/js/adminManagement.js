$(function() {
	if (successMessage==="done"){
		$.notifyDefaults({
			placement: {
				from: "top",
				align: "center"
			},
			animate:{
				enter: "animated fadeInUp",
				exit: "animated fadeOutDown"
			}
		});
		$.notify({
			placement: {
				from: "top",
				align: "center"
			},	
			// options
			icon: 'fa fa-paw',
			message: 'New Admin has been created !!!' 
		},{
			// settings
			type: 'success'
		});
		history.pushState(null,null,location.href.substring(0,location.href.indexOf("?")));
	}
	$.LoadingOverlaySetup({
	    image           : "/elephantry/resources/images/source.gif",
	    minSize         : "400px" 
	});
	var $txtSearch = $("#txtSearch");
	var $tblAdminBody = $("#tbl-admin tbody");
	var $pgtAdmin = $("#pgt-admin");
	var $loading = $("#loadingOverlay");
	var baseUrl = rootBaseUrl;
	searchAdmin($txtSearch.val(),1);
	$txtSearch.keyup($.debounce(1000,function(){
		searchAdmin($txtSearch.val(),1);
	}));
	function updateAdmin(userId, action, $this){
		$.ajax({
			url: baseUrl + "/admin/adminManagement/updateStatus",
			type: "POST",
			dataType: "json",
			cache: false,
			data: {
				"userId": userId,
				"action": action
			},
			success: function(res) {
				if (res.success) {
					if(res.active===1){
						$this.val("Deactivate");
						$this.removeClass("btn-success").addClass("btn-danger");
					}else{
						$this.val("Activate");
						$this.removeClass("btn-danger").addClass("btn-success");
					}
				}else{
					console.log("modal: user not found");
				}
			}
		});
	}
	
	function searchAdmin(q,pageNum) {
		$loading.LoadingOverlay("show");
		$.ajax({
			url: baseUrl + "/admin/adminManagement/search",
			type: 'GET',
			dataType: "json",
			cache: false,
			data: {
				"q": q,
				"pageNum": pageNum
			},
			success: function(res){
				var html = '';
				for (var i = 0; i < res.users.length; i++) {
					html += buildHTMLResulTableRow(res.users[i]);
				}
				$tblAdminBody.html(html);
				$tblAdminBody.find("input[type='button']").click(function() {
					updateAdmin($(this).attr("data-userId"),$(this).val(),$(this));
				});
				var numOfPage = Math.ceil(res.adminCount/5.0);
				$pgtAdmin.html(buildHTMLPagination(numOfPage, pageNum));
				$pgtAdmin.find("a").click(function() {
					searchAdmin($txtSearch.val(),$(this).attr("data-page"));
				});
				$loading.LoadingOverlay("hide");
			},
			error: function(err) {
				console.log(err);
				$loading.LoadingOverlay("hide");
			}
		});
	}
	function buildHTMLResulTableRow(user){
		return "<tr>" + "<td class='text-center'>" + user.userId + "</td>"
		 + "<td class='text-center'>" + user.email + "</td>"
		 + "<td class='text-center'>" + user.createdTime + "</td>"
		 + "<td class='text-center'>" + '<input type="button" data-userId="'+user.userId+'" class="btn btn-'+(user.active===1?"danger":"success")+' btn-xs" style="width:90px;" value="'+(user.active===1?"Deactivate":"Activate")+'"/>' + "</td>"
		 + "</tr>";
	}
	function buildHTMLPagination(numOfPage, page){
		var pag = "";
		if (numOfPage<=1) {
			pag = "";
		}else{
			for (var i = 1; i <= numOfPage; i++) {
				if (page==i) {
					pag += '<li class="active"><a href="#" data-page="'+i+'">'+i+'</a></li>';
				}else{
					pag += '<li><a href="#" data-page="'+i+'">'+i+'</a></li>';
				}
			}
		}
		return pag;
	}
});
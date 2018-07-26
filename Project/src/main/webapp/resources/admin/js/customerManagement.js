$(function() {
	$.LoadingOverlaySetup({
	    image           : "/elephantry/resources/images/source.gif",
	    minSize         : "400px" 
	});
	var $txtSearch = $("#txtSearch");
	var $txtSearchEmail = $("#txtSearchEmail");
	var $tblCustomerBody = $("#tbl-customer tbody");
	var $pgtCustomer = $("#pgt-customer");
	var $loading = $("#loadingOverlay");
	var baseUrl = rootBaseUrl;
	searchCustomer($txtSearchEmail.val(),$txtSearch.val(),1);
	$txtSearchEmail.keyup($.debounce(1000,function(){
			searchCustomer($txtSearchEmail.val(),$txtSearch.val(),1);
	}));
	$txtSearch.keyup($.debounce(1000,function(){
			searchCustomer($txtSearchEmail.val(),$txtSearch.val(),1);
	}));
	function updateCustomer(customerId, action, $this){
		$.ajax({
			url: baseUrl + "/admin/customerManagement/updateStatus",
			type: "POST",
			dataType: "json",
			cache: false,
			data: {
				"customerId": customerId,
				"action": action
			},
			success: function(res) {
				if (res.success) {
					if(res.status===1){
						$this.val("Deactivate");
						$this.removeClass("btn-success").addClass("btn-danger");
					}else{
						$this.val("Activate");
						$this.removeClass("btn-danger").addClass("btn-success");
					}
				}else{
					console.log("modal: customer not found");
				}
			}
		});
	}
	function searchCustomer(email,keyword,pageNum) {
		$loading.LoadingOverlay("show");
		$.ajax({
			url: baseUrl + "/admin/customerManagement/search",
			type: 'GET',
			dataType: "json",
			cache: false,
			data: {
				"email": email,
				"keyword":  keyword,
				"pageNum": pageNum
			},
			success: function(res){
				var html = '';
				for (var i = 0; i < res.customers.length; i++) {
					html += buildHTMLResulTableRow(res.customers[i]);
				}
				$tblCustomerBody.html(html);
				$tblCustomerBody.find("input[type='button']").click(function() {
					updateCustomer($(this).attr("data-customerId"),$(this).val(),$(this));
				});
				var numOfPage = Math.ceil(res.customerCount/5.0);
				$pgtCustomer.html(buildHTMLPagination(numOfPage, pageNum));
				$pgtCustomer.find("a").click(function() {
					searchCustomer($txtSearchEmail.val(),$txtSearch.val(),$(this).attr("data-page"));
				});
				$loading.LoadingOverlay("hide");
			},
			error: function(err) {
				console.log(err);
				$loading.LoadingOverlay("hide");
			}
		});
	}
	function buildHTMLResulTableRow(customer){
		console.log(customer.phone,customer.firstName);
		return "<tr>" + "<td  class='text-center'>" + customer.customerId + "</td>"
		 + "<td class='text-center'>" + customer.firstName + " " + customer.lastName + "</td>"
		 + "<td class='text-center'>" + customer.email + "</td>"
		 + "<td class='text-center'>" + customer.gender + "</td>"
		 + "<td class='text-center'>" + customer.dob + "</td>"
		 + "<td class='text-center'>" + customer.website + "</td>"
		 + "<td class='text-center'>" + customer.company + "</td>"
		 + "<td class='text-center'>" + customer.phone + "</td>"
		 + "<td class='text-center'>" + customer.province + "</td>"
		 + "<td class='text-center'>" + customer.package1 + "</td>"
		 + "<td class='text-center'>" + customer.address + "</td>"
		 + "<td class='text-center'>" + '<input type="button" data-customerId="'+customer.customerId+'" class="btn btn-'+(customer.active===1?"danger":"success")+' btn-xs" style="width:82px; font-size:12.5px; " value="'+(customer.active===1?"Deactivate":"Activate")+'"/>' + "</td>"
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
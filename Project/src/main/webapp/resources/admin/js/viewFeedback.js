$(function() {
	var $loading = $("#loadingOverlay");
	var $tblBodyReadFeedback = $("#tbl-read-feedback tbody");
	var $tblBodyUnreadFeedback = $("#tbl-unRead-feedback tbody");
	var $pagiRead = $("#pgt-read-feedback");
	var $pagiUnread = $("#pgt-unRead-feedback");
	var $ddlunReadDate = $("#ddl-unReadDate");
	var $ddlReadDate = $("#ddl-readDate");
	var baseUrl = rootBaseUrl;
	filterUnReadFeedback($ddlunReadDate.val(),1);
	$ddlReadDate.change(function() { /* tab da doc*/
		filterReadFeedback($ddlReadDate.val(),1);
	});
	$ddlunReadDate.change(function() {/* tab chua doc*/
		filterUnReadFeedback($ddlunReadDate.val(),1);
	});
	$("#read-tabb").click(function() {/* tab da doc*/
		filterReadFeedback($ddlReadDate.val(),1);
	})
	$("#unread-tabb").click(function() {/* tab chua doc*/
		filterUnReadFeedback($ddlunReadDate.val(),1);
	})
	
	function markAsUnRead(feedbackId, day, pageNum) {
		console.log("markAsUnRead");
		$.ajax({
			url: baseUrl+"/admin/home/feedback/update",
			type: "POST",
			dataType: "json",
			cache: false,
			data:{
				"feedbackId": feedbackId,
				"action": "unRead",
				"txtSortDesc": day,
				"pageNum": pageNum
			},
			success: function(res) {/* hien thi nhung cai DA DOC*/
				if (res.success) {
					var html='';
					for (var i = 0; i < res.feedbacks.length; i++) {
						html+=buildHTMLResulTableRowRead(res.feedbacks[i]);
					}
					$tblBodyReadFeedback.html(html);
					$tblBodyReadFeedback.find("input[type='button']").click(function() {
						markAsUnRead($(this).attr("data-feedbackId"),day,pageNum);
					});
					$tblBodyReadFeedback.find("tr").click(function(evt) {
						openModal(evt,$(this).attr("data-feedbackEmail"),$(this).attr("data-feedbackId"),$(this).attr("data-feedbackName"),$(this).attr("data-feedbackPhone"),$(this).attr("data-feedbackContent"));
					});
					var numOfPage = Math.ceil(res.countFeedback/10.0);
					$pagiRead.html(buildHTMLPagination(numOfPage, pageNum));
					$pagiRead.find("a").click(function() {
						filterReadFeedback($ddlReadDate.val(),$(this).attr("data-page"));
					});
				}else{
					console.log("Some thing wrong")
				}
			}
		})
	}
	function filterReadFeedback(fewDay,pageNum){
		$.ajax({
			url: baseUrl+"/admin/home/feedback/read/filter",
			type: "GET",
			dataType: "json",
			cache: false,
			data:{
				"txtSortDesc": fewDay,
				"pageNum": pageNum
			},
			success: function(res) {
				var html='';
				for (var i = 0; i < res.feedbacks.length; i++) {
					html+=buildHTMLResulTableRowRead(res.feedbacks[i]);
				}
				$tblBodyReadFeedback.html(html);
				$tblBodyReadFeedback.find("input[type='button']").click(function() {
					markAsUnRead($(this).attr("data-feedbackId"),fewDay,pageNum);
				});
				$tblBodyReadFeedback.find("tr").click(function(evt) {
					openModal(evt,$(this).attr("data-feedbackEmail"),$(this).attr("data-feedbackId"),$(this).attr("data-feedbackName"),$(this).attr("data-feedbackPhone"),$(this).attr("data-feedbackContent"));
				});
				var numOfPage = Math.ceil(res.countFeedback/10.0);
				$pagiRead.html(buildHTMLPagination(numOfPage, pageNum));
				$pagiRead.find("a").click(function() {
					filterReadFeedback($ddlReadDate.val(),$(this).attr("data-page"));
				});
//				$loading.LoadingOverlay("hide");
			},
			error: function(err) {
				console.log(err);
//				$loading.LoadingOverlay("hide");
			}
		});
	}
	function markAsRead(feedbackId, day, pageNum) {
		
		$.ajax({
			url: baseUrl+"/admin/home/feedback/update",
			type: "POST",
			dataType: "json",
			cache: false,
			data:{
				"feedbackId": feedbackId,
				"action": "read",
				"txtSortDesc": day,
				"pageNum": pageNum
			},
			success: function(res) {
//				console.log("markAs Read");
//				console.log(res);
				if (res.success) {
					var html='';
					for (var i = 0; i < res.feedbacks.length; i++) {
						html+=buildHTMLResulTableRowUnread(res.feedbacks[i]);
					}
					$tblBodyUnreadFeedback.html(html);
					$tblBodyUnreadFeedback.find("input[type='button']").click(function() {
						markAsRead($(this).attr("data-feedbackId"),day,pageNum);
					});
					$tblBodyUnreadFeedback.find("tr").click(function(evt) {
						openModal(evt,$(this).attr("data-feedbackEmail"),$(this).attr("data-feedbackId"),$(this).attr("data-feedbackName"),$(this).attr("data-feedbackPhone"),$(this).attr("data-feedbackContent"));
						markAsRead($(this).attr("data-feedbackId"),day,pageNum);
					});
					var numOfPage = Math.ceil(res.countFeedback/10.0);
					$pagiUnread.html(buildHTMLPagination(numOfPage, pageNum));
					$pagiUnread.find("a").click(function() {
						filterUnReadFeedback($ddlunReadDate.val(),$(this).attr("data-page"));
					});
				}else{
					console.log("Some thing wrong")
				}
			}
		})
	}
	function filterUnReadFeedback(fewDay,pageNum){
		$.ajax({
			url: baseUrl+"/admin/home/feedback/unRead/filter",
			type: "GET",
			dataType: "json",
			cache: false,
			data:{
				"txtSortDesc": fewDay,
				"pageNum": pageNum
			},
			success: function(res) {
				var html='';
				for (var i = 0; i < res.feedbacks.length; i++) {
					html+=buildHTMLResulTableRowUnread(res.feedbacks[i]);
				}
				$tblBodyUnreadFeedback.html(html);
				$tblBodyUnreadFeedback.find("input[type='button']").click(function() {
					markAsRead($(this).attr("data-feedbackId"),fewDay,pageNum);
				});
				$tblBodyUnreadFeedback.find("tr").click(function(evt) {
					openModal(evt,$(this).attr("data-feedbackEmail"),$(this).attr("data-feedbackId"),$(this).attr("data-feedbackName"),$(this).attr("data-feedbackPhone"),$(this).attr("data-feedbackContent"));
					markAsRead($(this).attr("data-feedbackId"),fewDay,pageNum);
				});
				var numOfPage = Math.ceil(res.countFeedback/10.0);
				$pagiUnread.html(buildHTMLPagination(numOfPage, pageNum));
				$pagiUnread.find("a").click(function() {
					filterUnReadFeedback($ddlunReadDate.val(),$(this).attr("data-page"));
				});
//				$loading.LoadingOverlay("hide");
			},
			error: function(err) {
				console.log(err);
//				$loading.LoadingOverlay("hide");
			}
		});
	}
	
	
	function buildHTMLResulTableRowRead(feedback){
		var cont = "";
		if (feedback.content.length>50) {
			cont = feedback.content.substring(0,100) + " ...";
		}else{
			cont = feedback.content;
		}
		return "<tr style='cursor: pointer;' data-feedbackEmail='"+feedback.email+"' data-feedbackId='"+feedback.feedbackId+"' data-feedbackName='"+feedback.customerName+"' data-feedbackPhone='"+feedback.phone+"' data-feedbackContent='"+feedback.content+"' >"
		 + "<td  class='text-center'>" + feedback.customerName + "</td>"
		 + "<td class='text-center'>" + cont+"</td>"
		 + "<td class='text-center'>" + feedback.createdTime + "</td>"
		 + "<td class='text-center'>" + '<input type="button" data-feedbackId="'+feedback.feedbackId+'" class="btn btn-success" value="Mark as unread"/>' + "</td>"
		 + "</tr>";
	}
	function buildHTMLResulTableRowUnread(feedback){
		var cont = "";
		if (feedback.content.length>50) {
			cont = feedback.content.substring(0,100) + " ...";
		}else{
			cont = feedback.content;
		}
		return "<tr style='cursor: pointer;' data-feedbackEmail='"+feedback.email+"' data-feedbackId='"+feedback.feedbackId+"' data-feedbackName='"+feedback.customerName+"' data-feedbackPhone='"+feedback.phone+"' data-feedbackContent='"+feedback.content+"' >"
		 + "<td  class='text-center'>" + feedback.customerName + "</td>"
		 + "<td class='text-center'>" + cont+"</td>"
		 + "<td class='text-center'>" + feedback.createdTime + "</td>"
		 + "<td class='text-center'>" + '<input type="button" data-feedbackId="'+feedback.feedbackId+'" class="btn btn-success" value="Mark as read"/>' + "</td>"
		 + "</tr>";
	}
	function buildHTMLPagination(numOfPage, page){
		if (numOfPage<=1) {
			return "";
		}
		var pag = "";
		for (var i = 1; i <= numOfPage; i++) {
			if (page==i) {
				pag += '<li class="active"><a href="#" data-page="'+i+'">'+i+'</a></li>';
			}else{
				pag += '<li><a href="#" data-page="'+i+'">'+i+'</a></li>';
			}
		}
		return pag;
	}
	function openModal(evt,email,id,name,phone,content) {
		var $cell=$(evt.target).closest('td');
	    if( $cell.index()<3){
	    	$("#feedbackHeader").html(""+name);
	    	$("#feedbackContent").html("<a href='tel:"+phone+"'>Dial: "+phone+"</a> <a style='float: right;' href='mailto:"+email+"'>Send an email to: "+email+"</a><hr><p>"+content+"</p>");
	    	$("#myModalRead").modal('show');
	    }
	}
})
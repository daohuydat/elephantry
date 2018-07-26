/**
 * 
 */
$(function() {
	var ids = [];
	var baseUrl = rootBaseUrl;
	var $txtSearch = $("#txtSearch");
	var $detailRecommendation = $("#detailRecommendation");
	var $tblRecommendationCompleted = $("#tbl-recommendationCompleted tbody");
	var $pgtRecommendationCompleted = $("#pgt-recommendationCompleted");
	var $loading = $("#loadingOverlay");
	var dateSearch = $("#reportrange_right").find("span").text();
	var startDate = Date.parse(dateSearch.split("-")[0].trim()).toString(
			'yyyy-MM-dd');
	var endDate = Date.parse(dateSearch.split("-")[1].trim()).toString(
			'yyyy-MM-dd');
	searchRecommendationCompleted(startDate, endDate, $txtSearch.val(), 1);
	$txtSearch.keyup($.debounce(1000, function() {
		searchRecommendationCompleted(startDate, endDate, $txtSearch.val(), 1);
	}));

	function searchRecommendationCompleted(startDate, endDate, keyword, pageNum) {
		$loading.LoadingOverlay("show");
		$.ajax({
			url : baseUrl + "/portal/recommendation/completed/search",
			type : 'GET',
			dataType : "json",
			cache : false,
			data : {
				"startDate" : startDate,
				"endDate" : endDate,
				"keyword" : keyword,
				"pageNum" : pageNum
			},
			success : function(res) {
				var html = '';
				for (var i = 0; i < res.recommendations.length; i++) {
					html += buildHTMLResulTableRow(res.recommendations[i]);
				}
				$tblRecommendationCompleted.html(html);
				var numOfPage = Math.ceil(res.recommendationCount / 5.0);
				console.log(numOfPage);
				$pgtRecommendationCompleted.html(buildHTMLPagination(numOfPage,
						pageNum));
				$pgtRecommendationCompleted.find("a")
						.click(
								function() {
									searchRecommendationCompleted(startDate,
											endDate, $txtSearch.val(), $(this)
													.attr("data-page"));
								});
				$loading.LoadingOverlay("hide");
				linkViewClick();
				$.getScript();
			},
			error : function(err) {
				console.log(err);
				$loading.LoadingOverlay("hide");
			}
		});
	}

	function buildHTMLResulTableRow(recommendation) {
		return "<tr class='even pointer'>"
				+ "<td class='a-center ' style='width: 3%'><input type='checkbox' "
				+ "class='flat checkrecords' name='table_records' value='"
				+ recommendation.recommendationId
				+ "'></td>"
				+ "<td style='width: 5%' >"
				+ recommendation.recommendationId
				+ "</td>"
				+ "<td >"
				+ "<a class='linkDetail' href='#' data-page='"
				+ recommendation.recommendationId
				+ "'>"
				+ recommendation.name
				+ "</a></td>"
				+ "<td style='width: 10%'>"
				+ recommendation.createdTime
				+ "</td>"
				+ "<td style='width: 15%'>"
				+ recommendation.startedTime
				+ "</td>"
				+ "<td style='width: 15%'>"
				+ recommendation.finishedTime
				+ "</td>"
				+ "<td style='width: 10%'>"
				+ recommendation.recommendationStatusId
				+ "</td>"
				// + "<td class=' last centerField'><button data-rid='" +
				// recommendation.recommendationId + "'" +
				// "class='btn-submitonly btn btn-success btn-xs'
				// type='button'>submit</button></td>"
				// + "<td class=' last centerField'><button data-rid='" +
				// recommendation.recommendationId + "'" +
				// "class='btn-deleteonly btn btn-danger btn-xs'
				// type='button'>delete</button></td>"
				+ "<td class=' last centerField' style='width: 10%'><a class='btn btn-success btn-xs' href='"
				+ baseUrl
				+ "/portal/file/download/result/"
				+ recommendation.recommendationId
				+ "' ><i class='fa fa-download fa-lg' style=' margin-left: 10px; margin-right: 10px; text-align: center;'></i></a></td>"
				+ "</tr>";
	}

	function buildHTMLPagination(numOfPage, page) {
		if (numOfPage <= 1) {
			return "";
		}
		var pag = "";
		for (var i = 1; i <= numOfPage; i++) {
			if (page == i) {
				pag += '<a class="btn btn-info active" href="#" data-page="'
						+ i + '">' + i + '</a>';
			} else {
				pag += '<a class="btn btn-info"  href="#" data-page="' + i
						+ '">' + i + '</a>';
			}
		}
		return pag;
	}

	function linkViewClick() {
		$(".linkDetail").on('click', function() {
			viewRecommendation($(this).attr("data-page"));
			$("#modalDetail").modal('show');
		});
	}

	$("#reportrange_right").on(
			"apply.daterangepicker",
			function(a, b) {
				console.log(b.startDate.format("YYYY-MM-DD") + " to "
						+ b.endDate.format("YYYY-MM-DD"));
				startDate = b.startDate.format("YYYY-MM-DD");
				endDate = b.endDate.format("YYYY-MM-DD");
				searchRecommendationCompleted(startDate, endDate, $txtSearch
						.val(), 1);
			});

	function viewRecommendation(id) {
		$
				.ajax({
					url : baseUrl + '/portal/recommendation/viewDetail',
					type : 'POST',
					dataType : 'json',
					cache : false,
					data : {
						'id' : id
					},
					success : function(res) {
						var html = '';
						html += "<div class='row'><div class='col-md-4 blue' style='margin-left: 30px'><h2>"
								+ res.name
								+ ""
								+ "</h2></div></div><br><div class='left col-md-8 col-md-offset-2'><div class='row'><div class='col-md-6'>"
								+ "<div class='row'><p><strong>ID: </strong> <span class='green' style='font-style: italic;'>"
								+ res.recommendationId
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Customer name: </strong><span class='green' style='font-style: italic;'>"
								+ res.customer
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Priority: </strong><span class='green' style='font-style: italic;'>"
								+ res.priorityName
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Number of Item: </strong><span class='green' style='font-style: italic;'>"
								+ res.numOfItem
								+ "</span></p></div><div class='row'><div class='col-md-6'><p><strong>Estimated Duration: </strong><span class='green' style='font-style: italic;'>"
								+ res.estimatedDuration
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Timer: </strong><span class='green' style='font-style: italic;'>"
								+ res.timer
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Record Count: </strong><span class='green' style='font-style: italic;'>"
								+ res.recordCount
								+ ""
								+ "</span></p></div></div><div class='row'><p><strong>Status: </strong><span class='green' style='font-style: italic;'>"
								+ res.recommendationStatusId
								+ ""
								+ "</span></p></div><div class='row'><strong>Type: </strong><span class='green' style='font-style: italic;'> Item Based"
								+ "</span></p></div><div class='row'><p><strong>Threshold: </strong><span class='green' style='font-style: italic;'>"
								+ res.threshold
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Created Date:: </strong><span class='green' style='font-style: italic;'>"
								+ res.createdTime
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Started Time: </strong><span class='green' style='font-style: italic;'>"
								+ res.startedTime
								+ ""
								+ "</span></p></div><div class='row'><p><strong>Finished Time: </strong><span class='green' style='font-style: italic;'>"
								+ res.finishedTime
								+ ""
								+ "</span></p></div></div></div></div>"
						$detailRecommendation.html(html);
					},
					error : function(err) {
						console.log(err);
						location.reload();
					}
				});
	}

});
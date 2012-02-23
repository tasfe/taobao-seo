(function() {
	$(".cancel-job-link").click(function() {
		var $tr = $(this).closest("tr");
		var numIid = $tr.attr("num_iid");
		$.ajax({
			url:"listing/cancel-job",
			data: {numIid: numIid},
			success: function()
			{
				$tr.remove();
			}
		});
		return false;
	});
})();

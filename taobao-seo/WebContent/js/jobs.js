(function() {
	$('a.change-job-link').click(function(){
		var $tr = $(this).closest("tr");
		$('.editor', $tr).show();
		var dayOfWeek = $('td.list-time', $tr).attr('day-of-week');
		$('option', $tr).removeAttr('selected');
		$('option[value="' + dayOfWeek + '"]', $tr).attr('selected', 'selected');
		return false;
	});
	
	$('button.ok').click(function(){
		var $editor = $(this).closest('.editor');
		var dayOfWeek = $('select', $editor).val();
		var time = $('input', $editor).val();
		var numIid = $(this).closest("tr").attr("num_iid");
		$.ajax({
			url: 'listing/schedule-listing',
			data: {numIids: numIid, dayOfWeek: dayOfWeek, time: time},
			type: 'POST',
			success: function(data) {
				$tr = $editor.closest('tr');
				refresh($tr);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
				var headers = jqXHR.getAllResponseHeaders();
				alert(headers);
				alert(errorThrown);
			}
		});
		return false;
	});
	
	$('button.cancel').click(function(){
		var $editor = $(this).closest('.editor');
		$editor.hide();
		return false;
	});
	
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
	
	function refresh($tr)
	{
		$.ajax({
			url: 'listing/jobs',
			type: 'POST',
			success: function(data) {
				$('.listing .content').html(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
				var headers = jqXHR.getAllResponseHeaders();
				alert(headers);
				alert(errorThrown);
			}
		});
	}
})();

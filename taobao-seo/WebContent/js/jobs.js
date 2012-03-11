(function() {
	function selectionChanged($row)
	{
		var $table = $row.closest('table');
		var selectedItems = $table.data('items');
		if (!selectedItems)
		{
			selectedItems = [];
		}
		var num_iid = $row.attr("num_iid");
		var selected = $("input", $row).attr("checked");
		if (selected)
		{
			var found = $.grep(selectedItems, function(a){
				return a == num_iid;
			}, false);
			if (found.length == 0)
			{
				selectedItems.push(num_iid);
			}
		}
		else
		{
			selectedItems = $.grep(selectedItems, function(a){
				return a == num_iid;
			}, true);
		}
		$table.data('items', selectedItems);
	}
	
	$("#jobs-table").mytable({selectionChanged: selectionChanged});
	
	$('.editor input[name="time"]').timepicker({});
	
	$("#adjust-dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 400
	});
	
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
				refresh();
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
			data: {numIids: numIid},
			success: function()
			{
				$tr.remove();
			}
		});
		return false;
	});
	
	$('#jobs button.batch-change').click(function(){
		var $table = $("#jobs-table");
		var items = $table.data('items');
		if (!items || items.length == 0)
		{
			alert('未选中项目。');
			return false;
		}
		var $dialog = $("#adjust-dialog");
		$dialog.dialog("option", "buttons", {
			确定: function() {
				var dayOfWeek = $('form select', $dialog).val();
				var time = $('form input[name="time"]', $dialog).val();
				$.ajax({
					url: 'listing/schedule-listing',
					data: {numIids: items.join(), dayOfWeek: dayOfWeek, time: time},
					type: 'POST',
					success: function(data){
						$dialog.dialog('close');
						refresh();
					}
				});
				return false;
			},
			取消: function() {
				$dialog.dialog('close');
				return false;
			}
		});
		$dialog.dialog("open");
		return false;
	});
	
	$('button.batch-cancel').click(function(){
		var $table = $("#jobs-table");
		var items = $table.data('items');
		if (!items || items.length == 0)
		{
			alert('未选中任务。');
			return false;
		}
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url: 'listing/cancel-job',
			data: {numIids: items.join()},
			type: 'POST',
			success: function(data) {
				refresh();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
				var headers = jqXHR.getAllResponseHeaders();
				alert(headers);
				alert(errorThrown);
			}
		});
	});
	
	function refresh()
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

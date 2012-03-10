(function() {
	
	function selectionChanged(row)
	{
		var $table = $("table.listing-items");
		var selectedItems = $table.data('items');
		if (!selectedItems)
		{
			selectedItems = [];
		}
		var num_iid = row.attr("num_iid");
		var selected = $("input", row).attr("checked");
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
	
	$("table.listing-items").mytable({selectionChanged: selectionChanged});
	
	$("#adjust-dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 400
	});

	$('a.adjust-link, a.change-job').click(function(){
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
	
	$('a.cancel-job').click(function(){
		var $tr = $(this).closest("tr");
		var numIid = $tr.attr("num_iid");
		$.ajax({
			url: 'listing/cancel-job',
			data: {numIids: numIid},
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
	
	$('button.well-distribute').click(function()
	{
		var $table = $("table.listing-items");
		var dayOfWeek = $table.attr('day-of-week');
		var hour = $table.attr('hour');
		var expected = $table.attr('expected');
		$.ajax({
			url: 'listing/well-distribute',
			data: {
				'listHour.dayOfWeek': dayOfWeek, 
				'listHour.hour': hour,
				expected: expected},
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
	});
	
	$('button.batch-change').click(function(){
		var $table = $("table.listing-items");
		var items = [];
		$('tbody tr', $table).each(function(){
			var numIid = $(this).attr('num_iid');
			items.push(numIid); 
		});
	});
	
	$('button.batch-cancel').click(function(){
		var $table = $("table.listing-items");
		var items = [];
		$('tbody tr', $table).each(function(){
			var numIid = $(this).attr('num_iid');
			items.push(numIid); 
		});
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
		var $table = $('table.listing-items');
		var dayOfWeek = $table.attr('day-of-week');
		var hour = $table.attr('hour');
		var expected = $table.attr('expected');
		$.ajax({
			url: 'listing/hour-items',
			data: {
				expected: expected, 
				'listHour.dayOfWeek': dayOfWeek, 
				'listHour.hour': hour},
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
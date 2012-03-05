(function() {
	$("#dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 1000
	});
	
	$("table.grid a").click(function() {
		var url = $(this).attr('href');
		$.ajax({
			url:url,
			success: function(data)
			{
				$('.listing .content').html(data);
			}
		});
		return false;
	});
	
	$('#period-form select[name="period"]').change(function(){
		var period = $(this).val();
		$.ajax({
			url:'listing/period-view',
			data:{period: period},
			success: function(data)
			{
				$('.listing .content').html(data);
			}
		});
	});
	
	
	$('#all-to-period7').button().click(function(){
		$.ajax({
			url:'listing/change-period',
			success: function()
			{
				$.ajax({
					url:'listing/period-view',
					data:{period: 7},
					success: function(data)
					{
						$('.listing .content').html(data);
					}
				});
			}
		});
		return false;
	});
	
	$('#well-distribute-all').button().click(function(){
		$.ajax({
			url:'listing/change-period',
			success: function()
			{
				$.ajax({
					url:'listing/period-view',
					data:{period: 7},
					success: function(data)
					{
						$('.listing .content').html(data);
					}
				});
			}
		});
		return false;
	});
	
	$('td').hover(function(){
		$('.tools', this).show();
	}, function(){
		$('.tools', this).hide();
	});
	
	$('.distribute-tool').click(function(e){
		alert("well distribute thid period.");
		return false;
	});
	
	$('.add-tool').click(function(e){
		var $td = $(this).closest('td');
		var time = $td.attr('time');
		var dayOfWeek = $td.attr('day-of-week');
		$.ajax({
			url: 'listing/items-selector',
			type: 'POST',
			success: function(data) {
				var $dialog = $('#dialog');
				$dialog.html(data);
				$dialog.dialog('option', 'buttons', {
					确定 : function(){
						var selectedItems = '';
						var selectedRows = $('#selected-items tr', $dialog);
						selectedRows.each(function(){
							selectedItems = selectedItems + $(this).attr('num_iid') + ',';
						});
						$.ajax({
							url: 'listing/schedule-listing',
							data: {numIids: selectedItems, dayOfWeek: dayOfWeek, time: time},
							type: 'POST',
							success: function(data) {
								$dialog.dialog('close');
							},
							error: function(jqXHR, textStatus, errorThrown) {
								alert(textStatus);
								var headers = jqXHR.getAllResponseHeaders();
								alert(headers);
								alert(errorThrown);
							}
						});
					},
					取消 : function(){
						$dialog.dialog('close');
					}
				});
				$dialog.dialog('open');;
			}
		});
		return false;
	});
})();

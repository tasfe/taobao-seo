(function() {
	$("#dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 1000
	});
	
	$("table.grid a").click(function() {
		var url = $(this).attr('href');
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url:url,
			success: function(data)
			{
				$content.html(data);
			}
		});
		return false;
	});
	
	$('#period-form select[name="period"]').change(function(){
		var period = $(this).val();
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url:'listing/period-view',
			data:{period: period},
			success: function(data)
			{
				$content.html(data);
			}
		});
	});
	
	
	$('#all-to-period7').button().click(function(){
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url:'listing/change-period',
			success: function()
			{
				$.ajax({
					url:'listing/period-view',
					data:{period: 7},
					success: function(data)
					{
						$content.html(data);
					}
				});
			}
		});
		return false;
	});
	
	$('#well-distribute-all').button().click(function(){
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url:'listing/well-distribute',
			success: function()
			{
				
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
		var $td = $(this).closest('td');
		var dayOfWeek = $td.attr('day-of-week');
		if (!dayOfWeek)
		{
			alert("没有在这个时间段上架或者计划上架的宝贝。");
			return false;
		}
		var hour = $td.attr('hour');
		var $table = $td.closest('table');
		var expected = $table.attr('expected');
		var $content = $('.listing .content');
		window.loading.show($content);
		$.ajax({
			url: 'listing/well-distribute',
			data: {
				'listHour.dayOfWeek': dayOfWeek, 
				'listHour.hour': hour,
				expected: expected},
			type: 'POST',
			success: function(data){
				$content.html(data);
			}
		});
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

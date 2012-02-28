(function() {
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
	
	$('td').hover(function(){
		$('.add-tool', this).show();
	}, function(){
		$('.add-tool', this).hide();
	});
	
	$('.add-tool').click(function(){
		alert("Add items to this period.");
	});
})();

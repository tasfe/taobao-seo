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
})();

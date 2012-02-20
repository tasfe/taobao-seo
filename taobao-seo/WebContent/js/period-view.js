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
})();

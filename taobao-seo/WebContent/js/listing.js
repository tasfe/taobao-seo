(function() {
	$(".tabs>a").click(function() {
		$(".tabs>a").removeClass('current');
		$(this).addClass('current');
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

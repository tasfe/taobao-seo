(function() {
	window.loading = {
		show: function($content)
		{
			$content.html('loadding...');
		}
	};
	
	$(".tabs>a").click(function() {
		$(".tabs>a").removeClass('current');
		$(this).addClass('current');
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
	
})();

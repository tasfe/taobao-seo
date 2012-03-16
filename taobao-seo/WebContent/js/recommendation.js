(function() {
	$("#recommendation-status .start").button({
		icons: {
			primary: "ui-icon-play"
		}
	}).click(function() {
		if ( $( this ).text() === "启动" ) {
			$.ajax({
				url: 'recommendation/start',
				success: function( data ) {
					$('#recommendation-status .msg-running').show();
					$('#recommendation-status .msg-stopped').hide();
					$("#recommendation-status .start").button( "option", {
						label: "停止",
						icons: {
							primary: "ui-icon-pause"
						}
					});
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		} else {
			$.ajax({
				url: 'recommendation/stop',
				success: function( data ) {
					$('#recommendation-status .msg-running').hide();
					$('#recommendation-status .msg-stopped').show();
					$("#recommendation-status .start").button( "option", {
						label: "启动",
						icons: {
							primary: "ui-icon-play"
						}
					});
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		}
	});
	
	$('#recommendation-options .scope input[name="scope"]').change(function(){
		var scope = $(this).val();
		var form = $(this).closest('form');
		if (scope == 2)
		{
			$('input[name="keyword"]', form).removeAttr('disabled');
		}
		else
		{
			$('input[name="keyword"]', form).attr('disabled', 'disabled');
		}
		
		if (scope == 3)
		{
			$('div.item-selector', form).show();
		}
		else
		{
			$('div.item-selector', form).hide();
		}
	});
})();

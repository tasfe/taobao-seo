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
})();

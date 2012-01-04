(function() {
	$("#start").button({
		icons: {
			primary: "ui-icon-play"
		}
	}).click(function() {
		if ( $( this ).text() === "启动" ) {
			$.ajax({
				url: 'start',
				success: function( data ) {
					$('#msg-running').show();
					$('#msg-stopped').hide();
					$("#start").button( "option", {
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
				url: 'stop',
				success: function( data ) {
					$('#msg-running').hide();
					$('#msg-stopped').show();
					$("#start").button( "option", {
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

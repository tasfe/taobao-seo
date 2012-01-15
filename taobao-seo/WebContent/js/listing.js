(function() {
	$("#listing .start").button({
		icons: {
			primary: "ui-icon-play"
		}
	}).click(function() {
		if ( $( this ).text() === "启动" ) {
			$.ajax({
				url: 'listing/start',
				success: function( data ) {
					$('#listing .msg-running').show();
					$('#listing .msg-stopped').hide();
					$("#listing .start").button( "option", {
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
					$('#listing .msg-running').hide();
					$('#listing .msg-stopped').show();
					$("#listing .start").button( "option", {
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
	
	var itemData;
	$.getJSON('listing/items', function(data) {
			alert(data.items_onsale_get_response.items.item[0].title);
			itemData = data;
		}
	);
	
	var millisPerDay = 24 * 60 * 60000;
	var now = new Date();
	now.setUTCHours(0, 0, 0, 0);
	var time = now.getTime();
	var d = [ [time - 6 * millisPerDay, 84.36], 
	          [time - 5 * millisPerDay, 31.85], 
	          [time - 4 * millisPerDay, 38.74], 
	          [time - 3 * millisPerDay, 381.15], 
	          [time - 2 * millisPerDay, 82.38], 
	          [time - millisPerDay, 383.94], 
	          [time, 85.44]];
	var from = d[0][0] - millisPerDay;
	var to = time + millisPerDay;
	$.plot($("#chart"), 
		[{
			data: d,            
			bars: { 
				show: true,
				lineWidth: 30,
				align: "left"
			}
		}], 
		{ 
			xaxis: { 
				mode: "time",
				timeformat: "%m-%d",
				min: from,
				max: to
			}
		}
	);
})();

(function() {
	
	function selectionChanged(row)
	{
		var num_iid = row.attr("num_iid");
		var selected = $("input", row).attr("checked");
		if (selected)
		{
			var found = $.grep(selectedItems, function(a){
				return a == num_iid;
			}, false);
			if (found.length == 0)
			{
				selectedItems.push(num_iid);
			}
		}
		else
		{
			selectedItems= $.grep(selectedItems, function(a){
				return a == num_iid;
			}, true);
		}
		$(".selection").text("已选中 " + selectedItems.length + " 项");
	}
	
	$("#items-table").mytable({selectionChanged: selectionChanged});
	$(".add-label-link").click(function(){
		var currentItem = $(this).closest("tr").attr("num_iid");
		var $dialog = $("#label-dialog");
		var url = "merging.action";
		var q = "numIids=" + currentItem;
		$.ajax({
			url: url,
			data: q,
			type: 'POST',
			success: function(data) {
				$dialog.html(data);
				$dialog.dialog("option", "buttons", {
					确定: function() {
						var merges = getMerges();
						if (!merges)
						{
							alert("未改变原图。");
							return false;
						}
						showProcessingDialog();
						var url = 'merge.action';
						var q = 'numIids=' + currentItem + merges;
						$.ajax({
							url: url,
							data: q,
							type: 'POST',
							success: function(data){
								hideProcessingDialog();
								if ((data == 'ok'))
								{
									$dialog.dialog('close');
									reload();
								}
								else
								{
									alert(data);
								}
							}
						});
						return false;
					},
					取消: function() {
						$(this).dialog( "close" );
						return false;
					}
				});
				$dialog.dialog("open");
			}
		});
		return false;
	});
	
	$("#adjust-dialog").dialog({
		autoOpen: false,
		modal: true,
		width: 400
	});

	$('.adjust-link').click(function(){
		var numIid = $(this).closest("tr").attr("num_iid");
		$.ajax({
			url: 'listing/adjusting',
			data: {numIid : numIid},
			type: 'POST',
			success: function(data) {
				var $dialog = $('#adjust-dialog');
				$dialog.html(data);
				$dialog.dialog('option', 'buttons', {
					确定 : function(){
						var num = $('input[name="num"]').val();
						var listTime = $('input[name="list_time"]').val();
						$.ajax({
							url: 'listing/schedule-listing',
							data: {numIids: numIid, num: num, listTime: listTime},
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
				$dialog.dialog('open');
			}
		});
	});
})();
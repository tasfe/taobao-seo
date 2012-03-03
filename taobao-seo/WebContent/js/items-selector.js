(function() {
	$(".items-selector button").button();
	var $keyword = $("#keyword");
	$keyword.css('color', '#666');
	$keyword.focus(function() {
		if ($(this).val() == '关键字') {
			$(this).css('color', 'black');
			$(this).val('');
		}
	});
	$keyword.blur(function() {
		if ($(this).val() == '') {
			$(this).val('关键字');
			$(this).css('color', '#666');
		}
	});

	$("#search").click(function(){
		var q = getFilterParameters();
		$("#items").data('q', q);
		loadPage(1);
		return false;
	});

	$("#search-form").submit(function()
	{
		return false;
	});

	$("#selected-items").mytable();

	function getFilterParameters()
	{
		var $form = $("#search-form");
		var cids = null;
		var c = $("select[name='filter.sellerCids']", $form);
		var cid = c.val();
		if (cid)
		{
			cids = [cid];
			$("option[parent='" + cid + "']", $form).each(function(){
				cids.push($(this).val());
			});
		}
		var keyword = $("input[name='filter.keyWord']", $form).val();
		if (keyword == '关键字')
		{
			keyword = null;
		}
		var q = '';
		if (cids)
		{
			q += '&filter.sellerCids=' + cids;
		}
		if (status)
		{
			q += '&filter.status=' + status;
		}
		if (keyword)
		{
			q += '&filter.keyWord=' + keyword;
		}
		return q;
	}

	$("#pager").pager({ 
		pagenumber: 1, 
		pagecount: $('#items-table').attr('pages'), 
		buttonClickCallback: loadPage, 
		firstLabel: "首页", prevLabel: "前一页", nextLabel: "下一页", lastLabel: "末页" 
	});

	function loadPage(number) {
		loadPage(number, null);
	}

	function loadPage(number, callback) {
	    var limit = $('#items-table').attr('page-size');
	    var offset = (number - 1) * limit;
	    var q = $("#items").data('q') + "&option.limit=" + limit + "&option.offset=" + offset;
	    var url = "listing/items";
	    $("#items").html("<img src='images/loading.gif'/>");
		$.ajax({
			url: url,
			data: q,
			type: 'POST',
			success: function(data) {
				$("#items").html(data);
				//checkSelection();
				var pageCount = parseInt($("#items-table").attr("pages"));
				$("#pager").pager({ pagenumber: number, pagecount: pageCount, buttonClickCallback: loadPage, firstLabel: "首页", prevLabel: "前一页", nextLabel: "下一页", lastLabel: "末页" });
				if (callback)
				{
					callback();
				}
			}
		});
	}

	function reload()
	{
		reload(null);
	}

	function reload(callback)
	{
		var i = parseInt($("#items-table").attr("pageIndex"));
		loadPage(i+1, callback);
	}

		$('#select-btn').click(function() {
			var $selectedItems = $("#items tr.row-selected:visible");
			var $copy = $selectedItems.clone();
			$selectedItems.hide();
			$copy.appendTo($('#selected-items tbody'));
		});
		$('#unselect-btn').click(function(){
			var $selectedItems = $('#selected-items tr.row-selected');
			$selectedItems.each(function(){
				var numIid = $(this).attr('num_iid');
				$('#items tr[num_iid=' + numIid + ']').show();
			});
			$selectedItems.remove();
		});
})();

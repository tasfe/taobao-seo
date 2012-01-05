(function() {
	var millisPerDay = 24 * 60 * 60000;
	var now = new Date();
	var d = [ [now.getTime() - 6 * millisPerDay, 84.36], 
	          [now.getTime() - 5 * millisPerDay, 31.85], 
	          [now.getTime() - 4 * millisPerDay, 38.74], 
	          [now.getTime() - 3 * millisPerDay, 381.15], 
	          [now.getTime() - 2 * millisPerDay, 82.38], 
	          [now.getTime() - millisPerDay, 383.94], 
	          [now.getTime(), 85.44]];     
	$.plot($("#chart"), [{  
		data: d,            
		bars: { 
			show: true,
			lineWidth: 30,
			fill: true,
			fillColors: {colors: [{ brightness: 0.6, opacity: 0.8 } ]}
		}        
	}], { 
			xaxis: { mode: "time" }
	});
})();

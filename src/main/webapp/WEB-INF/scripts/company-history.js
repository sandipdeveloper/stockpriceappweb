/**
 * List company page functionalities
 */

$(document).ready(function () {
		
	$( "#get-company-data" ).click(function() {
		var companyStockSymbol = $.trim($("#company-stock-symbol").val());
		if(companyStockSymbol === null
				||companyStockSymbol === '') {
				changeStatus("Please enter company name and stock symbol", "red");
			} else {
				populateData(companyStockSymbol);
			}
	});
	
	function populateData(companyStockSymbol) {
	    
		$.ajax({
	        url : "http://localhost:8080/company/chart/"+companyStockSymbol,
	        type: "GET",
	        success:function(data, textStatus, jqXHR) {
	        	 var chartdata = 'callback('+data.stockPriceHistory+')';
	            // Create the chart
	              $('#container-chart').highcharts('StockChart', {

	                rangeSelector : {
	                    selected : 'All'
	                },

	                title : {
	                    text : data.companyName+' ('+data.symbol+') '+'stock price'
	                },

	                series : [{
	                    name : data.symbol,
	                    data : data.stockPriceHistory,
	                    tooltip: {
	                        valueDecimals: 2
	                    }
	                }]
	            });
	              changeStatus('', 'green');  
	        },
	        
	        error: function(jqXHR, textStatus, errorThrown) {
	        	if( jqXHR.status === 400) {
	        		changeStatus("Please enter company name and stock symbol", "red");
	        	} else if(jqXHR.status === 404){
	        		changeStatus("Company does not exist in the current listing", "red");		        		
	        	} else {
	        		changeStatus("Some error occurred, plesae try again later", "red");
	        	}
	        }
		});	
	}
	
	function changeStatus( text, color) {
		$( "#result" ).text(text);
		$( "#result" ).css("color", color );
	}
});

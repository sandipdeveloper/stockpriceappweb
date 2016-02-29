/**
 * Add company page functionalities
 */

$(document).ready(function () {	
	$( "#add-company" ).click(function() {
		
		var companyName = $.trim($("#company-name").val());
		var companyStockSymbol = $.trim($("#company-stock-symbol").val());
		
		if(companyName === null 
			|| companyName === '' 
			|| companyStockSymbol === null
			||companyStockSymbol === '') {
			changeStatus("Please enter company name and stock symbol", "red");
		} else {		
			$.ajax({
		        url : "http://localhost:8080/company",
		        type: "POST",
		        data : {"companyName":companyName, "symbol":companyStockSymbol},
		        success:function(data, textStatus, jqXHR) {
		        	changeStatus("Company created successfully", "green");
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		        	if( jqXHR.status === 400) {
		        		changeStatus("Please enter company name and stock symbol", "red");
		        	} else if(jqXHR.status === 404){
		        		changeStatus("Company does not exist in the current listing", "red");		        		
		        	}
		        	else if(jqXHR.status === 409) {
		        		changeStatus("Company already exists", "red");
		        	} else {
		        		changeStatus("Some error occurred, plesae try again later", "red");
		        	}
		        }
			});
			
        	$("#company-name").val("");
        	$("#company-stock-symbol").val("");
		}
	});
	
	function changeStatus( text, color) {
		$( "#result" ).text(text);
		$( "#result" ).css("color", color );
	}
});

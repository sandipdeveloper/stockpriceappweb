/**
 * Delete company page functionalities
 */

$(document).ready(function () {	
	$( "#delete-company" ).click(function() {
		
		var companyStockSymbol = $.trim($("#company-stock-symbol").val());
		
		if(companyStockSymbol === null
			||companyStockSymbol === '') {
			changeStatus("Please enter company's stock symbol", "red");
		} else {		
			$.ajax({
		        url : "http://localhost:8080/company/"+companyStockSymbol,
		        type: "DELETE",
		        success:function(data, textStatus, jqXHR) {
		        	changeStatus("Company deleted successfully", "green");
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		        	if( jqXHR.status === 400) {
		        		changeStatus("Please enter company's stock symbol", "red");
		        	} else if(jqXHR.status === 404){
		        		changeStatus("Company does not exist in the current listing", "red");		        		
		        	} else {
		        		changeStatus("Some error occurred, plesae try again later", "red");
		        	}
		        }
			});
			
        	$("#company-stock-symbol").val("");
		}
	});
	
	function changeStatus( text, color) {
		$( "#result" ).text(text);
		$( "#result" ).css("color", color );
	}
});

/**
 * List company page functionalities
 */

$(document).ready(function () {
	
	populateData();
	
	$( "#refresh-data" ).click(function() {
		populateData();
	});
	
	function populateData() {
		$.ajax({
	        url : "http://localhost:8080/companies",
	        type: "GET",
	        success:function(data, textStatus, jqXHR) {
	        	data = data.stockPrices;
	        	var rowHtml = '';
	        	for(var count = 0; count < data.length; count++) {
	        	     rowHtml += '<tr> <th scope="row">'+(count+1)+'</th>';
	        	     rowHtml += '<td>'+data[count].companyName+'</td>';
	        	     rowHtml += '<td>'+data[count].symbol+'</td>';
	        	     rowHtml += '<td>'+data[count].latestQuote+'</td></tr>';		        	     
	        	}
       	        $('#data-table-body').html(rowHtml);
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	        	$('result').css('color', 'red');
	        	$('result').text('some error occurred retrieveing data, status = '+jqXHR.status);
	        }
		});	
	}
});

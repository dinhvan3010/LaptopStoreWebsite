$(document).ready(function(){
	var url=`/api/cart/info`;
	$.getJSON(url).then(info=>{
		showCartInfo(info);
	})
	
	$(".cart-add").on("click",function(){
		var id = $(this).attr("id");
		var url= `/api/cart/add/${id}`;
		$.getJSON(url).then(info =>{
			showCartInfo(info);
			
		})
	});
	$(".cart-remove").on("click",function(){
		var id = $(this).attr("id");
		var url= `/api/cart/remove/${id}`;
		$.getJSON(url).then(info =>{
			showCartInfo(info);
			$(this).closest("tr").remove();	
		})
	});
	$(".cart-update").on("input",function(){
		var id = $(this).attr("id");
		var qty = $(this).val();
		var price = $(this).attr("data-price");
		var disc= $(this).attr("data-disc")
		var amount = qty* price * (1-disc);
		var url= `/api/cart/update/${id}/${qty}`;
		$.getJSON(url).then(info =>{
			showCartInfo(info);
			//thay doi amount cua san pham//
			$(this).closest("tr").find(".amount").html(amount.toFixed(2));	
			
		})
	});
	$(".cart-clear").on("click",function(){
		var url= `/api/cart/clear`;
		$.getJSON(url).then(info =>{
			showCartInfo(info);
		$("tbody").empty();
		})
	});
});


function showCartInfo(info){
	$("#cart-info").html(` ${info.count}`);
}
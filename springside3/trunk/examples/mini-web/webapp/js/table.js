function jumpPage(pageNo){
		$("#pageNo").val(pageNo);
		$("#mainForm").submit();
	}
	
	function order(orderBy,defaultOrder){
		if($("#orderBy").val()==orderBy){
			if($("#order").val()==""){
			$("#order").val(defaultOrder);}
			else if($("#order").val()=="desc"){
			$("#order").val("asc");}
			else if($("#order").val()=="asc"){
			$("#order").val("desc");}
		}
		else{
			$("#orderBy").val(orderBy);
			$("#order").val(defaultOrder);
		}

		$("#mainForm").submit();
	}

	function modifyItem(id){
		$("#id").val(id);
		$("#mainForm").attr("action",modifyAction);
		$("#mainForm").submit();
	}
	
	function deleteItem(id){
		$("#id").val(id);
		$("#mainForm").attr("action",deleteAction);
		$("#mainForm").submit();
	}
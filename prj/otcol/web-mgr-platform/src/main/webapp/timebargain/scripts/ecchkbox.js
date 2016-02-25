  ec_table.rows(2).cells(0).innerHTML = '<input type="Checkbox" name="chkall" value="false" onclick="javascript:checkall(this.checked);">';
  
  function checkall(bVal)
  {
    //alert(bVal);   
	for (var i = 0; i < ec.length; i++) {
        var element = ec.elements[i];
        if (element.type.indexOf("checkbox") == 0) {
            element.checked = bVal;
		}
    }     
  }
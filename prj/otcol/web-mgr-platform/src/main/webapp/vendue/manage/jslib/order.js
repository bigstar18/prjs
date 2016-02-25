var sign = true;

function init() {
	changeOrder(sign);  
}

function changeOrder(sign) {
	if(sign) {
		var orderField=frm.orderField.value;
		var orderDesc=frm.orderDesc.value;
		if(orderField!=''&&orderDesc!='') {
			var direction="";
			if(orderDesc=="false") {
				direction="¡ü";
			} else { 
             	direction="¡ý";
			}
			var tbHead=document.getElementById("tb");
			var location;
			tbHead.rows(0).attachEvent("onclick", new Function("dtquery()")); 
			for(var i=0;i<tbHead.rows(0).cells.length;i++) {                 
				if(tbHead.rows(0).cells[i].abbr==orderField) {
					location=i;
					var text=tbHead.rows(0).cells[i].innerText;
                   	tbHead.rows(0).cells[i].innerText=text+direction;
                  	tbHead.rows(0).cells[i].style.color="blue";
                   	tbHead.rows(0).cells[i].style.cursor="hand";
               	} else if(tbHead.rows(0).cells[i].abbr!="") {
                    tbHead.rows(0).cells[i].style.cursor="hand";
              	}
            }
		}
	}
}

function dtquery() {
	o = event.srcElement;  
    if(o.tagName!="TD")   return;
	var orderField=frm.orderField.value;
	var orderDesc=frm.orderDesc.value; 
	if(o.abbr!='') {
		if(orderField==o.abbr) {
			if(orderDesc=="true") {
				frm.orderDesc.value="false";
			} else {
				frm.orderDesc.value="true"; 
			}
		} else {
          	frm.orderDesc.value="false";
        	frm.orderField.value=o.abbr;
      	}
		doQuery();
	}
} 

function doQuery() {
	frm.submit();
}
<%@ page pageEncoding="GBK"%>

<iframe id='submitFrame' name='submitFrame' width=0 height=0 style='display:none' src='' application='yes'></iframe>
<script>
var collFrm = document.all.tags("FORM");
if (collFrm) {
	for ( var i = 0; i < collFrm.length; i++) {
		if(collFrm[i].targetType == "hidden"){
			collFrm[i].target ="submitFrame";
		}
	}

}
</script>
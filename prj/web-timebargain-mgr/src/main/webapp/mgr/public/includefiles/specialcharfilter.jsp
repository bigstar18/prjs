<%@ page contentType="text/html;charset=GBK"%>
<script>
function showKeyPress(evt) {
	evt = (evt) ? evt : window.event
	return checkSpecific(String.fromCharCode(evt.keyCode));
}
function checkSpecific(realkey){
	var specialKey = "!<>#$()?%\&\^*\'\"\+\|";//ÌØÊâ×Ö·ûÁÐ±í
	var flg = false;
	flg = (specialKey.indexOf(realkey) >= 0);
	if (flg) {
		return false;
	}
	return true;
}
document.onkeypress=showKeyPress;
</script>
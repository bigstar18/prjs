<%@ page pageEncoding="GBK"%>
<script type="text/javascript">
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function basicInformation_onclick() {
	if (tmp_baseinfo_up) {
		tmp_baseinfo_up = false;
		frm.baseinfo_img.src = "<%=skinPath%>/images1/ctl_detail_Down.gif";
		tmp_baseinfo = baseinfo.innerHTML;
		baseinfo.innerHTML = "";
	} else {
		tmp_baseinfo_up = true;
		frm.baseinfo_img.src = "<%=skinPath%>/images1/ctl_detail_Up.gif";
		baseinfo.innerHTML = tmp_baseinfo;
	}
}
</script>
	<div class="right_02"><img src="<%=skinPath %>/cssimg/13.gif"  />&nbsp;&nbsp;²éÑ¯Ìõ¼þ</div>
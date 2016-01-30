<%@ page pageEncoding="GBK"%>
<script type="text/javascript" src="${publicPath}/js/submitcount.js"></script>
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
<%//本页面功能为：页面中每个 form 表单当其配置 targetType 为 hidden 时，给其增加一个 隐藏域，并将其提交执行隐藏域 %>
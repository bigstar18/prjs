<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/forDateFormat" prefix="datefn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" href="<%=basePath%>report/report_css.css"
	type="text/css" />
<link rel="stylesheet"
	href="<%=basePath%>common/skinstyle/default/common/commoncss/common1.css"
	type="text/css" />
<link rel="stylesheet"
	href="<%=basePath%>public/widgets/ecside/css/ecside_style.css"
	type="text/css" />

<script type="text/javascript">

document.onreadystatechange = subSomething;// ��ҳ�����״̬�ı��ʱ��ִ���������.
function subSomething() {
	var collFrm = document.all.tags("FORM");
	if (collFrm) {
		for ( var i = 0; i < collFrm.length; i++) {
			var au = '111111';
			if (typeof (AUsessionId) != "undefined") {
				au = AUsessionId;
			}
			var e = document.createElement("input");
			e.setAttribute("type", "hidden");
			e.setAttribute("id", 'AUsessionId');
			e.setAttribute("name", 'AUsessionId');
			e.setAttribute("value", au);
			collFrm[i].appendChild(e);
		}

	}
}
/**
 * ��֤�������nΪС�������� s ҪУ����ַ�����n С��λ��
 * true ��֤�ɹ�
 * ��֤ʧ��
 */
function flote(s,n){
	if(isEmpty(s)){
		return true;
	}
	if(!integer(n)){
		return false;
	}else if(isEmpty(n)){
	}else if(n<0){
		return false;
	}else if(n==0){
		return integer(s);
	}
	//var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
</script>
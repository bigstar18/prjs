<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.broker.common.Global"%>
<%@page import="gnnt.MEBS.common.broker.statictools.ApplicationContextInit"%>
<%
	//��¼��ʽ����ΪĬ����ʽ����
	String skinName = "default";
	//�г����ƣ�common_mgr
	String path = request.getContextPath();
	//����·�� http://127.0.0.1:8080/
	String serverPath=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/";
	//web url��http://127.0.0.1:8080/common_mgr
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//broker ·����http://127.0.0.1:8080/common_mgr/broker
	String mgrPath=basePath+"/broker";
	//public ·����http://127.0.0.1:8080/common_mgr/broker/public
	String publicPath = mgrPath + "/public";
	//��������ҳ��·����http://127.0.0.1:8080/common_mgr/broker/public/includefiles
	String includePath = publicPath + "/includefiles";
	//��ǰ��ʽ·����http://127.0.0.1:8080/common_mgr/broker/skinstyle/default
	String skinPath = mgrPath + "/skinstyle/" + skinName;
	//ecside ��ʽ·����http://127.0.0.1:8080/common_mgr/broker/skinstyle/default/ecside
	String escideskinPath =skinPath+ "/ecside";
	//���·����http://127.0.0.1:8080/common_mgr/broker/frame
	String framePath = basePath + "/broker/frame";
	//��ťͼƬ·����http://127.0.0.1:8080/common_mgr/broker/skinstyle/default/image/frame/menu/
	String menuPicPath = skinPath + "/image/frame/menu/";
	/*���� ������� ����ҳ��include��̬·���޷�������ҳ���·�� */
	session.setAttribute("skinPath",skinPath);
%>
<c:set var="basePath" value="<%=basePath %>" />
<c:set var="mgrPath" value="<%=mgrPath %>" />
<c:set var="publicPath" value="<%=publicPath %>" />
<c:set var="includePath" value="<%=includePath %>" />
<c:set var="skinPath" value="<%=skinPath %>" />
<c:set var="escideskinPath" value="<%=escideskinPath %>" />
<c:set var="framePath" value="<%=framePath %>" />
<c:set var="menuPicPath" value="<%=menuPicPath %>" />
<c:set var="skinName" value="<%=skinName %>" />
<script>
//javascript ��Ƥ��·��
var jsSkinPath="../broker/skinstyle/<%=skinName%>/";
var allJsSkinPath="<%=skinPath%>/";
</script>
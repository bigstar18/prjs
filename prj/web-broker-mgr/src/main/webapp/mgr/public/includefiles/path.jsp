<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.model.User"%>
<%@page import="gnnt.MEBS.common.mgr.common.Global"%>
<%@page import="gnnt.MEBS.common.mgr.statictools.ApplicationContextInit"%>
<%
	//��¼����ʽ����
	String skinName = null;
	User sessinUser = (User) request.getSession().getAttribute(
			Global.CURRENTUSERSTR);
	if (sessinUser == null) {//����û�Ϊ�գ�����ʽ����ΪĬ����ʽ����
		skinName = "default";
	} else {//����û���Ϊ�գ�����ʽ����Ϊ�û���ʽ����
		skinName = sessinUser.getSkin();
	}
	//�г����ƣ�common_demo
	String path = request.getContextPath();
	//web url��http://127.0.0.1:8080/common_demo
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//mgr ·����http://127.0.0.1:8080/common_demo/mgr
	String mgrPath=basePath+"/mgr";
	//public ·����http://127.0.0.1:8080/common_demo/mgr/public
	String publicPath = mgrPath + "/public";
	//��������ҳ��·����http://127.0.0.1:8080/common_demo/mgr/public/includefiles
	String includePath = publicPath + "/includefiles";
	//��ǰ��ʽ·����http://127.0.0.1:8080/common_demo/mgr/skinstyle/default
	String skinPath = mgrPath + "/skinstyle/" + skinName;
	//ecside ��ʽ·����http://127.0.0.1:8080/common_demo/mgr/skinstyle/default/ecside
	String escideskinPath =skinPath+ "/ecside";
	//���·����http://127.0.0.1:8080/common_demo/mgr/frame
	String framePath = basePath + "/mgr/frame";
	//��ťͼƬ·����http://127.0.0.1:8080/common_demo/mgr/skinstyle/default/image/frame/menu/
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
var jsSkinPath="../mgr/skinstyle/<%=skinName%>/";
var allJsSkinPath="<%=skinPath%>";
</script>
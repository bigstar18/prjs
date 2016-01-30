<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.front.model.integrated.User"%>
<%@page import="gnnt.MEBS.common.front.common.Global"%>
<%@page import="gnnt.MEBS.common.front.statictools.ActionUtil"%>
<%@page import="gnnt.MEBS.common.front.statictools.Tools" %>
<%@page import="gnnt.MEBS.common.front.statictools.ApplicationContextInit" %>
<%
/*skinstyle*/
String skinName = null;
User user = (User) request.getSession().getAttribute(Global.CURRENTUSERSTR);
if (user == null) {
	skinName = "default";
} else {
	skinName = user.getSkin();
}
//jsʹ�õ�Ƥ��·��(/front/skinstyle/default)
String jsSkinPath = "/front/skinstyle/" + skinName;
//web����ӿ�(http://localhost:8080)
String serverInterface = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//web������(/RealMardridFront)
String serverName = request.getContextPath();
//web�����·��(http://localhost:8080/RealMardridFront)
String basePath = serverInterface + serverName;
//��ǰ�������·��(/RealMardridFront/front/index.jsp)
StringBuffer currenturl = new StringBuffer((String)request.getAttribute("currenturl"));
//��ǰ���ʾ���·��(http://localhost:8080/RealMardridFront/front/index.jsp)
String currentPath = basePath + currenturl.toString();
//�жϵ�ǰ·���Ƿ����get����
if(request.getQueryString()!=null){
	currenturl.append("?");
	currenturl.append(request.getQueryString());
}
//����get�����ĵ�ǰ���ʾ���·��(http://localhost:8080/RealMardridFront/front/index.jsp?a='a')
String currentRealPath = basePath + currenturl.toString();
currentRealPath = new String(currentRealPath.getBytes("iso-8859-1"),"GBK");
//ǰ̨·������λ��(http://localhost:8080/RealMardridFront/front)
   String frontPath=basePath+"/front";
//������Ϣ����λ��(http://localhost:8080/RealMardridFront/front/public)
String publicPath = frontPath + "/public";
//����������Ϣ����λ��(http://localhost:8080/RealMardridFront/front/public/includefiles)
String includePath = publicPath + "/includefiles";
//Ƥ������·��(http://localhost:8080/RealMardridFront/front/skinstyle/default)
String skinPath = basePath+jsSkinPath;
//���·��(http://localhost:8080/RealMardridFront/front/frame)
String framePath = frontPath + "/frame";
//��css�����ҳ��ʹ�õ�һЩͼƬ�ȵĴ��λ��(http://localhost:8080/RealMardridFront/front/skinstyle/default/image/frame/menu/)
String menuPicPath = skinPath + "/image/frame/menu/";
/*���� ������� ����ҳ��include��̬·���޷�������ҳ���·�� */
session.setAttribute("jsSkinPath",jsSkinPath);//jsʹ�õ�Ƥ��·��
session.setAttribute("serverName",serverName);//jsʹ�õ���Ŀ����
session.setAttribute("serverPath",serverInterface);
session.setAttribute("basePath",basePath);//web�����·��
session.setAttribute("currentPath",currentPath);//��ǰ�������·��
session.setAttribute("currentRealPath",currentRealPath);//����get�����ĵ�ǰ���ʾ���·��
session.setAttribute("frontPath",frontPath);//ǰ̨·������λ��
session.setAttribute("publicPath",publicPath);//������Ϣ����λ��
session.setAttribute("includePath",includePath);//����������Ϣ����λ��
session.setAttribute("skinPath",skinPath);//Ƥ������·��
session.setAttribute("framePath",framePath);//���·��
session.setAttribute("menuPicPath",menuPicPath);//��css�����ҳ��ʹ�õ�һЩͼƬ�ȵĴ��λ��
session.setAttribute("today",Tools.fmtDate(new java.util.Date()));
%>
<c:set var="GNNT_" value='<%=ActionUtil.PARAMETERPREFIX%>' />
<script>
	//js�õ�����ʽ·��
	var jsSkinPath="<%=skinPath%>/";
</script>

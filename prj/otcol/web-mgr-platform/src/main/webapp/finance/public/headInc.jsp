<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.base.query.*'%>
<%@ page import='java.util.List' %>
<%@ page import='gnnt.MEBS.finance.base.util.*' %>
<%@ include file="/common/public/choSkin.jsp"%>
<link rel="stylesheet" href="<%=skinPath%>/button.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/style_finance.css" type="text/css"/>
<%!
  public final String FIRMID=gnnt.MEBS.finance.base.util.SysData.getConfig("firmId");;
  public final String FIRMNAME="����������";
  public final String FULLNAME="������ȫ��";
  public final String TRADERID="����Ա����";
  public final String CODEID="ģ�����";
  public final String CODENAME="ģ������";
  public final String SUMMARYNO="ժҪ��";
  public final String DUMMARYNAME="ժҪ����";
  public final String BITCODE="��Ŀ����";
  public final String BITCODENAME="��Ŀ����";
  public final String CONTRACTNO="��ͬ��";
  public final String COMMODITYID="��Ʒ����";
  public final String VOUCHERNO="ƾ֤��";
  
  public final String USERID="�� �� ID";
  public final String USERNAME="�û�����";
  public final String OLDPWD="�� �� ��";
  public final String NEWPWD="�� �� ��";
  public final String CONFORMPWD="ȷ�Ͽ���";
  
%>
<%
    /*
		�Ѿ���choSkin.jsp�ж���ı���:
		skinName-����ƥ����ļ������� 
		skin-ѡ�еķ�� 
		path-/mgr
		basePath1-http:/172.16.2.214:8888/mgr
		skinPath-�õ���css
		escideskinPath-ecside��Ӧ��css
	*/
	//String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/finance";
	//�������
	String comPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String userSkinName = "default";
	//String skinPath = basePath + "/skin/" + userSkinName;
	String nowDate = Utils.formatDate("yyyy-MM-dd",new Date());
	pageContext.setAttribute("nowDate",nowDate);
	
    PageInfo pageInfo=(PageInfo)request.getAttribute("pageInfo");
    String orderFiled="";
    String orderType="";
    if(pageInfo!=null)
    {
    List listf=pageInfo.getOrderFields();
    if(listf!=null&&listf.size()>0)
    {
        OrderField order=(OrderField)listf.get(0);
        orderFiled=order.getOrderField();
        orderType=order.isOrderDesc()+"";
    }
    }
    pageContext.setAttribute("orderFiled", orderFiled);
    pageContext.setAttribute("orderType", orderType);
 %>
<html>
	<base href="<%=basePath%>/">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	
	<link rel="stylesheet" href="<%=skinPath%>/css/style.css" type="text/css"/>
	<script language="javascript" src="public/jstools/formInit.js"></script>
	<script language="javascript" src="<%=comPath%>/timebargain/scripts/global.js"></script>
	<script language="javascript" src="<%=comPath%>/common/jslib/tools.js"></script>
	<script language="javascript">
		var basePath = "<%=basePath%>";
		var skinPath = "<%=skinPath%>";
		var sign=true;
		<c:if test='${not empty resultMsg}'>
			alert('<c:out value="${resultMsg}"/>');
		</c:if>
	</script>
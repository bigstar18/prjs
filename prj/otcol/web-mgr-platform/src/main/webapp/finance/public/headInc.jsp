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
  public final String FIRMNAME="交易商名称";
  public final String FULLNAME="交易商全称";
  public final String TRADERID="交易员代码";
  public final String CODEID="模板代码";
  public final String CODENAME="模板名称";
  public final String SUMMARYNO="摘要号";
  public final String DUMMARYNAME="摘要名称";
  public final String BITCODE="科目代码";
  public final String BITCODENAME="科目名称";
  public final String CONTRACTNO="合同号";
  public final String COMMODITYID="商品代码";
  public final String VOUCHERNO="凭证号";
  
  public final String USERID="用 户 ID";
  public final String USERNAME="用户名称";
  public final String OLDPWD="旧 口 令";
  public final String NEWPWD="新 口 令";
  public final String CONFORMPWD="确认口令";
  
%>
<%
    /*
		已经在choSkin.jsp中定义的变量:
		skinName-与风格匹配的文件夹名称 
		skin-选中的风格 
		path-/mgr
		basePath1-http:/172.16.2.214:8888/mgr
		skinPath-用到的css
		escideskinPath-ecside对应的css
	*/
	//String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/finance";
	//输入控制
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
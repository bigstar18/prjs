<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@page import="gnnt.MEBS.base.query.OrderField"%>
<%@page import="gnnt.MEBS.base.query.PageInfo"%>
<%@ page import='java.util.*'%>
<%@ page import="java.text.*"%>
<%@ page import="gnnt.MEBS.delivery.util.*"%>
<%@ page import="gnnt.MEBS.common.security.*"%>
<%@ page isELIgnored="false"%>
<%@ include file="../../common/public/session.jsp" %>
<%!
//�������� 
public final String POPEDOM="1"; //ϵͳ���� 0:�г� 1:�ֿ� 9:��Ա
public final String TITLE = "�ֿ����ϵͳ";  //��վ����
public final String TITLE_EN = "�ֿ����ϵͳ";  //��վ����
%>
<%
    String USERNAME = AclCtrl.getLogonID(request);		
	String contextPath= request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/delivery/";
	String comPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath;
	String martPath = basePath + "martModule/";
	String publicPath = basePath+"public/";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	String nowDate = sdf.format(new Date());
	pageContext.setAttribute("nowDate",nowDate);
	
	String FIRMID=SysData.getConfig("firmId");
	String FIRMNAME=SysData.getConfig("firmName");
	String POSTFIX=SysData.getConfig("postfix");
	pageContext.setAttribute("FIRMID", FIRMID);
    pageContext.setAttribute("FIRMNAME", FIRMNAME);
    pageContext.setAttribute("POSTFIX", POSTFIX);
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
	<base href="<%=basePath%>/">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	
	<!--<link rel="stylesheet" href="<%=publicPath%>/css/style.css" type="text/css"/>-->
	<script language="javascript" src="<%=publicPath%>/jstools/formInit.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/common.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/frameCtrl.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/tools.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/jquery.js"></script>
	<!-- <script language="javascript" src="<%=comPath%>/timebargain/scripts/global.js"></script> -->

	<!--ȫѡ����ѡ������  -->
	<!--<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script> -->

	<script language="javascript">
		var sign=true;
		var basePath = "<%=basePath%>";
		<c:if test='${not empty resultMsg}'>
			alert('<c:out value="${resultMsg}"/>');
		</c:if>
   function checkMoneyForSettle(obj,msg)
	{
		//var money = obj.value;
		var money = document.getElementById(obj).value;
		if(money!='' &&(money.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(money) == parseFloat(0)))
		{
			alert("������һ����0���֣�");
	        document.getElementById(obj).value = "";
	        document.getElementById(obj).focus();
		}
		else
		{
			var thismoney = frm.thisPayMent.value;
			var percentMoney = (frm.totalMoney.value)*0.01*(frm.percent.value);
			if(percentMoney!='')
			{
				thismoney = percentMoney;
				frm.thisPayMent.value = percentMoney.toFixed(2);
			}
		}
	}
	
	function thisPay(msg,forwardUrl)
	{
		var thismoney = frm.thisPayMent.value;
     	var submark = false;
     	if(confirm(msg+""+thismoney+"��"))
     	{
     		frm.subbtn.disabled = true;
     		frm.opt.value="submit";
     		submark = true;
     	}
     	if(submark)
     	{
     		frm.action=forwardUrl+Date();
     		frm.submit();
     	}
	}
	</script>
	
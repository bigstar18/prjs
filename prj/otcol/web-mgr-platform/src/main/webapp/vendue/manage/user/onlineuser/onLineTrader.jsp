<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.vendue.util.*"%>
<%@ include file="../../globalDef.jsp"%>
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "onlineuser";
}
if (orderType == null || orderType.equals("")) {
	orderType = "false";
}
String order = "";
if (orderType.equals("false")) {
	order = "asc";
}
if (orderType.equals("true")){
	order = "desc";
}
String aa = orderField+" "+order;
request.setAttribute("aa", aa);
%>
<!--变量定义-->
<html>
  <head>
	<title>在线交易商浏览</title>
</head>
<body onload="init();">
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
    <!--默认选中板块-->
	<c:set var="defaultSec" value=""/>
	<c:choose>
	<c:when test="${empty param.partitionID}">
    <c:set var="defaultSec" value=""/>
    <db:select var="row" table="v_syspartition" columns="partitionid" where="1=1 " orderBy="partitionid desc">
      <c:set var="defaultSec" value="${row.partitionid}"/>
    </db:select>
    <jsp:include page="../../public/menu1.jsp">
	  	<jsp:param name="partitionID" value="${defaultSec}"/>
	  	<jsp:param name="idx" value="${defaultSec}"/>
    </jsp:include>
	  </c:when>
	  <c:otherwise>
	  	<jsp:include page="../../public/menu1.jsp"/>
	  </c:otherwise>
	</c:choose>
	<%
        List<String> list = new ArrayList<String>();//存放所有在线交易商用户的数组
        String[] us = null;
        String uss=null;//存放所有在线交易商用户的字符串
				int traderTotal=0;//交易商总数
        String partitionID=request.getParameter("partitionID");
	    String userCodeForQuery = request.getParameter("userCode");
				if(partitionID==null){
				    partitionID=pageContext.getAttribute("defaultSec").toString();
				}
				
				/*ActiveUserRMI activeUser = (ActiveUserRMI) Naming.lookup("rmi://" + ACTIVEHOST+":" +ACTIVEPORT+ "/"+ACTIVEUSERCLASS+""+partitionID);*/
				gnnt.MEBS.member.ActiveUser.ActiveUserRMI activeUser = (gnnt.MEBS.member.ActiveUser.ActiveUserRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+ACTIVEUSERCLASS+"");
        uss=activeUser.getAllUsers();
				if(uss!=null&&!"".equals(uss))
				{
					traderTotal=uss.split(";").length;
				}
	%>
		        <fieldset width="100%">
		        <legend>在线交易商查询</legend>
		        <span>
			    <table border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr height="35">
            	<td align="right" width="30%"> 交易商代码：</td>
                <td align="left" width="30%">
                <input name="userCode" type="text" class="text" value="${param.userCode}" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
			    <td align="left">
               	<input type="button" onclick="frm.submit()" class="btn" value="查询">&nbsp;&nbsp;
               	<input type="reset" class="btn">&nbsp;&nbsp;
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<font style="font-size:12px;">在线交易商总数：<%=traderTotal%></font>
		<br><br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  		    <td class="panel_tHead_LB">&nbsp;</td>
			    <td class="panel_tHead_MB" abbr="onlineuser">交易员代码</td>
			    <td class="panel_tHead_MB">登录时间</td>
			    <td class="panel_tHead_MB">登录IP </td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<%
				String userobj = null;
				String onlineuser = null;
				String logintime  = null;
				String loginIP    = null;
				String[] oneUser = null;
				if(uss!=null) {
					us=uss.split(";");
					for (int i=0; i<us.length; i++) {
						list.add(us[i]);
					}
					if (order.equals("desc")) {
						Collections.sort(list,new test1());
					} else {
						Collections.sort(list,new test());
					}
				}
				if(userCodeForQuery !=null && !"".equals(userCodeForQuery)){
					for(int a = 0 ; a < list.size() ; a ++)
					{		
						userobj = list.get(a);	
						if(userobj!=null)
						{
							oneUser = userobj.split(",");
						}
						if(oneUser.length >2)	
						{
							onlineuser = oneUser[0];
							logintime = oneUser[1];
							loginIP = oneUser[2];
							if(onlineuser.equals(userCodeForQuery)){
								%>
								<tr onclick="selectTr();" align=center height="25">
						  		<td class="panel_tBody_LB">&nbsp;</td>
						  		<td class="underLine"><%=onlineuser%></td>
						  		<td class="underLine"><%=logintime%></td>
						  		<td class="underLine"><%=loginIP%></td>
						  		<td class="panel_tBody_RB">&nbsp;</td>
						  		</tr>
						  		<%
							}
						}
					}
				}
				else{
					for(int a = 0 ; a < list.size() ; a ++)
					{		
						userobj = list.get(a);	
						if(userobj!=null)
						{
							oneUser = userobj.split(",");
						}
						if(oneUser.length >2)	
						{
							onlineuser = oneUser[0];
							logintime = oneUser[1];
							loginIP = oneUser[2];
							%>
							<tr onclick="selectTr();" align=center height="25">
					  		<td class="panel_tBody_LB">&nbsp;</td>
					  		<td class="underLine"><%=onlineuser%></td>
					  		<td class="underLine"><%=logintime%></td>
					  		<td class="underLine"><%=loginIP%></td>
					  		<td class="panel_tBody_RB">&nbsp;</td>
					  		</tr>
					  		<%
						}
					}
				}%>
		  	</tBody>
			<jsp:include page="../../public/pageTurn1Copy.jsp">
			<jsp:param name="colspan" value="3"/>
			</jsp:include>
		</table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}

function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}

//查看所属会员信息
function belongtoManager(userCode)
{
	PopWindow("../trader/belongtoManager.jsp?userCode="+userCode,700,600);
}

//点击菜单中的板块栏做查询时
function clickMenu(v){
	 frm.partitionID.value=v;
	 frm.idx.value=v;
	 frm.action="onLineTrader.jsp";
   frm.submit();
}
</SCRIPT>
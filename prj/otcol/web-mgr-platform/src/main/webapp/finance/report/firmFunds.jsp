<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.List' %>
<%@ page import='java.util.Map' %>
<%@ page import='java.math.BigDecimal' %>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
    <title>交易商资金情况</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	 
	</script> 
  </head>
  <%
  try{
    List list = (List)request.getAttribute("list");
    Map map=null;
    List listResult=null;
   	if(list!=null&&list.size()==2)
    {
      	map=(Map)list.get(0);
      	listResult=(List)list.get(1);
    }
    if(map!=null)
    {
   %>
  <body>
  </br>
  </br>
  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="90%" height="90%" align="center">
  <tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" width="15%">&nbsp;</td>
				<td class="panel_tHead_MB" width="30%" align="right">名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="panel_tHead_MB" width="40%" align="right">内容&nbsp;&nbsp;</td>
				<td class="panel_tHead_MB" width="15%">&nbsp;</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
  </tHead>
  <tBody>
  <tr height="22" onclick="selectTr();">
  <td class="panel_tBody_LB">&nbsp;</td>
  <td class="underLine">&nbsp;</td>
  <td class="underLine" align="right"><%=FIRMID%>：</td><td align="right" class="underLine"><b><%=(map.get("firmId")).toString()%></b></td>
  <td class="underLine">&nbsp;</td>
  <td class="panel_tBody_RB">&nbsp;</td>
  </tr >
  <tr height="22" onclick="selectTr();">
  <td class="panel_tBody_LB">&nbsp;</td>
  <td class="underLine">&nbsp;</td>
  <td class="underLine" align="right">期初余额：</td><td align="right" class="underLine"><fmt:formatNumber value="<%=(map.get("lastBalance")).toString()%>" pattern="#,##0.00"/></td>
  <td class="underLine">&nbsp;</td>
  <td class="panel_tBody_RB">&nbsp;</td>
  </tr>
  <%
     if(listResult!=null&&listResult.size()>0)
     {
        for(int i=0;i<listResult.size();i++)
        {
           Map m=(Map)listResult.get(i);
           %>
           <tr height="22" onclick="selectTr();">
           <td class="panel_tBody_LB">&nbsp;</td>
           <td class="underLine">&nbsp;</td>
           <td align="right" class="underLine"><%=(m.get("name")).toString()%>：</td><td align="right" class="underLine"><fmt:formatNumber value="<%=(m.get("value")).toString()%>" pattern="#,##0.00"/></td>
           <td class="underLine">&nbsp;</td>
           <td class="panel_tBody_RB">&nbsp;</td>
           </tr>
           <%
        }
     }
   %>
   <tr height="22" onclick="selectTr();">
   <td class="panel_tBody_LB">&nbsp;</td>
   <td class="underLine">&nbsp;</td>
  <td class="underLine" align="right">期末余额：</td><td align="right" class="underLine"><fmt:formatNumber value="<%=(map.get("balance")).toString()%>" pattern="#,##0.00"/></td>
  <td class="underLine">&nbsp;</td>
  <td class="panel_tBody_RB">&nbsp;</td>
  </tr>
  </tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4">
					&nbsp;
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
  </table>
  
  </body>
  <%
     }
     }catch(Exception e)
     {
       e.printStackTrace();
     }
   %>
</html>
<%@ include file="../public/footInc.jsp" %>
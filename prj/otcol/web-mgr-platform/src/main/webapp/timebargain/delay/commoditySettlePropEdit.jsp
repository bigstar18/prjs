<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/Date.js"/>"></script>	
		<title>
		</title>
		<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
}
//delete
function save_onclick(){
	document.forms(0).action = "<c:url value="/timebargain/delay/delay.do?funcflg=commoditySettlePropEdit"/>";
	document.forms(0).submit();
}
function alertMsg(){
	var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}
}
</script>
	</head>
	<body leftmargin="0" topmargin="0" onLoad="return alertMsg()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="100%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/delay/delay.do?funcflg=commoditySettlePropEdit"
						method="POST" styleClass="form" >
						
						<fieldset class="pickList" style="width:70%" align="center">
							<legend class="common">
								<b>商品交收权限
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
              
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>
									<td align="right" width="20%" valign="top">
											&nbsp;&nbsp;交收申报：
									</td>
									<td align="left">
										<table class="common">
										<%
											List listSettle = (List)request.getAttribute("listSettle");
											List listMiddle = (List)request.getAttribute("listMiddle");
											List listSettle1 = (List)request.getAttribute("commoditySelect"); 
											if(listSettle1!=null&&listSettle1.size()>0){
												listSettle1.remove(0);
												int sel = 0;
												for(int i = 0;i<listSettle1.size();i++){
													sel ++ ;
												  // else
												   {
													  
													LabelValue labelSettle1 = (LabelValue)listSettle1.get(i);
													%>
														<td><input type="checkbox" name="settle" value="<%=labelSettle1.getValue() %>"
														<%
															for(int j=0;j<listSettle.size();j++){
																Map map = (Map)listSettle.get(j);
																if(labelSettle1.getValue().equals((String)map.get("COMMODITYID"))){
																	out.print("checked");
																	break;
																}
															
															}
														 %>
														
														> <%=labelSettle1.getLabel() %> &nbsp;&nbsp;</td>
													<%
													}
													if(sel%4==0){
													%>
														<tr> </tr>
													<%
													
													}
												}
												System.out.println("sel="+sel);
											}else{
												%><tr><td colspan='7'>无交易商品</td></tr><%
											}
										 %>
										 </table>
									</td>
								</tr>
								<tr>
									<td align="right" width="20%">
									</td>
									<td>
									</td>
								</tr>
								<tr>
									<td align="right" width="20%">
									</td>
									<td>
									</td>
								</tr>
								<tr>
									<td align="right" width="20%" valign="top">
											中立仓申报：
									</td>
									<td>
										<table class="common">
										<%
											List listMiddle1 = (List)request.getAttribute("commoditySelect");
											if(listMiddle1!=null&&listMiddle1.size()>0){
												int mid = 0;
												for(int i = 0;i<listMiddle1.size();i++){
													mid ++;
													{
													LabelValue labelMiddle1 = (LabelValue)listMiddle1.get(i);
													%>
														<td><input type="checkbox" name="middle" value="<%=labelMiddle1.getValue() %>"
														<%
															for(int j=0;j<listMiddle.size();j++){
																Map map = (Map)listMiddle.get(j);
																if(labelMiddle1.getValue().equals((String)map.get("COMMODITYID"))){
																	out.print("checked");
																	break;
																}
															
															}
														 %>
														> <%=labelMiddle1.getLabel() %> &nbsp;&nbsp;</td>
													<%
													}
													if(mid%4==0){
												  %>
												  	<tr> </tr>
												  <%
												   }
												}
												
											}else{
												%><tr><td colspan='7'>无交易商品</td></tr><%
											}
										 %>
										 </table>
									</td>
								</tr>
				</table >
			</span>
          </td>
        </tr>					
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>																																										
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
						
						<html:hidden property="crud" />
					</html:form>
				</td>
			</tr>
			
		</table>
		
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
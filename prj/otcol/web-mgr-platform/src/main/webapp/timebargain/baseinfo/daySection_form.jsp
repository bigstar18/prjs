<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%
	String weeks = (String)request.getAttribute("weeks");
	Map mapWeek = (Map)request.getAttribute("mapWeek");
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
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
		<script language="JavaScript" > 
function window_onload()
{
    highlightFormElements();
    
    setTimeout("isOK()",1000);
	
}
function isOK(){
	var relWeek = "<%=weeks%>";
	if (relWeek != null && relWeek != "") {
		var relWeeks = relWeek.split(",");
	    var weeks;
		for (j = 0; j < relWeeks.length; j++) {
			if ("1" == relWeeks[j]) {
				weeks = document.forms(0).week1;
			}
			if ("2" == relWeeks[j]) {
				weeks = document.forms(0).week2;
			}
			if ("3" == relWeeks[j]) {
				weeks = document.forms(0).week3;
			}
			if ("4" == relWeeks[j]) {
				weeks = document.forms(0).week4;
			}
			if ("5" == relWeeks[j]) {
				weeks = document.forms(0).week5;
			}
			if ("6" == relWeeks[j]) {
				weeks = document.forms(0).week6;
			}
			if ("7" == relWeeks[j]) {
				weeks = document.forms(0).week7;
			}
			try {
				if (weeks) {
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						week.checked = false;
						setDisabled(week);
					}
				}
			} catch (e) {
				alert("无交易节！");
			}
		}
	}
}


//save111111
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		window.close();
	    document.forms(0).submit();
	    document.forms(0).save.disabled = true;
   }
    
}

   



function suffixNamePress()
{
	
  if (marketForm.addedTax.value < 1 && (event.keyCode>=46 && event.keyCode<=57) )  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}



//---------------------------start baseinfo-------------------------------

//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------

//-----------------------------end paraminfo-----------------------------
</script>
	</head>

	<body onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeTime.do?funcflg=saveDaySection"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList">
							<legend class="common">
								<b>每日交易节设置
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
            
              
<span id="baseinfo">
<table cellSpacing="4" cellPadding="4" width="100%" border="1" align="center" class="common">
									
								
								<tr>
									<td align="right" width="118">
											星期一：
									</td>
									<%
										if (mapWeek != null) {
											List list2 = (List)mapWeek.get("2");
											if (list2 != null && list2.size() > 0) {
												for (int i2 = 0; i2 < list2.size(); i2++) {
													Map map2 = (Map)list2.get(i2);
													String weekDay2 = map2.get("weekDay")+"";
									    			String sectionID2 = map2.get("sectionID")+"";
									    			String status2 = map2.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week2" value="<%=sectionID2%>" <%if ("0".equals(status2)) {%> checked <%} %> style="border: 0px"/><%=sectionID2%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
									
								</tr>
								<tr>
									<td align="right" width="118">
											星期二：
									</td>
									<%
										if (mapWeek != null) {
											List list3 = (List)mapWeek.get("3");
											if (list3 != null && list3.size() > 0) {
												for (int i3 = 0; i3 < list3.size(); i3++) {
													Map map3 = (Map)list3.get(i3);
													String weekDay3 = map3.get("weekDay")+"";
									    			String sectionID3 = map3.get("sectionID")+"";
									    			String status3 = map3.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week3" value="<%=sectionID3%>" <%if ("0".equals(status3)) {%> checked <%} %> style="border: 0px"/><%=sectionID3%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
									
								</tr>
								<tr>
									<td align="right" width="118">
											星期三：
									</td>
									<%
										if (mapWeek != null) {
											List list4 = (List)mapWeek.get("4");
											if (list4 != null && list4.size() > 0) {
												for (int i4 = 0; i4 < list4.size(); i4++) {
													Map map4 = (Map)list4.get(i4);
													String weekDay4 = map4.get("weekDay")+"";
									    			String sectionID4 = map4.get("sectionID")+"";
									    			String status4 = map4.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week4" value="<%=sectionID4%>" <%if ("0".equals(status4)) {%> checked <%} %> style="border: 0px"/><%=sectionID4%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
								</tr>
								<tr>
									<td align="right" width="118">
											星期四：
									</td>
									<%
										if (mapWeek != null) {
											List list5 = (List)mapWeek.get("5");
											if (list5 != null && list5.size() > 0) {
												for (int i5 = 0; i5 < list5.size(); i5++) {
													Map map5 = (Map)list5.get(i5);
													String weekDay5 = map5.get("weekDay")+"";
									    			String sectionID5 = map5.get("sectionID")+"";
									    			String status5 = map5.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week5" value="<%=sectionID5%>" <%if ("0".equals(status5)) {%> checked <%} %> style="border: 0px"/><%=sectionID5%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
								</tr>
								<tr>
									<td align="right" width="118">
											星期五：
									</td>
									<%
										if (mapWeek != null) {
											List list6 = (List)mapWeek.get("6");
											if (list6 != null && list6.size() > 0) {
												for (int i6 = 0; i6 < list6.size(); i6++) {
													Map map6 = (Map)list6.get(i6);
													String weekDay6 = map6.get("weekDay")+"";
									    			String sectionID6 = map6.get("sectionID")+"";
									    			String status6 = map6.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week6" value="<%=sectionID6%>" <%if ("0".equals(status6)) {%> checked <%} %> style="border: 0px"/><%=sectionID6%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
								</tr>
								<tr>
									<td align="right" width="118">
											星期六：
									</td>
									<%
										if (mapWeek != null) {
											List list7 = (List)mapWeek.get("7");
											if (list7 != null && list7.size() > 0) {
												for (int i7 = 0; i7 < list7.size(); i7++) {
													Map map7 = (Map)list7.get(i7);
													String weekDay7 = map7.get("weekDay")+"";
									    			String sectionID7 = map7.get("sectionID")+"";
									    			String status7 = map7.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week7" value="<%=sectionID7%>" <%if ("0".equals(status7)) {%> checked <%} %> style="border: 0px"/><%=sectionID7%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
								</tr>
								<tr>
									<td align="right" width="118">
											星期日：
									</td>
									<%
										if (mapWeek != null) {
											List list1 = (List)mapWeek.get("1");
											if (list1 != null && list1.size() > 0) {
												for (int i1 = 0; i1 < list1.size(); i1++) {
													Map map1 = (Map)list1.get(i1);
													String weekDay1 = map1.get("weekDay")+"";
									    			String sectionID1 = map1.get("sectionID")+"";
									    			String status1 = map1.get("status")+"";
									    			%>
									    				<td align="left" >
															<input type="checkbox" name="week1" value="<%=sectionID1%>" <%if ("0".equals(status1)) {%> checked <%} %> style="border: 0px"/><%=sectionID1%>&nbsp;										
														</td>
									    			<%
												}
											}
										}
									%>
								</tr>
								
								
								

													
</table >
</span>
            
          </td>
        </tr>					
        	
								<tr>
									<td colspan="5" height="3">	
								</td>
								</tr>																																										
								<tr>
									<td colspan="5" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
									
									</td>
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>
		
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

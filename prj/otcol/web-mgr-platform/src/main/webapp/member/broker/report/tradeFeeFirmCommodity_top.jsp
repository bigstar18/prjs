<%@ include file="/timebargain/printreport/util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%
Date sysdate = new Date();
SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
String nowDate = df.format(sysdate);
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    //highlightFormElements();
 //   statQueryForm.marketCode.focus();

    query_onclick();
    module_breed();
}
//query_onclick
function query_onclick()
{
	var startDate = statQueryForm.beginDate.value;
	var endDate = statQueryForm.endDate.value;
	var beginComm=statQueryForm.beginComm.value;
	var endComm=statQueryForm.endComm.value;
	if(beginComm == "" && endComm != ""){
		alert("开始品种不能为空！");
		statQueryForm.beginComm.focus();
		return false;
	}
	if(beginComm != "" && endComm == ""){
		alert("结束品种不能为空！");
		statQueryForm.endComm.focus();
		return false;
	}

 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
 {
  if(statQueryForm.beginDate.value=="")
  {
    alert("开始日期不能为空！");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(statQueryForm.endDate.value=="")
  {
    alert("结束日期不能为空！");
    statQueryForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.beginDate.value))
  {
    alert("开始日期格式不正确！");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.endDate.value))
  {
    alert("结束日期格式不正确！");
    statQueryForm.endDate.focus();
    return false;
  }
  if ( startDate > '<%=nowDate%>' ) { 
      alert("开始日期不能大于当天日期!"); 
      statQueryForm.beginDate.focus();
      return false; 
  } 
  if ( startDate > endDate ) { 
      alert("开始日期不能大于结束日期!"); 
      return false; 
  } 
 }
 
  statQueryForm.submit();
}
//requery_onclick
function requery_onclick()
{
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/quotation.jsp"/>";
}
function module_breed(){
	var moduleId =document.getElementById("moduleId").value;
	var beginComm=document.getElementById("beginComm");
	var endComm=document.getElementById("endComm");
	beginComm.length=1;
	endComm.length=1;
	if(moduleId==4){
		beginComm.name="_tb.ID[>=]";
		endComm.name="_tb.ID[<=]";
	}else{
		beginComm.name="_tb.breedID[>=]";
		endComm.name="_tb.breedID[<=]";
	}
	if(moduleId == ""){
		statQueryForm.beginComm.disabled=true;
		statQueryForm.endComm.disabled=true;
		return ;
	}
	var xmlHttp;
	try{
		//IE 6+
		xmlHttp=new ActiveXobject("Msxml2.XMLHTTP");
	}catch(e){
		try{
			//FireFox
			xmlHttp=new XMLHttpRequest();
		}catch(e){
			try{
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				alert("您的浏览器不支持Ajax!");
				return false;
			}
		}
	}
	xmlHttp.open("post","${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=getBreedByModuleId&moduleId="+moduleId,true);
	xmlHttp.send(null);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			beginComm.disabled=false;
			endComm.disabled=false;
			var text=xmlHttp.responseText;
			var breedsAsc=text.split("-")[0];
			var breedsDesc=text.split("-")[1];
			var breedAscList=breedsAsc.split(";");
			for(var i = 0;i < breedAscList.length - 1; i++){
				var breedId=breedAscList[i].split(":")[0];
				var breedName =breedAscList[i].split(":")[1];
				var option =document.createElement("option");
				option.text=breedName;
				option.value=breedId;
				beginComm.add(option);
			}
			var breedDescList=breedsDesc.split(";");
			for(var i = 0;i < breedDescList.length - 1; i++){
				var breedId=breedDescList[i].split(":")[0];
				var breedName =breedDescList[i].split(":")[1];
				var option =document.createElement("option");
				option.text=breedName;
				option.value=breedId;
				endComm.add(option);
			}	
		}
	}
}

</script>
	</head>
<%
   ////把起始交易商列表换成了输入框
   //List firmAscList=getList("select firmId from t_firm order by firmId");
   //List firmDescList=getList("select firmId from t_firm order by firmId desc");
   //pageContext.setAttribute("firmAscList",firmAscList);
   //pageContext.setAttribute("firmDescList",firmDescList);
   List breedStartList = getList("select breedID from t_a_breed order by breedID asc");
   List breedEndList = getList("select breedID from t_a_breed order by breedID desc");
   pageContext.setAttribute("breedStartList",breedStartList);
   pageContext.setAttribute("breedEndList",breedEndList);
 %>
	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form action="${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=tradeFeeFirmCommodityList"
						method="POST" styleClass="form" target="ListFrame" name="statQueryForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
									模块：
									</td>
									<td>
									<select name="moduleId" style="width:111" maxlength="16" id="moduleId" onchange="module_breed()">
										<option selected="true" value="">全部</option>
										<option value="2">订单</option>
										<option value="3">挂牌</option>
										<option value="4">竞价</option>
									</select>
									</td>

						            <td>&nbsp;</td>
						            <td align="right">
										开始品种：
									</td>
									<td>
									<SELECT name="_tb.breedID[>=]" style="width:111" maxlength="16" id="beginComm">
	            						<OPTION value="">全部</OPTION>
									</SELECT>
									</td>
									<td align="right">
										&nbsp;&nbsp;
									</td>
									<td align="right">
										结束品种：
									</td>
									<td>
									
									<SELECT  name="_tb.breedID[<=]" style="width:111" maxlength="16" id="endComm">
	            						<OPTION value="">全部</OPTION>
          							</SELECT>
									<td align="right">
									</td>
									<td>
									</td>
									<td align="left">
						            </td> 
						            <td>&nbsp;</td>
									<td align="right">
										开始日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[>=]"  value="" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期"  styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;
									</td>
									<td align="right">
										结束日期：
									</td>
									<td>
										<input type="text" id="endDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[<=]"  ondblclick="if(!this.readOnly){setRq(this);}"  value=""  title="双击选择日期"  styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>	
																	
								<td align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" 
											onclick="javascript:return query_onclick();" class="button" value="执行查询"/>
									</td>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset"   class="button" value="重&nbsp;&nbsp;&nbsp;&nbsp;置" />
									</td>	
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

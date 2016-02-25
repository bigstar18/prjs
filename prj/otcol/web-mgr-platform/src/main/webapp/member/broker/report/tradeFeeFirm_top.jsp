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
    isQryHis_onclick();
    query_onclick();
}
//query_onclick
function query_onclick()
{
	var startDate = statQueryForm.beginDate.value;
	var endDate = statQueryForm.endDate.value;
	var beginComm=statQueryForm.beginComm.value;
	var endComm=statQueryForm.endComm.value;
	if(beginComm == "" && endComm != ""){
		alert("��ʼƷ�ֲ���Ϊ�գ�");
		statQueryForm.beginComm.focus();
		return false;
	}
	if(beginComm != "" && endComm == ""){
		alert("����Ʒ�ֲ���Ϊ�գ�");
		statQueryForm.endComm.focus();
		return false;
	}
 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
 {
  if(statQueryForm.beginDate.value=="")
  {
    alert("��ʼ���ڲ���Ϊ�գ�");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(statQueryForm.endDate.value=="")
  {
    alert("�������ڲ���Ϊ�գ�");
    statQueryForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.beginDate.value))
  {
    alert("��ʼ���ڸ�ʽ����ȷ��");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.endDate.value))
  {
    alert("�������ڸ�ʽ����ȷ��");
    statQueryForm.endDate.focus();
    return false;
  }
  if ( startDate > '<%=nowDate%>' ) { 
      alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
      statQueryForm.beginDate.focus();
      return false; 
  } 
  if ( startDate > endDate ) { 
      alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
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

function isQryHis_onclick()
{
  if(statQueryForm.isQryHis.checked)
  {
    statQueryForm.beginComm.disabled=false;
    statQueryForm.endComm.disabled=false;
    statQueryForm.isPartition.value="Y";
  }
  else
  {
    statQueryForm.beginComm.disabled=true;
    statQueryForm.endComm.disabled=true;
    statQueryForm.isPartition.value="N";
  }
}

function reset_onclick(){
	statQueryForm.isQryHis.checked=false;
	statQueryForm.beginComm.disabled=true;
	statQueryForm.endComm.disabled=true;
	statQueryForm.isPartition.value="N";
	statQueryForm.beginComm.value="";
	statQueryForm.endComm.value="";
	statQueryForm.beginDate.value='${lastDay}'
	statQueryForm.endDate.value='${lastDay}';
	statQueryForm.beginFirm.value="";
	statQueryForm.endFirm.value="";
}
function module_breed(){
	var moduleId =document.getElementById("moduleId").value;
	var beginComm=document.getElementById("beginComm");
	var endComm=document.getElementById("endComm");
	beginComm.length=1;
	endComm.length=1;
	if(moduleId == ""){
		statQueryForm.isQryHis.checked=false;
		statQueryForm.beginComm.disabled=true;
		statQueryForm.endComm.disabled=true;
		statQueryForm.isPartition.value="N";
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
				alert("�����������֧��Ajax!");
				return false;
			}
		}
	}
	xmlHttp.open("post","${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=getBreedByModuleId&moduleId="+moduleId,true);
	xmlHttp.send(null);
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
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
   ////����ʼ�������б����������
   //List firmAscList=getList("select firmId from t_firm order by firmId");
   //List firmDescList=getList("select firmId from t_firm order by firmId desc");
   //pageContext.setAttribute("firmAscList",firmAscList);
   //pageContext.setAttribute("firmDescList",firmDescList);
   //List breedStartList = getList("select breedid from t_a_breed order by breedid asc");
   //List breedEndList = getList("select breedid from t_a_breed order by breedid desc");
   //pageContext.setAttribute("breedStartList",breedStartList);
   //pageContext.setAttribute("breedEndList",breedEndList);
 %>
	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form action="${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=tradeFeeFirmList"
						method="POST" styleClass="form" target="ListFrame" name="statQueryForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
									ģ�飺
									</td>
									<td>
									<select name="moduleId" style="width:111" maxlength="16" id="moduleId" onchange="module_breed()">
										<option selected="true" value="">ȫ��</option>
										<option value="2">����</option>
										<option value="3">����</option>
										<option value="4">����</option>
										
									</select>
									</td>
									<td align="right">
									</td>
									<td>
									<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()"
									 value="false" class="NormalInput"/><label for="isQryHis" >��Ʒ��</label>
									</td>
									<td align="left">
						            </td> 
						            <td>&nbsp;</td>
						            <td align="right">
										��ʼƷ�֣�
									</td>
									<td>
									<input type="hidden" id="isPartition" name="_isPartition[=]"/>
									<SELECT name="_a.breedID[>=]" style="width:111" maxlength="16" id="beginComm">
	            						<OPTION value="">ȫ��</OPTION>
									</SELECT>
									</td>
									<td align="right">
										&nbsp;&nbsp;
									</td>
									<td align="right">
										����Ʒ�֣�
									</td>
									<td>
									
									<SELECT  name="_a.breedID[<=]" style="width:111" maxlength="16" id="endComm">
	            						<OPTION value="">ȫ��</OPTION>
          							</SELECT>
									
									<td align="right">
										��ʼ�����̣�
									</td>
									<td>
									<input type="text" name="_a.firmid[>=]" size=11 id="beginFirm"/>
									</td>
									<td align="right">
										&nbsp;&nbsp;
									</td>
									<td align="right">
										���������̣�
									</td>
									<td>
									<input type="text" name="_a.firmid[<=]" size=11 id="endFirm"/>
									</td>	
									<td align="right">
										&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
								<td align="right">
									</td>
									<td>
									</td>
									<td align="right">
									</td>
									<td>
									</td>
									<td align="left">
						            </td> 
						            <td>&nbsp;</td>
									<td align="right">
										��ʼ���ڣ�
									</td>
									<td>
										<input type="text" id="beginDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[>=]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="˫��ѡ������"  styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;
									</td>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[<=]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="˫��ѡ������"  styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>	
																	
								<td align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" 
											onclick="javascript:return query_onclick();" class="button" value="ִ�в�ѯ"/>
									</td>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" 
											onclick="javascript:return reset_onclick();"  class="button" value="��&nbsp;&nbsp;&nbsp;&nbsp;��" />
									</td>	
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

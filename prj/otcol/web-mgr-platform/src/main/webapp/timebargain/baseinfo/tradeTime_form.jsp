<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
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
	var gatherBid = document.forms(0).gatherBid.value;
	if (gatherBid == "0") {
		setReadOnly(document.forms(0).bidStartTime);
		setReadOnly(document.forms(0).bidEndTime);
	}else if (gatherBid == "1") {
		setReadWrite(document.forms(0).bidStartTime);
		setReadWrite(document.forms(0).bidEndTime);
	}
    highlightFormElements();
    //setReadOnly(document.forms(0).modifyTime);
   // setReadOnly(document.forms(0).breedID);
   // setReadOnly(document.forms(0).breedName);
    if(document.forms(0).crud.value == "create")
    {
      //alert();
      document.forms(0).sectionID.focus();
      document.forms(0).status.value = "1";
      //alert();
    }
    else if(document.forms(0).crud.value == "update")
    {
      setReadOnly(document.forms(0).sectionID);
      document.forms(0).name.focus();
      
    }
    
    
}
//����
function change(){
	var gatherBid = document.forms(0).gatherBid.value;
	var td1 = window.id1;
	var td2 = window.id3;
	if (gatherBid == "0") {
	td1.innerHTML = '';
	td2.innerHTML = '';
	document.getElementById("id2").style.visibility = "hidden";
	document.getElementById("id4").style.visibility = "hidden";
   	document.getElementById("aaa").className = "yin";
		setReadOnly(document.forms(0).bidStartTime);
		setReadOnly(document.forms(0).bidEndTime);
	}else if (gatherBid == "1") {
	td1.innerHTML = '���Ͼ��ۿ�ʼʱ�䣺';
	td2.innerHTML = '���Ͼ��۽���ʱ�䣺';
	document.getElementById("id2").style.visibility = "visible";
	document.getElementById("id4").style.visibility = "visible";
   	document.getElementById("aaa").className = "xian";
		setReadWrite(document.forms(0).bidStartTime);
		setReadWrite(document.forms(0).bidEndTime);
	}
}

//save
function save_onclick()
{		

	if (confirm("��ȷ��Ҫ�ύ��")) {
		if (!tmp_baseinfo_up) {
			baseinfo_onclick();
		}
		if (!tmp_paraminfo_up) {
			paraminfo_onclick();
		}
		if (document.forms(0).sectionID.value == "") {
			alert("���׽ڱ�Ų���Ϊ�գ�");
			document.forms(0).sectionID.focus();
			return false;
		}
		if (document.forms(0).name.value == "") {
			alert("���׽����Ʋ���Ϊ�գ�");
			document.forms(0).name.focus();
			return false;
		}
		if (document.forms(0).status.value == "") {
			alert("��ǰ���׽�״̬״̬����Ϊ�գ�");
			document.forms(0).status.focus();
			return false;
		}
		if (document.forms(0).gatherBid.value == "") {
			alert("�Ƿ����ü��Ͼ��۲���Ϊ�գ�");
			document.forms(0).gatherBid.focus();
			return false;
		}
		if (document.forms(0).gatherBid.value == "1") {
			if (document.forms(0).bidStartTime.value == "") {
				alert("���Ͼ��ۿ�ʼʱ�䲻��Ϊ�գ�");
				document.forms(0).bidStartTime.focus();
				return false;
			}
			if (document.forms(0).bidStartTime.value.indexOf("��") != "-1") {
				alert("ʱ�䲻����������ð�ţ�");
				return false;
			}
			if (!isTime(document.forms(0).bidStartTime.value)) {
				alert("���Ͼ��ۿ�ʼʱ���ʽ����ȷ��");
				document.forms(0).bidStartTime.focus();
				return false;
			}
			if (document.forms(0).bidEndTime.value == "") {
				alert("���Ͼ��۽���ʱ�䲻��Ϊ�գ�");
				document.forms(0).bidEndTime.focus();
				return false;
			}
			if (document.forms(0).bidEndTime.value.indexOf("��") != "-1") {
				alert("ʱ�䲻����������ð�ţ�");
				return false;
			}
			if (!isTime(document.forms(0).bidEndTime.value)) {
				alert("���Ͼ��۽���ʱ���ʽ����ȷ��");
				document.forms(0).bidEndTime.focus();
				return false;
			}
		}
		if (document.forms(0).startTime.value == "") {
			alert("���׿�ʼʱ�䲻��Ϊ�գ�");
			document.forms(0).startTime.focus();
			return false;
		}
		if (document.forms(0).startTime.value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		if (!isTime(document.forms(0).startTime.value)) {
			alert("���׿�ʼʱ���ʽ����ȷ��");
			document.forms(0).startTime.focus();
			return false;
		}
		if (document.forms(0).endTime.value == "") {
			alert("���׽���ʱ�䲻��Ϊ�գ�");
			document.forms(0).endTime.focus();
			return false;
		}
		if (document.forms(0).endTime.value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		if (!isTime(document.forms(0).endTime.value)) {
			alert("���׽���ʱ���ʽ����ȷ��");
			document.forms(0).endTime.focus();
			return false;
		}
		
		//if (document.forms(0).bidStartTime.value > document.forms(0).startTime.value || document.forms(0).bidStartTime.value > document.forms(0).bidEndTime.value) {
		//	alert("���Ͼ��ۿ�ʼʱ��Ӧ���ڽ��׿�ʼʱ��򼯺Ͼ��۽���ʱ��");
		//	return false;
		//}
		
		if (document.forms(0).tradeTimeType.value == "0") {//ͬһ�콻��
			if (document.forms(0).gatherBid.value == "1" && document.forms(0).bidStartTime.value != "" && document.forms(0).bidEndTime.value != "") {
			var bidStartTimes = document.forms(0).bidStartTime.value.split(":");
			var startTimes = document.forms(0).startTime.value.split(":");
			var bidEndTimes = document.forms(0).bidEndTime.value.split(":");
			
			var dateBST = new Date(0,0,0,bidStartTimes[0],bidStartTimes[1],bidStartTimes[2]);
			var hourBS = dateBST.getHours();
			var minuteBS = dateBST.getMinutes();
			var secondBS = dateBST.getSeconds();
			var relDateBST = parseInt(hourBS)*3600 + parseInt(minuteBS)*60 + parseInt(secondBS);
			
			var dateST = new Date(0,0,0,startTimes[0],startTimes[1],startTimes[2]);
			var hourST = dateST.getHours();
			var minuteST = dateST.getMinutes();
			var secondST = dateST.getSeconds();
			var relDateST = parseInt(hourST)*3600 + parseInt(minuteST)*60 + parseInt(secondST);
			
			var dateBT = new Date(0,0,0,bidEndTimes[0],bidEndTimes[1],bidEndTimes[2]);
			var hourBT = dateBT.getHours();
			var minuteBT = dateBT.getMinutes();
			var secondBT = dateBT.getSeconds();
			var relDateBT = parseInt(hourBT)*3600 + parseInt(minuteBT)*60 + parseInt(secondBT);
			
			if (relDateBST > relDateST || relDateBST > relDateBT || relDateBST == relDateST || relDateBST == relDateBT) {
				alert("���Ͼ��ۿ�ʼʱ��Ӧ���ڽ��׿�ʼʱ��򼯺Ͼ��۽���ʱ�䣡");
				document.forms(0).bidStartTime.focus();
				return false;
			}
			if (relDateBT > relDateST || relDateBT == relDateST) {
				alert("���Ͼ��۽���ʱ��Ӧ���ڽ��׿�ʼʱ�䣡");
				document.forms(0).bidEndTime.focus();
				return false;
			}
			
			var endTimes = document.forms(0).endTime.value.split(":");
			var dateET = new Date(0,0,0,endTimes[0],endTimes[1],endTimes[2]);
			var hourET = dateET.getHours();
			var minuteET = dateET.getMinutes();
			var secondET = dateET.getSeconds();
			var relDateET = parseInt(hourET)*3600 + parseInt(minuteET)*60 + parseInt(secondET);
			if (relDateST > relDateET || relDateST == relDateET) {
				alert("���׿�ʼʱ��Ӧ���ڽ��׽���ʱ�䣡");
				document.forms(0).startTime.focus();
				return false;
			}
			}else if (document.forms(0).gatherBid.value == "0") {
				document.forms(0).bidStartTime.value = "";
				document.forms(0).bidEndTime.value = "";
			}
		}else {
			if (document.forms(0).gatherBid.value == "1") {
				if (document.forms(0).bidStartTime.value > document.forms(0).bidEndTime.value) {
					alert("���Ͼ��ۿ�ʼʱ��Ӧ���ڼ��Ͼ��۽���ʱ�䣡");
					return false;
				}
			}
		}
		
		
		
				
		//alert(minuteBS);
		//alert(secondBS);
		//alert(relDateBS);
		document.forms(0).submit();
    	document.forms(0).save.disabled = false;
	}
		
	
    
 }

//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=search"/>";
}
//�޸�����

var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
  }
}

var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    document.forms(0).paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    document.forms(0).paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}

 function suffixNamePress()
{
	
  if (event.keyCode<=47 || event.keyCode>58)
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="100%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeTime.do?funcflg=save"
						method="POST" styleClass="form">
						
						<fieldset class="pickList">
							<legend class="common">
								<b>���׽ڲ���
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            <fieldset class="pickList">
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>������Ϣ</b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>
									<td align="right">
											���׽ڱ�ţ�
									</td>
									<td>
										<html:text property="sectionID" maxlength="4"
											styleClass="text" onkeypress="onlyNumberInput()" />
										<span class="req">*</span>
									</td>
									
									<td align="right">
											���׽����ƣ�
									</td>
									<td>
										<html:text property="name" maxlength="10"
											styleClass="text"  onkeypress="onlyNumberAndCharInput()" />
										<span class="req">*</span>
									</td>
								
									
								</tr>
								<%
										String type = (String)request.getAttribute("type");
									%>
									<tr>
									<td align="right">
											��ǰ���׽�״̬��
									</td>
									<td>
										<html:select property="status" style="width:150">
											<html:option value=""></html:option>
				                           <html:option value="0">��Ч</html:option>
					                       <html:option value="1">����</html:option>
			                            </html:select>
										<span class="req">*</span>
									</td>
									<td align="right">
											�Ƿ����ü��Ͼ��ۣ�
									</td>
									<td>
										<html:select property="gatherBid" style="width:150" onchange="change()">
										   <html:option value=""></html:option>
				                           <html:option value="0">������</html:option>
					                       <html:option value="1">����</html:option>
			                            </html:select>
										<span class="req">*</span>
									</td>
									
									
									</tr>
								<tr class="<%if("1".equals(type)){%>yin<%}else{%>xian<%}%>" id="aaa">
									<td align="right" id="id1">
											���Ͼ��ۿ�ʼʱ�䣺
									</td>
									<td id="id2" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
										<html:text property="bidStartTime" maxlength="8"
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
									
									<td align="right" id="id3">
											���Ͼ��۽���ʱ�䣺 
									</td>
									<td id="id4" style="visibility:<%if("1".equals(type)){%>hidden<%}else{%>visible<%}%>;">
										<html:text property="bidEndTime" maxlength="8"
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											��ǰ���׽ڿ�ʼʱ�䣺
									</td>
									<td>
										<html:text property="startTime" maxlength="8"
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
									
									<td align="right">
											��ǰ���׽ڽ���ʱ�䣺 
									</td>
									<td>
										<html:text property="endTime" maxlength="8"
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
	 										
</table >
</span>
            </fieldset>
          </td>
        </tr>					
 <c:if test="${param['crud']=='update'}">      	
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            <fieldset class="pickList">
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>��Ʒ��Ϣ</b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="paraminfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="paraminfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">

        			<tr><th>��Ʒ����</th><th>��ӦƷ��</th></tr>	
        			
        			<%
        				List list = (List)request.getAttribute("list");
        				if (list != null && list.size() > 0) {
        					for (int i = 0; i < list.size(); i++) {
        					Map map = (Map)list.get(i);
        					//String breedID = map.get("breedID").toString();
        					String breedName = map.get("breedName").toString();
        					String name = map.get("name").toString();
        				
        				
        			%>
        											
					<tr align="center">
									
									<td>
										<%=name%>
									</td>
				
									<td>
										<%=breedName%>
										
									</td>
					</tr>		
					
					<%
							}
						}
					%>									
</table >
</span>
            </fieldset>
          </td>
        </tr>   
   </c:if>     							
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>																																										
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											����
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
						
						<html:hidden property="crud" />
						 <c:if test="${param['crud']=='update'}">  
						<%
							String modifyTime = (String)request.getAttribute("modifyTime");
						%>
						<font color="red" size="2">�޸�ʱ�䣺<%=modifyTime%></font>
						</c:if>
						<html:hidden property="tradeTimeType"/>
					</html:form>
				</td>
			</tr>
			
		</table>

		
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

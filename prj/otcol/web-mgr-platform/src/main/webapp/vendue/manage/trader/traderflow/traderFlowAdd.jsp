<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<!--ȡ�ò���-->
<!--�ж��û�����������̵�Ԫ��������̵�Ԫ�����Ƿ��Ѿ�����-->
<c:set var="addFlag" value="true"/>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
%>
<db:select var="row" table="v_flowcontrol" columns="<%=COLS_FLOWCONTROL%>" where="unitid=${param.unitID} and unittype=${param.unitType} and tradepartition=${param.tradePartition}">
	<c:set var="addFlag" value="false"/>
</db:select>
<c:choose>
<c:when test="${addFlag=='true'}">
<db:insert table="v_flowcontrol"
  tradepartition="${param.tradePartition}"
  unitid="${param.unitID}"
  unittype="${param.unitType}"
  startmode="${param.startMode}"
  starttime="${param.startTime}"
  durativetime="${param.durativeTime}"
/>
<!--Ϊ��д��־,���������������-->
<db:select var="row" table="v_syspartition" columns="description" where="partitionid=${param.tradePartition}">
  <c:set var="parDes" value="${row.description}"/>
</db:select>
<%
//д��־
String tempParDes=pageContext.getAttribute("parDes").toString();
String unitid=request.getParameter("unitID");
String unitTypePara=request.getParameter("unitType");

String unitType="";
if("1".equals(unitTypePara)){
  unitType="���׽�";
}else if("2".equals(unitTypePara)){
  unitType="��Ϣ��";
}

%>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("����������ӳɹ���");
gotoUrl("traderFlowList.jsp?partitionID=${param.tradePartition}&idx=${param.tradePartition}");
//-->
</SCRIPT>
</c:when>
<c:otherwise>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("���������̵�Ԫ��ź����̵�Ԫ�����Ѿ�ͬʱ��һ����¼�г���,����������.");
gotoUrl("traderFlowAdd.jsp?partitionID=${param.tradePartition}");
//-->
</SCRIPT>
</c:otherwise>
</c:choose>
</c:if>
  <head>
	<title>��ӽ�������</title>
</head>
<body>
<form name=frm id=frm method="post">
		<fieldset width="100%">
		<legend>��ӽ�������</legend>
		<BR>
		<span>
<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
            	<td align="right"> ���װ�飺</td>
                <td align="left">
                	<select disabled>
                		 <db:select var="rowColumn" columns="<%=COLS_SYSPARTITION%>" table="v_syspartition">
                		 <option value="${rowColumn.partitionid}" <c:if test="${rowColumn.partitionid==param.partitionID}">selected</c:if>>${rowColumn.description}</option>
                		 </db:select>
                  </select>
                  <input type="hidden" name="tradePartition" value="${param.partitionID}">
                </td>
        </tr>
        <tr height="35">
                <td align="right"> ���̵�Ԫ��ţ�</td>
                <td align="left">
                	<input name="unitID" type="text" maxlength="5" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'���̵�Ԫ���')">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ���̵�Ԫ���ͣ�</td>
                <td align="left">
                	<select name="unitType" onchange="controlUnitType();">
                		 <option value="1">���׽�</option>
                		 <option value="2">��Ϣ��</option>
                  </select>	
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���׽ڿ�ʼģʽ��</td>
                <td align="left">
                	<select name="startModeOpt" onchange="controlTimeModel();">
                		 <option value="1">����ָ��ʱ���Զ���ʼ</option>
                		 <option value="2">�ֹ���ʼ</option>
                		 <option value="3">���տ��������Զ���ʼ</option>
                    </select>	
                    <input type="hidden" name="startMode">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���׽ڿ�ʼʱ�䣺</td>
                <td align="left">
                	<input name="startTime" type="text" maxlength="32" class="text" style="width: 180px;" onchange="checkHMFormat(this,'���׽ڿ�ʼʱ��')">(HH:MM)
                </td>
               </tr>
               <tr height="35">
                <td align="right"> ���׵�Ԫ����ʱ�䣺</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'���׵�Ԫ����ʱ��')">��
                </td>
              </tr>
        	</table>
			<BR>
        </span>  
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
          	  <input type="hidden" name="add">
			  <input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="gotoUrl();" class="btn" value="����">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoUrl(){
		window.location.href("traderFlowList.jsp?partitionID=${param.partitionID}"); 
	}
function frmChk()
{
	if(Trim(frm.unitID.value) == "")
	{
		alert("���̵�Ԫ��Ų���Ϊ�գ�");
		frm.unitID.focus();
		return false;
	}else if(Trim(frm.tradePartition.value) == "")
	{
		alert("���װ�鲻��Ϊ�գ�");
		frm.tradePartition.focus();
		return false;
	}else if(Trim(frm.unitType.value) == "")
	{
		alert("���̵�Ԫ���Ͳ���Ϊ�գ�");
		frm.unitType.focus();
		return false;
	}else if(Trim(frm.startMode.value) == "")
	{
		alert("���׽ڿ�ʼģʽ����Ϊ�գ�");
		frm.startMode.focus();
		return false;
	}	else if(Trim(frm.durativeTime.value) == "")
	{
		alert("����ʱ�䲻��Ϊ�գ�");
		frm.durativeTime.focus();
		return false;
	}
	else 
	{
	if(frm.unitType.value==1){//ֻ����ӽ��׽ڿ��ƣ�����Ϣ�ڲ�����
	if(frm.startModeOpt.value == 1){
			if(frm.startTime.value==""){
				alert("���׽ڿ�ʼʱ�䲻��Ϊ��");
				frm.startTime.focus();
				return false;
			}
		}	
	}	
	    if(userConfirm()){
		  frm.add.value="add";
		  frm.submit();
		  //return true;
		}else{
		  return false;
		}  
		 
	}
}

//���Ƶ�ѡ��ʼģʽΪ�Զ���ʼ��ʱ��ʼʱ��Ϊ��������,����ʼģʽΪ�ֶ���ʼʱ�������뿪ʼʱ��
function controlTimeModel(){
  if(frm.startModeOpt.value=="2"||frm.startModeOpt.value=="3"){
  	frm.startTime.readOnly=true;
  	frm.startTime.value="";
  	conModeOpt();
  }else if(frm.startModeOpt.value=="1"){
    frm.startTime.readOnly=false;
    conModeOpt();
  }
}

function conModeOpt(){
  frm.startMode.value=frm.startModeOpt.value;
}

//�����̵�Ԫ����Ϊ���׽�ʱ����ģʽ�뽻�׿�ʼʱ�䶼���Ա༭,��Ϊ��Ϣ��ʱ����������Ա༭
function controlUnitType(){
  if(frm.unitType.value==2){
    frm.startModeOpt.disabled=true;
    frm.startTime.value="";
    frm.startTime.readOnly=true;
    frm.startMode.value="-1";
    frm.startTime.disabled=true;
    frm.durativeTime.disabled=true;
  }else{
    frm.startModeOpt.disabled=false;
    frm.startTime.disabled=false;
    frm.durativeTime.disabled=false;
    controlTimeModel();
  }
}
//-->
</SCRIPT>
<script language="javascript">
    if(frm.unitType.value==2){
      frm.startModeOpt.disabled=true;
      frm.startTime.readOnly=true;
    }
	if(frm.startModeOpt.value=="2"||frm.startModeOpt.value=="3"){
       frm.startTime.readOnly=true;
    }else{
      frm.startMode.value=frm.startModeOpt.value;
    }
</script>
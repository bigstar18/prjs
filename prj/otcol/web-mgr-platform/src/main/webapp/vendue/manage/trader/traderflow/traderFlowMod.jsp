<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<!--ȡ�ò���-->
  <head>
	<title>�޸Ľ�������</title>
	<script language="javascript" src="../../js/formInit.js"></script>
</head>
<c:if test="${not empty param.add}">
<%
  java.util.Date curdate = new java.util.Date();
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
   
<db:update table="v_flowcontrol"
    startmode="${param.startMode}"
    starttime="${param.startTime}"
    durativetime="${param.durativeTime}"
    where="unitid=${param.unitID} and unitType=${param.unitType} and  tradepartition=${param.tradePartition}"
/>
<!-Ϊ��д��־,���������������-->
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
	  alert("���̿�����Ϣ�޸ĳɹ���");
	  window.returnValue="1";
	  window.close();
	  //-->
   </script>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>�޸Ľ�������</legend>
		<BR>
		<span>
		<db:select var="row" table="v_flowcontrol" columns="<%=COLS_FLOWCONTROL%>" where="unitid='${param.unitID}'  and unittype='${param.unitType}' and tradepartition=${param.tradePartition}">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
            	<td align="right"> ���װ�� ��</td>
                <td align="left">
                	<select name="" disabled>
                		 <db:select var="rowColumn" columns="<%=COLS_SYSPARTITION%>" table="v_syspartition">
                		 <option value="${rowColumn.partitionid}"
                		 <c:if test="${rowColumn.partitionid==row.tradepartition}"><c:out value="selected"/></c:if>>${rowColumn.description}</option>
                		 </db:select>
                  </select>
                  <input type="hidden" name="tradePartition" value="${row.tradepartition}">
                </td>
        </tr>
        <tr height="35">
                <td align="right"> ���̵�Ԫ��� ��</td>
                <td align="left">
                	<input name="unitID" type="text" maxlength="5" class="text" style="width: 180px;" value="${row.unitid}" readonly>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ���̵�Ԫ���� ��</td>
                <td align="left">
                	<select disabled>
                		 <option value="1" <c:if test="${row.unittype==1}"><c:out value="selected"/></c:if>>���׽�</option>
                		 <option value="2" <c:if test="${row.unittype==2}"><c:out value="selected"/></c:if>>��Ϣ��</option>
                  </select>
                  <input type="hidden" name="unitType"  value="${row.unittype}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���׽ڿ�ʼģʽ ��</td>
                <td align="left">
                	<select name="startModeOpt" onchange="controlTimeModel();">
                		 <option value="1" <c:if test="${row.startmode==1}"><c:out value="selected"/></c:if>>����ָ��ʱ���Զ���ʼ</option>
                		 <option value="2" <c:if test="${row.startmode==2}"><c:out value="selected"/></c:if>>�ֹ���ʼ</option>
                		 <option value="3" <c:if test="${row.startmode==3}"><c:out value="selected"/></c:if>>���տ��������Զ���ʼ</option>
                  </select>
                  <input type="hidden" name="startMode" value="${row.startmode}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���׽ڿ�ʼʱ�� ��</td>
                <td align="left">
                	<input name="startTime" type="text" maxlength="32" class="text" style="width: 180px;" value="${row.starttime}" onchange="startTimeChk();">hh:mm
                </td>
               </tr>
               <tr height="35">
                <td align="right"> ���׵�Ԫ����ʱ�� ��</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" value="${row.durativetime}" onchange="checkNumber(this,false,false,'���׵�Ԫ����ʱ��')">��
                </td>
              </tr>
        	</table>
			<BR>
		</db:select>
		</span>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
              <input type="hidden" name="add">
			  <input type="button" onclick="return frmChk();" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function isTime(val,msg) {
	var str=val.value;
    if(str=="") {
    	alert("������ʱ��!");
    	frm.startTime.focus();
    	return false;
   	} else {
    	if(str.length == 5) {
         	var j=str.indexOf(":");
         	var hour=str.substring(0,j);
			var minute=str.substring(j+1);
         	if(j==-1) {
             	alert(msg+"��ʽ����!");
           		return false;
      		}
         	if (hour>=24||minute>=60) {
         		alert(msg+"��ʽ����!");
       			return false;
     		}
      	} else {
			alert(msg+"��ʽ����!");
           	return false;
       	}
       	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})$/);
   		if (a == null) {
			alert(msg+"��ʽ����"); 
			return false;
		}
		if (a[1]>24||a[3]>60) {
           	alert(msg+"��ʽ����!");
           	return false;
    	}
        return true;
   	}
}





var operate = false;
function startTimeChk(){
	operate = false;
	var s = frm.startTime;
	var ts = isTime(s,'���׽ڿ�ʼʱ��');
	if(ts){
		operate = true;
	}
}
function frmChk()
{ 
	
	if(Trim(frm.unitID.value) == "")
	{
		alert("���̿��ƺŲ���Ϊ�գ�");
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
	}else if(Trim(frm.durativeTime.value) == "")
	{
		alert("����ʱ�䲻��Ϊ�գ�");
		frm.durativeTime.focus();
		return false;
	}
	else 
	{ 
	  if(frm.startModeOpt.value == 1){
		  var str=frm.startTime.value;
			var msg="���׽ڿ�ʼʱ��";
		    if(str=="") {
		    	alert("������ʱ��!");
		    	frm.startTime.focus();
		    	return false;
		   	} else {
		    	if(str.length == 5) {
		         	var j=str.indexOf(":");
		         	var hour=str.substring(0,j);
					var minute=str.substring(j+1);
		         	if(j==-1) {
		             	alert(msg+"��ʽ����!");
		           		return false;
		      		}
		         	if (hour>=24||minute>=60) {
		         		alert(msg+"��ʽ����!");
		       			return false;
		     		}
		      	} else {
					alert(msg+"��ʽ����!");
		           	return false;
		       	}
		       	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})$/);
		   		if (a == null) {
					alert(msg+"��ʽ����"); 
					return false;
				}
				if (a[1]>24||a[3]>60) {
		           	alert(msg+"��ʽ����!");
		           	return false;
		    	}
		   	}
	  	if(!operate){
	  		if(frm.startTime.value==""&&frm.unitType.value==1){//ֻ����ӽ��׽ڿ��ƣ�����Ϣ�ڲ�����
	  			alert("���׽ڿ�ʼʱ�䲻��Ϊ��");
				frm.startTime.focus();
				return false;
	  		}
	  	}
	  }
	  if(userConfirm()){
	    frm.add.value="true";
	    frm.submit();
	    //return true;
	  }else{
	    return false;
	  }
	}
}

function conModeOpt(){
  frm.startMode.value=frm.startModeOpt.value;
}

//���Ƶ�ѡ��ʼģʽΪ�Զ���ʼ��ʱ��ʼʱ��Ϊ��������,����ʼģʽΪ�ֶ���ʼʱ�������뿪ʼʱ��
function controlTimeModel(){
  if(frm.startModeOpt.value=="2"||frm.startModeOpt.value=="3"){
  	frm.startTime.readOnly=true;
  	frm.startTime.value="";
  	conModeOpt();
  	//frm.startMode.value="-1";
  }else if(frm.startModeOpt.value=="1"){
    frm.startTime.readOnly=false;
    conModeOpt();
  }
}

//�����̵�Ԫ����Ϊ���׽�ʱ����ģʽ�뽻�׿�ʼʱ�䶼���Ա༭,��Ϊ��Ϣ��ʱ����������Ա༭
function controlUnitType(){
  if(frm.unitType.value==2){
    frm.startModeOpt.disabled=true;
    frm.startTime.value="";
    frm.startTime.readOnly=true;
    frm.startMode.value="-1";
  }else{
    frm.startModeOpt.disabled=false;
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
	if(frm.startMode.value=="2"||frm.startMode.value=="3"){
      frm.startTime.readOnly=true;
    }
</script>
<%@ include file="/vendue/manage/public/footInc.jsp" %>
<%@ page contentType="text/html;charset=GBK" %>
<base target="post">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--ȡ�ò���-->
  <head>
	<title>�޸�ϵͳ��������</title>
</head>

<c:if test="${not empty param.add}">
<%
try{
    java.util.Date curdate = new java.util.Date();
    java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
<db:update table="v_sysproperty"
    durativetime="${param.durativeTime}"
    spacetime="${param.spaceTime}"
    countdownstart="${param.countdownStart}"
    countdowntime="${param.countdownTime}"
    optmode="${param.optMode}"
    where="tradepartition=${param.tradePartition}"
/>

<%
	  //ˢ���ڴ�
      String partitionID=request.getParameter("tradePartition");
      long durativeTime=Long.parseLong(request.getParameter("durativeTime"));//Ĭ�ϳ���ʱ��
      long spacetime=Long.parseLong(request.getParameter("spaceTime"));//�ڼ���Ϣʱ��
      int countdownStart=Integer.parseInt(request.getParameter("countdownStart"));//����ʱ��ʼʱ��
      int countdownTime=Integer.parseInt(request.getParameter("countdownTime"));//����ʱ˳��ʱ��
            
	  	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":"+port+ "/"+REMOTECLASS+""+partitionID);
      dao.updateSysProperty(durativeTime,spacetime,countdownStart,countdownTime);

%>
     <SCRIPT LANGUAGE='JavaScript'>
        alert('ϵͳ���������޸ĳɹ�!');
        window.returnValue="1";
        window.close();
     </script>
<%
}
catch(Exception e)
{
  e.printStackTrace();
  errOpt();
}		
%>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>�޸�ϵͳ����������Ϣ</legend>
		<BR>
		<span>
		<db:select var="row" table="v_sysproperty" columns="<%=COLS_SYSPROPERTY%>" where="tradepartition='${param.partitionID}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
            	<td align="right"> ���װ�飺</td>
                <td align="left">
                	<select disabled>
                		 <db:select var="rowColumn" columns="<%=COLS_SYSPARTITION%>" table="v_syspartition" where="partitionid=${param.partitionID}">
                		 <option value="${rowColumn.partitionid}">${rowColumn.description}</option>
                		 </db:select>
                        </select>	
                   <input type="hidden" name="tradePartition" value="${param.partitionID}">
                </td>
        </tr>
			   <tr height="35">
            	<td align="right"> ���׽�Ĭ�ϳ���ʱ�䣺</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" value="${row.durativetime}" onchange="checkNumber(this,false,false,'���׽�Ĭ�ϳ���ʱ��')">��
                </td>
         </tr>
        <tr height="35">
                <td align="right"> �ڼ���Ϣʱ�䣺</td>
                <td align="left">
                	<input name="spaceTime" type="text" maxlength="6" class="text" style="width: 180px;" value="${row.spacetime}" onchange="checkNumber(this,false,false,'�ڼ���Ϣʱ��')">��
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ����ʱ��ʼʱ�䣺</td>
                <td align="left">                	
					<input name="countdownStart" type="text" class="text"  style="width: 180px;" value="${row.countdownstart}" onchange="changeSar(this.value)">��
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ����ʱ˳��ʱ�䣺</td>
                <td align="left">
                	<input name="countdownTime"  readonly="true" type="text" class="text" style="width: 180px;" value="${row.countdowntime}" onchange="checkNumber(this,false,false,'����ʱ˳��ʱ��')">��
                </td>
              </tr>
               <input type="hidden" name="optMode" value="1">
        	</table>
			<BR>
        </span>
		</db:select>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
              <input type="hidden" name="add">
			  <input type="button" onclick="return frmChk(${param.partitionID})" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk(partitionID)
{ 
	if(Trim(frm.tradePartition.value) == "")
	{
		alert("���Ų���Ϊ�գ�");
		frm.tradePartition.focus();
		return false;
	}else if(Trim(frm.durativeTime.value) == "")
	{
		alert("���׽�Ĭ�ϳ���ʱ�䲻��Ϊ�գ�");
		frm.durativeTime.focus();
		return false;
	}else if(Trim(frm.spaceTime.value) == "")
	{
		alert("�ڼ���Ϣʱ�䲻��Ϊ�գ�");
		frm.spaceTime.focus();
		return false;
	}
	else if(Trim(frm.countdownStart.value) == "")
	{
		alert("����ʱ��ʼʱ�䲻��Ϊ�գ�");
		frm.countdownStart.focus();
		return false;
	}
	else if(Trim(frm.countdownTime.value) == "")
	{
		alert("����ʱ˳��ʱ�䲻��Ϊ�գ�");
		frm.countdownTime.focus();
		return false;
	}else if(Trim(frm.optMode.value) == "")
	{
		alert("����ʱ����ģʽ����Ϊ�գ�");
		frm.optMode.focus();
		return false;
	}
	else if(isNaN(new Number(frm.countdownStart.value)))
	{
		alert("����ʱ��ʼʱ�䲻�ǺϷ���ֵ");
		frm.countdownStart.focus();
		return false;
	}
	else if(partitionID==3&&Trim(frm.durativeTime.value)!=Trim(frm.countdownStart.value))
	{
		alert("�б�ģʽ���׽ڳ���ʱ��͵���ʱ��ʼʱ��������");
		frm.countdownStart.value="";
		frm.countdownStart.focus();
		return false;
	}
	else 
	{ 
	  if(userConfirm()){
	    frm.add.value="true";
		frm.submit();
	  }else{
	    return false;
	  }
	}
}
function changeSar(time)
{
	frm.countdownTime.value=time;
}
//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>
<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

  <head>
	<title>��ӹ㲥��Ϣ</title>
</head>

<!--ȡ�ò���-->
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
String loginID = AclCtrl.getLogonID(request);
%>

<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
%>
<db:select var="row" table="dual" columns="SP_V_BROADCAST.nextval as seqID">
<c:set var="id" value="${row.seqID}"/>
</db:select>
<!--���ݷ�����ʱ��-->
<c:set var="sysdate" value=""/>
<db:select var="v_result" table="dual" columns="sysdate">
    <c:set var="sysdate" value="${v_result.sysdate}"/>
</db:select>
<%
  String sendtime=request.getParameter("sendtime");
  java.sql.Date a=null;
  java.sql.Date convertDate=a.valueOf(sendtime);
  String endtime=request.getParameter("endtime");
  java.sql.Date convertEndDate=a.valueOf(endtime);
  Timestamp now_dt = new Timestamp(System.currentTimeMillis());

if((convertEndDate.getTime()/(1000*60*60*24)-convertDate.getTime()/(1000*60*60*24))<0)
  {
	  %>
	<SCRIPT LANGUAGE="JavaScript">
		alert('�㲥��Ϣ���ʧ��');
	</SCRIPT>
<%
  }else{
%>
  	<db:insert table="v_broadcast"
  id="${id}"
  title="${param.title}"
  author="${param.author}"
  content="${param.content}"
  type="${param.type}"
  sendtime="<%=convertDate%>"
  endtime="<%=convertEndDate%>"
  createtime="<%=now_dt %>"
  updatetime="<%=now_dt %>"
/>

   <SCRIPT LANGUAGE="JavaScript">
   <!--
   alert("�㲥��Ϣ��ӳɹ���");
   gotoUrl("broadcastList.jsp");
   //-->
   </SCRIPT>
  <%
	}
%>
</c:if>



<body>
<form name=frm id=frm method="post">
		<fieldset width="100%">
		<legend>��ӹ㲥��Ϣ</legend>
		<BR>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
                	<input name="title" type="text" maxlength="256" class="text" style="width: 180px;">
                </td>
        </tr>
		<tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                	<input name="author" type="text" maxlength="32" class="text" style="width: 180px;" value="<%=loginID%>" readonly>
                </td>
        </tr>
		<tr height="35">
                <td align="right"> ��� ��</td>
                <td align="left">
                	<input name="type" type="text" maxlength="3" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'���')">
               		<font size="2" color="red">�������벻����3λ����ֵ��</font>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���Ϳ�ʼʱ�䣺</td>
                <td align="left">
                	<MEBS:calendar eltID="sendtime" eltName="sendtime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=sqlDate%>"/>
                </td>
               </tr>
               <tr height="35">
                <td align="right"> ���ͽ���ʱ�䣺</td>
                <td align="left">
                	<MEBS:calendar eltID="endtime" eltName="endtime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=sqlDate%>"/>
                </td>
               </tr>
               <tr height="35">       
                <td align="right"> ���� ��</td>
                <td align="left">
                	<textarea name="content" class="normal" onkeyup='value=value.substr(0,1024);this.nextSibling.innerHTML=value.length+"/1024";'></textarea><div>0/1024</div>
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
			  <input name="back" type="button" onclick="gotoUrl('broadcastList.jsp');" class="btn" value="����">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{
	if(Trim(frm.title.value) == "")
	{
		alert("���ⲻ��Ϊ�գ�");
		frm.title.focus();
		return false;
	}
	else if(Trim(frm.sendtime.value) == "")
	{
		alert("���Ϳ�ʼ���ڲ���Ϊ�գ�");
		frm.sendtime.focus();
		return false;
	}
	else if(Trim(frm.endtime.value) == "")
	{
		alert("���ͽ������ڲ���Ϊ�գ�");
		frm.endtime.focus();
		return false;
	}
	else if(Trim(frm.endtime.value) < Trim(frm.sendtime.value))
	{
		alert("���ͽ������ڲ������ڷ��Ϳ�ʼ���ڣ�");
		frm.endtime.focus();
		return false;
	}
	else 
	{
	   if(userConfirm()){
		 frm.add.value="add";
		 frm.submit();
		 //return true;
	   }else{
	     return false; 
	   }
	}
}

//���ƴ��������û���ѡ��
    function checkTradeUser(){
      if(document.frm.tradeUserCheck.checked){
        document.frm.tradeUser.value="true";	
      }
    }
//-->
</SCRIPT>

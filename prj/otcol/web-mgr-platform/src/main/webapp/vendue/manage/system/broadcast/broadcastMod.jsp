<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

  <head>
	<title>�޸Ĺ㲥��Ϣ</title>
</head>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
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
  
  if((convertEndDate.getTime()/(1000*60*60*24)-convertDate.getTime()/(1000*60*60*24))<0)
  {
	  %>
	<SCRIPT LANGUAGE="JavaScript">
	alert('�㲥��Ϣ�޸�ʧ��');
	window.close();
	</SCRIPT>
<%
  }else{
%>
  	<db:update table="v_broadcast"
    title="${param.title}"
    author="${param.author}"
    content="${param.content}"
    type="${param.type}"
    sendtime="<%=convertDate%>"
    endtime="<%=convertEndDate%>"
    updatetime="${sysdate}"
    where="id=${param.id}"
    />

   <SCRIPT LANGUAGE="JavaScript">
   <!--
   alert("�㲥��Ϣ�޸ĳɹ���");
   window.returnValue="1";
   window.close();
   //-->
   </SCRIPT>
  <%
	}
%>
</c:if>



<body>
<form name=frm id=frm action="" method="post" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>�޸Ĺ㲥��Ϣ</legend>
		<BR>
		<span>
		<db:select var="row" table="v_broadcast" columns="<%=COLS_BROADCAST%>" where="id='${param.id}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<input type="hidden" name="id" value="${row.id}">
				<input type="hidden" name="oldtitle" value="${row.title}">
				<input type="hidden" name="oldsendtime" value="${row.sendtime}">
				<tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
                	<input name="title" type="text" maxlength="256" class="text" style="width: 180px;" value="${row.title}">
                </td>
        </tr>
              <tr height="35">
                <td align="right"> ���Ϳ�ʼʱ�䣺</td>
                <td align="left">
                	<MEBS:calendar eltID="sendtime" eltName="sendtime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="${row.sendtime}"/>
                </td>
               </tr>
               <tr height="35">
                <td align="right"> ���ͽ���ʱ�䣺</td>
                <td align="left">
                	<MEBS:calendar eltID="endtime" eltName="endtime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="${row.endtime}"/>
                </td>
               </tr>
        <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                	<input name="author" type="text" maxlength="32" class="text" style="width: 180px;" value="${row.author}">
                </td>
        </tr>
              <tr height="35">
                <td align="right"> ��� ��</td>
                <td align="left">
                	<input name="type" type="text" class="text" maxlength="3" style="width: 180px;" value="${row.type}" onchange="checkNumber(this,false,false,'���')">
                	<font size="2" color="red">�������벻����3λ����ֵ��</font>
                </td>
              </tr>
               <tr height="35">       
                <td align="right"> ���� ��</td>
                <td align="left">
                <textarea name="content" class="normal" onkeyup='value=value.substr(0,1024);this.nextSibling.innerHTML=value.length+"/1024";'>${row.content}</textarea><div>0/1024</div>
                </td>
              </tr>
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
			  <input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
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
	} else if(Trim(frm.sendtime.value) == "")
	{
		alert("�������ڲ���Ϊ�գ�");
		frm.sendtime.focus();
		return false;
	}
	else 
	{ 
	  if(userConfirm()){
	    frm.add.value="true";
		frm.submit();
	  }else {
	    return false;
	  }
	}
}
//-->
function dismantleMark(id,code,partitionID,amount){
  var a=openDialog("dismantleMark.jsp?id="+id+"&code="+code+"&partitionID="+partitionID+"&amount="+amount+"","_blank","400","550");
}
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="common/util.jsp" %>
<%
    String reportTiltle = "��������ʷ�ɽ�����";
%>
<html xmlns:MEBS>
<head>
<%
	List CateGoryIdList=getList("select id,name from m_firmcategory order by id");  
	pageContext.setAttribute("CateGoryIdList",CateGoryIdList);
%>
<script language="javascript">

function onloaddate(){
	var month=new Date().getMonth()+1;
	if(month.toString().length==1){
		month="0"+month;
	}
	var day=new Date().getDate();
	if(day.toString().length==1){
		day="0"+day;
	}	
	document.getElementById("zDate").value=new Date().getYear()+'-'+month+'-'+day;
	document.getElementById("zEndDate").value=new Date().getYear()+'-'+month+'-'+day;
	
}
</script>
</head>

<body  onload="onloaddate();">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0"  width="60%" align="center">
<tr><td>
<fieldset class="pickList" >
	<legend class="common">
		<b><%=reportTiltle %></b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
	  <tr>
        <td align="right">��ʼ�����̣�</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" style="ime-mode:disabled" value="" size="15" maxlength="4" onblur="fandealcontent(this)" onkeypress="notSpace()" >
         <SELECT name="select5" onchange="dealcontent(this)"  >
	         <OPTION value="">��ѡ��</OPTION>
	         <c:forEach items="${brokerAscList}" var="result">
		     <option value="${result["BROKERID"]}">${result["BROKERID"]}</option>
			 </c:forEach>
          </SELECT>
        </td>
      </tr>
      <tr>
        <td align="right">���������̣�</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" style="ime-mode:disabled"  value="" size="15" maxlength="4" onblur="fandealcontent1(this)" onkeypress="notSpace()">
      
             <SELECT  name="select6" onchange="dealcontent1(this)">
	            <OPTION value="">��ѡ��</OPTION>
	            <c:forEach items="${brokerDescList}" var="result">
		        <option value="${result["BROKERID"]}">${result["BROKERID"]}</option>
		    </c:forEach>
          </SELECT>
            </div></td>
      </tr>
 	<tr>
        <td align="right">��ʼ�����̣�</td>
        <td>
        <INPUT TYPE="text" NAME="zFirmStart" style="ime-mode:disabled" value="" size="15" maxlength="32" onkeypress="notSpace()" >
       
        </td>
      </tr>
      <tr>
        <td align="right">���������̣�</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zFirmEnd" style="ime-mode:disabled"  value="" size="15" maxlength="32" onkeypress="notSpace()">
            </div></td>
      </tr>
	  <tr>
	        <td align="right">���������</td>
	        <td>
	        <INPUT TYPE="text" NAME="cateGoryId" value="" style="ime-mode:disabled" size="15" maxlength="10"  onkeypress="notSpace()" id="cateGoryId" onblur="fandealcontentCateGory(this)">
	         <SELECT name="select8" id="cateGorySel"  onchange="dealcontentCateGory(this)">
	         <OPTION value="">��ѡ��</OPTION>
	         <c:forEach items="${CateGoryIdList}" var="result">
		     <option value="${result.id}">${result.name}</option>
			 </c:forEach>
	          </SELECT>   
			  <span class="req"></span>
	        </td>
       </tr> 
      <tr>
        <td align="right">��ʼ���ڣ�</td>
        <td >
        	<input type="text" style="width: 100px" 
				class="wdate" maxlength="10" name="zDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
      <tr>
        <td align="right">�������ڣ�</td>
        <td >
        	<input type="text" style="width: 100px" 
				class="wdate" maxlength="10" name="zEndDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
    </table>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<rightButton:rightButton name="�鿴" onclick="return querybft('pdf')" className="btn_sec" action="${basePath}/timebargain/brokerFirmTradeReport/brokerFirmTradeReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="����Ϊexcel" onclick="return querybft('excel')" className="btn_sec1" action="${basePath}/timebargain/brokerFirmTradeReport/brokerFirmTradeReportExcel.action" id="saveExcel"></rightButton:rightButton>
	     		</td>
		</tr>
	</table>
	</fieldset>
	</td>
	</tr>
</table>
</FORM>
</body>
</html>
<script language="javascript">
function dealcontent(sel)
{
    document.forms[0].zStart.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent(sel)
{

    var len=document.forms[0].select5.options.length;

    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select5.options[i].value-sel.value==0)
        {
            document.forms[0].select5.options[i].selected=true;
            break;
        }
    }
}

function dealcontent1(sel)
{
    document.forms[0].zEnd.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent1(sel)
{

    var len=document.forms[0].select6.options.length;

    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select6.options[i].value-sel.value==0)
        {
            document.forms[0].select6.options[i].selected=true;
            break;
        }
    }
}
function dealcontentCateGory(sel)
{
    document.forms[0].cateGoryId.value=sel.options[sel.selectedIndex].innerHTML;
}
function fandealcontentCateGory(sel)
{

    var len=document.forms[0].select8.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select8.options[i].value-sel.value==0)
        {
            document.forms[0].select8.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}
function querybft(exportTo)
{
	var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;
	var zEndDate=document.forms[0].zEndDate.value;
	var zFirmStart=document.forms[0].zFirmStart.value;
    var zFirmEnd=document.forms[0].zFirmEnd.value;
    var cateGoryId = document.getElementById("cateGorySel").value;

    if(zStart == "" && zEnd !=""){
		alert("��ʼ�����̲���Ϊ�գ�");
		return false;
	}
	if(zStart != "" && zEnd ==""){
		alert("���������̲���Ϊ�գ�");
		return false;
	}
	if(zFirmStart == "" && zFirmEnd != ""){
		alert("��ʼ�����̲���Ϊ�գ�");
		return false;
	}
	if(zFirmStart != "" && zFirmEnd == ""){
		alert("���������̲���Ϊ�գ�");
		return false;
	}
	if (zDate == "") {
		alert("��ʼ���ڲ���Ϊ�գ�");
		document.forms[0].zDate.focus();
		return false;
	}
	if (zEndDate == "") {
		alert("�������ڲ���Ϊ�գ�");
		document.forms[0].zEndDate.focus();
		return false;
	}
	if (zDate > zEndDate) {
		alert("��ʼ���ڲ��ܴ��ڽ������ڣ�");
		return false;
	}

		 
	if(exportTo=="excel"){
		var url = "${mgrPath}/app/timebargain/printReport/brokerFirmTradeReportExcel.jsp?startBrokerID="+zStart +"&endBrokerID="+ zEnd +"&startFirmID="+zFirmStart+"&endFirmID="+zFirmEnd+"&beginDate=" + zDate + "&endDate=" + zEndDate + "&title=��������ʷ�ɽ�����&cateGoryId="+cateGoryId;
		document.location.href = url;
	}
	else{
		var url = "${mgrPath}/app/timebargain/printReport/brokerFirmTradeReport.jsp?startBrokerID="+zStart +"&endBrokerID="+ zEnd +"&startFirmID="+zFirmStart+"&endFirmID="+zFirmEnd+"&beginDate=" + zDate + "&endDate=" + zEndDate + "&title=��������ʷ�ɽ�����&cateGoryId="+cateGoryId;
		showDialog(url, "", 900, 650);
	}
}

</script>

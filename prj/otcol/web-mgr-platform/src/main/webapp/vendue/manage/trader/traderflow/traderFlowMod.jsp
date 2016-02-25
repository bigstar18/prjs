<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<!--取得参数-->
  <head>
	<title>修改交易流程</title>
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
<!-为了写日志,查出所属板块的描述-->
<db:select var="row" table="v_syspartition" columns="description" where="partitionid=${param.tradePartition}">
  <c:set var="parDes" value="${row.description}"/>
</db:select>
<%
//写日志
String tempParDes=pageContext.getAttribute("parDes").toString();
String unitid=request.getParameter("unitID");
String unitTypePara=request.getParameter("unitType");

String unitType="";
if("1".equals(unitTypePara)){
  unitType="交易节";
}else if("2".equals(unitTypePara)){
  unitType="休息节";
}

%>
   <SCRIPT LANGUAGE="JavaScript">
	 	<!--
	  alert("流程控制信息修改成功！");
	  window.returnValue="1";
	  window.close();
	  //-->
   </script>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>修改交易流程</legend>
		<BR>
		<span>
		<db:select var="row" table="v_flowcontrol" columns="<%=COLS_FLOWCONTROL%>" where="unitid='${param.unitID}'  and unittype='${param.unitType}' and tradepartition=${param.tradePartition}">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
            	<td align="right"> 交易板块 ：</td>
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
                <td align="right"> 流程单元编号 ：</td>
                <td align="left">
                	<input name="unitID" type="text" maxlength="5" class="text" style="width: 180px;" value="${row.unitid}" readonly>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 流程单元类型 ：</td>
                <td align="left">
                	<select disabled>
                		 <option value="1" <c:if test="${row.unittype==1}"><c:out value="selected"/></c:if>>交易节</option>
                		 <option value="2" <c:if test="${row.unittype==2}"><c:out value="selected"/></c:if>>休息节</option>
                  </select>
                  <input type="hidden" name="unitType"  value="${row.unittype}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 交易节开始模式 ：</td>
                <td align="left">
                	<select name="startModeOpt" onchange="controlTimeModel();">
                		 <option value="1" <c:if test="${row.startmode==1}"><c:out value="selected"/></c:if>>到达指定时间自动开始</option>
                		 <option value="2" <c:if test="${row.startmode==2}"><c:out value="selected"/></c:if>>手工开始</option>
                		 <option value="3" <c:if test="${row.startmode==3}"><c:out value="selected"/></c:if>>按照控制流程自动开始</option>
                  </select>
                  <input type="hidden" name="startMode" value="${row.startmode}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 交易节开始时间 ：</td>
                <td align="left">
                	<input name="startTime" type="text" maxlength="32" class="text" style="width: 180px;" value="${row.starttime}" onchange="startTimeChk();">hh:mm
                </td>
               </tr>
               <tr height="35">
                <td align="right"> 交易单元持续时间 ：</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" value="${row.durativetime}" onchange="checkNumber(this,false,false,'交易单元持续时间')">秒
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
			  <input type="button" onclick="return frmChk();" class="btn" value="保存">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
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
    	alert("请输入时间!");
    	frm.startTime.focus();
    	return false;
   	} else {
    	if(str.length == 5) {
         	var j=str.indexOf(":");
         	var hour=str.substring(0,j);
			var minute=str.substring(j+1);
         	if(j==-1) {
             	alert(msg+"格式有误!");
           		return false;
      		}
         	if (hour>=24||minute>=60) {
         		alert(msg+"格式有误!");
       			return false;
     		}
      	} else {
			alert(msg+"格式有误!");
           	return false;
       	}
       	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})$/);
   		if (a == null) {
			alert(msg+"格式有误"); 
			return false;
		}
		if (a[1]>24||a[3]>60) {
           	alert(msg+"格式有误!");
           	return false;
    	}
        return true;
   	}
}





var operate = false;
function startTimeChk(){
	operate = false;
	var s = frm.startTime;
	var ts = isTime(s,'交易节开始时间');
	if(ts){
		operate = true;
	}
}
function frmChk()
{ 
	
	if(Trim(frm.unitID.value) == "")
	{
		alert("流程控制号不能为空！");
		frm.unitID.focus();
		return false;
	}else if(Trim(frm.tradePartition.value) == "")
	{
		alert("交易板块不能为空！");
		frm.tradePartition.focus();
		return false;
	}else if(Trim(frm.unitType.value) == "")
	{
		alert("流程单元类型不能为空！");
		frm.unitType.focus();
		return false;
	}else if(Trim(frm.startMode.value) == "")
	{
		alert("交易节开始模式不能为空！");
		frm.startMode.focus();
		return false;
	}else if(Trim(frm.durativeTime.value) == "")
	{
		alert("持续时间不能为空！");
		frm.durativeTime.focus();
		return false;
	}
	else 
	{ 
	  if(frm.startModeOpt.value == 1){
		  var str=frm.startTime.value;
			var msg="交易节开始时间";
		    if(str=="") {
		    	alert("请输入时间!");
		    	frm.startTime.focus();
		    	return false;
		   	} else {
		    	if(str.length == 5) {
		         	var j=str.indexOf(":");
		         	var hour=str.substring(0,j);
					var minute=str.substring(j+1);
		         	if(j==-1) {
		             	alert(msg+"格式有误!");
		           		return false;
		      		}
		         	if (hour>=24||minute>=60) {
		         		alert(msg+"格式有误!");
		       			return false;
		     		}
		      	} else {
					alert(msg+"格式有误!");
		           	return false;
		       	}
		       	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})$/);
		   		if (a == null) {
					alert(msg+"格式有误"); 
					return false;
				}
				if (a[1]>24||a[3]>60) {
		           	alert(msg+"格式有误!");
		           	return false;
		    	}
		   	}
	  	if(!operate){
	  		if(frm.startTime.value==""&&frm.unitType.value==1){//只对添加交易节控制，对休息节不控制
	  			alert("交易节开始时间不能为空");
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

//控制当选择开始模式为自动开始如时开始时间为不能输入,当开始模式为手动开始时可以输入开始时间
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

//当流程单元类型为交易节时交易模式与交易开始时间都可以编辑,当为休息节时这两项都不可以编辑
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
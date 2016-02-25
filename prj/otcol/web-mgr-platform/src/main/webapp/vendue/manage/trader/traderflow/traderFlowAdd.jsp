<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<!--取得参数-->
<!--判断用户所输入的流程单元编号与流程单元类型是否已经存在-->
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
<!--为了写日志,查出所属板块的描述-->
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
alert("交易流程添加成功！");
gotoUrl("traderFlowList.jsp?partitionID=${param.tradePartition}&idx=${param.tradePartition}");
//-->
</SCRIPT>
</c:when>
<c:otherwise>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("所输入流程单元编号和流程单元类型已经同时在一条记录中出现,请重新输入.");
gotoUrl("traderFlowAdd.jsp?partitionID=${param.tradePartition}");
//-->
</SCRIPT>
</c:otherwise>
</c:choose>
</c:if>
  <head>
	<title>添加交易流程</title>
</head>
<body>
<form name=frm id=frm method="post">
		<fieldset width="100%">
		<legend>添加交易流程</legend>
		<BR>
		<span>
<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
            	<td align="right"> 交易板块：</td>
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
                <td align="right"> 流程单元编号：</td>
                <td align="left">
                	<input name="unitID" type="text" maxlength="5" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'流程单元编号')">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 流程单元类型：</td>
                <td align="left">
                	<select name="unitType" onchange="controlUnitType();">
                		 <option value="1">交易节</option>
                		 <option value="2">休息节</option>
                  </select>	
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 交易节开始模式：</td>
                <td align="left">
                	<select name="startModeOpt" onchange="controlTimeModel();">
                		 <option value="1">到达指定时间自动开始</option>
                		 <option value="2">手工开始</option>
                		 <option value="3">按照控制流程自动开始</option>
                    </select>	
                    <input type="hidden" name="startMode">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 交易节开始时间：</td>
                <td align="left">
                	<input name="startTime" type="text" maxlength="32" class="text" style="width: 180px;" onchange="checkHMFormat(this,'交易节开始时间')">(HH:MM)
                </td>
               </tr>
               <tr height="35">
                <td align="right"> 交易单元持续时间：</td>
                <td align="left">
                	<input name="durativeTime" type="text" maxlength="8" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'交易单元持续时间')">秒
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
			  <input type="button" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="gotoUrl();" class="btn" value="返回">&nbsp;&nbsp;
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
		alert("流程单元编号不能为空！");
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
	}	else if(Trim(frm.durativeTime.value) == "")
	{
		alert("持续时间不能为空！");
		frm.durativeTime.focus();
		return false;
	}
	else 
	{
	if(frm.unitType.value==1){//只对添加交易节控制，对休息节不控制
	if(frm.startModeOpt.value == 1){
			if(frm.startTime.value==""){
				alert("交易节开始时间不能为空");
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

//控制当选择开始模式为自动开始如时开始时间为不能输入,当开始模式为手动开始时可以输入开始时间
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

//当流程单元类型为交易节时交易模式与交易开始时间都可以编辑,当为休息节时这两项都不可以编辑
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
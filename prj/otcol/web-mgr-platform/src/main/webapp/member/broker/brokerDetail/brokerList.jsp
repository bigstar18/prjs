<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>

<style>
	checkbox_1
	{
		border-width: 0px;	
		border-left-style: none;
		border-right-style: none;
		border-top-style: none;
		border-bottom-style: none;
		font-family: SimSun;
		font-size: 9pt;
		color: #333333;
		background-color: transparent;
	}
    </style>



<html xmlns:MEBS>
	<head>
	<import namespace="MEBS" implementation="<%=basePath%>/public/calendar.htc">
	<title>系统用户浏览</title>
	<script type="text/javascript">
		//默认查询设置
		function initQuery(){
			if( frm_query.pageSize.value == null || frm_query.pageSize.value == "" || frm_query.pageSize.value == "null"){
				frm_query.action="<%=brokerControllerPath %>brokerList";
				frm_query.submit();
			}
		}
	</script>
</head>
<body onload="initQuery();">
<form method='post' name=frm_query action="<%=brokerControllerPath %>brokerList" callback="closeDialog(1);" targetType="hidden">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
    
		<fieldset width="100%">
		<legend><%=BROKER%>信息查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35" width="100%">
            	<td align="right" width="15%"> <%=BROKERID%>：</td>
                <td align="left">
                	<input name="_b.brokerid[like]" id="brokerid" type="text" class="text" style="width: 150px;" value="${oldParams['b.brokerid[like]']}" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right" width="15%"> 交易商代码：</td>
                <td align="left">
                	<input name="_fb.firmid[=]" id="firmId" type="text" class="text" style="width: 150px;" value="${oldParams['fb.firmid[=]']}" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                
				
              </tr>
              <tr height="35" width="100%">
					<td align="right" width="10%">所在省份：&nbsp;</td>
					<td align="left">
						<input id="locationProvince" name="_locationProvince[like]" value="<c:out value='${oldParams["locationProvince[like]"]}'/>" type=text class="text" style="width:100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right" width="10%">名称：&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16"">
					</td>
				</tr>
				<tr height="35" width="100%">
					<td align="right" width="10%">市场开发人员：&nbsp;</td>
					<td align="left">
						<input id="marketManager" name="_marketManager[like]" value="<c:out value='${oldParams["marketManager[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16"">
					</td>
					<td align="right" width="10%">创建日期：</td>
					<td align="left" colspan="6">
						<MEBS:calendar eltName="_trunc(b.createDate)[=][date]" eltCSS="date" eltID="scleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(b.createDate)[=][date]"]}'/>"/>
					</td>
					<td align="left"  width="50%">
                	&nbsp;&nbsp;<input type="button" onclick="queryInfo();" class="btn" value="查询">&nbsp;&nbsp;
                <!-- add by yangpei  增加重置功能 -->
                	<input type="button" onclick="resetForm();" class="btn" value="重置">&nbsp;&nbsp;
               		<script type="text/javascript">
               			function resetForm(){
               				frm_query.brokerid.value = "";
               				frm_query.firmId.value = "";
               				frm_query.locationProvince.value= "";
               				frm_query.marketManager.value = "";
               				frm_query.name.value = "";
               				frm_query.scleardate.value = "";
               			}
               		</script>
                </td>
				</tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  			    <td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input class=checkbox_1 type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			        <td class="panel_tHead_MB" align=left><%=BROKERID%> </td>
			        <td class="panel_tHead_MB" align=left>交易商代码 </td>
					<td class="panel_tHead_MB" align=left>名称 </td>
			        <td class="panel_tHead_MB" align=left>电话</td>
			        <td class="panel_tHead_MB" align=left>手机</td>
			        <td class="panel_tHead_MB" align=left>电子邮件</td>
				    <td class="panel_tHead_MB" align=left>地址</td>
				    <td class="panel_tHead_MB" align=left>创建日期</td>
				    <td class="panel_tHead_MB" align=left>市场开发人员 </td>
				    <td class="panel_tHead_MB" align=left>所在省份</td>
				    <td class="panel_tHead_MB" align=left>权限设置</td>
				    <td class="panel_tHead_MB" align=left>所辖交易商</td>
				    <td class="panel_tHead_MB" align=left>密码修改</td>			      		   
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:forEach items="${resultList}" var="result">
					<tr onclick="selectTr();" align=center height="25">
							<td class="panel_tBody_LB">&nbsp;</td>
							<td class="underLine" align=left><input name="ck" type="checkbox" value="${result.brokerid}"></td>
							<td class="underLine" align=left><a href="javascript:editRoleInfo('${result.brokerid}');" class="normal">${result.brokerid}</a></td>
							<td class="underLine" align=left>&nbsp;${result.firmId}</td>
							<td class="underLine" align=left>&nbsp;${result.name}</td>
							<td class="underLine" align=left>&nbsp;${result.telephone}</td>
							<td class="underLine" align=left>&nbsp;${result.mobile}</td>
							<td class="underLine" align=left>&nbsp;${result.email}</td>
							<td class="underLine" align=left>&nbsp;${result.address}</td>
							<td class="underLine" align=left>&nbsp;
							<c:if test="${result.createDate!=null}">
							<fmt:formatDate value="${result.createDate}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							</td>
							<td class="underLine" align=left>&nbsp;${result.marketManager}</td>
							<td class="underLine" align=left>&nbsp;${result.locationProvince}</td>
							<td class="underLine" align=left>
							  <a href="javascript:addRight('${result.brokerid}');" class="nomal">查看</a>
							</td>
							<td class="underLine" align=left>
							  <a href="javascript:mngUser('${result.brokerid}');" class="nomal">查看</a>
							</td>
							<td class="underLine" align=left>
							  <a href="javascript:modPwd('${result.brokerid}');" class="nomal">修改</a>
							</td>							
							<td class="panel_tBody_RB">&nbsp;</td>
						</tr>
							</c:forEach>
		  	</tBody>
<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="14">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="14" align="right" class="pagerext">
							<%@ include file="../../public/pagerInc.jsp" %>
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="14"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="right">
			    <input type="button" onclick="javascript:window.location='<%=brokerControllerPath %>brokerAddForward'" class="btn" value="添加">&nbsp;&nbsp;
  			    <input type="button" onclick="return deletefrmChk(frm_query,tb);" class="btn" value="删除">
  			    <input type="button" onclick="exportExce();" class="btn" value="导出报表">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          <input type="hidden" name="rightList" value=""/>
          </td>
          </tr>
     </table>	 
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function keyChk()
{
	if(event.keyCode == 13)
	{
		var idx = frm_query.pageJumpIdx.value;
		if(!isNaN(idx) && idx <= 0 && idx >= 1)
		{			
			frm_query.pageIndex.value = idx;
			//return true;
			frm_query.submit();
		}
		else
		{
			alert("请输入1-"+"0"+"之间的整数！");
			return false;
		}
	}
}

function deletefrmChk(frm_delete,tableList)
{
	if(isSelNothing(tableList,"ck") == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(tableList,"ck"))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("您确实要操作选中数据吗？"))
	{	
		frm_query.action="<%=brokerControllerPath %>brokerDelete";
		frm_query.submit();
	}
	else
	{
		return false;
	}
}

function keyChkSetPageSize()
{
	if(event.keyCode == 13)
	{
		var idx = frm_query.pageSize.value;
		if(!isNaN(idx) && idx>0)
		{			
			frm_query.pageSize.value = idx;
			frm_query.submit();
			//return true;
		}
		else
		{
			alert("请输入大于0的数!");
			return false;
		}
	}
}
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//-->
//修改系统用户信息
function editRoleInfo(brokerid){
  
  var result=window.showModalDialog("<%=brokerControllerPath %>brokerModForward&brokerid="+brokerid,"", "dialogWidth=800px; dialogHeight=500px; status=yes;scroll=yes;help=no;");
  if(result)
    {
    	frm_query.submit();
    	}
}

function modPwd(userid){
  //var a=window.open("setPwd.jsp?sysUser="+sysUser+"","_blank","width=300,height=250,scrollbars=yes");
  //result=PopWindow("setBrokerPwd.jsp?userid="+userid+"",300,250);
  window.showModalDialog("<%=brokerControllerPath%>setBrokerPwdForward&brokerid="+userid+"","", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;");
}

function queryInfo(){
  //frm_query.pageIndex.value = 1;
  if(frm_query.firmId.value != ""){
     frm_query.action = "<%=brokerControllerPath %>brokerList&qc=yes";
  }
  frm_query.submit();	
}

//添加用户权限
function addRight(brokerid){
 
  window.showModalDialog("<%=brokerRightControllerPath%>brokerRightList&brokerid="+brokerid,"", "dialogWidth=400px; dialogHeight=500px; status=yes;scroll=yes;help=no;");
  
}

function mngUser(brokerid)
{
	//alert(brokerid);
	//window.location = "brokerUserList.jsp?brokerid="+brokerid;
	window.location="<%=brokerControllerPath %>brokerlistFirm&brokerid="+brokerid;
	//alert(frm_query.action);
//frm_query.submit();
	//PopWindow("brokerUserList.jsp?brokerid="+brokerid,600,500);
}
function exportExce(){
	var chuLiMethod=window.confirm("是否处理全部数据？\n“取消”为处理当前页面数据。");
	if(chuLiMethod == true){
		frm_query.action="<%=brokerPath %>/brokerListExcel.jsp?chuLiMethod=1";
	}else{
		frm_query.action="<%=brokerPath %>/brokerListExcel.jsp?chuLiMethod=2";	
	}
	frm_query.submit();
	frm_query.action="<%=brokerControllerPath %>brokerList";
}

function ExcelExport(tableid) 
{ 
    var table = document.getElementById(tableid); //获取页面的table
    var excel = new ActiveXObject("Excel.Application");  //实例化Excel.Application对象
    var workB = excel.Workbooks.Add(); ////添加新的工作簿
    var sheet = workB.ActiveSheet; 
  //var  sheet= workB.Worksheets(1);//该句代码和上句代码同意，都是激活一个sheet
  /***************常用样式设置语句***************************************/
    sheet.Rows(1).WrapText  = false;  //自动换行设置
    sheet.Rows(1).Font.Size=12;//设置第一行的字体大小
    //sheet.Rows(1).Interior.ColorIndex=2;//设置第一行背景色 
    //sheet.Rows(1).Font.ColorIndex=1;//设置第一行字体色   
    //sheet.Range(sheet.Cells(1,1),sheet.Cells(1,7)).mergecells=true;//第一行1到7单元格合并
    //sheet.Columns("A").ColumnWidth =45;//设置列宽
    //sheet.Columns("B").ColumnWidth =45;
    sheet.Columns("C").ColumnWidth =12;//设置列宽
    sheet.Columns("D").ColumnWidth =12;
    sheet.Columns("E").ColumnWidth =12;//设置列宽
    sheet.Columns("F").ColumnWidth =12;
    sheet.Columns("G").ColumnWidth =12;//设置列宽
    sheet.Columns("H").ColumnWidth =20;
    sheet.Columns("I").ColumnWidth =20;//设置列宽
    sheet.Columns("J").ColumnWidth =20;
    sheet.Columns("K").ColumnWidth =12;
    //sheet.Columns("C:D:E:F:G:H:I:J:K").ColumnWidth =35;//另一种设置列宽的方式
    sheet.Rows(1).RowHeight = 35;//设置行高
    sheet.Rows(1).Font.Name="黑体";//设置字体
    sheet.Columns.AutoFit;//所有列自适应宽度
     //水平对齐方式(貌似-4108为水平居中)
    sheet.Range( sheet.Cells(1,1),sheet.Cells(1,11)).HorizontalAlignment =-4108;
    //垂直对齐方式
    //sheet.Range( sheet.Cells(1,1),sheet.Cells(1,5)).VerticalAlignment  =-4108;
    //根据Borders()中参数值设置各个方向边距，1,2,3,4--->top,buttom,left,right
    //sheet.Range( sheet.Cells(2,1),sheet.Cells(1,5)).Borders(4).Weight = 2;
 /**将页面table写入到Excel中，具体复杂情况（合并单元格等）可在这里面具体操作**********/
    var LenRow = table .rows.length-2; //以下为循环遍历获取页面table的cell元素
    for (i = 0; i < LenRow ; i++) 
    { 
        var lenCol = table.rows(i).cells.length-4; 
        for (j = 0; j < lenCol ; j++) 
        { 	
        	sheet.Rows(i+2).WrapText  = true;  //自动换行设置
            sheet.Cells(i+1, j+1).value = table.rows(i).cells(j).innerText + " "; //通过该语句将table的每个
                                        //cell赋予Excel 当前Active的sheet下的相应的cell下
        } 
    } 
    //sheet.Range( sheet.Cells(2,1),sheet.Cells(LenRow,11)).Borders(4) = 1;
    sheet.Range( sheet.Cells(2,1),sheet.Cells(LenRow,11)).HorizontalAlignment =2;
    excel.Visible = true;//设置excel为可见
  excel.UserControl = true;  //将Excel交由用户控制
} 

</SCRIPT>
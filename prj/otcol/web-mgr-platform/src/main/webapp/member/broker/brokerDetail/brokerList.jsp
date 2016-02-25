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
	<title>ϵͳ�û����</title>
	<script type="text/javascript">
		//Ĭ�ϲ�ѯ����
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
		<legend><%=BROKER%>��Ϣ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35" width="100%">
            	<td align="right" width="15%"> <%=BROKERID%>��</td>
                <td align="left">
                	<input name="_b.brokerid[like]" id="brokerid" type="text" class="text" style="width: 150px;" value="${oldParams['b.brokerid[like]']}" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right" width="15%"> �����̴��룺</td>
                <td align="left">
                	<input name="_fb.firmid[=]" id="firmId" type="text" class="text" style="width: 150px;" value="${oldParams['fb.firmid[=]']}" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                
				
              </tr>
              <tr height="35" width="100%">
					<td align="right" width="10%">����ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<input id="locationProvince" name="_locationProvince[like]" value="<c:out value='${oldParams["locationProvince[like]"]}'/>" type=text class="text" style="width:100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right" width="10%">���ƣ�&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16"">
					</td>
				</tr>
				<tr height="35" width="100%">
					<td align="right" width="10%">�г�������Ա��&nbsp;</td>
					<td align="left">
						<input id="marketManager" name="_marketManager[like]" value="<c:out value='${oldParams["marketManager[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16"">
					</td>
					<td align="right" width="10%">�������ڣ�</td>
					<td align="left" colspan="6">
						<MEBS:calendar eltName="_trunc(b.createDate)[=][date]" eltCSS="date" eltID="scleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(b.createDate)[=][date]"]}'/>"/>
					</td>
					<td align="left"  width="50%">
                	&nbsp;&nbsp;<input type="button" onclick="queryInfo();" class="btn" value="��ѯ">&nbsp;&nbsp;
                <!-- add by yangpei  �������ù��� -->
                	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;&nbsp;
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
			        <td class="panel_tHead_MB" align=left>�����̴��� </td>
					<td class="panel_tHead_MB" align=left>���� </td>
			        <td class="panel_tHead_MB" align=left>�绰</td>
			        <td class="panel_tHead_MB" align=left>�ֻ�</td>
			        <td class="panel_tHead_MB" align=left>�����ʼ�</td>
				    <td class="panel_tHead_MB" align=left>��ַ</td>
				    <td class="panel_tHead_MB" align=left>��������</td>
				    <td class="panel_tHead_MB" align=left>�г�������Ա </td>
				    <td class="panel_tHead_MB" align=left>����ʡ��</td>
				    <td class="panel_tHead_MB" align=left>Ȩ������</td>
				    <td class="panel_tHead_MB" align=left>��Ͻ������</td>
				    <td class="panel_tHead_MB" align=left>�����޸�</td>			      		   
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
							  <a href="javascript:addRight('${result.brokerid}');" class="nomal">�鿴</a>
							</td>
							<td class="underLine" align=left>
							  <a href="javascript:mngUser('${result.brokerid}');" class="nomal">�鿴</a>
							</td>
							<td class="underLine" align=left>
							  <a href="javascript:modPwd('${result.brokerid}');" class="nomal">�޸�</a>
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
			    <input type="button" onclick="javascript:window.location='<%=brokerControllerPath %>brokerAddForward'" class="btn" value="���">&nbsp;&nbsp;
  			    <input type="button" onclick="return deletefrmChk(frm_query,tb);" class="btn" value="ɾ��">
  			    <input type="button" onclick="exportExce();" class="btn" value="��������">
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
			alert("������1-"+"0"+"֮���������");
			return false;
		}
	}
}

function deletefrmChk(frm_delete,tableList)
{
	if(isSelNothing(tableList,"ck") == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,"ck"))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷʵҪ����ѡ��������"))
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
			alert("���������0����!");
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
//�޸�ϵͳ�û���Ϣ
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

//����û�Ȩ��
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
	var chuLiMethod=window.confirm("�Ƿ���ȫ�����ݣ�\n��ȡ����Ϊ����ǰҳ�����ݡ�");
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
    var table = document.getElementById(tableid); //��ȡҳ���table
    var excel = new ActiveXObject("Excel.Application");  //ʵ����Excel.Application����
    var workB = excel.Workbooks.Add(); ////����µĹ�����
    var sheet = workB.ActiveSheet; 
  //var  sheet= workB.Worksheets(1);//�þ������Ͼ����ͬ�⣬���Ǽ���һ��sheet
  /***************������ʽ�������***************************************/
    sheet.Rows(1).WrapText  = false;  //�Զ���������
    sheet.Rows(1).Font.Size=12;//���õ�һ�е������С
    //sheet.Rows(1).Interior.ColorIndex=2;//���õ�һ�б���ɫ 
    //sheet.Rows(1).Font.ColorIndex=1;//���õ�һ������ɫ   
    //sheet.Range(sheet.Cells(1,1),sheet.Cells(1,7)).mergecells=true;//��һ��1��7��Ԫ��ϲ�
    //sheet.Columns("A").ColumnWidth =45;//�����п�
    //sheet.Columns("B").ColumnWidth =45;
    sheet.Columns("C").ColumnWidth =12;//�����п�
    sheet.Columns("D").ColumnWidth =12;
    sheet.Columns("E").ColumnWidth =12;//�����п�
    sheet.Columns("F").ColumnWidth =12;
    sheet.Columns("G").ColumnWidth =12;//�����п�
    sheet.Columns("H").ColumnWidth =20;
    sheet.Columns("I").ColumnWidth =20;//�����п�
    sheet.Columns("J").ColumnWidth =20;
    sheet.Columns("K").ColumnWidth =12;
    //sheet.Columns("C:D:E:F:G:H:I:J:K").ColumnWidth =35;//��һ�������п�ķ�ʽ
    sheet.Rows(1).RowHeight = 35;//�����и�
    sheet.Rows(1).Font.Name="����";//��������
    sheet.Columns.AutoFit;//����������Ӧ���
     //ˮƽ���뷽ʽ(ò��-4108Ϊˮƽ����)
    sheet.Range( sheet.Cells(1,1),sheet.Cells(1,11)).HorizontalAlignment =-4108;
    //��ֱ���뷽ʽ
    //sheet.Range( sheet.Cells(1,1),sheet.Cells(1,5)).VerticalAlignment  =-4108;
    //����Borders()�в���ֵ���ø�������߾࣬1,2,3,4--->top,buttom,left,right
    //sheet.Range( sheet.Cells(2,1),sheet.Cells(1,5)).Borders(4).Weight = 2;
 /**��ҳ��tableд�뵽Excel�У����帴��������ϲ���Ԫ��ȣ�����������������**********/
    var LenRow = table .rows.length-2; //����Ϊѭ��������ȡҳ��table��cellԪ��
    for (i = 0; i < LenRow ; i++) 
    { 
        var lenCol = table.rows(i).cells.length-4; 
        for (j = 0; j < lenCol ; j++) 
        { 	
        	sheet.Rows(i+2).WrapText  = true;  //�Զ���������
            sheet.Cells(i+1, j+1).value = table.rows(i).cells(j).innerText + " "; //ͨ������佫table��ÿ��
                                        //cell����Excel ��ǰActive��sheet�µ���Ӧ��cell��
        } 
    } 
    //sheet.Range( sheet.Cells(2,1),sheet.Cells(LenRow,11)).Borders(4) = 1;
    sheet.Range( sheet.Cells(2,1),sheet.Cells(LenRow,11)).HorizontalAlignment =2;
    excel.Visible = true;//����excelΪ�ɼ�
  excel.UserControl = true;  //��Excel�����û�����
} 

</SCRIPT>
<%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
<html>
	<head>
		<title>ϵͳ�û����</title>
		<script type="text/javascript"
			src="../widgets/ecside/js/prototype_mini.js"></script>
		<script type="text/javascript"
			src="../widgets/ecside/js/ecside_msg_gbk_cn.js"></script>
		<script type="text/javascript"
			src="../widgets/ecside/js/ecside.js"></script>
		<link rel="stylesheet" type="text/css"
			href="../widgets/ecside/css/ecside_style.css" />

	</head>
	<body >
		<br />
<form name="myForm" action="/ecsideTest/ecside/getList.action"
			method="post">
			<fieldset width="50%" height="60%">
				<legend>
					<font style="font-weight: bold;color: black">��ѯ����</font>
				</legend>
				<table>
					<tr>
						<td>
							��Ա���:
							<input type="text" name="" value="" size="11" />
							&nbsp;&nbsp;
						</td>
						
						<td>
							�ر��Ա:
							<input type="text" name="" value="" size="11" />
							&nbsp;&nbsp;
						</td>
					
						<td>
									&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="��ʼ���" />&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							<input type="button" value="����" />
						</td>
					</tr>
				</table>
			</fieldset>

		</form>
		<br />	

					
<form id="ec"  method="post"  style="width:100%;visibility :hidden;"  nearPages="3"  filterable="true"  canResizeColWidth="true"  maxRowsExported="1000000"  minColWidth="45" 
 action="http://127.0.0.1:8088/ecsideTest/ecside/getList.action"  deleteAction="http://127.0.0.1:8088/ecsideTest/ecside/delete.action" >
<div class="ecSide"  id="ec_main_content"  style="width:100%;" >
<!-- ECS_AJAX_ZONE_PREFIX__begin_ ec_ECS_AJAX_ZONE_SUFFIX -->
<div>
<input type="hidden"  name="ec_i"  value="ec" />
<input type="hidden"  name="eti" />
<input type="hidden"  name="eti_p" />
<input type="hidden"  name="ec_efn" />
<input type="hidden"  name="ec_ev" />
<input type="hidden"  name="ec_crd"  value="10" />
<input type="hidden"  name="ec_f_a" />
<input type="hidden"  name="ec_p"  value="1" />
<input type="hidden"  name="ec_s_checkboxId" />
<input type="hidden"  name="ec_s_id[=][long]" />
<input type="hidden"  name="ec_s_name[Like]" />
<input type="hidden"  name="ec_s_age[=]" />
<input type="hidden"  name="ec_s_address[like]" />
<input type="hidden"  name="ec_totalpages"  value="1" />
<input type="hidden"  name="ec_totalrows"  value="6" />
<input type="hidden"  name="ec_f_checkboxId" /><input type="hidden"  name="ec_f_id[=][long]" /><input type="hidden"  name="ec_f_name[Like]" /><input type="hidden"  name="ec_f_age[=]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" /><input type="hidden"  name="ec_f_address[like]" />

</div>
<div class="headZone"  id="ec_headZone" >
�ͻ�ƽ�ֻ���
<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;"  width="100%"    >
	<thead id="ec_table_head" ><tr>
	<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��Ʒ����" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��Ʒ����</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >�򽨲־���</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ƽ�־���</div></td>
		<td valign="middle"  columnName="age[=]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'age[=]','asc','ec');"  title="���� ������" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >������</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ӯ��</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ���ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >���ֲ־���</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ƽ�־���</div></td>
		<td valign="middle"  columnName="age[=]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'age[=]','asc','ec');"  title="���� ������" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >������</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ӯ��</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >�����</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >ӯ��</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >������</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >���ڷ�</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ӯ��</div></td>
</tr>
	</thead>
</table></div>
<div style="overflow:auto;width:100%;height:50px"  class="bodyZone"  id="ec_bodyZone" >
<table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;"  width="100%"    >
	<tbody id="ec_table_body" >
<tr bgcolor="red" class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >�ֻ�����</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> <font color="red"> 8335</font> </td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"><font color="red"> 10325.00</font> </td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"><font color="red"> 10325.00</font> </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > <font color="red"> -3432561.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > <font color="green"> 8355</font></td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  ><font color="green"> 591.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  ><font color="green"> 591.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > <font color="green"> 97515.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > -3446880.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
</tr>

<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >��ͨ��</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"   ><font color="red"> 307.53</font></td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"   ><font color="red"> 6115.00</font> </td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"   ><font color="red"> 6115.00</font> </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > <font color="red"> -2996350.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >  <font color="green"> 307.53</font></td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"   > <font color="green"> 304.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"   > <font color="green"> 304.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"   >  <font color="green"> 72960.00</font></td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > 5811.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > -2865280.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   >9734.00</td>
</tr>



	</tbody>
</table><iframe style="border:0px;" marginwidth="0" marginheight="0" frameborder="0" border="0" width="0" height="0" id="ec_ecs_export_iframe" name="ec_ecs_export_iframe" ></iframe></div>

<!-- ECS_AJAX_ZONE_PREFIX_ _end_ec_ECS_AJAX_ZONE_SUFFIX -->
</div>
</form><div id="ec_waitingBar"  class="waitingBar" ></div><div id="ec_waitingBarCore"  class="waitingBarCore" ></div>


					
<form id="ec2"  method="post"  style="width:100%;"  nearPages="3"  filterable="true"  canResizeColWidth="true"  maxRowsExported="1000000"  minColWidth="45" 
 action="http://127.0.0.1:8088/ecsideTest/ecside/getList.action">
<div class="ecSide"  id="ec_main_content2"  style="width:100%;" >
<!-- ECS_AJAX_ZONE_PREFIX__begin_ ec_ECS_AJAX_ZONE_SUFFIX -->
<div>

</div>
<div class="headZone"  id="ec_headZone" >
�ͻ�ƽ����ϸ
<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;"  width="100%"    >
	<thead id="ec_table_head" ><tr>
	<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��Ʒ����" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >����</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��¼�˺�</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ��ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >�û�����</div></td>
		<td valign="middle"  columnName="age[=]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'age[=]','asc','ec');"  title="���� ������" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��Ʒ</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >����</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� ���ֲ־���" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >����ʱ��</div></td>
		<td valign="middle"  columnName="age[=]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'age[=]','asc','ec');"  title="���� ������" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >���ַ���</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >���ּ�</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >ƽ��ʱ��</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >ƽ�ַ���</div></td>
		<td valign="middle"  columnName="address[like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'address[like]','asc','ec');"  title="���� ��ӯ��" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >ƽ�ּ�</div></td>
		<td valign="middle"  columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >�ֲּ�</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >ӯ��</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >������</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >���ڷ�</div></td>
		<td valign="middle" columnName="name[Like]"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  width="100"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name[Like]','asc','ec');"  title="���� �û�" ><span onmousedown="ECSideUtil.StartResize(event,this,'ec');" onmouseup="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span><div class="headerTitle" >��ӯ��</div></td>
</tr>
	</thead>
</table></div>
<div style="overflow:auto;width:100%;height:500px"  class="bodyZone"  id="ec_bodyZone" >
<table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;"  width="100%"    >
	<tbody id="ec_table_body" >
<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  >XXX </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
</tr>
<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  >XXX </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
</tr>
<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  >XXX </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
</tr>

<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  >XXX </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
</tr>

<tr class="odd "  onclick="ECSideUtil.selectRow(this,'ec');"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');">
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')"   >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"> XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  >XXX </td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="red"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="gnnt"  ondblclick="ECSideUtil.editCell(this,'ec')" bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="4.0.44"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  >XXX</td>
<td style="text-align:center;"  width="100"  cellValue="172.16.1.64"  ondblclick="ECSideUtil.editCell(this,'ec')"  bgcolor="green"  > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
<td style="text-align:center;"  width="100"  cellValue="8888" ondblclick="ECSideUtil.editCell(this,'ec')"   > XXX</td>
</tr>

	</tbody>
</table><iframe style="border:0px;" marginwidth="0" marginheight="0" frameborder="0" border="0" width="0" height="0" id="ec_ecs_export_iframe" name="ec_ecs_export_iframe" ></iframe></div>
<div id="ec_toolbar"  class="toolbar"  style="width:100%;" >
	<table id="ec_toolbarTable"  class="toolbarTable"  cellpadding="0"  cellspacing="0" ><tr><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav firstPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="��һҳ" /><input type="button"  disabled="disabled"  class="pageNav prevPageD"  onclick="ECSideUtil.gotoPage(0,'ec');"  title="��һҳ" /></td><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav nextPageD"  onclick="ECSideUtil.gotoPage(2,'ec');"  title="��һҳ"  disabled="disabled" /><input type="button"  disabled="disabled"  class="pageNav lastPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="��ĩҳ"  disabled="disabled" /></td>
<td class="separatorTool" >&#160;</td>
<td class="pageJumpTool"  nowrap="nowrap"  onmouseover="ECSideUtil.NearPagesBar.showMe(this,'ec');"  onmouseout="ECSideUtil.NearPagesBar.hideMe(this,'ec');" ><nobr><input type="button"  class="pageNav jumpPage"  onclick="ECSideUtil.gotoPageByInput(this,'ec');" /><input type="text"  name="ec_pg"  value="1"  class="jumpPageInput"  onkeydown="if (event.keyCode && event.keyCode==13 ) {ECSideUtil.gotoPageByInput(this,'ec');;return false; } " />/1ҳ</nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="pageSizeTool"  nowrap="nowrap" >ÿҳ<select name="ec_rd"  onchange="ECSideUtil.changeRowsDisplayed('ec',this);" >
				<option value="5" >5</option><option value="10"  selected="selected">10</option><option value="20" >20</option><option value="50" >50</option><option value="100" >100</option><option value="1000" >1000</option><option value="6" >ȫ��</option>
				</select>��</td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="exportTool" ><nobr><input type="button"  class="toolButton exportPrint"  onclick="ECSideUtil.doExport('print','_print_','','ec');"  alt="��ӡ"  title="��ӡ" /></nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="extendTool" ></td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="statusTool" ><nobr>��6����¼,��ʾ1��6</nobr></td>
</tr>
	</table></div><div id="ec_toolbarShadow"  style="display:none;" ></div>
<!-- ECS_AJAX_ZONE_PREFIX_ _end_ec_ECS_AJAX_ZONE_SUFFIX -->
</div>
</form><div id="ec_waitingBar"  class="waitingBar" ></div><div id="ec_waitingBarCore"  class="waitingBarCore" ></div>















<script type="text/javascript" >
(function(){ 
 var gird=ECSideUtil.createGird('ec'); 
 gird.useAjax=true;  
 gird.doPreload=true; 
 gird.isClassic=false; 
 gird.minHeight='300'; 
})();
</script>

		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>



	</body>
</html>
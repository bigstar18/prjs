 <%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
<html>
	<head>
		<title>�����б�</title>
		<script type="text/javascript" src="../widgets/ecside/js/prototype_mini.js"></script>
		<script type="text/javascript" src="../widgets/ecside/js/ecside_msg_gbk_cn.js"></script>
		<script type="text/javascript" src="../widgets/ecside/js/ecside.js"></script>
		<link rel="stylesheet" type="text/css" href="../widgets/ecside/css/ecside_style.css" />
		<style type="text/css">
					    .scroll {   
        width: 50%;                                     /*���*/   
        height: 200px;                                  /*�߶�*/   
        color: ;                                        /*��ɫ*/   
        font-family: ;                                  /*����*/   
        padding-left: 10px;                             /*������߾�*/   
        padding-right: 10px;                            /*�����ұ߾�*/   
        padding-top: 10px;                              /*�����ϱ߾�*/   
        padding-bottom: 10px;                           /*�����±߾�*/   
        overflow-x: scroll;                             /*���������(scroll:ʼ�ճ���;auto:��Ҫʱ����;����ο�CSS�ĵ�)*/   
        overflow-y: scroll;                             /*���������*/   
           
        scrollbar-face-color: #D4D4D4;                  /*������������ɫ*/   
        scrollbar-hightlight-color: #ffffff;                /*������3D�����������ɫ*/   
        scrollbar-shadow-color: #919192;                    /*������3D����İ�����ɫ*/   
        scrollbar-3dlight-color: #ffffff;               /*���������߿���ɫ*/   
        scrollbar-arrow-color: #919192;                 /*��ͷ��ɫ*/   
        scrollbar-track-color: #ffffff;                 /*��������ɫ*/   
        scrollbar-darkshadow-color: #ffffff;                /*���������߿���ɫ*/   
    }   
		</style>
	</head>
	<body><div>
		<br />
		<br />
		<form name="myForm" action="http://127.0.0.1:8088/staticPages/ecside/ecside.action" method="post">
			<fieldset>
				<legend style="font-weight:bold; ">
						���ò�ѯ����
				</legend>
				<table>
					<tr>
					  <td>  �ϼ�����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <label>
					    <select name="select" size="1" style="width:80px">
				        </select>
				      </label>					    &nbsp;&nbsp;&nbsp;</td>
					  <td>  ��������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <label>
					    <select name="select" size="1" style="width:80px">
				        </select>
				      </label>					    &nbsp;&nbsp;&nbsp;</td>
						<td><input type="submit" value="��ѯ"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="����"/></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<br />
	    
		
		
		
<form id="ec"  method="post"  style="width:100%;visibility :hidden;"  nearPages="3"  filterable="true"  canResizeColWidth="true"  maxRowsExported="1000000"  minColWidth="45" 
 action="http://127.0.0.1:8088/staticPages/ecside/ecside.action" >
<div class="ecSide"  id="ec_main_content"  style="width:100%;" >
<!-- ECS_AJAX_ZONE_PREFIX__begin_ ec_ECS_AJAX_ZONE_SUFFIX -->
<div>
<input type="hidden"  name="ec_i"  value="ec" />
<input type="hidden"  name="eti" />
<input type="hidden"  name="eti_p" />
<input type="hidden"  name="ec_efn" />
<input type="hidden"  name="ec_ev" />
<input type="hidden"  name="ec_crd"  value="6" />
<input type="hidden"  name="ec_f_a" />
<input type="hidden"  name="ec_p"  value="1" />
<input type="hidden"  name="ec_s_checkboxId" />
<input type="hidden"  name="ec_s_id" />
<input type="hidden"  name="ec_s_account" />
<input type="hidden"  name="ec_s_name" />
<input type="hidden"  name="ec_s_balance" />
<input type="hidden"  name="ec_s_drift" />
<input type="hidden"  name="ec_s_value" />
<input type="hidden"  name="ec_s_available" />
<input type="hidden"  name="ec_s_occupy" />
<input type="hidden"  name="ec_s_freeze" />
<input type="hidden"  name="ec_s_risk" />
<input type="hidden"  name="ec_s_rate" />
<input type="hidden"  name="ec_totalpages"  value="1" />
<input type="hidden"  name="ec_totalrows"  value="6" />
<input type="hidden"  name="ec_f_checkboxId" /><input type="hidden"  name="ec_f_id" /><input type="hidden"  name="ec_f_account" /><input type="hidden"  name="ec_f_name" /><input type="hidden"  name="ec_f_balance" /><input type="hidden"  name="ec_f_drift" /><input type="hidden"  name="ec_f_value" /><input type="hidden"  name="ec_f_available" /><input type="hidden"  name="ec_f_occupy" /><input type="hidden"  name="ec_f_freeze" /><input type="hidden"  name="ec_f_risk" /><input type="hidden"  name="ec_f_rate" />
 
</div>
<div class="headZone"  id="ec_headZone" >
<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;table-layout:fixed;"  width="40%"    >
	<thead id="ec_table_head" ><tr>
	<td valign="middle"  columnName="checkboxId"  width="2%"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'ec');" 	onmouseout="ECSideUtil.unlightHeader(this,'ec');" >
	<span class="columnSeparator" >&#160;</span><div class="headerTitle" >
	<span class="checkboxHeader"  onclick="ECSideUtil.checkAll(this,'checkboxId','ec');" >&#160;</span></div></td>
<td valign="middle"  columnName="id" width="18%"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'id','asc','ec');"  title="���� Id" >
<span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >��������</div></td>
<td valign="middle" width="20%"  columnName="account"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'account','asc','ec');"  title="���� ��¼�˺�" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >��������</div></td>
</tr>
	</thead>
</table></div>
<div style="overflow:auto;width:100%;height:580px"  class="bodyZone"  id="ec_bodyZone" >
<table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;table-layout:fixed;"  width="40%"    >
	<tbody id="ec_table_body" >
		
		<tr class="odd "  ondblclick="update()"  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');"    recordKey="1" >
<td width="2%"  cellValue="1"   style="text-align: center;"  ondblclick="ECSideUtil.editCell(this,'ec')"   ><input type="checkbox"  name="checkboxId"  value="1"  class="checkbox" /></td>
<td style="text-align:center;" width="18%"    cellValue="1"  ondblclick="ECSideUtil.editCell(this,'ec')"   >admin901</td>
<td style="text-align:center;" width="20%"    cellValue="00211"  ondblclick="ECSideUtil.editCell(this,'ec')"   >������</td>
</tr>		
	</tbody>
</table><iframe style="border:0px;" marginwidth="0" marginheight="0" frameborder="0" border="0" width="0" height="0" id="ec_ecs_export_iframe" name="ec_ecs_export_iframe" ></iframe></div>
 
<div id="ec_toolbar"  class="toolbar"  style="width:100%;" >
	<table id="ec_toolbarTable"  class="toolbarTable"  cellpadding="0"  cellspacing="0" ><tr><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav firstPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="��һҳ" /><input type="button"  disabled="disabled"  class="pageNav prevPageD"  onclick="ECSideUtil.gotoPage(0,'ec');"  title="��һҳ" /></td><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav nextPageD"  onclick="ECSideUtil.gotoPage(2,'ec');"  title="��һҳ"  disabled="disabled" /><input type="button"  disabled="disabled"  class="pageNav lastPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="��ĩҳ"  disabled="disabled" /></td>
<td class="separatorTool" >&#160;</td>
<td class="pageJumpTool"  nowrap="nowrap"  onmouseover="ECSideUtil.NearPagesBar.showMe(this,'ec');"  onmouseout="ECSideUtil.NearPagesBar.hideMe(this,'ec');" ><nobr><input type="button"  class="pageNav jumpPage"  onclick="ECSideUtil.gotoPageByInput(this,'ec');" /><input type="text"  name="ec_pg"  value="1"  class="jumpPageInput"  onkeydown="if (event.keyCode && event.keyCode==13 ) {ECSideUtil.gotoPageByInput(this,'ec');;return false; } " />/1ҳ</nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="pageSizeTool"  nowrap="nowrap" >ÿҳ<select name="ec_rd"  onchange="ECSideUtil.changeRowsDisplayed('ec',this);" >
				<option value="5" >5</option><option value="10" >10</option><option value="20" >20</option><option value="50" >50</option><option value="100" >100</option><option value="1000" >1000</option><option value="6"  selected="selected">ȫ��</option>
				</select>��</td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="exportTool" ><nobr><input type="button"  class="toolButton exportXls"  onclick="ECSideUtil.doExport('xls','�û��б�.xls','','ec');"  alt="����XLS"  title="����ΪXLS�ļ�" /><input type="button"  class="toolButton exportCsv"  onclick="ECSideUtil.doExport('csv','�û��б�.csv','','ec');"  alt="����CSV"  title="����ΪCSV�ļ�" /></nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="extendTool" ></td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="statusTool" ><nobr>��1����¼,��ʾ1��1</nobr></td>
</tr>
	</table></div><div id="ec_toolbarShadow"  style="display:none;" ></div>
<!-- ECS_AJAX_ZONE_PREFIX_ _end_ec_ECS_AJAX_ZONE_SUFFIX -->
</div>
</form><div id="ec_waitingBar"  class="waitingBar" ></div><div id="ec_waitingBarCore"  class="waitingBarCore" ></div>
<script type="text/javascript" > 
(function(){ 
 var gird=ECSideUtil.createGird('ec'); 
 gird.useAjax=true;  
 gird.doPreload=false; 
 gird.isClassic=false; 
})();
</script>
 
 
		<br />
		<table align="center">
			<tr>
				<td>
					<input id='delete' type="reset" value="ɾ��"/>
				</td>
				<td>
					<input id='add' type="button" value="���" onClick="add()"/>
				</td>
			</tr>
		</table>
	</body></div>
</html>
<script type="text/javascript" > 
	function add(){
		var url="addOrganizationType.jsp";
				var result=window.showModalDialog(url,"","dialogWidth=400px;dialogHeight=350px");
	}
	function update(){
		var url="updateOrganizationType.jsp";
		window.showModalDialog(url,"","dialogWidth=400px;dialogHeight=350px");
	}
</script>
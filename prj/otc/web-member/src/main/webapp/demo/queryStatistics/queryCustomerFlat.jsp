 <%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
<html>
	<head>
		<title>会员列表</title>
		<script type="text/javascript" src="widgets/ecside/js/prototype_mini.js"></script>
		<script type="text/javascript" src="widgets/ecside/js/ecside_msg_gbk_cn.js"></script>
		<script type="text/javascript" src="widgets/ecside/js/ecside.js"></script>
		<link rel="stylesheet" type="text/css" href="widgets/ecside/css/ecside_style.css" />
		<style type="text/css">
					    .scroll {   
        width: 50%;                                     /*宽度*/   
        height: 200px;                                  /*高度*/   
        color: ;                                        /*颜色*/   
        font-family: ;                                  /*字体*/   
        padding-left: 10px;                             /*层内左边距*/   
        padding-right: 10px;                            /*层内右边距*/   
        padding-top: 10px;                              /*层内上边距*/   
        padding-bottom: 10px;                           /*层内下边距*/   
        overflow-x: scroll;                             /*横向滚动条(scroll:始终出现;auto:必要时出现;具体参考CSS文档)*/   
        overflow-y: scroll;                             /*竖向滚动条*/   
           
        scrollbar-face-color: #D4D4D4;                  /*滚动条滑块颜色*/   
        scrollbar-hightlight-color: #ffffff;                /*滚动条3D界面的亮边颜色*/   
        scrollbar-shadow-color: #919192;                    /*滚动条3D界面的暗边颜色*/   
        scrollbar-3dlight-color: #ffffff;               /*滚动条亮边框颜色*/   
        scrollbar-arrow-color: #919192;                 /*箭头颜色*/   
        scrollbar-track-color: #ffffff;                 /*滚动条底色*/   
        scrollbar-darkshadow-color: #ffffff;                /*滚动条暗边框颜色*/   
    }   
		</style>
	</head>
	<body><div>
		<br />
		<br />
		<form name="myForm" action="http://127.0.0.1:8088/staticPages/ecside/ecside.action" method="post">
			<fieldset>
				<legend style="font-weight:bold; ">
						设置查询条件
				</legend>
				<table>
					<tr>
					  <td width="182">查询范围：
					    <select name="select2" size="1" style="width:80px">
					      <option>历史</option>
                        </select></td>
					  <td width="164">会员编号：
					    <select name="select6" size="1" style="width:80px">
					      <option>不限</option>
                        </select></td>
					  <td width="178">操作类型<select name="select3" size="1" style="width:80px" disabled="disabled">
					      <option>不限</option>
                        </select> </td>
				  </tr>
					<tr>
					  <td>起始日期：
					    <select name="select3" size="1" style="width:80px" disabled="disabled">
					      <option>2011-4-7</option>
                        </select></td>
					  <td> 操作员： &nbsp;
					     <select name="select5" size="1" style="width:80px">
					      <option>不限</option>
                      </select></td>
					  <td>买卖：
					    <label>
					    <select name="select" size="1" style="width:80px">
					      <option>不限</option>
				        </select>
				      </label></td>
					  <td>建/平：
					    <label>
					    <select name="select" size="1" style="width:80px">
					      <option>不限</option>
				        </select>
				      </label></td>
					  <td>处理状态：
					    <label>
					    <select name="select" size="1" style="width:80px">
					      <option>不限</option>
				        </select>
				      </label></td>
				  </tr>
					<tr>
					  <td>终止日期：
					    <select name="select4" size="1" style="width:80px" disabled="disabled">
					      <option>2011-4-7</option>
                                                </select></td>
					  <td>  商品名称：
					    <label>
					    <select name="select" size="1" style="width:80px">
					      <option>不限</option>
				        </select>
				      </label>					    &nbsp;&nbsp;&nbsp;</td>
						
						<td><input type="submit" value="查询"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/></td>
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
<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;table-layout:fixed;"  width="300%"    >
	<thead id="ec_table_head" ><tr><td valign="middle"  columnName="checkboxId"  width="2%"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');" ><span class="columnSeparator" >&#160;</span><div class="headerTitle" ><span class="checkboxHeader"  onclick="ECSideUtil.checkAll(this,'checkboxId','ec');" >&#160;</span></div></td>
<td valign="middle"  columnName="id"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'id','asc','ec');"  title="排序 Id" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >指价单号</div></td>
<td valign="middle"  columnName="account"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'account','asc','ec');"  title="排序 登录账号" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >账号</div></td>
<td valign="middle"  columnName="name"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'name','asc','ec');"  title="排序 用户名称" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >用户名称</div></td>
<td valign="middle"  columnName="balance"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"  class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'balance','asc','ec');"  title="排序 余额" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >操作员</div></td>
<td valign="middle"  columnName="drift"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'drift','asc','ec');"  title="排序 浮动盈亏" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >操作累型</div></td>
<td valign="middle"  columnName="value"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'value','asc','ec');"  title="排序 净值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >商品</div></td>
<td valign="middle"  columnName="available"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'available','asc','ec');"  title="排序 可用保证金" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >买/卖</div></td>
<td valign="middle"  columnName="occupy"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'occupy','asc','ec');"  title="排序 占用保证金" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >类型</div></td>
<td valign="middle"  columnName="freeze"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'freeze','asc','ec');"  title="排序 冻结保证金" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >数量</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >指价</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >止损价</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >止盈价</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >期限</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >下单时间</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >平单时间</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >关联单号</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >成交方向</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >下单市价</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >穿越价</div></td>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >处理状态</div></td>
</tr>
	</thead>
</table></div>
<div style="overflow:auto;width:100%;height:580px"  class="bodyZone"  id="ec_bodyZone" >
  <table id="ec_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  style="table-layout:fixed;table-layout:fixed;"  width="300%"    >
    <tbody id="ec_table_body" >
      <tr class="odd "  ondblclick=""  onmouseover="ECSideUtil.lightRow(this,'ec');"  onmouseout="ECSideUtil.unlightRow(this,'ec');"    recordkey="1" >
        <td width="2%"  cellvalue="1"  ondblclick="ECSideUtil.editCell(this,'ec')"   ><input type="checkbox"  name="checkboxId"  value="1"  class="checkbox" /></td>
        <td style="text-align:center;"    cellvalue="1"  ondblclick="ECSideUtil.editCell(this,'ec')"   >002</td>
        <td style="text-align:center;"    cellvalue="00211"  ondblclick="ECSideUtil.editCell(this,'ec')"   >leon金元宝</td>
        <td style="text-align:center;"    cellvalue="fxxt11"  ondblclick="ECSideUtil.editCell(this,'ec')"   >77515100</td>
        <td style="text-align:center;"   cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >66441500</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >44294400</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >1099</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >9155</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >2011-4-1 11:45:00</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >未调整</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
		<td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >44294400</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >1099</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >9155</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >2011-4-1 11:45:00</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >未调整</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
        <td style="text-align:center;"    cellvalue="0.0"  ondblclick="ECSideUtil.editCell(this,'ec')"   >N/A</td>
      </tr>
    </tbody>
  </table>
  <iframe style="border:0px;" marginwidth="0" marginheight="0" frameborder="0" border="0" width="0" height="0" id="ec_ecs_export_iframe" name="ec_ecs_export_iframe" ></iframe>
</div>
<td valign="middle"  columnName="risk"  sortable="true"  resizeColWidth="true"  editTemplate="ecs_t_input"    class="tableHeader  tableResizeableHeader editableColumn"  onmouseover="ECSideUtil.lightHeader(this,'ec');"  onmouseout="ECSideUtil.unlightHeader(this,'ec');"  oncontextmenu="ECSideUtil.showColmunMenu(event,this,'ec');"  onmouseup="ECSideUtil.doSort(event,'risk','asc','ec');"  title="排序 风险总阈值" ><span onMouseDown="ECSideUtil.StartResize(event,this,'ec');" onMouseUp="ECSideUtil.EndResize(event);" class="columnSeparator columnResizeableSeparator" >&#160;</span>
<div class="headerTitle" >总价:<input type="text" value="0" size="12"></div></td>
<div id="ec_toolbar"  class="toolbar"  style="width:100%;" >
	<table id="ec_toolbarTable"  class="toolbarTable"  cellpadding="0"  cellspacing="0" ><tr><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav firstPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="第一页" /><input type="button"  disabled="disabled"  class="pageNav prevPageD"  onclick="ECSideUtil.gotoPage(0,'ec');"  title="上一页" /></td><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav nextPageD"  onclick="ECSideUtil.gotoPage(2,'ec');"  title="下一页"  disabled="disabled" /><input type="button"  disabled="disabled"  class="pageNav lastPageD"  onclick="ECSideUtil.gotoPage(1,'ec');"  title="最末页"  disabled="disabled" /></td>
<td class="separatorTool" >&#160;</td>
<td class="pageJumpTool"  nowrap="nowrap"  onmouseover="ECSideUtil.NearPagesBar.showMe(this,'ec');"  onmouseout="ECSideUtil.NearPagesBar.hideMe(this,'ec');" ><nobr><input type="button"  class="pageNav jumpPage"  onclick="ECSideUtil.gotoPageByInput(this,'ec');" /><input type="text"  name="ec_pg"  value="1"  class="jumpPageInput"  onkeydown="if (event.keyCode && event.keyCode==13 ) {ECSideUtil.gotoPageByInput(this,'ec');;return false; } " />/1页</nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="pageSizeTool"  nowrap="nowrap" >每页<select name="ec_rd"  onchange="ECSideUtil.changeRowsDisplayed('ec',this);" >
				<option value="5" >5</option><option value="10" >10</option><option value="20" >20</option><option value="50" >50</option><option value="100" >100</option><option value="1000" >1000</option><option value="6"  selected="selected">全部</option>
				</select>条</td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="exportTool" ><nobr><input type="button"  class="toolButton exportXls"  onclick="ECSideUtil.doExport('xls','用户列表.xls','','ec');"  alt="导出XLS"  title="导出为XLS文件" /><input type="button"  class="toolButton exportCsv"  onclick="ECSideUtil.doExport('csv','用户列表.csv','','ec');"  alt="导出CSV"  title="导出为CSV文件" /></nobr></td>
<td class="separatorTool" >&#160;</td>
<td class="extendTool" ></td>
<td class="separatorTool" >&#160;</td>
<td nowrap="nowrap"  class="statusTool" ><nobr>共6条记录,显示1到6</nobr></td>
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
		
	</body></div>
</html>
<script type="text/javascript" > 
	function add(){
		var url="addSpecialMembers.html";
				var result=window.showModalDialog(url,"","dialogWidth=800px;dialogHeight=650px");
	}
	function update(){
		var url="updateSpecialMembers.html";
		window.showModalDialog(url,"","dialogWidth=800px;dialogHeight=650px");
	}
</script>
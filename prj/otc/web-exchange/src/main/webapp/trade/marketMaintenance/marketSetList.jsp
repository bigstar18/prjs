<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns:MEBS>
  <head>
    
    <title>行情异常</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS" implementation="${basePath}/common/jslib/calendar.htc"/>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
		    <div class="div_tj">
			  <form name="frm" action="${basePath}/marketMaintenanceSet/marketLogSet/list.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table2_td_width">
					  <div class="div2_top">
						<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" width="100%">
						  <tr>
					        <td class="table3_td_1" align="left">
					                            商品代码:&nbsp;
							   <label>
							     <input type="text" class="input_text" id="commodityId" name="${GNNT_}commodityId[like]" size="14" 
							            value="${oldParams['commodityId[like]'] }"/>
							   </label>
							</td>
							<td class="table3_td_anniu" align="left">
							  <button class="btn_sec" onClick="search1()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onClick="myReset()">重置</button>
							</td>
						  </tr>
						</table>
					  </div>
					</td>
				  </tr>
			    </table>
			  </form>
		    </div>
		    
		    <div class="div_gn">
		      <div style="float: left;">
	            <button class="anniu_btn" onclick="addLogSet()" id="add">添加</button>
	          </div>
	        </div>
       
	        <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="resultList"
							  autoIncludeParameters="${empty param.autoInc}"
							  var="market"
							  action="${basePath}/marketMaintenanceSet/marketLogSet/list.action"
							  xlsFileName="marketLogList.xls" 
			                  csvFileName="marketLogList.csv"
							  title="" minHeight="345" listWidth="100%"
							  retrieveRowsCallback="limit" sortRowsCallback="limit"
							  filterRowsCallback="limit" 	style="table-layout:fixed" >
					  <ec:row >
						<ec:column width="4%" property="_0" title="序号"
								   value="${GLOBALROWCOUNT}" sortable="false"
								   filterable="false" style="text-align:center; "/>		 
						<ec:column width="15%" property="commodityId[like]" title="商品代码" style="text-align:center; ">
							<a href="#" class="blank_a" onclick="return update('${market.commodityId}');">
							  <font color="#880000">${market.commodityId}</font>
							</a>
					    </ec:column>
						<ec:column property="quoprice[=][double]" title="最小行情价格(美元/盎司)"
								   width="20%" style="text-align:left; " >
						    <fmt:formatNumber value="${market.minquoprice}" pattern="#,##0.0000" />
						</ec:column>
						<ec:column property="quoprice[=][double]" title="最大行情价格(美元/盎司)"
								   width="20%" style="text-align:left; " >
						    <fmt:formatNumber value="${market.maxquoprice}" pattern="#,##0.0000" />
						</ec:column>
						
										
					  </ec:row>
					</ec:table>
				  </td>
				</tr>
			  </table>
			</div>
	          
		  </td>
		</tr>
	  </table>
    </div>
    
        <!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- 编辑和过滤所使用的操作模板 -->
		<textarea id="esc_fundFlowType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="fundFlowType[=]">
			<ec:options items="fundFlowMap" />
		</select>
	    </textarea>
	    
	  <div style="margin-left: 20px; margin-top: 10px;">
	    <font color="red" size="2">
	                   此页面补录数据均为外盘行情，请慎重操作!
	    </font>
	  </div>
  </body>
</html>

<script type="text/javascript" >

   function search1(){
	 
	   frm.submit();
   } 
   function addLogSet(){
	   var url="${basePath}/marketMaintenanceSet/marketLogSet/forwardAdd.action";
	   ecsideDialog(url,window,590,300);		
   } 

   function update(commodityId){
	  var url="${basePath}/marketMaintenanceSet/marketLogSet/forwardUpdate.action?obj.commodityId=" + commodityId;
	  ecsideDialog(url,window,590,350);
   }  
  
</script>
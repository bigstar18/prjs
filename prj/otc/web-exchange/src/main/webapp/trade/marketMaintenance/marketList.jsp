<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns:MEBS>
  <head>
    <title>行情异常</title>
	<%@ include file="/public/ecsideLoad.jsp"%>
	<IMPORT namespace="MEBS" implementation="${basePath}/common/jslib/calendar.htc"/>
	
	<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
    <script type="text/javascript" src='<%=basePath%>/dwr/interface/checkPrice.js' /></script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
		    <div class="div_tj">
			  <form name="frm" action="${basePath}/marketMaintenance/marketLog/list.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td style="height: 40px; width: 100%;">
					  <div class="div2_top">
						<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed" width="100%">
						  <tr>
					        <td class="table3_td_1" align="left">
					                            商品代码:&nbsp;
							   <label>
							     <input type="text" class="input_text"  name="${GNNT_}commodityId[like]" size="14" 
							            value="${oldParams['commodityId[like]'] }"/>
							   </label>
							</td>
							<td align="left" width="265">
							  &nbsp;行情价格:&nbsp;
							  <label>
								<input type="text" name="${GNNT_}quoprice[=][double]"  class="input_text"
								 value="${oldParams['quoprice[=][double]'] }"/>
							  </label>
							  &nbsp;<font color="red">(美元/盎司)</font>
							</td>
							<td  align="left" >
									开始时间：
							  <input type="text" style="width: 160px" id="startDate"
									 class="wdate" maxlength="10"
									 name="${GNNT_}actionTime[>=][timestemp]"
									 value='${oldParams["actionTime[>=][timestemp]"]}'
									 onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
							</td>

							<td align="left">
									结束时间：
							  <input type="text" style="width: 160px" id="endDate"
									 class="wdate" maxlength="10"
									 name="${GNNT_}actionTime[<=][timestemp]"
									 value='${oldParams["actionTime[<=][timestemp]"]}'
							         onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
							</td>
							<td class="table3_td_anniu" align="right">
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
	            <button class="anniu_btn" onclick="addLog()" id="add">添加</button>
	          </div>
	          
	          <div style="float: left; margin-left: 50px;">
	          <form name="frm_add" action="${basePath}/marketMaintenance/marketLog/add.action" method="post">
	                                商品：
	            <select id="commodityId" name="commodityId" class="input_textmin" >
				  <option value="">请选择</option>
				  <c:forEach items="${commodityList}" var="commodity">
					<option value="${commodity.id}">${commodity.id }</option>
				  </c:forEach>
				</select>
	             &nbsp;&nbsp;&nbsp;&nbsp;
	                                    行情价格：
	               <input type="text" id="quoprice"  name="quoprice" class="input_text"  onchange="price('quoprice')"  />
	                &nbsp;<font color="red">(美元/盎司)</font> 
	                <input style="display:none"/>
	          </form>
	           
	           </div>
	        </div>
	                 
	        <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="resultList"
							  autoIncludeParameters="${empty param.autoInc}"
							  var="market"
							  action="${basePath}/marketMaintenance/marketLog/list.action"
							  xlsFileName="marketLogList.xls" 
			                  csvFileName="marketLogList.csv"
							  title="" minHeight="345" listWidth="100%"
							  retrieveRowsCallback="limit" sortRowsCallback="limit"
							  filterRowsCallback="limit" 	style="table-layout:fixed" >
					  <ec:row >

						<ec:column width="4%" property="_0" title="序号"
								   value="${GLOBALROWCOUNT}" sortable="false"
								   filterable="false" style="text-align:center; "/>		 
						<ec:column width="20%" property="commodityId[like]" title="商品代码"
							       style="text-align:center; ">
								   ${market.commodityId }
					    </ec:column>
						<ec:column property="quoprice[=][double]" title="行情价格(美元/盎司)"
								   width="20%" style="text-align:left; " >
						    <fmt:formatNumber value="${market.quoprice}" pattern="#,##0.0000" />
						</ec:column>
						<ec:column property="actionTime[=][timestemp]" title="创建时间"
								   width="20%" style="text-align:center; ">
								 ${ datefn:formatdate(market.actionTime)}
						</ec:column>
						<ec:column width="20%" property="operator[like]" title="录入员"
							       style="text-align:center; ">
								   ${market.operator }
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
  
   function addLog(){
		  var flag = true;
			flag = myblur("all")
			if (flag) {
				if(!isFormChanged(frm_add,null)){
					alert("没有修改内容");
					return false;
				}
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					frm_add.submit();
				} else {
					return false;
				}
			}
	  }
    function myblur(userID) {
		var flag = true;
		if ("commodityId" == userID) {
			flag = commodity(userID);
		} else if ("quoprice" == userID) {
			flag = price(userID);
		} else {
			if (!commodity("commodityId")){
				flag = false;
			}else if (!price("quoprice")){
				flag = false;
			}
			
		}
		
		return flag;
	}
    function commodity(userID){
		var flag = false;
		var str = "商品";
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (isEmpty(user.value)) {
			innerHTML = str + "不能为空";
			alert(innerHTML);
			user.focus();
		} else {
			flag = true;
		}
		return flag;
	}
  function price(userID){
		var flag = false;
		var str = "行情价格";
		var innerHTML = "";
		var user = document.getElementById(userID);
		if (isEmpty(user.value)) {
			innerHTML = str + "不能为空";
			alert(innerHTML);
			user.focus();
		}else if(!IsIntOrFloat(user.value)){
			innerHTML = "不是非负数";	
			alert(innerHTML);
			user.focus();
		}else if(intByNum(user.value,12)){
			innerHTML = "小数点前数字最多12位";	
			alert(innerHTML);
		}else if(!flote(user.value,4)){                                                       
			innerHTML = "最多4位小数的数字";	
			alert(innerHTML);
			user.focus();
		} else {
			flag = true;
		}
		priceScope();
		
		return flag;
	}

	function priceScope(){
		var id = document.getElementById("commodityId").value;
		var prices = document.getElementById("quoprice").value;

		  if(prices){
				checkPrice.judgePrice(id,prices, function(isExist){
					if(!isExist){
						alert('请入符合标准的行情价格');
						document.getElementById("quoprice").value="";
						document.getElementById("quoprice").focus();
					}
				});
		 }	 
	}
 
    //判断是否为非负数（包括整数，浮点数）
	function IsIntOrFloat(num){
	  var reNum=/(^\d)\d*(\.?)\d*$/;
	  return (reNum.test(num));
	}
    //判断整数位
	function intByNum(str,n){
		var flag=false;
		if(str.length>0){
			var strs=new Array();
			strs=str.split(".");
			if(strs.length==1){
				if(str.length>n){
					flag=true;
				}
			}else if(strs.length==2){
				var s=strs[0];
				if(s.length>n){
					flag=true;
				}
			}
		}
		return flag;
	}

</script>
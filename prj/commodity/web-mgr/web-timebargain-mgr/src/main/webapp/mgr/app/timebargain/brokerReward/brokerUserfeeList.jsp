<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
Date sysdate = new Date();
SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
String nowDate = df.format(sysdate);
%>
<html>
  <head>
    <title>手续费汇总</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		  var startDate = frm.beginDate.value;
		  var endDate = frm.endDate.value;
		  
		 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		 {
		  if(frm.beginDate.value=="")
		  {
		    alert("开始日期不能为空！");
		    frm.beginDate.focus();
		    return false;
		  }
		  if(frm.endDate.value=="")
		  {
		    alert("结束日期不能为空！");
		    frm.endDate.focus();
		    return false;
		  }
		  if ( startDate > '<%=nowDate%>' ) { 
		      alert("开始日期不能大于当天日期!"); 
		      frm.beginDate.focus();
		      return false; 
		  } 
		  if ( startDate > endDate ) { 
		      alert("开始日期不能大于结束日期!"); 
		      return false; 
		  } 
		 }  
		 frm.submit();
	  }

	  function initSummarizingWay(){
			//汇总方式value
			var summarizingWay = document.getElementById("summarizingWay");

			//交易商/品种 隐藏域
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//分结算日/汇总 隐藏域
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");
			
			
			//品种
			var breed = document.getElementById("breedId");
			
			if(${summarizingWay1 == 'summarizingFirm'}){
				//交易商
				summarizingWayInpt1.value = "summarizingFirm";
				if(${summarizingWay2 == 'Summarizing'}){
					//汇总
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 1);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "Summarizing";

					breed.disabled = "disabled";
					breed.style.cssText = "background-color: gray;";
				}else if(${summarizingWay2 == 'SettleDay'}){
					//分结算日
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 3);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "SettleDay";

					breed.disabled = "disabled";
					breed.style.cssText = "background-color: gray;";
				}
			}else if(${summarizingWay1 == 'summarizingBreed'}){
				//品种
				summarizingWayInpt1.value = "summarizingBreed";
				if(${summarizingWay2 == 'Summarizing'}){
					//汇总
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 2);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "Summarizing";
				}else if(${summarizingWay2 == 'SettleDay'}){
					//分结算日
					var summarizingWayOpt = eachSummarizingWay(summarizingWay, 4);
					summarizingWayOpt.selected = "selected";

					summarizingWayInpt2.value = "SettleDay";
				}
			}
			
			/*遍历汇总方式,根据val返回val指定的option对象*/
			function eachSummarizingWay(way, val){
				wayChildren = way.childNodes;
				for(var i in wayChildren){
					if(wayChildren[i].value == val){
						return wayChildren[i];
					}
				}
			}

		}

	  /*改变汇总方式*/
		function summarizingWayChange(){
			var summarizingWayVal = document.getElementById("summarizingWay").value;
			//交易商/品种 隐藏域
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//分结算日/汇总 隐藏域
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");

			var breed = document.getElementById("breedId");
			if(summarizingWayVal == '1'){
				//交易商汇总
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "Summarizing";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '2'){
				//品种汇总
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "Summarizing";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}else if(summarizingWayVal == '3'){
				//交易商结算日
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "SettleDay";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '4'){
				//品种结算日
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "SettleDay";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}
		}
    </script>
  </head>
  
  <body onload="initSummarizingWay();">
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/brokerReward/brokerUserFeeList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0" align="center">
						  <tr>  
						      <td class="table3_td_1" align="right">
									交易商开始：
									<label>
										<input type="text" class="input_text" id="startFirm" name="startFirm" maxlength="16"/>
									</label>
								</td>
								<td class="table3_td_1" align="left">
									交易商结束：
									<label>
										<input type="text" class="input_text" id="endFirm"   name="endFirm" maxlength="16"/>
									</label>
								</td>
								<td class="table3_td_1" align="left">
									加盟商编号：
									<label>
										<input type="text" class="input_text" id="brokerid"   name="brokerid" maxlength="16"/>
									</label>
								</td>
							</tr>
							<tr>
								<td class="table3_td_1" align="right" style="width: 250px">
										汇总方式：
										<label>
											<input type = "hidden" id = "summarizingWayInpt1" name = "summarizingWay1" value = ""/>
									        <input type = "hidden" id = "summarizingWayInpt2"name = "summarizingWay2" value = ""/>
											<select id="summarizingWay" name="summarizingWay" onchange="summarizingWayChange()" class="normal">
												<option value="1">分交易商汇总</option>
												<option value="2">分交易商品种汇总</option>
												<option value="3">分交易商结算日汇总</option>
												<option value="4">分交易商品种结算日汇总</option>
											</select>
										</label>
									</td>
									
									<td class="table3_td_1" align="right">
								           品种:&nbsp;
									<label>
									  <select id="breedId" name="breedId">
									    <option value="">全部</option>
										<c:forEach items="${breedStartList}" var="map" >
										  <option value="${map.BREEDID}">${map.BREEDNAME}</option>
						                </c:forEach>
									  </select>
								    </label>
							   </td>
							</tr>
							<tr>
                              <td class="table3_td_1" align="right">
											结算开始日期:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"				
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											结算结束日期:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">重置</button>
						    </td>
					      </tr>
					    </table>
					    <script type="text/javascript">
					      frm.startFirm.value = "<c:out value='${startFirm}'/>";
					      frm.endFirm.value = "<c:out value='${endFirm}'/>";
					      frm.brokerid.value = "<c:out value='${brokerid}'/>";
					      var summarizingWayInput3 = "<c:out value='${summarizingWay}'/>";
					      if(summarizingWayInput3 =="summarizingFirmAndSummarizing"){
							frm.summarizingWay.value=1;
						  }else if(summarizingWayInput3 == "summarizingBreedAndSummarizing"){
							  frm.summarizingWay.value=2;
						  }else if(summarizingWayInput3 == "summarizingFirmAndSettleDay"){
							  frm.summarizingWay.value=3;
						   }else if(summarizingWayInput3 == "summarizingBreedAndSettleDay"){
							   frm.summarizingWay.value=4;
						  };
					      frm.breedId.value = "<c:out value='${breedId}'/>";
					      frm.beginDate.value = "<c:out value='${beginDate}'/>";
					      frm.endDate.value = "<c:out value='${endDate}'/>";
					      summarizingWayChange();
					    </script>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
            </div>
            
            <div class="div_list">
           
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="brokerFeeSum"
							  action="${basePath}/timebargain/brokerReward/brokerUserFeeList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedRewardSum.xls" csvFileName="breedRewardSum.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					 
					     <ec:column property="BROKERID" title="隶属加盟商"  width="10%" style="text-align:center;" />					 
						  <ec:column property="FIRMID" title="交易商代码"  width="10%" ellipsis="true" style="text-align:center;" />
						  <ec:column property="FIRMNAME" title="交易商名称"  width="10%" style="text-align:center;" />
						  <c:if test="${summarizingWay1 == 'summarizingBreed'}">
							<ec:column property="BREEDNAME" title="品种名称"  width="10%" style="text-align:center;" />
						  </c:if>
						  <c:if test="${summarizingWay1 == 'summarizingFirm'}">
							<ec:column property="_1" title="品种名称" value="全部" width="10%" style="text-align:center;" />
						  </c:if>
						  <ec:column property="QUANTITY" title="成交量"  width="10%" style="text-align:right;" calcTitle="合计" format="#,###" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.QUANTITY}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="TRADEFEE" title="交易手续费(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.TRADEFEE}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="FIRSTPAY" title="佣金首款(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.FIRSTPAY}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="SECONDPAY" title="佣金尾款(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.SECONDPAY}" pattern="#,##0.00"/>
						  </ec:column>
						  <c:if test="${summarizingWay2 == 'SettleDay'}">
							<ec:column property="CLEARDATE" title="发生时间" cell="date" format="date" width="10%" style="text-align:center;"/>
						  </c:if>
					
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
  </body>
</html>

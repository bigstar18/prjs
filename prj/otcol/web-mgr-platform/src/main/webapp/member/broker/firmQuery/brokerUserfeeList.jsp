<%@ page pageEncoding="GBK"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.base.query.*'%>
<%
   String memberPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/member";
   
   String nowDate = Utils.formatDate("yyyy-MM-dd",new Date());
   String nowTime = Utils.formatDate("yyyyMMdd HHmm",new Date());
   pageContext.setAttribute("nowDate",nowDate);
   pageContext.setAttribute("nowTime",nowTime);
   
%>

<html xmlns:MEBS>
	<head>
	  <title>手续费汇总表</title>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		 
		<import namespace="MEBS" implementation="<%=memberPath%>/public/calendar.htc">
		
		<script type="text/javascript">
			//重置按钮重新填写
			function resetForm(){
				document.getElementById("sfirmid").value="";
				document.getElementById("brokerid").value="";
				document.getElementById("efirmid").value="";
				frm_query.moduleId.value="";
				document.getElementById("breedName").value="";
				document.getElementById("sOccurDate").value="";
				document.getElementById("eOccurDate").value="";
			}
			//提交表单进行查询
			function submitForm(){
				var startDate = document.getElementById("sOccurDate").value;
				var endDate =  document.getElementById("eOccurDate").value;
				

			  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
			  {
				if(startDate == ""){
					alert("起始日期不能为空！");
					frm_query.sOccurDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("结束日期不能为空！");
					frm_query.eOccurDate.focus();
					return false;
					
				}
				if(!isDateFomat(startDate))
			    {
			        alert("起始日期格式不正确！\n如：" + '<%=nowDate%>');
			        frm_query.sOccurDate.value = "";
			        frm_query.sOccurDate.focus();
			        return false;
			    }
			    if(!isDateFomat(endDate))
			    {
			        alert("结束日期格式不正确！\n如：" + '<%=nowDate%>');
			        frm_query.eOccurDate.value = "";
			        frm_query.eOccurDate.focus();
			        return false;
			    }
			  
			    if ( startDate > '<%=nowDate%>' ) { 
			        alert("起始日期不能大于当天日期!"); 
			        frm_query.sOccurDate.focus();
			        return false; 
			    } 
			    if ( startDate > endDate ) { 
			        alert("起始日期不能大于结束日期!"); 
			        return false; 
			    } 
			  }  
				frm_query.submit();
			}
			
			function initSummarizingWay(){
				//汇总方式value
				var summarizingWay = document.getElementById("summarizingWay");

				//交易商/品种 隐藏域
				var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
				//分结算日/汇总 隐藏域
				var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");
				
				//品种
				var breed = document.getElementById("breedName");
				
				if(${oldParams['summarizingWay1'] == 'summarizingFirm'}){
					//交易商
					summarizingWayInpt1.value = "summarizingFirm";
					if(${oldParams['summarizingWay2'] == 'Summarizing'}){
						//汇总
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 1);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "Summarizing";

						breed.disabled = "disabled";
						breed.style.cssText = "background-color: gray;";
					}else if(${oldParams['summarizingWay2'] == 'SettleDay'}){
						//分结算日
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 3);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "SettleDay";

						breed.disabled = "disabled";
						breed.style.cssText = "background-color: gray;";
					}
				}else if(${oldParams['summarizingWay1'] == 'summarizingBreed'}){
					//品种
					summarizingWayInpt1.value = "summarizingBreed";
					if(${oldParams['summarizingWay2'] == 'Summarizing'}){
						//汇总
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 2);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "Summarizing";
					}else if(${oldParams['summarizingWay2'] == 'SettleDay'}){
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
		</script>
	</head>
	<body leftmargin="2" topmargin="0" onload="initSummarizingWay()">
	
		<table width="100%">
		    <tr>
				<td>
					<form name="frm_query" action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=brokerUserFeeList"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件</b>
							</legend>
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35" width="100%">
							<td align="right" style="width: 12%">
								交易商开始：
							</td>
							<td align="left" style="width: 12%">
								<input name="_fb.firmid[>=]" id="sfirmid" size=10 type=text value="<c:out value='${oldParams["fb.firmid[>=]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right" style="width: 12%">
								交易商结束：
							</td>
							<td align="left" style="width: 12%">
								<input name="_fb.firmid[<=]" id="efirmid" size=10 type=text value="<c:out value='${oldParams["fb.firmid[<=]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right" style="width: 8%">
								结算日期：
							</td>
							<td align="left" style="width: 27%">
							<MEBS:calendar eltName="_trunc(OccurDate)[>=][date]" eltCSS="date" eltID="sOccurDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(OccurDate)[>=][date]"]}'/>"/>
							&nbsp;至&nbsp;
							<MEBS:calendar eltName="_trunc(OccurDate)[<=][date]" eltCSS="date" eltID="eOccurDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(OccurDate)[<=][date]"]}'/>"/>
							</td>
							
						</tr>
						<tr height="35" width="100%">
							<td align="right">汇总方式：</td>
							<td align="left">
								<input type = "hidden" id = "summarizingWayInpt1" name = "_summarizingWay1" value = ""/>
								<input type = "hidden" id = "summarizingWayInpt2"name = "_summarizingWay2" value = ""/>
								<select id = "summarizingWay" onchange="summarizingWayChange()">
									<option value="1">分交易商汇总</option>
									<option value="2">分交易商品种汇总</option>
									<option value="3">分交易商结算日汇总</option>
									<option value="4">分交易商品种结算日汇总</option>
								</select>
								<!-- 
								<select name="_summarizingWay1">
								<c:if test="${oldParams['summarizingWay1'] == 'summarizingFirm'}">
									<option selected="selected" value="summarizingFirm">交易商</option>
									<option value="summarizingBreed">品&nbsp; 种</option>
								</c:if>
								<c:if test="${oldParams['summarizingWay1'] == 'summarizingBreed'}">
									<option value="summarizingFirm">交易商</option>
									<option selected="selected" value="summarizingBreed">品&nbsp; 种</option>
								</c:if>
								</select>
								
								&nbsp;&nbsp;&nbsp;&nbsp;
								<select name="_summarizingWay2">
								<c:if test="${oldParams['summarizingWay2'] == 'Summarizing'}">
									<option selected="selected" value="Summarizing">汇&nbsp;&nbsp;&nbsp; 总</option>
									<option value="SettleDay">分结算日</option>
								</c:if>
								<c:if test="${oldParams['summarizingWay2'] == 'SettleDay'}">
									<option value="Summarizing">汇&nbsp;&nbsp;&nbsp; 总</option>
									<option selected="selected" value="SettleDay">分结算日</option>
								</c:if>
								</select> -->
							</td>
							
							<td align="right">
								加盟商编号：
							</td>
							<td align="left">
								<input name="_brokerid[like]" id="brokerid" size=10 type=text value="<c:out value='${oldParams["brokerid[like]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							
							<td align="right">品种：</td>
							<td align="left">
								<input type="text" size="10" name="_breedName[like]" id="breedName" value="<c:out value='${oldParams["breedName[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模块：
								<select id="moduleId" name="_moduleId[=]">
									<option selected="true" value="">全部</option>
									<option value="2">订单</option>
									<option value="3">挂牌</option>
									<option value="4">竞价</option>
								</select>
							</td>
							<script>
								frm_query.moduleId.value = "<c:out value='${oldParams["moduleId[=]"]}'/>";
							</script>
							<td align="left">
								<input type="button" class="btn" onclick="submitForm();" value="查询">&nbsp;
								<input type="button" class="btn" onclick="resetForm();"value="重置">&nbsp;
							</td>
						</tr>
					</table>
						</fieldset>
					</form>
				</td>
			</tr>
			
			<tr>
				<td>
				    <c:set var="time" value="${nowTime}" ></c:set>
					<ec:table items="resultList" var="brokerFeeSum"
						action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=brokerUserFeeList"
						xlsFileName="手续费汇总${nowTime}.xls"
					    showPrint="false"
						rowsDisplayed="20" listWidth="100%" 
						minHeight="300"
						filterable="false">
						<ec:row>
						  <ec:column property="brokerId" title="隶属加盟商"  width="10%" style="text-align:center;" />
						  <ec:column property="moduleId" title="模块"  width="10%" style="text-align:center;" >
						     <c:if test="${brokerFeeSum.moduleId=='2'}">订单</c:if>
	  						 <c:if test="${brokerFeeSum.moduleId=='3'}">挂牌</c:if>
	  						 <c:if test="${brokerFeeSum.moduleId=='4'}">竞价</c:if>
						  </ec:column>
						  <ec:column property="firmId" title="交易商代码"  width="10%" style="text-align:center;" />
						  <ec:column property="firmName" title="交易商名称"  width="10%" style="text-align:center;" />
						  <c:if test="${oldParams['summarizingWay1'] == 'summarizingBreed'}">
							<ec:column property="breedName" title="品种名称"  width="10%" style="text-align:center;" />
						  </c:if>
						  <c:if test="${oldParams['summarizingWay1'] == 'summarizingFirm'}">
							<ec:column property="_1" title="品种名称" value="全部" width="10%" style="text-align:center;" />
						  </c:if>
						  <ec:column property="quantity" title="成交量"  width="10%" style="text-align:right;" calcTitle="合计" format="#,###" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.quantity}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="tradeFee" title="交易手续费(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.tradeFee}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="firstPay" title="佣金首款(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.firstPay}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="secondPay" title="佣金尾款(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.secondPay}" pattern="#,##0.00"/>
						  </ec:column>
						  <c:if test="${oldParams['summarizingWay2'] == 'SettleDay'}">
							<ec:column property="occurdate" title="发生时间" cell="date" format="date" width="10%" style="text-align:center;"/>
						  </c:if>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">

		/*改变汇总方式*/
		function summarizingWayChange(){
			var summarizingWayVal = document.getElementById("summarizingWay").value;
			//交易商/品种 隐藏域
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//分结算日/汇总 隐藏域
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");

			var breed = document.getElementById("breedName");
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
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

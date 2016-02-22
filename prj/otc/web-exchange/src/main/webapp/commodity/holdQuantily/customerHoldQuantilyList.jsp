<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" 
			cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/customerHoldQuantily/list.action?sortName=primary.commodityId"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmin">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														商品名称:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}commodity.name[like]"
																id="commodityName"
																value="${oldParams['commodity.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="submitMember()">查询</button>&nbsp;&nbsp;
														<button  class="btn_cz" onclick="myReset()">重置</button>
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
							<button class="anniu_btn" onclick="addMemberQuotePoint()"
								id="update">
								添加
							</button>
							<button class="anniu_btn" onclick="deleteMemberQuotePoint()" id="delete">
								删除
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="holdQuantily"
											action="${basePath}/commodity/customerHoldQuantily/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${holdQuantily.firmId}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" title="商品名称" width="10%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${holdQuantily.firmId}','${holdQuantily.commodityId}');"><font color="#880000">${holdQuantily.commodityName }</font> </a> </ec:column>
												<ec:column property="primary.firmId[like]" title="客户名称" width="10%"	style="text-align:left;" ellipsis="true" >${holdQuantily.memberName} </ec:column>
												<ec:column property="primary.oneMaxOrderQty[=][int]" title="单笔最大下单量"
													width="16%" style="text-align:right;"
													value="${holdQuantily.oneMaxOrderQty}" />
												<ec:column property="primary.oneMinOrderQty[=][int]" title="单笔最小下单量"
													width="16%" style="text-align:right;"
													value="${holdQuantily.oneMinOrderQty}" />
												<ec:column property="primary.maxHoldQty[=][int]" title="最大持仓量"
													width="16%" style="text-align:right;"
													value="${holdQuantily.maxHoldQty}" />
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
		<!-- 编辑和过滤所使用的默认交易商模板 -->
		<textarea id="ecs_t_firmDis" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.firmId[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>

		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/commodity/customerHoldQuantily/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.firmId="+firmId;
			
			ecsideDialog(url,"",580,520);
		}
		function submitMember(){
			frm.submit();
		}
		function addMemberQuotePoint(){
			var url="${basePath}/commodity/customerHoldQuantily/forwardAdd.action";
			ecsideDialog(url,"",580,453);
		}
		function deleteMemberQuotePoint(){
	  		var url="${basePath}/commodity/customerHoldQuantily/delete.action";
	 		deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>
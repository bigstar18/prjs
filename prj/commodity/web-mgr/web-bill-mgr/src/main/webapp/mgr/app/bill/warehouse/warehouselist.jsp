<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>仓库列表</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/stock/warehouse/list.action?sortColumns=order+by+id+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" >
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top" style="margin-left: 0px">
												<table class="table3_style" border="0" cellspacing="0" 
													cellpadding="0" width="100%">
													<tr>
														<td class="table3_td_1" align="right">
															仓库编号：
															<label>
																<input id="id" type="text" class="input_text"  style="width: 110px;"
																maxLength="<fmt:message key='warehouseId_q' bundle='${PropsFieldLength}'/>"
																	name="${GNNT_}primary.warehouseId[allLike]"
																	value="${oldParams['primary.warehouseId[allLike]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															仓库名称：
															<label>
																<input type="text" class="input_text" style="width: 110px;"
																maxLength="<fmt:message key='warehouseName_q' bundle='${PropsFieldLength}'/>"
																
																	name="${GNNT_}primary.warehouseName[allLike]"
																	value="${oldParams['primary.warehouseName[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu">
															<button class="btn_sec" onclick=select1();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																	重置
															</button>
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
							<rightButton:rightButton name="添加" onclick="addWarehouse();" className="btn_sec" action="/stock/warehouse/addForward.action" id="add"></rightButton:rightButton>
							&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteWarehouse();" className="btn_sec" action="/stock/warehouse/delete.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="warehouse"
											action="${basePath}/stock/warehouse/list.action?sortColumns=order+by+id+desc"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="warehouse.xls" csvFileName="warehouse.csv" 
											showPrint="true" listWidth="100%" minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${warehouse.id}"
													width="5%" viewsAllowed="html" />
												<ec:column width="10%" property="_0" title="序号" style="text-align:center;"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="id" width="15%" title="编号"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/warehouse/forwardUpdate.action" id="detail" text="<font color='#880000'>${warehouse.id}</font>" onclick="details(${warehouse.id})"/>
												</ec:column>
												<ec:column property="warehouseId" width="15%" title="仓库编号"
													style="text-align:left;" ellipsis="true">
												</ec:column>
												<ec:column property="warehouseName" width="20%" title="仓库名称"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="status" width="15%" title="仓库状态"
													style="text-align:center;">
													
													${warehouseStatusMap[warehouse.status]}
												</ec:column>
												<c:if test="${haveWarehouse =='0'}">
												<ec:column property="status" width="50%" title="数据操作"
													style="text-align:center;" sortable='false' ellipsis="true">
													<c:if test="${not empty map[warehouse.warehouseId]}">
														<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/warehouseUser/updateForwardPassword.action" id="updatePassword" text="<font color='#880000'>修改超级管理员密码</font>" onclick="updatePassword('${map[warehouse.warehouseId].userId}')"/>
													</c:if>
													<c:if test="${empty map[warehouse.warehouseId]}">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/stock/warehouseUser/addForward.action" id="AddWuser" text="<font color='#880000'>添加超级管理员</font>" onclick="AddWuser('${warehouse.warehouseId}')"/>
													</c:if>
												</ec:column>
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


			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" /></textarea>

<SCRIPT type="text/javascript">
	function addWarehouse(){
			var a=document.getElementById("add").action;
			var url="${basePath}"+a;
			if( ${haveWarehouse =='0'}){
				if(showDialog(url, "", 700, 530)){
					frm.submit();
				}
			}else{
				if(showDialog(url, "", 700, 530)){
					frm.submit();
				}
			}
				

		}

	
	function select1() {
		frm.submit();
	}
	
	function details(id){
		var a=document.getElementById("detail").action;
		var url="${basePath}"+a+"?entity.id="+id;
		if(showDialog(url, "", 700, 500)){
			frm.submit();
		}
	}
	function deleteWarehouse() {
		var a=document.getElementById('delete').action;
		var url = "${basePath}"+a+"?autoInc=false";
		deleteEcside(ec.ids, url);
	}
	//添加仓库超级管理员
	function AddWuser(id){
		var a=document.getElementById("AddWuser").action;
		var url="${basePath}"+a+"?warehouseId="+id;
		if(showDialog(url, "", 600, 400)){
			frm.submit();
		}
	}
	//修改仓库超级管理员密码
	function updatePassword(id){
		var a=document.getElementById("updatePassword").action;
		var url="${basePath}"+a+"?entity.userId="+id;
		if(showDialog(url, "", 500, 400)){
			frm.submit();
		}
	}
</SCRIPT>
	</body>
</html>
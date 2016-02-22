<%@ page pageEncoding="GBK"%>
<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
			<form id="ec" method="post" style="width: 100%; visibility: hidden;"
				nearPages="3" filterable="true" canResizeColWidth="true"
				maxRowsExported="1000000" minColWidth="45"
				action="${basePath}/student/list.action">
				<div class="ecSide" id="ec_main_content" style="width: 100%;">
					<!-- ECS_AJAX_ZONE_PREFIX__begin_ ec_ECS_AJAX_ZONE_SUFFIX -->
					<div>
						<input type="hidden" name="ec_i" value="ec" />
						<input type="hidden" name="eti" />
						<input type="hidden" name="eti_p" />
						<input type="hidden" name="ec_efn" />
						<input type="hidden" name="ec_ev" />
						<input type="hidden" name="ec_crd"
							value="<c:out value="${pageInfo.pageSize}" />" />
						<input type="hidden" name="ec_f_a" />
						<input type="hidden" name="ec_p" value="1" />

						<input type="hidden" name="ec_totalpages"
							value="<c:out value="${pageInfo.totalPages}" />" />
						<input type="hidden" name="ec_totalrows"
							value="<c:out value="${pageInfo.totalRecords}"/>" />
					</div>

					<!-- 查询字段 -->
					<%@ include file="table/queryField.jsp"%>
					<!-- 查询字段 -->

					<!-- 查询数据 -->
					<%@ include file="table/queryData.jsp"%>
					<!-- 查询数据 -->

					<!--底部工具栏 -->
					<%@ include file="/public/pagerInc.jsp"%>
					<!--底部工具栏-->

					<div id="ec_toolbarShadow" style="display: none;"></div>
					<!-- ECS_AJAX_ZONE_PREFIX_ _end_ec_ECS_AJAX_ZONE_SUFFIX -->
				</div>
			</form>
			<div id="ec_waitingBar" class="waitingBar"></div>
			<div id="ec_waitingBarCore" class="waitingBarCore"></div>
			
		</td>
	</tr>
</table>
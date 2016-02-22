<%@ page pageEncoding="GBK"%>
 <%@ include file="/applyAndAudit/public/commodityIsOnMarket.jsp"  %>
<input type="hidden" name="buttonClick" value="">
<div class="tab_pad">
	<table border="0" cellspacing="0" cellpadding="0" width="95%"
		align="center">
		<c:if test="${applyObj!=null}">
			<tr align="center">
				<td>
					<font color="red">状态：待审批，此申请由${applyObj.proposer}于${datefn:formatdate(applyObj.modTime)}提交</font>
				</td>
			</tr>
			<tr align="center">
				<td>&nbsp;
				</td>
			</tr>
		</c:if>
		<c:forEach items="${buttonList}" var="result">
			<tr>
				<c:if test="${applyObj==null&&(isOpenOperationButton==null || isOpenOperationButton!='N')}">
					<td align="center">
						<button class="btn_secmid" name="buttons"
							onclick="audit('${result.operateKey}')" id="update">
							${result.operateName}
						</button>
					</td>
				</c:if>
				<td align="center">
					<button class="btn_secmid" onClick="window.close()">
						关&nbsp;&nbsp;&nbsp;&nbsp;闭
					</button>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

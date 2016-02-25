<%@ page pageEncoding="GBK"%>
<table id="tableList" border="0" cellspacing="0" cellpadding="0"
	width="100%" height="400">
	<tHead>
		<tr>
			<td class="panel_tHead_LB">
				&nbsp;
			</td>
			<td class="panel_tHead_MB">
				<input type="checkbox" id="checkAll"
					onclick="selectAll(tableList,'delCheck')">
			</td>
			<td class="panel_tHead_MB" abbr="deliveryId" align="center">
				交收编号
			</td>
			<td class="panel_tHead_MB" abbr="tradeNo" align="center">
				合同号
			</td>
			<td class="panel_tHead_MB" abbr="breedId" align="center">
				品种代码
			</td>
			<td class="panel_tHead_MB" abbr="quantity" align="center">
				仓单对应冻结数量
			</td>
			<td class="panel_tHead_MB" abbr="price" align="center">
				单价
			</td>
			<td class="panel_tHead_MB" abbr="firmId_S" align="center">
				卖方交易商代码
			</td>
			<td class="panel_tHead_MB" abbr="firmId_B" align="center">
				买方交易商代码
			</td>
			<td class="panel_tHead_MB" abbr="type" align="center">
				类型
			</td>
			<td class="panel_tHead_RB">
				&nbsp;
			</td>
		</tr>
	</tHead>
	<tBody>
		<c:forEach items="${resultList}" var="result">
			<tr onclick="selectTr();">
				<td class="panel_tBody_LB">
					&nbsp;
				</td>
				<td class="underLine">
					<input name="delCheck" type="checkbox"
						value="<c:out value="${result.deliveryId}"/>">
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.deliveryId}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.tradeNo}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.breedId}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<fmt:formatNumber value="${result.quantity}" type="currency" pattern="${sessionScope.quantityDecimalsString }"/>
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.price}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.firmId_S}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:out value="${result.firmId_B}" />
					&nbsp;
				</td>
				<td class="underLine" align="center">
					<c:if test="${result.type==1}">履约</c:if>
					<c:if test="${result.type==2}">买方违约</c:if>
					<c:if test="${result.type==3}">卖方违约</c:if>
					<c:if test="${result.type==4}">双方违约</c:if>
					<c:if test="${result.type==5}">自主交收</c:if>
				</td>
				<td class="panel_tBody_RB">
					&nbsp;
				</td>
			</tr>

		</c:forEach>
	</tBody>
	<tFoot>
		<tr height="100%">
			<td class="panel_tBody_LB">
				&nbsp;
			</td>
			<td colspan="9">
				&nbsp;
			</td>
			<td class="panel_tBody_RB">
				&nbsp;
			</td>
		</tr>
		<c:choose>
			<c:when test="${hasWarehouse=='Y'}">
				<tr height="22">
					<td class="pager" colspan="10" align="center">
						<!--======================================= 有仓库================================================================ -->
						<button class="lgrbtn" type="button" onclick="matchSettle();"
							<c:choose> <c:when test="${hisTrade.isRegstock =='Y'}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							添加配对
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="auditing()"
							<c:choose> <c:when test="${hisTrade.isRegstock =='Y'}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							违约处理
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="deleteMatch();"
							<c:choose> <c:when test="${hisTrade.isRegstock =='Y'}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							废除配对
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="autoDelivery();"
							<c:choose> <c:when test="${hisTrade.isRegstock =='Y'}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							自主交收
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button"
							onclick="send('<c:out value="${hisTrade.tradeNo }"/>')"
							<c:choose> <c:when test="${num==0}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							发送仓库
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="back()">
							返回
						</button>
					</td>
					<td class="panel_tBody_RB">
						&nbsp;
					</td>
				</tr>
				
			</c:when>
			<c:otherwise>
				<tr height="22">
					<td class="pager" colspan="11" align="center">

						<!--================================================ 没有仓库 ===========================================================-->
						<button class="lgrbtn" type="button" onclick="matchOneSettle();"
							<c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if><c:if test="${hisTrade.isRegstock=='Y' }">disabled="disabled"</c:if>>
							添加配对
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="auditing()"
							<c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if><c:if test="${hisTrade.isRegstock=='Y' }">disabled="disabled"</c:if>>
							违约处理
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="deleteMatch();"
							<c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if><c:if test="${hisTrade.isRegstock=='Y' }">disabled="disabled"</c:if>>
							废除配对
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="autoDelivery();"
							<c:choose> <c:when test="${hisTrade.isRegstock =='Y'}">disabled="disabled"</c:when><c:otherwise><c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if></c:otherwise></c:choose>>
							自主交收
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button"
							onclick="send('<c:out value="${hisTrade.tradeNo }"/>')"
							<c:if test="${hisTrade.status==3 }">disabled="disabled"</c:if>>
							确认
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="lgrbtn" type="button" onclick="back()">
							返回
						</button>
					</td>
					<td class="panel_tBody_RB">
						&nbsp;
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr height="22">
			<td class="panel_tFoot_LB">
				&nbsp;
			</td>
			<td class="panel_tFoot_MB" colspan="9"></td>
			<td class="panel_tFoot_RB">
				&nbsp;
			</td>
		</tr>
	</tFoot>
</table>
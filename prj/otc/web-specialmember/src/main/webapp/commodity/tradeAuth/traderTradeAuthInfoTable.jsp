<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />
				&nbsp;&nbsp;交易权限设置
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" name="obj.commodityId"
					value="${obj.commodityId}">
				<tr height="20">
					<td align="right">
						商品名称:
					</td>
					<td>
						<input type="text" id="name" class="input_textmid1"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						交易账号:
					</td>
					<td>
						<input type="text" name="obj.firmId" value="${obj.firmId}" id="firmId" class="input_textmid1" readonly="readonly">
					</td>
					<td align="left" height="40">
						<div id="firmId_vTip"></div>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						显示权限:
					</td>
					<td>
						<select id="display" name="obj.display" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.display==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						买入市建:
					</td>
					<td>
						<select id="m_B_Open" name="obj.m_B_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_B_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						卖出市建:
					</td>
					<td>
						<select id="m_S_Open" name="obj.m_S_Open" style="width: 140">
							<c:forEach items="${authorityMap}" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_S_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>

				<tr height="20">
					<td align="right">
						买入市平:
					</td>
					<td>
						<select id="m_B_Close" name="obj.m_B_Close" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_B_Close==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						卖出市平:
					</td>
					<td>
						<select id="m_S_Close" name="obj.m_S_Close" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.m_S_Close==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						买入止建:
					</td>
					<td>
						<select id="l_B_Open" name="obj.l_B_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						卖出止建:
					</td>
					<td>
						<select id="l_S_Open" name="obj.l_S_Open" style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						买入止损:
					</td>
					<td>
						<select id="l_B_CloseLose" name="obj.l_B_CloseLose"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_CloseLose==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						卖出止损:
					</td>
					<td>
						<select id="l_S_CloseLose" name="obj.l_S_CloseLose"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_CloseLose==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						买入止盈:
					</td>
					<td>
						<select id="l_B_CloseProfit" name="obj.l_B_CloseProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_B_CloseProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						卖出止盈:
					</td>
					<td>
						<select id="l_S_CloseProfit" name="obj.l_S_CloseProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.l_S_CloseProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						撤销止建:
					</td>
					<td>
						<select id="cancel_L_Open" name="obj.cancel_L_Open"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_L_Open==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						撤销止损:
					</td>
					<td>
						<select id="cancel_StopLoss" name="obj.cancel_StopLoss"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_StopLoss==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="20">
					<td align="right">
						撤销止盈:
					</td>
					<td>
						<select id="cancel_StopProfit" name="obj.cancel_StopProfit"
							style="width: 140">
							<c:forEach items="${authorityMap }" var="maps">
								<option value="${maps.key}"
									<c:if test="${obj.cancel_StopProfit==maps.key}">selected="selected"</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						控制方式:
					</td>
					<td>
						<select id="operate" name="obj.operate" class="select_widmid" style="width: 140">
							<option value="P">
								个性化
							</option>
							<option value="D">
								默认
							</option>
						</select>
						<script type="text/javascript">
							document.getElementById('operate').value = '${obj.operate}';
						</script>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


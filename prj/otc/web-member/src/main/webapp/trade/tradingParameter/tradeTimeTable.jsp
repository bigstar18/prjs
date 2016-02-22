<%@ page pageEncoding="GBK"%>
	<table cellSpacing="0" cellPadding="5" width="100%" border="0"
		align="center" class="common">
		<tr>
			<td align="right">
				交易节编号：
			</td>
			<td>
				<input class="from" type="text" name="obj.sectionId" value="${obj.sectionId}" id="sectionId" maxlength="10" class="text">
				<span class="req">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				交易节名称：
			</td>
			<td>
				<input class="from" type="text" name="obj.name" id="name" value="${obj.name}">
				<span class="req">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				交易开始时间：
			</td>
			<td>
				<input class="from" type="text" name="obj.startTime" value="${obj.startTime}" id="startTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<span class="req">* HH:MM:SS</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				交易结束时间：
			</td>
			<td>
				<input class="from" type="text" name="obj.endTime" value="${obj.endTime}" id="endTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<span class="req">* HH:MM:SS</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				状态：
			</td>
			<td>
				<select name="obj.status" id="status" style="width: 150">
					<c:forEach items="${tradeStatusMap}" var="tradeStatusMap">
						<option value="${tradeStatusMap.key}">
							<c:out value="${tradeStatusMap.value}" />
						</option>
					</c:forEach>
				</select>
				<span class="req">*</span>
			</td>
	</table>



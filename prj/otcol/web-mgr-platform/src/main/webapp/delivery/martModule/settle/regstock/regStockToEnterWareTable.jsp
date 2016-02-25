<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"></td>
				<td class="panel_tHead_MB" abbr="to_number(regStockId)">ע�ᵥ��</td>
				<td class="panel_tHead_MB" abbr="commodityName">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMID}</td>
				<td class="panel_tHead_MB" abbr="warehouseName">�ֿ�����</td>
				<td class="panel_tHead_MB" abbr="type">�ֵ�����</td>
				<td class="panel_tHead_MB" abbr="relTurnToWeight">ת������</td>
				<td class="panel_tHead_MB" abbr="createDate">����ʱ��</td>
				<td class="panel_tHead_MB" abbr="modifyTime">�޸�ʱ��</td>
				<td class="panel_tHead_MB" abbr="statusName">���״̬</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"></td>
	  			<td class="underLine"><c:out value="${result.regStockId}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.commodityName}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.firmId}"/>&nbsp;</td>
				<td class="underLine"><c:out value="${result.warehouseName}"/>&nbsp;</td>
				
				<td class="underLine">
					<c:choose>
						<c:when test="${result.type==0 }">��׼�ֵ�</c:when>
						<c:when test="${result.type==1 }">�Ǳ�׼�ֵ�</c:when>
						<c:when test="${result.type==2 }">��ʱ�ֵ�</c:when>
					</c:choose>
	  			</td>
	  			<td class="underLine"><c:out value="${result.relTurnToWeight}"/>&nbsp;</td>
				<td class="underLine"><fmt:formatDate value="${result.createDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLine"><fmt:formatDate value="${result.modifyTime}" pattern="yyyy-MM-dd"/>&nbsp;</td>
				<td class="underLine">
					<font color="red">
						<c:out value="${result.statusName}"/>&nbsp;
					</font>		
				</td>
					
				<td class="underLine"><span onclick="fAuditing('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">����->></span></td> 
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11">
					<%@ include file="../../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		</table>
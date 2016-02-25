<%@ page pageEncoding="GBK" %>    
    <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
		        <td class="panel_tHead_MB" abbr="to_number(id)">��ⵥ��</td>
		        <td class="panel_tHead_MB" abbr="to_number(enterinformid)">֪ͨ����</td>
				<td class="panel_tHead_MB" abbr="firmId">${FIRMID}</td>
				<td class="panel_tHead_MB" abbr="wareHouseName">�ֿ�</td>
				<td class="panel_tHead_MB" abbr=lot>����</td>	
				<td class="panel_tHead_MB" abbr="commodityName">Ʒ������</td>
				<td class="panel_tHead_MB" abbr="weight">��ʼ����</td>
				<td class="panel_tHead_MB" abbr="existAmount">�ִ�����</td>
				<td class="panel_tHead_MB" abbr="frozenAmount">��������</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">��λ</td>
				<td class="panel_tHead_MB" abbr="enterDate">���ʱ��</td>
				<td class="panel_tHead_MB" abbr="statusName">״̬</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_RB">&nbsp</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.id}"/></td>
	  			<td class="underLine"><c:out value="${result.enterinformid}"/></td>
	  			<td class="underLine"><c:out value="${result.firmId}"/></td>
				<td class="underLine"><c:out value="${result.wareHouseName}"/></td>
				<td class="underLine"><c:out value="${result.lot}"/></td>	
				<td class="underLine"><c:out value="${result.commodityName}"/></td>
				<td class="underLine"><c:out value="${result.weight}"/></td>
				<td class="underLine"><c:out value="${result.existAmount}"/></td>
				<td class="underLine"><c:out value="${result.frozenAmount}"/></td>
				<td class="underLine"><c:out value="${result.existAmount - result.frozenAmount}"/></td>
				<td class="underLine">
				  <c:if test="${result.countType!=null}">
				    (<c:out value="${result.countType}"/>)
				  </c:if>
				  <c:if test="${result.countType == null}">
				    &nbsp;
				  </c:if>
				</td>		
				<td class="underLine"><fmt:formatDate value="${result.enterDate}" pattern="yyyy-MM-dd"/></td>
				<td class="underLine"><FONT COLOR="red"><c:out value="${result.statusName}"/></FONT></td>
				<td class="underLine"><span onclick="fView('<c:out value="${result.id}"/>')" style="cursor:hand;color:blue">����->></span></td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="14">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="14">
					<%@ include file="../../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="14"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		</table>
		
	
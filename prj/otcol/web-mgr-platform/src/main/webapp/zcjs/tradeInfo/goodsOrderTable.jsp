<%@ page pageEncoding="GBK" %>
<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="goodsorderid">id</td>
				<td class="panel_tHead_MB" abbr="tradecommoditymsgid">��ƷId</td>
				<td class="panel_tHead_MB" abbr="firmid">������Id</td>
				<td class="panel_tHead_MB" abbr="price">�۸�</td>
				<td class="panel_tHead_MB" abbr="quantity">����</td>
				<td class="panel_tHead_MB" abbr="partbargainquantity">�ѳɽ�����</td>
				<td class="panel_tHead_MB" abbr="regstockid">�ֵ���</td>
				<!--  td class="panel_tHead_MB" abbr="tradepoundage">����������</td>-->
				<td class="panel_tHead_MB" abbr="tradebail">���ױ�֤��</td>
				<td class="panel_tHead_MB" abbr="goodsorderdate">�ҵ�ʱ��</td>
				<!--  td class="panel_tHead_MB" abbr="modifydate">�޸�ʱ��</td>-->
				<td class="panel_tHead_RB" >&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<c:forEach items="${resultList}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.goodsorderid}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.tradecommoditymsgid}"/>&nbsp;</td>
			  			<td class="underLine"><c:out value="${result.firmid}"/>&nbsp;</td>
						<td class="underLine"><c:out value="${result.price}"/>&nbsp;</td>
						<td class="underLine"><c:out value="${result.quantity}"/>&nbsp;</td>
						<td class="underLine"><c:out value="${result.partbargainquantity}"/>&nbsp;</td>
						<td class="underLine"><c:out value="${result.regstockid}"/>&nbsp;</td>
						<!--  td class="underLine"><c:out value="${result.tradepoundage}"/>&nbsp;</td>-->
						<td class="underLine"><c:out value="${result.tradebail}"/>&nbsp;</td>
						<td class="underLine"><fmt:formatDate value="${result.goodsorderdate }" pattern="yyyy-MM-dd" type="both"/>&nbsp;</td>
						<!--td class="underLine"><c:out value="${result.modifydate}"/>&nbsp;</td-->
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="9">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="9">
					<%@ include file="../../common/public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="9"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
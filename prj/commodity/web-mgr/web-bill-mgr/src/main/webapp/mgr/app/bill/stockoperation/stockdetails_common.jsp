<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					�ֵ���Ϣ
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4"
					width="100%" align="center" class="table2_style">
					<s:iterator id="stockOperation"
						value="#attr.pageInfo.result">
						<tr>
							<td width="20%" align="right">
								�ֵ��ţ�
							</td>
							<td width="30%">
								${stockOperation.stock.stockId }
								<input type="hidden" id="stock.stockId"
									name="stock.stockId" class="input_text"
									value="${stockOperation.stock.stockId}" />
							</td>
							<td width="20%" align="right">
								�ֿ��ţ�
							</td>
							<td width="30%">
								${stockOperation.stock.warehouseId }
								<input type="hidden" id="stock.warehouseId"
									name="stock.warehouseId" class="input_text"
									value="${stockOperation.stock.warehouseId}" />
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">
								�ֿ�ԭʼƾ֤�ţ�
							</td>
							<td width="30%">
								<div style="width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${stockOperation.stock.realStockCode}</div>
							</td>
							<td align="right" width="20%">
								���������̣�
							</td>
							<td width="20%">
								${stockOperation.stock.ownerFirm}
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								Ʒ����
							</td>
							<td width="30%">
								${stockOperation.stock.breed.breedName }
							</td>
							<td width="20%" align="right">
								��Ʒ������
							</td>
							<td width="30%"><div style="width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
								<fmt:formatNumber
									value="${stockOperation.stock.quantity }"
									pattern="#,##0.00" />
								��${stockOperation.stock.unit }��					
							</div>

							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								����ʱ�䣺
							</td>
							<td width="30%">
								<fmt:formatDate
									value="${stockOperation.stock.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="20%" align="right">
								�����ʱ�䣺
							</td>
							<td width="30%">
								<fmt:formatDate
									value="${stockOperation.stock.lastTime }"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>

					</s:iterator>
				</table>

<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>ǿ�Ƽ���������Ϣ</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post">
			<div style="overflow-x: hidden; position: relative; z-index: 1; overflow: auto; height: 450px; width: 100%;">
			<div class="div_cx">
				<table border="0" width="95%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :ǿ�Ƽ���������Ϣ
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											ǿ�Ƽ�����Ϣ
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%"
											align="center" class="table2_style">
											<tr height="30">
												<td align="right" width="30%">
													ǿ�����ڣ�
												</td>
												<td width="20%">
													<fmt:formatDate value="${entity.deductDate}" pattern="yyyy-MM-dd"/>
												</td>
												<td align="right" width="30%">
													��Ʒ���룺
												</td>
												<td width="20%">
													${entity.commodityId}
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ӯ���ּ�����1��
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.profitLvl1 }' /><span>%</span>
												</td>
												<td align="right" width="30%">
													ǿ���۸�
												</td>
												<td width="20%">
													${entity.deductPrice }
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ӯ���ּ�����2��
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.profitLvl2 }' /><span>%</span>
												</td>
												<td align="right" width="30%">
													����������־��
												</td>
												<td width="20%">
													${loserBSFlagMap[entity.loserBSFlag ]}
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ӯ���ּ�����3��
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.profitLvl3 }' /><span>%</span>
												</td>
												<td align="right" width="30%">
													����ǿ��ģʽ��
												</td>
												<td width="20%">
													${loserModeMap[entity.loserMode] }
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ӯ���ּ�����4��
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.profitLvl4 }' /><span>%</span>
												</td>
												<td align="right" width="30%">
													�Ƿ�Գ壺
												</td>
												<td width="20%">
													${selfCounteractMap[entity.selfCounteract] }
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ӯ���ּ�����5��
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.profitLvl5 }' /><span>%</span>
												</td>
												<td align="right" width="30%">
													���������
												</td>
												<td width="20%">
													<fmt:formatNumber value='${entity.lossRate }' /><span>%</span>
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													�ܼ���ǿ��������
												</td>
												<td width="20%">
													${deductedQty}
												</td>
												<td align="right" width="30%">
													�ܼ��ѶԳ�������
												</td>
												<td width="20%">
													${counteractedQty }
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													ǿ��״̬��
												</td>
												<td width="20%">
													${deductStatusMap[entity.deductStatus]}
												</td>
												<td align="right" width="30%">
													ִ��ʱ�䣺
												</td>
												<td width="20%">
													${entity.execTime }
												</td>
											</tr>
											<tr height="30">
												<td align="right" width="30%">
													���棺
												</td>
												<td width="20%">
													${entity.alert}
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td align="right">
						<button class="btn_sec" onClick=window.close();>
							�ر�
						</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>	
			</table>
		</div>
		</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>
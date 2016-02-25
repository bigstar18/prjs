<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body>
		<form name="frm" method="post" action="">
			<fieldset width="100%">
				<legend>
					��ͬ������Ϣ
				</legend>
				<span>
					<input type="hidden" name="orderField" value="${orderFiled}">
					<input type="hidden" name="orderDesc" value="${orderType}">
					<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
					<input type="hidden" id="pageNo" name="pageNo">
					<input type="hidden" name="quantity" id="quantity" value="${quantity }">
					<input type="hidden" name="tradeId" id="tradeId" value=${tradeId }>
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35">
							<td align="right" class="tdstyle">
								��ͬ�ţ�
							</td>
							<td align="left">
								${hisTrade.tradeNo }
							</td>
							<td align="right" class="tdstyle">
								�򷽱�ţ�
							</td>
							<td align="left">
								${hisTrade.firmId_B }
							</td>
							<td align="right" class="tdstyle">
								������ţ�
							</td>
							<td align="left">
								${hisTrade.firmId_S }
							</td>
							<td align="right" class="tdstyle">
								Ʒ�����ƣ�
							</td>
							<td align="left">
								${hisTrade.breedId }
							</td>
						</tr>
						<tr height="35">
							<td align="right" class="tdstyle">
								���ۣ�
							</td>
							<td align="left">
								${hisTrade.price }
							</td>
							<td align="right" class="tdstyle">
								�ɽ�������
							</td>
							<td align="left">
								${hisTrade.quantity }
							</td>
							<td align="right" class="tdstyle">
								�ɽ�ʱ�䣺
							</td>
							<td align="left">
								<fmt:formatDate value="${hisTrade.tradeDate }"
									pattern="M/d/yyyy" />
							</td>
							<td align="right" class="tdstyle">
								����ʱ�䣺
							</td>
							<td align="left">
								<fmt:formatDate value="${hisTrade.clearDate }"
									pattern="M/d/yyyy" />
							</td>
						</tr>
					</table> </span>
			</fieldset>
			<%@ include file="matchSettleTable.jsp"%>
			<table border="0" cellspacing="0" cellpadding="0" width="80%">
				<tr height="35">
					<td align="center">
						<div>
							<button class="lgrbtn" type="button" onclick="matchSettle();">
								��Դ���
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="lgrbtn" type="button" onclick="back()">
								����
							</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function checkNumber(weight,frozenWeight,regStockID){
	var initWeight=document.getElementById(regStockID).value;
		if(isNaN(initWeight)){
			alert("��������ӦΪ����");
			document.getElementById(regStockID).value="";
		}else {
		   if(parseFloat(initWeight)>accSub(weight,frozenWeight)){
			alert("����������ڲֵ�ʣ�����������������룡");
			document.getElementById(regStockID).value="";
			}
		}
	}
	function matchSettle(){
		frm.action="<%=basePath%>bargainManagerController.zcjs?funcflg=match";
		match(frm,tableList,'delCheck');
	}
	
	function match(frm,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
		{
			var collCheck = tableList.children[1].all.namedItem(checkName);
			for(var i=0;i < collCheck.length;i++ )
			{
				if( collCheck[i].checked == true )
				{
					//alert(""+collCheck[i].value+"");
					var ipv = document.getElementById(collCheck[i].value);
					if(ipv.value == null || ipv.value == ""){
						alert("�����������Ϊ�գ�");
						ipv.focus();
						return false;
					}			
				}
			}
			frm.submit();
		}
		else
		{
		return false;
		}
	}
	function back(tradeid){
		frm.action = "<%=basePath%>bargainManagerController.zcjs?funcflg=getDeliveryList";
		frm.submit();
	}
	//add by yangpei ���js�и���������޷�ȡ��ȷֵ������
	function accSub(arg1,arg2){
	���� var r1,r2,m,n;
	���� try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	���� try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	���� m=Math.pow(10,Math.max(r1,r2));
	���� //last modify by deeka
	���� //��̬���ƾ��ȳ���
	���� n=(r1>=r2)?r1:r2;
	���� return ((arg1*m-arg2*m)/m).toFixed(n);
	}


</script>
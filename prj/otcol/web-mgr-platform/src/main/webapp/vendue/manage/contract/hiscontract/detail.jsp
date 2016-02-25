<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>


<%!


	
public String disDouble2(double f)
{
	String result = "0.00";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.00");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disDouble4(double f)
{
	String result = "0.0000";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.0000");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disDoubleSpe(double f)
{
	if(f < 0) f = 0;
	String result = "0.00";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.00");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disTime(Timestamp ts)
{
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(ts);
	}
	catch(Exception e)
	{
	}	
	return "";

}

public String disDate1(Timestamp ts)
{
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		return sdf.format(ts);
	}
	catch(Exception e)
	{
	}	
	return "";

}

/*
*���ݺ�ͬ��ҵ�����ͳ����ˮ���
*/
public double getMoney(int operation,long contractid,String userid)
{
	System.out.println("=========================================================================");
	System.out.println("������ "+userid +" ��Ŀ�� "+operation);
	double money = 0;
	try
	{
		DeliveryAction delivery = (DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();		
		MoneyVO[] mvos = delivery.getMoneyList("where operation="+operation+" and contractno="+contractid+" and FirmID='"+userid+"'");
		if(mvos != null)
		{
			  for(int i=0;i<mvos.length;i++)
			  {
				  money += mvos[i].money;
				  System.out.println("mvos["+i+"]  is "+mvos[i].iD+"   ��Ŀ��Ϊ  "+mvos[i].operation+"   ��ͬ��Ϊ "+mvos[i].contractNo+"   ���Ϊ  "+mvos[i].money);
			  }
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	System.out.println();
	System.out.println();
	return money;
}
%>


<%
//��ú�ͬ��
long contractid = Long.parseLong(request.getParameter("contractID"));

//������λ
String str6 = delNull(request.getParameter("str6"));

//��ý��չ������
DeliveryAction delivery = (DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();

//ȡ����ʷ��ͬ����
BargainVO bvo = delivery.getHisBargain(contractid);

System.out.println(">>>>>>>>>>>>>>>>>"+bvo.actualAmount);

//ȡ����Ʒ����
CommodityVO cvo = delivery.getCommodity(bvo.commodityID);

//ȡ�ú�ͬ���ѽɻ����ܶ�
double totalPayForGoods = delivery.getPaidMoneyForContract(contractid);
System.out.println("totalPayForGoods is "+totalPayForGoods);

//ȡ���򷽴���
String buyerid = delivery.getBuyerID(contractid);

//ȡ����������
String sellerid = delivery.getSellerID(contractid);

//ȡ���򷽽����̶���
TradeUserVO tvo = delivery.getTradeUser(buyerid);
%>
<%
try
{
	String pd = TradeDAOFactory.getDAO().getPD();
%>

<html>
  <head>
	<title class="Noprint">��ͬ��ϸ</title>
</head>

<body>
<fieldset width="100%">
	<legend>��ͬ��ϸ</legend>
	<BR>
	<span>		
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			   <tr height="25">
					<td align="right" width=15%> ����ģʽ ��</td>
					<td align="left" width=15%>&nbsp;<%
					if(cvo.tradeMode == 0)
					{
						out.println("����");
					}
					else if(cvo.tradeMode == 1)
					{
						out.println("����");
					}
					else if(cvo.tradeMode == 2)
					{
						out.println("�б�");
					}
					%>
					</td>
					<td align="right" width=15%> ��ͬ״̬ ��</td>
					<td align="left" width=15%>&nbsp;<%
					if(bvo.status == 0)
					{
						out.println("��Լ��");
					}
					else if(bvo.status == 1)
					{
						out.println("������");
					}
					else if(bvo.status == 2)
					{
						out.println("�鵵");
					}
					%>
					</td>
					<td align="right" width=15%> ִ�н�� ��</td>
					<td align="left" width=15%>&nbsp;<%
					if(bvo.result == 0)
					{
						out.println("������Լ");
					}
					else if(bvo.result == 1)
					{
						out.println("��ΥԼ");
					}
					else if(bvo.result == 2)
					{
						out.println("����ΥԼ");
					}
					%>
					</td>
			   </tr>  
			   <tr height="25">
					<td align="right"> ��ͬ�� ��</td>
					<td align="left" >&nbsp;<%=contractid%></td>
					<td align="right"> ��ĺ� ��</td>
					<td align="left" >&nbsp;<%=bvo.code%></td>
					<td align="right">&nbsp;</td>
					<td align="left" >&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> �򷽴��� ��</td>
					<td align="left" >&nbsp;<%=buyerid%></td>
					<td align="right"> �������� ��</td>
					<td align="left" >&nbsp;<%=sellerid%></td>
					<td align="right">&nbsp;</td>
					<td align="left" >&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> �ɽ��� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.price)%></td>
					<td align="right"> �ɽ�����(<%=str6%>) ��</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount*cvo.tradeUnit)%></td>
					<!--<%if(pd.equals("true")){%>
					<td align="right"> �ɽ�����(<%=DW%>) ��</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount)%></td>
					<%}else{%>
					<td align="right"> �ɽ�����(<%=DWS%>) ��</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount*cvo.tradeUnit)%></td>
					<%}%>-->
					<td align="right"> �ɽ���� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount)%></td>
			   </tr>
			   <tr height="25">
					<td align="right" colspan=6>&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> ռ�ñ�֤�� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_bail)%></td>
					<td align="right"> �ѽ������� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_poundage)%></td>
					<td align="right"> ʣ�ౣ֤�� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_lastBail)%></td>
			   </tr>			   
			   <tr height="25">
					<td align="right"> Ӧ���ܶ� ��</td>
					<td align="right" >&nbsp;
					<%
					double totalMoney = 0;
					if(bvo.status == 0)//��Լ��
					{
						totalMoney = Arith.format(Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount + bvo.b_poundage,2);
					}
					else//��ͬ״̬ȷ��
					{
						if(bvo.result == 0)//������Լ
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage,2);
						}
						else if(bvo.result == 1)//��ΥԼ
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage + Arith.div(Arith.mul(bvo.b_bail,bvo.fellBackAmount),bvo.amount*cvo.tradeUnit),2);
						}
						else if(bvo.result == 2)//����ΥԼ
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage - Arith.div(Arith.mul(bvo.s_bail,bvo.fellBackAmount),bvo.amount*cvo.tradeUnit),2);
						}
					}
					out.println(disDouble2(totalMoney));
					%>
					</td>
					<td align="right"> �ѽɻ��� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(totalPayForGoods)%></td>
					<td align="right"> �ѽ��ܶ� ��</td>
					<td align="right" >&nbsp;
					<%
					double totalPaidMoney = 0;
					if(bvo.status == 0)
					{
						totalPaidMoney = bvo.b_bail+bvo.b_poundage+totalPayForGoods;
					}
					else
					{
						totalPaidMoney = totalMoney;						
					}
					out.println(disDouble2(totalPaidMoney));
					%>
					</td>
			   </tr>
			   <tr height="25">
					<td align="right"> ��Ӧ�ɿ� ��</td>
					<td align="right" >&nbsp;<%=disDouble2(totalMoney-totalPaidMoney)%></td>
					<td align="right"> <!--��Ԥ�ջ������ ��--></td>
					<td align="right" >&nbsp;<font color=blue><!--<%=disDouble2(tvo.paymentForGoods)%>--></font></td>
					<td colspan=2>&nbsp;</td>
			   </tr>
		</table>	
	<BR>
	</span>
</fieldset>
<fieldset width="100%">
	<legend>������ϸ</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����¼</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="35%">����</td>
	  <td align="right" width="20%">���</td>
	  <td align="center" width="20%">��ע</td>
	  <td align="right" width="45%">��Ӧ�������(<%=str6%>)</td>
	  <!--<%if(pd.equals("true")){%>
	  <td align="right" width="45%">��Ӧ�������(<%=DW%>)</td>
	  <%}else{%>
	  <td align="right" width="45%">��Ӧ�������(<%=DWS%>)</td>
	  <%}%>-->
	  </tr>
	  <%
	  //ʵ����һ���
	  double inPayMoney = 0;

	  //���������
	  double totalAmount = 0;

	  double price_tem = 0d;

	  MoneyVO[] mvos = delivery.getMoneyList("where operation=406 and note is null and contractno="+contractid+" order by id");
	  //MoneyVO[] mvos_tem = delivery.getMoneyList("where operation=504 and note is null and contractno="+contractid+" order by id");
	  if(mvos != null)
	  {
		  for(int i=0;i<mvos.length;i++)
		  {
			  inPayMoney += mvos[i].money;	
			  if(pd.equals("true"))
			  {
				price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)   // ��֤����ڵ��ڻ���  xieying
					totalAmount +=Arith.div(mvos[i].money,bvo.price);
				else
					totalAmount += Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit))/cvo.tradeUnit;
			  }
			  else
			  {
				price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount);
				if(price_tem<=0)  // ��֤����ڵ��ڻ���  xieying
					totalAmount +=Arith.div(mvos[i].money,bvo.price);
				else
					totalAmount += Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount));
			  }
			  %>
				  <tr height="25">
				<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
				<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
				<td align="center">&nbsp;<%=delNull(mvos[i].note)%></td>
				<%if(pd.equals("true")){%>
				<td align="right">&nbsp;
				<%
					price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)  // ��֤����ڵ��ڻ���  xieying
				 {
				%>
				<%=disDouble4(Arith.div(mvos[i].money,bvo.price))%>
				<%}else{%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit))/cvo.tradeUnit)%></td>
			   <%}%>
			  <%}else{%>
			    <td align="right">&nbsp;
				<%
					price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)  // ��֤����ڵ��ڻ���  xieying
				{
				%>
				<%=disDouble4(Arith.div(mvos[i].money,bvo.price))%>
				<%}else{%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit)))%></td>
			    <%}}%>

			  </tr>
			  <%			  	
		  }
	  }

	  mvos = delivery.getMoneyList("where operation=406 and note='bail' and contractno="+contractid+" order by id");
	  if(mvos != null)
	  {
		  double fellAmount = 0;
		  if(bvo.result == 1) fellAmount = bvo.fellBackAmount;
		  for(int i=0;i<mvos.length;i++)
		  {
			  inPayMoney += mvos[i].money;			  			  
			  System.out.println("bvo.amount is "+bvo.amount+" totalAmount is "+totalAmount);
			  %>
				  <tr height="25">
				<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
				<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
				<td align="center">&nbsp;��֤��ת����</td>
				<td align="right">&nbsp;
			<%if(price_tem<=0)  // ��֤����ڵ��ڻ���  xieying
			{%>	
			 <%if(pd.equals("true")){%> 
              <%=disDouble4(Arith.div(Arith.mul(bvo.b_bail,cvo.tradeUnit*(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount))%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price)/cvo.tradeUnit)%>
			 <%}else{%>
			  <%=disDouble4(Arith.div(Arith.mul(bvo.b_bail,cvo.tradeUnit*(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount))%>
			 <%}%>
			 <%}else{}%>
			  </td>
			  </tr>
			  <%
				  if(pd.equals("true"))
				  {
					 //totalAmount+=Arith.div(Arith.mul(bvo.b_bail,(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,bvo.amount));
				  }
				  else
			      {
					  //totalAmount+=Arith.div(Arith.mul(bvo.b_bail,bvo.amount-totalAmount-fellAmount),Arith.mul(bvo.price,bvo.amount));
				  }
		  }
	  }

	  %>				
	</table>
	<BR>
	</span>	
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˿��¼</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="35%">����</td>
	  <td align="right" width="20%">���</td>
	  <td align="center" width="20%">��ע</td>
	  <td align="right" width="45%">��Ӧ�������(<%=str6%>)</td>	
	 <!--<%if(pd.equals("true")){%>
	  <td align="right" width="45%">��Ӧ�������(<%=DW%>)</td>
	  <%}else{%>
	  <td align="right" width="45%">��Ӧ�������(<%=DWS%>)</td>
	  <%}%>-->
	  </tr>
	  <%
	  //Ӧ����һ���
	  double returnPayMoney = 0;
	  mvos = delivery.getMoneyList("where operation=409 and contractno="+contractid+" order by id");
	  System.out.println("----------------------- totalAmount = "+totalAmount);
	  if(mvos != null)
	  {
		  for(int i=0;i<mvos.length;i++)
		  {
			  returnPayMoney += mvos[i].money;
			  //System.out.println("totalAmount is "+totalAmount);
			//totalAmount -= Arith.div(mvos[i].money,bvo.price);
			  /*if(pd.equals("true"))
				totalAmount -= Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount))/cvo.tradeUnit;
			  else
				totalAmount -= Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount));*/
			  %>
				  <tr height="25">
					<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
					<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
					<td align="center">&nbsp;<%=delNull(mvos[i].note)%></td>
					<!--<%if(pd.equals("true")){%>
					<td align="right">&nbsp;<%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount))/cvo.tradeUnit)%></td>
					<%}else{%>
					<td align="right">&nbsp;<%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount)))%></td>
					<%}%>-->
					<td align="right">&nbsp;<%=disDouble4(totalAmount-bvo.actualAmount)%></td>
				  </tr> 
			  <%
		  }
	  }
	  %>	
	</table>
	<BR>
	</span>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ܼ�</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="">�ܻ��� ��</td>
	  <td align="center" width="">&nbsp;<%=disDouble2(totalPayForGoods)%></td>
	  <td align="center" width="">������� ��</td>
	  <!--<td align="center" width="">&nbsp;<%=disDouble4(totalAmount)%></td>-->
	  <td align="center" width="">&nbsp;<%=disDouble4(bvo.actualAmount)%></td>
	  </tr>
	</table>
	<BR>
	</span>
</fieldset>
<%
if(bvo.status > 0)
{
	%>
<fieldset width="100%">
	<legend>�������</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <%if(pd.equals("true")){%>
	  <td align="center" width="">ʵ�ʽ������� ��</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.actualAmount/cvo.tradeUnit)%></td>
	  <td align="center" width="">ΥԼ���� ��</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.fellBackAmount/cvo.tradeUnit)%></td>
	  <td align="center" width="">������� ��</td>
	  <td align="left" width="">&nbsp;
	  <%=disDouble4((bvo.amount*cvo.tradeUnit-bvo.actualAmount-bvo.fellBackAmount)/cvo.tradeUnit)%>
		  </td>
	  <%}else{%>
	  <td align="center" width="">ʵ�ʽ������� ��</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.actualAmount)%></td>
	  <td align="center" width="">ΥԼ���� ��</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.fellBackAmount)%></td>
	  <td align="center" width="">������� ��</td>
	  <td align="left" width="">&nbsp;
	  <%=disDouble4(bvo.amount*cvo.tradeUnit-bvo.actualAmount-bvo.fellBackAmount)%>
		  </td>
	  <%}%>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>

<%
		  int result = bvo.result; //��ͬ���  0��������Լ 1����ΥԼ 2������ΥԼ
%>

<fieldset width="100%">
	<legend>����ϸ</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">ʵ���򷽻��� ��+����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(inPayMoney)%><%//if(result==1){out.println(disDouble2(inPayMoney-getMoney(515,contractid,sellerid)));}else{out.println(disDouble2(inPayMoney));}%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ���򷽻��� ��-����</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(returnPayMoney)%>
	  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ���򷽱�֤�� ��+����</td>
	  <td align="right" width="">&nbsp;
	  <%//=disDouble2(getMoney(511,contractid,buyerid))%>
	    <%if(result==1){out.println(disDouble2(getMoney(415,contractid,sellerid)));}else{out.println(0.00);}%>
	  </td>
	  </tr>  
	  <tr height="25">
	  <td align="right" width="">Ӧ���򷽱�֤�� ��-����</td>
	  <td align="right" width="">
		  &nbsp;<%=disDouble2(getMoney(415,contractid,buyerid))%>
		  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ���������� ��+����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ�����ܽ�� ��</td>	
	  <td align="right" width="">&nbsp;
	  <!--<%=disDouble2(inPayMoney-returnPayMoney+getMoney(515,contractid,sellerid)-getMoney(515,contractid,buyerid)+getMoney(503,contractid,buyerid))%>-->
	  <%=disDouble2(inPayMoney-returnPayMoney+getMoney(415,contractid,sellerid)-getMoney(415,contractid,buyerid)+getMoney(403,contractid,buyerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>

<fieldset width="100%">
	<legend>������ϸ</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">ʵ�ջ��� ��+����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(totalPayForGoods)%><%//if(result==2){out.println(disDouble2(totalPayForGoods-getMoney(515,contractid,buyerid)));}else{out.println(disDouble2(totalPayForGoods));}%></td>
	  </tr>
	<tr height="25">
	  <td align="right" width="50%">ʵ��������֤��-����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(404,contractid,sellerid))%></td>
	  </tr>
	<tr height="25">
	  <td align="right" width="">Ӧ��������֤��+����</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(getMoney(407,contractid,sellerid))%>
	  </td>
	  </tr>
	<tr height="25">
	  <td align="right" width="">Ӧ��������֤��</td>
	  <td align="right" width="">&nbsp;
	   <%//=disDouble2(getMoney(511,contractid,buyerid))%>
	  <%if(result==2){out.println(disDouble2(getMoney(415,contractid,buyerid)));}else{out.println(0.00);}%>
	  </td>
	  </tr>
		  <tr height="25">
	  <td align="right" width="">Ӧ��������֤�� ��+����</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(getMoney(415,contractid,sellerid))%>
	  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ�����������ѣ�-����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,sellerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">Ӧ�������ܽ�� ��</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(totalPayForGoods-getMoney(404,contractid,sellerid)+getMoney(407,contractid,sellerid)+getMoney(415,contractid,sellerid)-getMoney(403,contractid,sellerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>
<fieldset width="100%">
	<legend>�г���ϸ</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">�г��������� ��+����</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid)+getMoney(403,contractid,sellerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">�ܼ� ��</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid)+getMoney(403,contractid,sellerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>	
	<%
}
%>

<br>
<table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
  <tr height="25">
	<td width="40%"><div align="center">
	  <input type="button" onclick="window.print();" class="btn" value="��ӡ">&nbsp;&nbsp;
	  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
	</div></td>
  </tr>
</table>
</body>
</html>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>
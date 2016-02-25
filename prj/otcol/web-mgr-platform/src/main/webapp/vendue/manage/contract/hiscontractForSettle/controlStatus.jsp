<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%
		//-------------------------------------
		String rSWeight = request.getParameter("regStockWeight");
		double regStockWeight = Double.parseDouble(rSWeight);
		String contractId = request.getParameter("contractid");
		String firmID_B = request.getParameter("firmID_B");
		String firmID_S = request.getParameter("firmID_S");
		String regstockid = request.getParameter("regstockid");
		String bfbamount = replaceLNull(request.getParameter("bfbAmount"));
		double bfbAmount = Double.parseDouble(bfbamount);
		String sfbamount = replaceLNull(request.getParameter("sfbAmount"));
		double sfbAmount = Double.parseDouble(sfbamount);
		String exauamount = replaceLNull(request.getParameter("exauAmount"));
		double exauAmount = Double.parseDouble(exauamount);
		long contractid = 0;
		if(contractId!=null && !"".equals(contractId.trim()))
		{
			contractid = Long.parseLong(contractId.trim());
		}
		
		//------------connection message-----------------------------	
			
		Connection conn = null;
	  	PreparedStatement ps = null;
	  	ResultSet rs = null;
	  	CallableStatement calStmt=null;
	  	
	  	String code = "";
		double amount = 0;
		String tradeDate = "";
		long commodityid = 0; 
		double showprice = 0.0;
		int condtrademode=0;
		int contractStatus = -1;
				
		try{
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    DataSource ds = (DataSource)envContext.lookup(JNDI);
		    conn = ds.getConnection();
		    
		    	conn.setAutoCommit(false);
		    	
				//------------save weight of broken contracts ----------------------
				if(bfbAmount != 0)
				{
					//fulfil = 2;
					String bsql = "INSERT INTO v_settleMatch VALUES(?,'-0',?,2,0)";
					ps=conn.prepareStatement(bsql);
				    ps.setLong(1,contractid);
				    ps.setDouble(2,bfbAmount);
				    ps.executeUpdate();
					//1:��Լ  2����ΥԼ  3������ΥԼ  4��˫��ΥԼ
				}
				if(sfbAmount != 0)
				{
					//fulfil = 3;
					String ssql = "INSERT INTO v_settleMatch VALUES(?,'-0',?,3,0)";
					ps=conn.prepareStatement(ssql);
					ps.setLong(1,contractid);
				    ps.setDouble(2,sfbAmount);
				    ps.executeUpdate();
					//1:��Լ  2����ΥԼ  3������ΥԼ  4��˫��ΥԼ
				}
				//if(bfbAmount != 0 && bfbAmount != 0)//˫��ΥԼ
		  		//{
		  		//	fulfil = 4;
		  		//}
		  		
				//------------contract message-----------------------------
				String sql1 = " select h.CODE,h.PRICE,h.AMOUNT,h.USERID,to_char(h.TRADEDATE,'yyyy-MM-dd hh24:mi:ss'),h.COMMODITYID,c.userid,c.trademode,h.status from V_HISBARGAIN h,v_commodity c where c.id = h.commodityid and h.CONTRACTID="+contractid;
		
				  ps=conn.prepareStatement(sql1);
				  rs=ps.executeQuery();
				  while(rs.next())
				  {
						code = rs.getString(1);
						showprice = rs.getDouble(2);
						amount = rs.getDouble(3);
						tradeDate = rs.getString(5);
						commodityid = rs.getLong(6);
						condtrademode = rs.getInt(8);
						contractStatus = rs.getInt(9);
						if(condtrademode==0)
						{
							firmID_B = rs.getString(4);
							firmID_S = rs.getString(7);
						}
						else if(condtrademode==1)
						{
							firmID_B = rs.getString(7);
							firmID_S = rs.getString(4);
						}
						
					}
					rs.close();
	     			ps.close();
     		
%>
<html>
	<body>
		<form name="frm" method="post" action="controlStatus.jsp">
			<input type="hidden" name="contractid" value="<%=contractId %>">
			<input type="hidden" name="firmID_B" value="<%=firmID_B %>">
			<input type="hidden" name="firmID_S" value="<%=firmID_S %>">
			<input type="hidden" name="amount" value="<%=amount %>">
			<input type="hidden" name="regStockWeight" value="<%=regStockWeight %>">
			<input type="hidden" name="bfbAmount" value="<%=bfbAmount %>">
			<input type="hidden" name="sfbAmount" value="<%=sfbAmount %>">
			<input type="hidden" name="exauAmount" value="<%=exauAmount %>">
			<input type="hidden" name="opt">
			<input type="hidden" name="bfbA">
			<input type="hidden" name="sfbA">
			<br>
<%

//---------------------------------��ͬ״̬��0-δ���� 1-������ 2-����------------------------------------------
			
     		String optValue = request.getParameter("opt");
     		//------------------���洢------------------------
     		if(optValue!=null && "OK".equals(optValue.trim()))
     		{
			  		String bfbA = replaceLNull(request.getParameter("bfbA"));
			  		String sfbA = replaceLNull(request.getParameter("sfbA"));
			  		
			  		int category=0,trademode=0;
			  		double beginprice=0,price=0,b_lastbail=0,s_lastbail=0;
			  		String userid1 = "",userid2 = "";
			  		String innersql = " select c.category,c.beginprice,c.userid,h.price,h.userid,h.b_lastbail, "+
			  											" h.s_lastbail,c.trademode from v_commodity c,v_hisbargain h "+
			  											" where c.id = h.commodityid and h.contractid = ? ";
			  		ps=conn.prepareStatement(innersql);
			  		ps.setLong(1,contractid);
			  		rs=ps.executeQuery();
					  while(rs.next())
					  {
					  	category=rs.getInt(1);beginprice=rs.getDouble(2);userid1=rs.getString(3);price=rs.getDouble(4);
					  	userid2=rs.getString(5);b_lastbail=rs.getDouble(6);s_lastbail=rs.getDouble(7);trademode=rs.getInt(8);
					  }
					rs.close();
     				ps.close();
     				
     				String buyer = "";
     				String seller = "";
     				
     				if(trademode==0)//����
			  		{				  			
			  			buyer = userid2;
			  			seller = userid1;
			  		}
			  		else if(trademode==1)//����
			  		{
			  			buyer = userid1;
			  			seller = userid2;
			  		}
     				
     			//---------------------��ֵ��Ŷ�Ӧ������-----------------------
     				  List weightList = new ArrayList();
     				  List regstockidList = new ArrayList();
     				  List fulfilList = new ArrayList();
     				  
     				  String sql2 = " select weight,regstockid,status from v_settleMatch where id=? ";
     				  ps=conn.prepareStatement(sql2);
     				  ps.setLong(1,contractid);
					  rs=ps.executeQuery();
					  while(rs.next())
					  {
					  	weightList.add(rs.getDouble(1));
					  	regstockidList.add(rs.getString(2));
					  	fulfilList.add(rs.getInt(3));
					  }
					  rs.close();
     				  ps.close();
     			
     			 //-------------------------�������� ���洢------------------------------------
     			 
			  		String userid = AclCtrl.getLogonID(request);
			  		int mark = 0;
			  		
			   	for(int a=0;a<weightList.size();a++)
			   	{
			   		int fulfil = (Integer)fulfilList.get(a);
			   		System.out.print("�������� ���洢------"+a+"\n");
			  		calStmt=conn.prepareCall("{?=call FN_S_createSettleMatch(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		  			calStmt.registerOutParameter(1, Types.INTEGER);
		  			calStmt.setString(2, "4");
		  			calStmt.setLong(3, category);
		  			calStmt.setDouble(4, ((Double)weightList.get(a)).doubleValue());
		  			calStmt.setInt(5, fulfil);
		  			calStmt.setString(6, null);
				  	calStmt.setString(7, buyer);
				  	calStmt.setDouble(8, price);
				  	calStmt.setDouble(9, b_lastbail);
				  	calStmt.setDouble(10, 0);
				  	calStmt.setString(11, seller);
				  	calStmt.setDouble(12, price);//����˫�����ռ�һ��
				  	calStmt.setDouble(13, s_lastbail);
				  	calStmt.setString(14, fulfil==1?(String)regstockidList.get(a):null);
				  	calStmt.setLong(15, contractid);
				  	calStmt.setString(16, null);
				  	calStmt.setString(17, userid);
				  	calStmt.setString(18, "4");
				  	
			  		//------���ݷ���ֵ�ж��Ƿ������������Ϣ-------
		  			calStmt.execute();
		  			int result = calStmt.getInt(1); 
		  			
		  			if(result<0)
		  			{
		  				conn.rollback();
		  				mark = result;
		  				break;
		  			}
			  	}
						
						String resultMSG = "";
						if(mark==-4)
						{
							resultMSG="�ֵ������������������ֵ�Ʒ�������Ʒ�ֲ���";
						}
						else if(mark==-3)
						{
							resultMSG="������ʵ����������";
						}
						else if(mark==-2)
						{
							resultMSG="�ֵ���Ӧ�������Ϣ���Ϸ�";
						}
						else if(mark==-1)
						{
							resultMSG="��д��Ϣ����";
						}
						else
						{
						//---------------------�޸ĺ�ͬ״̬---------------------------------
							String innersql1 = " update V_HISBARGAIN set status = 2 where CONTRACTID=? ";
			     			ps=conn.prepareStatement(innersql1);
			     			ps.setLong(1,contractid);
						  	ps.executeUpdate();
						  	
						  	innersql1 = " update v_settleMatch set HANDLERESULT = 1 where CONTRACTID=? ";
			     			ps=conn.prepareStatement(innersql1);
			     			ps.setLong(1,contractid);
						  	ps.executeUpdate();
						  	
						  	conn.commit();
						  	ps.close();
							resultMSG="���ͨ���������Ϣ��ӳɹ�";
						}
						%>
						<SCRIPT LANGUAGE="JavaScript">
							alert('<%=resultMSG %>');
							window.location.href="controlStatus.jsp";
						</SCRIPT>
						<%
     			}     		
		}catch(Exception e){
			
				%>
				<SCRIPT LANGUAGE="JavaScript">
					alert("����ʧ��,�����쳣!");
					window.location.href="controlStatus.jsp";
				</SCRIPT>
				<%
     		e.printStackTrace();
 			}finally{
 			conn.setAutoCommit(true);
	 			if(rs!=null){
	 				try{rs.close();}
	 				catch(Exception ex){}
	 				rs=null;
	 			}
     		if(ps!=null){
     		try{ps.close();}
     		catch(Exception ex){}
     		ps=null;
     		}
	 			try{conn.close();}
	 			catch (Exception e){}
        		conn = null;
 		}	
%>
		<fieldset width="100%">
		<legend>��ͬ������Ϣ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
          <td align="right" class="tdstyle">��ͬ�ţ�</td>
          <td align="left"><%=contractId %></td>
          <td align="right" class="tdstyle">��ĺţ�</td>
          <td align="left"><%=code %></td>
          <td align="right" class="tdstyle">�򷽱�ţ�</td>
          <td align="left"><%=firmID_B %></td>
          <td align="right" class="tdstyle"> ������� ��</td>
          <td align="left"><%=firmID_S %></td>
        </tr>
        <tr height="35">
          <td align="right" class="tdstyle">��Ʒ���룺</td>
          <td align="left"><%=commodityid %></td>
          <td align="right" class="tdstyle">���ۣ�</td>
          <td align="left"><%=showprice %></td>
          <td align="right" class="tdstyle">�ɽ�������</td>
          <td align="left"><%=amount %></td>
          <td align="right" class="tdstyle">�ɽ�ʱ�䣺</td>
          <td align="left"><%=tradeDate %></td>
        </tr>
      </table>
      </span>  
		</fieldset>
		<br>
		<fieldset width="100%">
		<legend>��Ի�����Ϣ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
          		<td align="right" class="tdstyle"><font color="red">������������</font></td>
          		<td align="left"><font color="red"><input type="text" name="amount1" value="<%=amount %>" readonly="readonly"></font></td>
        	 </tr>
        	 <tr height="35">
        		<td align="right" class="tdstyle">��Լ������</td>
          		<td align="left"><input type="text" name="regStockWeight1" value="<%=regStockWeight %>" readonly="readonly"></td>
        	 </tr>
			<tr height="35">
	          <td align="right" class="tdstyle">��ΥԼ������</td>
          			<td align="left"><input type="text" value="0.0" id="bfbAmount1" name="bfbAmount1" onblur="changeAmount('bfbAmount1')"></td>
        	</tr>
			<tr height="35">
          		<td align="right" class="tdstyle">����ΥԼ������</td>
          		<td align="left"><input type="text" value="0.0"  id="sfbAmount1" name="sfbAmount1" onblur="changeAmount('sfbAmount1')"></td>
        	</tr>
			<tr height="35">
	          <td align="right" class="tdstyle">���������</td>
	          <td align="left"><input type="text" name="exauAmount1" value="<%=amount-regStockWeight %>"  readonly="readonly"></td>
	        </tr>
	        <tr height="35">
	        	<td align="left" class="tdstyle" colspan="2">
	        		<div align="center">
	        		<c:if test="<%=contractStatus!=2 %>">
					   	<input type="button" name="conStatusBtn" onclick="return controlStatus();" class="btn" value="ȷ��">
					   	&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
					   <input name="back" type="button" onclick="returnParent('<%=contractId %>')" class="btn" value="����">
            		</div>
        		</td>
        	</tr>
      </table>
      </span>  
	</fieldset>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	function checkNum(v)
	{
		if(v=="")
		{
			return parseFloat("0");
		}
		return parseFloat(v);
	}
	function changeAmount(v)
	{
			var Amount = checkNum(frm.amount1.value);
			var rSW = checkNum(frm.regStockWeight1.value);
			var bfbamount = checkNum(frm.bfbAmount1.value);
			var sfbamount = checkNum(frm.sfbAmount1.value);
			//var exauAmount = frm.exauAmount.value;
			var sumAmount = rSW+bfbamount+sfbamount;
			if(sumAmount>Amount)
			{
				alert("�����ܺͳ���ʵ������������������!");
				document.getElementById(v).value="0.0";
				document.getElementById(v).focus();
			}
			else
			{
				var laveamount=Amount-sumAmount;
				frm.exauAmount1.value=laveamount;
			}
	}
	
	function controlStatus()
	{
			var Amount = checkNum(frm.amount1.value);
			var rSW = checkNum(frm.regStockWeight1.value);
			var bfbamount = checkNum(frm.bfbAmount1.value);
			var sfbamount = checkNum(frm.sfbAmount1.value);
			var sumAmount = rSW+bfbamount+sfbamount;
			var laveamount=Amount-sumAmount;
			
			frm.bfbAmount.value=bfbamount;
			frm.sfbAmount.value=sfbamount;
			frm.exauAmount.value=laveamount;
			
			alert("ȷ��ִ�н��?");
			if(bfbamount>0)
			{
				frm.bfbA.value="2";
			}
			if(sfbamount>0)
			{
				frm.sfbA.value="3";
			}
			frm.opt.value="OK";
			frm.submit();	
	}
	function returnParent(v)
	{
		frm.action="settleMatchList.jsp?contractid="+v;
		frm.submit();
	}
</script>
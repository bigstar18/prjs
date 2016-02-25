<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<html>
	<%
		long contractid=0,regstockid=0;
		String contractId = request.getParameter("contractid");
		if(contractId!=null && !"".equals(contractId.trim()))
		{
			contractid = Long.parseLong(contractId.trim());
		}
		String regstockId = request.getParameter("regstockid");
		if(regstockId!=null && !"".equals(regstockId.trim()))
		{
			regstockid = Long.parseLong(regstockId.trim());
		}
		//------------contract message-----------------------------
		String sql1 = " select h.CODE,h.PRICE,h.AMOUNT,h.USERID,to_char(h.TRADEDATE,'yyyy-MM-dd hh24:mi:ss'),h.COMMODITYID,c.userid,c.trademode from V_HISBARGAIN h,v_commodity c where c.id = h.commodityid and h.CONTRACTID="+contractid;
		//out.print(sql1);
		String code = "";
		double price = 0.0;
		long amount = 0;
		String buyer = "";
		String seller = "";
		String tradeDate = "";
		long commodityid = 0; 
		int trademode = -1;
		//------------regstock message-----------------------------
		String sql2 = " select s.unitweight,s.breedid,s.firmid,s.weight,s.frozenweight,s.warehouseid from s_regstock s where s.regstockid='"+regstockid+"'";
		long breedid = 0;
		String firmid = "";
		double weight = 0.0;
		double frozenweight = 0.0;
		double unitweight = 0;
		String warehouseid = "";
		//------------connection message-----------------------------				
		Connection conn = null;
	  	PreparedStatement ps = null;
	  	PreparedStatement psBatch = null;
	  	ResultSet rs = null;
	  
		try{
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    DataSource ds = (DataSource)envContext.lookup(JNDI);
		    conn = ds.getConnection();
		    
		//------------query contract message-----------------------------
		
			  ps=conn.prepareStatement(sql1);
			  rs=ps.executeQuery();
			  while(rs.next())
			  {
					code = rs.getString(1);
					price = rs.getDouble(2);
					amount = rs.getLong(3);
					tradeDate = rs.getString(5);
					commodityid = rs.getLong(6);
					trademode = rs.getInt(8);
					if(trademode==0)
					{
						buyer = rs.getString(4);
						seller = rs.getString(7);
					}
					else if(trademode==1)
					{
						buyer = rs.getString(7);
						seller = rs.getString(4);
					}
				}
				rs.close();
	     		ps.close();	
     	//---------------query restock message----------------------------------
     			//out.print(sql2);
     			ps=conn.prepareStatement(sql2);
			  	rs=ps.executeQuery();
			  	while(rs.next())
			  	{
			  		unitweight = rs.getDouble(1);
			  		breedid = rs.getLong(2);
					firmid = rs.getString(3);
					weight = rs.getDouble(4);
					frozenweight = rs.getDouble(5);
					unitweight = rs.getDouble(1);
					warehouseid = rs.getString(6);
			  	}
			  	rs.close();
	     		ps.close();		
	%>
	
	<body>
		<form name="frm" method="post">
			<br>
		<fieldset width="100%">
			<legend>合同基本信息</legend>
			<span>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
				  <tr height="35">
			          <td align="right" class="tdstyle">合同号：</td>
			          <td align="left"><%=contractId %></td>
			          <td align="right" class="tdstyle">标的号：</td>
			          <td align="left"><%=code %></td>
			          <td align="right" class="tdstyle">买方编号：</td>
			          <td align="left"><%=buyer %></td>
			          <td align="right" class="tdstyle"> 卖方编号 ：</td>
			          <td align="left"><%=seller %></td>
			      </tr>
		          <tr height="35">
		          	  <td align="right" class="tdstyle">商品代码：</td>
			          <td align="left"><%=commodityid %></td>
			          <td align="right" class="tdstyle">单价：</td>
			          <td align="left"><%=price %></td>
			          <td align="right" class="tdstyle">成交数量：</td>
			          <td align="left"><%=amount %></td>
			          <td align="right" class="tdstyle">成交时间：</td>
			          <td align="left"><%=tradeDate %></td>
		        </tr>
      		</table>
      	</span>  
	</fieldset>
		<br>
		<fieldset width="100%">
			<legend>仓单基本信息</legend>
			<span>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
				  <tr height="35">
			          <td align="right" class="tdstyle">仓单号：</td>
			          <td align="left"><%=regstockid %></td>
			          <td align="right" class="tdstyle">交易商编号：</td>
			          <td align="left"><%=firmid %></td>
			          <td align="right" class="tdstyle"> 商品品种编号 ：</td>
			          <td align="left"><%=breedid %></td>
			      </tr>
		          <tr height="35">
		          	<td align="right" class="tdstyle">仓单对应仓库号：</td>
			          <td align="left"><%=warehouseid %></td>
		          	  <td align="right" class="tdstyle">仓单对应数量：</td>
			          <td align="left"><%=weight %></td>
			          <td align="right" class="tdstyle">仓单对应冻结数量：</td>
			          <td align="left"><%=frozenweight %></td>
		        </tr>
      		</table>
      	</span> 
      	<br>
	</fieldset>
		<%
			}catch(Exception e){
		%>
		<SCRIPT LANGUAGE="JavaScript">
			alert("操作异常!");
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
     		if(psBatch!=null){
	     		try{psBatch.close();}
	     		catch(Exception ex){}
	     		psBatch=null;
     		}
	 			try{conn.close();}
	 			catch (Exception e){}
        		conn = null;
 		}	
%>		
		</form>
		<table align="center"><tr><td align="center">
			<input type="button" value="关闭" class="btn" onclick="window.close();">
		</td></tr></table>
	</body>
</html>
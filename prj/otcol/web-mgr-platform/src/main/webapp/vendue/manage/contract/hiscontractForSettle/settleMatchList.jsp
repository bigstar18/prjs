<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<html>
	<%	
		String contractId = request.getParameter("contractid");
		String firmID_B = "";
		String firmID_S = "";
		long contractid = 0;
		int trademode=0;
		double regStockWeight = 0;
		if(contractId!=null && !"".equals(contractId.trim()))
		{
			contractid = Long.parseLong(contractId.trim());
		}
		
		String sql1 = " select h.CODE,h.PRICE,h.AMOUNT,h.USERID,to_char(h.TRADEDATE,'yyyy-MM-dd hh24:mi:ss'),h.COMMODITYID,c.userid,c.trademode from V_HISBARGAIN h,v_commodity c where c.id = h.commodityid and h.CONTRACTID="+contractid;
		//out.print(sql1);
		//------------contract message-----------------------------
		String code = "";
		double price = 0.0;
		long amount = 0;
		String tradeDate = "";
		long commodityid = 0; 
		//------------regstock message-----------------------------
		String regstockid = "";
		double weight = 0.0;
		int handleresult = 0;
		//------------connection message-----------------------------				
		Connection conn = null;
	  	PreparedStatement ps = null;
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
						firmID_B = rs.getString(4);
						firmID_S = rs.getString(7);
					}
					else if(trademode==1)
					{
						firmID_B = rs.getString(7);
						firmID_S = rs.getString(4);
					}
				}
			rs.close();
     		ps.close();	
     		//-----------------------------删除配对操作-------------------------------
			String optValue = request.getParameter("opt");
			if(optValue!=null  &&  "delete".equals(optValue.trim()))
			{
			
			  conn.setAutoCommit(false);
			  
			  ps=conn.prepareStatement(" delete from v_settleMatch where id = '"+contractId+"'");
			  ps.executeUpdate();
			  ps.close();
			  
			  ps=conn.prepareStatement(" update v_hisbargain set status=0 where contractid = '"+contractId+"'");
			  ps.executeUpdate();
			  ps.close();
			  
			  conn.commit();
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
				alert("操作成功!");
				window.location.href="matchSettle.jsp?contractid="+<%=contractId %>;
			  </SCRIPT>				
			  <%
			}
	%>
	
	<body>
		<form name="frm" method="post" action="settleMatchList.jsp">
		<input type="hidden" name="contractid" value="<%=contractId %>">
		<input type="hidden" name="code" value="<%=code %>">
		<input type="hidden" name="firmID_B" value="<%=firmID_B %>">
		<input type="hidden" name="firmID_S" value="<%=firmID_S %>">
		<input type="hidden" name="commodityid" value="<%=commodityid %>">
		<input type="hidden" name="price" value="<%=price %>">
		<input type="hidden" name="amount" value="<%=amount %>">
		<input type="hidden" name="tradeDate" value="<%=tradeDate %>">
		<input type="hidden" name="opt">
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
          <td align="left"><%=firmID_B %></td>
          <td align="right" class="tdstyle"> 卖方编号 ：</td>
          <td align="left"><%=firmID_S %></td>
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
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
	  	<tHead>
	  		<tr height="25"  align=center>
	  			<td class="panel_tHead_LB">&nbsp;</td>
			    <td class="panel_tHead_MB">合同号</td>
			    <td class="panel_tHead_MB">仓单号</td>
			    <td class="panel_tHead_MB">数量</td>
			    <td class="panel_tHead_MB">查看</td>
			    <td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
	<%
		//------------query message-----------------------------
			  
			  String sql = " select regstockid,weight,HANDLERESULT from v_settleMatch where id=? ";
		      ps=conn.prepareStatement(sql);
		      ps.setLong(1,contractid);		    
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
		        String rstid = rs.getString(1);
				double perWeighe = rs.getDouble(2);
				regStockWeight += perWeighe;
				handleresult = rs.getInt(3);
		%>
				<tr height="25"  align=center>
	  			<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine"><%=contractid %></td>
			    <td class="underLine"><%=rstid %></td>
			    <td class="underLine"><%=perWeighe %></td>
			    <td class="underLine"><a href="#" onclick="entryAudit('<%=contractId %>','<%=rstid %>')">查看详情>>></a></td>
			    <td class="panel_tBody_RB">&nbsp;</td>
				</tr>
		<%
				}
				rs.close();
     		    ps.close();
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
		 			try{conn.close();}
		 			catch (Exception e){}
	        		conn = null;
	 		}
	%>
		  </tBody>
		  <tFoot>
			<tr height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4" align="right">
					<input name="settlebtn" type="button" onclick="auditSettleMatch()" class="btn" value="审核配对">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<%
					//System.out.print("```````````````````````handleresult:"+handleresult);
					if(handleresult!=1)
					{
					%>
					<input name="settlebtn" type="button" onclick="cancleSettleMatch()" class="btn" value="废除配对">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<%
					}
					%>
					<input name="back" type="button" onclick="returnParent('<%=contractId %>')" class="btn" value="返回">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="25">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<input type="hidden" name="regStockWeight" value="<%=regStockWeight %>">
	<input type="hidden" name="contractid" value="<%=contractId %>">
		</form>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

function cancleSettleMatch()
{
	confirm("确定废除该合同的配对信息?")
	{
		frm.opt.value="delete";
		frm.submit();
	}
}
function entryAudit(v1,v2)
{
	var v="viewMsg.jsp?contractid="+v1+"&regstockid="+v2;
	window.showModalDialog(v,"", 
				"dialogWidth=700px; dialogHeight=400px; status=no;scroll=yes;help=no;");
}
function auditSettleMatch()
{
	frm.action="controlStatus.jsp";
	frm.submit();
}
function returnParent(v)
{
	frm.action="matchSettle.jsp?contractid="+v;
	frm.submit();
}
</SCRIPT>
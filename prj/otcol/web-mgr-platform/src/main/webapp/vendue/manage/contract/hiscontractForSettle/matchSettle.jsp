<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<html>
	<%
		String contractId = request.getParameter("contractid");
		String firmID_B = "";
		String firmID_S = "";
		long contractid = 0;
		int trademode=0;
		int matchNum=0;
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
		long breedid = 0;
		String name = "";
		String firmid = "";
		double weight = 0.0;
		double frozenweight = 0.0;
		String enterdate = "";
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
     		
     		//--------------配对操作----------------------------------
     		String optValue = request.getParameter("opt");
     		if( optValue!=null && "settle".equals(optValue.trim()))
     		{
     			String[] regstockids = request.getParameterValues("ck");
				int regstockidsLen = 0;
				if(regstockids!=null)
				{
					regstockidsLen = regstockids.length;
				}
				long sumAmount = 0;
				conn.setAutoCommit(false);
				
				ps=conn.prepareStatement(" update v_hisbargain set status=1 where contractid = '"+contractId+"'");
			  ps.executeUpdate();
			  ps.close();
				
				String sql = "INSERT INTO v_settleMatch VALUES(?,?,?,1,0)";
				//contractid,regstockid,regStovkAmount
				//1:履约  2：买方违约  3：卖方违约  4：双方违约
				psBatch=conn.prepareStatement(sql);
				for(int no=0;no<regstockidsLen;no++)
				{
					String rsw = request.getParameter("ck"+regstockids[no]);
					long regStockWeight = 0;
					if(rsw!=null && !"".equals(rsw.trim()))
					{
						regStockWeight = Long.parseLong(rsw);
					}
					sumAmount += regStockWeight;
				    psBatch.setLong(1,contractid);
				    psBatch.setString(2,regstockids[no]);
				    psBatch.setDouble(3,regStockWeight);
				    psBatch.addBatch();
				 }
				 
				 //检验填写数量总和是否超过合同总数量
				 if(sumAmount>amount)
				 {
				 %>
				 <SCRIPT LANGUAGE="JavaScript">
					 alert("填写数量总和超过合同总数量,请重新填写!");
				 </SCRIPT>
				 <%
				 }
				 else
				 {
					 int[] updateCounts = psBatch.executeBatch();
					 conn.commit();
				 %>
				 <SCRIPT LANGUAGE="JavaScript">
					 alert("配对成功,"+<%=updateCounts.length %>+"条数据已被操作!");
					 window.location.href="settleMatchList.jsp?contractid="+<%=contractid %>;
				 </SCRIPT>
				 <%
				 }
				 psBatch.close();
     		}
	%>
	
	<body>
		<form name="frm" method="post" action="matchSettle.jsp">
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
				<td class="panel_tHead_MB" align=left>
				<input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB">仓单号</td>
			    <td class="panel_tHead_MB">商品品种代码</td>
			    <td class="panel_tHead_MB">商品名称</td>
			    <td class="panel_tHead_MB">卖方交易商代码</td>
				<td class="panel_tHead_MB">该仓单交收数量</td>
			    <td class="panel_tHead_MB">冻结数量</td>
			    <td class="panel_tHead_MB">拟入库时间</td>
			    <td class="panel_tHead_MB">详情</td>
	<%
		//------------query regstock message-----------------------------
			  String sql3 = " select count(*) from v_settleMatch where id=?";

			  ps=conn.prepareStatement(sql3);
			  ps.setLong(1,contractid);
			  rs=ps.executeQuery();
			  while(rs.next())
			  {
			  	matchNum = rs.getInt(1);
			  }
			  rs.close();
     		  ps.close();	    
	%>
			    <c:if test="<%=matchNum==0 %>">
			    	<td class="panel_tHead_MB">配对数量</td>
			    </c:if>
			    <td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody valign="top" height="300">
	<%
		//------------query regstock message-----------------------------
			  String sql2 = " select a.regstockid,a.breedid,c.name,a.firmid,a.weight,a.frozenweight,a.unitweight,"+
									" to_char(b.enterdate,'yyyy-MM-dd hh24:mi:ss') from s_regstock a , w_enter_ware b ,w_commodity c "+
									" where a.firmid='"+firmID_S+"' and a.stockid=b.id and c.id = a.breedid||'' ";
			  //out.print(sql2);
			  ps=conn.prepareStatement(sql2);
			  rs=ps.executeQuery();
			  while(rs.next())
			  {
			  		regstockid = rs.getString(1);
		      		breedid = rs.getLong(2);
					name = rs.getString(3);
					firmid = rs.getString(4);
					weight = rs.getDouble(5);
					frozenweight = rs.getDouble(6);
					enterdate = rs.getString(8);
		%>
				<tr height="25"  align=center>
	  			<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align=left><input name="ck" type="checkbox" value='<%=regstockid %>'></td>
			    <td class="underLine"><%=regstockid %></td>
			    <td class="underLine"><%=breedid %></td>
			    <td class="underLine"><%=name %></td>
			    <td class="underLine"><%=firmid %></td>
			    <td class="underLine"><%=weight %></td>
			    <td class="underLine"><%=frozenweight %></td>
			    <td class="underLine"><%=enterdate %></td>
			    <td class="underLine"><a href="#" onclick="regStockView('<%=regstockid %>')">查看详情>>></a></td>
				<td class="underLine">
					<c:if test="<%=matchNum==0 %>">
						<input id="<%="ck"+regstockid %>" name="<%="ck"+regstockid %>" type="text" onblur="checkAmount(<%=weight %>,<%=regstockid %>)">
					</c:if>
				</td>
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
		  </tBody>
		  <tFoot>
			<tr height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="panel_tFoot_MB"  colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10" align="right">
					<c:if test="<%=matchNum==0 %>">
						<input name="settlebtn" type="button" onclick="matchSettle('<%=contractId %>')" class="btn" value="配对处理">
					</c:if>
					<c:if test="<%=matchNum>0 %>">
						<input name="settlebtn" type="button" onclick="matchSettleList('<%=contractId %>')" class="btn" value="配对信息">
					</c:if>
					<input name="back" type="button" onclick="returnParent('<%=contractId %>')" class="btn" value="返回">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="25">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>	
		</form>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function checkAmount(v1,v2)
{
		var str = "ck"+v2;
		var weight = document.getElementById(str).value;
		if(weight>v1)
		{
			alert("填写数量超过仓单["+v2+"]的数量,请重新填写!");
			document.getElementById(str).value="";
			document.getElementById(str).focus();
		}
}
function regStockView(v)
{
	window.showModalDialog("<%=request.getContextPath()%>/settle/servlet/regStockView.wha?flag=4&requestId="+v,"", "dialogWidth=800px; dialogHeight=500px; status=yes;scroll=yes;help=no;");
}
function matchSettle(v)
{
	var cks = document.getElementsByName("ck");
	var no = 0;
	for( var a=0;a<cks.length;a++)
	{
		if(cks[a].checked)
		{
				no ++;
		}
	}
	if(no>0)
	{
		frm.opt.value="settle";
		frm.submit();
	}
	else
	{
		alert("没有可操作数据!");
	}
}
function matchSettleList(v)
{
	window.location.href="settleMatchList.jsp?contractid="+v;
}
function returnParent(v)
{
	window.location.href="hisConList.jsp";
}
</SCRIPT>
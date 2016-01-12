<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<%@ include file="/mgr/app/timebargain/printReport/excelExpFile.jsp"%>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:m="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
</head>
<body>
<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String cleardate = request.getParameter("cleardate");
	String brokerId = request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	
	String filter = " ";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null")){
			filter += " and t.firmid>='"+startFirmID +"'";
			}
		else{
			startFirmID=null;
		}
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null")){
			filter += " and t.firmid<='"+endFirmID +"'";
		}
		else{
			endFirmID=null;
		}
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null")){
			filter+=" and t.firmid in (select firmId from BR_FirmAndBroker where brokerId ='"+brokerId+"')";
		}
		else{
			brokerId = null;
		}
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null")){
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id="+cateGoryId+")) ";
			
		}else{
			cateGoryId = null;
		}
	}
	String sql = "";
	String cleardateFormat="to_date('"+cleardate+"','yyyy-MM-dd')";
	Map map=ClientLedger.queryClientLedgerTotal(cleardate,cleardate,startFirmID,endFirmID,"15",cateGoryId,brokerId);
	if(map!=null)
	{
	   List list=ClientLedger.queryFiledMap("15");
	   list.remove(list.size()-1);
	   list.remove(list.size()-1);
	   list.remove(0);
	   List filedList=(List)map.get("filed");
	   List valueList=(List)map.get("value");
	   pageContext.setAttribute("filed", list);
	   %>
	   
	<br><br>			
	<table align="center" width="800px" border="0">
	<tr><td colspan="17" class="reportHead">资金日报表</td></tr>
	<tr>
		<td class="reportRight" colspan="17">日期:<%=cleardate%></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="2500px">
	<tr>
	<%
	if(list!=null&&list.size()>0)
	{
	int length=list.size();
	for(int i=0;i<list.size();i++)
	{
	  Map m=(Map)list.get(i);
	  String name=(String)m.get("name");
	  %>
	    <td class="td_reportMdHead">
	  <%=name%></td>
	  <%
	}
	%>
	<td class="td_reportMdHead">+本日保证金</td>
	<td class="td_reportMdHead">+本日浮亏</td>
	<td class="td_reportMdHead">+本日交收保证金</td>
	<td class="td_reportMdHead">+浮动盈亏</td>
	<td class="td_reportMdHead">本日权益</td>
	<td class="td_reportMdHead">上日保证金</td>
	<td class="td_reportMdHead">上日浮亏</td>
	<td class="td_reportRdHead">上日交收保证金</td>
	<%
	}
	%>
	
	</tr>	
	<%
	          double ClearAssureAll=0;
			  double ClearFLAll=0;
			  double ClearSettleMarginAll=0;
			  double RuntimeMarginAll=0;
			  double RuntimeFLAll=0;
			  double RuntimeSettleMarginAll=0;
			  double MinClearDepositAll=0;
			  double quanAll=0;
			  double floatinglossAll=0;
	          List totalList=new ArrayList();
	  		  if(valueList!=null&&valueList.size()>0&&filedList!=null&&filedList.size()>0)
	  		  {
	  		        String firmId="";
	  		        double lastBalance=0;
		  		    for(int i=0;i<valueList.size();i++)
		  		    {
		  		       Map valueMap=(Map)valueList.get(i);
		  		       if(valueMap!=null)
		  		       {
		  		       %>
		  		       <tr onclick="selectTr()">
		  		       <%
		  		         int length=filedList.size()-2;
		  		         for(int ii=1;ii<filedList.size()-2;ii++)
		  		         {
		  		           String field=(String)filedList.get(ii);
		  		           if(ii<2)
		  		           {
		  		             Object value=valueMap.get(field);
		  		             firmId=value.toString();
		  		             %>
		  		             <td class="td_reportMd" ><%=value.toString()%></td>
		  		             <% 
		  		           }
		  		           else
		  		           {
		  		             Object value=valueMap.get(field);
		  		             double v=0;
		  		             boolean sign1=false;
		  		             if(totalList.size()>ii-2)
		  		             {
		  		               v=Double.parseDouble((String)totalList.get(ii-2));
		  		               sign1=true;
		  		             }
		  		              v+=((BigDecimal)value).doubleValue();
		  		              if(sign1)
		  		              {
		  		              totalList.set(ii-2,v+"");
		  		              }
		  		              else
		  		              {
		  		               totalList.add(v+"");
		  		              }
		  		             
		  		              %>
		  		              <td class="td_reportMd">
		  		              <%
		  		              lastBalance=Double.parseDouble(value.toString());
		  		             %>
		                   <fmt:formatNumber value="<%=value.toString()%>" pattern="#,##0.00"/></td>
		  		             <%
		  		           }
		  		         }
		  		         
		  		       }
		  		       sql="select ClearMargin-ClearAssure ClearAssure,ClearFL,ClearSettleMargin,RuntimeMargin-RuntimeAssure RuntimeMargin,"+
                    "RuntimeFL,RuntimeSettleMargin,MinClearDeposit from t_h_firm t where firmId='"+firmId+"' "+filter;
                    List thFirmList=dao.queryBySQL(sql);   
                       Object ClearAssure="0";
				       Object ClearFL="0";
				       Object ClearSettleMargin="0";
				       Object RuntimeMargin="0";
				       Object RuntimeFL="0";
				       Object RuntimeSettleMargin="0";
				       Object MinClearDeposit="0";
                      if(thFirmList!=null&&thFirmList.size()>0)
                      {
				          Map m=(Map)thFirmList.get(0);
				          ClearAssure=m.get("ClearAssure");
				          ClearFL=m.get("ClearFL");
				          ClearSettleMargin=m.get("ClearSettleMargin");
				          RuntimeMargin=m.get("RuntimeMargin");
				          RuntimeFL=m.get("RuntimeFL");
				          RuntimeSettleMargin=m.get("RuntimeSettleMargin");
				          MinClearDeposit=m.get("MinClearDeposit");
                     }
                     sql="select (nvl(sum(t.floatingloss),0)) floatingloss  from t_h_firmholdsum t where firmId='"+firmId+"' "+filter;
                     List listFloatingloss=dao.queryBySQL(sql);
                        Object floatingloss="0";
                        if(listFloatingloss!=null&&listFloatingloss.size()>0)
                      {
                          Map m=(Map)listFloatingloss.get(0);
                          floatingloss=m.get("floatingloss");
                      }
                      double quan=lastBalance+Double.parseDouble(RuntimeMargin.toString())+Double.parseDouble(RuntimeFL.toString())
                                  +Double.parseDouble(RuntimeSettleMargin.toString())+Double.parseDouble(floatingloss.toString());
                     ClearAssureAll+=Double.parseDouble(ClearAssure.toString());
			         ClearFLAll+=Double.parseDouble(ClearFL.toString());
			         ClearSettleMarginAll+=Double.parseDouble(ClearSettleMargin.toString());
			         RuntimeMarginAll+=Double.parseDouble(RuntimeMargin.toString());
			         RuntimeFLAll+=Double.parseDouble(RuntimeFL.toString());
			         RuntimeSettleMarginAll+=Double.parseDouble(RuntimeSettleMargin.toString());
			         MinClearDepositAll+=Double.parseDouble(RuntimeSettleMargin.toString());
			         quanAll+=Double.parseDouble(quan+"");
			         floatinglossAll+=Double.parseDouble(floatingloss.toString());
                      %>
                      <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=RuntimeMargin%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=RuntimeFL%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=RuntimeSettleMargin%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=floatingloss%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=quan%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=ClearAssure%>" pattern="#,##0.00"/></td>
			          <td class="td_reportMd1">&nbsp;
			          <fmt:formatNumber value="<%=ClearFL%>" pattern="#,##0.00"/></td>
			          <td class="td_reportRd1">&nbsp;
			          <fmt:formatNumber value="<%=ClearSettleMargin%>" pattern="#,##0.00"/></td>
                      <% 
		  		       %>
	  		             </tr>
		  		         <%
		  		    }
		  		    
	  		  }
	  		 %>
	<tr onclick="selectTr()">
	
	<%
	   int length=totalList.size();
	   if(length>0)
	   {
	   %>
	   <td class="td_reportMd"><b>合计</b></td>
	   <%
	   for(int i=0;i<totalList.size();i++)
	   {
	      String value=(String)totalList.get(i);
	      %>
	      <td class="td_reportMd">
	      <b><fmt:formatNumber value="<%=value%>" pattern="#,##0.00"/></b></td>
	      <%
	   }
	   %>
	   <td class="td_reportMd1">&nbsp;
	   <fmt:formatNumber value="<%=RuntimeMarginAll%>" pattern="#,##0.00"/></td>
       <td class="td_reportMd1">&nbsp;
       <fmt:formatNumber value="<%=RuntimeFLAll%>" pattern="#,##0.00"/></td>
       <td class="td_reportMd1">&nbsp;
       <fmt:formatNumber value="<%=RuntimeSettleMarginAll%>" pattern="#,##0.00"/></td>
       <td class="td_reportMd1">&nbsp;
       <fmt:formatNumber value="<%=floatinglossAll%>" pattern="#,##0.00"/></td>
	   <td class="td_reportMd1">&nbsp;
       <fmt:formatNumber value="<%=quanAll%>" pattern="#,##0.00"/></td>
       <td class="td_reportMd1">&nbsp;
       <fmt:formatNumber value="<%=ClearAssureAll%>" pattern="#,##0.00"/></td>
	   <td class="td_reportMd1">&nbsp;
	   <fmt:formatNumber value="<%=ClearFLAll%>" pattern="#,##0.00"/></td>
	   <td class="td_reportRd1">&nbsp;
	   <fmt:formatNumber value="<%=ClearSettleMarginAll%>" pattern="#,##0.00"/></td>
	   <%
	   }
	%>
	</tr>
	<%
	 if(length==0){
	%>
	<tr>
		<td class="td_reportRd" colspan="<%=list.size()+8 %>">
			无符合条件信息。
		</td>
	</tr>
	<%}%>
	</table>
	
	<%
	}
	%>
					  	</td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
			</td>	
		</tr>
</table>
</body>
</html>
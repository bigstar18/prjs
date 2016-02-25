<%@ page contentType="text/html;charset=GBK" %><%@ include file="util.jsp" %><%@ page import="gnnt.MEBS.finance.firmFunds.ClientLedger"%><%@ page import="gnnt.MEBS.finance.service.*"%><%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String brokerId=request.getParameter("brokerId");
	String cateGoryId=request.getParameter("cateGoryId");
	String filter = " ";
	if(chcekNull(startFirmID)){
		if(!startFirmID.equals("null"))
			filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		if(!endFirmID.equals("null"))
			filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate >= to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate <= to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(brokerId)){
		if(!brokerId.equals("null"))
			filter+=" and t.firmid in (select firmId from M_B_FIRMANDBROKER where brokerId ='"+brokerId+"')";
	}
	if(chcekNull(cateGoryId)){
		if(!cateGoryId.equals("null"))
			filter +=" and t.firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='"+cateGoryId+"')) ";
	}
	
	String sql = "";
	Map map=ClientLedger.queryClientLedgerTotalCateGory(startClearDate,endClearDate,startFirmID,endFirmID,"2",cateGoryId, brokerId);
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	if(map!=null)
	{
	   List list=ClientLedger.queryFiledMap("2");
	   list.remove(list.size()-1);
	   list.remove(list.size()-1);
	   list.remove(0);
	   List filedList=(List)map.get("filed");
	   List valueList=(List)map.get("value");
	   pageContext.setAttribute("filed", list);
	   %> 
<style>
table
  {
  border-collapse:collapse;
  }
table,th, td
  {
  border: 1px solid black;
  }
</style>
<br><center class="reportHead">资金月报表</center>
<table align="center" width="800px" ><tr><td colspan="17">日期:<%=startClearDate%>至<%=endClearDate%></td></tr></table><table align="center" class="reportTemp" width="2000px"><tr>
	<%
	if(list!=null&&list.size()>0)
	{
	int length=list.size();
	for(int i=0;i<list.size();i++)
	{
	  Map m=(Map)list.get(i);
	  String field=(String)m.get("code");
	  String name=(String)m.get("name");
	  //if(!"MarginBack".equals(field)&&!"FLBack".equals(field)&&!"SettleMarginBack".equals(field)&&!"Margin".equals(field)&&!"FL".equals(field)&&!"SettleMargin".equals(field))
	  //{
	  
	     %><td><%=name%></td><%//}
	  }
%><td>+本日保证金</td><td>+本日浮亏</td><td>+本日交收保证金</td><td>+浮动盈亏</td><td>本日权益</td><td>成交量</td><%
	}
	%></tr><%
	          List totalList=new ArrayList();
	          double MarginAll=0;
              double FLAll=0;
              double SettleMarginAll=0;
              double quanAll=0;
			  double floatinglossAll=0;
			  double quantityAll=0;
	  		  if(valueList!=null&&valueList.size()>0&&filedList!=null&&filedList.size()>0)
	  		  {
	  		        double lastBalance = 0;
		  		    for(int i=0;i<valueList.size();i++)
		  		    {
		  		       Map valueMap=(Map)valueList.get(i);
		  		       String firmId="";
		  		       if(valueMap!=null)
		  		       {
		  		       %><tr><%
		  		         int length=filedList.size()-2;
		  		         for(int ii=0;ii<filedList.size()-2;ii++)
		  		         {
		  		           String field=(String)filedList.get(ii);
		  		           //if(!"MarginBack".equals(field)&&!"FLBack".equals(field)&&!"SettleMarginBack".equals(field)&&!"Margin".equals(field)&&!"FL".equals(field)&&!"SettleMargin".equals(field))
		  		           //{
			  		           if(ii<1)
			  		           {
			  		             Object value=valueMap.get(field);
			  		             firmId=value.toString();
			  		             %><td><%=value.toString()%></td><% 
			  		           }
			  		           else
			  		           {
			  		             Object value=valueMap.get(field);
			  		             double v=0;
			  		             boolean monthSign=false;
			  		             if(totalList.size()>ii-1)
			  		             {
			  		               v=Double.parseDouble((String)totalList.get(ii-1));
			  		               monthSign=true;
			  		             }
			  		              v+=((BigDecimal)value).doubleValue();
			  		              if(monthSign)
			  		              {
			  		              totalList.set(ii-1,v+"");
			  		              }
			  		              else
			  		              {
			  		               totalList.add(v+"");
			  		              }
			  		             
			  		              %><td><%
				  		              lastBalance=Double.parseDouble(value.toString());
				  		          %><fmt:formatNumber value="<%=value.toString()%>" pattern="#,##0.00"/></td><%
			  		           }
		  		           //}
		  		           //else
		  		           //{
		  		             // totalList.add(null);
		  		           //}
		  		         }
		  		         
		  		          sql="select to_char(nvl(max(t.cleardate),sysdate),'yyyy-MM-dd') d from t_h_firm t where t.cleardate>=to_date('"+startClearDate+"','yyyy-MM-dd') and t.cleardate<=to_date('"+endClearDate+"','yyyy-MM-dd') and t.firmid='"+firmId+"'";
		  		          List dateList=dao.queryBySQL(sql);
		  		          if(dateList.size()>0)
		  		          {
		  		            Map m=(Map)dateList.get(0);
		  		            String d=(String)m.get("d");
		  		            sql="select "+ 
                                "nvl(RuntimeMargin-RuntimeAssure,0)Margin,"+
                                "nvl(RuntimeFL,0)FL,"+
                                "nvl(RuntimeSettleMargin,0)SettleMargin"+ 
                                " from t_h_firm t where t.firmid='"+firmId+"' and t.cleardate=to_date('"+d+"','yyyy-MM-dd')";
                            List dataList=dao.queryBySQL(sql);
                            sql="select (nvl(sum(t.floatingloss),0)) floatingloss  from t_h_firmholdsum t where firmId='"+firmId+"' and t.cleardate=to_date('"+d+"','yyyy-MM-dd') "+filter;
                            String hsql="select nvl(sum(t.quantity),0) sumQuantity from t_h_trade t where firmId= '"+firmId+"'"+filter;
		                    List listFloatingloss=dao.queryBySQL(sql);
		                    //System.out.println("hsql:"+hsql);
		                    List sumQuantityList=dao.queryBySQL(hsql);
		                   	Map sumQuanMap=(Map)sumQuantityList.get(0);
		                   	double sumQuantity=((BigDecimal)sumQuanMap.get("sumQuantity")).doubleValue();
		                   	quantityAll+=sumQuantity;
		                    Object floatingloss="0";
		                    if(listFloatingloss!=null&&listFloatingloss.size()>0) {
		                        Map mf=(Map)listFloatingloss.get(0);
		                        floatingloss=mf.get("floatingloss");
		                    }
		                            
                            if(dataList.size()>0)
                            {
                              Map m1=(Map)dataList.get(0);
                              double Margin=((BigDecimal)m1.get("Margin")).doubleValue();
                              double FL=((BigDecimal)m1.get("FL")).doubleValue();
                              double SettleMargin=((BigDecimal)m1.get("SettleMargin")).doubleValue();
                              double quan = lastBalance + Margin + FL + SettleMargin + Double.parseDouble(floatingloss.toString());
                              MarginAll+=Margin;
                              FLAll+=FL;
                              SettleMarginAll+=SettleMargin;
                              quanAll += quan;
                              floatinglossAll += Double.parseDouble(floatingloss.toString());
                              %><td><fmt:formatNumber value="<%=Margin+""%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=FL+""%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=SettleMargin+""%>" pattern="#,##0.00"/></td>
<td><fmt:formatNumber value="<%=floatingloss.toString()%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=quan+""%>" pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value="<%=sumQuantity%>" pattern="#,##0.00"/></td><%
                              
                            }else{
                              %><td><fmt:formatNumber value="<%=0+""%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=0+""%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=0+""%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=0%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=0+""%>" pattern="#,##0.00"/></td>
                              <td><fmt:formatNumber value="<%=0%>" pattern="#,##0.00"/></td><td><fmt:formatNumber value="<%=0%>" pattern="#,##0.00"/><</td><%
                            }
		  		            
		  		          }
		  		       }
		  		       
		  		       %></tr><%
		  		    }	    
	  		  }
	  		 %><tr><%
	   int length=totalList.size();
	   if(length>0){
	   %><td><b>合计</b></td><%
	   
	   for(int i=0;i<totalList.size();i++)
	   {
	      String value=(String)totalList.get(i);
	      if(value!=null&&!"".equals(value))
	      {
	      %><td><b><fmt:formatNumber value="<%=value%>" pattern="#,##0.00"/></b></td><%
	      }
	   }
	   %><td><b><fmt:formatNumber value="<%=MarginAll+""%>" pattern="#,##0.00"/></b></td><td><b><fmt:formatNumber value="<%=FLAll+""%>" pattern="#,##0.00"/></b></td><td><b><fmt:formatNumber value="<%=SettleMarginAll+""%>" pattern="#,##0.00"/></b></td><td><b><fmt:formatNumber value="<%=floatinglossAll+""%>" pattern="#,##0.00"/></b></td><td><b><fmt:formatNumber value="<%=quanAll+""%>" pattern="#,##0.00"/></b></td>
	   <td><b><fmt:formatNumber value="<%=quantityAll+""%>" pattern="#,##0.00"/></b></td><%
	   }
	%></tr><%
	 if(length==0){
	%><tr>
     <td class="td_reportRd" colspan="<%=list.size()+5 %>">
			无符合条件信息。
		</td></tr><%}%></table><%
	}
	%>
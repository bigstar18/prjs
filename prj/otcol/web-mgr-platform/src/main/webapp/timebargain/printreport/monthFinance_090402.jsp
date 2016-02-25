<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.finance.firmFunds.ClientLedger"%>
<%@ page import="gnnt.MEBS.finance.manager.ViewManager"%>
<%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate = request.getParameter("endClearDate");
	String sql = "";
	Map map=ClientLedger.queryClientLedgerTotal(startClearDate,endClearDate,startFirmID,endFirmID,"2");
	if(map!=null)
	{
	   List list=ViewManager.queryFiledMap("2");
	   list.remove(list.size()-1);
	   list.remove(list.size()-1);
	   list.remove(0);
	   List filedList=(List)map.get("filed");
	   List valueList=(List)map.get("value");
	   pageContext.setAttribute("filed", list);
	   %>
	   
	<br><center class="reportHead">资金月报表</center>
	<table align="center" width="800px" border="0">
	<tr>
		<td class="reportRight" colspan="17">日期:<%=startClearDate%>至<%=endClearDate%></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="2200px">
	<tr>
	<%
	if(list!=null&&list.size()>0)
	{
	int length=list.size();
	for(int i=0;i<list.size();i++)
	{
	  Map m=(Map)list.get(i);
	  String name=(String)m.get("name");
	  if(i==length-1)
	  {
	    %>
	    <td class="td_reportRdHead">
	    <%
	  }
	  else
	  {
	     %>
	     <td class="td_reportMdHead">
	     <%
	  }
	  %>
	  <%=name%></td>
	  <%
	}
	}
	%>
	
	</tr>	
	<%
	          List totalList=new ArrayList();
	  		  if(valueList!=null&&valueList.size()>0&&filedList!=null&&filedList.size()>0)
	  		  {
	  		        
		  		    for(int i=0;i<valueList.size();i++)
		  		    {
		  		       Map valueMap=(Map)valueList.get(i);
		  		       if(valueMap!=null)
		  		       {
		  		       %>
		  		       <tr>
		  		       <%
		  		         int length=filedList.size()-2;
		  		         for(int ii=0;ii<filedList.size()-2;ii++)
		  		         {
		  		           String field=(String)filedList.get(ii);
		  		           if(ii<1)
		  		           {
		  		             Object value=valueMap.get(field);
		  		             %>
		  		             <td class="td_reportMd" ><%=value.toString()%></td>
		  		             <% 
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
		  		              if(ii==length-1)
		  		              {
		  		              %>
		  		              <td class="td_reportRd">
		  		              <%
		  		              }
		  		              else
		  		              {
		  		              %>
		  		              <td class="td_reportMd1">
		  		              <%
		  		              }
		  		             %>
		  <fmt:formatNumber value="<%=value.toString()%>" pattern="#,##0.00"/></td>
		  		             <%
		  		           }
		  		         }
		  		         %>
	  		             </tr>
		  		         <%
		  		       }
		  		    }
	  		  }
	  		 %>
	<tr>
	
	<%
	   int length=totalList.size();
	   if(length>0){
	   %>
	   <td class="td_reportMd"><b>合计</b></td>
	   <%
	   }
	   for(int i=0;i<totalList.size();i++)
	   {
	      String value=(String)totalList.get(i);
	      if(i==length-1)
	      {
	      %>
	      <td class="td_reportRd">
	      <%
	      }
	      else
	      {
	      %>
	      <td class="td_reportMd1">
	      <%
	      }
	      %>
	      <b><fmt:formatNumber value="<%=value%>" pattern="#,##0.00"/></b></td>
	      <%
	   }
	%>
	</tr>
	<%
	 if(length==0){
	%>
	<tr>
		<td class="td_reportRd" colspan="<%=list.size() %>">
			无符合条件信息。
		</td>
	</tr>
	<%}%>
	</table>
	
	<%
	}
	%>
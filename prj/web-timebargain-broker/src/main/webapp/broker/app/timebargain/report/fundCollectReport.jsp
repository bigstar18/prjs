<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>金网安泰 - 资金汇总表</title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="打印">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startClearDate = request.getParameter("startdate");
	String endClearDate = request.getParameter("enddate");
	String firmcategory = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String brokerageFilter = " ("+ user.getSql();
	
	String filter = "  and t.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate >= to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate <= to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter += " and t.firmid in ( select f.firmid from m_firm f where f.firmcategoryid = '"+firmcategory+" ') ";
		brokerageFilter += " and firmid in ( select f.firmid from m_firm f where f.firmcategoryid = '"+firmcategory+" ') ";
	}
	
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//会员添加居间条件
		brokerageFilter += " and firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	brokerageFilter += ") ";
	String sql = "";
	Map map=ClientLedger.queryClientLedgerTotal(startClearDate,endClearDate,startFirmID,endFirmID,brokerageFilter,"15");
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	if(map!=null)
	{
	   List list=ClientLedger.queryFiledMap("15");
	   //list.remove(list.size()-1);
	   //list.remove(list.size()-1);
	   //list.remove(0);
	   List filedList=(List)map.get("filed");
	   List valueList=(List)map.get("value");
	   pageContext.setAttribute("filed", list);
	   %>
	   
	<br><center class="reportHead">资金汇总表</center>
	<table align="center" width="800px" border="0">
	<tr>
		<td class="reportRight" colspan="7">
		    起始交易商代码：<%=startFirmID%>&nbsp;&nbsp;结束交易商代码：<%=endFirmID%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td class="reportRight" colspan="10">
		    起始日期：<%=startClearDate%>&nbsp;&nbsp;结束日期：<%=endClearDate%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <%
		    	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		    	%>
		    		居间：<%=brokerageID%>
		    		<%
		    	}
		     %>
		    
		</td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="2000px">
	<tr>
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
	  
	     %>
	     <td class="td_reportMdHead">
	  <%=name%></td>
	  <%
	  //}
	}
	%>
	<td class="td_reportRdHead">本日权益</td>
	<%
	}
	%>
	
	</tr>	
	<%
	          List totalList=new ArrayList();
	          //double MarginAll=0;
              //double FLAll=0;
              //double SettleMarginAll=0;
              double quanAll=0;
			  //double floatinglossAll=0;
	  		  if(valueList!=null&&valueList.size()>0&&filedList!=null&&filedList.size()>0)
	  		  {
	  		        double lastBalance = 0;
		  		    for(int i=0;i<valueList.size();i++)
		  		    {
		  		       Map valueMap=(Map)valueList.get(i);
		  		       String firmId="";
		  		       if(valueMap!=null)
		  		       {
		  		       %>
		  		       <tr onclick="selectTr()">
		  		       <%
		  		         int length=filedList.size();
		  		         for(int ii=0;ii<filedList.size();ii++)
		  		         {
		  		           String field=(String)filedList.get(ii);
			  		           if(ii<1)
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
			  		             
			  		              %>
			  		              <td class="td_reportMd1">
			  		              <%
				  		              lastBalance=Double.parseDouble(value.toString());
				  		          %>
			  <fmt:formatNumber value="<%=value.toString()%>" pattern="#,##0.00"/></td>
			  		             <%
			  		           }
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
                            sql="select (nvl(sum(t.floatingloss),0)) floatingloss  from t_h_firmholdsum t where t.firmId='"+firmId+"' and t.cleardate=to_date('"+d+"','yyyy-MM-dd') "+filter;
		                    List listFloatingloss=dao.queryBySQL(sql);
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
                              //MarginAll+=Margin;
                             // FLAll+=FL;
                              //SettleMarginAll+=SettleMargin;
                              quanAll += quan;
                              //floatinglossAll += Double.parseDouble(floatingloss.toString());
                              %>
			                  <td class="td_reportRd1">&nbsp;
			                  <fmt:formatNumber value="<%=quan%>" pattern="#,##0.00"/></td>
                              <%
                              
                            }else{
                              %>
			                  <td class="td_reportRd1">&nbsp;
			                  <fmt:formatNumber value="<%=0%>" pattern="#,##0.00"/></td>
                              <%
                            }
		  		            
		  		          }
		  		       }
		  		       %>
	  		             </tr>
		  		         <%
		  		    }
		  		    
		  		   
		  		    
	  		  }
	  		 %>
	<tr onclick="selectTr()">
	
	<%
	   int length=totalList.size();
	   if(length>0){
	   %>
	   <td class="td_reportMd"><b>合计</b></td>
	   <%
	   
	   for(int i=0;i<totalList.size();i++)
	   {
	      String value=(String)totalList.get(i);
	      if(value!=null&&!"".equals(value))
	      {
	      %>
	      <td class="td_reportMd1">
	      <b><fmt:formatNumber value="<%=value%>" pattern="#,##0.00"/></b></td>
	      <%
	      }
	   }
	   %>
	   <td class="td_reportRd1">
	   <b><fmt:formatNumber value="<%=quanAll%>" pattern="#,##0.00"/></b></td>
	   <%
	   }
	%>
	</tr>
	<%
	 if(length==0){
	%>
	<tr>
		<td class="td_reportRd" colspan="<%=list.size()+7 %>">
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
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="打印"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function replaceHtml(replacedStr,repStr,endStr){   
    var replacedStrF = "";   
    var replacedStrB = "";   
    var repStrIndex = replacedStr.indexOf(repStr);   
    while(repStrIndex != -1){   
        replacedStrF = replacedStr.substring(0,repStrIndex);   
        replacedStrB = replacedStr.substring(repStrIndex,replacedStr.length);   
        replacedStrB = replacedStrB.substring(replacedStrB.indexOf(endStr)+1,replacedStrB.length);   
        replacedStr = replacedStrF + replacedStrB;   
        repStrIndex = replacedStr.indexOf(repStr);   
    }   
    return replacedStr;   
}   
//elTalbeOut 这个为导出内容的外层表格，主要是设置border之类的样式，elDiv则是整个导出的html部分   
function htmlToExcel(elTableOut,elDiv){   
    try{   
        //设置导出前的数据，为导出后返回格式而设置   
        var elDivStrBak = elDiv.innerHTML;   
        //设置table的border=1，这样到excel中就有表格线 ps:感谢双面提醒   
        elTableOut.border=1;   
        //过滤elDiv内容   
        var elDivStr = elDiv.innerHTML;   
        elDivStr = replaceHtml(elDivStr,"<A",">");   
        elDivStr = replaceHtml(elDivStr,"</A",">");   
        elDiv.innerHTML=elDivStr;      
           
        var oRangeRef = document.body.createTextRange();   
        oRangeRef.moveToElementText( elDiv );   
        oRangeRef.execCommand("Copy");   
           
        //返回格式变换以前的内容   
        elDiv.innerHTML = elDivStrBak;   
        //内容数据可能很大，所以赋空   
        elDivStrBak = "";   
        elDivStr = "";   
           
        var oXL = new ActiveXObject("Excel.Application")   
        var oWB = oXL.Workbooks.Add ;   
        var oSheet = oWB.ActiveSheet ;   
        oSheet.Paste();   
        oSheet.Cells.NumberFormatLocal = "@";   
        oSheet.Columns("D:D").Select   
        oXL.Selection.ColumnWidth = 20  
        oXL.Visible = true;        
        oSheet = null;   
        oWB = null;   
        appExcel = null;   
    }catch(e){   
        alert(e.description)   
    }   
}
function method1(tableid) {//整个表格拷贝到EXCEL中 
    var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //创建AX对象excel 
    var oWB = oXL.Workbooks.Add(); 
    //获取workbook对象 
        var oSheet = oWB.ActiveSheet; 
    //激活当前sheet 
    var sel = document.body.createTextRange(); 
    sel.moveToElementText(curTbl); 
    //把表格中的内容移到TextRange中 
    sel.select(); 
    //全选TextRange中内容 
    sel.execCommand("Copy"); 
    //复制TextRange中内容  
    oSheet.Paste(); 
    //粘贴到活动的EXCEL中       
    oXL.Visible = true; 
    //设置excel可见属性 
} 
</SCRIPT>
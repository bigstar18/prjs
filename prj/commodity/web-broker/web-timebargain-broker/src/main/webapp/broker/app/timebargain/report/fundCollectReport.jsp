<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>������̩ - �ʽ���ܱ�</title>
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
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
		     		 
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
		//��Ա��ӾӼ�����
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
	   
	<br><center class="reportHead">�ʽ���ܱ�</center>
	<table align="center" width="800px" border="0">
	<tr>
		<td class="reportRight" colspan="7">
		    ��ʼ�����̴��룺<%=startFirmID%>&nbsp;&nbsp;���������̴��룺<%=endFirmID%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td class="reportRight" colspan="10">
		    ��ʼ���ڣ�<%=startClearDate%>&nbsp;&nbsp;�������ڣ�<%=endClearDate%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <%
		    	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		    	%>
		    		�Ӽ䣺<%=brokerageID%>
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
	<td class="td_reportRdHead">����Ȩ��</td>
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
	   <td class="td_reportMd"><b>�ϼ�</b></td>
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
			�޷���������Ϣ��
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
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ"> 
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
//elTalbeOut ���Ϊ�������ݵ��������Ҫ������border֮�����ʽ��elDiv��������������html����   
function htmlToExcel(elTableOut,elDiv){   
    try{   
        //���õ���ǰ�����ݣ�Ϊ�����󷵻ظ�ʽ������   
        var elDivStrBak = elDiv.innerHTML;   
        //����table��border=1��������excel�о��б���� ps:��л˫������   
        elTableOut.border=1;   
        //����elDiv����   
        var elDivStr = elDiv.innerHTML;   
        elDivStr = replaceHtml(elDivStr,"<A",">");   
        elDivStr = replaceHtml(elDivStr,"</A",">");   
        elDiv.innerHTML=elDivStr;      
           
        var oRangeRef = document.body.createTextRange();   
        oRangeRef.moveToElementText( elDiv );   
        oRangeRef.execCommand("Copy");   
           
        //���ظ�ʽ�任��ǰ������   
        elDiv.innerHTML = elDivStrBak;   
        //�������ݿ��ܴܺ����Ը���   
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
function method1(tableid) {//������񿽱���EXCEL�� 
    var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //����AX����excel 
    var oWB = oXL.Workbooks.Add(); 
    //��ȡworkbook���� 
        var oSheet = oWB.ActiveSheet; 
    //���ǰsheet 
    var sel = document.body.createTextRange(); 
    sel.moveToElementText(curTbl); 
    //�ѱ���е������Ƶ�TextRange�� 
    sel.select(); 
    //ȫѡTextRange������ 
    sel.execCommand("Copy"); 
    //����TextRange������  
    oSheet.Paste(); 
    //ճ�������EXCEL��       
    oXL.Visible = true; 
    //����excel�ɼ����� 
} 
</SCRIPT>
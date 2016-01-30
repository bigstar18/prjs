<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/report.css" type="text/css"/>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>������̩ - �ʽ�����</title>
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
	String cleardate = request.getParameter("cleardate");
	String firmcategoryid = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String filter = " and t.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(firmcategoryid!=null&&!firmcategoryid.equals("")){
		filter += " and m.firmcategoryid = '"+firmcategoryid+"'";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//��Ա��ӾӼ�����
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	String sql ="select t.firmId,m.name from t_h_firm t,m_firm m where t.firmId=m.firmId "+filter+" order by t.firmid";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">�޷���������Ϣ��</font></b></div>
    <%
    }
    for(int i=0;i<list.size();i++)
    {
         Map firmIDMap = (Map)list.get(i);
    	String firmId = (String)firmIDMap.get("firmId");
    	String name = (String)firmIDMap.get("name");
%>
<br><center class="reportHead">�ʽ�����</center><br>	
<table width="600px" align="center" >
	<tr>
		<td class="reportLeft">�����̴���:<%=firmId %></td>
		<td class="reportLeft" >����������:<%=name%></td>
		<td class="reportLeft" width="100px">&nbsp;</td>
		<td class="reportRight">��λ:Ԫ</td>
		<td class="reportRight">����:<%=cleardate %></td>
	</tr>
</table>
    <%
       sql="select t.lastbalance,t.todaybalance from f_firmbalance t,m_firm m where t.firmId='"+firmId+"' and m.firmId='"+firmId+"' "+filter;
       sql=sql.replaceAll("cleardate","b_date");
       List firmbalanceList=dao.queryBySQL(sql);
       Object lastbalance=new BigDecimal(0);
       Object todaybalance=new BigDecimal(0);
       if(firmbalanceList!=null&&firmbalanceList.size()>0)
       {
            Map map=(Map)firmbalanceList.get(0);
            lastbalance=map.get("lastbalance");
            todaybalance=map.get("todaybalance");
       }
       sql="select ClearMargin-ClearAssure ClearAssure,ClearFL,ClearSettleMargin,RuntimeMargin-RuntimeAssure RuntimeMargin,"+
       "RuntimeFL,RuntimeSettleMargin,MinClearDeposit,MaxOverdraft from t_h_firm t,m_firm m where t.firmId='"+firmId+"' and m.firmId='"+firmId+"' "+filter;
       List thFirmList=dao.queryBySQL(sql);
       Object ClearAssure=new BigDecimal(0);
       Object ClearFL=new BigDecimal(0);
       Object ClearSettleMargin=new BigDecimal(0);
       Object RuntimeMargin=new BigDecimal(0);
       Object RuntimeFL=new BigDecimal(0);
       Object RuntimeSettleMargin=new BigDecimal(0);
       Object MinClearDeposit=new BigDecimal(0);
       Object MaxOverdraft=new BigDecimal(0);
       if(thFirmList!=null&&thFirmList.size()>0)
       {
          Map map=(Map)thFirmList.get(0);
          ClearAssure=map.get("ClearAssure");
          ClearFL=map.get("ClearFL");
          ClearSettleMargin=map.get("ClearSettleMargin");
          RuntimeMargin=map.get("RuntimeMargin");
          RuntimeFL=map.get("RuntimeFL");
          RuntimeSettleMargin=map.get("RuntimeSettleMargin");
          MinClearDeposit=map.get("MinClearDeposit");
          MaxOverdraft=map.get("MaxOverdraft");
       }
       sql = "select nvl(sum(t.floatingloss),0) floatingloss from t_h_firmholdsum t,m_firm m where t.firmid = '" + firmId + "'  and m.firmId='"+firmId+"' " + filter;
       List floatingLossList = dao.queryBySQL(sql);
       Object floatingloss = new BigDecimal(0);
       if (floatingLossList != null && floatingLossList.size() > 0) {
       		Map map = (Map)floatingLossList.get(0);
       		floatingloss = map.get("floatingloss");
       }
       double runtimeRight = ((BigDecimal)todaybalance).doubleValue() + ((BigDecimal)RuntimeMargin).doubleValue() + ((BigDecimal)RuntimeFL).doubleValue() + ((BigDecimal)RuntimeSettleMargin).doubleValue() + ((BigDecimal)floatingloss).doubleValue();
       sql="select '('||(case when f.fieldsign>0 then '+' else '-' end)||')'||f.name name,nvl(tt.value,0) value  from (select * from f_clientledger t, m_firm m where  t.firmId='"+firmId+"'  and m.firmId='"+firmId+"'  "+filter+""+  
           " ) tt,f_ledgerfield f "+
           "where f.code=tt.code(+) and f.moduleid in (11,15) order by f.moduleid,f.ordernum";
       sql=sql.replaceAll("cleardate","b_date");
       System.out.println("=="+filter);
       System.out.println("=="+sql);
       List clientledgerList=dao.queryBySQL(sql);

    %>
<table width="600px" height="200px" align="center" border="0" class="reportTemp">
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">�����ʽ����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="600px">&nbsp;<fmt:formatNumber value="<%=lastbalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<%
	  if(clientledgerList!=null&&clientledgerList.size()>0)
	  {
	    for(int a=0;a<clientledgerList.size();a++)
	    {
	      Map map=(Map)clientledgerList.get(a);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">&nbsp;<%=map.get("name").toString()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(map.get("value"))%></td>
	     </tr>
	      <%
	    }
	  }
          sql="select nvl(sum(case when f.fieldsign>0 then tt.value else -tt.value end),0) value  from (select * from f_clientledger t, m_firm m where  t.firmId='"+firmId+"' and m.firmId='"+firmId+"'  "+filter+""+  
           " ) tt,f_ledgerfield f "+
           "where f.code=tt.code(+) and f.moduleid not in (11,15) order by f.moduleid,f.ordernum";
          sql=sql.replaceAll("cleardate","b_date");
          List clientledgerOtherList=dao.queryBySQL(sql);
	  if(clientledgerOtherList!=null&&clientledgerOtherList.size()>0)
	  {
              
	      Map map=(Map)clientledgerOtherList.get(0);
	      %>
	      <tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)��������ϵͳ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(map.get("value"))%></td>
	     </tr>
	      <%
	    
	  }


	 %>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">�����ʽ����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=todaybalance.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)���ձ�֤��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)���ո���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)���ս��ձ�֤��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=RuntimeSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">(+)����ӯ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=floatingloss.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">����Ȩ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(runtimeRight)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">���ձ�֤��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearAssure.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">���ո���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearFL.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">���ս��ձ�֤��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=ClearSettleMargin.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">����׼��������޶�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=MinClearDeposit.toString()%>" pattern="#,##0.00"/></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">��׷���ʽ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<%
		  double superAdd=0;
		   if(((BigDecimal)todaybalance).doubleValue()+((BigDecimal)MaxOverdraft).doubleValue()-((BigDecimal)MinClearDeposit).doubleValue()>=0)
		   {
		     
		   }
		   else
		   {
		      superAdd=((BigDecimal)MinClearDeposit).doubleValue()-((BigDecimal)todaybalance).doubleValue()-((BigDecimal)MaxOverdraft).doubleValue();
		   }
		%>
		<td class="td_reportRd1" width="50%">&nbsp;<%=turnToNum2(superAdd)%></td>
	</tr>
	<tr>
		<td class="td_reportMdHead_Right" align="right" width="50%">��Ѻ�ʽ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="td_reportRd1" width="50%">&nbsp;<fmt:formatNumber value="<%=MaxOverdraft.toString()%>" pattern="#,##0.00"/></td>
	</tr>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
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
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp"%>

<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>������̩ - ת��ӯ����ϸ</title>
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
	String firmcategory = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String filter_m = " 1=1 and m.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter_m += " and m.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter_m += " and m.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(cleardate)){
		//filter += " and m.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter_m += " and m.firmcategoryid = '"+firmcategory+"'";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//��Ա��ӾӼ�����
		filter_m += " and m.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	//query data
	String sql = " select m.firmId,m.name from m_firm m where "+filter_m+" order by m.firmId";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">�޷���������Ϣ��</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getFirmId = (String)firmIDMap.get("firmId");
    	String innerSql = " select customerid,commodityid, "+
    	" (case when bs_flag=1 then '���' else '����' end)||(case when ordertype=1 then '����' else 'ת��' end) type, "+
		" quantity,price,holdprice,to_char(holdtime,'yyyy-MM-dd') holdtime,close_pl, "+
		" (case when to_char(TradeAtClearDate,'yyyy-MM-dd')=to_char(AtClearDate,'yyyy-MM-dd') then quantity else 0 end) ding from "+
		" (select t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.quantity,t.price,t.holdprice,t.holdtime,t.close_pl,t.tradetime,t.atcleardate,t.tradeatcleardate "+
		" from t_h_trade t where t.ordertype=2 and t.cleardate=to_date('"+cleardate+"','yyyy-MM-dd') "+
		" and t.FirmID='"+getFirmId +"' order by customerid,commodityid )";
    
   	BigDecimal sumQuantity = new BigDecimal(0);
   	BigDecimal sumClose_pl = new BigDecimal("0.00");
   	BigDecimal sumDing = new BigDecimal(0);
		%>
		<br><center class="reportHead">ת��ӯ����ϸ��</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft">
			�����̴���:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %>&nbsp;&nbsp;
			����������:&nbsp;<%=turnToStr(firmIDMap.get("name")) %>
		</td>
		<td class="reportRight" colspan="6">����:&nbsp;<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" height="100px" width="600px">
	<tr>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">����(��)</td>
	<td class="td_reportMdHead">ת�ü۸�</td>
	<td class="td_reportMdHead">�����۸�</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">ת��ӯ��</td>
	<td class="td_reportRdHead">������ת��</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("holdprice")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("holdtime")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("close_pl")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("ding")) %></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));
		sumClose_pl = sumClose_pl.add(turnToNum(innerMap.get("close_pl")));
		sumDing = sumDing.add(turnToNum(innerMap.get("ding")));
	}
	if(innerlist.size()>0){
	%>
	<tr>
	<td class="td_reportMd"><b>�ϼ�</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<fmt:formatNumber value="<%=sumClose_pl %>" pattern="#,##0.00"/></b></td>
	<td class="td_reportRd1"><b>&nbsp;<%=sumDing %></b></td>
	</tr>
<%}else{%>
	<tr>
			<td class="td_reportRd" colspan="9">
				�޷���������Ϣ��
			</td>
		</tr>
	<%}%>
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
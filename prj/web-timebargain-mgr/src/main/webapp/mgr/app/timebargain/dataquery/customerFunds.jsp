<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/report.css" type="text/css"/>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>�ʽ������</title>
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
				
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
						  
						   <br><center class="reportHead">�ʽ������</center><br>
                             <table align="center" class="reportTemp" width="600px">
                             
                             <c:forEach items="${result}" var="info">
                             
                             
                            
	                         <tr>
		                         <td class="td_reportMdHead">�����̴���</td>
		                         <td class="td_reportMdHead">${firmID }</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">�ڳ����</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.LASTBALANCE }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+���ձ�֤��</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLEARMARGIN }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-���ձ�֤��</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.RUNTIMEMARGIN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+���ո���</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLEARFL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-���ո���</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.RUNTIMEFL }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
	                            <c:set var="out_Amount" value="${info.INAMOUNT - info.OUTAMOUNT }"></c:set>
		                         <td class="td_reportMdHead">+�����</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${out_Amount }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-ί�ж����ʽ�</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.ORDERFROZEN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+����ת��ӯ��</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLOSE_PL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-���������ʽ�</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.OTHERFROZEN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+�����仯</td>
		                         <td class="td_reportMdHead">
		                          <fmt:formatNumber value="${(info.USEFULFUND - info.LASTBALANCE) - (info.CLEARMARGIN + info.CLEARFL + out_Amount + info.CLOSE_PL - info.RUNTIMEMARGIN - info.RUNTIMEFL - info.ORDERFROZEN - info.TRADEFEE - info.OTHERFROZEN )}" pattern="#,##0.00" />
		                         </td>
		                         <td class="td_reportMdHead">-����������</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.TRADEFEE }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">���տ����ʽ�</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.USEFULFUND }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">��Ѻ�ʽ�</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.MAXOVERDRAFT }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">����ӯ��</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.PL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">��ǰȨ��</td>
		                         <td class="td_reportMdHead">
		                           <fmt:formatNumber value="${info.BALANCE + info.CLEARMARGIN - info.CLEARASSURE + info.RUNTIMESETTLEMARGIN  + info.CLEARFL + info.PL + info.CLOSE_PL - info.TRADEFEE }" pattern="#,##0.00" />
		                         </td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                          </c:forEach>
	                       </table>
	                     
					  </td>
					 </tr>
					 
				</table>
				
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
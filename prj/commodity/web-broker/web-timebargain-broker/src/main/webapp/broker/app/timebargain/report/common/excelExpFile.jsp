<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%
	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy/MM/dd-HH/mm");
	String datetime = tempDate.format(new java.util.Date());	 
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition","attachment; filename=GNNTReport"+datetime +".xls"); 
%>

<!--[if gte mso 9]><xml>
 <m:ExcelWorkbook>
  <m:ExcelWorksheets>
   <m:ExcelWorksheet>
    <m:Name>Sheet1</m:Name>
    <m:WorksheetOptions>
     <m:DefaultRowHeight>285</m:DefaultRowHeight>
     <m:Selected/>
     <m:Panes>
      <m:Pane>
       <m:Number>3</m:Number>
       <m:ActiveRow>2</m:ActiveRow>
       <m:ActiveCol>1</m:ActiveCol>
      </m:Pane>
     </m:Panes>
     <m:ProtectContents>False</m:ProtectContents>
     <m:ProtectObjects>False</m:ProtectObjects>
     <m:ProtectScenarios>False</m:ProtectScenarios>
    </m:WorksheetOptions>
   </m:ExcelWorksheet>
  </m:ExcelWorksheets>
  <m:WindowHeight>9090</m:WindowHeight>
  <m:WindowWidth>11715</m:WindowWidth>
  <m:WindowTopX>240</m:WindowTopX>
  <m:WindowTopY>90</m:WindowTopY>
  <m:ProtectStructure>False</m:ProtectStructure>
  <m:ProtectWindows>False</m:ProtectWindows>
 </m:ExcelWorkbook>
</xml><![endif]-->
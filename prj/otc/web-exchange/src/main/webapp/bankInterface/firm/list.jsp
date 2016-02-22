<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
int pageSize = BANKPAGESIZE;
String today = Tool.fmtDate(new java.util.Date());
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
String firmID = Tool.delNull(request.getParameter("firmID"));
String bankID = Tool.delNull(request.getParameter("bankID"));
String firmType = Tool.delNull(request.getParameter("firmType"));
String capitalType = Tool.delNull(request.getParameter("capitalType"));
String capitalStatus = Tool.delNull(request.getParameter("capitalStatus"));
String s_time = Tool.delNull(request.getParameter("s_time"));
String e_time = Tool.delNull(request.getParameter("e_time"));
String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
if(submitFlag == null || submitFlag.trim().length()<=0){
	if(s_time == null || s_time.trim().length()<=0){
		s_time=today;
	}
	if(e_time == null || e_time.trim().length()<=0){
		e_time=today;
	}
}
String filter = "";
if(firmID != null && firmID.trim().length()>0){
	filter += " and fbc.firmID like '%"+firmID.trim()+"%' ";
}
if(bankID != null && bankID.trim().length()>0 && !bankID.trim().equalsIgnoreCase("-2")){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(firmType != null && firmType.trim().length()>0 && !firmType.trim().equalsIgnoreCase("-2")){
	filter += " and mf.firmtype='"+firmType.trim()+"' ";
}
if(capitalType != null && capitalType.trim().length()>0 && !"-2".equals(capitalType)){
	filter += " and fbc.type="+capitalType;
}
if(capitalStatus != null && capitalStatus.trim().length()>0 && !"-2".equals(capitalStatus)){
	if(capitalStatus.equals("2")){
		filter += " and fbc.status not in ("+ProcConstants.statusSuccess+","+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") ";
	}else{
		filter += " and fbc.status="+capitalStatus;
	}
}
if(s_time != null && s_time.trim().length()>0){
	filter += " and trunc(createtime)>=to_date('"+s_time+"','yyyy-MM-dd') ";
}
if(e_time != null && e_time.trim().length()>0){
	filter += " and trunc(createtime)<=to_date('"+e_time+"','yyyy-MM-dd') ";
}
filter += " order by createtime,id";
System.out.println(filter);
Vector moneyList = dao.getCapitalInfoList2(filter);
//System.out.println(moneyList.size());
double allMoney = 0;
for(int i = 0 ; i <moneyList.size();i++){
	CapitalValue money = (CapitalValue)moneyList.get(i);
	allMoney += money.money;
}
int maxpage = moneyList.size()%pageSize==0 ? moneyList.size()/pageSize : moneyList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);
Vector bankList = dao.getBankList(" and validFlag=0 ");
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>
	<!-- <script language="javascript" src="<%//=request.getContextPath()%>/bankInterface/lib/validate.js"></script> -->
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
    <title>ƾ֤�б�</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag">
		<div id="main_body">
			<table border="0" cellspacing="0" class="table1_style"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthcdmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed;">
													<tr>
														<td class="table3_td_1" align="left">
															�����˺�:&nbsp;
															<label>
																<input type="text"
																	class="input_text" name="firmID" value="<%//=firmID%>" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<select name="bankID" class="normal" style="width:120px">
																	<OPTION value="">��ѡ��</OPTION>
															   <%
																for(int i=0;i<bankList.size();i++) {
																	BankValue bv = (BankValue)bankList.get(i);
																	%>
																	<option value="<%=bv.bankID%>" <%//=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
																	<%
																}
																%>
																</select>
															</label>
														</td>
                                                        <td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select name="capitalStatus" class="normal" style="width: 100px">
																	<OPTION value="">ȫ��</OPTION>
																	<option value="<%=ProcConstants.statusSuccess%>" <%//=(""+ProcConstants.statusSuccess).equals(capitalStatus)?"selected":""%>>�ɹ�</option>
																	<option value="<%=ProcConstants.statusFailure%>" <%//=(""+ProcConstants.statusFailure).equals(capitalStatus)?"selected":""%>>ʧ��</option>
																	<option value="<%=ProcConstants.statusBankProcessing%>" <%//=(""+ProcConstants.statusBankProcessing).equals(capitalStatus)?"selected":""%>>������</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															ת������:&nbsp;
															<label>
																<select name="capitalType" class="normal" style="width: 100px">
							                                     <OPTION value="">ȫ��</OPTION>
							                                     <option value="<%=ProcConstants.inMoneyType%>" <%//=(""+ProcConstants.inMoneyType).equals(capitalType)?"selected":""%>>���</option>
              				                                     <option value="<%=ProcConstants.outMoneyType%>" <%//=(""+ProcConstants.outMoneyType).equals(capitalType)?"selected":""%>>����</option>
						                                       </select>
															</label>
														</td>
													</tr>
													<tr>
									                    
														
														 <td class="table3_td_1mid" align="left">
															�����˺�����:&nbsp;
															<label>
																<select name="firmType" class="normal" style="width: 100px">
							                                     <OPTION value="">ȫ��</OPTION>
							                                     <option value="C" <%//=firmType.equals("C")?"selected":""%>>�ͻ�</option>
              				                                     <option value="M" <%//=firmType.equals("M")?"selected":""%>>�ۺϻ�Ա</option>
              				                                     <option value="S" <%//=firmType.equals("S")?"selected":""%>>�ر��Ա</option>
						                                       </select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width:120px" class="wdate" maxlength="10" name="s_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1" align="left">
														         ��ֹ����:&nbsp;
																<input type="text" style="width:100px" class="wdate" maxlength="10" name="e_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1mid" align="left">
															<button class="btn_sec" onclick="doQuery();">
																��ѯ
															</button>&nbsp;
															<button class="btn_sec" onclick="ConvertToExcel('tableList',0,1)">
																����
															</button>
														     &nbsp;
															<button class="btn_cz" onclick="frm.reset();">
																����
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
						</div>
						<div class="div_list" style="overflow-x:scroll;">
				<table style="table-layout:fixed;" id="tableList" border="0" cellspacing="0" cellpadding="0" width="150%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">ת��ʱ��</td>
				<td class="panel_tHead_MB" align="center">��������ˮ��</td>
				<td class="panel_tHead_MB" align="center">������ˮ��</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_MB" align="center">�ͻ����Ա���</td>
				<td class="panel_tHead_MB" align="center">�˺�����</td>
				<td class="panel_tHead_MB" align="center">�����˻�����</td>
				<td class="panel_tHead_MB" align="center">�����ʽ��˺�</td>
				<td class="panel_tHead_MB" align="center">ת�˽��</td>
				<td class="panel_tHead_MB" align="center">ת������</td>
				<td class="panel_tHead_MB" align="center">����״̬</td>
				<td class="panel_tHead_MB_last" align="center">��ע</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			double ins = 0;//���ɹ�
			int insn = 0;
			double ini = 0;//�������
			int inin = 0;
			double inf = 0;//���ʧ��
			int infn = 0;
			double ous = 0;//����ɹ�
			int ousn = 0;
			double oui = 0;//��������
			int ouin = 0;
			double ouf = 0;//����ʧ��
			int oufn = 0;
			for(int i=0;i<obj.getCurNum();i++){
				CapitalValue money = (CapitalValue)obj.get(i);
				if(money.type==ProcConstants.inMoneyType){
					if(money.status==ProcConstants.statusSuccess){
						ins+=money.money;
						insn++;
					}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusFailure){
						inf+=money.money;
						infn++;
					}else{
						ini+=money.money;
						inin++;
					}
				}else if(money.type==ProcConstants.outMoneyType){
					if(money.status==ProcConstants.statusSuccess){
						ous += money.money;
						ousn++;
					}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusFailure){
						ouf += money.money;
						oufn++;
					}else{
						oui += money.money;
						ouin++;
					}
				}
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.actionID%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.funID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if("C".equals(money.firmType)) {
							out.print("�ͻ�");
						}else if("M".equals(money.firmType)){
							out.print("��Ա");
						}else if("S".equals(money.firmType)){
							out.print("�ر��Ա");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.accountName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.account)%>&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.type==ProcConstants.inMoneyType){
							out.print("���");
						}else if(money.type==ProcConstants.outMoneyType){
							out.print("����");
						}else if(money.type==ProcConstants.inMoneyBlunt){
							out.print("������");
						}else if(money.type==ProcConstants.outMoneyBlunt){
							out.print("�������");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.status==ProcConstants.statusSuccess){
							out.print("�ɹ�");
						}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusBlunt){
							out.print("ʧ��");
						}else{
							out.print("������");
						}
					%>&nbsp;</td>
					<td class="underLine" title="<%=Tool.delNull(money.note)%>" align="center"><%=Tool.delNull(money.note).length()>5 ? Tool.delNull(money.note).substring(0,4):Tool.delNull(money.note)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">��ǰ�����&nbsp;</td>
				<td class="underLine" align="left">�ɹ��ϼƹ�<%=insn+ousn%>��</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtDouble2(ins)%>(Ԫ)&nbsp;<%=insn%>��</td>
				<td class="underLine" align="left" colspan="3">���𹲼ƣ�<%=Tool.fmtDouble2(ous)%>(Ԫ)&nbsp;<%=ousn%>��</td>
				<td class="underLine" align="left" colspan="3">������ֵ��<%=Tool.fmtDouble2(ins-ous)%>(Ԫ)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine" align="left">ʧ�ܺϼƹ�<%=infn+oufn%>��</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtDouble2(inf)%>(Ԫ)&nbsp;<%=infn%>��</td>
				<td class="underLine" align="left" colspan="3">���𹲼ƣ�<%=Tool.fmtDouble2(ouf)%>(Ԫ)&nbsp;<%=oufn%>��</td>
				<td class="underLine" align="left" colspan="3">������ֵ��<%=Tool.fmtDouble2(inf-ouf)%>(Ԫ)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine" align="left">�����кϼƹ�<%=inin+ouin%>��</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtDouble2(ini)%>(Ԫ)&nbsp;<%=inin%>��</td>
				<td class="underLine" align="left" colspan="3">���𹲼ƣ�<%=Tool.fmtDouble2(oui)%>(Ԫ)&nbsp;<%=ouin%>��</td>
				<td class="underLine" align="left" colspan="3">������ֵ��<%=Tool.fmtDouble2(ini-oui)%>(Ԫ)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="12">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="12" align=right>
				��<%=pageIndex%>/<%=obj.getPageCount()%>ҳ &nbsp;&nbsp;��<%=obj.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageIndex != 1) {
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}
				if(pageIndex != obj.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="12"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
							</form>
  </body>
</html>
<script>
	frm.firmID.value="<%=firmID%>";
	frm.bankID.value="<%=bankID%>";
	frm.firmType.value="<%=firmType%>";
	frm.capitalType.value="<%=capitalType%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
	frm.capitalStatus.value="<%=capitalStatus%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function pgTurn(i) {
	frm.submitFlag.value="do";
	if(i == 0) {
		frm.pageIndex.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex.value = <%=obj.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function doQuery()
{
	frm.submitFlag.value="do";
	var firmID = frm.firmID.value;
	var s_time = frm.s_time.value;
	var e_time = frm.e_time.value;
	var now1 = new Date()+"";
	if(!ismyStr(firmID)){
		alert("��������Ϣ�Ƿ��ַ�");
		frm.firmID.focus();
	} else if(s_time != "" && !CompareDate(s_time,now1)){
		alert("��ʼ���ڲ��Ϸ���������(yyyy-MM-dd)��ʽ����");
		frm.s_time.value="";
	} else if(e_time != "" && !CompareDate(e_time,now1)){
		alert("�������ڲ��Ϸ���������(yyyy-MM-dd)��ʽ����");
		frm.s_time.value="";
	} else if(e_time != "" && !CompareDate(s_time,e_time)){
		alert("����Ŀ�ʼ���ڲ��ܳ�����������");
		frm.s_time.value="";
		frm.e_time.value="";
	} else{
		frm.submit();
		frm.pageIndex.value = 1;
	}
}

function ismyStr(str){
	var patrn=/^\w*$/;
	if (patrn.exec(str)) {
		return true ;
	}
	return false ;
}
function pgJumpChk()
{
	frm.submitFlag.value="do";
	if(event.keyCode == 13)
	{
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
		{
			alert("������1 - <%=obj.getPageCount()%>������֣�");			
		}
		else
		{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
/**
 * ��table�е����ݷ��õ�Excel�����ȥ
 * tableID ����ID��
 * rowStart �ӵڼ��п�ʼ
 * colStart �ӵڼ��п�ʼ
 */
function ConvertToExcel(tableID, rowStart ,colStart){
	var xlApp,myWorkbook,myWordsheet,myHTMLTableCell,myExcelCell,myExcelCell2;
	var myCellColSpan,myCellRowSpan;
	try{
		xlApp=new ActiveXObject("Excel.Application");//����Excel����
	}catch(e){
		alert("�޷�����Excel!\n\n"+e.message+"\n\n�����ȷ�����ĵ������Ѿ���װ��Excel����ô�����IE�İ�ȫ����");
		return false;
	}
	xlApp.Visible=true;//����Excel����ɼ�
	myWorkbook=xlApp.Workbooks.Add();//��ȡWorkBook����
	xlApp.DisplayAlerts=false;//��Excel�򿪺����Ϲر�
	myWorkbook.Worksheets(3).Delete();
	myWorkbook.Worksheets(2).Delete();
	xlApp.DisplayAlerts=true;//��Excel�򿪺󲻹ر�
	myWorksheet=myWorkbook.ActiveSheet;
	var obj = document.all.tags("table");//��ȡ��ǰҳ�����еı��
	var RealColCount;  //ʵ��������ͨ��HTML���ĵڶ��еõ���
	RealColCount=0;
	for(x=0;x<obj.length;x++) {//����ȡ�õ���Щ���
		if(obj[x].id==tableID) {//�������ID�ŵ��ڴ����ID��
			myWorksheet.Cells.NumberFormatLocal="@";//���õ�Ԫ���ʽΪ�ı�
			for (i=rowStart; i<obj[x].rows.length-4; i++) {//����������ÿһ��
				for (j=colStart; j<obj[x].rows(i).cells.length-1; j++){//��������ÿһ��
					myExcelCell=myWorksheet.Cells(i+1-rowStart,j+1-colStart);//��ȡ���Excel����ʮ�ֽ����λ�ñ��
					myHTMLTableCell=obj[x].rows(i).cells(j);//ȡ��ҳ��table�����ʮ�ֽ����е�<td>
					if(i==rowStart){
						myExcelCell.Borders.Weight = 1;//�������б߿�
						myExcelCell.Interior.ColorIndex = 6;//��������Ϊ��ɫΪ��ɫ
					}
					myExcelCell.HorizontalAlignment = 3;//���õ�Ԫ�������ʾ
					myExcelCell.Value = myHTMLTableCell.innerText;//�����ʮ�ֽ���λ��<td>��ֵ�ŵ�Excel����ʮ�ֽ���λ��
					myCellColSpan = myHTMLTableCell.colSpan;//ȡ�õ�ǰ��Ԫ��ռ���˶�����
					myCellRowSpan = myHTMLTableCell.rowSpan;//ȡ�õ�ǰ��Ԫ��ռ���˶�����
					//���ǵ�һ�У����кϲ���Ԫ������
					if((i>=rowStart)&&(myCellColSpan*myCellRowSpan>1)){
						myExcelCell2=myWorksheet.Cells(i+1-rowStart+(myCellRowSpan-1),j+1-colStart+(myCellColSpan-1));
						myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
					}
					if(i==rowStart+1){
						RealColCount=RealColCount+myCellColSpan;
					}
				}
			}
			//����б���������һ����Ч�еĵ�Ԫ������Ϊ1��������������ĺϲ����
			if((obj[x].rows(rowStart).cells.length==1)&&(RealColCount>0)){
				myExcelCell=myWorksheet.Cells(1,1);
				myExcelCell2=myWorksheet.Cells(1,RealColCount);
				myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
			}
		}
	}
	myWorksheet.Columns("A:IV").AutoFit;
}
//-->
</SCRIPT>
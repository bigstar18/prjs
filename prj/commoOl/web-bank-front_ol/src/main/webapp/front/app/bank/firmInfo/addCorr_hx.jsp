<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��������ǩԼ��Ϣ</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>		
		
		
	<script>
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//ִ��%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("��ȷ��Ҫִ�д˲�����")){
				$("#subfrm").submit();
				}
				
				}
			});
	    });
		function AccountPropChange(){
		var AccountProp = document.getElementsByName("AccountProp");
		var strNew;
		for(var i=0;i<AccountProp.length;i++){
			if(AccountProp.item(i).checked){
				strNew = AccountProp.item(i).getAttribute("value");  
				break;
			}else{
				continue;
			}
		}
		if(strNew == "1"){
			document.getElementById('TrForCompany').style.display = "none";
		}else if(strNew == "0"){
			document.getElementById('TrForCompany').style.display = "block";
		}else{
			alert("δ֪����");
		}
		}
		function NoteFlagChange(){
		var NoteFlag = document.getElementsByName("NoteFlag");
		var str;
		for(var i=0;i<NoteFlag.length;i++){
			if(NoteFlag.item(i).checked){
				str = NoteFlag.item(i).getAttribute("value");  
				break;
			}else{
				continue;
			}
		}
		if(str == "0"){
			document.getElementById('Phone').style.display = "none";
		}else if(str == "1"){
			document.getElementById('Phone').style.display = "block";
		}else{
			alert("δ֪����");
		}
		}
		
	  function showMsgBoxCallbak(result,msg){<%//�ص�����%>
			$("#frm").submit();
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/firmInfo/gotoRgstDelFirmInfoPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ:
				</div>
				<div class="content">�ڴ������Խ��л�������ǩԼ������</div>
			</div>
			<div class="clear"></div>
			<div id="u24" class="u24"  >						
			<div id="message" class="form margin_10b">			
						
				<form id="subfrm" name="subfrm" action="${basePath}/bank/firmInfo/gotoRgstHX.action" method="post" >
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
					<tr>
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">							
							<input name="firmID" type=text readonly value=${firmIDAndAccount.firm.firmID}>
							<input name="bankID" type=hidden readonly value=${firmIDAndAccount.bank.bankID}>						
					</td>
				</tr>
				<tr >
					<td align="right">���˻����ƣ�&nbsp;</td>
					<td align="left">
						<input name="accountName1" id="accountName1" type=text class="validate[required,maxSize[10]]"style="width: 150px"><span>*</span>				
					</td>
				</tr>
				<tr>
					<td align="right">�˻����ͣ�&nbsp;</td>
					<td align="left">
						<input onclick="AccountPropChange()" type = "radio" value = "1" name="AccountProp" id="AccountProp" />����
						<input onclick="AccountPropChange()" type = "radio" value = "0" name="AccountProp" id="AccountProp" CHECKED />��ҵ
					</td>
				</tr>
				<tr >
					<td align="right">�����˺�(����)��&nbsp;</td>
					<td align="left">
					
						<input name="RelatingAcct" id="RelatingAcct" value="" type=text  class="validate[required,maxSize[32]]" style="width: 150px"><span>*</span>	
					
					</td>
				</tr>
				
				<tr >
					<td align="right">�����˻�����&nbsp;</td>
					<td align="left">
					
						<input name="RelatingAcctName" id="RelatingAcctName" readonly value=${firmIDAndAccount.firm.name} type=text class="validate[required,maxSize[30]]" style="width: 150px"><span>*</span>
						
					</td>
				</tr>
				<tr >
					<td align="right">�����У�&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBank" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="RelatingAcctBank" id="RelatingAcctBanks" style="width: 150px" >
							<option value="0" selected>--��ѡ��--</option>
							<option value="��������">�й���������</option>
							<option value="��������">�й���������</option>
							<option value="ũҵ����">�й�ũҵ����</option>
							<option value="�й�����">�й�����</option>
							<option value="��������">�й���������</option>
							<option value="ũҵ��չ����">�й�ũҵ��չ����</option>
							<option value="��ͨ����">��ͨ����</option>
							<option value="��������">��������</option>
							<option value="�������">�������</option>
							<option value="��������">��������</option>
							<option value="��������">��������</option>
							<option value="�㶫��չ����">�㶫��չ����</option>
							<option value="���ڷ�չ����">���ڷ�չ����</option>
							<option value="��������">��������</option>
							<option value="��ҵ����">��ҵ����</option>
							<option value="�ֶ���չ����">�Ϻ��ֶ���չ����</option>
							<option value="��������">��������</option>
							<option value="ũ����ҵ����">ũ����ҵ����</option>
							<option value="ũ��������">ũ��������</option>
							<option value="����������">����������</option>
							<option value="������������">�й�������������</option>
						</select>
					</td>
				</tr>
				
				
				<tr>
					<td align="right">�����е�ַ��&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankAddr" id="RelatingAcctBankAddr"  value="" type=text  class="validate[required]"  style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr >
					<td align="right">�������кţ�&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctBankCode" id="RelatingAcctBankCode" value="" type=text  class="validate[required,custom[onlyNumberSp],maxSize[12]]" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="padding-left:148px" id="TrForCompany" style="display: block;">
							<table border="0">
								<tr >
									<td align="right">��ϵ�ˣ�&nbsp;</td>
									<td align="left">
										<input name="PersonName" id="PersonName" type=text  class="validate[required,maxSize[50]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr >
									<td align="right">�칫�绰��&nbsp;</td>
									<td align="left">
										<input name="OfficeTel" id="OfficeTel" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right" >�ƶ��绰��&nbsp;</td>
									<td align="left">
										<input name="MobileTel" id="MobileTel" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr >
									<td align="right">��ϵ��ַ��&nbsp;</td>
									<td align="left">
										<input name="Addr" id="Addr" type=text  class="validate[required,maxSize[30]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr >
									<td align="right">�������룺&nbsp;</td>
									<td align="left">
										<input name="ZipCode" id="ZipCode" type=text  class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr>
									<td align="right">����������&nbsp;</td>
									<td align="left">
										<input name="LawName" id="LawName" type=text class="validate[required,maxSize[20]]" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr >
					<td align="right">�Ƿ���Ҫ����֪ͨ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="NoteFlag" CHECKED onclick="NoteFlagChange()"/>��Ҫ
						<input type = "radio" value = "0" name="NoteFlag" onclick="NoteFlagChange()"/>����Ҫ
					</td>
				</tr>
				<tr id="Phone">
					<td align="right" >����֪ͨ�ֻ��ţ�&nbsp;</td>
					<td align="left">
						<input name="NotePhone"  id="NotePhone" value="" type=text   class="validate[required,maxSize[20]]" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr >
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="eMail" value="" type=text  class="text" maxlength="20" style="width: 140px"><font color="red">&nbsp;</font>
					</td>
				</tr>
				<tr >
					<td align="right">���˱�ʶ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="CheckFlag" CHECKED/>��Ҫ
						<input type = "radio" value = "0" name="CheckFlag" />����Ҫ
					</td>
				</tr>
					
			</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">
								<input type="button" id="performBtn"  value="ȷ��" class="btbg"/>
							</span>
						</label>
					</div>
					
				
				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
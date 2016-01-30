<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="gnnt.MEBS.integrated.mgr.model.usermanage.MFirmApply,
					javax.servlet.ServletOutputStream,java.io.* " %>

<html>
	<head>
		<title>入市协议书</title>
		<meta http-equiv="Pragma" content="no-cache">
		<base target="_self">
	</head>
	<body>
	<a name="firstAnchor"></a> 
		<div id="printDiv" style="margin-left:15px;margin-right:15px;margin-top:30px;margin-bottom:20px;">
<div align="center"><font size="4" color="#e73a49">长三脚商品交易所现货挂牌交易商入市协议</font></div>
<br/><strong style="color:#e73a49">尊敬的交易商：</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;甲方：长三角商品交易所有限公司
<br/>&nbsp;&nbsp;&nbsp;&nbsp;乙方：${entity.fullName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（交易账号：${entity.applyID}）
<br/>&nbsp;&nbsp;&nbsp;&nbsp;丙方：＿＿＿＿＿
<br/>&nbsp;&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》和相关法律、法规，甲、乙、丙三方经过平等协商，就乙方参与甲方主持下的商品交易、丙方代理和协助甲方为乙方提供甲方授权范围内的服务等有关事项订立本协议。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第一条 丙方得到甲方的授权，为乙方提供开户指导、培训以及信息服务，协助甲方进行风险控制。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第二条 在签署本协议前，乙方已仔细阅读、理解并认可了《风险告知书》及《交易商须知》 。 《风险告知书》及《交易商须知》经乙方签署后作为本协议的附件，系本协议不可分割的组成部分。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第三条 甲、乙双方一致确认并同意，甲方即长三角商品交易所的相关规则中有关条款赋予甲方的权利是为确保交易正常进行和控制风险所必须的条件。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<u>乙方自愿接受甲方的管理和监督检查。乙方承诺，如乙方在交易中出现违反相关规则规定的情形，乙方同意甲方根据相关规则进行处理（包括但不限于实行“代为转让”等措施） ，并自愿承担因此产生的损失。</u>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第四条 乙方承诺，其有足够充分的能力，全面、及时、适当的履行商品购销义务。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第五条 乙方自愿采用甲方提供的电子交易平台进行商品交易，甲方为乙方提供交易系统、交易商位及其他有关商品交易、交收的设施和服务，并为乙方代收、代付有关款项。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第六条 乙方应按照甲方相关规则的规定通过丙方向甲方申请交易代码，并设置、保存和修改交易密码。由于乙方交易密码管理不善或非因甲方原因造成的损失，应由乙方承担。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第七条 <u> 甲方有权根据实际情况对相关规定进行修订并及时在官方网站予以公布，乙方对此予以认可，并承诺及时关注甲方官方网站和遵照修订后的规则执行。甲乙双方确认甲方相关规则构成本协议的有效组成部分，乙方应及时了解甲方最新的、适用的相关规则，若乙方对相关规则有任何疑问，应及时联系甲方相关人员予以解答；任何因乙方未及时了解最新相关规则或对相关规则的误解而导致的责任，由乙方自行承担。</u>>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第八条 乙方应严格按照相关规则履行义务，包括但不限于交易资金、贷款及其他费用的支付、结算和货物交收等。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第九条 本协议履行过程中，如果相关法律、法规、规章、政策及甲方相关规则发生变化，甲方有权依照上述变化直接变更或补充本协议相关条款，变更或补充条款优先适用。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<u>根据上述情况的变化，甲方有权对本协议相关条款进行的变更或补充，一经变更或补充以约定的通知方式或在甲方营业场所、 网站公告等方式向乙方发出，则该等变更或补充于相关通知到达乙方或公告发出之时即行生效。</u>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<u>若乙方不同意甲方根据本条做出的变更或补充， 可书面通知甲方或丙方终止本协议；甲方或丙方在收到乙方通知后五个工作日内为乙方办理销户手续。</u>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十条 因不可抗力导致的系统故障、设备故障、通讯故障、电力故障等突发事故给乙方造成的损失，甲方不承担责任。因上述事故造成交易或交易数据中断，恢复交易时以故障发生前系统最终记录的交易数据为有效数据。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十一条 不可抗力是指双方不能预见、不能避免且不能克服的客观情况，包括但不限于火灾、爆炸、台风、洪水、地震、潮汐、雷电、战争、供电、电信及通信设备中断、罢工、政府管制、法规政策变更、交易所暂停交易、交易所交易规则中规定的交易异常、设备故障、通讯故障、互联网系统故障及通过互联网未经许可的存取、盗窃交易敏感性数据、他人恶意地对网站攻击、计算机病毒及非可归因于任何一方的信息传递中断。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十二条 甲方和丙方向乙方提供的各种信息及资料仅作为交易参考，乙方据此进行交易的，风险自担。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十三条 乙方不继续参加交易。应通过丙方向甲方提出书面申请，在办理相关手续、结算款项和相关费用后本协议终止。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十四条 因本协议引起的一切争议，由甲、乙、丙三方协商解决，如协商不成或不愿协商的，同意将争议向江苏省无锡市滨湖区人民法院提起诉讼。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十五条 本协议在甲、乙、丙三方签署（自然人当事人签字、法人当事人加盖公章或合同章、甲方加盖开户专用章）后生效。<u> 若本协议在网上开户流程中使用电子签约方式，则本协议经甲乙丙三方在网上开户系统中点击确认后生效。</u>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十六条 本协议未尽事宜，甲、乙、丙三方一致同意按照甲方相关规则执行。甲方相关规则是本协议不可分割的组成部分，两者具有同等法律效力。除本协议有明确约定或三方签署确认外，对本协议的任何手写修改均为无效。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;第十七条 本协议壹式叁份，甲、乙、丙三方各持一份，具有同等效力。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<u>本人/ 企业 已认真阅读 《交易商须知》 、 《风险告知书》及 《交易商入市协议》并完全理解和同意，自愿承担交易中的风险及由此带来的一切可能的损失。</u>
<br/>交易商抄写：＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿<u>（请抄写以上划线部分，乙方在网上开户系统中点击确认即视为乙方抄写以上部分）</u>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<br/>甲方：长三角商品交易所有限公司（盖章）
<br/>授权代表：
<br/>
<br/>乙方：
<br/>法人交易商（盖章） ：
<br/>授权代表（签名） ：
<br/>自然人交易商（签字） ：
<br/>
<br/>丙方（盖章） ：
<br/>授权代表：
<br/>签订日期：
		</div>
		<div id=noPrint align="right" style="margin-right:10px;margin-bottom:5px;">
			<input type="button" value="print" onClick="myPrint()"/>
		</div>
	</body>
	<script  language="javascript">
		window.onload=function(){
			window.location.hash = firstAnchor;
		}
		function myPrint(){
			document.getElementById("noPrint").style.display="none";
			window.print();
			document.getElementById("noPrint").style.display="";
		}
	</script>
</html>

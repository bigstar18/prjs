<%@ page contentType="text/html;charset=GBK"%>
<html><head>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="${publicPath }/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${publicPath}/js/PasswordMode.js"></script>
</head>
<body>
<a name="firstAnchor"></a> 
<div id="print" style="margin-left:15px;margin-right:15px;margin-top:30px;margin-bottom:20px;border:2px dashed #F4A460;">
<div align="center"><font size="4" color="#e73a49">交易商须知</font></div>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">一、 交易商的开户条件</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;合格的法人交易商应是具有法人资格或相应经济组织资格、能独立从事大宗商品交易活动并独立承担法律责任的经济组织。合格的自然人交易商须是年满十八周岁，具有完全民事行为能力且具有一定的积极承受能力、风险控制能力和交易商品相关知识的公民。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;交易商须以真实、有效的身份开户。交易商须保证资金来源的合法性及所提供资料的真实性、合法性、有效性。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">二、开户文件的签署</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;合格的自然人交易商开户，必须由交易商本人亲自签署开户文件。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;法人交易商开户，可由其法定代表人（负责人）或委托代理人签署开户文件，但必须在开户文件上加盖法人交易商公章或合同专用章。委托代理人办理开户手续的，法人交易商应提供加盖机构公章并经法定代人（负责人）签名的真实、合法、有效的《授权委托书》原件。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">三、 交易商 开户</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;（一）知晓商品交易风险
<br/>&nbsp;&nbsp;&nbsp;&nbsp;交易商应知晓从事商品交易具有风险，应仔细阅读《风险告知书》并签字确认。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;（二）交易所的授权服务机构(会员)不得接受交易商的全权委托。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;交易商应知晓授权服务机构(会员)及其工作人员不得接受交易商的全权委托，交易商也不得要求授权服务机构(会员)或其工作人员以全权委托的方式进行大宗商品交易。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;（三）授权服务机构(会员)公示网址
<br/>&nbsp;&nbsp;&nbsp;&nbsp;有关长三角商品交易所授权服务机构(会员)的信息可以通过官方网（www.yrdce.cn）的公示信息进行查询和核实。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;（四）知晓授权服务机构(会员)的有关规定
<br/>&nbsp;&nbsp;&nbsp;&nbsp;交易商应知晓只有得到交易所授权、取得相应资格的授权服务机构(会员)及其分支机构才能根据《长三角商品交易所现货挂牌授权服务机构(会员)管理办法(暂行)》代理和协助交易所从事相关业务。
</div>
<div id="noprint" align="right" style="margin-right:10px;margin-bottom:5px;"><input type="button" value="打印" onclick="myPrint()"/></div>
<br/>
</body>
<SCRIPT type="text/javascript">
	window.onload=function(){
		window.location.hash = firstAnchor;
	}
	function myPrint(){
		document.getElementById("noprint").style.display="none";
		window.print();
		document.getElementById("noprint").style.display="";
	}
</SCRIPT>
</html>
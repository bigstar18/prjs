<%@ page contentType="text/html;charset=GBK"%>
<%
request.setAttribute("pageTitle","注册成功");
%>
<jsp:include page="header.jsp" flush="false"></jsp:include>
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<!-------------------------State Begin------------------------->
<div class="clear"></div>
<div class="stateBg state">
  <div class="step2">注册步骤：　1.填写注册信息&hellip;&hellip;　　<span>2.注册成功</span></div>
</div>
<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<div class="bodyStep4">
	<div class="title">恭喜您！注册成功啦！</div>
	<div class="center font_black_14">欢迎加入<%=Global.getMarketInfoMap().get("marketName") %>现货交易平台——中国最具有影响力的大宗现货B2B电子商务平台！</div>
	<div class="border_gray_b"></div>
	<div class="content" style="display: none;">
		<span class="font_orange_14b ">填写以下信息发布公司介绍，我们将为您更精准的推荐与您相关的信息！</span>

		<div class="div">
			<label for="trade">主营行业：</label>
			<select name="trade1" class="trade">
				<option value="a">数码、电脑</option>
			</select>
			<select name="trade2" class="trade2">
				<option value="a">电脑借口线</option>
			</select>
			<select name="trade3" class="trade3">
				<option value="a">请选择</option>
			</select>
			<div class="clear"></div>
		</div>
		<div class="div">
			<div class="div2">
				<label for="intro">公司介绍：</label>
				<textarea name="textarea" class="intro" class="validate[maxSize[<fmt:message key="remark" bundle="${proplength}" />]]" ></textarea>
			</div>
			<div class="help">温馨提示：<br />
				1.您可从贵公司成立历史、主营产品、品牌、服务等优势方面进行描述。<br />
				2.限50-1200字。
			</div>
			<div class="clear"></div>
		</div>
		<div class="div center"><input type="button" value="确认提交" /></div>
		<div class="clear"></div>
 	</div>
	<div class="border_gray_b"></div>
	<div class="content" style="display: none;">
	  <span class="font_orange_14b">填写以上信息，您将免费在行业中进行宣传！</span><br />
		<span class="font_black_12">免费宣传产品：让1100万买家轻松找到您的产品，买家主动找上门！</span><br />
		<div class="font_blue_12">
			<ul>
				<li>·<a href="#" class="link_blue2_12">如何在<%=Global.getMarketInfoMap().get("marketName") %>现货平台宣传产品？</a></li>
				<li>·<a href="#" class="link_blue2_12">宣传产品有什么诀窍吗？</a></li>
				<li>·<a href="#" class="link_blue2_12">我该如何查找商机？</a></li>
			</ul>
		</div>
		<div class="right"><a href="#" class="move"><img src="${skinPath}/image/logon/done_ad.jpg" border="0" /></a></div>
		<div class="clear"></div>
	</div>
</div>
<!-------------------------Body End------------------------->
<jsp:include page="/front/frame/footer.jsp" flush="false"></jsp:include>
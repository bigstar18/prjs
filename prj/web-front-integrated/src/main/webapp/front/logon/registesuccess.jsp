<%@ page contentType="text/html;charset=GBK"%>
<%
request.setAttribute("pageTitle","ע��ɹ�");
%>
<jsp:include page="header.jsp" flush="false"></jsp:include>
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<!-------------------------State Begin------------------------->
<div class="clear"></div>
<div class="stateBg state">
  <div class="step2">ע�Ჽ�裺��1.��дע����Ϣ&hellip;&hellip;����<span>2.ע��ɹ�</span></div>
</div>
<!-------------------------State End------------------------->
<!-------------------------Body Begin------------------------->
<div class="clear"></div>
<div class="bodyStep4">
	<div class="title">��ϲ����ע��ɹ�����</div>
	<div class="center font_black_14">��ӭ����<%=Global.getMarketInfoMap().get("marketName") %>�ֻ�����ƽ̨�����й������Ӱ�����Ĵ����ֻ�B2B��������ƽ̨��</div>
	<div class="border_gray_b"></div>
	<div class="content" style="display: none;">
		<span class="font_orange_14b ">��д������Ϣ������˾���ܣ����ǽ�Ϊ������׼���Ƽ�������ص���Ϣ��</span>

		<div class="div">
			<label for="trade">��Ӫ��ҵ��</label>
			<select name="trade1" class="trade">
				<option value="a">���롢����</option>
			</select>
			<select name="trade2" class="trade2">
				<option value="a">���Խ����</option>
			</select>
			<select name="trade3" class="trade3">
				<option value="a">��ѡ��</option>
			</select>
			<div class="clear"></div>
		</div>
		<div class="div">
			<div class="div2">
				<label for="intro">��˾���ܣ�</label>
				<textarea name="textarea" class="intro" class="validate[maxSize[<fmt:message key="remark" bundle="${proplength}" />]]" ></textarea>
			</div>
			<div class="help">��ܰ��ʾ��<br />
				1.���ɴӹ�˾������ʷ����Ӫ��Ʒ��Ʒ�ơ���������Ʒ������������<br />
				2.��50-1200�֡�
			</div>
			<div class="clear"></div>
		</div>
		<div class="div center"><input type="button" value="ȷ���ύ" /></div>
		<div class="clear"></div>
 	</div>
	<div class="border_gray_b"></div>
	<div class="content" style="display: none;">
	  <span class="font_orange_14b">��д������Ϣ�������������ҵ�н���������</span><br />
		<span class="font_black_12">���������Ʒ����1100����������ҵ����Ĳ�Ʒ��������������ţ�</span><br />
		<div class="font_blue_12">
			<ul>
				<li>��<a href="#" class="link_blue2_12">�����<%=Global.getMarketInfoMap().get("marketName") %>�ֻ�ƽ̨������Ʒ��</a></li>
				<li>��<a href="#" class="link_blue2_12">������Ʒ��ʲô������</a></li>
				<li>��<a href="#" class="link_blue2_12">�Ҹ���β����̻���</a></li>
			</ul>
		</div>
		<div class="right"><a href="#" class="move"><img src="${skinPath}/image/logon/done_ad.jpg" border="0" /></a></div>
		<div class="clear"></div>
	</div>
</div>
<!-------------------------Body End------------------------->
<jsp:include page="/front/frame/footer.jsp" flush="false"></jsp:include>
<%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css"> 

body{ text-align:center; margin:0 auto;} 
.waicheng { margin:5px auto;  border:1px solid #000000;} 
p{ font-size:13px;font-family:"宋体"}
div{ font-size:13px; font-family:"宋体";}
.yam{ border-left-color:#CCCCCC; border-right-color:#CCCCCC; border-top-color:#CCCCCC}

</style> 

</head>

<body><div class="waicheng" style="width:80%; " >
<form name="myForm" action="/ecsideTest/ecside/getList1.action"
			method="post">
			<fieldset>
				<legend>
					<font style="font-weight: bold; color: black">查询选项</font>
				</legend>
				<table width="940">
					<tr>
						<td width="263" height="18">
							&nbsp;&nbsp;&nbsp; 机构:
							<input  name="b" onClick="return er()">
&nbsp;&nbsp;						</td>
						<td width="211">
							&nbsp; 经纪人:
							<select name="select3" style=" width:100px">
								<option selected="selected">当前</option>
								<option >历史</option>
                            </select>
&nbsp;&nbsp;						</td>
						<td width="129">&nbsp;</td>
						<td width="317" rowspan="3">
								<fieldset>
									<legend>
										<font style="font-weight: bold; color: black">显示选项</font>
									</legend>
									<table>
										<tr>
											<td width="149" height="15"><label>
											  <input type="checkbox" name="checkbox" value="checkbox" checked="checked"/>
											  平仓单
											</label></td>
											<td width="154"><input type="checkbox" name="checkbox5" value="checkbox"  checked="checked" disabled="disabled"/>
资金账户 </td>
										</tr>
										<tr>
											<td><input type="checkbox" name="checkbox2" value="checkbox" checked="checked" />
指价单 </td>
											<td><input type="checkbox" name="checkbox6" value="checkbox"  checked="checked"/>
备注 </td>
										</tr>
										<tr>
											<td><input type="checkbox" name="checkbox3" value="checkbox"  checked="checked"/>
未平仓单 </td>
											<td><input type="checkbox" name="checkbox7" value="checkbox"  checked="checked"/>
延期费 </td>
										</tr><tr>
											<td><input type="checkbox" name="checkbox4" value="checkbox"  checked="checked"/>
资金流水 </td>
											<td><input type="checkbox" name="checkbox8" value="checkbox" checked="checked" />
显示无单账户 </td>
										</tr>
									</table>
								</fieldset>
						
						</td>
					</tr>
					<tr>
					  <td height="34">&nbsp;</td>
					  <td>起始日期:                      
					    <label>
					    <select name="select" style=" width:100px" disabled="disabled">
				        </select>
				      </label></td>
					  <td>&nbsp;</td>
				  </tr>
					<tr>
						<td>&nbsp;						</td>
						<td>终止日期:
							  <select name="select2"  style=" width:100px" disabled="disabled">
                              </select>
&nbsp;&nbsp;						</td>
<td><input name="button" type="button" value="查询" />
  <br />
  <input name="button2" type="button" value="重置" /></td>
</tr>
				</table>
			</fieldset>
		</form><br /><br /><br /><br />
<form>

<h1 align="center">机构报表</h1>
<p align="right"><hr align="center" />
 <div align="right">打印日期：2011-03-30 &nbsp; &nbsp; &nbsp; &nbsp;
 </div>
 <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;名称：北京吉源通科贸有限公司</p>
<p align="right">起始日期：2011-02-02 &nbsp; &nbsp;</p>
<p align="right">终止日期：2011-03-30 &nbsp; &nbsp;</p>

<h2 align="center">指价单</h2>

<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr height="30">
    <td bgcolor="#FFCC99"><div align="center">账户</div></td>
    <td bgcolor="#FFCC99"><div align="center">下单时间</div></td>
    <td bgcolor="#FFCC99"><div align="center">中间价单号</div></td>
    <td bgcolor="#FFCC99"><div align="center">商品</div></td>
    <td bgcolor="#FFCC99"><div align="center">买/卖</div></td>
    <td bgcolor="#FFCC99"><div align="center">类型</div></td>
    <td bgcolor="#FFCC99"><div align="center">数量</div></td>
    <td bgcolor="#FFCC99"><div align="center">中间价</div></td>
    <td bgcolor="#FFCC99"><div align="center">止损价</div></td>
    <td bgcolor="#FFCC99"><div align="center">止盈价</div></td>
    <td bgcolor="#FFCC99"><div align="center">期限</div></td>
  </tr>
</table>
</form>
<table width="100%" border="0">
  <tr height="30">
    <td colspan="4"><div align="center">
      <h2>资金账户</h2>
    </div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >上日余额：</div></td>
    <td><div align="right" >0</div></td>
    <td><div align="center" >浮动盈亏：</div></td>
    <td><div align="center">0</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >今日划转：</div></td>
    <td><div align="right" >-1000000</div></td>
    <td><div align="center" >净值：</div></td>
    <td><div align="center">-1058559.7</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >交易盈亏：</div></td>
    <td><div align="right" >-26810</div></td>
    <td><div align="center" >占用保证金：</div></td>
    <td><div align="center">331956.8</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >手续费：</div></td>
    <td><div align="right" >-28987.59</div></td>
    <td><div align="center" >冻结保证金：</div></td>
    <td><div align="center">0</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >延续费：</div></td>
    <td><div align="right" >-2762.11</div></td>
    <td><div align="center" >可用保证金：</div></td>
    <td><div align="center">18609483.5</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >今日余额：</div></td>
    <td><div align="right">-1058559.7</div></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<h1>&nbsp;</h1>
<h1>&nbsp;</h1>
<h1>&nbsp;</h1>
<h1>平仓单</h1>
<table table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
<tr height="30">
	<td bgcolor="#FFCC99"><div align="center">序号</div></td>
	<td bgcolor="#FFCC99"><div align="center">账户</div></td>
	<td bgcolor="#FFCC99"><div align="center">数量</div></td>
	<td bgcolor="#FFCC99"><div align="center">商品</div></td>
	<td bgcolor="#FFCC99"><div align="center">建仓单号</div></td>
	<td bgcolor="#FFCC99"><div align="center">建仓时间</div></td>
	<td bgcolor="#FFCC99"><div align="center">买/卖</div></td>
	<td bgcolor="#FFCC99"><div align="center">建仓价</div></td>
	<td bgcolor="#FFCC99"><div align="center">持仓价</div></td>
	<td bgcolor="#FFCC99"><div align="center">平仓单号</div></td>
	<td bgcolor="#FFCC99"><div align="center">平仓时间</div></td>
	<td bgcolor="#FFCC99"><div align="center">买/卖</div></td>
	<td bgcolor="#FFCC99"><div align="center">平仓价</div></td>
	<td bgcolor="#FFCC99"><div align="center">手续费</div></td>
	<td bgcolor="#FFCC99"><div align="center">盈亏</div></td>
	</tr>
		<tr height="30">
			<td bgcolor="#FFCCCC" ><b>1</b></td>
			<td bgcolor="#FFCCCC" ><p><b>00412345678</b></p>
	      </td>
			<td bgcolor="#FFCCCC" ><b>1.00</b></td>
			<td bgcolor="#FFCCCC" ><b> 天通金</b></td>
			<td bgcolor="#FFCCCC" ><b>2667601</b></td>
			<td bgcolor="#FFCCCC" ><b>2011-3-28 12:24:33</b></td>
			<td bgcolor="#FFCCCC" ><b>卖</b></td>
			<td bgcolor="#FFCCCC" ><b>301.44</b></td>
			<td bgcolor="#FFCCCC" ><b>300.20</b></td>
			<td bgcolor="#FFCCCC" ><b>2688605</b></td>
			<td bgcolor="#FFCCCC" ><b>2011-3-29 2:54:33</b></td>
			<td bgcolor="#FFCCCC" ><b>买</b></td>
			<td bgcolor="#FFCCCC" ><b>300.15</b></td>
			<td bgcolor="#FFCCCC" ><b>-481.27</b></td>
			<td bgcolor="#FFCCCC" ><b>50.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>2</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>现货白银</b></td>
			<td ><b>2628606</b></td>
			<td ><b>2011-3-24 17:36:33</b></td>
			<td ><b>买</b></td>
			<td ><b>7955.00</b></td>
			<td ><b>7854.00</b></td>
			<td ><b>2688606</b></td>
			<td ><b>2011-3-29 2:54:55</b></td>
			<td ><b>卖</b></td>
			<td ><b>7841.00</b></td>
			<td ><b>-189.55</b></td>
			<td ><b>-195.00</b></td>
		</tr>
		<tr bgcolor="#FFCCCC" height="30">
			<td ><b>3</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>2.00</b></td>
			<td ><b>天通金</b></td>
			<td ><b>2667610</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>买</b></td>
			<td ><b>301.29</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688607</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>卖</b></td>
			<td ><b>299.91</b></td>
			<td ><b>-961.92</b></td>
			<td ><b>-580.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>4</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>天通金</b></td>
			<td ><b>2667601</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>卖</b></td>
			<td ><b>301.44</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688605</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>买</b></td>
			<td ><b>300.15</b></td>
			<td ><b>-481.27</b></td>
			<td ><b>50.00</b></td>
		</tr>
		<tr bgcolor="#FFCCCC" height="30">
			<td ><b>5</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>现货白银</b></td>
			<td bgcolor="#FFCCCC" ><b>2628606</b></td>
			<td ><b>2011-3-24 17:36:33</b></td>
			<td ><b>买</b></td>
			<td ><b>7955.00</b></td>
			<td ><b>7854.00</b></td>
			<td ><b>2688606</b></td>
			<td ><b>2011-3-29 2:54:55</b></td>
			<td ><b>卖</b></td>
			<td ><b>7841.00</b></td>
			<td ><b>-189.55</b></td>
			<td ><b>-195.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>6</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>2.00</b></td>
			<td ><b>天通金</b></td>
			<td ><b>2667610</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>买</b></td>
			<td ><b>301.29</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688607</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>卖</b></td>
			<td ><b>299.91</b></td>
			<td ><b>-961.92</b></td>
			<td ><b>-580.00</b></td>
		</tr>
	<tr height="30">
			<td ><b>&nbsp;总计</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>8.00</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>&nbsp;</b></td>
			<td ><b>-3265.48</b></td>
			<td ><b>1650.00</b></td>
	</tr>

</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<h1>延期费</h1>
<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr height="30">
    <td width="16%" bgcolor="#FFCC99"><div align="center">日期</div></td>
    <td width="16%" bgcolor="#FFCC99"><div align="center">时间</div></td>
    <td width="15%" bgcolor="#FFCC99"><div align="center">类型</div></td>
    <td width="10%" bgcolor="#FFCC99"><div align="center">关联类型</div></td>
    <td width="14%" bgcolor="#FFCC99"><div align="center">变动金额</div></td>
    <td width="18%" bgcolor="#FFCC99"><div align="center">变后金额</div></td>
    <td width="11%" bgcolor="#FFCC99"><div align="center">备注</div></td>
  </tr>
  <tr bgcolor="#FFCCCC" height="30">
  	<td ><b>2011-03-24</b></td>
			<td ><b>&nbsp;21:16:34</b></td>
			<td ><b>延期费划拨</b></td>
			<td ><b>&nbsp;2628432</b></td>
			<td ><div align="right"><b>&nbsp;-81.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.99</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr height="30">
  	<td ><b>2011-03-23</b></td>
			<td ><b>&nbsp;15:14:55</b></td>
			<td ><b>延期费划拨</b></td>
			<td ><b>&nbsp;2628412</b></td>
			<td ><div align="right"><b>&nbsp;-11.67</b></div></td>
			<td ><div align="center"><b>&nbsp;6547469.11</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr bgcolor="#FFCCCC" height="30">
  	<td ><b>2011-01-25</b></td>
			<td ><b>&nbsp;12:16:27</b></td>
			<td ><b>延期费划拨</b></td>
			<td ><b>&nbsp;2628444</b></td>
			<td ><div align="right"><b>&nbsp;-41.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.24</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr height="30">
  	<td ><b>2011-01-24</b></td>
			<td ><b>&nbsp;22:15:28</b></td>
			<td ><b>延期费划拨</b></td>
			<td ><b>&nbsp;2628634</b></td>
			<td ><div align="right"><b>&nbsp;-72.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.53</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<h1>资金流水</h1>
<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr bgcolor="#FFCCCC" height="30">
    <td width="16%"><div align="center">日期</div></td>
    <td width="16%"><div align="center">时间</div></td>
    <td width="15%"><div align="center">类型</div></td>
    <td width="10%"><div align="center">关联类型</div></td>
    <td width="14%"><div align="center">变动金额</div></td>
    <td width="18%"><div align="center">变后金额</div></td>
    <td width="11%"><div align="center">备注</div></td>
  </tr>
  <tr bgcolor="#FFCC99" height="30">
    <td ><b>2011-03-24</b></td>
    <td ><b>&nbsp;21:16:34</b></td>
    <td ><b>延期费划拨</b></td>
    <td ><b>&nbsp;2628432</b></td>
    <td ><div align="right"><b>&nbsp;-81.67</b></div></td>
    <td ><div align="center"><b>&nbsp;9997469.99</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr height="30">
    <td ><b>2011-03-23</b></td>
    <td ><b>&nbsp;15:14:55</b></td>
    <td ><b>延期费划拨</b></td>
    <td ><b>&nbsp;2628412</b></td>
    <td ><div align="right"><b>&nbsp;-11.67</b></div></td>
    <td ><div align="center"><b>&nbsp;6547469.11</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr bgcolor="#FFCC99" height="30">
    <td ><b>2011-01-25</b></td>
    <td ><b>&nbsp;12:16:27</b></td>
    <td ><b>延期费划拨</b></td>
    <td ><b>&nbsp;2628444</b></td>
    <td ><div align="right"><b>&nbsp;-41.67</b></div></td>
    <td ><div align="center"><b>&nbsp;9997469.24</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr height="30">
    <td ><b>2011-01-24</b></td>
    <td ><b>&nbsp;22:15:28</b></td>
    <td ><b>延期费划拨</b></td>
    <td ><b>&nbsp;2628634</b></td>
    <td ><div align="right"><b>&nbsp;-72.67</b></div></td>
    <td ><div align="center"><b>&nbsp;9997469.53</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
</table>
<p>&nbsp;</p>
</div>

</body>
</html>

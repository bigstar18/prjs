<%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css"> 

body{ text-align:center; margin:0 auto;} 
.waicheng { margin:5px auto;  border:1px solid #000000;} 
p{ font-size:13px;font-family:"����"}
div{ font-size:13px; font-family:"����";}
.yam{ border-left-color:#CCCCCC; border-right-color:#CCCCCC; border-top-color:#CCCCCC}

</style> 

</head>

<body><div class="waicheng" style="width:80%; " >
<form name="myForm" action="/ecsideTest/ecside/getList1.action"
			method="post">
			<fieldset>
				<legend>
					<font style="font-weight: bold; color: black">��ѯѡ��</font>
				</legend>
				<table width="940">
					<tr>
						<td width="263" height="18">
							&nbsp;&nbsp;&nbsp; ����:
							<input  name="b" onClick="return er()">
&nbsp;&nbsp;						</td>
						<td width="211">
							&nbsp; ������:
							<select name="select3" style=" width:100px">
								<option selected="selected">��ǰ</option>
								<option >��ʷ</option>
                            </select>
&nbsp;&nbsp;						</td>
						<td width="129">&nbsp;</td>
						<td width="317" rowspan="3">
								<fieldset>
									<legend>
										<font style="font-weight: bold; color: black">��ʾѡ��</font>
									</legend>
									<table>
										<tr>
											<td width="149" height="15"><label>
											  <input type="checkbox" name="checkbox" value="checkbox" checked="checked"/>
											  ƽ�ֵ�
											</label></td>
											<td width="154"><input type="checkbox" name="checkbox5" value="checkbox"  checked="checked" disabled="disabled"/>
�ʽ��˻� </td>
										</tr>
										<tr>
											<td><input type="checkbox" name="checkbox2" value="checkbox" checked="checked" />
ָ�۵� </td>
											<td><input type="checkbox" name="checkbox6" value="checkbox"  checked="checked"/>
��ע </td>
										</tr>
										<tr>
											<td><input type="checkbox" name="checkbox3" value="checkbox"  checked="checked"/>
δƽ�ֵ� </td>
											<td><input type="checkbox" name="checkbox7" value="checkbox"  checked="checked"/>
���ڷ� </td>
										</tr><tr>
											<td><input type="checkbox" name="checkbox4" value="checkbox"  checked="checked"/>
�ʽ���ˮ </td>
											<td><input type="checkbox" name="checkbox8" value="checkbox" checked="checked" />
��ʾ�޵��˻� </td>
										</tr>
									</table>
								</fieldset>
						
						</td>
					</tr>
					<tr>
					  <td height="34">&nbsp;</td>
					  <td>��ʼ����:                      
					    <label>
					    <select name="select" style=" width:100px" disabled="disabled">
				        </select>
				      </label></td>
					  <td>&nbsp;</td>
				  </tr>
					<tr>
						<td>&nbsp;						</td>
						<td>��ֹ����:
							  <select name="select2"  style=" width:100px" disabled="disabled">
                              </select>
&nbsp;&nbsp;						</td>
<td><input name="button" type="button" value="��ѯ" />
  <br />
  <input name="button2" type="button" value="����" /></td>
</tr>
				</table>
			</fieldset>
		</form><br /><br /><br /><br />
<form>

<h1 align="center">��������</h1>
<p align="right"><hr align="center" />
 <div align="right">��ӡ���ڣ�2011-03-30 &nbsp; &nbsp; &nbsp; &nbsp;
 </div>
 <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;���ƣ�������Դͨ��ó���޹�˾</p>
<p align="right">��ʼ���ڣ�2011-02-02 &nbsp; &nbsp;</p>
<p align="right">��ֹ���ڣ�2011-03-30 &nbsp; &nbsp;</p>

<h2 align="center">ָ�۵�</h2>

<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr height="30">
    <td bgcolor="#FFCC99"><div align="center">�˻�</div></td>
    <td bgcolor="#FFCC99"><div align="center">�µ�ʱ��</div></td>
    <td bgcolor="#FFCC99"><div align="center">�м�۵���</div></td>
    <td bgcolor="#FFCC99"><div align="center">��Ʒ</div></td>
    <td bgcolor="#FFCC99"><div align="center">��/��</div></td>
    <td bgcolor="#FFCC99"><div align="center">����</div></td>
    <td bgcolor="#FFCC99"><div align="center">����</div></td>
    <td bgcolor="#FFCC99"><div align="center">�м��</div></td>
    <td bgcolor="#FFCC99"><div align="center">ֹ���</div></td>
    <td bgcolor="#FFCC99"><div align="center">ֹӯ��</div></td>
    <td bgcolor="#FFCC99"><div align="center">����</div></td>
  </tr>
</table>
</form>
<table width="100%" border="0">
  <tr height="30">
    <td colspan="4"><div align="center">
      <h2>�ʽ��˻�</h2>
    </div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >������</div></td>
    <td><div align="right" >0</div></td>
    <td><div align="center" >����ӯ����</div></td>
    <td><div align="center">0</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >���ջ�ת��</div></td>
    <td><div align="right" >-1000000</div></td>
    <td><div align="center" >��ֵ��</div></td>
    <td><div align="center">-1058559.7</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >����ӯ����</div></td>
    <td><div align="right" >-26810</div></td>
    <td><div align="center" >ռ�ñ�֤��</div></td>
    <td><div align="center">331956.8</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >�����ѣ�</div></td>
    <td><div align="right" >-28987.59</div></td>
    <td><div align="center" >���ᱣ֤��</div></td>
    <td><div align="center">0</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >�����ѣ�</div></td>
    <td><div align="right" >-2762.11</div></td>
    <td><div align="center" >���ñ�֤��</div></td>
    <td><div align="center">18609483.5</div></td>
  </tr>
  <tr height="30">
    <td><div align="center" >������</div></td>
    <td><div align="right">-1058559.7</div></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<h1>&nbsp;</h1>
<h1>&nbsp;</h1>
<h1>&nbsp;</h1>
<h1>ƽ�ֵ�</h1>
<table table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
<tr height="30">
	<td bgcolor="#FFCC99"><div align="center">���</div></td>
	<td bgcolor="#FFCC99"><div align="center">�˻�</div></td>
	<td bgcolor="#FFCC99"><div align="center">����</div></td>
	<td bgcolor="#FFCC99"><div align="center">��Ʒ</div></td>
	<td bgcolor="#FFCC99"><div align="center">���ֵ���</div></td>
	<td bgcolor="#FFCC99"><div align="center">����ʱ��</div></td>
	<td bgcolor="#FFCC99"><div align="center">��/��</div></td>
	<td bgcolor="#FFCC99"><div align="center">���ּ�</div></td>
	<td bgcolor="#FFCC99"><div align="center">�ֲּ�</div></td>
	<td bgcolor="#FFCC99"><div align="center">ƽ�ֵ���</div></td>
	<td bgcolor="#FFCC99"><div align="center">ƽ��ʱ��</div></td>
	<td bgcolor="#FFCC99"><div align="center">��/��</div></td>
	<td bgcolor="#FFCC99"><div align="center">ƽ�ּ�</div></td>
	<td bgcolor="#FFCC99"><div align="center">������</div></td>
	<td bgcolor="#FFCC99"><div align="center">ӯ��</div></td>
	</tr>
		<tr height="30">
			<td bgcolor="#FFCCCC" ><b>1</b></td>
			<td bgcolor="#FFCCCC" ><p><b>00412345678</b></p>
	      </td>
			<td bgcolor="#FFCCCC" ><b>1.00</b></td>
			<td bgcolor="#FFCCCC" ><b> ��ͨ��</b></td>
			<td bgcolor="#FFCCCC" ><b>2667601</b></td>
			<td bgcolor="#FFCCCC" ><b>2011-3-28 12:24:33</b></td>
			<td bgcolor="#FFCCCC" ><b>��</b></td>
			<td bgcolor="#FFCCCC" ><b>301.44</b></td>
			<td bgcolor="#FFCCCC" ><b>300.20</b></td>
			<td bgcolor="#FFCCCC" ><b>2688605</b></td>
			<td bgcolor="#FFCCCC" ><b>2011-3-29 2:54:33</b></td>
			<td bgcolor="#FFCCCC" ><b>��</b></td>
			<td bgcolor="#FFCCCC" ><b>300.15</b></td>
			<td bgcolor="#FFCCCC" ><b>-481.27</b></td>
			<td bgcolor="#FFCCCC" ><b>50.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>2</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>�ֻ�����</b></td>
			<td ><b>2628606</b></td>
			<td ><b>2011-3-24 17:36:33</b></td>
			<td ><b>��</b></td>
			<td ><b>7955.00</b></td>
			<td ><b>7854.00</b></td>
			<td ><b>2688606</b></td>
			<td ><b>2011-3-29 2:54:55</b></td>
			<td ><b>��</b></td>
			<td ><b>7841.00</b></td>
			<td ><b>-189.55</b></td>
			<td ><b>-195.00</b></td>
		</tr>
		<tr bgcolor="#FFCCCC" height="30">
			<td ><b>3</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>2.00</b></td>
			<td ><b>��ͨ��</b></td>
			<td ><b>2667610</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>��</b></td>
			<td ><b>301.29</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688607</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>��</b></td>
			<td ><b>299.91</b></td>
			<td ><b>-961.92</b></td>
			<td ><b>-580.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>4</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>��ͨ��</b></td>
			<td ><b>2667601</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>��</b></td>
			<td ><b>301.44</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688605</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>��</b></td>
			<td ><b>300.15</b></td>
			<td ><b>-481.27</b></td>
			<td ><b>50.00</b></td>
		</tr>
		<tr bgcolor="#FFCCCC" height="30">
			<td ><b>5</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>1.00</b></td>
			<td ><b>�ֻ�����</b></td>
			<td bgcolor="#FFCCCC" ><b>2628606</b></td>
			<td ><b>2011-3-24 17:36:33</b></td>
			<td ><b>��</b></td>
			<td ><b>7955.00</b></td>
			<td ><b>7854.00</b></td>
			<td ><b>2688606</b></td>
			<td ><b>2011-3-29 2:54:55</b></td>
			<td ><b>��</b></td>
			<td ><b>7841.00</b></td>
			<td ><b>-189.55</b></td>
			<td ><b>-195.00</b></td>
		</tr>
		<tr height="30">
			<td ><b>6</b></td>
			<td ><b>00412345678</b></td>
			<td ><b>2.00</b></td>
			<td ><b>��ͨ��</b></td>
			<td ><b>2667610</b></td>
			<td ><b>2011-3-28 12:24:33</b></td>
			<td ><b>��</b></td>
			<td ><b>301.29</b></td>
			<td ><b>300.20</b></td>
			<td ><b>2688607</b></td>
			<td ><b>2011-3-29 2:54:33</b></td>
			<td ><b>��</b></td>
			<td ><b>299.91</b></td>
			<td ><b>-961.92</b></td>
			<td ><b>-580.00</b></td>
		</tr>
	<tr height="30">
			<td ><b>&nbsp;�ܼ�</b></td>
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
<h1>���ڷ�</h1>
<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr height="30">
    <td width="16%" bgcolor="#FFCC99"><div align="center">����</div></td>
    <td width="16%" bgcolor="#FFCC99"><div align="center">ʱ��</div></td>
    <td width="15%" bgcolor="#FFCC99"><div align="center">����</div></td>
    <td width="10%" bgcolor="#FFCC99"><div align="center">��������</div></td>
    <td width="14%" bgcolor="#FFCC99"><div align="center">�䶯���</div></td>
    <td width="18%" bgcolor="#FFCC99"><div align="center">�����</div></td>
    <td width="11%" bgcolor="#FFCC99"><div align="center">��ע</div></td>
  </tr>
  <tr bgcolor="#FFCCCC" height="30">
  	<td ><b>2011-03-24</b></td>
			<td ><b>&nbsp;21:16:34</b></td>
			<td ><b>���ڷѻ���</b></td>
			<td ><b>&nbsp;2628432</b></td>
			<td ><div align="right"><b>&nbsp;-81.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.99</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr height="30">
  	<td ><b>2011-03-23</b></td>
			<td ><b>&nbsp;15:14:55</b></td>
			<td ><b>���ڷѻ���</b></td>
			<td ><b>&nbsp;2628412</b></td>
			<td ><div align="right"><b>&nbsp;-11.67</b></div></td>
			<td ><div align="center"><b>&nbsp;6547469.11</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr bgcolor="#FFCCCC" height="30">
  	<td ><b>2011-01-25</b></td>
			<td ><b>&nbsp;12:16:27</b></td>
			<td ><b>���ڷѻ���</b></td>
			<td ><b>&nbsp;2628444</b></td>
			<td ><div align="right"><b>&nbsp;-41.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.24</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
    <tr height="30">
  	<td ><b>2011-01-24</b></td>
			<td ><b>&nbsp;22:15:28</b></td>
			<td ><b>���ڷѻ���</b></td>
			<td ><b>&nbsp;2628634</b></td>
			<td ><div align="right"><b>&nbsp;-72.67</b></div></td>
			<td ><div align="center"><b>&nbsp;9997469.53</b></div></td>
			<td ><b>&nbsp;</b></td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<h1>�ʽ���ˮ</h1>
<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr bgcolor="#FFCCCC" height="30">
    <td width="16%"><div align="center">����</div></td>
    <td width="16%"><div align="center">ʱ��</div></td>
    <td width="15%"><div align="center">����</div></td>
    <td width="10%"><div align="center">��������</div></td>
    <td width="14%"><div align="center">�䶯���</div></td>
    <td width="18%"><div align="center">�����</div></td>
    <td width="11%"><div align="center">��ע</div></td>
  </tr>
  <tr bgcolor="#FFCC99" height="30">
    <td ><b>2011-03-24</b></td>
    <td ><b>&nbsp;21:16:34</b></td>
    <td ><b>���ڷѻ���</b></td>
    <td ><b>&nbsp;2628432</b></td>
    <td ><div align="right"><b>&nbsp;-81.67</b></div></td>
    <td ><div align="center"><b>&nbsp;9997469.99</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr height="30">
    <td ><b>2011-03-23</b></td>
    <td ><b>&nbsp;15:14:55</b></td>
    <td ><b>���ڷѻ���</b></td>
    <td ><b>&nbsp;2628412</b></td>
    <td ><div align="right"><b>&nbsp;-11.67</b></div></td>
    <td ><div align="center"><b>&nbsp;6547469.11</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr bgcolor="#FFCC99" height="30">
    <td ><b>2011-01-25</b></td>
    <td ><b>&nbsp;12:16:27</b></td>
    <td ><b>���ڷѻ���</b></td>
    <td ><b>&nbsp;2628444</b></td>
    <td ><div align="right"><b>&nbsp;-41.67</b></div></td>
    <td ><div align="center"><b>&nbsp;9997469.24</b></div></td>
    <td ><b>&nbsp;</b></td>
  </tr>
  <tr height="30">
    <td ><b>2011-01-24</b></td>
    <td ><b>&nbsp;22:15:28</b></td>
    <td ><b>���ڷѻ���</b></td>
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

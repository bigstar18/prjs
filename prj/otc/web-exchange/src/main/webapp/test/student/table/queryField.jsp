<%@ page pageEncoding="GBK"%>
<%@ taglib uri="/tld/forEcSide" prefix="forEcside"%>

<!-- ��ѯ�ֶ� -->
<div class="headZone" id="ec_headZone">
	<table border="0" cellspacing="0" cellpadding="0" class="tableRegion"
		style="table-layout: fixed;" width="100%">
		<thead id="ec_table_head">
			<tr>
				<forEcside:td_checkBox />
				<forEcside:td columnName="id[=][long]" title="ѧ��"></forEcside:td>
				<forEcside:td columnName="name[Like]" title="����" ></forEcside:td>
				<forEcside:td columnName="age[=][int]" title="����" ></forEcside:td>
				<forEcside:td columnName="grade[=]" title="�꼶"></forEcside:td>
				<forEcside:td columnName="speciality[Like]" title="רҵ"></forEcside:td>
			</tr>
		</thead>
	</table>
</div>
<!-- ��ѯ�ֶ� -->

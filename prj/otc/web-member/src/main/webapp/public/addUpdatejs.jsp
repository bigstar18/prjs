<%@ page pageEncoding="GBK"%>

<script>
	function commitStuInfo(){
		var name=document.getElementById("name").value;
		var age=document.getElementById("age").value;
		if(name==""||name==null){
			alert('������������');
				return false;
		}
		else if(isNaN(age)||age==""){
			alert('�������Ϊ���֣�����������');
			return false;
		}else{
			myFrm.submit();
		}
		
	}
	
	function commitTeaInfo(){
		var name=document.getElementById("name").value;
		var age=document.getElementById("age").value;
		if(name==""||name==null){
			alert('���������ƣ�');
				return false;
		}
		else if(isNaN(age)){
			alert('�������Ϊ���֣�����������');
			return;
		}else{
			teaFrm.submit();
		}
	}
</script>

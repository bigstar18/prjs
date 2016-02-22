<%@ page pageEncoding="GBK"%>

<script>
	function commitStuInfo(){
		var name=document.getElementById("name").value;
		var age=document.getElementById("age").value;
		if(name==""||name==null){
			alert('请输入姓名！');
				return false;
		}
		else if(isNaN(age)||age==""){
			alert('年龄必须为数字，请重新输入');
			return false;
		}else{
			myFrm.submit();
		}
		
	}
	
	function commitTeaInfo(){
		var name=document.getElementById("name").value;
		var age=document.getElementById("age").value;
		if(name==""||name==null){
			alert('请输入名称！');
				return false;
		}
		else if(isNaN(age)){
			alert('年龄必须为数字，请重新输入');
			return;
		}else{
			teaFrm.submit();
		}
	}
</script>

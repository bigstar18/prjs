function tips(t){
	$('<div class="tips" />').html(t).appendTo('body').animate({top:30},function(){
		var t=$(this);
		setTimeout(function(){
			t.animate({top:100,opacity: 0},function(){
				t.remove()
			})
		},2000)
	})
}

$(function(){
	$('form').submit(function(){
		var data=$(this).serializeArray();
		$.post($(this).attr('action'),data,function(data){
			if(data.status==1){
				//alert('注册成功\n\r'+data.post+'\n\r'+data['return']);
				alert('注册成功');
				location.href='/'
			}else{
				tips(data.msg);
			}
		},'json')
		return false;
	})
	
	$('#addr1').load('/region/').change(function(){
		if($(this).val()){
			$('#addr2').load('/region/'+$(this).val())
		}
	})
	$('#addr2').change(function(){
		if($(this).val()){
			$('#addr3').load('/region/'+$(this).val())
		}
	})
})
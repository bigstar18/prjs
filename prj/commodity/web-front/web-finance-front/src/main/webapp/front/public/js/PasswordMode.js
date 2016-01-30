var PasswordMode={
	//�ж�������������� 
	charMode:function (iN){
		if (iN>=48 && iN <=57) //����
			return 1;
		if (iN>=65 && iN <=90) //��д
			return 2;
		if (iN>=97 && iN <=122) //Сд
			return 4;
		else
			return 8;
	},
	//��������ģʽ  
	bitTotal:function (num){
		modes=0;
		for (i=0;i<4;i++){
			if (num & 1) modes++;
		num>>>=1;
		}
		return modes;
	},
	//����ǿ�ȼ���  
	checkStrong:function (pwd){
		if (pwd.length<=4){
			return 0; //����̫�� 
		}
		Modes=0;
		for (i=0;i<pwd.length;i++){
			//����ģʽ
			Modes|=this.charMode(pwd.charCodeAt(i));
		}
		return this.bitTotal(Modes);
	},
	//��ʾ��ɫ  
	pwStrength:function (pwd,lab1,lab2,lab3){
		O_color="#eeeeee";
		L_color="#ffff99";
		M_color="#ffcc00";
		H_color="#ff9900";
		if (pwd==null||pwd==''){
			Lcolor=Mcolor=Hcolor=O_color;
		}else{
			S_level=this.checkStrong(pwd);
			switch(S_level) {
			case 0:
				Lcolor=Mcolor=Hcolor=O_color;
			case 1:
				Lcolor=L_color;
				Mcolor=Hcolor=O_color;
				break;
			case 2:
				Lcolor=Mcolor=M_color;
				Hcolor=O_color;
				break;
			default:
				Lcolor=Mcolor=Hcolor=H_color;
			}
		}
		document.getElementById(lab1).style.background=Lcolor;
		document.getElementById(lab2).style.background=Mcolor;
		document.getElementById(lab3).style.background=Hcolor;
		return;
	}
}
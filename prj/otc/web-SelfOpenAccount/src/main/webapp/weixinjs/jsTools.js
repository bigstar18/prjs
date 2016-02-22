//验证身份证号码
// 构造函数，变量为15位或者18位的身份证号码 
function clsIDCard(CardNo) {
    this.Valid = false;
    this.ID15 = '';
    this.ID18 = '';
    this.Local = '';
    if (CardNo != null)
        this.SetCardNo(CardNo);
}


// 设置身份证号码，15位或者18位 
clsIDCard.prototype.SetCardNo = function (CardNo) {
    this.ID15 = '';
    this.ID18 = '';
    this.Local = '';
    CardNo = CardNo.replace(" ", "");
    var strCardNo;
    if (CardNo.length == 18) {
        pattern = /^\d{17}(\d|x|X)$/;
        if (pattern.exec(CardNo) == null)
            return;
        strCardNo = CardNo.toUpperCase();
    } else {
        pattern = /^\d{15}$/;
        if (pattern.exec(CardNo) == null)
            return;
        strCardNo = CardNo.substr(0, 6) + '19' + CardNo.substr(6, 9)
        strCardNo += this.GetVCode(strCardNo);
    }
    this.Valid = this.CheckValid(strCardNo);
}
// 校验身份证有效性 
clsIDCard.prototype.IsValid = function () {
    return this.Valid;
}
// 返回生日字符串，格式如下，1981-10-10 
clsIDCard.prototype.GetBirthDate = function () {
    var BirthDate = '';
    if (this.Valid)
        BirthDate = this.GetBirthYear() + '-' + this.GetBirthMonth() + '-'
            + this.GetBirthDay();
    return BirthDate;
}
// 返回生日中的年，格式如下，1981 
clsIDCard.prototype.GetBirthYear = function () {
    var BirthYear = '';
    if (this.Valid)
        BirthYear = this.ID18.substr(6, 4);
    return BirthYear;
}
// 返回生日中的月，格式如下，10 
clsIDCard.prototype.GetBirthMonth = function () {
    var BirthMonth = '';
    if (this.Valid)
        BirthMonth = this.ID18.substr(10, 2);
    if (BirthMonth.charAt(0) == '0')
        BirthMonth = BirthMonth.charAt(1);
    return BirthMonth;
}
// 返回生日中的日，格式如下，10 
clsIDCard.prototype.GetBirthDay = function () {
    var BirthDay = '';
    if (this.Valid)
        BirthDay = this.ID18.substr(12, 2);
    return BirthDay;
}

// 返回性别，1：男，0：女 
clsIDCard.prototype.GetSex = function () {
    var Sex = '';
    if (this.Valid)
        Sex = this.ID18.charAt(16) % 2;
    return Sex;
}

// 返回15位身份证号码 
clsIDCard.prototype.Get15 = function () {
    var ID15 = '';
    if (this.Valid)
        ID15 = this.ID15;
    return ID15;
}

// 返回18位身份证号码 
clsIDCard.prototype.Get18 = function () {
    var ID18 = '';
    if (this.Valid)
        ID18 = this.ID18;
    return ID18;
}

// 返回所在省，例如：上海市、浙江省 
clsIDCard.prototype.GetLocal = function () {
    var Local = '';
    if (this.Valid)
        Local = this.Local;
    return Local;
}

clsIDCard.prototype.GetVCode = function (CardNo17) {
    var Wi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var Ai = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
    var cardNoSum = 0;
    for (var i = 0; i < CardNo17.length; i++)
        cardNoSum += CardNo17.charAt(i) * Wi[i];
    var seq = cardNoSum % 11;
    return Ai[seq];
}

clsIDCard.prototype.CheckValid = function (CardNo18) {
    if (this.GetVCode(CardNo18.substr(0, 17)) != CardNo18.charAt(17))
        return false;
    if (!this.IsDate(CardNo18.substr(6, 8)))
        return false;
    var aCity = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    };
    if (aCity[parseInt(CardNo18.substr(0, 2))] == null)
        return false;
    this.ID18 = CardNo18;
    this.ID15 = CardNo18.substr(0, 6) + CardNo18.substr(8, 9);
    this.Local = aCity[parseInt(CardNo18.substr(0, 2))];
    return true;
}

clsIDCard.prototype.IsDate = function (strDate) {
    var r = strDate.match(/^(\d{1,4})(\d{1,2})(\d{1,2})$/);
    if (r == null)
        return false;
    var d = new Date(r[1], r[2] - 1, r[3]);
    return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[2] && d
        .getDate() == r[3]);
}


//luhm验证银行卡号 
function luhmCheck(bankno) {
    if (bankno == "") {
        return false;
    }
    var lastNum = bankno.substr(bankno.length - 1, 1);//取出最后一位（与luhm进行比较）

    var first15Num = bankno.substr(0, bankno.length - 1);//前15或18位
    var newArr = new Array();
    for (var i = first15Num.length - 1; i > -1; i--) {    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i, 1));
    }
    var arrJiShu = new Array();  //奇数位*2的积 <9
    var arrJiShu2 = new Array(); //奇数位*2的积 >9

    var arrOuShu = new Array();  //偶数位数组
    for (var j = 0; j < newArr.length; j++) {
        if ((j + 1) % 2 == 1) {//奇数位
            if (parseInt(newArr[j]) * 2 < 9)
                arrJiShu.push(parseInt(newArr[j]) * 2);
            else
                arrJiShu2.push(parseInt(newArr[j]) * 2);
        }
        else //偶数位
            arrOuShu.push(newArr[j]);
    }

    var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
    for (var h = 0; h < arrJiShu2.length; h++) {
        jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
        jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
    }

    var sumJiShu = 0; //奇数位*2 < 9 的数组之和
    var sumOuShu = 0; //偶数位数组之和
    var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal = 0;
    for (var m = 0; m < arrJiShu.length; m++) {
        sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
    }

    for (var n = 0; n < arrOuShu.length; n++) {
        sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
    }

    for (var p = 0; p < jishu_child1.length; p++) {
        sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
        sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
    }
    //计算总和
    sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

    //计算Luhm值
    var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
    var luhm = 10 - k;

    if (lastNum == luhm) {
        return true;
    }
    else {
        return false;
    }
}
(function($){
	$.getUrlParam = function(name)
	{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return unescape(r[2]); return null;
	}
})(jQuery);

//验证单个控件是否为空（input输入为空 select未选择）
function validateNull(formId, tips) {
    var formType = $("#" + formId).attr("type");
    var formValue = "";
    if (formType == "text" || formType == "password" || formType == "tel") {
        formValue = $("#" + formId).val();
    } else if (formType == "select") {
        formValue = $("#" + formId).find("option:selected").val();
    }

    if (formValue == "" || formValue == "-1") {
        EV_modeAlert(formId,'errorMSG', tips);
        return true;
    }
    
    return false;
}

//移动电话 
function checkMobilePhoneForm(id_) {
	var mobilehone = document.getElementById(id_).value;

	if (mobilehone == ""){
		EV_modeAlert(id_, 'errorMSG', "请输入手机号！");
		return false;
	}

	if (mobilehone.length > 0) {
		
		if(mobilehone.length != 11){
			EV_modeAlert(id_, 'errorMSG', "移动电话输入有误，请检查！");
			return false;
		}
		
		var reg0 = /^13\d{5,9}$/;
		var reg1 = /^150\d{4,8}$/;
		var reg2 = /^151\d{4,8}$/;
		var reg3 = /^152\d{4,8}$/;
		var reg4 = /^153\d{4,8}$/;
		var reg5 = /^155\d{4,8}$/;
		var reg6 = /^156\d{4,8}$/;
		var reg7 = /^157\d{4,8}$/;
		var reg8 = /^158\d{4,8}$/;
		var reg9 = /^159\d{4,8}$/;
		var reg10 = /^170\d{4,8}$/;
		var reg11 = /^176\d{4,8}$/;
		var reg12 = /^18\d{5,9}$/;
		var reg13 = /^147\d{4,8}$/;
		var reg14 = /^0\d{10,11}$/;
		
		if (reg0.test(mobilehone))
			return true;
		if (reg1.test(mobilehone))
			return true;
		if (reg2.test(mobilehone))
			return true;
		if (reg3.test(mobilehone))
			return true;
		if (reg4.test(mobilehone))
			return true;
		if (reg5.test(mobilehone))
			return true;
		if (reg6.test(mobilehone))
			return true;
		if (reg7.test(mobilehone))
			return true;
		if (reg8.test(mobilehone))
			return true;
		if (reg9.test(mobilehone))
			return true;
		if (reg10.test(mobilehone))
			return true;
		if (reg11.test(mobilehone))
			return true;
		if (reg12.test(mobilehone))
			return true;
		if (reg13.test(mobilehone))
			return true;
		if (reg14.test(mobilehone))
			return true;

		EV_modeAlert(id_, 'errorMSG', "移动电话输入有误，请检查！");
		return false;
	}
}

function marketInfo(){
	jQuery.ajax({
		url:"dictAction.action",
		async:false,
		type:"post",
		data:{type:'marketInfo'},
		cache:false,
		datatype:"html",
		success:function(data){
			if(data!=null)
			{
				var mInfo = data.split(";");
				$("#market_name").html(mInfo[0]);
				$("#market_phone").html(mInfo[1]);
			}
		},
		error:function(XMLHttpResuest,textStatus,errorThrown){}
	});
	return true;
}

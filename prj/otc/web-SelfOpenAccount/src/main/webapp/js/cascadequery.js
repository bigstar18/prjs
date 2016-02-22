cityareaname=new Array(35);
function first(preP,preC,formname,selectP,selectC)
{
a=0;
if (selectP!='')
{
	alert(1);
 switch (selectP) {
    case '北京' : a=1; break;
    case '深圳' : a=2; break;
    case '上海' : a=3; break;
    case '重庆' : a=4; break;
    case '天津' : a=5; break;
    case '广东' : a=6; break;
    case '河北' : a=7; break;
    case '山西' : a=8; break;
    case '内蒙古' : a=9; break;
    case '辽宁' : a=10; break;
    case '吉林' : a=11; break;
    case '黑龙江' : a=12; break;
    case '江苏' : a=13; break;
    case '浙江' : a=14; break;
    case '安徽' : a=15; break;
    case '福建' : a=16; break;
    case '江西' : a=17; break;
    case '山东' : a=18; break;
    case '河南' : a=19; break;
    case '湖北' : a=20; break;
    case '湖南' : a=21; break;
    case '广西' : a=22; break;
    case '海南' : a=23; break;
    case '四川' : a=24; break;
    case '贵州' : a=25; break;
    case '云南' : a=26; break;
    case '西藏' : a=27; break;
    case '陕西' : a=28; break;
    case '甘肃' : a=29; break;
    case '青海' : a=30; break;
    case '宁夏' : a=31; break;
    case '新疆' : a=32; break;
    case '香港' : a=33; break;
    case '澳门' : a=34; break;
    case '台湾' : a=35; break;
 } 
}

tempoption=new Option('北京','北京');
eval('document.'+formname+'.'+preP+'.options[1]=tempoption;');
cityareaname[0]=new Array('东城区','西城区','崇文区','宣武区','朝阳区','海淀区','丰台区','石景山');

tempoption=new Option('深圳','深圳');
eval('document.'+formname+'.'+preP+'.options[2]=tempoption;');
cityareaname[1]=new Array('罗湖','福田','南山','盐田','宝安','龙岗');

tempoption=new Option('上海','上海');
eval('document.'+formname+'.'+preP+'.options[3]=tempoption;');
cityareaname[2]=new Array('宝山','金山','南市','长宁','静安','青浦','崇明','卢湾','松江','奉贤','浦东','杨浦','虹口','普陀','闸北','黄浦','闵行','徐汇','嘉定','南汇');

tempoption=new Option('重庆','重庆');
eval('document.'+formname+'.'+preP+'.options[4]=tempoption;');
cityareaname[3]=new Array('渝中','江北','沙坪坝','南岸','九龙坡','大渡口');

tempoption=new Option('天津','天津');
eval('document.'+formname+'.'+preP+'.options[5]=tempoption;');
cityareaname[4]=new Array('和平','河北','河西','河东','南开','红桥','塘沽','汉沽','大港','东丽','西青','津南','北辰','武清','滨海');

tempoption=new Option('广东','广东');
eval('document.'+formname+'.'+preP+'.options[6]=tempoption;');
cityareaname[5]=new Array('广州','珠海','中山','佛山','东莞','清远','肇庆','阳江','湛江','韶关','惠州','河源','汕尾','汕头','梅州');

tempoption=new Option('河北','河北');
eval('document.'+formname+'.'+preP+'.options[7]=tempoption;');
cityareaname[6]=new Array('石家庄','唐山','秦皇岛','邯郸','邢台','张家口','承德','廊坊','沧州','保定','衡水');

tempoption=new Option('山西','山西');
eval('document.'+formname+'.'+preP+'.options[8]=tempoption;');
cityareaname[7]=new Array('太原','大同','阳泉','朔州','长治','临汾','晋城');

tempoption=new Option('内蒙古','内蒙古');
eval('document.'+formname+'.'+preP+'.options[9]=tempoption;');
cityareaname[8]=new Array('呼和浩特','包头','乌海','临河','东胜','集宁','锡林浩特','通辽','赤峰','海拉尔','乌兰浩特');

tempoption=new Option('辽宁','辽宁');
eval('document.'+formname+'.'+preP+'.options[10]=tempoption;');
cityareaname[9]=new Array('沈阳','大连','鞍山','锦州','丹东','盘锦','铁岭','抚顺','营口','辽阳','阜新','本溪','朝阳','葫芦岛');

tempoption=new Option('吉林','吉林');
eval('document.'+formname+'.'+preP+'.options[11]=tempoption;');
cityareaname[10]=new Array('长春','吉林','四平','辽源','通化','白山','松原','白城','延边');

tempoption=new Option('黑龙江','黑龙江');
eval('document.'+formname+'.'+preP+'.options[12]=tempoption;');
cityareaname[11]=new Array('哈尔滨','齐齐哈尔','牡丹江','佳木斯','大庆','伊春','黑河','鸡西','鹤岗','双鸭山','七台河','绥化','大兴安岭');

tempoption=new Option('江苏','江苏');
eval('document.'+formname+'.'+preP+'.options[13]=tempoption;');
cityareaname[12]=new Array('南京','苏州','无锡','常州','镇江','连云港','扬州','徐州','南通','盐城','淮阴','泰州','宿迁');

tempoption=new Option('浙江','浙江');
eval('document.'+formname+'.'+preP+'.options[14]=tempoption;');
cityareaname[13]=new Array('杭州','湖州','丽水','温州','绍兴','舟山','嘉兴','金华','台州','衢州','宁波');

tempoption=new Option('安徽','安徽');
eval('document.'+formname+'.'+preP+'.options[15]=tempoption;');
cityareaname[14]=new Array('合肥','芜湖','蚌埠','滁州','安庆','六安','黄山','宣城','淮南','宿州','马鞍山','铜陵','淮北','阜阳','池州','巢湖','亳州');

tempoption=new Option('福建','福建');
eval('document.'+formname+'.'+preP+'.options[16]=tempoption;');
cityareaname[15]=new Array('福州','厦门','泉州','漳州','龙岩','南平','宁德','莆田','三明');

tempoption=new Option('江西','江西');
eval('document.'+formname+'.'+preP+'.options[17]=tempoption;');
cityareaname[16]=new Array('南昌','景德镇','九江','萍乡','新余','鹰潭','赣州','宜春','吉安','上饶','抚州');

tempoption=new Option('山东','山东');
eval('document.'+formname+'.'+preP+'.options[18]=tempoption;');
cityareaname[17]=new Array('济南','青岛','淄博','德州','烟台','潍坊','济宁','泰安','临沂','菏泽','威海','枣庄','日照','莱芜','聊城','滨州','东营');

tempoption=new Option('河南','河南');
eval('document.'+formname+'.'+preP+'.options[19]=tempoption;');
cityareaname[18]=new Array('郑州','开封','洛阳','平顶山','安阳','鹤壁','新乡','焦作','濮阳','许昌','漯河','三门峡','南阳','商丘','周口','驻马店','信阳','济源');

tempoption=new Option('湖北','湖北');
eval('document.'+formname+'.'+preP+'.options[20]=tempoption;');
cityareaname[19]=new Array('武汉','黄石','十堰','荆州','宜昌','襄樊','鄂州','荆门','孝感','黄冈','咸宁','恩施','随州','仙桃','天门','潜江','神农架');

tempoption=new Option('湖南','湖南');
eval('document.'+formname+'.'+preP+'.options[21]=tempoption;');
cityareaname[20]=new Array('长沙','株州','湘潭','衡阳','邵阳','岳阳','常德','郴州','益阳','永州','怀化','娄底','湘西 ');

tempoption=new Option('广西','广西');
eval('document.'+formname+'.'+preP+'.options[22]=tempoption;');
cityareaname[21]=new Array('南宁','柳州','桂林','梧州','北海','防城港','钦州','贵港','玉林','贺州','百色','河池');

tempoption=new Option('海南','海南');
eval('document.'+formname+'.'+preP+'.options[23]=tempoption;');
cityareaname[22]=new Array('海口','三亚','通什','琼海','琼山','文昌','万宁','东方','儋州');

tempoption=new Option('四川','四川');
eval('document.'+formname+'.'+preP+'.options[24]=tempoption;');
cityareaname[23]=new Array('成都','自贡','攀枝花','泸州','德阳','绵阳','广元','遂宁','内江','乐山','南充','宜宾','广安','达川','巴中','雅安','眉山','阿坝','甘孜','凉山 ');

tempoption=new Option('贵州','贵州');
eval('document.'+formname+'.'+preP+'.options[25]=tempoption;');
cityareaname[24]=new Array('贵阳','六盘水','遵义','铜仁','毕节','安顺','黔西南','黔东南','黔南');

tempoption=new Option('云南','云南');
eval('document.'+formname+'.'+preP+'.options[26]=tempoption;');
cityareaname[25]=new Array('昆明','东川','曲靖','玉溪','昭通','思茅','临沧','保山','丽江','文山','红河','西双版纳','楚雄','大理','德宏','怒江','迪庆');

tempoption=new Option('西藏','西藏');
eval('document.'+formname+'.'+preP+'.options[27]=tempoption;');
cityareaname[26]=new Array('拉萨','那曲','昌都','山南','日喀则','阿里','林芝');

tempoption=new Option('陕西','陕西');
eval('document.'+formname+'.'+preP+'.options[28]=tempoption;');
cityareaname[27]=new Array('西安','铜川','宝鸡','咸阳','渭南','延安','汉中','榆林','商洛','安康');

tempoption=new Option('甘肃','甘肃');
eval('document.'+formname+'.'+preP+'.options[29]=tempoption;');
cityareaname[28]=new Array('兰州','金昌','白银','天水','嘉峪关','定西','平凉','庆阳','陇南','武威','张掖','酒泉','甘南','临夏');

tempoption=new Option('青海','青海');
eval('document.'+formname+'.'+preP+'.options[30]=tempoption;');
cityareaname[29]=new Array('西宁','海东','海北','黄南','海南','果洛','玉树','海西');

tempoption=new Option('宁夏','宁夏');
eval('document.'+formname+'.'+preP+'.options[31]=tempoption;');
cityareaname[30]=new Array('银川','石嘴山','银南','固原');

tempoption=new Option('新疆','新疆');
eval('document.'+formname+'.'+preP+'.options[32]=tempoption;');
cityareaname[31]=new Array('乌鲁木齐','克拉玛依','石河子','吐鲁番','哈密','和田','阿克苏','喀什','克孜勒苏','巴音郭楞','昌吉','博尔塔拉','伊犁');

tempoption=new Option('香港','香港');
eval('document.'+formname+'.'+preP+'.options[33]=tempoption;');
cityareaname[32]=new Array('香港');

tempoption=new Option('澳门','澳门');
eval('document.'+formname+'.'+preP+'.options[34]=tempoption;');
cityareaname[33]=new Array('澳门');

tempoption=new Option('台湾','台湾');
eval('document.'+formname+'.'+preP+'.options[35]=tempoption;');
cityareaname[34]=new Array('台湾');

eval('document.'+formname+'.'+preP+'.options[a].selected=true;');

    if (selectP!='')
      {
        b=0;for(i=0;i<cityareaname[a-1].length;i++)
           {
             if (selectC==cityareaname[a-1][i])
      {b=i+1;tempoption=new Option(cityareaname[a-1][i],cityareaname[a-1][i],false,true);}
             else
      tempoption=new Option(cityareaname[a-1][i],cityareaname[a-1][i]);
            eval('document.'+formname+'.'+preC+'.options[i+1]=tempoption;');
           }
        eval('document.'+formname+'.'+preC+'.options[b].selected=true;');
      }
}

 function selectcityarea(preP,preC,formname)
   {
     cityid=eval('document.'+formname+'.'+preP+'.selectedIndex;');
     j=eval('document.'+formname+'.'+preC+'.length;');
     for (i=1;i<j;i++)
        {eval('document.'+formname+'.'+preC+'.options[j-i]=null;')}
     if (cityid!="0")
       {
         for (i=0;i<cityareaname[cityid-1].length;i++)
            {
    tempoption=new Option(cityareaname[cityid-1][i],cityareaname[cityid-1][i]);
             eval('document.'+formname+'.'+preC+'.options[i+1]=tempoption;');
            }
       }
    }
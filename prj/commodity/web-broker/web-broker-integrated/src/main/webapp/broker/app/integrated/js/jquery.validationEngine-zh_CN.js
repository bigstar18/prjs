(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { // Add your regex rules here, you can take telephone as an example
                    "regex": "none",
                    "alertText": "* �˴����ɿհ�",
                    "alertTextCheckboxMultiple": "* ��ѡ��һ����Ŀ",
                    "alertTextCheckboxe": "* �����빳ѡ����",
                    "alertTextDateRange": "* ���ڷ�Χ���ɿհ�"
                },
                "dateRange": {
                    "regex": "none",
                    "alertText": "* ��Ч�� ",
                    "alertText2": " ���ڷ�Χ"
                },
                "dateTimeRange": {
                    "regex": "none",
                    "alertText": "* ��Ч�� ",
                    "alertText2": " ʱ�䷶Χ"
                },
                "minSize": {
                    "regex": "none",
                    "alertText": "* ���� ",
                    "alertText2": " ���ַ�"
                },
                "maxSize": {
                    "regex": "none",
                    "alertText": "* ��� ",
                    "alertText2": " ���ַ�"
                },
                "onlyDoubleSp": {
                    "regex": /^\d+\.?\d{0,2}$/,
                    "alertText": "* ���2λС������"
                },
				"groupRequired": {
                    "regex": "none",
                    "alertText": "* �����ѡ������һ����λ"
                },
                "min": {
                    "regex": "none",
                    "alertText": "* ��СֵΪ "
                },
                "max": {
                    "regex": "none",
                    "alertText": "* ���ֵΪ "
                },
                "past": {
                    "regex": "none",
                    "alertText": "* ���ڱ������� "
                },
                "future": {
                    "regex": "none",
                    "alertText": "* ���ڱ������� "
                },	
                "maxCheckbox": {
                    "regex": "none",
                    "alertText": "* ���ѡȡ ",
                    "alertText2": " ����Ŀ"
                },
                "minCheckbox": {
                    "regex": "none",
                    "alertText": "* ��ѡ�� ",
                    "alertText2": " ����Ŀ"
                },
                "equals": {
                    "regex": "none",
                    "alertText": "* ��������������ͬ������"
                },
                "creditCard": {
                    "regex": "none",
                    "alertText": "* ��Ч�����ÿ�����"
                },
                 "bankAccount": {
                    "regex": "none",
                    "alertText": "* ��Ч�����к���"
                },
                "mobile":{
                	"regex":/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/,
                	 "alertText": "* ��Ч���ֻ�����"
                },
                "phone": {
                    // credit: jquery.h5validate.js / orefalo
                    "regex": /^([\+][0-9]{1,3}[ \.\-])?([\(]{1}[0-9]{2,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
                    "alertText": "* ��Ч�ĵ绰����"
                },
                 "fax": {
                    // credit: jquery.h5validate.js / orefalo
                    "regex": /^([\+][0-9]{1,3}[ \.\-])?([\(]{1}[0-9]{2,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
                    "alertText": "* ��Ч�Ĵ����"
                },
                "email": {
                    // Shamelessly lifted from Scott Gonzalez via the Bassistance Validation plugin http://projects.scottsplayground.com/email_address_validation/
                    "regex": /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
                    "alertText": "* �ʼ���ַ��Ч"
                },
               
                "password": {
                    "regex":  /^(.{1})((.|\s){5,500})$|^\s*$/,
                    "alertText": "* ��������6λ"
                },
                "doubleCus": {
                    // Number, including positive, negative, and floating decimal. credit: orefalo
                    "regex": /^[1-9]\d*(\.\d{0,2})?$|^0(\.\d{0,2})?$/,
                    "alertText": "* ��Ч������(���ܳ�����λС��)"
                },
                "double": {
                    // Number, including positive, negative, and floating decimal. credit: orefalo
                    "regex": /^\d+\.?\d{0,2}$/,
                    "alertText": "* ��Ч������"
                },
                "integer": {
                    "regex": /^[\-\+]?\d+$/,
                    "alertText": "* ������Ч������"
                },
                "number": {
                    // Number, including positive, negative, and floating decimal. credit: orefalo
                    "regex": /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/,
                    "alertText": "* ��Ч������"
                },
                "number1": {
                     "regex": /^[\-\+]?(([0-9]+)(([0-9]+))?|(([0-9]+))?)$/,
                    "alertText": "* ��Ч������"
                },
                "date": {
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
                    "alertText": "* ��Ч�����ڣ���ʽ����Ϊ YYYY-MM-DD"
                },
                "time": {
                    "regex": /^([0-9]|([0-1][0-9])|([2][0-3])):([0-9]|([0-5][0-9])):([0-9]|([0-5][0-9]))$/,
                    "alertText": "* ��Ч��ʱ�䣬��ʽ����Ϊ HH:MM:SS"
                },
                "ipv4": {
                    "regex": /^((([01]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))[.]){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))$/,
                    "alertText": "* ��Ч�� IP ��ַ"
                },
                "url": {
                    "regex": /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
                    "alertText": "* Invalid URL"
                },
                "onlyNumberSp": {
                    "regex": /^[0-9\ ]+$/,
                    "alertText": "* ֻ��������"
                },
                "onlyLetterSp": {
                    "regex": /^[a-zA-Z\ \']+$/,
                    "alertText": "* ֻ����Ӣ����ĸ��Сд"
                },
                "onlyLetterNumber": {
                    "regex": /^[0-9a-zA-Z]+$/,
                    "alertText": "* ֻ����Ӣ����ĸ��Сд������"
                },
	            //tls warning:homegrown not fielded 
                "dateFormat":{
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/,
                    "alertText": "* ��Ч�����ڸ�ʽ"
                },
                //tls warning:homegrown not fielded 
				"dateTimeFormat": {
	                "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1}$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^((1[012]|0?[1-9]){1}\/(0?[1-9]|[12][0-9]|3[01]){1}\/\d{2,4}\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1})$/,
                    "alertText": "* ��Ч�����ڻ�ʱ���ʽ",
                    "alertText2": "�ɽ��ܵĸ�ʽ�� ",
                    "alertText3": "mm/dd/yyyy hh:mm:ss AM|PM �� ", 
                    "alertText4": "yyyy-mm-dd hh:mm:ss AM|PM"
	            },
	            "checkFirmByFirmId": {
                    "url": "../../ajaxcheck/checkFirmByFirmId.action",
                    "extraDataDynamic": ['#oldFirmId'],
                    // error
                    "alertText": "* �˽����̴����ѱ�ʹ��",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "�˽����̴������ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ�Ͻ����̴����Ƿ�������������ʹ�ã����Եȡ�"
                },
                "checkFirmByBankAccount": {
                    "url": "../../ajaxcheck/checkFirmByBankAccount.action",
                    "extraDataDynamic": ['#oldBankAccount'],
                    // error
                    "alertText": "* �������ʺ��ѱ���ʹ��",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "�������˺ſ���ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ�������˺��Ƿ�������������ʹ�ã����Եȡ�"
                },
                "checkFirmByEmail": {
                    "url": "../../ajaxcheck/checkFirmByEmail.action",
                    "extraDataDynamic": ['#oldEmail'],
                    // error
                    "alertText": "* ��������ѱ���ʹ��",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "������ſ���ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ����������Ƿ�������������ʹ�ã����Եȡ�"
                },
                "checkUserByUserId": {
                  	"url": "../ajaxcheck/checkUserByUserId.action",
                    // error
                    "alertText": "* ���û������ѱ���ʹ��",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "���û��������ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ���û������Ƿ��������û�ʹ�ã����Եȡ�"
                },
                "checkRoleByName": {
                    "url": "../ajaxcheck/checkRoleByName.action",
                    // error
                    "alertText": "* �˽�ɫ�����ѱ���ʹ��",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "�˽�ɫ���ƿ���ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ�Ͻ�ɫ�����Ƿ���������ɫʹ�ã����Եȡ�"
                },
                "checkMessageByUserId": {
                    "url": "../../ajaxcheck/checkMessageByUserId.action",
                    "extraDataDynamic": ['#recieverType'],
                    // error
                    "alertText": "* �˽����˲�����",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "�˽����˿���ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ�Ͻ������Ƿ��д��ڣ����Եȡ�"
                },
                "mouseCheckFirmByFirmId": {
                    "url": "../../ajaxcheck/demo/mouseCheckFirmByFirmId.action",
                    // error
                    "alertText": "* �˽����̲�����",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "�˽����̿���ʹ��",
                    // speaks by itself
                    "alertTextLoad": "* ����ȷ�Ͻ������Ƿ��д��ڣ����Եȡ�"
                }
            };
            
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
/**
 *日期函数
 *
 */


/**
 *比较两个日期:
 *   date1 > date2   return 1;
 *   date1 = date2   return 0;
 *   date1 < date2   return -1;
 */
function DateDiff(date1, date2)
{
	var array_date1 = (new String(date1)).split("-");
	var array_date2 = (new String(date2)).split("-");

	if (parseInt(array_date1[0],10) > parseInt(array_date2[0],10))
	{
		return 1;
	}
	if (parseInt(array_date1[0],10) < parseInt(array_date2[0],10))
	{
		return -1;
	}
	if (parseInt(array_date1[1],10) > parseInt(array_date2[1],10))
	{
		return 1;
	}
	if (parseInt(array_date1[1],10) < parseInt(array_date2[1],10))
	{
		return -1;
	}
	if (parseInt(array_date1[2],10) > parseInt(array_date2[2],10))
	{
		return 1;
	}
	if (parseInt(array_date1[2],10) < parseInt(array_date2[2],10))
	{
		return -1;
	}
	return 0;
}

/**
 *
 *判断日期是否在一个指定日期范围内
 *
 */
function BetweenDate(datevalue, date1, date2)
{
	datevalue = formatDate(datevalue);

	if (DateDiff(datevalue, date1) < 0 || DateDiff(datevalue, date2) > 0)
	{
		return false;
	}
	else
	{
		return true;
	}
}

function formatDate(datevalue)
{
	try
	{
		date_array = (new String(datevalue)).split("-");
		//alert(date_array[0]+","+date_array[1]+","+date_array[2]);

		dateObj = new Date(parseInt(date_array[0],10), (parseInt(date_array[1],10) - 1), parseInt(date_array[2],10));
		//alert(dateObj.getFullYear() +"," + dateObj.getMonth() + "," + dateObj.getDate());
		if (isNaN(dateObj.getFullYear()) || isNaN(dateObj.getMonth()) || isNaN(dateObj.getMonth()))
		{
			throw "err";
		}
		newdate = dateObj.getFullYear() + "-" + (dateObj.getMonth() > 8 ? "" : "0") + (dateObj.getMonth() + 1) + "-" + (dateObj.getDate() > 9 ? "" : "0") + dateObj.getDate();
	}
	catch(e)
	{
		var now = new date();
		newdate = (now.getFullYear + "-" + (now.getMonth() > 8 ? "" : "0") + (now.getMonth() + 1) + "-" + now.getDate());
	}
	return newdate;
}

function isDate(datestring)
{
	try
	{
		date_array = (new String(datestring)).split("-");
		new Date(parseInt(date_array[0],10), (parseInt(date_array[1],10) - 1), parseInt(date_array[2],10));

		return true;
	}
	catch(e)
	{
		return false;
	}
}
function isTime(timestring)
{
	try
	{
	    if(timestring.length != 8)
	    {
	      return false;
	    }
		time_array = (new String(timestring)).split(":");
		if(time_array.length != 3)
		{
		  return false;
		}
		var h = parseInt(time_array[0],10);
		if(h < 0 || h > 23)
		{
		  return false;
		}
		var m = parseInt(time_array[1],10);
		if(m < 0 || m > 59)
		{
		  return false;
		}
		var s = parseInt(time_array[2],10);
		if(s < 0 || s > 59)
		{
		  return false;
		}
		return true;
	}
	catch(e)
	{
		return false;
	}
}
/*
验证是否是标准的yyyy-MM-dd HH:mm:ss格式
*/
function isDateTime(datetimestring)
{
	try
	{
	    datetime_array = (new String(datetimestring)).split(" ");
	    //alert(datetime_array.length);
        if(datetime_array.length != 2)
        {
          return false;
        }	    
		date_array = (new String(datetime_array[0])).split("-");
		//alert(date_array.length);
		if(date_array.length != 3)
        {
          return false;
        }
		time_array = (new String(datetime_array[1])).split(":");
		//alert(time_array.length);
        if(time_array.length != 3)
        {
          return false;
        }	
		new Date(parseInt(date_array[0],10), (parseInt(date_array[1],10) - 1), parseInt(date_array[2],10),parseInt(time_array[0],10),parseInt(time_array[1],10),parseInt(time_array[2],10));
		return true;
	}
	catch(e)
	{
		return false;
	}
}
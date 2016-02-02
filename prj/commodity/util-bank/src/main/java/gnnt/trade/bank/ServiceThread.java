package gnnt.trade.bank;

import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * <p>Title: 系统服务线程</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class ServiceThread  extends Thread   
{
	private CapitalProcessor m_processor=null;
	public ServiceThread(CapitalProcessor processor)
	{
		this.m_processor=processor;
	}
	
	/**
	 * 向指定日志文件写入日志信息
	 * @param content 日志内容
	 * @return void
	 */
	private void log(String content)
	{
		Logger plog = Logger.getLogger("Servicelog");
		plog.debug(content);		
	}
	
	public void run()
	{
		this.log("========>启动处理器对象服务线程<=======");
		String compareTime=m_processor.getConfig("CompareTime");//读写文件时间 格式HH:mm:ss
		String compareMode=m_processor.getConfig("CompareMode");//是否自动对账 0：是  1：否
		int hour=0;
		int minute=0;
		int second=0;
		boolean run=false;//主线程是否运行
		String[] time = compareTime.split(":");
		if(compareMode.equals("0") && time.length >= 3) 
		{
			try
			{
				hour=Integer.parseInt(time[0]);
				minute=Integer.parseInt(time[1]);
				second=Integer.parseInt(time[2]);
				run=true;
			} 
			catch (Exception e)
			{
				this.log("配置文件中自动对账时间的格式错误！");
			}
		}
		boolean isRun=false; //用来控制是否对账
		while(run)
		{
			//系统时间
			Calendar sysCalendar = Calendar.getInstance();
			//获取对账信息时间
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY,hour);
			calendar.set(Calendar.MINUTE,minute);
			calendar.set(Calendar.SECOND,second);
			
			if (sysCalendar.before(calendar))
			{
				isRun = true;
			}
			if (sysCalendar.after(calendar) && isRun) 
			{
				try 
				{
					if(m_processor.setMoneyInfo(null,sysCalendar.getTime())!=0)
					{
						this.log("给银行发送数据错误!");
					}
					else
					{
						this.log(sysCalendar.getTime()+"给银行发送数据成功!");
					}
					if(m_processor.getBankCompareInfo(null,sysCalendar.getTime())!=0)
					{
						this.log("获取银行对账数据错误!");
					}
					else
					{
						this.log(sysCalendar.getTime()+"获取银行对账数据成功!");
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				isRun = false;
			}

			try
			{
				sleep(20*1000);
			}
			catch (InterruptedException e)
			{
				this.log("自动对账sleep错误："+e.getMessage());
			}
		}
	}
}

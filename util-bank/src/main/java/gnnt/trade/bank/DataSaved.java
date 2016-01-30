package gnnt.trade.bank;

import java.util.Calendar;

public class DataSaved extends Thread{

	
	private String compareTime="00:00:00";
	private Calendar sysCalendar = Calendar.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private String[] time = compareTime.split(":");
	boolean dataSaved = true;
	public DataSaved(boolean dataSaved)
	{
		this.dataSaved = dataSaved;
	}
	public void run() {
		calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
		calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
		calendar.set(Calendar.SECOND,Integer.parseInt(time[2]));
		boolean a = true;
		while(a)
		{
			if(sysCalendar.after(calendar))
			{
				dataSaved = false;
				a = false;
			}
			//System.out.println("------>dataSaved<---------"+dataSaved);
		}
		try {
			sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new DataSaved(false).start();
	}
}

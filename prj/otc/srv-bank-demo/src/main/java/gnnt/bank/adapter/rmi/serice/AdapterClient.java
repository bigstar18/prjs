package gnnt.bank.adapter.rmi.serice;
import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.util.Tool;
public class AdapterClient {
	public BankAdapter adapter = null;
	public AdapterClient(){
	}
	/**
	 * 初始化适配器
	 */
	public void initAdapter(){
			adapter = BankAdapter.getInstance();
			adapter.start();
			Tool.log("初始化适配器完成");
	}
}

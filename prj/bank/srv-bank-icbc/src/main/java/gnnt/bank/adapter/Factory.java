package gnnt.bank.adapter;

import gnnt.bank.adapter.bankBusiness.BankBusiness;
import gnnt.bank.adapter.rmi.service.RmiClient;
import gnnt.bank.adapter.socket.SocketClient;
import gnnt.bank.adapter.socket.SocketServer;

public class Factory {
	private BankAdapter bankAdapter = null;
	private BankBusiness bankBusiness = null;
	private ObjTransformer objTransfomer = null;
	private SocketClient socketClient = null;
	private RmiClient rmiClient = null;
	private SocketServer socketServer = null;

	private static Factory factory = new Factory();

	public static Factory getInstance() {
		return factory;
	}

	public BankAdapter getBankAdapter() {
		if (this.bankAdapter == null) {
			try {
				this.bankAdapter = ((BankAdapter) Class.forName(BankAdapter.class.getPackage().getName() + "." + BankAdapter.getConfig("AdapterImpl"))
						.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return this.bankAdapter;
	}

	public BankBusiness getBankBusiniss() {
		if (this.bankBusiness == null) {
			this.bankBusiness = new BankBusiness();
		}
		return this.bankBusiness;
	}

	public SocketClient getClient() {
		String socketIp = BankAdapter.getConfig("socketIp");
		int socketPort = Integer.parseInt(BankAdapter.getConfig("socketPort"));
		this.socketClient = new SocketClient(socketIp, socketPort);

		return this.socketClient;
	}

	public ObjTransformer getObjTransfomer() {
		if (this.objTransfomer == null) {
			this.objTransfomer = new ObjTransformer();
		}
		return this.objTransfomer;
	}

	public RmiClient getRmiClient() {
		if (this.rmiClient == null) {
			this.rmiClient = new RmiClient();
		}
		return this.rmiClient;
	}

	public SocketServer getSocketServer() throws Exception {
		if (this.socketServer == null) {
			this.socketServer = new SocketServer(Integer.parseInt(BankAdapter.getConfig("localSocketPort")));
		}
		return this.socketServer;
	}
}
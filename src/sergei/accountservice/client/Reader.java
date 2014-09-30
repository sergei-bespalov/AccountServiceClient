package sergei.accountservice.client;

import java.rmi.RemoteException;
import java.sql.SQLException;

import sergei.accountservice.intf.AccountService;

public class Reader implements Runnable {
	public AccountService serv = null;
	public Integer id = null;
	/*
	 * Caller AccountService.getAmount(id)
	 * @param service AccountService object
	 * @param balanceId balance identifier
	 */
	public Reader(AccountService service, Integer balanceId){
		serv = service;
		id = balanceId;
	}
	@Override
	public void run() {
		try {
			serv.getAmount(id);
		} catch (RemoteException | SQLException e) {
			System.err.println("Exception: "+e);
		}
	}
}

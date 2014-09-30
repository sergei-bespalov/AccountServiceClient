package sergei.accountservice.client;

import java.rmi.RemoteException;
import java.sql.SQLException;

import sergei.accountservice.intf.AccountService;

public class Writer implements Runnable {
	public AccountService serv = null;
	public Integer id = null;
	public Long value = null;
	/*
	 * Caller AccountService.addAmount(id,value)
	 * @param service AccountService object
	 * @param balanceId balance identifier
	 * @param value positive or negative value, which must be added to current balance
	 */
	public Writer(AccountService service, Integer balanceId, Long tValue){
		serv = service;
		value = tValue;
		id = balanceId;
	}
	@Override
	public void run() {
		try {
			serv.addAmount(id,value);
		} catch (RemoteException | SQLException e) {
			System.err.println("Exception: "+e);
		}
	}
}

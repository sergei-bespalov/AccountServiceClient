package sergei.accountservice.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sergei.accountservice.intf.AccountService;

public class Client {
	public  	String             ServerURL = null;
	public 		Integer            rCount    = null;
	public 		Integer            wCount    = null;
	public 		ArrayList<Integer> idList    = null;
	
	public static void main(String[] args)
	{
		System.out.println("start");
		Client client = new Client();
		try {
			client.readSettings("client_settings.xml");
		} catch (NumberFormatException | SAXException | IOException | ParserConfigurationException e) {
			System.err.println("Exception: "+e);
			return;
		}
		try {
			AccountService serv = (AccountService)Naming.lookup(client.ServerURL);
			Random rnd = new Random();
			ArrayList<Thread> treads = new ArrayList<Thread>();
			while(client.rCount != 0 || client.wCount !=0){
				int method = rnd.nextInt(10000)%2;
				int rndId  = client.idList.get
						(rnd.nextInt(client.idList.size()));
				int rndValue  =  rnd.nextInt();
				if(method != 0 && client.wCount > 0){
					Thread tr = new Thread(new Writer(serv,rndId,(long)rndValue));
					treads.add(tr);
					tr.start();
					client.wCount--;
				}
				if(method == 0 && client.rCount > 0){
					Thread tr = new Thread(new Reader(serv,rndId));
					treads.add(tr);
					tr.start();
					client.rCount--;
				}
				int maxThreadsCount = rnd.nextInt(100);
				if (treads.size() > maxThreadsCount){
					for(Thread tr: treads){
						try {
							tr.join();
						} catch (InterruptedException e) {
							System.err.println("Exception: "+e);
						}
					}
					treads.clear();
				}
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			System.err.println("Exception: "+e);
			return;
		}
		System.out.println("finish");
	}
	/*
	 * Read settings from the configuration file
	 * @param path to the configuration file
	 */
	public void readSettings(String path) throws SAXException, IOException, ParserConfigurationException, NumberFormatException{
		SettingsReader sr = new SettingsReader();
		sr.readSettings(path);
		ServerURL = "rmi://"+sr.RMIAdrress().getHostAddress()+":"+sr.RMIPort()+"/AccountService";
		rCount = sr.rCount();
		wCount = sr.wCount();
		idList = parseIdList(sr.idList());
	}
	/*
	 * Converting string containing identifiers to array
	 * @param idList string containing identifiers
	 */
	public static ArrayList<Integer> parseIdList(String idList) throws NumberFormatException{
		idList = idList.replace(" ","");
		String[] splitList = idList.split(",");
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(String id: splitList){
			if(id.contains("-")){
				int centr = id.indexOf("-");
				int first = Integer.parseInt(id.substring(0, centr));
				int last  = Integer.parseInt(id.substring(centr+1));
				for(int i = first; i <= last; i++){
					array.add(i);
				}
			}else{
				int i = Integer.parseInt(id);
				array.add(i);
			}
		}
		return array;
	}

}

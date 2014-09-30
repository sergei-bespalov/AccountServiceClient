package sergei.accountservice.client;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SettingsReader {
	private Integer      RMIPort        = null;
	private InetAddress  RMIAdrress     = null;
	private Integer      rCount         = null;
	private Integer      wCount         = null;
	private String       idList         = null;
	/*
	 * reads the configuration file
	 * @param path the path to the configuration file
	 */
	public  void readSettings(String path) 
			throws SAXException, IOException, ParserConfigurationException
	{
		File fXmlFile = new File(path);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		RMIPort = Integer.parseInt(doc.getElementsByTagName("RMIPort").item(0).getTextContent());
		RMIAdrress = (InetAddress) InetAddress.getByName(doc.getElementsByTagName("RMIAdrress").item(0).getTextContent());
		idList = doc.getElementsByTagName("idList").item(0).getTextContent();
		rCount = Integer.parseInt(doc.getElementsByTagName("rCount").item(0).getTextContent());
		wCount = Integer.parseInt(doc.getElementsByTagName("wCount").item(0).getTextContent());
	}
	
	public Integer RMIPort()
	{
		return RMIPort;
	}
	
	public InetAddress RMIAdrress()
	{
		return RMIAdrress;
	}
	
	public Integer rCount()
	{
		return rCount;
	}
	
	public Integer wCount()
	{
		return wCount;
	}
	
	public String idList()
	{
		return idList;
	}
	
	public void reset()
	{
		RMIPort = null;
		RMIAdrress = null;
		rCount = null;
		wCount = null;
		idList = null;	
	}
}


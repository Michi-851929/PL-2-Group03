import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;


public class ClientConnect{
	String id = null;
	String pass = null;
	String message = null;
	private static SSLContext sslc;
	private static SSLSocketFactory sslf;
	ClientConnect() throws Exception{
		try {
			sslc = SSLContext.getDefault();
			sslf = sslc.getSocketFactory();
		}catch(Exception e) {
			throw e;
		}
	}
	static Message post(Object o) throws Exception{
		try {
			Socket s = sslf.createSocket(ConnectName.name,ConnectName.port);
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectInputStream ois = new ObjectInputStream(is);
			oos.writeObject(o);
			Message ans = (Message)ois.readObject();
			return ans;
		}catch(Exception e) {
			throw e;
		}
	}
	
	int createAccount(String i,String p,String mac) {	
		Message tmp = new Message(i,p,0);
		Message ans = null;
		tmp.message = mac;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return ans.mode;
	}
	
	int login(String i,String p) {
		//送受信
		id = i;
		pass = p;
		return status;
	}
	
	Object getMonth(Calendar month) {
		//送受信
		if(status != 200) {
			return status;
		}
		CalendarMonth cm;
		return cm;
	}
	
	Object getDate(Calendar date) {
		//送受信
		if(status != 200) {
			return status;
		}
		CalendarMonth cm;
		return cm;
	}
	
	Object getEvent(ClientEvent event) {
		//送受信
		if(status != 200) {
			return status;
		}
		ClientEvent cm;
		return cm;
	}
	
	int nice(String event_id) {
		//送受信
		return status;
	}
	int joinEvent(String event_id) {
		//送受信
		return status;
	}
	
	int report(String event_id) {
		//送受信
		return status;
	}
	
	int sendMessage(String event_id,String message){
		//送受信
		return status;
	}
	
	int makeEvent(Event event) {
		//送受信
		return status;
	}
	
	int editEvent(Event event) {
		//送受信
		return status;
	}
	
	int deleteEvent(Event event) {
		//送受信
		return status;
	}
	
	int searchEvent(String search) {
		//送受信
		return status;
	}
	
	int makeCommunity(String name) {
		//送受信
		return status;
	}
	
	int searchCommunity(String search) {
		//送受信
		return status;
	}
	
	int serachCommunity(String name) {
		//送受信
		return status;
	}
	
	int quitCommunity(String name) {
		//送受信
		return status;
	}
	
	int changePassword(String new_password) {
		//送受信
		return status;
	}
	
	Object inquire() {
		//送受信
		if(status != 200) {
			return status;
		}
		String[] message = null;
		return message;
	}
}
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
			if(ans.mode == 1) {
				throw  new Exception("Not Found(user)");
			}else if(ans.mode == 2) {
				throw  new Exception("Banned user");
			}else if(ans.mode == 3) {
				throw  new Exception("Auth falled");
			}else if(ans.mode == 4) {
				throw  new Exception("Request falled");
			}
			return ans;
		}catch(Exception e) {
			throw e;
		}
	}
	
	void createAccount(String i,String p,String mac) throws Exception {	//アカウント作成
		Message tmp = new Message(i,p,0);
		Message ans = null;
		tmp.message = mac;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		if((int)ans.mode == 0) {
			this.id = i;
			this.pass = p;
		}
	}
	
	void login(String i,String p) throws Exception { //ログイン
		Message tmp = new Message(i,p,1);
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		if((int)ans.message == 0) {
			this.id = i;
			this.pass = p;
		}
	}
	
	CalendarMonth getMonth(Calendar month) throws Exception { //月データ取得
		Message tmp = new Message(this.id,this.pass,2);
		tmp.message = month;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (CalendarMonth)ans.message;
	}
	
	ArrayList<ClientEvent> getDate(Calendar date) throws Exception { //日データ取得
		Message tmp = new Message(this.id,this.pass,3);
		tmp.message = date;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (ArrayList<ClientEvent>) Arrays.asList((ClientEvent[])ans.message);
	}
	
	ClientEvent getEvent(ClientEvent event) throws Exception { //イベント(1個)取得
		Message tmp = new Message(this.id,this.pass,4);
		tmp.message = event;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (ClientEvent)ans.message;
	}
	
	void nice(String event_id) throws Exception { //いいね
		Message tmp = new Message(this.id,this.pass,5);
		tmp.message = event_id;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
	}
	void joinEvent(String event_id) throws Exception { //イベント参加
		Message tmp = new Message(this.id,this.pass,6);
		tmp.message = event_id;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	int report(String event_id) throws Exception { //イベント通報
		Message tmp = new Message(this.id,this.pass,7);
		tmp.message = event_id;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	void sendMessage(String event_id,String message) throws Exception{ //メッセージ送信
		Message tmp = new Message(this.id,this.pass,8);
		tmp.message = event_id;
		tmp.message2 = message;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	int makeEvent(ClientEvent event) throws Exception { //イベント作成
		Message tmp = new Message(this.id,this.pass,9);
		tmp.message = event;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	int editEvent(ClientEvent event) throws Exception { //イベント編集
		Message tmp = new Message(this.id,this.pass,10);
		tmp.message = event;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	int deleteEvent(ClientEvent event) throws Exception { //イベント削除
		Message tmp = new Message(this.id,this.pass,11);
		tmp.message = event;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	int searchEvent(String search) { //イベント検索
		//送受信
		return status;
	}
	
	int makeCommunity(String name) throws Exception { //コミュニティ作成
		Message tmp = new Message(this.id,this.pass,12);
		tmp.message = name;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	int searchCommunity(String search) { //コミュニティ検索
		//送受信
		return status;
	}
	
	
	int quitCommunity(String name) throws Exception { //コミュニティ脱退
		Message tmp = new Message(this.id,this.pass,13);
		tmp.message = name;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (int) ans.message;
	}
	
	int changePassword(String new_password) throws Exception { //パスワード変更
		Message tmp = new Message(this.id,this.pass,14);
		tmp.message = new_password;
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		if((int)ans.message == 0) {
			this.pass = new_password;
		}
		return (int) ans.message;
	}
	
	String[] inquire() throws Exception { //メッセージ問い合わせ
		Message tmp = new Message(this.id,this.pass,15);
		Message ans = null;
		try {
			ans = post(tmp);
		} catch (Exception e) {
			throw e;
		}
		return (String[]) ans.message;
	}
}
import java.util.Calendar;


public class ClientConnect{
	String id = null;
	String pass = null;
	String message = null;
	int status = 200;
	
	static String post(){
		//送受信関数
		String get = null;
		return get;
	}
	
	int createAccount(String i,String p,String mac) {	
		//送受信
		id = i;
		pass = p;
		return status;
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
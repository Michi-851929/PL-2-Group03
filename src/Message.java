import java.io.Serializable;

public class Message implements Serializable{
	//サーバー,クライアント共に使用
	int mode;
	Object message;
	String name;
	String pass;
	Message(String n,String p,int i,Object o){	
		name = n;
		pass = p;
		mode = i;
		message = o;
	}
}
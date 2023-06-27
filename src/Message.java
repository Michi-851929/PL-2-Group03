import java.io.Serializable;

public class Message implements Serializable{
	//サーバー,クライアント共に使用
	int mode;
	Object message;
	Message(int i,Object o){
		mode = i;
		message = o;
	}
}
import java.io.Serializable;

public class Message implements Serializable{
	int mode;
	Object message;
	Message(int i,Object o){
		mode = i;
		message = o;
	}
}
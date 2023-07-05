import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class ServerConnect{
	int mode = 0;
	SSLServerSocketFactory sf;
	Server se;
	ServerConnect(Server server) throws Exception{
		try (FileInputStream fis = new FileInputStream(Certificate.name);){
			KeyStore ks;
			KeyManagerFactory kmf;
			ks = KeyStore.getInstance("pkcs12");
			ks.load(fis,Certificate.pass.toCharArray());
			kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks,Certificate.pass.toCharArray());
			SSLContext tls  = SSLContext.getInstance("TLSv1.3");
			tls.init(kmf.getKeyManagers(),null,null);
			sf = tls.getServerSocketFactory();
			se = server;
	}catch(Exception e) {
			throw e;
		}
	}
	
	void run() throws Exception {
		this.mode = 1;
		SSLServerSocket ssss = (SSLServerSocket)sf.createServerSocket(ConnectName.port);
		try {
			while(this.mode == 1) {
				Socket s = ssss.accept();
				new ConnectThread(s,se).start();
			}
		}catch(Exception e) {
			throw e;
		}	
	}
	void stop() {
		this.mode = 0;
	}
}

class ConnectThread extends Thread{
	private Socket s;
	Server se;
	public ConnectThread(Socket socket,Server server) {
		this.s = socket;
	}
	
	public void run() {
		try {
		OutputStream os = s.getOutputStream();
		InputStream is = s.getInputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		ObjectInputStream ois = new ObjectInputStream(is);
		Message m = (Message) ois.readObject();
		Message ans = new Message(m.name,m.pass,0);
		//ここから各自処理
		if(m.mode == 0) {
			String name = m.name;
			String pass = m.pass;
			String mac = (String) m.message;
			if(se.isCreatableAccount(name,mac) == 1) {
				ans.message = 4;
			}else if(se.isCreatableAccount(name,mac) == 2) {
				ans.message = 5;
			}else {
				se.createAccount(name, pass, mac);
			}
		}else if(m.mode == 1) {
			ans.message = se.logIn(m.name,m.pass);
		}else {
			Account tmp = se.getAccount(m.name);
			if(tmp == new Account("", "", "")) {
				ans.mode = 1;
			}else if(tmp.isBanned()==true) {
				ans.mode = 2;
			}else if(tmp.verifyPassword(m.pass)==0) {
				ans.mode = 3;
			}else {
				if(m.mode == 2) {
					
				}
			}
		}
		oos.writeObject(ans);
		//ここまで各自処理
		}catch(Exception e) {
			
		}finally {
			try {
				if(s != null) {
					s.close();
				}
			}catch(Exception e) {
				
			}
		}
	}
}
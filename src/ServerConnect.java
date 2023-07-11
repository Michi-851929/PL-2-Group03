import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;

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
        this.se = server;
    }

    public void run() {
        try {
        OutputStream os = s.getOutputStream();
        InputStream is = s.getInputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        ObjectInputStream ois = new ObjectInputStream(is);
        Message m = (Message) ois.readObject();
        Message ans = new Message(m.name,m.pass,0);
        ans.message = null;
        ans.message2 = null;
        //ここから各自処理
        if(m.mode == 0) {
            String name = m.name;
            String pass = m.pass;
            String mac = (String) m.message;
            if(se.isCreatableAccount(name,mac) == Server.DUPLICATE_NAME) {
                ans.mode = 6;
            }else if(se.isCreatableAccount(name,mac) == Server.DUPLICATE_MAC) {
                ans.mode = 7;
            }else {
                se.createAccount(name, pass, mac);
            }
        }else if(m.mode == 1) {
            int tmp = se.logIn(m.name,m.pass);
            if(tmp == Server. LOGIN_NOTEXIST) {
                ans.mode = 1;
            }else if(tmp == Server.LOGIN_BANNED){
                ans.mode = 2;
            }
        }else {
            Account tmp = se.getAccount(m.name);
            if(tmp.equals(new Account("", "", "")) ) {
                ans.mode = 1;
            }else if(tmp.isBanned()==true) {
                ans.mode = 2;
            }else if(tmp.verifyPassword(m.pass)==0) {
                ans.mode = 3;
            }else {
                if(m.mode == 2) {
                    if(se.isCreatableCommunity((String)m.message) != Server.DUPLICATE_NAME) {
                        ans.mode = 4;
                    }else {
                        ans.message=se.getCommunity((String)m.message);
                    }
                }else if(m.mode == 3) {
                    ans.message = tmp;
                }else if(m.mode == 4) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))) {
                        ans.mode = 4;
                    }else{
                        ans.message = te;
                    }
                }else if(m.mode == 5) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))) {
                        ans.mode = 4;
                    }else{
                        if(Arrays.asList(tmp.getEventPreferred()).contains((String)m.message)) {
                            se.setDispreferEvent(m.name, (String)m.message);
                        }else {
                            se.setPreferredEvent(m.name, (String)m.message);
                        }
                    }
                }else if(m.mode == 6) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))) {
                        ans.mode = 4;
                    }else{
                        if(Arrays.asList(tmp.getEventGoing()).contains((String)m.message)) {
                            se.setPresentEvent(m.name, (String)m.message);
                        }else {
                            se.setAbsentEvent(m.name, (String)m.message);
                        }
                    }
                }else if(m.mode == 7) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))) {
                        ans.mode = 4;
                    }else{
                        se.reportEvent();
                    }
                }else if(m.mode == 8) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))||te.getEventOwner()!=m.name) {
                        ans.mode = 4;
                    }else{
                        se.addHostMessage((String)m.message,(String) m.message2);
                    }
                }else if(m.mode == 9) {
                    ClientEvent te = (ClientEvent)m.message;
                    int[] it= (int[])m.message2;
                    se.createEvent(te,it[0],it[1],it[2],it[3] );
                }else if(m.mode == 10) {
                    ClientEvent te = (ClientEvent)m.message;
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))||te.getEventOwner()!=m.name) {
                        ans.mode = 4;
                    }else{
                        se.manageEvent(te);
                    }
                }else if(m.mode == 11) {
                    ClientEvent te = (ClientEvent)m.message;
                    if(te.equals(new ClientEvent("", 0, 0, "", "", "", "", "", "", ""))||te.getEventOwner()!=m.name) {
                        ans.mode = 4;
                    }else{
                        se.deleteEvent(te.getEventId(),0,0);//保留中
                    }
                }else if(m.mode == 12) {
                    Community tc = (Community)m.message;
                    if(se.isCreatableCommunity(tc.getName())==Server.DUPLICATE_NAME) {
                        ans.mode = 4;
                    }else {
                        se.createCommunity(tc);
                    }
                }else if(m.mode == 13) {
                    if(se.isCreatableCommunity((String)m.message) == Server.DUPLICATE_NAME) {
                        ans.message = false;
                    }else {
                        ans.message = true;
                    }
                }else if(m.mode == 14) {
                    ans.message=se.searchCommunity((String)m.message);
                }else if(m.mode == 15) {
                    if(se.isCreatableCommunity((String)m.message) != Server.DUPLICATE_NAME||Arrays.asList(tmp.getCommunity()).contains((String)m.message)) {
                        ans.mode = 4;
                    }else {
                        se.joinCommunity((String)m.message, m.name);
                    }
                }else if(m.mode == 16) {
                    if(se.isCreatableCommunity((String)m.message) != Server.DUPLICATE_NAME||!Arrays.asList(tmp.getCommunity()).contains((String)m.message)){
                        ans.mode = 4;
                    }else {
                        se.quitCommunity((String)m.message, m.name);
                    }
                }else if(m.mode == 17) {
                    se.changePassword(m.name, m.pass, (String)m.message);
                }
                else {
                    ans.mode = 5;
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
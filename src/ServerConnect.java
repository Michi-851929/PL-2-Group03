import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.SSLServerSocketFactory;

public class ServerConnect{
    int mode = 0;
    SSLServerSocketFactory sf;
    Server se;
    ServerConnect(Server server) throws Exception{
        /*

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
        */
        
        se = server;
    }

    void run() throws Exception {
        this.mode = 1;
        //SSLServerSocket ssss = (SSLServerSocket)sf.createServerSocket(ConnectName.port);//これをコメントアウト
        ServerSocket ssss = new ServerSocket(ConnectName.port);
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
    int flag = 0;
    String name;
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
            }else if(tmp == Account.PASS_FALSE) {
                ans.mode = 3;
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
                    flag=1;
                    name = m.name;
                }else if(m.mode == 4) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")) {
                        ans.mode = 4;
                    }else{
                        ans.message = te;
                    }
                }else if(m.mode == 5) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")) {
                        ans.mode = 4;
                    }else{
                        if(tmp.getAEventPreferrd((String)m.message)) {
                            se.setDispreferEvent(m.name, (String)m.message);
                            ans.message = false;
                        }else {
                            se.setPreferredEvent(m.name, (String)m.message);
                            ans.message = true;
                        }
                    }
                }else if(m.mode == 6) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")) {
                        ans.mode = 4;
                    }else{
                        if(tmp.getAEventGoing((String)m.message)) {
                            ans.message = false;
                        }else {
                            se.setPresentEvent(m.name, (String)m.message);
                            ans.message = true;
                        }
                    }
                }else if(m.mode == 7) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")) {
                        ans.mode = 4;
                    }else{
                        int[] it = (int[])m.message2;
                        se.reportEvent((String) m.message,it[0],it[1]);
                    }
                }else if(m.mode == 8) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")||!te.getEventOwner().equals(m.name)) {
                        ans.mode = 4;
                    }else{

                        se.addHostMessage((String)m.message,(String) m.message2);
                    }
                }else if(m.mode == 9) {
                    ClientEvent te = (ClientEvent)m.message;
                    int[] it= (int[])m.message2;
                    ans.message=se.createEvent(te,it[0],it[1],it[2],it[3] );
                }else if(m.mode == 10) {
                    ClientEvent te = (ClientEvent)m.message;
                    if(te.getEventName().equals("")||!te.getEventOwner().equals(m.name)) {
                        ans.mode = 4;
                    }else{
                        se.manageEvent(te);
                    }
                }else if(m.mode == 11) {
                    ClientEvent te = (ClientEvent)m.message;
                    if(te.getEventName().equals("")||!te.getEventOwner().equals(m.name)) {
                        ans.mode = 4;
                    }else{
                        int[] it = (int[])m.message2;
                        se.deleteEvent(te.getEventId(),it[0],it[1]);
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
                    ans.message = se.changePassword(m.name, m.pass, (String)m.message);
                }else if(m.mode == 18) {
                    String[] st=(String[]) m.message;
                    ArrayList<ClientEvent> at= new ArrayList<>();
                    ClientEvent te;
                    int flag = 0;
                    for(int i = 0;i< st.length;i++) {
                        te = se.getEvent(st[i]);
                        if(!te.getEventName().equals("")) {
                            flag = 1;
                            at.add(te);
                        }
                    }
                    if(flag == 0) {
                        ans.mode = 4;
                    }else {
                        ans.message = at;
                    }
                }else if(m.mode == 19) {
                    String[] st=(String[]) m.message;
                    ArrayList<Community> at= new ArrayList<>();
                    Community te;
                    int flag = 0;
                    for(int i = 0;i< st.length;i++) {
                        te = se.getCommunity(st[i]);
                        if(!te.getName().equals("")) {
                            flag = 1;
                            at.add(te);
                        }
                    }
                    if(flag == 0) {
                        ans.mode = 4;
                    }else {
                        ans.message = at;
                    }
                }else if(m.mode == 20) {
                    ClientEvent te=se.getEvent((String) m.message);
                    if(te.getEventName().equals("")) {
                        ans.mode = 4;
                    }else{
                        if(!tmp.getAEventGoing((String)m.message)) {
                            ans.message = false;
                        }else {
                            se.setAbsentEvent(m.name, (String)m.message,(String)m.message2);
                            ans.message = true;
                        }
                    }
                }else {
                    ans.mode = 5;
                }
            }
            if(m.mode==3) {
            System.out.println(tmp.getLastCheckTime());
            System.out.println(LocalDateTime.now());
            }
        }

        oos.writeObject(ans);
        //ここまで各自処理
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(s != null) {
                    s.close();
                }
                if(flag ==1) {
                    se.getAccount(name).setLastCheckTime();
                }
            }catch(Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
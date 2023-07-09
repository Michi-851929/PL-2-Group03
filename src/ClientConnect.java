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
            if(ans.mode == 1) {
                throw  new Exception("Not Found(user)");
            }else if(ans.mode == 2) {
                throw  new Exception("Banned user");
            }else if(ans.mode == 3) {
                throw  new Exception("Auth falled");
            }else if(ans.mode == 4) {
                throw  new Exception("Request falled");
            }else if(ans.mode == 5) {
            	throw  new Exception("Not Found(Request)");
            }else if(ans.mode == 6) {
            	throw  new Exception("Duplication(user)");
            }else if(ans.mode == 7) {
            	throw  new Exception("Dupilicaion(mac)");
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

    Community getCommunity(String name) throws Exception { //コミュニティ取得
        Message tmp = new Message(this.id,this.pass,2);
        tmp.message = name;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Community)ans.message;
    }

   Account getAccount(Calendar date) throws Exception { //アカウント取得
        Message tmp = new Message(this.id,this.pass,3);
        tmp.message = date;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Account)ans.message;
    }

    ClientEvent getEvent(String event) throws Exception { //イベント(1個)取得
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

    void nice(String event_id) throws Exception { //いいね切り替え
        Message tmp = new Message(this.id,this.pass,5);
        tmp.message = event_id;
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }
    void joinEvent(String event_id) throws Exception { //イベント参加切り替え
        Message tmp = new Message(this.id,this.pass,6);
        tmp.message = event_id;
        try {
            post(tmp);
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
    /*イベント検索はクライアントの持つデータの中からの検索なので要りません 玖津見
    int searchEvent(String search) { //イベント検索
        //送受信
        return status;
    }
    */
    void makeCommunity(Community comm) throws Exception { //コミュニティ作成
        Message tmp = new Message(this.id,this.pass,12);
        tmp.message = comm;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }
    
    Boolean checkCommunity(String name) throws Exception { //コミュニティ確認
        Message tmp = new Message(this.id,this.pass,13);
        tmp.message = name;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Boolean)ans.message;//作れるならtrue,無理ならfalse
    }
    
    int searchCommunity(String search) { //コミュニティ検索
        //送受信 検索語を入れるとCommunity[]を返すメソッドsearchCommunity(String)がサーバにあります 玖津見
        return status;
    }

    void joinCommunity(String name) throws Exception { //コミュニティ加入
        Message tmp = new Message(this.id,this.pass,15);
        tmp.message = name;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    void quitCommunity(String name) throws Exception { //コミュニティ脱退
        Message tmp = new Message(this.id,this.pass,16);
        tmp.message = name;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    void changePassword(String new_password) throws Exception { //パスワード変更
        Message tmp = new Message(this.id,this.pass,17);
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
    }

    String[] inquire() throws Exception { //メッセージ問い合わせ
        Message tmp = new Message(this.id,this.pass,18);
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (String[]) ans.message;
    }
}
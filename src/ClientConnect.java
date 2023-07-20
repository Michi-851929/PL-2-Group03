import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;


public class ClientConnect{
    String id = null;
    String pass = null;
    private static SSLContext sslc;
    private static SSLSocketFactory sslf;

    public final static String NOT_FOUND = "Not Found(user)";//該当ユーザーがいない
    public final static String BANNED = "Banned user";//BANされてるユーザー
    public final static String AUTH = "Auth falled";//ユーザーはいるけどパスワードが違う
    public final static String ERROR = "Request falled";//リクエスト失敗(普通のエラー)
    public final static String REQ_FAILED = "Not Found(Request)";//該当するリクエストがないとき(呼ばれることはないはず)
    public final static String USER = "Duplication(user)";//アカウント作成時のみ投げます(user重複)
    public final static String MAC = "Dupilicaion(mac)";//アカウント作成時のみ投げます(mac重複)


    ClientConnect() throws Exception{
        try {
            sslc = SSLContext.getDefault();
            sslf = sslc.getSocketFactory();
        }catch(Exception e) {
            throw e;
        }
    }
    void logout() {
        id = null;
        pass = null;
    }
    static Message post(Object o) throws Exception{
        try {
            Socket s = sslf.createSocket(ConnectName.name,ConnectName.port);//これをコメントアウト
            ///Socket s = new Socket(ConnectName.name,ConnectName.port);
            OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);
            oos.writeObject(o);
            Message ans = (Message)ois.readObject();
            if(ans.mode == 1) {
                throw  new Exception(NOT_FOUND);//該当ユーザーがいない
            }else if(ans.mode == 2) {
                throw  new Exception(BANNED);//BANされてるユーザー
            }else if(ans.mode == 3) {
                throw  new Exception(AUTH);//ユーザーはいるけどパスワードが違う
            }else if(ans.mode == 4) {
                throw  new Exception(ERROR);//リクエスト失敗(普通のエラー)
            }else if(ans.mode == 5) {
                throw  new Exception(REQ_FAILED);//該当するリクエストがないとき(呼ばれることはないはず)
            }else if(ans.mode == 6) {
                throw  new Exception(USER);//アカウント作成時のみ投げます(user重複)
            }else if(ans.mode == 7) {
                throw  new Exception(MAC);//アカウント作成時のみ投げます(mac重複)
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
        if(ans.mode == 0) {
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

   Account getAccount(String name) throws Exception { //アカウント取得
        Message tmp = new Message(this.id,this.pass,3);
        tmp.message = name;
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

    Boolean nice(String event_id) throws Exception { //いいね切り替え
        Message tmp = new Message(this.id,this.pass,5);
        tmp.message = event_id;
        Message ans =null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Boolean)ans.message;
    }

    Boolean joinEvent(String event_id) throws Exception { //イベント参加
        Message tmp = new Message(this.id,this.pass,6);
        tmp.message = event_id;
        Message ans =null;
        try {
            ans =post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Boolean) ans.message;
    }

    int report(String event_id,int year,int month) throws Exception { //イベント通報
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
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    Boolean makeEvent(ClientEvent event,int day,int month,int start, int end) throws Exception { //イベント作成(返り値変更)
        Message tmp = new Message(this.id,this.pass,9);
        tmp.message = event;
        int[] it = {day,month,start,end};
        tmp.message2 = it;
        Message ans= null;
        try {
            ans= post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Boolean)ans.message;
    }

    void editEvent(ClientEvent event) throws Exception { //イベント編集
        Message tmp = new Message(this.id,this.pass,10);
        tmp.message = event;
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    void deleteEvent(ClientEvent event,int year,int month) throws Exception { //イベント削除
        Message tmp = new Message(this.id,this.pass,11);
        tmp.message = event;
        int[] it = {year,month};
        tmp.message2 = it;
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
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
        try {
            post(tmp);
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

    Community[] searchCommunity(String search) throws Exception { //コミュニティ検索
        //送受信 検索語を入れるとCommunity[]を返すメソッドsearchCommunity(String)がサーバにあります 玖津見
        Message tmp = new Message(this.id,this.pass,14);
        tmp.message = search;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Community[])ans.message;
    }

    void joinCommunity(String name) throws Exception { //コミュニティ加入
        Message tmp = new Message(this.id,this.pass,15);
        tmp.message = name;
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    void quitCommunity(String name) throws Exception { //コミュニティ脱退
        Message tmp = new Message(this.id,this.pass,16);
        tmp.message = name;
        try {
            post(tmp);
        } catch (Exception e) {
            throw e;
        }
    }

    void changePassword(String new_password) throws Exception { //パスワード変更
        Message tmp = new Message(this.id,this.pass,17);
        tmp.message = new_password;
        Message ans = null;
        try {
            ans=post(tmp);
        } catch (Exception e) {
            throw e;
        }
        if((int)ans.message == Account.PASS_CORRECT) {
            this.pass = new_password;
        }
    }

    @SuppressWarnings("unchecked")
    ArrayList<ClientEvent> getEvents(String[] event) throws Exception { //イベント(複数)取得
        Message tmp = new Message(this.id,this.pass,18);
        tmp.message = event;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (ArrayList<ClientEvent>)ans.message;
    }

    @SuppressWarnings("unchecked")
    ArrayList<Community> getCommunitys(String[] name) throws Exception { //コミュニティ取得
        Message tmp = new Message(this.id,this.pass,19);
        tmp.message = name;
        Message ans = null;
        try {
            ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (ArrayList<Community>)ans.message;
    }

    Boolean AbsentEvent(String event_id,String cause) throws Exception { //イベント参加
        Message tmp = new Message(this.id,this.pass,20);
        tmp.message = event_id;
        tmp.message2 = cause;
        Message ans = null;
        try {
           ans = post(tmp);
        } catch (Exception e) {
            throw e;
        }
        return (Boolean)ans.message;
    }
}
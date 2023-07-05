import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{
    private String name;
    private String password;
    private String mac_adress;
    private boolean is_banned;
    private ArrayList<String> community_involved = new ArrayList<>();
    private ArrayList<String> event_going = new ArrayList<>();
    private ArrayList<String> event_preferred = new ArrayList<>();
    
    //パスワード認証の戻り値を表す定数
    public final static int PASS_CORRECT = 1; //正しい
    public final static int PASS_FALSE = 0; //正しくない
    
    //コンストラクタ
    public Account(String name, String password, String mac_adress)
    {
        this.name = name;
        this.password = password;
        this.mac_adress = mac_adress;
        is_banned = false;
    }
    
    //ユーザ名取得
    public String getUserName()
    {
        return name;
    }
    
    //MACアドレス取得
    public String getMacAdress()
    {
        return mac_adress;
    }
    
    //アカウント追放
    public void banAccount()
    {
        is_banned = true;
    }
    
    //アカウント復活
    public void permitAccount()
    {
        is_banned = false;
    }
    
    //BAN状態取得
    public boolean isBanned()
    {
        return is_banned;
    }
    
    //パスワードを認証
    //正しいとき(int)PASS_CORECT, 正しくないとき(int)PASS_FALSEを返す
    public int verifyPassword(String password)
    {
        if(password.equals(this.password)) {
            return PASS_CORRECT;
        }
        else {
            return PASS_FALSE;
        }
    }
    
    //新旧パスワードを入力してパスワードを更新
    //旧パスワードが違うと失敗する
    //返り値はverifyPasswordに準ずる
    public int setPassword(String old_password, String new_password)
    {
        int pass_result = verifyPassword(old_password);
        if(pass_result == PASS_CORRECT) {
            password = new_password;
        }
        return pass_result;
    }
    
    //所属コミュニティを追加
    public void addCommunity(String community)
    {
        community_involved.add(community);
    }
    
    //所属コミュニティを脱退
    public void removeCommunity(String community)
    {
        community_involved.remove(community);
    }
    
    //所属コミュニティを取得
    public String[] getCommunity()
    {
        return (String[])community_involved.toArray();
    }
    
    //参加イベントを追加
    public void addEventGoing(String event)
    {
        event_going.add(event);
    }
    
    //参加イベントを取り消し
    public void removeEventGoing(String event)
    {
        event_going.remove(event);
    }
    
    //参加イベントを取得
    public String[] getEventGoing()
    {
        return (String[])event_going.toArray();
    }
    
    //いいねしたイベントを追加
    public void addEventPreferred(String event)
    {
        event_preferred.add(event);
    }
    
    //いいねしたイベントを取り消し
    public void removeEventPreferred(String event)
    {
        event_preferred.remove(event);
    }
    
    //いいねしたイベントを取得
    public String[] getEventPreferred()
    {
        return (String[])event_preferred.toArray();
    }
}

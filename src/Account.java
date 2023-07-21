import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Account implements Serializable{
    private String name;
    private String password;
    private String mac_adress;
    private boolean is_banned;
    private int lastcheck[] = new int[7];
    private ArrayList<String> community_involved = new ArrayList<>();
    private ArrayList<String> event_going = new ArrayList<>();
    private ArrayList<String> event_preferred = new ArrayList<>();
    private ArrayList<String> event_made = new ArrayList<>();
    int today_eventnum=0;
    //パスワード認証の戻り値を表す定数
    public final static int PASS_CORRECT = 1; //正しい
    public final static int PASS_FALSE = 0; //正しくない
    
    private Calendar today = Calendar.getInstance();

    //コンストラクタ
    public Account(String name, String password, String mac_adress)
    {
        this.name = name;
        this.password = password;
        this.mac_adress = mac_adress;
        is_banned = false;
        lastcheck[0] = LocalDateTime.now().getYear();
        lastcheck[1] = LocalDateTime.now().getMonthValue();
        lastcheck[2] = LocalDateTime.now().getDayOfMonth();
        lastcheck[3] = LocalDateTime.now().getHour();
        lastcheck[4] = LocalDateTime.now().getMinute();
        lastcheck[5] = LocalDateTime.now().getSecond();
        lastcheck[6] = LocalDateTime.now().getNano();
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

    public LocalDateTime getLastCheckTime()
    {
        LocalDateTime time = LocalDateTime.of(lastcheck[0], lastcheck[1], lastcheck[2], lastcheck[3], lastcheck[4], lastcheck[5], lastcheck[6]);

        return time;
    }
    
    public int[] getLastCheckInt(){
    	return lastcheck;
    }

    public void setLastCheckTime()
    {
        lastcheck[0] = LocalDateTime.now().getYear();
        lastcheck[1] = LocalDateTime.now().getMonthValue();
        lastcheck[2] = LocalDateTime.now().getDayOfMonth();
        lastcheck[3] = LocalDateTime.now().getHour();
        lastcheck[4] = LocalDateTime.now().getMinute();
        lastcheck[5] = LocalDateTime.now().getSecond();
        lastcheck[6] = LocalDateTime.now().getNano();
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
        return (String[])community_involved.toArray(new String[community_involved.size()]);
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
    public ArrayList<String> getEventGoing()
    {
        return event_going;
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
    public ArrayList<String> getEventPreferred()
    {
        return event_preferred;
    }
    
    //あるイベントに参加しているか
    public boolean getAEventGoing(String s) {
    	for(String str: event_going) {
    		if(str.equals(s)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //あるイベントにいいねしているか
    public boolean getAEventPreferrd(String s) {
    	for(String str: event_preferred) {
    		if(str.equals(s)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //作成したイベントを追加
    public void addEventMade(String event) {
    	event_made.add(event);
    }
    //作成したイベントを取得
    public ArrayList<String> getEventMade()
    {
        return event_made;
    }
    
    //作成したイベントの総数を取得
    public int getTotalEventMade() {
    	return event_made.size();
    }
    
    public boolean getEventMakeSig(Server se) //いいね数からイベント作成できるかを判断
    {
        int total_good=0;
        int limit_eventnum;
        for(String event_id : this.event_made) {
            total_good+=se.getEvent(event_id).getGood();
        }
        if(total_good<10) {
            limit_eventnum=2;
        }else if(total_good<50) {
            limit_eventnum=5;
        }else if(total_good<100) {
            limit_eventnum=10;
        }else if(total_good<500) {
            limit_eventnum=20;
        }else {
            limit_eventnum=30;
        }
        se.stdout("limit_eventnum : " + Integer.toString(limit_eventnum));
        se.stdout("total_good : " + Integer.toString(total_good));

        if(today_eventnum<limit_eventnum) {
            today_eventnum++;
            today=this.getToday();
            return true;
        }else {
            if(this.getToday().compareTo(today) == 0) {
                return false;
            }else {
                today_eventnum=1;
                today=this.getToday();
                return true;
            }
        }
    }


    public Calendar getToday() {//その日の日付(時分秒切り捨て)を取得
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar;
    }

}

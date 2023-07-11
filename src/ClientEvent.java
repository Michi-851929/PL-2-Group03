import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientEvent implements Serializable{
        private static final long serialVersionUID = 1L;
        private String eve_id;
        private int eve_year;
        private int eve_month;
        private String eve_name;
        private String eve_start;
        private String eve_finish;
        private String eve_place;
        private String eve_owner;
        private String eve_outline;
        private String eve_detail;
        private int eve_good = 0;
        private int eve_join = 0;
        private String eve_com_name;
        private ArrayList<String> eve_own_message = new ArrayList<>();
        private ArrayList<int[]> eve_own_messagetime = new ArrayList<>();
        private ArrayList<String> eve_cancel_name = new ArrayList<>();
        private ArrayList<int[]> eve_cancel_time = new ArrayList<>();
        private ArrayList<String> eve_cancel_message = new ArrayList<>();

        public ClientEvent(String name, int year, int month, String start, String finish, String place, String owner, String outline, String detail, String com){
        eve_name = name;
        eve_year = year;
        eve_month = month;
        eve_start = start;
        eve_finish = finish;
        eve_place = place;
        eve_owner = owner;
        eve_outline = outline;
        eve_detail = detail;
        eve_com_name = com;
    }

    //イベントID設定//
    public void setEventId(String id) {
        eve_id = id;
    }

    //イベントID取得//
    public String getEventId() {
        return eve_id;
    }

    //イベント名変更//
    public void setEventName(String new_name) {
        eve_name = new_name;
    }

    //イベント名取得//
    public String getEventName() {
        return eve_name;
    }

    public int getYear()
    {
        return eve_year;
    }

    public int getMonthValue()
    {
        return eve_month;
    }

    //開始時刻変更//
    public void setEventStart(String new_start) {
        eve_start = new_start;
    }

    //開始時刻取得//
    public String getEventStart() {
        return eve_start;
    }

    //終了時刻変更//
    public void setEventFinish(String new_finish) {
        eve_finish = new_finish;
    }

    //終了時刻取得//
    public String getEventFinish() {
        return eve_finish;
    }

    //開催場所変更//
    public void setEventPlace(String new_place) {
        eve_place = new_place;
    }

    //開催場所取得//
    public String getEventPlace() {
        return eve_place;
    }
    /*
    //主催者名変更//
    public void editEventOwner(String new_owner) {
        eve_owner = new_owner;
    }
    */
    //主催者名取得//
    public String getEventOwner() {
        return eve_owner;
    }

    //イベント概要変更//
    public void setEventOutline(String new_outline) {
        eve_outline = new_outline;
    }

    //イベント概要取得//
    public String getEventOutline() {
        return eve_outline;
    }

    //イベント詳細変更//
    public void setEventDetail(String new_detail) {
        eve_detail = new_detail;
    }

    //イベント詳細取得//
    public String getEventDetail() {
        return eve_detail;
    }
/*
    //所属コミュニティ名変更//
    public void setEventCommunity(Community new_com) {
        eve_com_name = new_com.getName();
    }
*/
    //所属コミュニティ名取得//
    public String getEventCommunityName() {
        return eve_com_name;
    }

    //主催者メッセージ設定//
    public void setOwnerMessage(String message) {
        LocalDateTime ldt = LocalDateTime.now();
        int[] time = {ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond(), ldt.getNano()};
        eve_own_messagetime.add(time);
        eve_own_message.add(message);
    }

    //主催者メッセージ取得//
    public String[] getOwnerMessage() {
        return (String[])eve_own_message.toArray();
    }

    public LocalDateTime getLastUpdateTime()
    {
        int[] time = eve_own_messagetime.get(eve_own_messagetime.size() - 1);
        return LocalDateTime.of(time[0], time[1], time[2], time[3], time[4], time[5], time[6]);
    }

    //キャンセル理由設定//
    public void sendCancel(String user_name, String message) {
        //Calendar calendar = Calendar.getInstance();
        LocalDateTime ldt = LocalDateTime.now();
        int[] time = {ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond(), ldt.getNano()};
        eve_cancel_name.add(user_name);
        eve_cancel_time.add(time); //時間取得
        eve_cancel_message.add(message);
    }

    //キャンセル者取得//
    public String[] getCancelName() {
        return (String[])eve_cancel_name.toArray();
    }

    //キャンセル理由取得//
    public String[] getCancelMessage() {
        return (String[])eve_cancel_message.toArray();
    }

    //キャンセル送信時間取得//
    public LocalDateTime[] getCancelTime() {
        return (LocalDateTime[])eve_cancel_time.toArray();
    }

    //いいね数取得//
    public int getGood() {
        return eve_good;
    }

    //いいね数増加//
    public int increaseGood() {
        eve_good++;
        return eve_good;
    }

    //いいね数現象//
    public int decreaseGood() {
        eve_good--;
        return eve_good;
    }
    //参加者数取得//
    public int getJoin() {
        return eve_join;
    }

    //参加者数取得//
    public int increaseJoin() {
        eve_join++;
        return eve_join;
    }

    //参加者数取得//
    public int decreaseJoin() {
        eve_join--;
        return eve_join;
    }
}
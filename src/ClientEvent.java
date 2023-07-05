import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ClientEvent implements Serializable{
        private static final long serialVersionUID = 1L;
        private String eve_id;
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
        private String eve_own_message = null;
        private Date eve_cancel_time = new Date();
        private String eve_cancel_message = null;
        
        public ClientEvent(String name, String start, String finish, String place, String owner, String outline, String detail, String com){
        eve_name = name;
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
    public void editEventName(String new_name) {
        eve_name = new_name;
    }
    
    //イベント名取得//
    public String getEventName() {
        return eve_name;
    }
    
    //開始時刻変更//
    public void editEventStart(String new_start) {
        eve_start = new_start;
    }
    
    //開始時刻取得//
    public String getEventStart() {
        return eve_start;
    }
    
    //終了時刻変更//
    public void editEventFinish(String new_finish) {
        eve_finish = new_finish;
    }
    
    //終了時刻取得//
    public String getEventFinish() {
        return eve_finish;
    }
    
    //開催場所変更//
    public void editEventPlace(String new_place) {
        eve_place = new_place;
    }
    
    //開催場所取得//
    public String getEventPlace() {
        return eve_place;
    }
    
    //主催者名変更//
    public void editEventOwner(String new_owner) {
        eve_owner = new_owner;
    }
    
    //主催者名取得//
    public String getEventOwner() {
        return eve_owner;
    }
    
    //イベント概要変更//
    public void editEventOutline(String new_outline) {
        eve_outline = new_outline;
    }
    
    //イベント概要取得//
    public String getEventOutline() {
        return eve_outline;
    }
    
    //イベント詳細変更//
    public void editEventDetail(String new_detail) {
        eve_detail = new_detail;
    }
    
    //イベント詳細取得//
    public String getEventDetail() {
        return eve_detail;
    }
    
    //所属コミュニティ名変更//
    public void editEventCommunity(Community new_com) {
        eve_com_name = new_com.getName();
    }
    
    //所属コミュニティ名取得//
    public String getEventCommunityName() {
        return eve_com_name;
    }
    
    //主催者メッセージ設定//
    public void sendOwnerMessage(String message) {
        eve_own_message = message;
    }
    
    //主催者メッセージ取得//
    public String getOwnerMessage() {
        return eve_own_message;
    }
    
    //キャンセル理由設定//
    public void sendCancel(String message) {
        Calendar calendar = Calendar.getInstance();
        eve_cancel_time = calendar.getTime(); //時間取得
        eve_cancel_message = message;
    }
    
    //キャンセル理由取得//
    public String getCancelMessage() {
        return eve_cancel_message;
    }
    
    //キャンセル送信時間取得//
    public Date getCancelTime() {
        return eve_cancel_time;
    }

    //いいね数取得//
    public int getGood() {
        return eve_good;
    }

    //参加者数取得//
    public int getJoin() {
        return eve_join;
    }
}
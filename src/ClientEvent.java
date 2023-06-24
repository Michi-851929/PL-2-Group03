
public class ClientEvent {
        String eve_id;
        String eve_name;
        String eve_start;
        String eve_finish;
        String eve_place;
        String eve_owner;
        String eve_outline;
        String eve_detail;
        int eve_good;
        int eve_join;
        String eve_com_name;
        String eve_own_message;
        String eve_cancel;
        
        ClientEvent(String name, String start, String finish, String place, String owner, String outline, String detail, String com_name){
         eve_name = name;
         eve_start = start;
         eve_finish = finish;
         eve_place = place;
         eve_owner = owner;
         eve_outline = outline;
         eve_detail = detail;
         eve_com_name = com_name;
    }

    //イベント削除//
    void deleteEvent() {
    }

    //イベント編集//
    void editEvent(String edit) {	
    }

    //主催者メッセージ送信//
    void sendMessage(String message) {	
    }
    
    //キャンセル理由送信//
    void sendCancel(String message) {
    }

    //いいね数取得//
    int getGood() {
        return eve_good;
    }

    //参加者数取得//
    int getJoin() {
        return eve_join;
    }

    @Override
    public String toString() {
        return "";
    }
}
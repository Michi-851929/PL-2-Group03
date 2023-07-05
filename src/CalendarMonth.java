import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalendarMonth implements Serializable{
    private static final long serialVersionUID = 1L;
    private int cm_year;
    private int cm_month;
    //private ArrayList<ClientEvent>[] cm_events = new ArrayList<ClientEvent>[31];
    private List<ArrayList<ClientEvent>> cm_events = new ArrayList<ArrayList<ClientEvent>>(31);
    
    
    CalendarMonth(int year, int month){
        cm_year = year;
        cm_month = month;
        for(int i =0;i<31;i++) {
            cm_events.add(i, new ArrayList<ClientEvent>());
        }
    }
    
    //年を返す
    public int getYear() {
        return cm_year;
    }
    
    //月を返す
    public int getMonth() {
        return cm_month;
    }
    
    //1か月分のイベントが日付ごとに格納された二次元ArrayListを返す
    public List<ArrayList<ClientEvent>> getEventList(){
        return cm_events;
    }
    
    //特定の日のイベント(ArrayList)を返す
    public ArrayList<ClientEvent> getDayEvent(int day){
        return cm_events.get(day-1);
    }
    
    //イベントを追加する 開始日:day_start 終了日:day_end
    public void addEvent(ClientEvent new_event,int day_start, int day_end) {
        for(int i = day_start; i <= day_end; i++) {
            ArrayList<ClientEvent> tmp_eventlist=  cm_events.get(i-1);
            tmp_eventlist.add(new_event);
            cm_events.set(i-1, tmp_eventlist);
        }
    }
    
    //イベントを削除する
    public void removeEvent(String rem_event_id) {
        for(int i = 0; i<31;i++) {
            ArrayList<ClientEvent> tmp_eventlist=  cm_events.get(1);
            for(int j = 0; j < tmp_eventlist.size(); j++) {
                if(tmp_eventlist.get(j).getEventId().equals(rem_event_id)){ //ClientEventクラスのeve_idの取得メソッドができたら書き換える
                    tmp_eventlist.remove(j);
                }
            }
            cm_events.set(i, tmp_eventlist);
        }
    }
    
    
}
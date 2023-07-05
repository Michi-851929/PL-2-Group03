import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable{
    private static final long serialVersionUID = 1L;
    public final static int TAG_MAXNUM = 5; //タグを何個まで設定できるか
    public final static int RETURN_OK = 0; //正常に処理できた時の返り値
    public final static int RETURN_ERROR = -1;//エラーがあった時の返り値
    public final static int RETURN_TAG_DUPULICATE = 1; //addTagメソッドで追加しようとしたタグが、既にタグリストに存在していたとき返り値
    public final static int RETURN_TAG_FULL = 2; //addTagメソッドでタグを追加しようとしたものの、既にタグが5個設定されていて追加できなかったときの返り値
    public final static int CALENDAR_YEARS = 10; //何年分のカレンダーを生成するか

    private String com_name;
    private String com_owner;
    private String com_info;
    private String[] com_tag = new String[TAG_MAXNUM];
    private ArrayList<String> com_member;
    private ArrayList<CalendarMonth> com_calendar;
    private int first_year = 2023; //何年のカレンダーから設定するか

    Community(String name, String owner, String info, String tag[]){ //引数は コミュニティ名. オーナー名, コミュニティ概要, タグ配列
        com_name = name;
        com_owner = owner;
        com_info = info;
        for(int i = 0; i<TAG_MAXNUM;i++) {
            if(tag[i].isEmpty()) {
                com_tag[i] = null;
            }
            else {
                com_tag[i] = tag[i];
            }
        }
        com_member = new ArrayList<>();
        com_calendar = new ArrayList<>();
        //CALENDAR_YEARS年分のCalendarMonthを生成
        for(int i = 0; i<12*CALENDAR_YEARS;i++) {
            com_calendar.add(new CalendarMonth(first_year+(i/12), i%12));
        }
    }


    //コミュニティ名設定
    public void setName(String new_name){
        com_name = new_name;
    }

    //コミュニティ名取得
    public String getName() {
        return com_name;
    }

    //コミュニティオーナー名設定
    public void setOwner(String new_owner){
        com_owner = new_owner;
    }

    //コミュニティオーナー名取得
    public String getOwner() {
        return com_owner;
    }

    public void setInfo(String new_info) {
        com_info = new_info;
    }

    public String getInfo() {
        return com_info;
    }

    public int addTag(String new_tag) {
        int retval=RETURN_ERROR;//返り値 return value

        //既に同じ名前のTagがないかを確認する
        for(int i = 0; i<TAG_MAXNUM;i++) {
            if(new_tag.equals(com_tag[i])) {
                //同じ名前のtagが既にあったときは
                retval = RETURN_TAG_DUPULICATE;
                break;
            }
        }

        //重複がないなら
        if(retval != RETURN_TAG_DUPULICATE) {
            retval = RETURN_TAG_FULL; //tagに空きがないときは2が返り値になる
            //タグを追加する枠を探す
            for(int i = 0; i<TAG_MAXNUM;i++) {
                if(com_tag[i].equals(null)) {
                    //タグに空きがあったら
                    com_tag[i] = new_tag;//タグを設定
                    retval = RETURN_OK; //正常に処理が出来たことを示す返り値0に設定
                    break; //ループを抜ける
                }
            }
        }

        return retval; //タグに空きがない:2 tag名に重複がある:1 正常にtagを追加できた:0
    }

    public int remTag(String rem_tag) {
        int retval = RETURN_ERROR; //返り値 return value

        //rem_tagと同じ名前のcom_tagを探す
        for(int i = 0; i<TAG_MAXNUM; i++) {
            if(com_tag[i].equals(rem_tag)) {
                //rem_tagと同じ名前のtagがあったら削除する
                com_tag[i] = null;
                retval = RETURN_OK;
                break;
            }
        }
        return retval; //-1:正常に削除できなかった(指定したタグが見つからなかった) 0:正常に削除できた
    }

    public String[] getTag() {
        return com_tag;
    }

    public void addMember(String new_member) {
        com_member.add(new_member);
    }

    public void remMember(String rem_member) {
        com_member.remove(rem_member);
    }

    //コミュニティ所属人数を出力
    public int getPopulation() {
        return com_member.size();
    }

    public CalendarMonth getCalendarMonth(int year, int month)
    {
        return com_calendar.get((year - first_year) * 12 + month - 1);
    }
}
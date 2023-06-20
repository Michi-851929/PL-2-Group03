import java.util.ArrayList;

public class Community{
    private String com_name;
    private String com_owner;
    private String com_info;
    private String[] com_tag = new String[5];
    //private String[] com_member = new;
    private ArrayList<String> com_member;
    private CalendarMonth com_calendars[];
    
    Community(String name, String owner, String info, String tag[]){
        com_name = name;
        com_owner = owner;
        com_info = info;
        for(int i = 0; i<5;i++) {
            if(tag[i].isEmpty()) {
                com_tag[i] = null;
            }
            else {
                com_tag[i] = tag[i];
            }
        }
        com_member = new ArrayList<>();
        //ここでcom_calendersのコンストラクタを呼び出す
    }
    
    public void setOwner(String new_owner){
        com_owner = new_owner;
    }
    
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
        int retval=-1;//返り値 return value
        
        //既に同じ名前のTagがないかを確認する
        for(int i = 0; i<5;i++) {
            if(new_tag.equals(com_tag[i])) {
                //同じ名前のtagが既にあったときは
                retval = 1;
                break;
            }
        }
        
        //重複がないなら
        if(retval != 1) {
            retval = 2; //tagに空きがないときは2が返り値になる
            //タグを追加する枠を探す
            for(int i = 0; i<5;i++) {
                if(com_tag[i].equals(null)) {
                    //タグに空きがあったら
                    com_tag[i] = new_tag;//タグを設定
                    retval = 0; //正常に処理が出来たことを示す返り値0に設定
                    break; //ループを抜ける
                }
            }
        }
        
        return retval; //タグに空きがない:2 tag名に重複がある:1 正常にtagを追加できた:0 
    }
    
    public int remTag(String rem_tag) {
        int retval = -1; //返り値 return value
        
        //rem_tagと同じ名前のcom_tagを探す
        for(int i = 0; i<5; i++) {
            if(com_tag[i].equals(rem_tag)) {
                //rem_tagと同じ名前のtagがあったら削除する
                com_tag[i] = null;
                retval = 0;
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
}
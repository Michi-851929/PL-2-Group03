import java.util.ArrayList;

public class Account {
	private String name;
	private String password;
	private ArrayList<Community> community_involved = new ArrayList<>();
	private ArrayList<ClientEvent> event_going = new ArrayList<>();
	private ArrayList<ClientEvent> event_preferred = new ArrayList<>();
	
	//パスワード認証の戻り値を表す定数
	public final static int PASS_CORRECT = 1;
	public final static int PASS_FALSE = 0;
	
	//コンストラクタ
	public Account(String name, String password)
	{
		this.name = name;
		this.password = password;
	}
	
	//ユーザ名取得
	public String getUserName()
	{
		return name;
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
	public void addCommunity(Community community)
	{
		community_involved.add(community);
	}
	
	//所属コミュニティを脱退
	public void removeCommunity(Community community)
	{
		community_involved.remove(community);
	}
	
	//所属コミュニティを取得
	public Community[] getCommunity()
	{
		return (Community[])community_involved.toArray();
	}
	
	//参加イベントを追加
	public void addEventGoing(ClientEvent event)
	{
		event_going.add(event);
	}
	
	//参加イベントを取り消し
	public void removeEventGoing(ClientEvent event)
	{
		event_going.remove(event);
	}
	
	//参加イベントを取得
	public ClientEvent[] getEventGoing()
	{
		return (ClientEvent[])event_going.toArray();
	}
	
	//いいねしたイベントを追加
	public void addEventPreferred(ClientEvent event)
	{
		event_preferred.add(event);
	}
	
	//いいねしたイベントを取り消し
	public void removeEventPreferred(ClientEvent event)
	{
		event_preferred.remove(event);
	}
	
	//いいねしたイベントを取得
	public ClientEvent[] getEventPreferred()
	{
		return (ClientEvent[])event_preferred.toArray();
	}
}

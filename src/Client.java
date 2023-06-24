

public class Client {
	
	//コンストラクタ
	public Client(){
		
	}
	
	//ログイン画面
	void loginScreen() {
		
	}
	
	//新規登録画面
	void registerScreen() {
		
	}
	
	//カレンダー画面
	void calenderScreen() {
		
	}
	
	//日付画面
	void dateScreen() {
		
	}
	
	//イベント画面
	void eventScreen() {
		
	}
	
	//ユーザ画面
	void userScreen() {
		
	}
	
	//パスワード変更画面
	void passwordScreen() {
		
	}
	
	//コミュニティ管理画面
	void communityScreen() {
		
	}
	
	
	//月データ取得
	int getMonthData(int yearmonth){
		
		return 0;
	}
	
	//月データ表示
	void displayMonthData(int yearmonth) {
		
	}

	//日付データ取得
	int getDateData(int date) {
		
		return 0;
	}
	
	//日付データ表示
	void displayDateData(int date) {
		
	}
	
	//イベントデータ取得
	int getEventData(int event_id) {
		
		return 0;
	}
	
	//イベントデータ表示
	void displayEventData(int event_id) {
		
	}
	
	//イベント検索
	String[] serchEvent(String serch_word) {
		
		String[] event_list;
		
		return event_list;
	}
	
	//参加
	int joinEvent(int event_id) {
		
		return 0;
	}
	
	//いいね
	int goodEvent(int event_id) {
		
		return 0;
	}
	
	//通報
	int reportEvent(int event_id) {
		
		return 0;
	}
	
	//メッセージ送信
	int sendMessage(int event_id,String message) {
		
		return 0;
	}
	
	//更新
	int update() {
		
		return 0;
	}
	
	//コミュニティ作成
	int createCommunity(String community_name,String community_overview,String[] community_tag) {
		
		return 0;
	}
	
	//コミュニティ検索
	int serchCommunity(String serch_word) {
		
		return 0;
	}
	
	//コミュニティ参加
	int joinCommunity(String community_name) {
		
		return 0;
	}
	
	//コミュニティ脱退
	int withdrawalCommunity(String community_name) {
		
		return 0;
	}
	
	//コミュニティ管理
	int managementCommunity(String community_name) {
		
		return 0;
	}
	
	//パスワード変更
	int changePassword(String prev_pass,String new_pass) {
		
		return 0;
	}
	
	//ログアウト
	int logout(String name) {
		
		return 0;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("Hello, world!");

	}

}

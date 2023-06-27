import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Client extends JFrame {
	private JTextField usernameField;
    private JPasswordField passwordField;
	
	//コンストラクタ(ログイン画面)
	public Client(){
		// ウィンドウの設定
        setTitle("ログイン");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);
        
     // コンテンツパネルの設定
        JPanel contentPane = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 画像を読み込む
                Image backgroundImage = new ImageIcon("login.png").getImage();
                // 画像を描画する
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);

        // タイトルラベル
        JLabel titleLabel = new JLabel("Communi+I", SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        titleLabel.setFont(titleFont);
        contentPane.add(titleLabel, gbc);

        // ユーザ名のラベルとフィールド
        JLabel usernameLabel = new JLabel("ユーザ名:");
        usernameField = new JTextField(20);
        contentPane.add(usernameLabel, gbc);
        contentPane.add(usernameField, gbc);

        // パスワードのラベルとフィールド
        JLabel passwordLabel = new JLabel("パスワード:");
        passwordField = new JPasswordField(20);
        contentPane.add(passwordLabel, gbc);
        contentPane.add(passwordField, gbc);

        // ログインボタン
        JButton loginButton = new JButton("ログイン");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        loginButton.setBackground(new Color(230, 255, 179));
        contentPane.add(loginButton, gbc);

        // アカウント登録ボタン
        JButton registerButton = new JButton("アカウント登録");
        registerButton.setBackground(new Color(230, 255, 179));
        contentPane.add(registerButton, gbc);

        // ボタンのアクションリスナー
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // ログイン処理を行う
                // ここではダミーの処理として、入力内容を表示するだけとします
                JOptionPane.showMessageDialog(Client.this, "ユーザ名: " + username + "\nパスワード: " + password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // アカウント登録画面に遷移する処理を実装する
                JOptionPane.showMessageDialog(Client.this, "アカウント登録画面に遷移します");
            }
        });

        setContentPane(contentPane);
		
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
		
		String[] event_list = null;
		
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
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Client loginUI = new Client();
                loginUI.setVisible(true);
            }
        });

	}

}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

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
    public JPanel contentPane = new JPanel();
    
    private JButton[] ui_jb_calendar = new JButton[7 * 5];
	
	//コンストラクタ(ログイン画面)
	public Client(){
		// ウィンドウの設定
        super("ログイン");
        contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
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
                calendarScreen();
                JOptionPane.showMessageDialog(Client.this, "ユーザ名: " + username + "\nパスワード: " + password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // アカウント登録画面に遷移する処理を実装する
                JOptionPane.showMessageDialog(Client.this, "アカウント登録画面に遷移します");
            }
        });

        this.contentPane = contentPane;
		add(this.contentPane);
		
	}
	
	//ログイン画面
	void loginScreen() {
		
	}
	
	//新規登録画面
	void registerScreen() {
		
	}
	
	//カレンダー画面
	void calendarScreen() {
		//玖津見が書いています
		contentPane.removeAll();
		contentPane.setLayout(new FlowLayout());
		//全体
		JPanel ui_panel_00 = new JPanel();
		ui_panel_00.setLayout(new BorderLayout());
		ui_panel_00.setSize(400, 500);
		
		//ヘッダ
		JPanel ui_panel_01 = new JPanel();
		ui_panel_01.setLayout(new FlowLayout());
		ui_panel_01.setBounds(0, 0, 400, 50);
		
		ui_panel_00.add(ui_panel_01, "North");
		
		//ボタン月ボタン
		JPanel ui_panel_02 = new JPanel();
		ui_panel_02.setLayout(new FlowLayout());
		ui_panel_02.setBounds(0, 0, 400, 50);
		JLabel ui_jl_month = new JLabel("2023/07");
		ui_jl_month.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
		ui_panel_02.add(ui_jl_month);
		
		ui_panel_01.add(ui_panel_02);
		
		//カレンダー
		JPanel ui_panel_03 = new JPanel();
		ui_panel_03.setLayout(new GridLayout(5, 7, 4, 4));
		ui_panel_03.setBounds(0, 50, 400, 450);
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 7; j++) {
				ui_jb_calendar[5 * i + j] = new JButton();
				ui_jb_calendar[5 * i + j].setText((5 * i + j + 1 >= 10 ? "" : "0") + Integer.toString(5 * i + j + 1));
				ui_jb_calendar[5 * i + j].setIcon(getDateIcon(true, 1 + 5 + i + j, j, 3, "成果報告会", true, "A会", false));
				ui_jb_calendar[5 * i + j].setMargin(new Insets(-3, -3, -3, -20));
				ui_jb_calendar[5 * i + j].setBorderPainted(false);
				ui_jb_calendar[5 * i + j].addActionListener(null);
				ui_panel_03.add(ui_jb_calendar[5 * i + j]);
			}
		}
		
		ui_panel_00.add(ui_panel_03,"Center");
		/*
		//カレンダー・ユーザ切り替え
		JPanel ui_panel_04 = new JPanel();
		ui_panel_04.setLayout(new GridLayout(1, 2));
		ui_panel_00.setBounds(0, 350, 350, 50);
		ui_panel_00.add(ui_panel_04);
		*/
		contentPane.add(ui_panel_00);
		setVisible(true);
		repaint();
	}
	
	private ImageIcon getDateIcon(boolean this_month, int date, int weekday, int event_number, String event1_name, boolean event1_preferred, String event2_name, boolean event2_preferred)
	{
		//玖津見が書いています
		int icon_width = 50;
		int icon_height = 60;
		int r = 8;
		Calendar calendar = Calendar.getInstance();
		Image img = createImage(icon_width, icon_height);
		System.out.println(img);
		Graphics g = img.getGraphics();
		
		if(Calendar.DATE == date) { //今月じゃない
			g.setColor(new Color(217, 217, 217));
		}
		else if(icon_width == 3) { //今日
			g.setColor(new Color(255, 192, 0));
		}
		else if(weekday == 0) { //日曜
			g.setColor(new Color(255, 179, 210));
		}
		else if(weekday == 6) { //土曜
			g.setColor(new Color(197, 226, 255));
		}
		else { //平日
			g.setColor(new Color(255, 255, 255));
		}
		g.fillOval(0, 0, r, r);
		g.fillOval(icon_width - r - 1, 0, r, r);
		g.fillOval(0, icon_height - r - 1, r, r);
		g.fillOval(icon_width - r - 1, icon_height - r - 1, r, r);
		g.fillRect(0, r / 2, icon_width, icon_height - r);
		g.fillRect(r / 2, 0, icon_width - r, icon_height);
		
		if(event1_preferred) { //いいね
			g.setColor(new Color(0, 176, 240));
		}
		else{ //非いいね
			g.setColor(new Color(242, 242, 242));
		}
		icon_width = icon_width - 6;
		icon_height = icon_height / 3 - 4;
		int offset = 22;
		r = 4;
		g.fillOval(3 + 0, offset + 0, r, r);
		g.fillOval(3 + icon_width - r - 1, offset + 0, r, r);
		g.fillOval(3 + 0, offset + icon_height - r - 1, r, r);
		g.fillOval(3 + icon_width - r - 1, offset + icon_height - r - 1, r, r);
		g.fillRect(3 + 0, offset + r / 2, icon_width, icon_height - r);
		g.fillRect(3 + r / 2, offset + 0, icon_width - r, icon_height);
		
		if(event2_preferred) { //いいね
			g.setColor(new Color(0, 176, 240));
		}
		else{ //非いいね
			g.setColor(new Color(242, 242, 242));
		}
		offset = 41;
		g.fillOval(3 + 0, offset + 0, r, r);
		g.fillOval(3 + icon_width - r - 1, offset + 0, r, r);
		g.fillOval(3 + 0, offset + icon_height - r - 1, r, r);
		g.fillOval(3 + icon_width - r - 1, offset + icon_height - r - 1, r, r);
		g.fillRect(3 + 0, offset + r / 2, icon_width, icon_height - r);
		g.fillRect(3 + r / 2, offset + 0, icon_width - r, icon_height);
		
		ImageIcon icon = new ImageIcon(img);
		return icon;
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

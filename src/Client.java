import java.awt.BorderLayout;
import java.awt.CardLayout;
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
    private JPanel contentPane0;
    private JPanel contentPane1;
    private JPanel contentPane2;
    private JButton ui_jb_calendarwindow;
    private JButton ui_jb_userwindow;
    private CardLayout ui_clayout;
    
    private JButton[] ui_jb_calendar = new JButton[7 * 5];
    
    public final static int WINDOW_WIDTH = 600;
    public final static int WINDOW_HEIGHT = 800;
    public final static Color THEME_COLOR = new Color(230, 255, 179);
    
    private String username;
    private String password;
	
	//コンストラクタ(ログイン画面)
	public Client(){
		// ウィンドウの設定
        super("ログイン");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    setLocationRelativeTo(null);
	    
        contentPane0 = new JPanel();
        ui_clayout = new CardLayout();
        contentPane0.setLayout(ui_clayout);
        contentPane1 = new JPanel();
        contentPane1.setLayout(null);
        contentPane2 = new JPanel();
        contentPane2.setLayout(null);
        contentPane0.add(contentPane1, "カレンダー画面");
        contentPane0.add(contentPane2, "ユーザ画面");
        ui_jb_calendarwindow = new JButton("カレンダー");
        ui_jb_calendarwindow.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae)
        	{
        		calendarScreen();
        	}
        });
		ui_jb_userwindow = new JButton("ユーザ");
		ui_jb_userwindow.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae)
        	{
        		userScreen();
        	}
        });
		
        add(contentPane0);
        
        loginScreen();
		
	}
	
	//ログイン画面
	void loginScreen() {
	    contentPane1.removeAll();
	    // ウィンドウの設定
	    setTitle("ログイン");

	 // コンテンツパネルの設定
        JPanel ui_panel_00 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 画像を読み込む
                Image backgroundImage = new ImageIcon("login.png").getImage();
                // 画像を描画する
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        ui_panel_00.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);

        // タイトルラベル
        JLabel titleLabel = new JLabel("Communi+I", SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        titleLabel.setFont(titleFont);
        ui_panel_00.add(titleLabel, gbc);

        // ユーザ名のラベルとフィールド
        JLabel usernameLabel = new JLabel("ユーザ名:");
        usernameField = new JTextField(20);
        ui_panel_00.add(usernameLabel, gbc);
        ui_panel_00.add(usernameField, gbc);

        // パスワードのラベルとフィールド
        JLabel passwordLabel = new JLabel("パスワード:");
        passwordField = new JPasswordField(20);
        ui_panel_00.add(passwordLabel, gbc);
        ui_panel_00.add(passwordField, gbc);

        // ログインボタン
        JButton loginButton = new JButton("ログイン");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        loginButton.setBackground(new Color(230, 255, 179));
        ui_panel_00.add(loginButton, gbc);

        // アカウント登録ボタン
        JButton registerButton = new JButton("アカウント登録");
        registerButton.setBackground(new Color(230, 255, 179));
        ui_panel_00.add(registerButton, gbc);

        // ボタンのアクションリスナー
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // ログイン処理を行う
                // ここではダミーの処理として、入力内容を表示するだけとします
                //calendarScreen();
                userScreen();
                JOptionPane.showMessageDialog(Client.this, "ユーザ名: " + username + "\nパスワード: " + password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // アカウント登録画面に遷移する処理を実装する
                registerScreen();
            }
        });

	    contentPane1.add(ui_panel_00);
	    setVisible(true);
	    repaint();
	}

	
	//新規登録画面
	void registerScreen() {
		contentPane1.removeAll();
		// ウィンドウの設定
        setTitle("新規登録");
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

        // パスワード確認のラベルとフィールド
        JLabel confirmPasswordLabel = new JLabel("パスワード確認:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        contentPane.add(confirmPasswordLabel, gbc);
        contentPane.add(confirmPasswordField, gbc);

        // 登録ボタン
        JButton registerButton = new JButton("登録");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        registerButton.setBackground(new Color(230, 255, 179));
        contentPane.add(registerButton, gbc);
        
        // 戻るボタン
        JButton backButton = new JButton("戻る");
        gbc.insets = new Insets(10, 0, 0, 0); 
        backButton.setBackground(new Color(230, 255, 179));
        contentPane.add(backButton, gbc);

        

        // ボタンのアクションリスナー
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginScreen();
                
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(Client.this, "パスワードが一致しません。もう一度やり直してください。");
                } else {
                    JOptionPane.showMessageDialog(Client.this, "ユーザ登録が完了しました。");
                    dispose();
                }
            }
        });

        setContentPane(contentPane);
        setVisible(true);
        repaint();
	}
	
	//カレンダー画面
	void calendarScreen() {
		//玖津見が書いています
		contentPane1.removeAll();
		contentPane1.setLayout(null);
		//全体
		JPanel ui_panel_00 = new JPanel();
		ui_panel_00.setLayout(null);
		ui_panel_00.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		ui_panel_00.setBackground(THEME_COLOR);
		
		//ヘッダ
		JPanel ui_panel_01 = new JPanel();
		ui_panel_01.setLayout(new FlowLayout());
		ui_panel_01.setBounds(0, 0, WINDOW_WIDTH, 75);
		ui_panel_01.setBackground(THEME_COLOR);
		
		ui_panel_00.add(ui_panel_01);
		
		//ボタン月ボタン
		JPanel ui_panel_02 = new JPanel();
		ui_panel_02.setLayout(new FlowLayout());
		ui_panel_02.setBounds(0, 0, WINDOW_WIDTH, 75);
		ui_panel_02.setBackground(THEME_COLOR);
		JLabel ui_jl_month = new JLabel("2023/07");
		ui_jl_month.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
		ui_panel_02.add(ui_jl_month);
		
		ui_panel_01.add(ui_panel_02);
		
		//カレンダー
		JPanel ui_panel_06 = new JPanel();
		ui_panel_06.setLayout(new FlowLayout());
		ui_panel_06.setBounds(-5, 75, WINDOW_WIDTH, 600);
		ui_panel_06.setBackground(THEME_COLOR);
		JPanel ui_panel_05 = new JPanel();
		ui_panel_05.setLayout(new BorderLayout());
		ui_panel_05.setSize(WINDOW_WIDTH - 16, 600 - 12);
		ui_panel_05.setBackground(THEME_COLOR);
		JPanel ui_panel_03 = new JPanel();
		ui_panel_03.setLayout(new GridLayout(5, 7, 5, 5));
		ui_panel_03.setBackground(THEME_COLOR);
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 7; j++) {
				ui_jb_calendar[7 * i + j] = new JButton();
				ui_jb_calendar[7 * i + j].setText((7 * i + j + 1 >= 10 ? "" : "0") + Integer.toString(7 * i + j + 1));
				ui_jb_calendar[7 * i + j].setIcon(getDateIcon(true, 1 + 7 * i + j, j, 3, "成果報告会があります", true, "A会", false));
				ui_jb_calendar[7 * i + j].setMargin(new Insets(0, 0, 0, -17));
				ui_jb_calendar[7 * i + j].setBorderPainted(false);
				ui_jb_calendar[7 * i + j].setBackground(THEME_COLOR);
				ui_jb_calendar[7 * i + j].setOpaque(true);
				ui_jb_calendar[7 * i + j].addActionListener(null);
				ui_panel_03.add(ui_jb_calendar[7 * i + j]);
			}
		}
		ui_panel_05.add(ui_panel_03, "Center");
		ui_panel_06.add(ui_panel_05);
		ui_panel_00.add(ui_panel_06);

		//カレンダー・ユーザ切り替え
		setFooter(ui_panel_00);
		
		contentPane1.add(ui_panel_00);
		ui_clayout.show(contentPane0, "カレンダー画面");
		setVisible(true);
		repaint();
	}
	
	public void setFooter(JPanel panel)
	{
		//カレンダー・ユーザ切り替え
		JPanel ui_panel_04 = new JPanel();
		ui_panel_04.setLayout(new GridLayout(1, 2));
		ui_panel_04.setBounds(0, 675, WINDOW_WIDTH, 125);
		ui_panel_04.add(ui_jb_calendarwindow);
		ui_panel_04.add(ui_jb_userwindow);
		panel.add(ui_panel_04);

	}
	
	private ImageIcon getDateIcon(boolean this_month, int date, int weekday, int event_number, String event1_name, boolean event1_preferred, String event2_name, boolean event2_preferred)
	{
		//玖津見が書いています
		int icon_width = 70;
		int icon_height = 100;
		int r = 12;
		Calendar calendar = Calendar.getInstance();
		Image img = createImage(icon_width, icon_height);
		System.out.println(img);
		Graphics g = img.getGraphics();
		Color c;
		
		if(Calendar.DATE == date) { //今月じゃない
			c = new Color(217, 217, 217);
		}
		else if(icon_width == 3) { //今日
			c = new Color(255, 192, 0);
		}
		else if(weekday == 0) { //日曜
			c = new Color(255, 179, 210);
		}
		else if(weekday == 6) { //土曜
			c = new Color(197, 226, 255);
		}
		else { //平日
			c = new Color(255, 255, 255);
		}
		Client.kadomaruRect(g, 0, 0, icon_width, icon_height, r, c, THEME_COLOR);
		
		if(event1_preferred) { //いいね
			g.setColor(new Color(0, 176, 240));
		}
		else{ //非いいね
			g.setColor(new Color(242, 242, 242));
		}
		icon_width = icon_width - 6;
		icon_height = icon_height / 3 - 4;
		int offset = 36;
		r = 10;
		Client.kadomaruRect(g, 3, offset, icon_width, icon_height, r);
		if(event1_preferred) { //いいね
			g.setColor(Color.WHITE);
		}
		else{ //非いいね
			g.setColor(Color.BLACK);
		}
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		g.drawString(event1_name.substring(0, event1_name.length() >= 5 ? 5 : event1_name.length()), 10, 48);
		if(event1_name.length() >= 5) {
			g.drawString(event1_name.substring(5, (event1_name.length() >= 9 ? 9 : event1_name.length())), 10, 60);
		}
		if(event1_name.length() >= 10) {
			g.drawString("　　　　…", 10, 60);
		}
		g.setColor(new Color(0, 176, 240));
		
		if(event2_preferred) { //いいね
			g.setColor(new Color(0, 176, 240));
		}
		else{ //非いいね
			g.setColor(new Color(242, 242, 242));
		}
		offset = 68;
		Client.kadomaruRect(g, 3, offset, icon_width, icon_height, r);
		if(event2_preferred) { //いいね
			g.setColor(Color.WHITE);
		}
		else{ //非いいね
			g.setColor(Color.BLACK);
		}
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		g.drawString(event2_name.substring(0, event2_name.length() >= 5 ? 5 : event2_name.length()), 10, 80);
		if(event2_name.length() >= 5) {
			g.drawString(event2_name.substring(5, (event2_name.length() >= 9 ? 9 : event2_name.length())), 10, 92);
		}
		if(event2_name.length() >= 10) {
			g.drawString("　　　　…", 10, 92);
		}
		g.setColor(new Color(0, 176, 240));
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		g.drawString(Integer.toString(date), 8, 24);
		
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
		g.drawString(Integer.toString(event_number % 10), 47, 24);
		if(event_number >= 10) {
			g.drawString(Integer.toString(event_number / 10), 39, 24);
		}
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		g.drawString("件", 53, 23);
		
		
		
		ImageIcon icon = new ImageIcon(img);
		return icon;
	}
	
	public static void kadomaruRect(Graphics g, int x, int y, int width, int height, int r)
	{
		g.fillOval(x, y, r, r);
		g.fillOval(x + width - r - 1, y, r, r);
		g.fillOval(x, y + height - r - 1, r, r);
		g.fillOval(x + width - r - 1, y + height - r - 1, r, r);
		g.fillRect(x, y + r / 2, width, height - r);
		g.fillRect(x + r / 2, y, width - r, height);
	}
	
	public static void kadomaruRect(Graphics g, int x, int y, int width, int height, int r, Color maincolor, Color background)
	{
		g.setColor(background);
		g.fillRect(0, 0, width, height);
		g.setColor(maincolor);
		g.fillOval(x, y, r, r);
		g.fillOval(x + width - r - 1, y, r, r);
		g.fillOval(x, y + height - r - 1, r, r);
		g.fillOval(x + width - r - 1, y + height - r - 1, r, r);
		g.fillRect(x, y + r / 2, width, height - r);
		g.fillRect(x + r / 2, y, width - r, height);
	}
	
	//日付画面
	void dateScreen() {
		
	}
	
	//イベント画面
	void eventScreen() {
		
	}
	
    //ユーザ画面
    void userScreen() {
        int button_width = 250;
        int button_height = 40;
        int r = 8;
        
        Image img = createImage(button_width, button_height);
        Graphics g = img.getGraphics();
        Client.kadomaruRect(g, 0, 0, button_width, button_height, r);

        contentPane2.removeAll();
        JPanel userScreen = new JPanel(new GridBagLayout()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 画像を読み込む
                Image backgroundImage = new ImageIcon("login.png").getImage();
                // 画像を描画する
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        userScreen.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);

        // ユーザ名のラベルとフィールド
        JLabel usernameLabel = new JLabel("ユーザ名: "+username);
        userScreen.add(usernameLabel, gbc);

        // パスワード変更ボタン
        JButton changePasswordButton = new JButton("パスワード変更");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        
        changePasswordButton.setBackground(new Color(255, 255, 255));
        changePasswordButton.setIcon(new ImageIcon(img));
        userScreen.add(changePasswordButton, gbc);

        // コミュニティ管理ボタン
        JButton communityManageButton = new JButton("コミュニティ管理");
        communityManageButton.setBackground(new Color(255, 255, 255));
        userScreen.add(communityManageButton, gbc);
        
        communityManageButton.setIcon(new ImageIcon(img));

        // ログアウトボタン
        JButton logOutButton = new JButton("ログアウト");
        logOutButton.setBackground(new Color(255, 255, 255));
        userScreen.add(logOutButton, gbc);
        
        logOutButton.setIcon(new ImageIcon(img));

        contentPane2.add(userScreen);
        ui_clayout.show(contentPane0, "ユーザ画面");
        setVisible(true);
        repaint();
    }
	
	//パスワード変更画面
	void passwordScreen() {
		setTitle("パスワード変更");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        
        setLayout(new BorderLayout());

        // 背景画像を表示するためのパネルを作成
        ImagePanel backgroundPanel = new ImagePanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);

        // 戻るボタン
        JButton backButton = new JButton("戻る");
        backButton.setBounds(10, 10, 60, 30);
        ImageIcon icon = new ImageIcon("back.png");
        backButton.setIcon(icon);
        
        // ボタンの余白を調整
        backButton.setMargin(new Insets(0, 0, 0, 0));
        
        // ボタンの枠線を非表示にする
        backButton.setBorderPainted(false);
        
        // ボタンの背景を透明にする
        backButton.setContentAreaFilled(false);
        backgroundPanel.add(backButton);

        // タイトル
        JLabel titleLabel = new JLabel("ユーザ名");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(150, 50, 200, 30);
        backgroundPanel.add(titleLabel);

        // 旧パスワード入力欄
        JLabel oldPasswordLabel = new JLabel("旧パスワード:");
        oldPasswordLabel.setBounds(150, 110, 100, 30);
        backgroundPanel.add(oldPasswordLabel);
        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(100, 140, 200, 30);
        backgroundPanel.add(oldPasswordField);

        // 新パスワード入力欄
        JLabel newPasswordLabel = new JLabel("新パスワード:");
        newPasswordLabel.setBounds(150, 180, 100, 30);
        backgroundPanel.add(newPasswordLabel);
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(100, 210, 200, 30);
        backgroundPanel.add(newPasswordField);

        // パスワード確認入力欄
        JLabel confirmPasswordLabel = new JLabel("パスワード確認:");
        confirmPasswordLabel.setBounds(150, 250, 100, 30);
        backgroundPanel.add(confirmPasswordLabel);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(100, 290, 200, 30);
        backgroundPanel.add(confirmPasswordField);

        // 変更ボタン
        JButton changeButton = new JButton("変更");
        changeButton.setBounds(150, 350, 100, 30);
        changeButton.setBackground(new Color(230, 255, 179));
        backgroundPanel.add(changeButton);

        setSize(400, 500);
        setVisible(true);
    }

    // 背景画像を描画するカスタムパネルクラス
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel() {
            // 画像ファイルのパスを指定して背景画像を読み込む
            String imagePath = "login.png";
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // 背景画像を描画
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
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

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
    public JPanel contentPane;
    
    private JButton[] ui_jb_calendar = new JButton[7 * 5];
    
    public final static int WINDOW_WIDTH = 600;
    public final static int WINDOW_HEIGHT = 800;
    
    private String username;
    private String password;
	
	//コンストラクタ(ログイン画面)
	public Client(){
		// ウィンドウの設定
        super("ログイン");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    setLocationRelativeTo(null);
	    
        contentPane = new JPanel();
        contentPane.setLayout(null);
        add(contentPane);
        
        loginScreen();
		
	}
	
	//ログイン画面
	void loginScreen() {
	    contentPane.removeAll();
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
                calendarScreen();
                //userScreen();
                JOptionPane.showMessageDialog(Client.this, "ユーザ名: " + username + "\nパスワード: " + password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // アカウント登録画面に遷移する処理を実装する
                registerScreen();
            }
        });

	    contentPane.add(ui_panel_00);
	    setVisible(true);
	    repaint();
	}

	
	//新規登録画面
	void registerScreen() {
		contentPane.removeAll();
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
				ui_jb_calendar[7 * i + j] = new JButton();
				ui_jb_calendar[7 * i + j].setText((7 * i + j + 1 >= 10 ? "" : "0") + Integer.toString(7 * i + j + 1));
				ui_jb_calendar[7 * i + j].setIcon(getDateIcon(true, 1 + 7 * i + j, j, 43, "成果報告会があります", true, "A会", false));
				ui_jb_calendar[7 * i + j].setMargin(new Insets(-3, -3, -3, -20));
				ui_jb_calendar[7 * i + j].setBorderPainted(false);
				ui_jb_calendar[7 * i + j].addActionListener(null);
				ui_panel_03.add(ui_jb_calendar[7 * i + j]);
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
		int icon_width = 70;
		int icon_height = 100;
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
		Client.kadomaruRect(g, 0, 0, icon_width, icon_height, r);
		
		if(event1_preferred) { //いいね
			g.setColor(new Color(0, 176, 240));
		}
		else{ //非いいね
			g.setColor(new Color(242, 242, 242));
		}
		icon_width = icon_width - 6;
		icon_height = icon_height / 3 - 4;
		int offset = 36;
		r = 4;
		Client.kadomaruRect(g, 3, offset, icon_width, icon_height, r);
		if(event1_preferred) { //いいね
			g.setColor(Color.WHITE);
		}
		else{ //非いいね
			g.setColor(Color.BLACK);
		}
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		g.drawString(event1_name.substring(0, 5), 10, 48);
		g.drawString(event1_name.substring(5, 9), 10, 60);
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
		g.drawString(event2_name, 10, 80);
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
	
	//日付画面
	void dateScreen() {
		
	}
	
	//イベント画面
	void eventScreen() {
		
	}
	
    //ユーザ画面
    void userScreen() {
        contentPane.removeAll();
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
        userScreen.add(changePasswordButton, gbc);

        // コミュニティ管理ボタン
        JButton communityManageButton = new JButton("コミュニティ管理");
        communityManageButton.setBackground(new Color(255, 255, 255));
        userScreen.add(communityManageButton, gbc);

        // ログアウトボタン
        JButton logOutButton = new JButton("ログアウト");
        logOutButton.setBackground(new Color(255, 255, 255));
        userScreen.add(logOutButton, gbc);

        contentPane.add(userScreen);
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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class Client extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel contentPane0;
    private JPanel contentPane1;
    private JPanel contentPane2;
    private JPanel footer;
    private JButton ui_jb_calendarwindow;
    private JButton ui_jb_userwindow;
    private CardLayout ui_clayout;
    private LocalDate ui_ld_firstofmonth;
    private LocalDate ui_ld_looking;
    private TrayIcon ui_ticon;
    private WindowListener ui_wlistener;
    private JFrame frame;

    private JButton[] ui_jb_calendar = new JButton[7 * 6];

    public final static int WINDOW_WIDTH = 600;
    public final static int WINDOW_HEIGHT = 800;
    public final static Color THEME_COLOR = new Color(230, 255, 179);
    public final static Color GOOD_COLOR = new Color(0, 176, 240);
    public final static Color JOIN_COLOR = new Color(255, 192, 0);
    public final static int MONDAY = 1;
    public final static int TUESDAY = 2;
    public final static int WEDNESDAY = 3;
    public final static int THURSDAY = 4;
    public final static int FRIDAY = 5;
    public final static int SATURDAY = 6;
    public final static int SUNDAY = 7;

    private String username;
    private String password;
    private String macaddress;
    private ClientConnect cc;
    private Account account;
    private ArrayList<Community> community_list = new ArrayList<>();
    private ArrayList<ClientEvent> event_list = new ArrayList<>();

    private  int login_flag;
    private int register_flag;
    private String eve_id;
    private Timer timer = new Timer(false);;
    
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<Integer> yearComboBox2;
    private JComboBox<String> monthComboBox2;
    private JComboBox<String> dayComboBox2;
    
    //コンストラクタ(ログイン画面)
    public Client(){
        // ウィンドウの設定
        super("ログイン");
        try {
            cc = new ClientConnect();
        } catch (Exception e) {
            cc = null;
            e.printStackTrace();
        }
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        ui_wlistener = new WindowListener() {
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                PopupMenu popup = new PopupMenu();
                Image img = createImage(20, 20);
                Graphics g = img.getGraphics();
                g.setColor(THEME_COLOR);
                g.fillRect(0, 0, 20, 20);
                g.setColor(Color.WHITE);
                g.fillRect(0, 10, 20, 10);
                ui_ticon = new TrayIcon(img, "Communi+I", popup);
                MenuItem ui_mi_open = new MenuItem("開く");
                ui_mi_open.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae)
                    {
                        calendarScreen();
                        SystemTray.getSystemTray().remove(ui_ticon);
                    }
                });
                popup.add(ui_mi_open);

                try {
                    SystemTray.getSystemTray().add(ui_ticon);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void windowClosed(WindowEvent e) {

            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {

            }

            public void windowDeactivated(WindowEvent e) {

            }

        };

        contentPane0 = new JPanel();
        ui_clayout = new CardLayout();
        contentPane0.setLayout(ui_clayout);

        contentPane1 = new JPanel();
        contentPane1.setLayout(null);

        contentPane2 = new JPanel();
        contentPane2.setLayout(null);

        contentPane0.add(contentPane1, "カレンダー画面");
        contentPane0.add(contentPane2, "ユーザ画面");

        ui_ld_firstofmonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);

        add(contentPane0);

        loginScreen();

    }

    //ログイン画面
    void loginScreen() {
        //int WINDOW_HEIGHT1 = 700; //画面からはみ出たのでログイン画面の大きさを調整しました。
        int button_width = 200;
        int button_height = 30;
        int r = 8;
        setTitle("ログイン画面");
        contentPane1.removeAll();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //setLayout(new BorderLayout());

        // 背景画像を表示するためのパネルを作成
        BufferedImage img0 = createBackgroundImage(WINDOW_WIDTH, WINDOW_HEIGHT);

        Graphics g0 = img0.getGraphics();
        g0.setColor(THEME_COLOR);
        g0.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g0.setColor(Color.WHITE);
        kadomaruRect(g0, 50, 50, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 150, 75);
        JPanel backgroundPanel = new JPanel();
        //setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        backgroundPanel.setBackground(THEME_COLOR);

        // タイトルラベル
        JLabel titleLabel = new JLabel("Communi+I", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(100, 100, 400, 50);
        backgroundPanel.add(titleLabel);

        // ユーザ名のラベルとフィールド
        JLabel usernameLabel = new JLabel("ユーザ名");
        usernameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        usernameField = new JTextField(20);
        usernameLabel.setBounds(100, 200, 400, 30);
        usernameField.setBounds(100, 250, 400, 30);
        backgroundPanel.add(usernameLabel);
        backgroundPanel.add(usernameField);

        // パスワードのラベルとフィールド
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        passwordField = new JPasswordField(20);
        passwordLabel.setBounds(100, 300, 400, 30);
        passwordField.setBounds(100, 350, 400, 30);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);

        // ログインボタン
        BufferedImage img1 = createBackgroundImage(button_width, button_height);
        Graphics g1 = img1.getGraphics();
        Color c1 = new Color(230, 255, 179);
        g1.setColor(c1);
        Client.kadomaruRect(g1, 0, 0, button_width, button_height, r, c1, Color.white);
        g1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        g1.setColor(Color.BLACK);
        g1.drawString("ログイン", 55, 22);
        JButton loginButton = new JButton("");
        loginButton.setBackground(Color.white);
        loginButton.setForeground(Color.black);
        loginButton.setOpaque(true);
        //loginButton.setMargin(new Insets(-3, -3, -3, -13));
        loginButton.setBorderPainted(false);
        loginButton.setBounds(200, 520, 200, 30);
        loginButton.setIcon(new ImageIcon(img1));
        backgroundPanel.add(loginButton);

        // ログインボタンのアクションリスナー
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                char[] passwordchars = passwordField.getPassword();
                password = new String(passwordchars);
                System.out.println("username:" + username);
                System.out.println("password:" + password);

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Client.this, "名前とパスワードを入力してください");
                } else {
                    try {
                        cc.login(username, password);
                        login_flag = 1;
                    } catch (Exception e1) {
                        String error = e1.getMessage();
                        if(error.equals(ClientConnect.NOT_FOUND) ) {
                            JOptionPane.showMessageDialog(Client.this, "該当ユーザーが見つかりません。");
                        }else if(error.equals(ClientConnect.BANNED) ) {
                            JOptionPane.showMessageDialog(Client.this, "該当ユーザーは無効化されています。");
                        }else if(error.equals(ClientConnect.AUTH) ) {
                            JOptionPane.showMessageDialog(Client.this, "パスワードをお確かめの上,もう一度入力してください。");
                        }else {
                            JOptionPane.showMessageDialog(Client.this, "エラーが発生しました。もう一度お試しください");
                        }
                    }

                    if (login_flag == 1) {
                        JOptionPane.showMessageDialog(Client.this, "ログイン成功");
                        login();
                        notificationScreen();
                    }
                }
            }
        });


        // アカウント登録ボタン
        BufferedImage img2 = createBackgroundImage(button_width, button_height);
        Graphics g2 = img2.getGraphics();
        Color c2 = new Color(230, 255, 179);
        g2.setColor(c2);
        Client.kadomaruRect(g2, 0 ,0 , button_width, button_height, r, c2, Color.white);
        g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("アカウント登録", 25, 22);
        JButton registerButton = new JButton("");
        registerButton.setBackground(Color.white);
        registerButton.setForeground(Color.black);
        registerButton.setOpaque(true);
        //registerButton.setMargin(new Insets(-3, -3, -3, -13));
        registerButton.setBorderPainted(false);
        registerButton.setBounds(200, 600, 200, 30);
        registerButton.setIcon(new ImageIcon(img2));
        backgroundPanel.add(registerButton);

        // アカウント登録ボタンのアクションリスナー
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerScreen();
            }
        });

        JLabel ui_jl_back = new JLabel("");
        ui_jl_back.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_jl_back.setIcon(new ImageIcon(img0));
        backgroundPanel.add(ui_jl_back);

        contentPane1.add(backgroundPanel);
        ui_clayout.show(contentPane0, "カレンダー画面");
        setVisible(true);
        repaint();
    }

    // 背景画像を作成するメソッド
    private BufferedImage createBackgroundImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Graphicsオブジェクトを取得して描画するための準備をする
        Graphics graphics = image.getGraphics();

        // 背景を描画する
        graphics.setColor(THEME_COLOR);
        graphics.fillRect(0, 0, width, height);

        // Graphicsオブジェクトを解放する
        graphics.dispose();

        return image;
    }



    //新規登録画面
    void registerScreen() {
        //int WINDOW_HEIGHT1 = 700;
        int button_width = 200;
        int button_height = 30;
        int r = 8;
        setTitle("アカウント登録");
        contentPane1.removeAll();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //setLayout(new BorderLayout());

        // 背景画像を表示するためのパネルを作成
        BufferedImage img0 = createBackgroundImage(WINDOW_WIDTH, WINDOW_HEIGHT);

        Graphics g0 = img0.getGraphics();
        g0.setColor(THEME_COLOR);
        g0.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g0.setColor(Color.WHITE);
        kadomaruRect(g0, 50, 50, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 150, 75);
        JPanel backgroundPanel = new JPanel();
        //setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // タイトルラベル
        JLabel titleLabel = new JLabel("Communi+I", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(100, 100, 400, 50);
        backgroundPanel.add(titleLabel);

        // ユーザ名のラベルとフィールド
        JLabel usernameLabel = new JLabel("ユーザ名");
        usernameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        usernameField = new JTextField(20);
        usernameLabel.setBounds(100, 180, 400, 30);
        usernameField.setBounds(100, 215, 400, 30);
        backgroundPanel.add(usernameLabel);
        backgroundPanel.add(usernameField);

        // パスワードのラベルとフィールド
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        passwordField = new JPasswordField(20);
        passwordLabel.setBounds(100, 270, 400, 30);
        passwordField.setBounds(100, 305, 400, 30);
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);

        // パスワード確認のラベルとフィールド
        JLabel confirmPasswordLabel = new JLabel("パスワード確認");
        confirmPasswordLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordLabel.setBounds(100, 370, 400, 30);
        confirmPasswordField.setBounds(100, 405, 400, 30);
        backgroundPanel.add(confirmPasswordLabel);
        backgroundPanel.add(confirmPasswordField);

        // アカウント登録ボタン
        BufferedImage img1 = createBackgroundImage(button_width, button_height);
        Graphics g1 = img1.getGraphics();
        Color c1 = new Color(230, 255, 179);
        g1.setColor(c1);
        Client.kadomaruRect(g1, 0, 0, button_width, button_height, r, c1, Color.white);
        g1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        g1.setColor(Color.BLACK);
        g1.drawString("アカウント登録", 25, 22);
        JButton registerButton = new JButton("");
        registerButton.setBackground(Color.white);
        registerButton.setForeground(Color.black);
        registerButton.setOpaque(true);
        //registerButton.setMargin(new Insets(-3, -3, -3, -13));
        registerButton.setBorderPainted(false);
        registerButton.setBounds(200, 520, 200, 30);
        registerButton.setIcon(new ImageIcon(img1));
        backgroundPanel.add(registerButton);

        //アカウント登録ボタンのアクションリスナー
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // アカウント登録画面に遷移する処理を実装する
                username = usernameField.getText();
                char[] passwordchars = passwordField.getPassword();
                password = new String(passwordchars);
                char[] confirmPasswordChars = confirmPasswordField.getPassword();
                String confirmPassword = new String(confirmPasswordChars);

                try {
                    macaddress = getMacAddress();
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (SocketException e1) {
                    e1.printStackTrace();
                }
                System.out.println("username:"+username);
                System.out.println("password:"+password);
                System.out.println("macaddress:"+macaddress);

                try {
                    cc.createAccount(username, password, macaddress);
                    register_flag = 0;
                }
                catch(Exception ex) {
                    register_flag = 1;
                }

                if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(Client.this, "全ての項目を入力してください");
                }else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(Client.this, "パスワードとパスワード確認が一致しません");
                } else {
                    try {
                        cc.createAccount(username, password, macaddress);
                    } catch (Exception e1) {
                        if(e1.getMessage().equals(ClientConnect.USER)) {
                            JOptionPane.showMessageDialog(Client.this, "既存のユーザーと名前が重複しています。別の名前をお試しください。");
                            register_flag = 2;
                        }else if(e1.getMessage().equals(ClientConnect.MAC)) {
                             JOptionPane.showMessageDialog(Client.this, "登録に失敗しました。サーバー管理者にお問い合わせください" );
                             register_flag = 2;
                        }else {
                            register_flag = 1;
                        }
                    }
                    if(register_flag==0) {
                        JOptionPane.showMessageDialog(Client.this, "アカウント登録成功");
                        login_flag = 1;
                        login();
                    }else if(register_flag==1){
                        JOptionPane.showMessageDialog(Client.this, "登録に失敗しました。もう一度お試しください。" );
                    }
                }

            }

        });

        // 戻るボタン
        BufferedImage img2 = createBackgroundImage(button_width, button_height);
        Graphics g2 = img2.getGraphics();
        Color c2 = new Color(230, 255, 179);
        g2.setColor(c2);
        Client.kadomaruRect(g2, 0, 0, button_width, button_height, r, c2, Color.white);
        g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("戻る", 77, 22);
        JButton backButton = new JButton("");
        backButton.setBackground(Color.white);
        backButton.setForeground(Color.black);
        backButton.setOpaque(true);
        //backButton.setMargin(new Insets(-3, -3, -3, -13));
        backButton.setBorderPainted(false);
        backButton.setBounds(200, 600, 200, 30);
        backButton.setIcon(new ImageIcon(img2));
        backgroundPanel.add(backButton);



        // 戻るボタンのアクションリスナー
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                loginScreen();

            }
        });



        JLabel ui_jl_back = new JLabel("");
        ui_jl_back.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_jl_back.setIcon(new ImageIcon(img0));
        backgroundPanel.add(ui_jl_back);

        contentPane1.add(backgroundPanel);
        setVisible(true);
        repaint();
    }

    //MACアドレス取得メソッド
    public static String getMacAddress() throws UnknownHostException, SocketException {

        InetAddress localhost = InetAddress.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localhost);

        byte[] macAddressBytes = networkInterface.getHardwareAddress();
        StringBuilder macAddressBuilder = new StringBuilder();

        for (int i=0;i<macAddressBytes.length;i++) {
            macAddressBuilder.append(String.format("%02X%s",macAddressBytes[i],(i<macAddressBytes.length-1) ? "-" : ""));
        }

        return macAddressBuilder.toString();
    }

    //カレンダー画面
    void calendarScreen() {
        //玖津見が書いています
        contentPane1.removeAll();
        contentPane1.setLayout(null);
        setTitle("Communi+I");
        setFooter(contentPane1);
        //全体
        JPanel ui_panel_00 = new JPanel();
        ui_panel_00.setLayout(null);
        ui_panel_00.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_panel_00.setBackground(THEME_COLOR);

        //ヘッダ
        JPanel ui_panel_01 = new JPanel();
        ui_panel_01.setLayout(null);
        ui_panel_01.setBounds(0, 0, WINDOW_WIDTH, 75);
        ui_panel_01.setBackground(THEME_COLOR);

        ui_panel_00.add(ui_panel_01);

        //ボタン月ボタン
        JPanel ui_panel_02 = new JPanel();
        ui_panel_02.setLayout(new BorderLayout());
        ui_panel_02.setBounds(WINDOW_WIDTH / 4 - 1, 0, WINDOW_WIDTH / 2, 75);
        ui_panel_02.setBackground(THEME_COLOR);
        JLabel ui_jl_month = new JLabel("", JLabel.CENTER);
        ui_jl_month.setText(Integer.toString(ui_ld_firstofmonth.getYear()) + "/" + (ui_ld_firstofmonth.getMonthValue() >= 10 ? "" : "0") + Integer.toString(ui_ld_firstofmonth.getMonthValue()));
        ui_jl_month.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_panel_02.add(ui_jl_month, "Center");
        JButton ui_jb_lastmonth = new JButton("<");
        ui_jb_lastmonth.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_jb_lastmonth.setBorderPainted(false);
        ui_jb_lastmonth.setBackground(THEME_COLOR);
        ui_jb_lastmonth.setOpaque(true);
        ui_jb_lastmonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ui_ld_firstofmonth = ui_ld_firstofmonth.minus(1, ChronoUnit.MONTHS);
                calendarScreen();
            }
        });
        ui_panel_02.add(ui_jb_lastmonth, "West");
        JButton ui_jb_nextmonth = new JButton(">");
        ui_jb_nextmonth.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_jb_nextmonth.setBorderPainted(false);
        ui_jb_nextmonth.setBackground(THEME_COLOR);
        ui_jb_nextmonth.setOpaque(true);
        ui_jb_nextmonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ui_ld_firstofmonth = ui_ld_firstofmonth.plus(1, ChronoUnit.MONTHS);
                calendarScreen();
            }
        });
        ui_panel_02.add(ui_jb_nextmonth, "East");

        ui_panel_01.add(ui_panel_02);

        //カレンダー
        JPanel ui_panel_06 = new JPanel();
        ui_panel_06.setLayout(null);
        ui_panel_06.setBounds(10, 75, WINDOW_WIDTH, 600 - 12);
        ui_panel_06.setBackground(THEME_COLOR);
        JPanel ui_panel_05 = new JPanel();
        ui_panel_05.setLayout(new BorderLayout());
        ui_panel_05.setSize(WINDOW_WIDTH - 24, 600 - 12);
        ui_panel_05.setBackground(THEME_COLOR);
        JScrollPane ui_panel_07 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ui_panel_07.setSize(WINDOW_WIDTH, 600);
        ui_panel_07.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 2));
        ui_panel_07.setBackground(THEME_COLOR);
        JScrollBar ui_sb_00 = ui_panel_07.getVerticalScrollBar();
        ui_sb_00.setOpaque(true);
        ui_sb_00.setBackground(THEME_COLOR);
        ui_sb_00.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 10));
        ui_sb_00.setVisible(false);
        int over = (getCalendarMatrics(35).getMonthValue() == ui_ld_firstofmonth.getMonthValue() ? 1 : 0);
        JPanel ui_panel_03 = new JPanel();
        ui_panel_03.setLayout(new GridLayout(5 + over, 7, 2, 13));
        ui_panel_03.setSize(WINDOW_WIDTH, 600 - 12);
        ui_panel_03.setBackground(THEME_COLOR);
        LocalDate date;

        for(int i = 0; i < 5 + over; i++) {
            for(int j = 0; j < 7; j++) {
                date = getCalendarMatrics(7 * i + j);
                System.out.println(date);
                ui_jb_calendar[7 * i + j] = new JButton();
                ui_jb_calendar[7 * i + j].setText((7 * i + j + 1 >= 10 ? "" : "0") + Integer.toString(7 * i + j + 1));
                try {
                    ui_jb_calendar[7 * i + j].setIcon(getDateIcon(date, 43, getNumberEvent(sortEvent(getADayEvents(date)),0), getNumberEvent(sortEvent(getADayEvents(date)),1)));
                } catch (Exception e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
                ui_jb_calendar[7 * i + j].setDisabledIcon(getDateIcon(date, 0, null, null));
                ui_jb_calendar[7 * i + j].setEnabled(date.getMonthValue() == ui_ld_firstofmonth.getMonthValue());
                ui_jb_calendar[7 * i + j].setMargin(new Insets(0, 0, 0, -20));
                ui_jb_calendar[7 * i + j].setBorderPainted(false);
                ui_jb_calendar[7 * i + j].setBackground(THEME_COLOR);
                ui_jb_calendar[7 * i + j].setOpaque(true);
                ui_jb_calendar[7 * i + j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                         ui_ld_looking = getCalendarMatrics(Integer.parseInt(ae.getActionCommand()) - 1);
                         dateScreen();
                    }
                });
                ui_panel_03.add(ui_jb_calendar[7 * i + j]);
            }
        }
        ui_panel_07.setViewportView(ui_panel_03);
        ui_panel_05.add(ui_panel_07, "Center");
        ui_panel_06.add(ui_panel_05);
        ui_panel_00.add(ui_panel_06);

        contentPane1.add(ui_panel_00);
        ui_clayout.show(contentPane0, "カレンダー画面");
        setVisible(true);
        repaint();
    }

    public LocalDate getCalendarMatrics(int num)
    {
        int offset = 0;
        switch(ui_ld_firstofmonth.getDayOfWeek()) {
            case SUNDAY:
                offset = 0;
                break;
            case MONDAY:
                offset = 1;
                break;
            case TUESDAY:
                offset = 2;
                break;
            case WEDNESDAY:
                offset = 3;
                break;
            case THURSDAY:
                offset = 4;
                break;
            case FRIDAY:
                offset = 5;
                break;
            case SATURDAY:
                offset = 6;
                break;
        }
        return (ui_ld_firstofmonth.plus(num - offset, ChronoUnit.DAYS));
    }

    public void setFooter(JPanel panel)
    {
        //カレンダー・ユーザ切り替え
        ui_jb_calendarwindow = new JButton("カレンダー");
        ui_jb_calendarwindow.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 36));
        ui_jb_calendarwindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                ui_clayout.show(contentPane0, "カレンダー画面");
            }
        });
        ui_jb_calendarwindow.setBorderPainted(false);
        ui_jb_calendarwindow.setOpaque(true);
        ui_jb_userwindow = new JButton("ユーザ");
        ui_jb_userwindow.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 36));
        ui_jb_userwindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                ui_clayout.show(contentPane0, "ユーザ画面");
            }
        });
        ui_jb_userwindow.setBorderPainted(false);
        ui_jb_userwindow.setOpaque(true);
        if(panel == contentPane1) {
            ui_jb_calendarwindow.setBackground(Color.WHITE);
            ui_jb_userwindow.setBackground(new Color(192, 192, 192));
        }
        else if(panel == contentPane2) {
            ui_jb_calendarwindow.setBackground(new Color(192, 192, 192));
            ui_jb_userwindow.setBackground(Color.WHITE);
        }
        footer = new JPanel();
        footer.setLayout(new GridLayout(1, 2));
        footer.setBounds(0, 675, WINDOW_WIDTH, 90);
        footer.add(ui_jb_calendarwindow);
        footer.add(ui_jb_userwindow);
        panel.add(footer);

    }

    private ImageIcon getDateIcon(LocalDate date, int event_number, ClientEvent event1, ClientEvent event2)
    {
        //玖津見が書いています
        int icon_width = 70;
        int icon_height = 100;
        int r = 12;
        int offset;
        Image img = createImage(icon_width, icon_height);
        Graphics g = img.getGraphics();
        Color c;
        LocalDate today = LocalDate.now();

        if(date.getMonthValue() != ui_ld_firstofmonth.getMonthValue()) { //今月じゃない
            c = new Color(217, 217, 217);
        }
        else if(date.equals(today)) { //今日
            c = new Color(255, 192, 0);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) { //日曜
            c = new Color(255, 179, 210);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) { //土曜
            c = new Color(197, 226, 255);
        }
        else { //平日
            c = new Color(255, 255, 255);
        }
        Client.kadomaruRect(g, 0, 0, icon_width, icon_height, r, c, THEME_COLOR);

        if(event1 != null) {
            String event1_name = event1.getEventName();
            if(!event1_name.equals("")) {
                if(account.getEventPreferred().contains(event1.getEventId())) { //いいね
                    g.setColor(new Color(0, 176, 240));
                }
                else{ //非いいね
                    g.setColor(new Color(242, 242, 242));
                }
                icon_width = icon_width - 6;
                icon_height = icon_height / 3 - 4;
                offset = 36;
                r = 10;
                Client.kadomaruRect(g, 3, offset, icon_width, icon_height, r);
                if(account.getEventPreferred().contains(event1.getEventId())) { //いいね
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
            }
        }
        if(event2 != null) {
            String event2_name = event2.getEventName();
            if(!event2_name.equals("")) {
                if(account.getEventPreferred().contains(event2.getEventId())) { //いいね
                    g.setColor(new Color(0, 176, 240));
                }
                else{ //非いいね
                    g.setColor(new Color(242, 242, 242));
                }
                offset = 68;
                Client.kadomaruRect(g, 3, offset, icon_width, icon_height, r);
                if(account.getEventPreferred().contains(event2.getEventId())) { //いいね
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
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        g.drawString(Integer.toString(date.getDayOfMonth()), 8, 24);

        if(event_number > 0) {
            g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
            g.drawString(Integer.toString(event_number % 10), 46, 24);
            if(event_number >= 10) {
                g.drawString(Integer.toString(event_number / 10), 38, 24);
            }
            g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
            g.drawString("件", 55, 23);
        }

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

        int WINDOW_HEIGHT = 700;

        contentPane1.removeAll();
        contentPane1.setLayout(null);
        setTitle("Communi+I");
        setFooter(contentPane1);


        // 全体
        JPanel ui_panel_00 = new JPanel();
        ui_panel_00.setLayout(null);
        ui_panel_00.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_panel_00.setBackground(THEME_COLOR);

        // ヘッダ
        JPanel ui_panel_01 = new JPanel();
        ui_panel_01.setLayout(null);
        ui_panel_01.setBounds(0, 0, WINDOW_WIDTH, 75);
        ui_panel_01.setBackground(THEME_COLOR);

        ui_panel_00.add(ui_panel_01);

        // 戻るボタン
        JButton backButton = new JButton("");
        backButton.setBounds(15, 13, 60, 50);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                calendarScreen();
            }
        });
        ImageIcon icon = new ImageIcon("back.png");
        backButton.setIcon(icon);

        // ボタンの余白を調整
        backButton.setMargin(new Insets(0, 0, 0, 0));

        // ボタンの枠線を非表示にする
        backButton.setBorderPainted(false);

        // ボタンの背景を透明にする
        backButton.setContentAreaFilled(false);
        ui_panel_01.add(backButton);

        // ボタン追加：右上に追加するボタン
        JButton addButton = new JButton("+");
        addButton.setBounds(WINDOW_WIDTH - 100, 13, 75, 50);
        addButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        addButton.setBorderPainted(false);
        addButton.setBackground(THEME_COLOR);
        addButton.setOpaque(true);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("イベントを追加します");
                // イベント作成画面のダイアログを作成
                JDialog eventDialog = new JDialog();
                eventDialog.setTitle("イベント作成");
                eventDialog.setModal(true); // モーダルダイアログとして設定
                eventDialog.setSize(500, 500);
                eventDialog.setLocationRelativeTo(null); // 中央に配置

                // イベント作成画面のコンポーネントを追加
                // TODO: イベント作成画面のコンポーネントを追加する処理を記述
                // イベント作成画面のコンポーネントを追加
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(null);
                eventPanel.setBackground(THEME_COLOR);

                // イベント名入力フィールド
                JLabel nameLabel = new JLabel("イベント名:");
                nameLabel.setBounds(20, 20, 100, 30);
                eventPanel.add(nameLabel);

                JTextField nameField = new JTextField();
                nameField.setBounds(120, 20, 315, 30);
                eventPanel.add(nameField);

                // 開始時間入力フィールド
                JLabel startTimeLabel = new JLabel("開始時刻:");
                startTimeLabel.setBounds(20, 60, 100, 30);
                eventPanel.add(startTimeLabel);
                
                int currentYear = ui_ld_looking.getYear();
                int currentMonth = ui_ld_looking.getMonthValue();
                int currentday = ui_ld_looking.getDayOfMonth();
                
                // 年のコンボボックス
                yearComboBox = new JComboBox<>();
                for (int year = currentYear - 10; year <= currentYear + 10; year++) {
                    yearComboBox.addItem(year);
                }
                yearComboBox.setSelectedItem(currentYear); // 現在の年を選択
                yearComboBox.setBounds(120, 60, 55, 30);
                yearComboBox.addActionListener(e -> updateDayComboBox(yearComboBox,monthComboBox,dayComboBox));
                eventPanel.add(yearComboBox);
                
                JLabel yearLabel = new JLabel("年");
                yearLabel.setBounds(175, 60, 20, 30);
                yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
                yearLabel.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(yearLabel);
                
                // 月のコンボボックス
                monthComboBox = new JComboBox<>();
                for (int month = 1; month <= 12; month++) {
                    monthComboBox.addItem(String.format("%02d", month));
                }
                monthComboBox.setSelectedItem(String.format("%02d", currentMonth)); // 現在の月を選択
                monthComboBox.setBounds(195, 60, 40, 30);
                monthComboBox.addActionListener(e -> updateDayComboBox(yearComboBox,monthComboBox,dayComboBox));
                eventPanel.add(monthComboBox);
                
                JLabel monthLabel = new JLabel("月");
                monthLabel.setBounds(235, 60, 20, 30);
                monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
                monthLabel.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(monthLabel);
                
                // 日のコンボボックス
                dayComboBox = new JComboBox<>();
                updateDayComboBox(yearComboBox,monthComboBox,dayComboBox); // 初期の日の選択肢を設定
                dayComboBox.setBounds(255, 60, 40, 30);
                dayComboBox.setSelectedItem(String.format("%02d", currentday));
                eventPanel.add(dayComboBox);
                
                JLabel dayLabel = new JLabel("日");
                dayLabel.setBounds(295, 60, 20, 30);
                dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
                dayLabel.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(dayLabel);

                // 時間のコンボボックス
                String[] hour = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
                JComboBox<String> hourComboBox = new JComboBox<>(hour);
                hourComboBox.setBounds(315, 60, 40, 30);
                eventPanel.add(hourComboBox);

                JLabel hourLabel = new JLabel("時");
                hourLabel.setBounds(355, 60, 20, 30);
                hourLabel.setHorizontalAlignment(SwingConstants.CENTER);
                hourLabel.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(hourLabel);

                String[] minute = {"00","05","10","15","20","25","30","35","40","45","50","55"};
                JComboBox<String> minuteComboBox = new JComboBox<>(minute);
                minuteComboBox.setBounds(375, 60, 40, 30);
                eventPanel.add(minuteComboBox);
                
                JLabel minuteLabel = new JLabel("分");
                minuteLabel.setBounds(415, 60, 20, 30);
                minuteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                minuteLabel.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(minuteLabel);



                // 終了時間入力フィールド
                JLabel endTimeLabel = new JLabel("終了時刻:");
                endTimeLabel.setBounds(20, 100, 100, 30);
                eventPanel.add(endTimeLabel);
                
                // 年のコンボボックス
                yearComboBox2 = new JComboBox<>();
                for (int year2 = currentYear - 10; year2 <= currentYear + 10; year2++) {
                    yearComboBox2.addItem(year2);
                }
                yearComboBox2.setSelectedItem(currentYear); // 現在の年を選択
                yearComboBox2.setBounds(120, 100, 55, 30);
                yearComboBox2.addActionListener(e -> updateDayComboBox(yearComboBox2,monthComboBox2,dayComboBox2));
                eventPanel.add(yearComboBox2);
                
                JLabel yearLabel2 = new JLabel("年");
                yearLabel2.setBounds(175, 100, 20, 30);
                yearLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                yearLabel2.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(yearLabel2);
                
                // 月のコンボボックス
                monthComboBox2 = new JComboBox<>();
                for (int month2 = 1; month2 <= 12; month2++) {
                    monthComboBox2.addItem(String.format("%02d", month2));
                }
                monthComboBox2.setSelectedItem(String.format("%02d", currentMonth)); // 現在の月を選択
                monthComboBox2.setBounds(195, 100, 40, 30);
                monthComboBox2.addActionListener(e -> updateDayComboBox(yearComboBox2,monthComboBox2,dayComboBox2));
                eventPanel.add(monthComboBox2);
                
                JLabel monthLabel2 = new JLabel("月");
                monthLabel2.setBounds(235, 100, 20, 30);
                monthLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                monthLabel2.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(monthLabel2);
                
                // 日のコンボボックス
                dayComboBox2 = new JComboBox<>();
                updateDayComboBox(yearComboBox2,monthComboBox2,dayComboBox2); // 初期の日の選択肢を設定
                dayComboBox2.setBounds(255, 100, 40, 30);
                dayComboBox2.setSelectedItem(String.format("%02d", currentday));
                eventPanel.add(dayComboBox2);
                
                JLabel dayLabel2 = new JLabel("日");
                dayLabel2.setBounds(295, 100, 20, 30);
                dayLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                dayLabel2.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(dayLabel2);

                // 時間のコンボボックス
                String[] hour2 = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
                JComboBox<String> hourComboBox2 = new JComboBox<>(hour2);
                hourComboBox2.setBounds(315, 100, 40, 30);
                eventPanel.add(hourComboBox2);

                JLabel hourLabel2 = new JLabel("時");
                hourLabel2.setBounds(355, 100, 20, 30);
                hourLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                hourLabel2.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(hourLabel2);

                String[] minute2 = {"00","05","10","15","20","25","30","35","40","45","50","55"};
                JComboBox<String> minuteComboBox2 = new JComboBox<>(minute2);
                minuteComboBox2.setBounds(375, 100, 40, 30);
                eventPanel.add(minuteComboBox2);
                
                JLabel minuteLabel2 = new JLabel("分");
                minuteLabel2.setBounds(415, 100, 20, 30);
                minuteLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                minuteLabel2.setVerticalAlignment(SwingConstants.CENTER);
                eventPanel.add(minuteLabel2);
                

                // 場所入力フィールド
                JLabel placeLabel = new JLabel("場所:");
                placeLabel.setBounds(20, 140, 100, 30);
                eventPanel.add(placeLabel);

                JTextField placeField = new JTextField();
                placeField.setBounds(120, 140, 315, 30);
                eventPanel.add(placeField);

                // 概要入力フィールド
                JLabel summaryLabel = new JLabel("概要:");
                summaryLabel.setBounds(20, 180, 100, 30);
                eventPanel.add(summaryLabel);

                JTextArea summaryArea = new JTextArea();
                summaryArea.setBounds(120, 180, 315, 80);
                summaryArea.setLineWrap(true);
                summaryArea.setWrapStyleWord(true);
                summaryArea.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                JScrollPane summaryScrollPane = new JScrollPane(summaryArea);
                summaryScrollPane.setBounds(120, 180, 315, 80);
                summaryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                summaryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                summaryScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
                eventPanel.add(summaryScrollPane);

                // 詳細入力フィールド
                JLabel detailsLabel = new JLabel("詳細:");
                detailsLabel.setBounds(20, 270, 100, 30);
                eventPanel.add(detailsLabel);

                JTextArea detailsArea = new JTextArea();
                detailsArea.setBounds(120, 270, 315, 80);
                detailsArea.setLineWrap(true);
                detailsArea.setWrapStyleWord(true);
                detailsArea.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
                detailsScrollPane.setBounds(120, 270, 315, 80);
                detailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                detailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                detailsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
                eventPanel.add(detailsScrollPane);


                // コミュニティ名入力フィールド
                JLabel communityLabel = new JLabel("コミュニティ名:");
                communityLabel.setBounds(20, 360, 100, 30);
                eventPanel.add(communityLabel);

                int com_size = community_list.size();
                String[] com_list = new String[com_size];
                for(int i=0;i<com_size;i++) {
                    com_list[i]=community_list.get(i).getName();
                }
                JComboBox<String> communityComboBox = new JComboBox<>(com_list);
                communityComboBox.setBounds(120, 360, 315, 30);
                eventPanel.add(communityComboBox);

                BufferedImage img = createBackgroundImage(60, 40);
                Graphics g = img.getGraphics();
                g.setColor(Color.WHITE);
                Client.kadomaruRect(g, 0, 0, 60, 40, 8, Color.WHITE, THEME_COLOR);
                g.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
                g.setColor(Color.BLACK);
                g.drawString("作成", 9, 27);
                JButton addButton = new JButton("");
                addButton.setBackground(THEME_COLOR);
                addButton.setForeground(Color.black);
                addButton.setOpaque(true);
                addButton.setBorderPainted(false);
                addButton.setBounds(220,400,60,40);
                addButton.setIcon(new ImageIcon(img));

                addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        String eventName = nameField.getText();
                        String startTime = (String)hourComboBox.getSelectedItem()+":"+(String)minuteComboBox.getSelectedItem();
                        String endTime = (String)hourComboBox2.getSelectedItem()+":"+(String)minuteComboBox2.getSelectedItem();
                        String place = placeField.getText();
                        String summary = summaryArea.getText();
                        String details = detailsArea.getText();
                        String communityName = (String)communityComboBox.getSelectedItem();

                        // 入力欄が空の場合、エラーメッセージを表示して進めないようにする
                        if (eventName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || place.isEmpty() ||
                            summary.isEmpty() || details.isEmpty() || communityName.isEmpty()) {
                            JOptionPane.showMessageDialog(eventDialog, "すべての項目を入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // 確認ダイアログを表示
                        int result = JOptionPane.showConfirmDialog(eventDialog,
                                "以下の情報でイベントを作成しますか？\n\n"
                                + "イベント名: " + eventName + "\n"
                                + "開始時間: " + startTime + "\n"
                                + "終了時間: " + endTime + "\n"
                                + "場所: " + place + "\n"
                                + "概要: " + summary + "\n"
                                + "詳細: " + details + "\n"
                                + "コミュニティ名: " + communityName,
                                "イベント作成の確認",
                                JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {
                            // OKボタンが押された場合の処理を記述
                            // ここでイベントを作成する処理を実行

                            System.out.println(ui_ld_looking);
                            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy");
                            String year = ui_ld_looking.format(formatter1);
                            String month = (String)monthComboBox.getSelectedItem();
                            String day1 = (String)dayComboBox.getSelectedItem();
                            String day2 = (String)dayComboBox2.getSelectedItem();
                            int intyear = Integer.parseInt(year);
                            int intmonth = Integer.parseInt(month);
                            int intday1 = Integer.parseInt(day1);
                            int intday2 = Integer.parseInt(day2);
                            ClientEvent event = new ClientEvent(eventName,intyear, intmonth, startTime,endTime,place,username,summary,details,communityName);
                            event_list.add(event);

                            //確認
                            int eve_size = event_list.size();
                            for(int i=0;i<eve_size;i++) {
                                String eve_name =event_list.get(i).getEventName();
                                System.out.println(eve_name);
                            }

                            for(int i=0;i<=community_list.size();i++) {
                                if(community_list.get(i).getName().equals(communityName)) {
                                    community_list.get(i).getCalendarMonth(intyear, intmonth).addEvent(event.getEventId(), intday1, intday2);
                                    break;
                                };
                            }
                            eventDialog.setVisible(false);
                            dateScreen();
                        }
                    }
                });

                eventPanel.add(addButton);

                // イベント作成ダイアログにパネルを追加
                eventDialog.add(eventPanel);


                eventDialog.setVisible(true);

            }
        });
        ui_panel_01.add(addButton);


        //ボタン月ボタン
        JPanel ui_panel_02 = new JPanel();
        ui_panel_02.setLayout(new BorderLayout());
        ui_panel_02.setBounds(WINDOW_WIDTH / 4 - 1, 0, WINDOW_WIDTH / 2, 75);
        ui_panel_02.setBackground(THEME_COLOR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        String formattedDate = ui_ld_looking.format(formatter);
        JLabel ui_jl_month = new JLabel("", JLabel.CENTER);
        ui_jl_month.setText(formattedDate);
        ui_jl_month.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_panel_02.add(ui_jl_month, "Center");
        JButton ui_jb_lastmonth = new JButton("<");
        ui_jb_lastmonth.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_jb_lastmonth.setBorderPainted(false);
        ui_jb_lastmonth.setBackground(THEME_COLOR);
        ui_jb_lastmonth.setOpaque(true);
        ui_jb_lastmonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ui_ld_looking = ui_ld_looking.minusDays(1);
                dateScreen();
            }
        });
        ui_panel_02.add(ui_jb_lastmonth, "West");
        JButton ui_jb_nextmonth = new JButton(">");
        ui_jb_nextmonth.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 32));
        ui_jb_nextmonth.setBorderPainted(false);
        ui_jb_nextmonth.setBackground(THEME_COLOR);
        ui_jb_nextmonth.setOpaque(true);
        ui_jb_nextmonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ui_ld_looking = ui_ld_looking.plusDays(1);
                dateScreen();
            }
        });
        ui_panel_02.add(ui_jb_nextmonth, "East");


        ui_panel_01.add(ui_panel_02);





        /*
         * イベントの取得
         * ここではダミーデータで対応します。
         */

        List<ClientEvent> eventList  = new ArrayList<>();
        ClientEvent event1 = new ClientEvent("イベント1", 2023, 07, "9:00", "19:00", "環境情報１号棟５１５室", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        ClientEvent event2 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event3 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event4 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event5 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event6 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event7 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event8 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event9 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");
        ClientEvent event10 = new ClientEvent("イベント2", 2023, 07, "10:00", "18:00", "オフィス", "USER", "セミナーです", "参加費無料", "PL-2-Group03");


        // ダミーデータの追加
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        eventList.add(event4);
        eventList.add(event5);
        eventList.add(event6);
        eventList.add(event7);
        eventList.add(event8);
        eventList.add(event9);
        eventList.add(event10);



        for (ClientEvent event : event_list) {
            System.out.println(event.getEventName());
        }

        int events_num = event_list.size();

        int button_width = 600;
        int button_height = 100;
        int r = 20;


        JPanel ui_panel_06 = new JPanel();
        ui_panel_06.setLayout(null);
        ui_panel_06.setBounds(13, 75, WINDOW_WIDTH, 550 - 12);
        ui_panel_06.setBackground(THEME_COLOR);
        JPanel ui_panel_05 = new JPanel();
        ui_panel_05.setLayout(new BorderLayout());
        ui_panel_05.setSize(WINDOW_WIDTH - 24, 550 - 12);
        ui_panel_05.setBackground(THEME_COLOR);

        // イベント一覧
        JPanel ui_panel_03 = new JPanel();
        ui_panel_03.setLayout(new GridLayout(events_num, 1, 4, 4));
        ui_panel_03.setBackground(THEME_COLOR); // ボタン間の隙間をTHEME_COLORで塗りつぶす

        for (int i = 0; i < events_num; i++) {

            Image img1 = createImage(button_width, button_height);
            Graphics g1 = img1.getGraphics();
            g1.setColor(Color.WHITE);
            Client.kadomaruRect(g1, 0, 0, button_width, button_height, r, Color.WHITE, THEME_COLOR);
            g1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
            g1.setColor(Color.BLACK);
            String name = event_list.get(i).getEventName();

            String s_time = event_list.get(i).getEventStart();
            String f_time = event_list.get(i).getEventFinish();
            String place = event_list.get(i).getEventPlace();
            String com_name = event_list.get(i).getEventCommunityName();
            int good_num = event_list.get(i).getGood();
            String id = event_list.get(i).getEventId();

            g1.drawString(name, 10, 30);
            g1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
            g1.drawString(s_time+"-"+f_time, 10, 90);
            g1.drawString(place, 170, 90);
            g1.drawString(com_name, 400, 30);
            g1.drawString("いいね数:"+Integer.toString(good_num), 400, 90);


            JButton eventButton = new JButton(name);
            eventButton.setBackground(THEME_COLOR);
            eventButton.setForeground(THEME_COLOR);
            eventButton.setOpaque(true);
            eventButton.setMargin(new Insets(0,0,0,0));
            eventButton.setBorderPainted(false);
            eventButton.setIcon(new ImageIcon(img1));

            eventButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    eve_id = id;
                    System.out.println(id);
                    eventScreen(event1, 16);
                }
            });


            ui_panel_03.add(eventButton);


        }



        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize(WINDOW_WIDTH, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 2));
        scrollPane.setBackground(THEME_COLOR);

        JScrollBar ui_sb_00 = scrollPane.getVerticalScrollBar();
        ui_sb_00.setOpaque(true);
        ui_sb_00.setBackground(THEME_COLOR);
        ui_sb_00.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 10));
        ui_sb_00.setVisible(false);

        scrollPane.setViewportView(ui_panel_03);
        ui_panel_05.add(scrollPane, "Center");
        ui_panel_06.add(ui_panel_05);
        ui_panel_00.add(ui_panel_06);

        JPanel ui_panel_07 = new JPanel();
        ui_panel_07.setLayout(new BorderLayout());
        ui_panel_07.setBounds(100, 613, WINDOW_WIDTH - 200, 62);
        ui_panel_07.setBackground(THEME_COLOR);

        int minValue = 0;   // スライダーの最小値
        int maxValue = 1000; // スライダーの最大値
        int initialValue = 1000; // スライダーの初期値

        JSlider slider = new JSlider(minValue, maxValue, initialValue);
        slider.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 2));
        slider.setBackground(THEME_COLOR);
        slider.setOpaque(false);

        JLabel valueLabel = new JLabel("いいねの数:"+String.valueOf(initialValue), JLabel.CENTER);
        valueLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));

        ui_panel_07.add(slider, BorderLayout.CENTER);
        ui_panel_07.add(valueLabel, BorderLayout.SOUTH);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                valueLabel.setText("いいねの数:"+String.valueOf(value));
                // スライダーの値が変更されたときの処理を記述
            }
        });




        ui_panel_00.add(ui_panel_07);




        contentPane1.add(ui_panel_00);
        setVisible(true);
        repaint();


    }
    
    private void updateDayComboBox(JComboBox<Integer> yearc,JComboBox<String> monthc,JComboBox<String> dayc) {
        int year = (int) yearc.getSelectedItem();
        int month = Integer.parseInt((String) monthc.getSelectedItem());
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        dayc.removeAllItems();
        for (int day = 1; day <= daysInMonth; day++) {
            dayc.addItem(String.format("%02d", day));
        }
    }

    //イベント画面 month, dayは表示のため
    void eventScreen(ClientEvent ce, int day) {
        setTitle("イベントの詳細");
        contentPane1.removeAll();
        contentPane1.setLayout(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setLayout(new BorderLayout());

        // 背景画像を表示するためのパネルを作成
        Image img0 = createImage(WINDOW_WIDTH, 675);
        Graphics g0 = img0.getGraphics();
        g0.setColor(THEME_COLOR);
        g0.fillRect(0, 0, WINDOW_WIDTH, 675);
        g0.setColor(Color.WHITE);
        kadomaruRect(g0, 50, 50, WINDOW_WIDTH - 100, 675 - 100, 75);
        ImagePanel backgroundPanel = new ImagePanel();
        //setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, 675);


        // 戻るボタン
        JButton backButton = new JButton("戻る");
        backButton.setBounds(10, 10, 60, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                userScreen();
            }
        });
        ImageIcon icon = new ImageIcon("back.png");
        backButton.setIcon(icon);

        // ボタンの余白を調整
        backButton.setMargin(new Insets(0, 0, 0, 0));

        // ボタンの枠線を非表示にする
        backButton.setBorderPainted(false);

        // ボタンの背景を透明にする
        backButton.setContentAreaFilled(false);
        backgroundPanel.add(backButton);

        //イベント情報を置くパネル
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(null);
        eventPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // タイトル
        JLabel titleLabel = new JLabel(ce.getMonthValue() +" / "+day);
        titleLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(WINDOW_WIDTH/2-100, 10, 200, 50);
        eventPanel.add(titleLabel);

        // イベント名
        JLabel eventNameLabel = new JLabel(ce.getEventName());
        eventNameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
        eventNameLabel.setBounds(70, 70, 400, 50);
        eventPanel.add(eventNameLabel);

        // イベントコミュニティ名
        JLabel eventCommunityNameLabel = new JLabel(ce.getEventCommunityName());
        eventCommunityNameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 17));
        eventCommunityNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        eventCommunityNameLabel.setBounds(WINDOW_WIDTH/2, 100, WINDOW_WIDTH/2-70, 50);
        eventPanel.add(eventCommunityNameLabel);

        // イベント名
        JLabel eventTimePlaceLabel = new JLabel(ce.getEventStart()+"-"+ce.getEventFinish()+" "+ce.getEventPlace() );
        eventTimePlaceLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 17));
        eventTimePlaceLabel.setBounds(70, 120, 400, 50);
        eventPanel.add(eventTimePlaceLabel);

        // イベント概要
        JTextArea eventOutlineLabel = new JTextArea(ce.getEventOutline(),20,1);
        eventOutlineLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        eventOutlineLabel.setLineWrap(true);
        eventOutlineLabel.setEditable(false);
        eventOutlineLabel.setBackground(Color.WHITE);
        eventOutlineLabel.setRows(calculateLineCount(eventOutlineLabel, WINDOW_WIDTH-140));
        Dimension d = eventOutlineLabel.getPreferredSize();
        eventOutlineLabel.setBounds(70, 170, WINDOW_WIDTH-140, d.height);
        eventPanel.add(eventOutlineLabel);

        // イベントオーナー名
        JLabel eventOwnerLabel = new JLabel(ce.getEventOwner());
        eventOwnerLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 17));
        eventOwnerLabel.setHorizontalAlignment(JLabel.RIGHT);
        eventOwnerLabel.setBounds(WINDOW_WIDTH/2, 170+d.height, WINDOW_WIDTH/2-70, 50);
        eventPanel.add(eventOwnerLabel);

        //イベント詳細
        JTextArea eventDetailLabel = new JTextArea(ce.getEventDetail(),18,1);

        eventDetailLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        eventDetailLabel.setLineWrap(true);
        eventDetailLabel.setEditable(false);
        eventDetailLabel.setBackground(Color.WHITE);
        eventDetailLabel.setRows(calculateLineCount(eventDetailLabel, WINDOW_WIDTH-130));
        Dimension d2 = eventDetailLabel.getPreferredSize();
        eventDetailLabel.setBounds(75, 270+d.height, WINDOW_WIDTH-150, d2.height);
        eventPanel.add(eventDetailLabel);
        if(account.getEventPreferred().contains(ce.getEventId())) {
            eventDetailLabel.setVisible(true);
        }
        else {
            eventDetailLabel.setVisible(false);
        }

        JLabel ui_jl_back = new JLabel("");
        ui_jl_back.setBounds(0, 0, WINDOW_WIDTH, 675);
        ui_jl_back.setIcon(new ImageIcon(img0));

        // いいねボタン
        ImageIcon iine;
        ImageIcon blueIine = new ImageIcon("blueiine.png");
        ImageIcon whiteIine = new ImageIcon("whiteiine.png");
        Image blueIineImg = Toolkit.getDefaultToolkit().getImage("blueiine.png");
        JButton goodButton = new JButton("いいね");
        goodButton.setContentAreaFilled(false);
        goodButton.setBorderPainted(false);
        if(account.getEventPreferred().contains(ce.getEventId())) {
            goodButton.setForeground(Color.white);
            iine = new ImageIcon(whiteIine.getImage());
        }
        else {
            goodButton.setForeground(GOOD_COLOR);
            iine = new ImageIcon(blueIine.getImage());
        }
        goodButton.setIcon(iine);
        goodButton.setBounds(WINDOW_WIDTH/2-140, 215+d.height, 120, 30);
        eventPanel.add(goodButton);

        //いいねボタン背景(非いいね時)
        BufferedImage img1 = createBackgroundImage(130, 40);
        Graphics2D g1 = (Graphics2D)img1.getGraphics();
        g1.setColor(Color.white);
        g1.fillRect(0, 0, 130, 40);
        g1.fillRoundRect(5, 5, 120, 30, 10, 10);
        g1.setColor(GOOD_COLOR);
        g1.setStroke(new BasicStroke(3));
        //g1.drawRoundRect(5, 5, 120, 30, 10, 10);
        g1.drawImage(blueIineImg, 5, 5, 30, 30, goodButton);
        JLabel goodButton_bg_false = new JLabel(new ImageIcon(img1));
        goodButton_bg_false.setBounds(WINDOW_WIDTH/2-145, 210+d.height, 130, 40);
        eventPanel.add(goodButton_bg_false);

        //いいねボタン背景(いいね時)
        BufferedImage img2 = createBackgroundImage(130, 40);
        Graphics2D g2 = (Graphics2D)img2.getGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, 130, 40);
        g2.setColor(GOOD_COLOR);
        g2.fillRoundRect(5, 5, 120, 30, 10, 10);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(5, 5, 120, 30, 10, 10);
        g2.drawImage(whiteIine.getImage(), 5, 5, 30, 30, goodButton);
        JLabel goodButton_bg_true = new JLabel(new ImageIcon(img2));
        goodButton_bg_true.setBounds(WINDOW_WIDTH/2-145, 210+d.height, 130, 40);
        eventPanel.add(goodButton_bg_true);

        if(account.getEventPreferred().contains(ce.getEventId())) {
            goodButton_bg_true.setVisible(true);
            goodButton_bg_false.setVisible(false);
        }
        else {
            goodButton_bg_false.setVisible(true);
            goodButton_bg_true.setVisible(false);
        }

        goodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                try {
                    Boolean tmp = cc.nice(ce.getEventId());
                    if(account.getEventPreferred().contains(ce.getEventId())) {
                        if(tmp ==false) {
                            goodButton.setForeground(Color.black);
                            goodButton_bg_false.setVisible(true);
                            goodButton_bg_true.setVisible(false);
                            eventDetailLabel.setVisible(false);
                            System.out.println("いいね解除しました");
                        }
                    }
                    else {
                        if(tmp == true) {
                            goodButton.setForeground(Color.WHITE);
                            goodButton_bg_false.setVisible(false);
                            goodButton_bg_true.setVisible(true);
                            eventDetailLabel.setVisible(true);
                            System.out.println("いいねしました");
                        }
                    }

                    //更新を呼ぶ
                } catch (Exception e) {
                    if(e.getMessage()==ClientConnect.ERROR) {
                        System.out.println("存在しないイベントです");
                    }
                    e.printStackTrace();
                }
            }
        });

        // 参加ボタン

        JButton joinButton = new JButton("参加  "+ce.getJoin());
        joinButton.setContentAreaFilled(false);
        joinButton.setBorderPainted(false);
        joinButton.setBounds(WINDOW_WIDTH/2+20, 215+d.height, 120, 30);
        if(account.getAEventGoing(ce.getEventId())) {
            joinButton.setForeground(Color.white);
        }
        else {
            joinButton.setForeground(JOIN_COLOR);
        }
        eventPanel.add(joinButton);

        //参加ボタン背景(非参加時)
        BufferedImage img3 = createBackgroundImage(130, 40);
        Graphics2D g3 = (Graphics2D)img3.getGraphics();
        g3.setColor(Color.white);
        g3.fillRect(0, 0, 130, 40);
        g3.fillRoundRect(5, 5, 120, 30, 10, 10);
        g3.setColor(JOIN_COLOR);
        g3.setStroke(new BasicStroke(3));
        g3.drawRoundRect(5, 5, 120, 30, 10, 10);
        JLabel joinButton_bg_false = new JLabel(new ImageIcon(img3));
        joinButton_bg_false.setBounds(WINDOW_WIDTH/2+15, 210+d.height, 130, 40);
        eventPanel.add(joinButton_bg_false);

        //参加ボタン背景(参加時)
        BufferedImage img4 = createBackgroundImage(130, 40);
        Graphics2D g4 = (Graphics2D)img4.getGraphics();
        g4.setColor(Color.white);
        g4.fillRect(0, 0, 130, 40);
        g4.setColor(JOIN_COLOR);
        g4.fillRoundRect(5, 5, 120, 30, 10, 10);
        g4.setStroke(new BasicStroke(3));
        g4.drawRoundRect(5, 5, 120, 30, 10, 10);
        JLabel joinButton_bg_true = new JLabel(new ImageIcon(img4));
        joinButton_bg_true.setBounds(WINDOW_WIDTH/2+15, 210+d.height, 130, 40);
        eventPanel.add(joinButton_bg_true);

        if(account.getAEventGoing(ce.getEventId())) {
            joinButton_bg_true.setVisible(true);
            joinButton_bg_false.setVisible(false);
        }
        else {
            joinButton_bg_false.setVisible(true);
            joinButton_bg_true.setVisible(false);
        }

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                try {
                    Boolean tmp = cc.joinEvent(ce.getEventId());
                    if(account.getAEventGoing(ce.getEventId())) {
                        if(tmp ==false) {
                            joinButton.setForeground(JOIN_COLOR);
                            joinButton_bg_false.setVisible(true);
                            joinButton_bg_true.setVisible(false);
                            System.out.println("参加解除しました");
                        }
                    }
                    else {
                        if(tmp == true) {
                        joinButton.setForeground(Color.WHITE);
                        joinButton_bg_false.setVisible(false);
                        joinButton_bg_true.setVisible(true);
                        System.out.println("参加しました");
                        }
                    }

                } catch (Exception e) {
                     if(e.getMessage()==ClientConnect.ERROR) {
                         System.out.println("存在しないイベントです");
                     }
                     e.printStackTrace();
                }
            }
        });


        //スクロール
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize(WINDOW_WIDTH, 675);
        scrollPane.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 2));
        scrollPane.setBackground(THEME_COLOR);

        JScrollBar ui_sb_00 = scrollPane.getVerticalScrollBar();
        ui_sb_00.setOpaque(true);
        ui_sb_00.setBackground(THEME_COLOR);
        ui_sb_00.setBorder(BorderFactory.createLineBorder(THEME_COLOR, 10));
        ui_sb_00.setVisible(true);

        scrollPane.setViewportView(eventPanel);

        contentPane1.add(backgroundPanel);
        backgroundPanel.add(scrollPane);

        eventPanel.add(ui_jl_back);
        // フッターなど
        setFooter(contentPane1);
        //setSize(400, 500);
        setVisible(true);
        repaint();
    }

    static int calculateLineCount(JTextArea textArea, int width) {
        // テキスト全体を取得
        String text = textArea.getText();
        // フォントとコンポーネントの幅を取得
        FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
        int componentWidth = width;
        // 折り返しの数を計算
        int lineCount = 1;
        int textWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int charWidth = fontMetrics.charWidth(c);
            textWidth += charWidth;
            if (textWidth > componentWidth) {
                lineCount++;
                textWidth = charWidth;
            }
        }
        System.out.println(lineCount);
        return lineCount;
    }

    //ユーザ画面
    void userScreen() {
        int button_width = 450;
        int button_height = 150;
        int r = 30;

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
        userScreen.setLayout(null);
        userScreen.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        userScreen.setBackground(THEME_COLOR);
        /*
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);
*/
        // ユーザ名のラベルとフィールド
        JPanel ui_panel_label = new JPanel();
        ui_panel_label.setLayout(new BorderLayout());
        ui_panel_label.setBounds(0, 0, WINDOW_WIDTH, 75);
        ui_panel_label.setBackground(THEME_COLOR);
        JLabel usernameLabel = new JLabel("ユーザ名: "+ username, JLabel.CENTER);
        usernameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
        ui_panel_label.add(usernameLabel);
        userScreen.add(ui_panel_label);
        //userScreen.add(usernameLabel, gbc);

        // パスワード変更ボタン
        Image img1 = createImage(button_width, button_height);
        Graphics g1 = img1.getGraphics();
        g1.setColor(Color.WHITE);
        Client.kadomaruRect(g1, 0, 0, button_width, button_height, r, Color.WHITE, THEME_COLOR);
        g1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 42));
        g1.setColor(Color.BLACK);
        g1.drawString("パスワード変更", 75, 94);
        JButton changePasswordButton = new JButton("1");
        //gbc.anchor = GridBagConstraints.CENTER;
        //gbc.insets = new Insets(10, 0, 0, 0);

        changePasswordButton.setBackground(THEME_COLOR);
        changePasswordButton.setForeground(THEME_COLOR);
        changePasswordButton.setOpaque(true);
        changePasswordButton.setMargin(new Insets(-3, -3, -3, -13));
        changePasswordButton.setBorderPainted(false);
        changePasswordButton.setIcon(new ImageIcon(img1));
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                passwordScreen();
            }
        });
        //userScreen.add(changePasswordButton, gbc);

        // コミュニティ管理ボタン
        Image img2 = createImage(button_width, button_height);
        Graphics g2 = img2.getGraphics();
        g2.setColor(Color.WHITE);
        Client.kadomaruRect(g2, 0, 0, button_width, button_height, r, Color.WHITE, THEME_COLOR);
        g2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 42));
        g2.setColor(Color.BLACK);
        g2.drawString("コミュニティ管理", 55, 94);
        JButton communityManageButton = new JButton("2");

        communityManageButton.setBackground(THEME_COLOR);
        communityManageButton.setForeground(THEME_COLOR);
        communityManageButton.setOpaque(true);
        communityManageButton.setMargin(new Insets(-3, -3, -3, -15));
        communityManageButton.setBorderPainted(false);
        communityManageButton.setIcon(new ImageIcon(img2));
        communityManageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                eventScreen(new ClientEvent("イベント1", 2023, 7, "9:00", "19:00", "環境情報１号棟５１５室", "ADMIN", "学会です", "ためになります", "PL-2-Group03"), 11);
            }
        });
        //userScreen.add(communityManageButton, gbc);

        // ログアウトボタン
        Image img3 = createImage(button_width, button_height);
        Graphics g3 = img3.getGraphics();
        g3.setColor(Color.WHITE);
        Client.kadomaruRect(g3, 0, 0, button_width, button_height, r, Color.WHITE, THEME_COLOR);
        g3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 42));
        g3.setColor(Color.BLACK);
        g3.drawString("ログアウト", 120, 94);
        JButton logOutButton = new JButton("3");
        logOutButton.setBackground(THEME_COLOR);
        logOutButton.setForeground(THEME_COLOR);
        logOutButton.setOpaque(false);
        logOutButton.setBorderPainted(false);
        logOutButton.setMargin(new Insets(-3, -3, -3, -15));
        logOutButton.setIcon(new ImageIcon(img3));
        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                cc.logout();
                login_flag = 0;
                logout();
            }
        });
        //userScreen.add(logOutButton, gbc);

        JPanel ui_panel_button = new JPanel();
        ui_panel_button.setLayout(new GridLayout(3, 1, 20, 20));
        ui_panel_button.setBounds(0, 75, WINDOW_WIDTH, 600 - 20);
        ui_panel_button.setBackground(THEME_COLOR);
        ui_panel_button.add(changePasswordButton);
        ui_panel_button.add(communityManageButton);
        ui_panel_button.add(logOutButton);
        userScreen.add(ui_panel_button);

        setFooter(contentPane2);

        contentPane2.add(userScreen);
        ui_clayout.show(contentPane0, "ユーザ画面");
        setVisible(true);

        repaint();
    }

    //パスワード変更画面
    void passwordScreen() {
        setTitle("パスワード変更");
        contentPane2.removeAll();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //setLayout(new BorderLayout());

        // 背景画像を表示するためのパネルを作成
        Image img0 = createImage(WINDOW_WIDTH, 675);
        Graphics g0 = img0.getGraphics();
        g0.setColor(THEME_COLOR);
        g0.fillRect(0, 0, WINDOW_WIDTH, 675);
        g0.setColor(Color.WHITE);
        kadomaruRect(g0, 50, 50, WINDOW_WIDTH - 100, 675 - 100, 75);
        ImagePanel backgroundPanel = new ImagePanel();
        //setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, 675);

        // 戻るボタン
        JButton backButton = new JButton("戻る");
        backButton.setBounds(10, 10, 60, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                userScreen();
            }
        });
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
        JLabel titleLabel = new JLabel("パスワード変更");
        titleLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        titleLabel.setBounds(100, 100, 400, 50);
        backgroundPanel.add(titleLabel);

        // 旧パスワード入力欄
        JLabel oldPasswordLabel = new JLabel("旧パスワード:");
        backgroundPanel.add(oldPasswordLabel);
        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordLabel.setBounds(100, 170, 400, 30);
        oldPasswordField.setBounds(100, 205, 400, 30);
        backgroundPanel.add(oldPasswordField);

        // 新パスワード入力欄
        JLabel newPasswordLabel = new JLabel("新パスワード:");
        backgroundPanel.add(newPasswordLabel);
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordLabel.setBounds(100, 270, 400, 30);
        newPasswordField.setBounds(100, 305, 400, 30);
        backgroundPanel.add(newPasswordField);

        // パスワード確認入力欄
        JLabel confirmPasswordLabel = new JLabel("パスワード確認:");
        backgroundPanel.add(confirmPasswordLabel);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordLabel.setBounds(100, 370, 400, 30);
        confirmPasswordField.setBounds(100, 405, 400, 30);
        backgroundPanel.add(confirmPasswordField);

        // 変更ボタン
        JButton changeButton = new JButton("変更");
        changeButton.setBounds(200, 480, 200, 30);
        changeButton.setBackground(new Color(230, 255, 179));
        backgroundPanel.add(changeButton);
        changeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                String oldPass = new String(oldPasswordField.getPassword());
                String newPass = new String(newPasswordField.getPassword());
                String confPass = new String(confirmPasswordField.getPassword());

                if(oldPass.isEmpty()|newPass.isEmpty()|confPass.isEmpty()) {
                    JOptionPane.showMessageDialog(Client.this, "全ての項目を入力してください。");
                }
                else {
                    if(oldPass.equals(password)) {
                        if(newPass.equals(confPass)) {
                            try {
                                cc.changePassword(newPass);
                            } catch (Exception e) {
                                String error = e.getMessage();
                                if(error.equals(ClientConnect.NOT_FOUND)) {
                                    JOptionPane.showMessageDialog(Client.this, "該当するユーザーがいません。");
                                    login_flag = 0;
                                }else if(error.equals(ClientConnect.BANNED)) {
                                    JOptionPane.showMessageDialog(Client.this, "該当ユーザーは無効化されています。");
                                    login_flag = 0;
                                }else if(error.equals(ClientConnect.AUTH)) {
                                    JOptionPane.showMessageDialog(Client.this, "パスワードが変更されました。再ログインをお願いします。");
                                    login_flag = 0;
                                }else {
                                    JOptionPane.showMessageDialog(Client.this, "不明なエラーが発生しました。再度お試しください。");
                                }
                            }
                            password = newPass;
                            JOptionPane.showMessageDialog(Client.this, "パスワードを変更しました。");
                            userScreen();
                        }
                        else {
                            JOptionPane.showMessageDialog(Client.this, "新パスワードとパスワード確認欄に入力したパスワードが一致しません。");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(Client.this, "旧パスワードが間違っています。");
                    }
                }
            }
        });

        JLabel ui_jl_back = new JLabel("");
        ui_jl_back.setBounds(0, 0, WINDOW_WIDTH, 675);
        ui_jl_back.setIcon(new ImageIcon(img0));
        backgroundPanel.add(ui_jl_back);


        setFooter(contentPane2);
        contentPane2.add(backgroundPanel);
        //setSize(400, 500);
        setVisible(true);
        repaint();
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
    
    //通知画面
    void notificationScreen() {
    	
    	final int WINDOW_WIDTH = 300;
        final int WINDOW_HEIGHT = 200;
        final int SCREEN_PADDING = 10;
    	
        frame = new JFrame("通知");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocation(getScreenRight() - WINDOW_WIDTH - SCREEN_PADDING, getScreenBottom() - WINDOW_HEIGHT - SCREEN_PADDING);

        // 全体
        JPanel ui_panel_00 = new JPanel();
        ui_panel_00.setLayout(null);
        ui_panel_00.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_panel_00.setBackground(THEME_COLOR);
        
        // 背景画像を表示するためのパネルを作成
        BufferedImage img0 = createBackgroundImage(WINDOW_WIDTH, WINDOW_HEIGHT);
        Graphics g0 = img0.getGraphics();
        g0.setColor(THEME_COLOR);
        g0.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g0.setColor(Color.WHITE);
        kadomaruRect(g0, 15, 10, WINDOW_WIDTH - 40, WINDOW_HEIGHT - 60, 20);
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        backgroundPanel.setBackground(THEME_COLOR);
        
        // 通知ラベルの設定
        // ここに通知内容を入れてください
        String number = ("2");
        String community = ("コミュニティ名");
        String event = ("イベント名");
        String message = ("テストです。どのように表示されるか確認しています。自動改行は有効ですか？\n改行も問題ありませんね。\n文字数が枠を超えてもスクロールできるように設計したつもりです。\nテストーーーーーー");
        
        JLabel numberLabel = new JLabel(number+"件の通知が届いています。");
        numberLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
        numberLabel.setBounds(20,10,190,20);
        ui_panel_00.add(numberLabel);
        
        JLabel community_event_Label = new JLabel(community+":"+event);
        community_event_Label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
        community_event_Label.setBounds(20,30,265,20);
        ui_panel_00.add(community_event_Label);
        
        JTextArea messageTextArea = new JTextArea("最新メッセージ:\n"+message);
        messageTextArea.setBounds(20,50,250,82);
        messageTextArea.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
        messageTextArea.setBackground(Color.white); // 背景を透明に設定
        messageTextArea.setBorder(null); // ボーダーを削除
        messageTextArea.setEditable(false); // 編集不可に設定
        messageTextArea.setLineWrap(true); // テキストの幅で自動的に改行
        
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20, 50, 255, 82); // スクロールペインのサイズを設定
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        scrollPane.setBackground(Color.WHITE);

        JScrollBar ui_sb_00 = scrollPane.getVerticalScrollBar();
        ui_sb_00.setOpaque(true);
        ui_sb_00.setBackground(Color.WHITE);
        ui_sb_00.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        ui_sb_00.setVisible(false);

        scrollPane.setViewportView(messageTextArea);
        ui_panel_00.add(scrollPane, "Center");
        
        JButton openButton = new JButton("Communi+Iを開く");
        openButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
        openButton.setBounds(80,132,130,15);
        openButton.setBackground(THEME_COLOR);
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                frame.setVisible(false);
                login();//使い方あってるかわからないので違っていたら直してください
                
            }
        });
        ui_panel_00.add(openButton);
        
        JLabel ui_jl_back = new JLabel("");
        ui_jl_back.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        ui_jl_back.setIcon(new ImageIcon(img0));
        backgroundPanel.add(ui_jl_back);
        ui_panel_00.add(backgroundPanel);
        
        frame.add(ui_panel_00);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true); // 常に他のウィンドウの上に表示

    }
    
    private int getScreenRight() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenBottom() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
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

    //ある日のイベントArrayList取得
    ArrayList<ClientEvent> getADayEvents(LocalDate date) throws Exception{
        ArrayList<ClientEvent> list = new ArrayList<>();
        for(Community community : community_list) {
            CalendarMonth calendar = community.getCalendarMonth(date.getYear(), date.getMonthValue());
            ArrayList<String> id_list = calendar.getDayEvent(date.getDayOfMonth());

            for(String id : id_list) {
                try {
                    list.add(getEventData(id));
                }
                catch(Exception e) {
                    throw e;
                }
            }
        }

        return list;
    }

    //ある曜日のイベントArrayList取得
    ArrayList<ClientEvent> getDayOfWeekEvents(int year, int month, int day_of_week){
        ArrayList<ClientEvent> events = new ArrayList<>();
        int first_day = 0;

        for(int i = 1; i < 8; i++) {
            LocalDate date = LocalDate.of(year, month, i);
            if(date.getDayOfWeek().getValue() == day_of_week) {
                first_day = i;
                break;
            }
        }

        for(int day = first_day; day < 32; day += 7) {
            LocalDate date = LocalDate.of(year, month, day);
            try {
                events.addAll(getADayEvents(date));
            } catch (Exception e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        return events;
    }

    //ある月のいいね数top10のイベントArrayList取得
    ArrayList<ClientEvent> getTopTenEvents(int year, int month){
        ArrayList<ClientEvent> event_all = new ArrayList<>();
        for(int day = 1; day < 32; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            try {
                event_all.addAll(getADayEvents(date));
            } catch (Exception e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        Collections.sort(event_all, new Comparator<ClientEvent>() {
            @Override
            public int compare(ClientEvent event1, ClientEvent event2) {
                return event2.getGood() - event1.getGood();
            }
        });

        while(event_all.size() < 10) {
            event_all.add(null);
        }

        ArrayList<ClientEvent> topten = new ArrayList<>(10);
        for(int i = 0; i < 10; i++) {
            topten.add(event_all.get(i));
        }

        return topten;
    }

    //イベントArrayListのソート
    ArrayList<ClientEvent> sortEvent(ArrayList<ClientEvent> events) throws Exception{
        ArrayList<ClientEvent> preferred = new ArrayList<>();
        ArrayList<ClientEvent> dispreferred = new ArrayList<>();

        for(ClientEvent event : events) {
            if(account.getAEventPreferrd(event.getEventId())) {
               preferred.add(event);
            }
            else {
                dispreferred.add(event);
            }
        }


        Collections.sort(preferred, new Comparator<ClientEvent>() {
            @Override
            public int compare(ClientEvent event1, ClientEvent event2) {
                return event2.getGood() - event1.getGood();
            }
        });

        Collections.sort(dispreferred, new Comparator<ClientEvent>() {
            @Override
            public int compare(ClientEvent event1, ClientEvent event2) {
                return event2.getGood() - event1.getGood();
            }
        });

        preferred.addAll(dispreferred);

        return preferred;
    }

    //?番目のイベントを返す
    ClientEvent getNumberEvent(ArrayList<ClientEvent> events, int num) {
        if(num < events.size()) {
            return events.get(num);
        }
        else {
            return null;
        }
    }

    //イベントデータ取得
    ClientEvent getEventData(String event_id) {
        ClientEvent result = null;
        for(ClientEvent event : event_list) {
            if(event.getEventId().equals(event_id)) {
                result = event;
                break;
            }
        }
        return result;
    }

    //イベントデータ表示
    void displayEventData(String event_id) throws Exception{
    }

    //イベント検索
    ArrayList<ClientEvent> searchEvent(String search_word) {
        ArrayList<ClientEvent> result_list = new ArrayList<>();
        for(ClientEvent event : event_list) {
            String[] word_list = search_word.split(" ");
            for(String word : word_list) {
                if(event.getEventName().contains(word)) {
                    result_list.add(event);
                    break;
                }
            }
        }
        return result_list;
    }


    //参加
    void joinEvent(String event_id) throws Exception{
        try {
            cc.joinEvent(event_id);
        }
        catch(Exception e) {
            throw e;
        }
    }

    //いいね
    void goodEvent(String event_id) throws Exception{
        try {
            cc.nice(event_id);
        }
        catch(Exception e) {
            throw e;
        }
    }

    //通報
    void reportEvent(String event_id,int year,int month) throws Exception{
        try {
            cc.report(event_id,year,month);
        }
        catch(Exception e) {
            throw e;
        }
    }

    //メッセージ送信
    void sendMessage(String event_id,String message) throws Exception{
        try {
            cc.sendMessage(event_id, message);
        }
        catch(Exception e) {
            throw e;
        }
    }

    //更新(使用しないように)
    int update() {
        try {
            this.community_list = (ArrayList<Community>) Arrays.asList(cc.getCommunitys(account.getCommunity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> ct = new ArrayList<>();
        community_list.forEach(e->{
            e.getCalendarArray().forEach(f->{
                f.getEventList().forEach(g->{
                    g.forEach(h->{
                        ct.add(h);
                    });
                });
            });
        });
        try {
            this.event_list = (ArrayList<ClientEvent>) Arrays.asList(cc.getEvents((String[])ct.toArray()));
        } catch (Exception e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        }
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

    void login()
    {
        //Accountオブジェクト取得
        
        //Communityオブジェクト取得

        //ClientEventオブジェクト取得

        ui_clayout.show(contentPane0, "カレンダー画面");
        userScreen();
        calendarScreen();
        addWindowListener(ui_wlistener);
    }

    //ログアウト
    int logout() {
    	timer.cancel();
        loginScreen();
        removeWindowListener(ui_wlistener);
        return 0;
    }

    void addTestData(Account account, Community community, ClientEvent event)
    {
        this.account = account;

        boolean check = true;
        for(Community com : community_list) {
            if(com.getName() == community.getName()) {
                check = false;
                break;
            }
        }
        if(check) { //同名のコミュニティがなければリストに追加
            community_list.add(community);
        }

        System.out.println("community:" + ((Community)community_list.get(0)).getCalendarMonth(2023, 7).getDayEvent(13));
        event_list.add(event);
        System.out.println("event:" + event_list);
    }

    void getNewMessage() { //更新の時はこれを呼んでください
        timer.cancel();
        timer = new Timer(false);
        TimerTask tt =new TimerTask() {
            public void run() {
            	if(login_flag ==1) {
            		update();
                	ArrayList<String> go = account.getEventGoing();
                	ArrayList<ClientEvent> go_event = null;
                	ArrayList<String[]> out_list= new ArrayList<>();
                	try {
                    	go_event = (ArrayList<ClientEvent>) Arrays.asList(cc.getEvents((String[])go.toArray()));
                    	go_event.forEach(event->{
                        	String[] tmp = {"","","",""};
                        	Message m = event.getNewOwnerMessage(account.getLastCheckInt());
                        	if((int)m.message2>0) {
                            	tmp[0] = event.getEventCommunityName();//コミュニティ名
                            	tmp[1] = event.getEventName();//イベント名
                            	tmp[2] = String.valueOf((int) m.message);//メッセージ数
                            	tmp[3] = (String) m.message2;//最新のメッセージ
                            	out_list.add(tmp);
                        	}
                    	});
                    	out_list.forEach(data->{
                        //dataを出力してください
                    	});
                	} catch (Exception e) {
                    // TODO 自動生成された catch ブロック
                    	e.printStackTrace();
                	}
            	}else {
            		timer.cancel();
            	}
            }
        };
        timer.schedule(tt,0,300000); //第2引数=何ミリ後に開始するか,第3引数=何ミリ秒おきか,とりあえず5分おきにしました.
    }
    



    public static void main(String[] args) {
        new Client();
        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Client loginUI = new Client();
                loginUI.setVisible(true);
            }
        });
        */
    }

}

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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
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

    private JButton[] ui_jb_calendar = new JButton[7 * 6];

    public final static int WINDOW_WIDTH = 600;
    public final static int WINDOW_HEIGHT = 800;
    public final static Color THEME_COLOR = new Color(230, 255, 179);
    public final static Color GOOD_COLOR = new Color(0, 176, 240);
    public final static Color JOIN_COLOR = new Color(255, 192, 0);

    private String username;
    private String password;
    private String macaddress;
    private ClientConnect cc;
    private Account account;
    private ArrayList<Community> community_list = new ArrayList<>();
    private ArrayList<ClientEvent> event_list = new ArrayList<>();

    private int login_flag;
    private int register_flag;
    private String eve_id;

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
                    // ログイン処理を行う
                    login_flag = 0;

                    if (login_flag == 0) {
                        JOptionPane.showMessageDialog(Client.this, "ログイン成功");
                        login();
                    } else {
                        JOptionPane.showMessageDialog(Client.this, "ログインに失敗しました。もう一度お試しください。");
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
                      // アカウント登録処理を行う
                    if(register_flag==0) {
                        JOptionPane.showMessageDialog(Client.this, "アカウント登録成功");
                        login();
                    }else {
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
                ui_jb_calendar[7 * i + j] = new JButton();
                ui_jb_calendar[7 * i + j].setText((7 * i + j + 1 >= 10 ? "" : "0") + Integer.toString(7 * i + j + 1));
                ui_jb_calendar[7 * i + j].setIcon(getDateIcon(date, 43, event_list.get(0), event_list.get(0)));
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
                eventDialog.setSize(400, 500);
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
                nameField.setBounds(120, 20, 250, 30);
                eventPanel.add(nameField);

                // 開始時間入力フィールド
                JLabel startTimeLabel = new JLabel("開始時間:");
                startTimeLabel.setBounds(20, 60, 100, 30);
                eventPanel.add(startTimeLabel);

                JTextField startTimeField = new JTextField();
                startTimeField.setBounds(120, 60, 250, 30);
                eventPanel.add(startTimeField);

                // 終了時間入力フィールド
                JLabel endTimeLabel = new JLabel("終了時間:");
                endTimeLabel.setBounds(20, 100, 100, 30);
                eventPanel.add(endTimeLabel);

                JTextField endTimeField = new JTextField();
                endTimeField.setBounds(120, 100, 250, 30);
                eventPanel.add(endTimeField);

                // 場所入力フィールド
                JLabel placeLabel = new JLabel("場所:");
                placeLabel.setBounds(20, 140, 100, 30);
                eventPanel.add(placeLabel);

                JTextField placeField = new JTextField();
                placeField.setBounds(120, 140, 250, 30);
                eventPanel.add(placeField);

                // 概要入力フィールド
                JLabel summaryLabel = new JLabel("概要:");
                summaryLabel.setBounds(20, 180, 100, 30);
                eventPanel.add(summaryLabel);

                JTextArea summaryArea = new JTextArea();
                summaryArea.setBounds(120, 180, 250, 80);
                summaryArea.setLineWrap(true);
                summaryArea.setWrapStyleWord(true);
                summaryArea.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                JScrollPane summaryScrollPane = new JScrollPane(summaryArea);
                summaryScrollPane.setBounds(120, 180, 250, 80);
                summaryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                summaryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                summaryScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
                eventPanel.add(summaryScrollPane);

                // 詳細入力フィールド
                JLabel detailsLabel = new JLabel("詳細:");
                detailsLabel.setBounds(20, 270, 100, 30);
                eventPanel.add(detailsLabel);

                JTextArea detailsArea = new JTextArea();
                detailsArea.setBounds(120, 270, 250, 80);
                detailsArea.setLineWrap(true);
                detailsArea.setWrapStyleWord(true);
                detailsArea.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
                JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
                detailsScrollPane.setBounds(120, 270, 250, 80);
                detailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                detailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                detailsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
                eventPanel.add(detailsScrollPane);


                // コミュニティ名入力フィールド
                JLabel communityLabel = new JLabel("コミュニティ名:");
                communityLabel.setBounds(20, 360, 100, 30);
                eventPanel.add(communityLabel);

                JTextField communityField = new JTextField();
                communityField.setBounds(120, 360, 250, 30);
                eventPanel.add(communityField);
                
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
                addButton.setBounds(170,400,60,40);
                addButton.setIcon(new ImageIcon(img));
                
                addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        String eventName = nameField.getText();
                        String startTime = startTimeField.getText();
                        String endTime = endTimeField.getText();
                        String place = placeField.getText();
                        String summary = summaryArea.getText();
                        String details = detailsArea.getText();
                        String communityName = communityField.getText();
                        
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



        for (ClientEvent event : eventList) {
            System.out.println(event.getEventName());
        }

        int events_num = eventList.size();

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
            String name = eventList.get(i).getEventName();

            String s_time = eventList.get(i).getEventStart();
            String f_time = eventList.get(i).getEventFinish();
            String place = eventList.get(i).getEventPlace();
            String com_name = eventList.get(i).getEventCommunityName();
            int good_num = eventList.get(i).getGood();
            String id = eventList.get(i).getEventId();

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
        JButton goodButton = new JButton("いいね");
        goodButton.setContentAreaFilled(false);
        goodButton.setBorderPainted(false);
        goodButton.setBounds(WINDOW_WIDTH/2-140, 215+d.height, 120, 30);
        if(account.getEventPreferred().contains(ce.getEventId())) {
            goodButton.setForeground(Color.white);
        }
        else {
            goodButton.setForeground(Color.black);
        }
        eventPanel.add(goodButton);

        //いいねボタン背景(非いいね時)
        BufferedImage img1 = createBackgroundImage(130, 40);
        Graphics2D g1 = (Graphics2D)img1.getGraphics();
        g1.setColor(Color.white);
        g1.fillRect(0, 0, 130, 40);
        g1.fillRoundRect(5, 5, 120, 30, 10, 10);
        g1.setColor(GOOD_COLOR);
        g1.setStroke(new BasicStroke(3));
        g1.drawRoundRect(5, 5, 120, 30, 10, 10);
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
                        goodButton.setForeground(Color.black);
                        goodButton_bg_false.setVisible(true);
                        goodButton_bg_true.setVisible(false);
                        eventDetailLabel.setVisible(false);
                        
                        //TODO not nice をここに
                        System.out.println("いいね解除しました");
                    	}else {
                    		System.out.println("同期ずれのため修正しました(現在はいいねされています)");
                    	}
                    }
                    else {
                        goodButton.setForeground(Color.WHITE);
                        goodButton_bg_false.setVisible(false);
                        goodButton_bg_true.setVisible(true);

                        eventDetailLabel.setVisible(true);

                        System.out.println("いいねしました");
                    	}else {
                    		System.out.println("同期ずれのため修正しました(現在はいいねされていません)");
                    	}
                    }

                    //更新を呼ぶ
                } catch (Exception e) {
                    if(e.getMessage()==ClientConnect.ERROR) {
                    	System.out.println("存在しないイベントにいいねされました");
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
            joinButton.setForeground(Color.black);
        }
        eventPanel.add(goodButton);

        //いいねボタン背景(非いいね時)
        BufferedImage img1 = createBackgroundImage(130, 40);
        Graphics2D g1 = (Graphics2D)img1.getGraphics();
        g1.setColor(Color.white);
        g1.fillRect(0, 0, 130, 40);
        g1.fillRoundRect(5, 5, 120, 30, 10, 10);
        g1.setColor(GOOD_COLOR);
        g1.setStroke(new BasicStroke(3));
        g1.drawRoundRect(5, 5, 120, 30, 10, 10);
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
                    if(account.getEventPreferred().contains(ce.getEventId())) {
                        goodButton.setForeground(Color.black);
                        goodButton_bg_false.setVisible(true);
                        goodButton_bg_true.setVisible(false);
                        eventDetailLabel.setVisible(false);
                        
                        //TODO not nice をここに
                        System.out.println("いいね解除しました");

                    }
                    else {
                        goodButton.setForeground(Color.WHITE);
                        goodButton_bg_false.setVisible(false);
                        goodButton_bg_true.setVisible(true);
                        eventDetailLabel.setVisible(true);

                        System.out.println("いいねしました");
                        cc.nice(ce.getEventId());
                    }

                } catch (Exception e) {
                    // TODO 自動生成された catch ブロック
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
                            changePassword(newPass, confPass);
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
    
    //ある日のイベント配列取得
    ClientEvent[] getADayEvents(LocalDate date) throws Exception{
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
        
        ClientEvent[] events = (ClientEvent[])(list.toArray());
        return events;
    }
    
    //イベント配列のソート
    ClientEvent[] sortEvent(ClientEvent[] events) throws Exception{   
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
        ClientEvent[] sorted_events = (ClientEvent[])(preferred.toArray());
        
        return sorted_events;
    }
    
    //?番目のイベントを返す
    ClientEvent getNumberEvent(ClientEvent[] events, int num) {
        if(num < events.length) {
            return events[num];
        }
        else {
         return null;   
        }
    }

    //イベントデータ取得
    ClientEvent getEventData(String event_id) throws Exception{
        ClientEvent event = null;
        try {
            event = cc.getEvent(event_id);
        }
        catch(Exception e) {
            throw e;
        }
        return event;
    }

    //イベントデータ表示
    void displayEventData(String event_id) throws Exception{
    }

    //イベント検索
    ClientEvent[] searchEvent(String search_word) {
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
        ClientEvent[] result_array = (ClientEvent[])(result_list.toArray());
        return result_array;
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
    void reportEvent(String event_id) throws Exception{
        try {
            cc.report(event_id);
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

    //更新
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
        loginScreen();
        removeWindowListener(ui_wlistener);
        return 0;
    }

    void addTestData(Account account, Community community, ClientEvent event)
    {
        this.account = account;
        community_list.add(community);
        event_list.add(event);
    }
    
    void getNewMessage() {
    	ArrayList<String> go = this.account.getEventGoing();
    	ClientEvent[] go_event = null;
    	try {
			go_event = cc.getEvents((String[])go.toArray());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	//go_eventの各要素でgetNewOwnerMessageのmessage2が0でないものを表示してください
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

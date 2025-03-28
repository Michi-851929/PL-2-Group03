import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Server extends JFrame implements ActionListener{
    private ServerConnect thread = null;

    private ArrayList<Account> account_list = new ArrayList<>();
    private ArrayList<Community> community_list = new ArrayList<>();
    private ArrayList<ClientEvent> event_list = new ArrayList<>();

    private JPanel ui_panel_00;
    private JPanel ui_panel_01;
    private JButton ui_jb_make;
    private JButton ui_jb_ban;
    private JButton ui_jb_deban;
    private JButton ui_jb_exit;
    private TextArea ui_ta_log;
    private JLabel ui_jl_command;
    private JPanel ui_panel_07;
    private CardLayout ui_clayout;
    private JTextField ui_tf_text0;
    private JTextField ui_tf_text1;
    private JPasswordField ui_tf_text2;
    private JLabel ui_jl_alert;
    private JButton ui_jb_commit;
    private JButton ui_jb_back;

    //アカウント作成可否を表す定数
    public final static int DUPLICATE_NOT = 0; //被りなし(可)
    public final static int DUPLICATE_NAME = 1; //名前被り
    public final static int DUPLICATE_MAC = 2; //MACアドレス被り

    //ログインエラーの返り値
    public final static int LOGIN_NOTEXIST = 3;
    public final static int LOGIN_BANNED = 4;

    public Server()
    {
        //ウィンドウの生成
        super("Communi+I Server");
        setLayout(null);

        ui_panel_00 = new JPanel();
        ui_panel_00.setLayout(new BorderLayout());
        ui_panel_00.setBounds(0, 0, 600 - 15, 400 - 35);

        ui_panel_01 = new JPanel();
        ui_panel_01.setLayout(new FlowLayout());
        ui_panel_01.setBackground(Color.WHITE);
        ui_panel_01.setBounds(50, 30, 500, 300);
        ui_panel_01.setVisible(false);
        ui_panel_00.add(ui_panel_01);

        JPanel ui_panel_02 = new JPanel();
        ui_panel_02.setLayout(new GridLayout(4, 1, 0, 20));
        ui_jb_make = new JButton("アカウント作成");
        ui_jb_make.setBackground(Color.cyan);
        ui_jb_make.setOpaque(true);
        ui_jb_make.setBorderPainted(false);
        ui_jb_make.addActionListener(this);
        ui_panel_02.add(ui_jb_make, "A");
        ui_jb_ban = new JButton("アカウント追放");
        ui_jb_ban.setBackground(Color.cyan);
        ui_jb_ban.setOpaque(true);
        ui_jb_ban.setBorderPainted(false);
        ui_jb_ban.addActionListener(this);
        ui_panel_02.add(ui_jb_ban, "B");
        ui_jb_deban = new JButton("アカウント復活");
        ui_jb_deban.setBackground(Color.cyan);
        ui_jb_deban.setOpaque(true);
        ui_jb_deban.setBorderPainted(false);
        ui_jb_deban.addActionListener(this);
        ui_panel_02.add(ui_jb_deban, "C");
        ui_jb_exit = new JButton("サーバ終了");
        ui_jb_exit.setForeground(Color.WHITE);
        ui_jb_exit.setBackground(Color.RED);
        ui_jb_exit.setOpaque(true);
        ui_jb_exit.setBorderPainted(false);
        ui_jb_exit.addActionListener(this);
        ui_panel_02.add(ui_jb_exit, "D");
        ui_panel_00.add(ui_panel_02, "West");

        JPanel ui_panel_03 = new JPanel();
        ui_panel_03.setLayout(new BorderLayout());
        ui_ta_log = new TextArea("", 100, 100, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ui_ta_log.setText("Server Logs");
        ui_ta_log.setEditable(false);
        ui_panel_03.add(ui_ta_log);
        ui_panel_00.add(ui_panel_03, "Center");

        add(ui_panel_00);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setVisible(true);


        //データベースの初期化
        Gson gson = new Gson();
        BufferedReader br0 = null;
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        String account_datastr = "";
        String community_datastr = "";
        String event_datastr = "";
        java.lang.reflect.Type list_type0 = new TypeToken<List<Account>>(){}.getType();
        java.lang.reflect.Type list_type1 = new TypeToken<List<Community>>(){}.getType();
        java.lang.reflect.Type list_type2 = new TypeToken<List<ClientEvent>>(){}.getType();
        try {
            br0 = new BufferedReader(new FileReader("src/user_accounts_database.json"));
            br1 = new BufferedReader(new FileReader("src/community_database.json"));
            br2 = new BufferedReader(new FileReader("src/event_database.json"));
            String s;
            while((s = br0.readLine()) != null) {
                account_datastr += s;
            }
            while((s = br1.readLine()) != null) {
                community_datastr += s;
            }
            while((s = br2.readLine()) != null) {
                event_datastr += s;
            }
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        account_list = gson.fromJson(account_datastr, list_type0);
        community_list = gson.fromJson(community_datastr, list_type1);
        event_list = gson.fromJson(event_datastr, list_type2);

    }

    public void newWindow(String text)
    {
        ui_jb_make.setEnabled(false);
        ui_jb_ban.setEnabled(false);
        ui_jb_deban.setEnabled(false);
        ui_jb_exit.setEnabled(false);
        ui_panel_01.removeAll();

        JPanel ui_panel_04 = new JPanel();
        ui_panel_04.setLayout(new BorderLayout(32, 32));
        ui_panel_04.setBackground(Color.WHITE);
        ui_panel_04.setSize(500, 300);
        ui_jl_command = new JLabel(text, JLabel.CENTER);
        ui_jl_command.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
        ui_panel_04.add(ui_jl_command, "North");
        JPanel ui_panel_05 = new JPanel();
        ui_panel_05.setLayout(new GridLayout(3, 1));
        ui_panel_05.setBackground(Color.WHITE);
        ui_tf_text0 = new JTextField(20);
        ui_tf_text0.setText("ユーザ名");
        ui_tf_text0.setForeground(Color.LIGHT_GRAY);
        ui_tf_text0.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                String s = ui_tf_text0.getText();
                if (s.equals("ユーザ名")) {
                    ui_tf_text0.setText("");
                    ui_tf_text0.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent fe) {
                String s = ui_tf_text0.getText();
                if (s.equals("")) {
                    ui_tf_text0.setText("ユーザ名");
                    ui_tf_text0.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
        ui_tf_text0.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
        ui_panel_05.add(ui_tf_text0, "A");
        if(text.equals("アカウント作成")) {
            ui_panel_07 = new JPanel();
            ui_clayout = new CardLayout();
            ui_panel_07.setLayout(ui_clayout);
            ui_panel_07.setBackground(Color.WHITE);
            ui_tf_text1 = new JTextField(20);
            ui_tf_text1.setText("パスワード");
            ui_tf_text1.setForeground(Color.LIGHT_GRAY);
            ui_tf_text1.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent fe) {
                    ui_clayout.show(ui_panel_07, "text2");
                    ui_tf_text2.requestFocus();
                }
                public void focusLost(FocusEvent fe) {

                }
            });
            ui_tf_text1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
            ui_panel_07.add(ui_tf_text1, "text1");
            ui_tf_text2 = new JPasswordField(20);
            ui_tf_text2.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent fe) {

                }
                public void focusLost(FocusEvent fe) {
                    String s = new String(ui_tf_text2.getPassword());
                    if (s.equals("")) {
                        ui_clayout.show(ui_panel_07, "text1");
                    }
                }
            });
            ui_tf_text2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
            ui_panel_07.add(ui_tf_text2, "text2");
            ui_panel_05.add(ui_panel_07, "B");
        }
        ui_jl_alert = new JLabel("", JLabel.CENTER);
        ui_jl_alert.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
        ui_jl_alert.setForeground(Color.RED);
        ui_panel_05.add(ui_jl_alert, "C");
        ui_panel_04.add(ui_panel_05, "Center");
        JPanel ui_panel_06 = new JPanel();
        ui_panel_06.setLayout(new GridLayout(2, 1, 10, 10));
        ui_panel_06.setBackground(Color.WHITE);
        String new_text = String.format("%c%c", text.charAt(5), text.charAt(6));
        ui_jb_commit = new JButton(new_text);
        ui_jb_commit.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        ui_jb_commit.setBackground(Color.WHITE);
        ui_jb_commit.addActionListener(this);
        ui_panel_06.add(ui_jb_commit);
        ui_jb_back = new JButton("戻る");
        ui_jb_back.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
        ui_jb_back.setBackground(Color.WHITE);
        ui_jb_back.addActionListener(this);
        ui_panel_06.add(ui_jb_back);
        ui_panel_04.add(ui_panel_06, "South");
        ui_panel_01.add(ui_panel_04);

        ui_panel_01.setVisible(true);
    }

    public void stdout(String text) {
        Calendar calendar = Calendar.getInstance();
        ui_ta_log.append("\n[" + calendar.getTime() + "] " + text);
    }

    public int isCreatableAccount(String user_name, String mac_adress)
    {
        int is_creatable = DUPLICATE_NOT;
        if(mac_adress.equals("")) {
            for(Account account : account_list) {
                if(account.getUserName().equals(user_name)) {
                    is_creatable = DUPLICATE_NAME;
                    break;
                }
            }
        }
        else {
            for(Account account : account_list) {
                if(account.getMacAdress().equals(mac_adress)) {
                    is_creatable = DUPLICATE_MAC;
                    break;
                }
                if(account.getUserName().equals(user_name)) {
                    is_creatable = DUPLICATE_NAME;
                    break;
                }
            }
        }
        return is_creatable;
    }

    public void createAccount(String user_name, String password, String mac_adress)
    {
        Account new_account = new Account(user_name, password, mac_adress);
        account_list.add(new_account);
        stdout("createAccount: " + user_name);
    }

    public Account getAccount(String user_name)
    {
        Account account = null;
        boolean is_successed = false;
        for(Account ac : account_list) {
            if(ac.getUserName().equals(user_name)) {
                account = ac;
                is_successed = true;
                break;
            }
        }
        if(is_successed) {
            return account;
        }
        else {
            return new Account("", "", "");
        }
    }

    public boolean isBanned(String user_name)
    {
        return getAccount(user_name).isBanned();
    }

    public boolean banAccount(String user_name)
    {
        Account account = getAccount(user_name);
        boolean is_successed = false;
        if(!account.getUserName().equals("")) {
            account.banAccount();
            stdout("banAccount: " + user_name);
            is_successed = true;
        }
        return is_successed;
    }

    public boolean permitAccount(String user_name)
    {
        Account account = getAccount(user_name);
        boolean is_successed = false;
        if(!account.getUserName().equals("")) {
            account.permitAccount();
            stdout("permitAccount: " + user_name);
            is_successed = true;
        }
        return is_successed;
    }

    public int logIn(String user_name, String password)
    {
        Account account = getAccount(user_name);
        if(account.getUserName().equals("")) {
            return LOGIN_NOTEXIST;
        }
        else if(account.isBanned()) {
            return LOGIN_BANNED;
        }
        else {
            int tmp = account.verifyPassword(password);
            return tmp;
        }
    }

    public int changePassword(String user_name, String old_password, String new_password)
    {
        return getAccount(user_name).setPassword(old_password, new_password);
    }

    public Community getCommunity(String community_name)
    {
        Community community = null;
        boolean is_successed = false;
        for(Community co : community_list) {
            if(co.getName().equals(community_name)) {
                community = co;
                is_successed = true;
                break;
            }
        }
        if(is_successed) {
            return community;
        }
        else {
            return new Community("", "", "", null);
        }
    }

    public Community[] searchCommunity(String search_word)
    {
        ArrayList<Community> result_list = new ArrayList<>();
        for(Community community : community_list) {
            String[] word_list = search_word.split(" ");
            limit:
            for(String word : word_list) {
                if(community.getName().contains(word)) {
                    result_list.add(community);
                    break;
                }
                String[] tags = community.getTag();
                for(int i = 0; i < tags.length; i++) {
                    if(tags[i] != null) {
                        if(tags[i].contains(word)) {
                            result_list.add(community);
                            break limit;
                        }
                    }
                }
            }
        }

        Community[] result_array = (Community[])(result_list.toArray(new Community[result_list.size()]));
        return result_array;
    }

    public int isCreatableCommunity(String community_name)
    {
        int is_creatable = DUPLICATE_NOT;
        for(Community community : community_list) {
            if(community.getName().equals(community_name)) {
                is_creatable = DUPLICATE_NAME;
                break;
            }
        }
        return is_creatable;
    }

    public void createCommunity(Community community)
    {
        community_list.add(community);
        getAccount(community.getOwner()).addCommunity(community.getName());
        community.addMember(community.getOwner());
        stdout("createCommunity: " + community.getName() + " by " + community.getOwner());
    }

    public void removeCommunity(String community_name)
    {
        community_list.remove(getCommunity(community_name));
        stdout("removeCommunity: " + community_name);
    }

    public void joinCommunity(String community_name, String user_name)
    {
        getAccount(user_name).addCommunity(community_name);
        getCommunity(community_name).addMember(user_name);
    }

    public void quitCommunity(String community_name, String user_name)
    {
        getAccount(user_name).removeCommunity(community_name);
        getCommunity(community_name).remMember(user_name);
    }

    public ClientEvent getEvent(String event_id)
    {
        ClientEvent event = null;
        boolean is_successed = false;
        for(ClientEvent ce : event_list) {
            if(ce.getEventId().equals(event_id)) {
                event = ce;
                is_successed = true;
                break;
            }
        }
        if(is_successed) {
            return event;
        }
        else {
            return new ClientEvent("", 0, 0, "", "", "", "", "", "", "");
        }
    }

    public boolean createEvent(ClientEvent event, int year, int month, int day_start, int day_end)
    {
        MessageDigest sha256 = null;
        Calendar calendar = Calendar.getInstance();
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Account owner=this.getAccount(event.getEventOwner());
        if(owner.getEventMakeSig(this)) {
            event.setEventId(String.format("%40x", new BigInteger(1, sha256.digest((event.getEventOwner() + calendar.getTime() + event.getEventName()).getBytes()))));
            event_list.add(event);
            getCommunity(event.getEventCommunityName()).getCalendarMonth(year, month).addEvent(event.getEventId(), day_start, day_end);
            owner.addEventMade(event.getEventId());
            stdout("createEvent: " + event.getEventName() + " in " + event.getEventCommunityName());
            return true;
        }else {
            return false;
        }
    }

    public void deleteEvent(String event_id, int year, int month)
    {
        ClientEvent event = getEvent(event_id);
        getCommunity(event.getEventCommunityName()).getCalendarMonth(year, month).removeEvent(event_id);
        event_list.remove(getEvent(event_id));
    }

    public void manageEvent(ClientEvent event)
    {
        ClientEvent server_event = getEvent(event.getEventId());
        server_event.setEventName(event.getEventName());
        server_event.setEventStart(event.getEventStart());
        server_event.setEventFinish(event.getEventFinish());
        server_event.setEventPlace(event.getEventPlace());
        server_event.setEventOutline(event.getEventOutline());
        server_event.setEventDetail(event.getEventDetail());
    }

    public int setPreferredEvent(String user_name, String event_id)
    {
        getAccount(user_name).addEventPreferred(event_id);
        return getEvent(event_id).increaseGood();
    }

    public int setDispreferEvent(String user_name, String event_id)
    {
        getAccount(user_name).removeEventPreferred(event_id);
        return getEvent(event_id).decreaseGood();
    }


    public int setPresentEvent(String user_name, String event_id)
    {
        getAccount(user_name).addEventGoing(event_id);
        return getEvent(event_id).increaseJoin();
    }

    public int setAbsentEvent(String user_name, String event_id, String message)
    {
        ClientEvent event = getEvent(event_id);
        getAccount(user_name).removeEventGoing(event_id);
        event.sendCancel(user_name, message);
        return event.decreaseJoin();
    }

    public void reportEvent(String event_id,int year,int month)
    {
        ClientEvent report_event=getEvent(event_id);
        if(report_event.increaseReport()>10) {
            this.deleteEvent(event_id, year, month);
        }

    }

    public void addHostMessage(String event_id, String message)
    {
        getEvent(event_id).setOwnerMessage(message);
    }


    
    public void actionPerformed(ActionEvent ae)
    {
        String s = ae.getActionCommand();
        switch(s) {
        case "作成":
            String s0 = ui_tf_text0.getText();
            String s1 = new String(ui_tf_text2.getPassword());
            if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s0.equals("") || s1.equals("")) {
                ui_jl_alert.setText("ユーザ名とパスワードを入力してください");
                break;
            }
            int duplicate_num = isCreatableAccount(s0, "");
            switch(duplicate_num) {
                case DUPLICATE_NOT:
                    createAccount(s0, s1, "");
                    ui_jb_make.setEnabled(true);
                    ui_jb_ban.setEnabled(true);
                    ui_jb_deban.setEnabled(true);
                    ui_jb_exit.setEnabled(true);
                    ui_panel_01.setVisible(false);
                    break;
                case DUPLICATE_NAME:
                    ui_jl_alert.setText("別のユーザ名を入力してください");
                    break;
                default:
                    break;
            }
            break;
        case "追放":
            String s2 = ui_tf_text0.getText();
            if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s2.equals("")) {
                ui_jl_alert.setText("ユーザ名を入力してください");
            }
            if(banAccount(s2)) {
                ui_jb_make.setEnabled(true);
                ui_jb_ban.setEnabled(true);
                ui_jb_deban.setEnabled(true);
                ui_jb_exit.setEnabled(true);
                ui_panel_01.setVisible(false);
            }
            else {
                ui_jl_alert.setText("ユーザ名が正しくありません");
            }
            break;
        case "復活":
            String s3 = ui_tf_text0.getText();
            if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s3.equals("")) {
                ui_jl_alert.setText("ユーザ名を入力してください");
            }
            if(permitAccount(s3)) {
                ui_jb_make.setEnabled(true);
                ui_jb_ban.setEnabled(true);
                ui_jb_deban.setEnabled(true);
                ui_jb_exit.setEnabled(true);
                ui_panel_01.setVisible(false);
            }
            else {
                ui_jl_alert.setText("ユーザ名が正しくありません");
            }
            break;
        case "戻る":
            ui_jb_make.setEnabled(true);
            ui_jb_ban.setEnabled(true);
            ui_jb_deban.setEnabled(true);
            ui_jb_exit.setEnabled(true);
            ui_panel_01.setVisible(false);
            break;
        case "サーバ終了":
            try {
                thread.stop();
            }
            catch(Exception e) {
                //e.printStackTrace();
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fw0 = null;
            FileWriter fw1 = null;
            FileWriter fw2 = null;
            try {
                fw0 = new FileWriter("src/user_accounts_database.json");
                fw1 = new FileWriter("src/community_database.json");
                fw2 = new FileWriter("src/event_database.json");
                fw0.write(gson.toJson(account_list));
                fw1.write(gson.toJson(community_list));
                fw2.write(gson.toJson(event_list));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    fw0.close();
                    fw1.close();
                    fw2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        default:
            newWindow(s);
            break;
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        try {
            server.thread = new ServerConnect(server);
            server.thread.run();
        }
        catch (Exception e) {
            server.stdout(e.getMessage());
        }

    }
}
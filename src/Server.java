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
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener, FocusListener{
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
	
	public Server()
	{
		super("Communi+I Server");
		setLayout(null);
		
		ui_panel_00 = new JPanel();
		ui_panel_00.setLayout(new BorderLayout());
		ui_panel_00.setBounds(0, 0, 600 - 15, 400 - 35);
		
		ui_panel_01 = new JPanel();
		ui_panel_01.setLayout(new FlowLayout());
		ui_panel_01.setBounds(50, 30, 500, 300);
		ui_panel_01.setVisible(false);
		ui_panel_00.add(ui_panel_01);
		
		JPanel ui_panel_02 = new JPanel();
		ui_panel_02.setLayout(new GridLayout(4, 1, 0, 20));
		ui_jb_make = new JButton("アカウント作成");
		ui_jb_make.addActionListener(this);
		ui_panel_02.add(ui_jb_make, "A");
		ui_jb_ban = new JButton("アカウント追放");
		ui_jb_ban.addActionListener(this);
		ui_panel_02.add(ui_jb_ban, "B");
		ui_jb_deban = new JButton("アカウント復活");
		ui_jb_deban.addActionListener(this);
		ui_panel_02.add(ui_jb_deban, "C");
		ui_jb_exit = new JButton("サーバ終了");
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
		
		/*
		 * 
		 * ↓この間にフィールドのリストを初期化する処理を入れる↓
		 * 
		 */
		
		
		
		/*
		 * 
		 * ↑この間にフィールドのリストを初期化する処理を入れる↑
		 * 
		 */
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
		ui_panel_04.setSize(500, 300);
		ui_jl_command = new JLabel(text, JLabel.CENTER);
		ui_jl_command.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
		ui_panel_04.add(ui_jl_command, "North");
		JPanel ui_panel_05 = new JPanel();
		ui_panel_05.setLayout(new GridLayout(3, 1));
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
		String new_text = String.format("%c%c", text.charAt(5), text.charAt(6));
		ui_jb_commit = new JButton(new_text);
		ui_jb_commit.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		ui_jb_commit.addActionListener(this);
		ui_panel_06.add(ui_jb_commit);
		ui_jb_back = new JButton("戻る");
		ui_jb_back.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
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
	
	/*
	 * 
	 * ↓この間のメソッドを書いてね↓
	 * 
	 */
	
	public void banAccount(String user_name)
	{
		
	}
	
	public void debanAccount(String user_name)
	{
		
	}
	
	public void createAccount(String user_name, String password)
	{
		
	}
	
	public void logIn(String user_name, String password)
	{
		
	}
	
	public void logOut(String user_name)
	{
		
	}
	
	public Community[] searchCommunity(String search_word)
	{
		//↓は信用しないでね リストの使い方は参考にしてね
		Community[] result_list = (Community[])(community_list.toArray());
		return result_list;
	}
	
	public void createCommunity(Community community)
	{
		
	}
	
	public void removeCommunity(Community community)
	{
		
	}
	
	public ClientEvent[] searchEvent(String search_word)
	{
		//↓は信用しないでね リストの使い方は参考にしてね
		ClientEvent[] result_list = (ClientEvent[])(event_list.toArray());
		return result_list;
	}
	
	public void manageEvent(ClientEvent event)
	{
		
	}
	
	/*
	 * 
	 * ↑この間のメソッドを書いてね↑
	 * 
	 */
	
	public void actionPerformed(ActionEvent ae)
	{
		String s = ae.getActionCommand();
		stdout(s);
		switch(s) {
		case "作成":
			String s0 = ui_tf_text0.getText();
			String s1 = new String(ui_tf_text2.getPassword());
			if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s0.equals("") || s1.equals("")) {
				ui_jl_alert.setText("ユーザ名とパスワードを入力してください");
			}
			else {
				createAccount(s0, s1);
				ui_jb_make.setEnabled(true);
				ui_jb_ban.setEnabled(true);
				ui_jb_deban.setEnabled(true);
				ui_jb_exit.setEnabled(true);
				ui_panel_01.setVisible(false);
			}
			break;
		case "追放":
			String s2 = ui_tf_text0.getText();
			if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s2.equals("")) {
				ui_jl_alert.setText("ユーザ名を入力してください");
			}
			else {
				banAccount(s2);
				ui_jb_make.setEnabled(true);
				ui_jb_ban.setEnabled(true);
				ui_jb_deban.setEnabled(true);
				ui_jb_exit.setEnabled(true);
				ui_panel_01.setVisible(false);
			}
			break;
		case "復活":
			String s3 = ui_tf_text0.getText();
			if(ui_tf_text0.getForeground() == Color.LIGHT_GRAY || s3.equals("")) {
				ui_jl_alert.setText("ユーザ名を入力してください");
			}
			else {
				debanAccount(s3);
				ui_jb_make.setEnabled(true);
				ui_jb_ban.setEnabled(true);
				ui_jb_deban.setEnabled(true);
				ui_jb_exit.setEnabled(true);
				ui_panel_01.setVisible(false);
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
			System.exit(0);
		default:
			newWindow(s);
			break;
		}
	}
	
	public void focusGained(FocusEvent fe) {
		
	}

	public void focusLost(FocusEvent fe) {
		
	}
	
	public static void main(String[] args)
	{
		new Server();
		/*
		 * 
		 * ↓この間に通信を始める処理を書いてね↓
		 * 
		 */
		
		
		
		/*
		 * 
		 * ↑この間に通信を始める処理を書いてね↑
		 * 
		 */
	}

}
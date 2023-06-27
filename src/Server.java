import java.awt.BorderLayout;
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
	private JTextField ui_tf_text0;
	private JTextField ui_tf_text1;
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
		ui_ta_log.setEditable(false);
		ui_panel_03.add(ui_ta_log);
		ui_panel_00.add(ui_panel_03, "Center");
		
		
		
		add(ui_panel_00);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setVisible(true);
	}
	
	public void newWindow(String text)
	{
		ui_jb_make.setEnabled(false);
		ui_jb_ban.setEnabled(false);
		ui_jb_deban.setEnabled(false);
		ui_jb_exit.setEnabled(false);
		ui_panel_01.removeAll();
		
		JPanel ui_panel_04 = new JPanel();
		ui_panel_04.setLayout(new BorderLayout(64, 64));
		ui_jl_command = new JLabel(text, JLabel.CENTER);
		ui_jl_command.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
		ui_panel_04.add(ui_jl_command, "North");
		JPanel ui_panel_05 = new JPanel();
		ui_panel_05.setLayout(new GridLayout(2, 1));
		ui_tf_text0 = new JTextField(24);
		ui_tf_text0.setText("ユーザ名");
		ui_tf_text0.setForeground(Color.LIGHT_GRAY);
		ui_tf_text0.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent fe) {
				String s0 = ui_tf_text0.getText();
				if (s0.equals("ユーザ名")) {
					ui_tf_text0.setText("");
					ui_tf_text0.setForeground(Color.BLACK);
				}
			}
			public void focusLost(FocusEvent fe) {
				String s0 = ui_tf_text0.getText();
				if (s0.equals("")) {
					ui_tf_text0.setText("ユーザ名");
					ui_tf_text0.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		ui_tf_text0.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
		ui_panel_05.add(ui_tf_text0, "A");
		if(text.equals("アカウント作成")) {
			ui_tf_text1 = new JTextField(24);
			ui_tf_text1.setText("パスワード");
			ui_tf_text1.setForeground(Color.LIGHT_GRAY);
			ui_tf_text1.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent fe) {
					String s1;
					if(!(ui_tf_text1 instanceof JPasswordField)) {
						s1 = ui_tf_text1.getText();
						if (s1.equals("パスワード")) {
							System.out.println("gained change");
							JTextField ui_tf_x = ui_tf_text0;
							ui_tf_text0 = ui_tf_x;
							ui_panel_05.removeAll();
							ui_panel_05.add(ui_tf_text0);
							ui_tf_text1 = new JPasswordField(24);
							ui_tf_text1.setText("");
							ui_tf_text1.setForeground(Color.BLACK);
							ui_tf_text1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
							ui_panel_05.add(ui_tf_text1);
							repaint();
						}
					}
				}
				public void focusLost(FocusEvent fe) {
					System.out.println(ui_tf_text1 instanceof JPasswordField);
					String s1 = new String(((JPasswordField)ui_tf_text1).getPassword());
					System.out.println(s1);
					if (s1.equals("")) {
						System.out.println("lost change");
						ui_tf_text1 = new JTextField("パスワド");
						ui_tf_text1.setForeground(Color.LIGHT_GRAY);
					}
				}
			});
			ui_tf_text1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
			ui_panel_05.add(ui_tf_text1, "B");
		}
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
		ui_ta_log.append("[" + calendar.getTime() + "] " + text + "\n");
	}
	
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
		Community[] result_list = new Community[10];
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
		ClientEvent[] result_list = new ClientEvent[10];
		return result_list;
	}
	
	public void manageEvent(ClientEvent event)
	{
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String s = ae.getActionCommand();
		switch(s) {
		case "作成":
			createAccount(ui_tf_text0.getText(), new String(((JPasswordField)ui_tf_text1).getPassword()));
			ui_jb_make.setEnabled(true);
			ui_jb_ban.setEnabled(true);
			ui_jb_deban.setEnabled(true);
			ui_jb_exit.setEnabled(true);
			ui_panel_01.setVisible(false);
		case "追放":
			banAccount(ui_tf_text0.getText());
			ui_jb_make.setEnabled(true);
			ui_jb_ban.setEnabled(true);
			ui_jb_deban.setEnabled(true);
			ui_jb_exit.setEnabled(true);
			ui_panel_01.setVisible(false);
		case "復活":
			debanAccount(ui_tf_text0.getText());
			ui_jb_make.setEnabled(true);
			ui_jb_ban.setEnabled(true);
			ui_jb_deban.setEnabled(true);
			ui_jb_exit.setEnabled(true);
			ui_panel_01.setVisible(false);
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
		String s0 = ui_tf_text0.getText();
		String s1;
		if(ui_tf_text1 instanceof JPasswordField) {
			s1 = new String(((JPasswordField)ui_tf_text1).getPassword());
		}
		else {
			s1 = ui_tf_text1.getText();
		}
		if (s0.equals("ユーザ名")) {
			ui_tf_text0.setText("");
			ui_tf_text0.setForeground(Color.BLACK);
		}
		if (s1.equals("パスワード")) {
			System.out.println("aaaaa");
			ui_tf_text1 = new JPasswordField();
			ui_tf_text1.setForeground(Color.BLACK);
		}
	}

	public void focusLost(FocusEvent fe) {
		String s0 = ui_tf_text0.getText();
		String s1 = new String(((JPasswordField)ui_tf_text1).getPassword());
		if (s0.equals("")) {
			ui_tf_text0.setText("ユーザ名");
			ui_tf_text0.setForeground(Color.LIGHT_GRAY);
		}
		if (s1.equals("")) {
			ui_tf_text1 = new JTextField("パスワード");
			ui_tf_text1.setForeground(Color.LIGHT_GRAY);
		}
	}
	
	public static void main(String[] args)
	{
		new Server();
	}

}
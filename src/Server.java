import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Server extends JFrame implements ActionListener{
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
	
	public Server()
	{
		super("Communi+I Server");
		setLayout(null);
		
		ui_panel_00 = new JPanel();
		ui_panel_00.setBounds(0, 0, 600 - 15, 400 - 35);
		ui_panel_00.setLayout(new BorderLayout());
		ui_panel_01 = new JPanel();
		ui_panel_01.setBounds(0, 0, 600 - 15, 400 - 35);
		ui_panel_01.setLayout(new BorderLayout());
		
		JPanel ui_panel_02 = new JPanel();
		ui_panel_02.setLayout(new GridLayout(4, 1, 0, 20));
		ui_jb_make = new JButton("アカウント作成");
		ui_jb_make.addActionListener(this);
		ui_panel_02.add(ui_jb_make, "A");
		ui_jb_ban = new JButton("アカウント追放");
		ui_jb_ban.addActionListener(this);
		ui_jb_ban.setPreferredSize(new Dimension(150, 50));
		ui_panel_02.add(ui_jb_ban, "B");
		ui_jb_deban = new JButton("アカウント復活");
		ui_jb_deban.addActionListener(this);
		ui_panel_02.add(ui_jb_deban, "C");
		ui_jb_exit = new JButton("サーバ終了");
		ui_jb_exit.addActionListener(this);
		ui_panel_02.add(ui_jb_exit, "D");
		ui_panel_00.add(ui_panel_02, "West");
		
		JPanel ui_panel_03 = new JPanel();
		//ui_panel_00.setSize(300, 400);
		ui_panel_03.setLayout(new BorderLayout());
		ui_ta_log = new TextArea("", 100, 100, TextArea.SCROLLBARS_VERTICAL_ONLY);
		ui_panel_03.add(ui_ta_log);
		ui_panel_00.add(ui_panel_03, "Center");
		
		
		
		add(ui_panel_00);
		add(ui_panel_01);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
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
		stdout(s);
	}
	
	public static void main(String[] args)
	{
		new Server();
	}

}

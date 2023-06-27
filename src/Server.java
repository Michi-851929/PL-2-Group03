import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Server extends JFrame implements ActionListener{
	private ArrayList<Account> account_list = new ArrayList<>();
	private ArrayList<Community> community_list = new ArrayList<>();
	private ArrayList<ClientEvent> event_list = new ArrayList<>();
	
	private JPanel ui_panel_0;
	private JPanel ui_panel_1;
	private JButton ui_jb_make;
	
	public Server()
	{
		super("Communi+I Server");
		setLayout(null);
		
		ui_panel_0 = new JPanel();
		ui_panel_0.setBounds(0, 0, 800, 600);
		ui_panel_0.setLayout(new BorderLayout());
		ui_panel_1 = new JPanel();
		ui_panel_1.setBounds(0, 0, WIDTH, HEIGHT);
		ui_panel_1.setLayout(new BorderLayout());
		
		ui_jb_make = new JButton("アカウント作成");
		ui_jb_make.addActionListener(this);
		ui_panel_0.add(ui_jb_make);
		
		
		
		add(ui_panel_0);
		add(ui_panel_1);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
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
		
	}
	
	public static void main(String[] args)
	{
		new Server();
	}

}

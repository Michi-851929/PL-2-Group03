public class Account {
	private String name;
	private String password;
	private Community[] community_involved = new Community[64];
	private ClientEvent[] event_going = new ClientEvent[256];
	private ClientEvent[] event_preferred = new ClientEvent[256];
	
	public final static int PASS_CORRECT = 1;
	public final static int PASS_FALSE = 0;
	
	public Account(String name, String password)
	{
		this.name = name;
		this.password = password;
	}
	
	public String getUserName()
	{
		return name;
	}
	
	public int verifyPassword(String password)
	{
		if(password.equals(this.password)) {
			return PASS_CORRECT;
		}
		else {
			return PASS_FALSE;
		}
	}
	
	public int setPassword(String old_password, String new_password)
	{
		int pass_result = verifyPassword(old_password);
		if(pass_result == PASS_CORRECT) {
			password = new_password;
		}
		return pass_result;
	}
	
	public void addCommunity()
	{
		
	}
	
	public void removeCommunity()
	{
		
	}
	
	public void addEventGoing()
	{
		
	}
	
	public void removeEventGoing()
	{
		
	}
	
	public void addEventPreferred()
	{
		
	}
	
	public void removeEventPreferred()
	{
		
	}
}

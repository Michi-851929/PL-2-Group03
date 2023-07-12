import java.time.LocalDate;

public class ClientDriver {

    public static void main(String[] args) {
        Client client = new Client();
        Account account = new Account("ADMIN", "meikyoushizen", "");
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        ClientEvent event1 = new ClientEvent("A会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        event1.setEventId("aa");
        community.getCalendarMonth(2023, 7).addEvent(event1.getEventId(), 12, 13);
        client.addTestData(account, community, event1);
        ClientEvent event2 = new ClientEvent("B会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        event2.setEventId("bb");
        community.getCalendarMonth(2023, 7).addEvent(event2.getEventId(), 13, 14);
        client.addTestData(account, community, event2);
        account.addEventPreferred("bb");
        try {
			client.getADayEvents(LocalDate.of(2023, 7, 13));
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

}

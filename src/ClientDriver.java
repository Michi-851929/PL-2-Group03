import java.util.ArrayList;

public class ClientDriver {

    public static void main(String[] args) {
        Client client = new Client();

        Account account = new Account("ADMIN", "meikyoushizen", "");

        ArrayList<Community> community_list = new ArrayList<>();
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        for(int i = 0; i < 7; i++) {
            community_list.add(community);
        }

        ArrayList<ClientEvent> event_list = new ArrayList<>();
        ClientEvent event1 = new ClientEvent("A会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        ClientEvent event2 = new ClientEvent("B会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        event1.setEventId("aa");
        community.getCalendarMonth(2023, 7).addEvent(event1.getEventId(), 12, 13);
        event_list.add(event1);
        event2.setEventId("bb");
        community.getCalendarMonth(2023, 7).addEvent(event2.getEventId(), 13, 14);
        account.addEventPreferred("bb");
        event_list.add(event2);

        client.addTestData(account, community_list, event_list);
        client.communityScreen();
    }

}
import java.util.ArrayList;

public class ClientDriver {

    public static void main(String[] args) {
        Client client = new Client();

        Account account = new Account("ADMIN", "meikyoushizen", "");

        ArrayList<Community> community_list = new ArrayList<>();
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        for(int i = 0; i < 3; i++) {
            community_list.add(community);
        }

        ArrayList<ClientEvent> event_list = new ArrayList<>();
        ClientEvent event1 = new ClientEvent("A会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになりますあああああああああああああああああああああああああああ", "PL-2-Group03");
        ClientEvent event2 = new ClientEvent("B会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        event1.setEventId("aa");
        community.getCalendarMonth(2023, 7).addEvent(event1.getEventId(), 12, 12);
        event_list.add(event1);
        event2.setEventId("bb");
        community.getCalendarMonth(2023, 7).addEvent(event2.getEventId(), 13, 13);
        account.addEventPreferred("bb");
        event_list.add(event2);
        event1.setOwnerMessage("開始10分前には班別に集合し、発表の準備をしていてください");
        event1.setOwnerMessage("発表は番号の若い班から順番に行います。一つの班の発表時間は質疑応答を含め25分です。");
        event1.setOwnerMessage("1週目に発表を行う班は2週目に比べ、評価のハードルが下がります。");
        event1.setOwnerMessage("今回の進捗報告書には、各班の発表の感想を記入し提出してください。");
        client.addTestData(account, community_list, event_list);
        event1.sendCancel("Alice", "体調不良ああああああああああああああああああああああああああああああああああああああああああ");
        event1.sendCancel("Bob", "新型コロナウイルスに感染したため");
        //client.eventScreen(event1, 10);
        client.calendarScreen();
    }

}
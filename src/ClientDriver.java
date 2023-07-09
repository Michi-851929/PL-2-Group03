
public class ClientDriver {

    public static void main(String[] args) {
        Client client = new Client();
        Account account = new Account("ADMIN", "meikyoushizen", "");
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        ClientEvent event = new ClientEvent("A会", "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        community.getCalendarMonth(2023, 7).addEvent(event.getEventId(), 12, 13);
        client.addTestData(account, community, event);
    }

}

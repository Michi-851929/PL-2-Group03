
public class ServerDriver {

    public static void main(String[] args) {
        Server server = new Server();
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        if(server.isCreatableCommunity(community.getName()) == Server.DUPLICATE_NOT) {
            server.createCommunity(community);
        }
        ClientEvent event = new ClientEvent("A会", "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
        server.createEvent(event, 2023, 1, 1, 3);
    }

}

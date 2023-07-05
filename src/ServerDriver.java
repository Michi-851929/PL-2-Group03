
public class ServerDriver {

    public static void main(String[] args) {
        Server server = new Server();
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("PL-2-Group03", "ADMIN", "プロジェクトラーニングのチーム", tags);
        if(server.isCreatableCommunity(community.getName()) == Server.DUPLICATE_NOT) {
            server.createCommunity(community);
        }
    }

}

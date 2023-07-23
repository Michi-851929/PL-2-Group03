
public class ServerDriver {

    public static void main(String[] args) {
        //サーバの起動
        Server server = new Server();

        //既存アカウントでのログイン正規
        System.out.println("///アカウントログインテスト///");
        System.out.println("アカウントログイン結果               :" + (server.logIn("ADMIN", "a") == Account.PASS_CORRECT));
        //既存アカウントでのログイン失敗
        System.out.println("アカウントログイン結果               :" + (server.logIn("ADMIN", "z") == Account.PASS_FALSE));
        //未知アカウントでのログイン
        System.out.println("アカウントログイン結果               :" + (server.logIn("admin", "a") == Server.LOGIN_NOTEXIST));
        //追放アカウントでのログイン
        System.out.println("アカウントログイン結果               :" + (server.logIn("ADMIN_BANNED", "a") == Server.LOGIN_BANNED));

        //パスワード変更
        System.out.println();
        System.out.println("///パスワード変更テスト///");
        server.changePassword("ADMIN", "a", "z");
        System.out.println("アカウントログイン結果               :" + (server.logIn("ADMIN", "z") == Account.PASS_CORRECT));

        //コミュニティ作成テスト
        System.out.println();
        System.out.println("///コミュニティ作成テスト///");
        System.out.println("未知コミュニティの作成可否           :" + (server.isCreatableCommunity("NewCommunity") == Server.DUPLICATE_NOT));
        System.out.println("既存コミュニティの作成可否           :" + (server.isCreatableCommunity("PL-2-Group03") == Server.DUPLICATE_NAME));
        //コミュニティオブジェクト
        String[] tags = {"横浜国立大学", "情報"};
        Community community = new Community("NewCommunity", "ADMIN", "プロジェクトラーニングのチーム", tags);
        if(server.isCreatableCommunity(community.getName()) == Server.DUPLICATE_NOT) {
            server.createCommunity(community);
            System.out.println("コミュニティの作成                   :" + (server.isCreatableCommunity("NewCommunity") == Server.DUPLICATE_NAME));
        }

        //コミュニティ検索テスト
        System.out.println();
        System.out.println("///コミュニティ検索テスト///");
        System.out.println("\"PL\"の検索結果");
        for(Community c : server.searchCommunity("PL")) {
            System.out.println("    ・" + c.getName());
        }
        System.out.println("\"PL New\"の検索結果");
        for(Community c : server.searchCommunity("PL New")) {
            System.out.println("    ・" + c.getName());
        }

        //コミュニティ削除テスト
        System.out.println();
        System.out.println("///コミュニティ削除テスト///");
        server.removeCommunity("NewCommunity");
        System.out.println("コミュニティ削除                     :" + server.getCommunity("NewCommunity").getName().equals(""));

        //コミュニティ加入脱退テスト
        System.out.println();
        System.out.println("///コミュニティ加入脱退テスト///");
        server.joinCommunity("PL-2-Group03", "ADMIN");
        System.out.println("コミュニティ加入                     :" + (server.getAccount("ADMIN").getCommunity()[1] == "PL-2-Group03"));
        server.quitCommunity("PL-2-Group03", "ADMIN");
        System.out.println("コミュニティ脱退                     :" + (server.getAccount("ADMIN").getCommunity().length == 1));

        //イベント作成テスト
        System.out.println();
        System.out.println("///イベント作成テスト///");
        String[] id = {"", ""};
        //イベントオブジェクト
        for(int i = 0; i < 3; i++) {
            ClientEvent event = new ClientEvent("A会", 2023, 07, "9:00", "19:00", "会館", "ADMIN", "学会です", "ためになります", "PL-2-Group03");
            if(i == 2) {
                System.out.println("イベントの作成失敗(ユーザ評価)       :" + !server.createEvent(event, 2023, 1, 1, 3));
            }
            else {
                System.out.println("イベントの作成                       :" + server.createEvent(event, 2023, 1, 1, 3));
                id[i] = event.getEventId();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }

        //イベント編集テスト
        System.out.println();
        System.out.println("///イベント編集テスト///");
        ClientEvent managed_event = server.getEvent(id[0]);
        managed_event.setEventName("B会");
        server.manageEvent(managed_event);
        System.out.println("イベントの編集                       :" + server.getEvent(id[0]).getEventName().equals("B会"));

        //いいね・参加のテスト
        System.out.println();
        System.out.println("///いいね・参加のテスト///");
        server.setPreferredEvent("ADMIN", id[0]);
        System.out.println("イベントのいいね                     :" + (server.getAccount("ADMIN").getEventPreferred().size() != 0));
        server.setDispreferEvent("ADMIN", id[0]);
        System.out.println("イベントのいいね取り消し             :" + (server.getAccount("ADMIN").getEventPreferred().size() == 0));
        server.setPresentEvent("ADMIN", id[0]);
        System.out.println("イベントの参加                       :" + (server.getAccount("ADMIN").getEventGoing().size() != 0));
        server.setAbsentEvent("ADMIN", id[0], "Absent");
        System.out.println("イベントの参加取り消し               :" + (server.getAccount("ADMIN").getEventGoing().size() == 0));
        System.out.println("イベントの参加取り消しメッセージ     :" + (server.getEvent(id[0]).getCancelMessage()[0].equals("Absent")));
        server.addHostMessage(id[0], "Owner");
        System.out.println("イベント主催者からのメッセージ       :" + (server.getEvent(id[0]).getOwnerMessage()[0].equals("Owner")));

        //イベント削除テスト
        System.out.println();
        System.out.println("///イベント削除テスト///");
        server.deleteEvent(id[1], 2023, 1);
        System.out.println("イベントの削除                       :" + (server.getCommunity("PL-2-Group03").getCalendarMonth(2023, 1).getEventList().get(0).size() == 1));
        for(int i = 0; i < 11; i++) {
            server.reportEvent(id[0], 2023, 1);
        }
        System.out.println("イベントの削除(イベント通報)         :" + (server.getCommunity("PL-2-Group03").getCalendarMonth(2023, 1).getEventList().get(0).size() == 0));



    }

}

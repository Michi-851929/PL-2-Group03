import java.util.Arrays;

public class AccountDriver {

    public static void main(String[] args) {
        Account account = new Account("名前", "パスワード", "MACアドレス");
        System.out.println("テスト用のAccountを作成しました");
        System.out.println("getUserName出力：" + account.getUserName());
        System.out.println("getMacAdress出力：" + account.getMacAdress());
        System.out.println();
        
        System.out.println("isbannedでアカウントのBAN状態を確認します(trueならBANされている、falseならBANされていない)");
        System.out.println("isbanned出力：" + account.isBanned());
        System.out.println("banAccountでアカウントをBAN状態にします");
        account.banAccount();
        System.out.println("isbanned出力：" + account.isBanned());
        System.out.println("permitAccountでBAN状態のアカウントを復活します");
        account.permitAccount();
        System.out.println("isbanned出力：" + account.isBanned());
        System.out.println();
        
        System.out.println("setLastCheckTimeで現在時刻を設定します");
        account.setLastCheckTime();
        System.out.println("getLastCheckInt出力：" + Arrays.toString(account.getLastCheckInt()));
        System.out.println("getLastCheckTime出力：" + account.getLastCheckTime());
        System.out.println();
        
        System.out.println("setPasswordで「パスワード」と「新パスワード」を入力します");
        account.setPassword("パスワード", "新パスワード");
        System.out.println("verifyPasswordでパスワードが変更されているか確認します（１なら正しい、０なら正しくない）");
        System.out.println("verifyPassword出力(引数「パスワード」)：" + account.verifyPassword("パスワード"));
        System.out.println("verifyPassword出力(引数「新パスワード」)：" + account.verifyPassword("新パスワード"));
        System.out.println();
        
        System.out.println("addCommunityで「コミュニティ名」を入力します");
        account.addCommunity("コミュニティ名");
        System.out.println("getCommunity出力：" + Arrays.toString(account.getCommunity()));
        System.out.println("removeCommunityで「コミュニティ名」を入力します");
        account.removeCommunity("コミュニティ名");
        System.out.println("getCommunity出力：" + Arrays.toString(account.getCommunity()));
        System.out.println();
        
        System.out.println("addEventGoingで「イベント名」を入力します");
        account.addEventGoing("イベント名");
        System.out.println("getEventGoing出力：" + account.getEventGoing());
        System.out.println("getAEventGoingでtrueが返ってくることを確認します");
        System.out.println("getAEventGoing出力(引数「イベント名」)：" + account.getAEventGoing("イベント名"));
        System.out.println("removeEventGoingで「イベント名」を入力します");
        account.removeEventGoing("イベント名");
        System.out.println("getEventGoing出力：" + account.getEventGoing());
        System.out.println("getAEventGoingでfalseが返ってくることを確認します");
        System.out.println("getAEventGoing出力(引数「イベント名」)：" + account.getAEventGoing("イベント名"));
        System.out.println();
        
        System.out.println("addEventPreferredで「イベント名」を入力します");
        account.addEventPreferred("イベント名");
        System.out.println("getEventPreferred出力：" + account.getEventPreferred());
        System.out.println("getAEventPreferrdでtrueが返ってくることを確認します");
        System.out.println("getAEventPreferrd出力(引数「イベント名」)：" + account.getAEventPreferrd("イベント名"));
        System.out.println("removeEventPreferredで「イベント名」を入力します");
        account.removeEventPreferred("イベント名");
        System.out.println("getEventPreferred出力：" + account.getEventPreferred());
        System.out.println("getAEventPreferrdでfalseが返ってくることを確認します");
        System.out.println("getAEventPreferrd出力(引数「イベント名」)：" + account.getAEventPreferrd("イベント名"));
        System.out.println();
        
        System.out.println("addEventMadeで「イベント名」を入力します");
        account.addEventMade("イベント名");
        System.out.println("getEventMade出力：" + account.getEventMade());
        System.out.println("getTotalEventMadeで1が返ってくることを確認します");
        System.out.println("getTotalEventMade出力：" + account.getTotalEventMade());
        System.out.println();
        
        System.out.println("getTodayで日付（時分秒切り捨て）を取得します");
        System.out.println("getToday出力：" + account.getToday().getTime());
        System.out.println();
        
    }

}



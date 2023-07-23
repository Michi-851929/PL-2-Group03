import java.time.LocalDateTime;
import java.util.Arrays;

public class ClientEventDriver {

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        ClientEvent event = new ClientEvent("", 2023, 7, "", "", "", "オーナー名", "", "", "コミュニティ名");
        System.out.println("テスト用のClientEventを作成しました");
        System.out.println("getYear出力：" + event.getYear());
        System.out.println("getMonthValue出力：" + event.getMonthValue());
        System.out.println("getEventOwner出力：" + event.getEventOwner());
        System.out.println("getEventCommunityName出力：" + event.getEventCommunityName());
        System.out.println();
        
        System.out.println("setEventIDで「イベントID」を入力します");
        event.setEventId("イベントID");
        System.out.println("getEventID出力：" + event.getEventId());
        System.out.println();
        
        System.out.println("setEventNameで「イベント名」を入力します");
        event.setEventName("イベント名");
        System.out.println("getEventName出力：" + event.getEventName());
        System.out.println();
        
        System.out.println("setEventStartで「開始時刻」を入力します");
        event.setEventStart("開始時刻");
        System.out.println("getEventStart出力：" + event.getEventStart());
        System.out.println("setEventFinishで「終了時刻」を入力します");
        System.out.println();
        
        event.setEventFinish("終了時刻");
        System.out.println("getEventFinish出力：" + event.getEventFinish());
        System.out.println();
        
        System.out.println("setEventPlaceで「開催場所」を入力します");
        event.setEventPlace("開催場所");
        System.out.println("getEventPlace出力：" + event.getEventPlace());
        System.out.println();
        
        System.out.println("setEventOutlineで「イベント概要」を入力します");
        event.setEventOutline("イベント概要");
        System.out.println("getEventOutline出力：" + event.getEventOutline());
        System.out.println();
        
        System.out.println("setEventDetailで「イベント詳細」を入力します");
        event.setEventDetail("イベント詳細");
        System.out.println("getEventDetail出力：" + event.getEventDetail());
        System.out.println();
        
        System.out.println("setOwnerMessageで「主催者メッセージ1」を入力します");
        event.setOwnerMessage("主催者メッセージ1");
        System.out.println("getOwnerMessage出力：" + Arrays.toString(event.getOwnerMessage()));
        System.out.println("getMessageDateData出力：" + event.getMessageDateData(0));
        System.out.println();
        
        System.out.println("sendCancelで「キャンセル者」と「キャンセル理由」を入力します");
        event.sendCancel("キャンセル者", "キャンセル理由");
        System.out.println("getCancalName出力：" + Arrays.toString(event.getCancelName()));
        System.out.println("getCancelMessage出力：" + Arrays.toString(event.getCancelMessage()));
        System.out.println("getCancelTime出力：" + Arrays.toString(event.getCancelTime()));
        System.out.println("getCancelDateData出力：" + event.getCancelDateData(0));
        System.out.println();
        
        System.out.println("getGoodでいいね数の増減を確認します");
        System.out.println("getGood出力：" + event.getGood());
        System.out.println("increaseGoodでいいね数を1増やします" + event.increaseGood());
        event.increaseGood();
        System.out.println("getGood出力：" + event.getGood());
        System.out.println("decreaseGoodでいいね数を１減らします");
        event.decreaseGood();
        System.out.println("getGood出力：" + event.getGood());
        System.out.println();
        
        System.out.println("getJoinで参加者数の増減を確認します");
        System.out.println("getJoin出力：" + event.getJoin());
        System.out.println("increaseJoinで参加者数を１増やします");
        event.increaseJoin();
        System.out.println("getJoin出力：" + event.getJoin());
        System.out.println("decreaseJoinで参加者数を１減らします");
        event.decreaseJoin();
        System.out.println("getJoin出力：" + event.getJoin());
        System.out.println();
        
        System.out.println("increaseReportで通報数を１増やします");
        System.out.println("increaseReport出力：" + event.increaseReport());
        System.out.println();
        
        System.out.println("setOwnerMessageで「主催者メッセージ2」を入力します");
        event.setOwnerMessage("主催者メッセージ2");
        System.out.println("getNewOwnerMessageで最新の主催者メッセージを取得します");
        System.out.println("getNewOwnerMessage出力：" + event.getNewOwnerMessage(time).message2);
        System.out.println();
        
        System.out.println("getLastUpdateTimeで最終更新時間を取得します");
        System.out.println("getLastUpdateTime出力：" + event.getLastUpdateTime());
        System.out.println();
        
    }

}

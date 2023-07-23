import java.util.ArrayList;
import java.util.List;

public class CalendarMonthDriver {

    public static void main(String[] args) {
        CalendarMonth cm = new CalendarMonth(2023, 7);
        System.out.println("テスト用のCalendarMonth(2023/07)を作成しました");
        System.out.println("getYear出力: "+cm.getYear());
        System.out.println("getMonth出力: "+cm.getMonth());
        System.out.println();
        System.out.println("addEventで7/3から7/5まで開催されるイベント「テスト」を追加します");
        cm.addEvent("テスト", 3, 5);
        System.out.println();

        System.out.println("getEventListで1か月分のイベントが格納された二次元ArrayListを取得し、7/2のイベント数が0, 7/3のイベント数が1であることを確認します");
        List<ArrayList<String>> list = cm.getEventList();
        System.out.println("2日のイベント数: "+list.get(1).size());
        System.out.println("3日のイベント数: "+list.get(2).size());
        System.out.println();

        System.out.println("getDayEventで7/3のイベントが格納された一次元ArrayListを取得し、7/3のイベント数が1であることを確認します");
        System.out.println("3日のイベント数: "+cm.getDayEvent(3).size());
        System.out.println();

        System.out.println("removeEventでイベント「テスト」を削除します");
        cm.removeEvent("テスト");
        System.out.println("3日のイベント数: "+cm.getDayEvent(3).size());

    }

}


public class CommunityDriver {

    public static void main(String[] args) {
        String[] tags = new String[5];
        tags[0] = "test";
        tags[1] = "テスト";
        tags[2] = "tag";
        tags[3] = "タグ";
        tags[4] = "a";
        Community comd = new Community("テストコミュニティ", "ADMIN", "テスト用のコミュニティです", tags);
        
        System.out.println("テスト用のコミュニティを作成しました");
        System.out.println("コミュニティ名: "+comd.getName());
        System.out.println("オーナー名: "+comd.getOwner());
        System.out.println("概要: "+comd.getInfo());
        System.out.println("人数: "+comd.getPopulation());
        System.out.println("タグ: "+comd.getTag()[0]+", "+comd.getTag()[1]+", "+comd.getTag()[2]+", "+comd.getTag()[3]+", "+comd.getTag()[4]);

        System.out.println("setNameでコミュニティ名を「TEST」に変更します");
        comd.setName("TEST");
        System.out.println("getName出力: "+comd.getName());
        System.out.println("setNameでオーナー名を「NEW_OWNER」に変更します");
        comd.setOwner("NEW_OWNER");
        System.out.println("getOwner出力: "+comd.getOwner());
        System.out.println("setInfoで概要を「新しい概要です」に変更します");
        comd.setInfo("新しい概要です");
        System.out.println("getInfo出力: "+comd.getInfo());
        System.out.println("addTagでタグ「no.6」を追加し、タグが追加できないことを意味する値が帰ってくることを確認します");
        if(comd.addTag("no.6")==2) {
            System.out.println("タグに空きがありません");
        }
        else {
            System.out.println("失敗");
        }
        System.out.println("addTagでタグ「test」を追加し、タグが重複していることを意味する値が帰ってくることを確認します");
        if(comd.addTag("test")==1) {
            System.out.println("タグ名が重複しています");
        }
        else {
            System.out.println("失敗");
        }
        System.out.println("remTagで存在しないタグ「no.6」の削除を試み、指定した名前のタグが存在していないことを意味する値が帰ってくることを確認します");
        if(comd.remTag("no.6")==-1) {
            System.out.println("指定したタグは登録されていません");
        }
        else {
            System.out.println("失敗");
        }
        System.out.println("remTagでタグ「test」の削除を試み、正常に削除できることを確認します");
        if(comd.remTag("test")==0){
            System.out.println("タグを正常に削除できました");
        }
        else {
            System.out.println("失敗");
        }
        System.out.println("addTagでタグ「tsuika」の追加を試み、正常に追加できることを確認します");
        if(comd.addTag("tsuika")==0) {
            System.out.println("タグを正常に追加できました");
        }
        else {
            System.out.println("失敗");
        }
        System.out.println("getTagで変更後のタグリストが正しく取得できることを確認します");
        System.out.println("getTag[]出力: "+comd.getTag()[0]+", "+comd.getTag()[1]+", "+comd.getTag()[2]+", "+comd.getTag()[3]+", "+comd.getTag()[4]);
        
        System.out.println("addMemberで「user」を追加し、所属人数が1人に増えることを確認します");
        comd.addMember("user");
        System.out.println("getPopulation出力: "+comd.getPopulation());
        
        System.out.println("remMemberで「user」を削除し、所属人数が0人に減ることを確認します");
        comd.remMember("user");
        System.out.println("getPopulation出力: "+comd.getPopulation());
        System.out.println();


    }

}

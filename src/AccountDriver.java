import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountDriver {

    public static void main(String[] args) {
        Account account = new Account("a", "b", "c");
        System.out.println(account.getCommunity());
        System.out.println(account.getCommunity().length);
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String password = "meikyoushizen";
        password = String.format("%40x", new BigInteger(1, sha256.digest((account.SALT_FRONT + password + account.SALT_BACK).getBytes())));
        System.out.println(password);
    }
}

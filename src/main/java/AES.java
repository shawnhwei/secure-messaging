import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES {
    private byte[] DHkey = null;
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    private SecretKey key = null;

    public AES(byte[] DHkey) {
        try {
            this.DHkey = DHkey;
            this.encryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
            this.decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = new SecretKeySpec(Arrays.copyOf(sha.digest(), 16), "AES");

            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] plaintext){
        try {
            return encryptCipher.doFinal(plaintext);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] ciphertext){
        try {
            return decryptCipher.doFinal(ciphertext);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

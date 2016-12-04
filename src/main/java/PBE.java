import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class PBE {
    private static final int saltCount = 1000;

    public static void main(String [] args) {
        Test ciphertext = passwordEncrypt("testing", ("hello world").getBytes());
        String plaintext = passwordDecrypt("testing", ciphertext);

        System.out.println(plaintext);
    }

    private static class Test{
        byte[] ciphertext;
        byte[] parameters;

        public Test(byte[] ciphertext, byte[] parameters) {
            this.ciphertext = ciphertext;
            this.parameters = parameters;
        }
    }

    private static Test passwordEncrypt(String password, byte[] plaintext) {
        try {
            byte[] salt = new byte[8];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
            SecretKey key = keyFactory.generateSecret(keySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, saltCount);
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

            byte[] ciphertext = cipher.doFinal(plaintext);

            ByteArrayOutputStream saltCipher = new ByteArrayOutputStream();
            saltCipher.write(salt);
            saltCipher.write(ciphertext);

            return new Test(saltCipher.toByteArray(), cipher.getParameters().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String passwordDecrypt(String password, Test ciphertext) {
        try {
            byte[] salt = Arrays.copyOfRange(ciphertext.ciphertext, 0, 8);
            byte[] text = Arrays.copyOfRange(ciphertext.ciphertext, 8, ciphertext.ciphertext.length);

            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
            SecretKey key = keyFactory.generateSecret(keySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, saltCount);
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PBEWithHmacSHA256AndAES_128");
            algorithmParameters.init(ciphertext.parameters);
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            byte[] plaintext = cipher.doFinal(text);

            return new String(plaintext);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

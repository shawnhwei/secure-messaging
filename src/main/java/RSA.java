import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    public static void main(String [] args) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.initialize(512, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        String message = "testing123";
        byte[] signature = signMessage(keyPair.getPrivate().getEncoded(), message);

        System.out.println(verifyMessage(keyPair.getPublic().getEncoded(), message, signature));
    }

    public static byte[] signMessage(byte[] key, String message) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey, new SecureRandom());
            signature.update(message.getBytes());
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Boolean verifyMessage(byte[] key, String message, byte[] testSig) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));

            Signature genSig = Signature.getInstance("SHA256withRSA");
            genSig.initVerify(publicKey);
            genSig.update(message.getBytes());
            return genSig.verify(testSig);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }
}

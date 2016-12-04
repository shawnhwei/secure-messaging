import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;

public class DHE {
    public final static int pValue = 47;

    public final static int gValue = 71;

    public final static int XaValue = 9;

    public final static int XbValue = 14;

    public static void main(String[] args) throws Exception {
//        BigInteger p = new BigInteger(Integer.toString(pValue));
//        BigInteger g = new BigInteger(Integer.toString(gValue));
//        BigInteger Xa = new BigInteger(Integer.toString(XaValue));
//        BigInteger Xb = new BigInteger(Integer.toString(XbValue));
//
//        createKey();
//
//        int bitLength = 512; // 512 bits
//        SecureRandom rnd = new SecureRandom();
//        p = BigInteger.probablePrime(bitLength, rnd);
//        g = BigInteger.probablePrime(bitLength, rnd);
//
//        createSpecificKey(p, g);

        DHParameterSpec dhParameterSpec = generateParameters();

    }

    public static DHParameterSpec generateParameters() throws Exception {
        AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
        paramGen.init(1024);

        AlgorithmParameters params = paramGen.generateParameters();
        DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
        return dhSpec;
    }

    private static class Secret{
        public BigInteger ab;
        public BigInteger AB;

        public Secret(BigInteger ab, BigInteger AB) {
            this.ab = ab;
            this.AB = AB;
        }
    }

    public static Secret createSecret(BigInteger p, BigInteger g) throws Exception {
        SecureRandom rnd = new SecureRandom();
        BigInteger ab = new BigInteger(1024, rnd);
        BigInteger AB = g.modPow(ab, p);
        return new Secret(ab, AB);
    }

    public static byte[] createKey(BigInteger AB, BigInteger ab, BigInteger p){
        BigInteger key = AB.modPow(ab, p);
        return key.toByteArray();
    }
}

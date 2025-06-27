import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

// Helper class to generate public and private RSA256 keys for JWT issuing and validation
// This is meant to run only once, before anything else, and manual file movement is necessary.
public class KeyGeneratorUtil {

    public static void main(String[] args) throws Exception {
        // Generate RSA public-private Key Pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Key size
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String destinationDir = "./keypair-generation/keys";
        // Create /keys directory if not exists
        File keysDir = new File(destinationDir);
        if (!keysDir.exists()) {
            keysDir.mkdirs();
        }

        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] publicKeyBytes = publicKey.getEncoded();

        writeKeyAsProperty(privateKeyBytes, "jwt.privatekey", destinationDir+"/private-key.txt");
        writeKeyAsProperty(publicKeyBytes, "jwt.publickey", destinationDir+"/public-key.txt");

        System.out.println("Keys generated and saved in /keys folder.");
    }

    private static void writeKeyAsProperty(byte[] keyBytes, String propertyName, String filename) throws IOException {
        // Base64 encode without line breaks (standard encoder)
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        // Build the line: {propertyName}=base64Key
        String line = propertyName + "=" + encodedKey + "\n";

        // try with resources
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(line);
        }
    }
}
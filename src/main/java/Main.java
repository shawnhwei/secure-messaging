import org.apache.commons.io.FileUtils;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            FileUtils.copyURLToFile(new URL("http://localhost:8888/generate_keys"), new File("./combined_keys"));
            byte[] data = Files.readAllBytes(Paths.get("./combined_keys"));
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
            unpacker.unpackValue()
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

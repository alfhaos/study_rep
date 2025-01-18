package was.v2;

import java.io.IOException;

public class ServerMain2 {

    private static final int PORT =12345;

    public static void main(String[] args) throws IOException {
        HttpServerV2 server = new HttpServerV2(PORT);
        server.start();
    }
}

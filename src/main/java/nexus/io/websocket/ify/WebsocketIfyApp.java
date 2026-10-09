package nexus.io.websocket.ify;

import nexus.io.tio.boot.TioApplication;
import nexus.io.websocket.ify.config.WebHelloConfig;

public class WebsocketIfyApp {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    TioApplication.run(WebsocketIfyApp.class, new WebHelloConfig(), args);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "ms");
  }
}

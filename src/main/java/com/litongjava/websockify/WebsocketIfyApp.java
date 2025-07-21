package com.litongjava.websockify;

import com.litongjava.tio.boot.TioApplication;
import com.litongjava.websockify.config.WebHelloConfig;

public class WebsocketIfyApp {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    TioApplication.run(WebsocketIfyApp.class, new WebHelloConfig(), args);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "ms");
  }
}

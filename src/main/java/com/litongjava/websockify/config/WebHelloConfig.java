package com.litongjava.websockify.config;

import com.litongjava.context.BootConfiguration;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.boot.websocket.WebSocketRouter;
import com.litongjava.tio.http.server.router.HttpRequestRouter;
import com.litongjava.websockify.handler.IndexHandler;
import com.litongjava.websockify.handler.WebsockifyHandler;

public class WebHelloConfig implements BootConfiguration {

  public void config() {

    TioBootServer server = TioBootServer.me();
    HttpRequestRouter requestRouter = server.getRequestRouter();

    IndexHandler helloHandler = new IndexHandler();
    requestRouter.add("/", helloHandler::index);
    
    WebSocketRouter router = TioBootServer.me().getWebSocketRouter();
    router.add("/websockify", new WebsockifyHandler());
  }
}

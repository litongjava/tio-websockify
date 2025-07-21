package com.litongjava.websockify.handler;

import com.litongjava.aio.BytePacket;
import com.litongjava.tio.client.ClientChannelContext;
import com.litongjava.tio.client.ClientTioConfig;
import com.litongjava.tio.client.ReconnConf;
import com.litongjava.tio.client.TioClient;
import com.litongjava.tio.client.intf.ClientAioHandler;
import com.litongjava.tio.core.ChannelContext;
import com.litongjava.tio.core.Node;
import com.litongjava.tio.core.Tio;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.websocket.common.WebSocketRequest;
import com.litongjava.tio.websocket.common.WebSocketResponse;
import com.litongjava.tio.websocket.common.WebSocketSessionContext;
import com.litongjava.tio.websocket.server.handler.IWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebsockifyHandler implements IWebSocketHandler {
  public static final String CHARSET = "utf-8";

  // 在 ChannelContext 上挂一个 key，用来保存对应的 TCP 客户端
  private static final String ATTR_TCP_CLIENT = "tcpClientCtx";

  @Override
  public void onAfterHandshaked(HttpRequest req, HttpResponse resp, ChannelContext wsCtx) throws Exception {
    // 从配置或 URL 参数里拿到要连接的 VNC 服务地址和端口
    String vncServer = EnvUtils.getStr("vnc-server");
    log.info("vnc-server:{}", vncServer);
    if (vncServer == null) {
      log.error("vnc-server can not be empty");
      return;
    }
    String[] split = vncServer.split(":");
    String host = split[0];
    int port = Integer.valueOf(split[1]);

    // 1. 构造并连接到 VNC Server 的 TIO 客户端
    Node node = new Node(host, port);
    ClientAioHandler tcpHandler = new TcpRelayClientHandler(wsCtx);
    ClientTioConfig cfg = new ClientTioConfig(tcpHandler, null, new ReconnConf(0L));
    cfg.setName("vnc-relay");
    TioClient tcpClient = new TioClient(cfg);
    ClientChannelContext tcpCtx = tcpClient.connect(node);

    // 2. 保存到 WS 会话上下文，以便 onBytes/onClose 时能取到
    wsCtx.setAttribute(ATTR_TCP_CLIENT, tcpCtx);

    log.info("已为 WS [{}] 建立到 {}:{} 的 TCP 连接 {}", wsCtx.getId(), host, port, tcpCtx);
  }

  @Override
  public Object onBytes(WebSocketRequest wsReq, byte[] data, ChannelContext wsCtx) throws Exception {
    // 收到 WebSocket 的二进制数据，转发给 TCP 
    ClientChannelContext tcpCtx = (ClientChannelContext) wsCtx.getAttribute(ATTR_TCP_CLIENT);
    if (tcpCtx != null && !tcpCtx.isClosed) {
      // T-io 里我们把字节包裹成 Packet 发出去
      Tio.send(tcpCtx, new BytePacket(data));
    }
    return null;
  }

  @Override
  public Object onClose(WebSocketRequest wsReq, byte[] bytes, ChannelContext wsCtx) throws Exception {
    // WS 关闭时，也断开到 TCP 的连接
    ClientChannelContext tcpCtx = (ClientChannelContext) wsCtx.getAttribute(ATTR_TCP_CLIENT);
    if (tcpCtx != null) {
      Tio.close(tcpCtx, "WS closed");
    }

    return null;
  }

  @Override
  public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
    return httpResponse;
  }

  @Override
  public Object onText(WebSocketRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
    WebSocketSessionContext wsSessionContext = (WebSocketSessionContext) channelContext.get();
    String path = wsSessionContext.getHandshakeRequest().getRequestLine().path;
    log.info("路径：{}，收到消息：{}", path, text);

    String message = "{user_id:'" + channelContext.userId + "',message:'" + text + "'}";
    WebSocketResponse wsResponse = WebSocketResponse.fromText(message, CHARSET);
    // 发送消息
    Tio.send(channelContext, wsResponse);
    return null; // 不需要额外的返回值
  }

}

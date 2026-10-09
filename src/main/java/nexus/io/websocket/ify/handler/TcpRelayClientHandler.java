package nexus.io.websocket.ify.handler;

import java.nio.ByteBuffer;

import nexus.io.aio.BytePacket;
import nexus.io.aio.Packet;
import nexus.io.tio.client.intf.ClientAioHandler;
import nexus.io.tio.core.ChannelContext;
import nexus.io.tio.core.Tio;
import nexus.io.tio.core.TioConfig;
import nexus.io.tio.websocket.common.WebSocketResponse;

public class TcpRelayClientHandler implements ClientAioHandler {

  private final ChannelContext wsCtx;

  public TcpRelayClientHandler(ChannelContext wsCtx) {
    this.wsCtx = wsCtx;
  }

  @Override
  public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext ctx) {
    byte[] bs = new byte[readableLength];
    buffer.get(bs);
    return new BytePacket(bs);
  }

  @Override
  public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext ctx) {
    BytePacket pkt = (BytePacket) packet;
    byte[] body = pkt.getBytes();
    ByteBuffer buf = ByteBuffer.allocate(body.length);
    buf.order(tioConfig.getByteOrder());
    buf.put(body);
    return buf;
  }

  @Override
  public void handler(Packet packet, ChannelContext tcpCtx) throws Exception {
    // 从 VNC Server 读到 bytes，转发为 WebSocket 二进制帧
    byte[] bs = ((BytePacket) packet).getBytes();
    WebSocketResponse wsResp = WebSocketResponse.fromBytes(bs);
    // 注意：不要走 Tio.send(tcpCtx)，而要把 WS context 传给它
    Tio.send(wsCtx, wsResp);
  }

  @Override
  public Packet heartbeatPacket(ChannelContext channelContext) {
    return null;
  }
}

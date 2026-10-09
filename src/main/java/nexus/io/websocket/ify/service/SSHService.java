package nexus.io.websocket.ify.service;

import nexus.io.tio.utils.base64.Base64Utils;
import nexus.io.tio.utils.json.JsonUtils;
import nexus.io.websocket.ify.vo.SSHTarget;

public class SSHService {

  public SSHTarget decodeBase64(String msg) {
    String decodeToString = Base64Utils.decodeToString(msg);
    return JsonUtils.parse(decodeToString, SSHTarget.class);
  }
}

package nexus.io.websocket.ify.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SSHTarget {
  private String host;
  private Integer port;
  private String username;
  private String password;
}

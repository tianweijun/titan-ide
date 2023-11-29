package titan.ide.config;

import java.io.InputStream;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class RootIdeConfigBuilder {

  public RootIdeCfg build() {
    InputStream jsonIdeConfigInputStream =
        this.getClass().getClassLoader().getResourceAsStream("config/rootIdeCfg.json");
    RootIdeCfg rootIdeConfig = Json.fromJson(jsonIdeConfigInputStream, RootIdeCfg.class);
    return rootIdeConfig;
  }
}

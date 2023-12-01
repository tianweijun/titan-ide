package titan.ide.config;

import java.io.InputStream;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class RootIdeConfigBuilder {

  public RootIdeConfig build() {
    InputStream jsonIdeConfigInputStream =
        this.getClass().getClassLoader().getResourceAsStream("config/rootIdeConfig.json");
    RootIdeConfig rootIdeConfig = Json.fromJson(jsonIdeConfigInputStream, RootIdeConfig.class);
    return rootIdeConfig;
  }
}

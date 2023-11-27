package titan.ide.test;

import titan.ide.IdeApplication;
import titan.ide.logger.Logger;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeTest {
  public static void main(String[] args) {
    new IdeApplication().run();
    Logger.debug("IdeTest", "IdeTest end");
  }
}

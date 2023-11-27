package titan.ide.exception;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeRuntimeException extends RuntimeException {

  public IdeRuntimeException(String message) {
    super(message);
  }

  public IdeRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public IdeRuntimeException(Throwable cause) {
    super(cause);
  }
}

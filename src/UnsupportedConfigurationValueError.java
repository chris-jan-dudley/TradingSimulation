
/**
 *
 * @author James
 */
public class UnsupportedConfigurationValueError extends RuntimeException {

    String err;

    /**
     *
     */
    public UnsupportedConfigurationValueError() {
    }

    /**
     *
     * @param s
     */
    public UnsupportedConfigurationValueError(String s) {
        this.err = s;
    }

}

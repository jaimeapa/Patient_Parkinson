package BITalino;

/**
 * Represents an exception specific to BITalino device operations.
 * This exception is thrown when an error occurs while interacting with the BITalino device.
 */
public class BITalinoException extends Exception {

	/**
	 * Serial version UID for ensuring compatibility during serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The error code associated with the BITalino exception.
	 */
	public int code;

	/**
	 * Constructs a new BITalinoException with the specified error type.
	 *
	 * @param errorType The {@link BITalinoErrorTypes} instance representing the error.
	 */
	public BITalinoException(BITalinoErrorTypes errorType) {
		super(errorType.getName());
		this.code = errorType.getValue();
	}
}


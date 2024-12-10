package BITalino;

/**
 * Enumeration representing various error types that can occur while interacting with the BITalino device.
 */
public enum BITalinoErrorTypes {

	/**
	 * Bluetooth Device not connected.
	 */
	BT_DEVICE_NOT_CONNECTED(0, "Bluetooth Device not connected"),

	/**
	 * The communication port could not be initialized. The provided parameters could not be set.
	 */
	PORT_COULD_NOT_BE_OPENED(1, "The communication port could not be initialized. The provided parameters could not be set."),

	/**
	 * Device is not in idle mode.
	 */
	DEVICE_NOT_IDLE(2, "Device not in idle mode"),

	/**
	 * Device is not in acquisition mode.
	 */
	DEVICE_NOT_IN_ACQUISITION_MODE(3, "Device not is acquisition mode"),

	/**
	 * The sampling rate chosen cannot be set in BITalino. Allowed values are 1000, 100, 10, or 1.
	 */
	SAMPLING_RATE_NOT_DEFINED(4, "The Sampling Rate chose cannot be set in BITalino. Choose 1000, 100, 10, or 1"),

	/**
	 * The computer lost communication with the device.
	 */
	LOST_COMMUNICATION(5, "The Computer lost communication"),

	/**
	 * An invalid parameter was provided.
	 */
	INVALID_PARAMETER(6, "INVALID_PARAMETER"),

	/**
	 * The threshold value must be between 0 and 63.
	 */
	THRESHOLD_NOT_VALID(7, "The threshold value must be between 0 and 63"),

	/**
	 * The number of analog channels available is between 0 and 5.
	 */
	ANALOG_CHANNELS_NOT_VALID(8, "The number of analog channels available are between 0 and 5"),

	/**
	 * Incorrect data provided for decoding.
	 */
	INCORRECT_DECODE(9, "Incorrect data to be decoded"),

	/**
	 * To set digital outputs, the input array must have 4 items, one for each channel.
	 */
	DIGITAL_CHANNELS_NOT_VALID(10, "To set the digital outputs, the input array must have 4 items, one for each channel."),

	/**
	 * The MAC address provided is not valid.
	 */
	MACADDRESS_NOT_VALID(11, "MAC address not valid."),

	/**
	 * Undefined error.
	 */
	UNDEFINED(12, "UNDEFINED ERROR");

	/**
	 * The integer value associated with the error type.
	 */
	private final int value;

	/**
	 * The descriptive name or message associated with the error type.
	 */
	private final String name;


	/**
	 * Constructor for the BITalinoErrorTypes enumeration.
	 *
	 * @param value The integer value associated with the error type.
	 * @param name A description of the error type.
	 */
	BITalinoErrorTypes(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Returns the integer value associated with the error type.
	 *
	 * @return The integer value of the error type.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the name or description of the error type.
	 *
	 * @return The name of the error type.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the error type based on the provided integer value.
	 *
	 * @param val The integer value to search for.
	 * @return The corresponding error type, or {@code UNDEFINED} if no match is found.
	 */
	public static final BITalinoErrorTypes getType(int val) {
		for (BITalinoErrorTypes t : BITalinoErrorTypes.values()) {
			if (t.getValue() == val) {
				return t;
			}
		}
		return UNDEFINED;
	}

	/**
	 * Retrieves the error type based on the provided name or description.
	 *
	 * @param val The name of the error to search for.
	 * @return The corresponding error type, or {@code UNDEFINED} if no match is found.
	 */
	public static final BITalinoErrorTypes getType(String val) {
		for (BITalinoErrorTypes t : BITalinoErrorTypes.values()) {
			if (t.getName().equals(val)) {
				return t;
			}
		}
		return UNDEFINED;
	}
}


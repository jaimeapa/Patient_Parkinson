package BITalino;

import javax.bluetooth.*;
import java.util.Vector;

/**
 * A class for discovering Bluetooth devices in the vicinity.
 * Implements the {@link DiscoveryListener} interface to handle device discovery events.
 */
public class DeviceDiscoverer implements DiscoveryListener {

    /**
     * A collection of discovered remote devices.
     * Only devices with the name "BITalino" are added to this vector.
     */
    public Vector<RemoteDevice> remoteDevices = new Vector<RemoteDevice>();

    /**
     * The discovery agent used to search for Bluetooth devices.
     */
    DiscoveryAgent discoveryAgent;

    /**
     * The name of the discovered Bluetooth device, if available.
     */
    public String deviceName;

    /**
     * The status of the inquiry process. Indicates whether the scan was completed, terminated, or encountered errors.
     */
    String inqStatus = null;

    /**
     * Constructs a new {@code DeviceDiscoverer} and starts the Bluetooth device inquiry process.
     * The discovery agent is initialized, and the inquiry begins immediately.
     */
    public DeviceDiscoverer() {
        try {
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            System.err.println(LocalDevice.getLocalDevice());
            discoveryAgent = localDevice.getDiscoveryAgent();
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when a Bluetooth device is discovered during the inquiry process.
     *
     * @param remoteDevice The discovered remote device.
     * @param cod The class of device (COD) of the discovered device.
     */
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass cod) {
        try {
            deviceName = remoteDevice.getFriendlyName(false); // Records the device name
            if (deviceName.equalsIgnoreCase("bitalino")) {
                remoteDevices.addElement(remoteDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the inquiry process is completed, terminated, or encounters errors.
     *
     * @param discType The type of inquiry completion. Possible values are:
     *                 {@code DiscoveryListener.INQUIRY_COMPLETED},
     *                 {@code DiscoveryListener.INQUIRY_TERMINATED},
     *                 {@code DiscoveryListener.INQUIRY_ERROR}.
     */
    public void inquiryCompleted(int discType) {
        if (discType == DiscoveryListener.INQUIRY_COMPLETED) {
            inqStatus = "Scan completed.";
        } else if (discType == DiscoveryListener.INQUIRY_TERMINATED) {
            inqStatus = "Scan terminated.";
        } else if (discType == DiscoveryListener.INQUIRY_ERROR) {
            inqStatus = "Scan with errors.";
        }
    }

    /**
     * Handles the event when Bluetooth services are discovered. Not implemented.
     *
     * @param transID The transaction ID of the service discovery operation.
     * @param servRecord An array of {@link ServiceRecord} objects representing the discovered services.
     */
    @Override
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {}

    /**
     * Handles the event when the service search is completed. Not implemented.
     *
     * @param transID The transaction ID of the service search operation.
     * @param respCode The response code indicating the result of the service search.
     */
    @Override
    public void serviceSearchCompleted(int transID, int respCode) {}
}


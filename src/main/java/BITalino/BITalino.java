
package BITalino;

 import javax.bluetooth.RemoteDevice;
 import javax.microedition.io.Connector;
 import javax.microedition.io.StreamConnection;
 import java.io.DataInputStream;
 import java.io.DataOutputStream;
 import java.io.IOException;
 import java.util.Vector;

/**
 * This class represents the BITalino device, which is used for acquiring physiological signals.
 * It provides methods to connect, configure, and retrieve data from the BITalino device.
 */
public class BITalino {

	/**
	 * An array representing the analog channels of the BITalino device.
	 * Initialized to null by default.
	 */
	private int[] analogChannels = null;

	/**
	 * The number of bytes used in the communication with the BITalino device.
	 * Default value is 0.
	 */
	private int number_bytes = 0;

	/**
	 * The socket connection to the BITalino device.
	 * Used for establishing a communication link.
	 */
	private StreamConnection hSocket = null;

	/**
	 * The input stream for receiving data from the BITalino device.
	 */
	private DataInputStream iStream = null;

	/**
	 * The output stream for sending data to the BITalino device.
	 */
	private DataOutputStream oStream = null;

	/**
	 * Constructs a new BITalino instance with default configurations.
	 */
	public BITalino() {}

	/**
	 * Searches for Bluetooth devices in range.
	 * @return a list of found devices with the name BITalino.
	 * @throws InterruptedException if the thread is interrupted during the search.
	 */
	public Vector<RemoteDevice> findDevices() throws InterruptedException
	{

		DeviceDiscoverer finder = new  DeviceDiscoverer();	
		while (finder.inqStatus == null) 
		{
			Thread.sleep(1000);
		}
		finder.inqStatus = null;
		return finder.remoteDevices;
		
	}

	/**
	 * Connects to a BITalino device.
	 * @param macAdd The device Bluetooth MAC address ("xx:xx:xx:xx:xx:xx").
	 * @param samplingRate Sampling rate in Hz. Accepted values are 1, 10, 100, or 1000 Hz.
	 * @throws BITalinoException if the MAC address or sampling rate is invalid.
	 */
	public void open(String macAdd, int samplingRate) throws BITalinoException 
	{
			if (macAdd.split(":").length > 1) 
			{
				macAdd = macAdd.replace(":", "");
			}
			if (macAdd.length() != 12) 
			{
				throw new BITalinoException(BITalinoErrorTypes.MACADDRESS_NOT_VALID);
			}
		
		try 
		{
			hSocket = (StreamConnection)Connector.open("btspp://" + macAdd + ":1", Connector.READ_WRITE);
			iStream = hSocket.openDataInputStream();
			oStream = hSocket.openDataOutputStream();
			Thread.sleep(2000);
			
		} 
		catch (Exception e) 
		{
			close();
		}
		
		try 
		{
			int variableToSend = 0;
			switch(samplingRate)
			{
			case 1000:
				variableToSend = 0x3;
				break;
			case 100:
				variableToSend = 0x2;
				break;
			case 10:
				variableToSend = 0x1;
				break;
			case 1:
				variableToSend = 0x0;
				break;
			default:
				close();
			}
			variableToSend = (variableToSend<<6)|0x03;
			Write(variableToSend);
		} 
		catch (Exception e) 
		{
			throw new BITalinoException(BITalinoErrorTypes.SAMPLING_RATE_NOT_DEFINED);
		}
	}

	/** Starts a signal acquisition from the device.
	 * \param[in] anChannels Set of channels to acquire. Accepted channels are 0...5 for inputs A1...A6.
	 * If this set is empty, no analog channels will be acquired.
	 * \remarks This method cannot be called during an acquisition.
	 * \exception BITalinoException (BITalinoErrorTypes.ANALOG_CHANNELS_NOT_VALID)
	 * \exception BITalinoException (BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED)
	 */
	public void start(int[] anChannels) throws Throwable 
	{
		analogChannels = anChannels;
		if (analogChannels.length > 6 | analogChannels.length == 0) {
			throw new BITalinoException(BITalinoErrorTypes.ANALOG_CHANNELS_NOT_VALID);
		} else {
			int bit = 1;
			for (int i : anChannels) {
				if (i<0 | i>5) 
				{
					throw new BITalinoException(BITalinoErrorTypes.ANALOG_CHANNELS_NOT_VALID);
				}
				else 
				{
					bit = bit | 1<<(2+i);
				}
			}
			int nChannels = analogChannels.length;
			if (nChannels <= 4) {
				number_bytes = (int) Math.ceil(((float)12 + (float)10 *nChannels)/8);
				
				
			} else {
				number_bytes = (int) Math.ceil(((float)52 + (float)6*(nChannels-4))/8);
			}
			try 
			{
				Write(bit);
			} 
			catch(Exception e) 
			{
				throw new BITalinoException(BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED);
			}
		}
		
	}

	/** Stops a signal acquisition.
	 * \remarks This method must be called only during an acquisition.
	 * \exception BITalinoException (BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED)
	 */
	public void stop() throws BITalinoException 
	{
		try 
		{
			Write(0);
		} 
		catch(Exception e) 
		{
			throw new BITalinoException(BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED);
		}
	}

	/** Disconnects from a %BITalino device. If an aquisition is running, it is stopped.
	 * \exception BITalinoException (BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED)
	 */
	public void close() throws BITalinoException 
	{
		try 
		{
			hSocket.close();
			iStream.close();
			oStream.close();
			hSocket=null;
			iStream = null;
			oStream = null;
		} 
		catch(Exception e) 
		{
			throw new BITalinoException(BITalinoErrorTypes.BT_DEVICE_NOT_CONNECTED);
		}
		
	}


	/**
	 * Writes a single data value to the BITalino device's output stream.
	 *
	 * @param data The data value to write to the device.
	 * @throws BITalinoException If communication with the device is lost.
	 */
	public void Write(int data) throws BITalinoException {
		try {
			oStream.write(data);
			oStream.flush();
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new BITalinoException(BITalinoErrorTypes.LOST_COMMUNICATION);
		}
	}

	/**
	 * Sets the battery threshold value for the BITalino device.
	 *
	 * @param value The battery threshold value, which must be between 0 and 63.
	 * @throws BITalinoException If the value is out of the valid range (0-63).
	 */
	public void battery(int value) throws BITalinoException {
		int Mode;
		if (value >= 0 && value <= 63) {
			Mode = value << 2;
			Write(Mode);
		} else {
			throw new BITalinoException(BITalinoErrorTypes.THRESHOLD_NOT_VALID);
		}

		
	}
	/** Assigns the digital outputs states.
	 * \param[in] digitalArray Vector of integers to assign to digital outputs, starting at first output (O1).
	 * On each vector element, 0 sets the output to low level and 1 sets the output to high level.
	 * This vector must contain exactly 4 elements.
	 * \remarks This method must be called only during an acquisition on original %BITalino. On %BITalino 2 there is no restriction.
	 * \exception BITalinoException (BITalinoErrorTypes.DIGITAL_CHANNELS_NOT_VALID)
	 * \exception BITalinoException (BITalinoErrorTypes.LOST_COMMUNICATION)
	 */
	public void trigger(int[] digitalArray) throws BITalinoException 
	{

		if (digitalArray.length != 4) 
		{
			throw new BITalinoException(BITalinoErrorTypes.DIGITAL_CHANNELS_NOT_VALID);
		} 
		else 
		{ 
			int data  = 3;
			for (int i= 0;i<digitalArray.length;i++) 
			{
				if (digitalArray[i]<0 | digitalArray[i]>1)
				{
					throw new BITalinoException(BITalinoErrorTypes.DIGITAL_CHANNELS_NOT_VALID);
				}
				else 
				{
					data = data | digitalArray[i]<<(2+i);
				}
				
			}
			Write(data);
		}
	}
	/** Returns the device firmware version string.
	 * \remarks This method cannot be called during an acquisition.
	 * \exception BITalinoException (BITalinoErrorTypes.LOST_COMMUNICATION)
	 * \exception IOException
	 */
	public String version() throws BITalinoException, IOException 
	{   

		try 
		{
			Write(7);
			byte[] version = new byte[30];
			String test = "";
			int i = 0;
			while (true) 
			{
				iStream.read(version,i,1);
				i++;
				test = new String(new byte[] {version[i-1]});
				if (test.equals("\n")) 
				{
					break;
				}
			}
			return new String(version);
		} 
		catch(Exception e) 
		{
			throw new BITalinoException(BITalinoErrorTypes.LOST_COMMUNICATION);
		}
	}
	/** Unpack a raw byte stream into a frames vector.
	 * \param[in] buffer Vector with the bytes read from the device.
	 * \return Vector of frames decoded frames.
	 * \exception BITalinoException (BITalinoErrorTypes.INCORRECT_DECODE)
	 */
	private Frame[] decode(byte[] buffer) throws IOException, BITalinoException 
	{

		try 
		{
			Frame[] frames = new Frame[1];
			int j=(number_bytes-1), i=0, CRC = 0,x0=0,x1=0,x2=0,x3=0,out=0,inp=0;
			CRC= (buffer[j-0]&0x0F)&0xFF;
			// check CRC
			for (int bytes = 0; bytes<number_bytes;bytes++) 
			{
				for (int bit=7;bit>-1;bit--)
				{
					inp=(buffer[bytes])>>bit & 0x01;
					if (bytes == (number_bytes - 1) && bit<4) 
					{
						inp = 0;
					}
					out=x3;
					x3=x2;
					x2=x1;
					x1=out^x0;
					x0=inp^out;
				}
			}
			//if the message was correctly received, it starts decoding	
			if (CRC == ((x3<<3)|(x2<<2)|(x1<<1)|x0)) 
			{
			
				/*parse frames*/
				
				frames[i]=new Frame();            
				frames[i].seq = (short) ((buffer[j-0]&0xF0)>>4)&0xf;
				frames[i].digital[0] = (short)((buffer[j-1]>>7)&0x01);
				frames[i].digital[1] = (short)((buffer[j-1]>>6)&0x01);
				frames[i].digital[2] = (short)((buffer[j-1]>>5)&0x01);
				frames[i].digital[3] = (short)((buffer[j-1]>>4)&0x01);
											
				/*parse buffer frame*/
				switch(analogChannels.length-1)
				{
				
				case 5:
					frames[i].analog[5]= (short)((buffer[j-7]&0x3F));	
				case 4:

					frames[i].analog[4] = (short)((((buffer[j-6]&0x0F)<<2)|((buffer[j-7]&0xc0)>>6))&0x3f);
				case 3:
					
					frames[i].analog[3] = (short)((((buffer[j-5]&0x3F)<<4)|((buffer[j-6]&0xf0)>>4))&0x3ff);
				case 2:
					
					frames[i].analog[2] = (short)((((buffer[j-4]&0xff)<<2)|(((buffer[j-5]&0xc0)>>6)))&0x3ff);
				case 1:
					
					frames[i].analog[1] = (short)((((buffer[j-2]&0x3)<<8)|(buffer[j-3])&0xff)&0x3ff);
				case 0:
					
					frames[i].analog[0] = (short)((((buffer[j-1]&0xF)<<6)|((buffer[j-2]&0XFC)>>2))&0x3ff);
				}
				
				
			} 
			else 
			{
				frames[i]=new Frame();
				frames[i].seq = -1;
			}
			return frames;
		} 
		catch (Exception e) 
		{
			throw new BITalinoException(BITalinoErrorTypes.INCORRECT_DECODE);
		}
	}

	/** Reads acquisition frames from the device.
	 * This method returns when all requested frames are received from the device, or when a receive timeout occurs.
	 * \param[in] nSamples Number of frames that should be read from the device.
	 * \return Vector of frames obtained from the device.
	 * \remarks If a problem occurred, the size of the frames vector  is lower than the frames vector size. This method must be called only during an acquisition.
	 * \exception BITalinoException (BITalinoErrorTypes.LOST_COMMUNICATION)
	 */
	public Frame[] read(int nSamples) throws BITalinoException 
	{

		try 
		{
			Frame[] frames = new Frame[nSamples];
			byte[] buffer = new byte[number_bytes];
			byte[] bTemp = new byte[1];
			int i=0;
			while (i<nSamples) 
			{
				iStream.readFully(buffer,0,number_bytes);
				Frame[] f = decode(buffer);
				if (f[0].seq == -1) 
				{
					while (f[0].seq == -1) 
					{
						iStream.readFully(bTemp,0,1);
						for (int j = number_bytes-2; j >= 0; j--) 
						{                
						    buffer[j+1] = buffer[j];
						}
						buffer[0] = bTemp[0];
						f = decode(buffer);
					}
					frames[i] = f[0];
				} 
				else 
				{
					
					frames[i] = f[0];
				}
				i++;
			}
			return frames;
		} 
		catch (Exception e) 
		{
			throw new BITalinoException (BITalinoErrorTypes.LOST_COMMUNICATION);
		}
		
	}
	
}

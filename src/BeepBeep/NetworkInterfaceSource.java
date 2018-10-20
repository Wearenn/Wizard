package BeepBeep;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.util.PcapPacketArrayList;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.Source;

/**
 * Outputs packets captured live from a network interface
 */

public class NetworkInterfaceSource extends Source {

	private String interfaceName;
	private Pcap pcap;
	private PcapPacketHandler<PcapPacketArrayList> jpacketHandler = new PcapPacketHandler<PcapPacketArrayList>() {
		public void nextPacket(PcapPacket packet, PcapPacketArrayList PacketsList) {
			PacketsList.add(packet);
		}
	};

	public NetworkInterfaceSource(String interfaceName) throws Exception {
		super(1);
		this.interfaceName = interfaceName;

		List<PcapIf> allDevices = new ArrayList<PcapIf>();
		StringBuilder errbuf = new StringBuilder();

		System.out.println("Opening device with name '" + interfaceName +"'");
		
		// Finding all devices
		int r = Pcap.findAllDevs(allDevices, errbuf);
		if (r == Pcap.NOT_OK || allDevices.isEmpty()) {
			throw new Exception("Can't read list of network devices. Error: " + errbuf.toString()
					+ "\nDo you have the rights to access the network interface?");
		}

		// Finding device with name interfaceName
		int i = 0;
		int interfaceIndex = -1;
		for (PcapIf device : allDevices) {
			if (device.getName().equals(interfaceName)) {
				interfaceIndex = i;
				break;
			}
			i++;
		}
		if (interfaceIndex < 0) {
			throw new Exception("Device with name " + interfaceName + " was not found.");
		}
		PcapIf device = allDevices.get(interfaceIndex);

		// Opening the found device
		int snaplen = 64 * 1024; // Capture all packets, no truncation
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 0;
		pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		if (pcap == null) {
			throw new Exception("Error while opening device for capture: " + errbuf.toString());
		}
		
		System.out.println("Live capture ready");
	}
	
	@Override
	protected boolean compute(Object[] inputs, Queue<Object[]> outputs) {
		PcapPacketArrayList packets = new PcapPacketArrayList();
		pcap.loop(1, jpacketHandler, packets);
		Object[] out = new Object[1];
		out[0] = packets.get(0);
		outputs.add(out);
		return true;
	}

	@Override
	public Processor clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getInterfaceName(){
		return interfaceName;
	}

	@Override
	public Processor duplicate(boolean b) {
		return null;
	}
}

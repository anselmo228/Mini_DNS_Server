import java.net.*;
import java.io.*;

public class Root_Dns {
	public static void main(String args[]) {
		try {
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(8888); 
			
			InetAddress ia = InetAddress.getByName("Localhost");
			
			while(true) {
				byte[] bf = new byte[300];
				DatagramPacket dp = new DatagramPacket(bf, bf.length); 
				System.out.println("Waiting for a packet reception...");
				ds.receive(dp);
				 
				String rs = new String(bf);
				String rs2= rs.trim();
				
				System.out.println("IP:" + dp.getAddress() + " Port#:" + dp.getPort()); 
				System.out.println("URl: " + rs);
				
				String[] toTLD = rs2.split("\\.");
				
				@SuppressWarnings("resource")
				DatagramSocket toLocal = new DatagramSocket();
				
				
				
				
				int portnum = 0;
				
				System.out.println("searching for TLD: " + toTLD[toTLD.length -1]);
				
				/*TLD 주솟값 데이터  */
				String opt = toTLD[toTLD.length -1];
				switch(opt) 
				{
				case "com":
					portnum = 7777;
					break;
				case "kr":
					portnum = 6666;
					break;
				case "uk":
					portnum = 5555;
					break;
				case "net":
					portnum = 4444;
					break;
				default: 
					System.out.println("Cannot find the address");
					break;					
				}
				
				String portnum_rs = Integer.toString(portnum);
				
				/*TLD주솟값 Local로 넘겨주기 */
				byte[] tld = portnum_rs.getBytes();
			
				DatagramPacket ttld = new DatagramPacket(tld, tld.length, ia, 9999);
				toLocal.send(ttld);
				
				
			}
			
		} catch(IOException e) {
			
		}
	}
}

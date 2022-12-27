import java.net.*;
import java.util.HashMap;
import java.io.*;

public class Local_Dns extends Thread {
	public static void main(String args[]){
	
		try {
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(9999); 
			InetAddress ia = InetAddress.getByName("Localhost");
			
			/*미리 캐싱된 데이터*/
			@SuppressWarnings("serial")
			HashMap<String, String> cache = new HashMap<>() {{
				put("google", "111.111.111.111"); //com
				put("daum", "123.123.123.123"); //net
				put("gachon","234.234.234.234"); //kr
			}};
			
			while(true) {
				byte[] bf = new byte[300];
				DatagramPacket dp = new DatagramPacket(bf, bf.length); 
				System.out.println("Waiting for a packet reception...");
				ds.receive(dp);
				String rs = new String(bf);
				String URL = rs.trim();
				
				System.out.println("[Host Address]IP:" + dp.getAddress() + " Port#:" + dp.getPort()); 
				int PortHost = dp.getPort();
				System.out.println("URl: " + URL);
				
				
				
				String[] domain = URL.split("\\.");
				
				
				
				if(cache.containsKey(domain[domain.length -2])) // 만약 URL이 캐시되어 있다면 HashMap에서 가져오기 
				{
					
					
					byte[] host = new byte[300];
					host = ("(CACHED DATA)" + cache.get(domain[domain.length -2])).getBytes();
					System.out.println("[Cached!]");
					
					DatagramPacket CtoHost = new DatagramPacket(host, host.length, ia, PortHost);
					ds.send(CtoHost);
				}
			    	
				else // 캐시 되어 있지 않다면 
				{
				byte[] rootB = URL.getBytes();
				DatagramPacket toRoot = new DatagramPacket(rootB, rootB.length, ia, 8888);
				ds.send(toRoot);
				
				/*Root로부터 해당 TLD에 맞는 TLD 주소 받아오기 */
				byte[] new_bf = new byte[300];
				DatagramPacket fromRoot = new DatagramPacket(new_bf, new_bf.length, ia, 8888);
				ds.receive(fromRoot);
				
				String TLD_port = new String(new_bf);
				String TLD_port1 = TLD_port.trim();
				
				int fromRoot_int = Integer.parseInt(TLD_port1);
				
				/*TLD로 URL주소 넘겨주기 */
				DatagramPacket toTLD = new DatagramPacket(rootB, rootB.length, ia, fromRoot_int);
				ds.send(toTLD);
				
				/*TLD로부터 URL IP주소 받아오기 */
				byte[] new_buff = new byte[300];
				DatagramPacket fromTLD = new DatagramPacket(new_buff, new_buff.length, ia, fromRoot_int);
				ds.receive(fromTLD);
				
				String TLD_IP = new String(new_buff);
				String TLD_IP2 = TLD_IP.trim();
				
				System.out.println("[Not Cached!]");
				cache.put(domain[domain.length -2], TLD_IP2); //URl의 ip주소를 hashmap에 저장 함으로써 다음번 입력때 TLD까지 가지 않고 바로 캐싱해주기  
				
				
				/*최종 IP주소 Host로 돌려주기 */
				byte[] fresult = TLD_IP2.getBytes();
				DatagramPacket toHost = new DatagramPacket(fresult, fresult.length, ia, PortHost);
				ds.send(toHost);
				}
				
			
				
			}
			
	
		
		} catch(IOException e) {}
		
	
}
}

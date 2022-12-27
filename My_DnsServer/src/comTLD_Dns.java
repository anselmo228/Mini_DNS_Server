import java.net.*;
import java.util.HashMap;
import java.io.*;

public class comTLD_Dns {
	public static void main(String args[]) {
		try {
			@SuppressWarnings("resource")
			DatagramSocket ds = new DatagramSocket(7777); 
			InetAddress ia = InetAddress.getByName("Localhost");
			
			while(true) {
				System.out.println("Waiting for a packet reception...");
				
				
				byte[] bf = new byte[300];
				
				/*Local로부터 URL 값 받아오기 */
				DatagramPacket fromLocal = new DatagramPacket(bf, bf.length, ia, 9999);
				ds.receive(fromLocal);
				
				System.out.println("IP:" + fromLocal.getAddress() + " Port#:" + fromLocal.getPort());
				
				String URL = new String(bf);
				String URL1 = URL.trim();
				
				String[] domain = URL1.split("\\.");
				
				/* .com에 해당하는 전체 주소  */
				@SuppressWarnings("serial")
				HashMap<String, String> map = new HashMap<>() {{
						put("google", "111.111.111.111");
						put("naver", "222.222.222.222");
						put("twitter", "333.333.333.333");				
				}};
				
				
				
				byte[] IP_bf = map.get(domain[domain.length - 2]).getBytes(); // 해당 URL의 주소 값 저장  
				
				/*Local로 IP주소 반환해주기  */
				DatagramPacket toLocal = new DatagramPacket(IP_bf, IP_bf.length, ia, 9999);
				ds.send(toLocal);
				
			}
			
	
		
		} catch(IOException e) {}
		
	
}
}

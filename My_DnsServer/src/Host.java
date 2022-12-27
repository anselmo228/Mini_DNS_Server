import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Host {

		public static void main(String args[]) {
			try {
				System.out.println("[Domain List]");
				System.out.println("<com URL>       <kr URL>    <uk URL>         <net URL>");
				System.out.println("1. google.com    snu.kr    oxford.uk         daum.net" + "\n" + "2. naver.com    "
						+ "gachon.kr    cambridge.uk    microsoft.net" + "\n" + "3. twitter.com     korea.kr                 amazon.net");
				System.out.println("=======================================================" + "\n");
				
				while(true)
				{
				@SuppressWarnings("resource")
				DatagramSocket ds = new DatagramSocket();
				InetAddress ia = InetAddress.getByName("Localhost");
				
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				
				System.out.println("Enter URL: ");
				String url = scan.next();
				
				if(url.equals("0"))
				{
					System.out.println("Server closed");
                    break;
				}
				/*입력한 URl주소 Local로 넘겨주기  */
				byte[] bf = url.getBytes();
				DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9999);
				ds.send(dp);
				bf = null;
				
				/*최종 IP 주솟 값 Local로부터 받아오기  */
				byte[] result = new byte[300];
				DatagramPacket IP = new DatagramPacket(result, result.length, ia, 9999);
				ds.receive(IP);
				
				
				String fresult = new String(result);
				
				System.out.println("Ip adress:" + fresult); //최종 결과 출력  
				
				}
				
			} catch(IOException e) {}
			
		}
	}


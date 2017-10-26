import java.util.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient implements Runnable 
{
   Socket s = null;
   protected BufferedReader stdin;
   protected PrintWriter stdout;
   protected PrintWriter stderr;
   public TCPClient(String host, int port) {
	  try {
		s = new Socket(host, port);
		  stdin = new BufferedReader(
			         new InputStreamReader(s.getInputStream()));
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
   }
   public void send(String msg) 
   {       
	   PrintWriter sockOut = null;
	try {
		sockOut = new PrintWriter(s.getOutputStream(), true);
		sockOut.println("Sending some stuff");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   sockOut.println(msg);
   }

@Override
public void run() 
{
	while(true)
	{
		String message = null;
		try {
			message = stdin.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


//   public void controlLoop() {
//      while(true) {
//         try {
//            stdout.print("-> ");
//            stdout.flush();
//            String msg = stdin.readLine();
//            if (msg == null) continue;
//            if (msg.equals("quit")) break;
//            stdout.println("sending: " + msg);
//            send(msg);
//            System.out.println("hello14");
//            msg = receive();
//            stdout.println("received: " + msg);
//         } catch(IOException e) {
//            stderr.println(e.getMessage());
//            break;
//         }
//      }
//      send("quit");
//      stdout.println("bye");
//   }
}
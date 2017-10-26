import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable
{
   protected ServerSocket serverSocket;
   protected BufferedReader in = null;
   protected PrintWriter out = null;
   private ArrayList<Socket> clientList = null;
   protected int myPort;
   
   public void addClient(Socket s)
   {
	   clientList.add(s);
   }
   
   public TCPServer(int port) 
   { 
      try 
      {
    	 clientList = new ArrayList<Socket>();
         myPort = port;
         serverSocket = new ServerSocket(myPort);
      } 
      catch(Exception e) 
      {
         System.err.println(e.getMessage());
         System.exit(1);
      } // catch
   }
@Override
	public void run() 
	{
	        try {
	         while(true) {
	            System.out.println("Server listening at port " + myPort);
	            Socket clientSocket = serverSocket.accept();
	            addClient(clientSocket);
	            System.out.println( "Adding this client on socket "+ clientSocket.getPort());
	         }
	      } catch(IOException ioe) {
	         System.err.println("Failed to accept socket, " + ioe);
	         System.exit(1);
	      }
	}
	
	public void notifyAll(String action, DShapeModel a)
	{
		for(Socket sock : clientList)
		{
			notify(sock, action, a);
		}
	}
	public void notify(Socket sock, String action, DShapeModel a)
	{
		 XMLEncoder xmlEncoder = null;
		try {
			out = new PrintWriter(sock.getOutputStream(), true);
			//xmlEncoder = new XMLEncoder(sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(action);
		System.out.println("Sending a message through socket number " + sock.getPort() );
		// xmlEncoder.writeObject(action + " ");
		 //xmlEncoder.writeObject(a);
		//xmlEncoder
		 //xmlEncoder.close();
	}
}
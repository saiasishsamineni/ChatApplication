import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;


public class server {

    private ServerSocket serversocket;


    public server(ServerSocket serversocket) {
        this.serversocket = serversocket;
    }

    public void start_sever()
    {
        try{
            while(!serversocket.isClosed()){
                Socket socket= serversocket.accept();
                System.out.println("New client has connected");
                BufferedWriter writer= new BufferedWriter(new FileWriter("location.txt"));
                writer.write(Inet4Address.getLocalHost().getHostAddress()+"/"+socket.getPort());
                writer.newLine();
                writer.close();
                System.out.println(Inet4Address.getLocalHost().getHostAddress()+"/"+socket.getPort());
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

            }

            }catch (IOException e) {

        }

        }

        public void closeserversocket()
        {
            try {
                if(serversocket!=null)
                {
                    serversocket.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

    }
    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(1235);
        server Server= new server(serversocket);
        Server.start_sever();

    }
}

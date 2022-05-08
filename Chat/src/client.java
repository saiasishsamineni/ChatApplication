import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;


    public client(Socket socket, String username){
        try{
            this.socket=socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }



    public void sendMessage(){
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner= new Scanner(System.in);

            while(socket.isConnected()){
                String messageToSend=scanner.nextLine();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                bufferedWriter.write("["+dtf.format(now)+"]"+username+":"+messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGrouphat;

                while (socket.isConnected()){
                    try{
                        msgFromGrouphat= bufferedReader.readLine();
                        System.out.println();
                        System.out.println(msgFromGrouphat);

                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }

            }
        }).start();
    }

    public void closeEverything( Socket socket,BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try {
            if (bufferedReader!= null){
                bufferedReader.close();
            }

            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket != null)
            {
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
   public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
       System.out.println("Enter your user name for the chat");
       String username= scanner.nextLine();
       BufferedWriter writer= new BufferedWriter(new FileWriter("users.txt"));
       writer.write(username);
       writer.newLine();
       writer.close();
       Socket socket=new Socket("localhost", 1235);
       client client=new client(socket,username);
       client.listenForMessage();
       client.sendMessage();
   }
    }

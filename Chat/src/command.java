import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class command {
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter the command");
        Scanner sc =  new Scanner(System.in);
        String in= sc.nextLine();
        switch (in){
            case "list-users":
                BufferedReader one = new BufferedReader(new FileReader("users.txt"));
                String line1;
                while ((line1 = one.readLine()) != null) {
                    System.out.println(line1);
                }
                break;
            case "list-geo":
                BufferedReader br = new BufferedReader(new FileReader("location.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

                break;
            case "status":
                System.out.println("status");
                break;
            default:
                System.out.println("thanks");

        }
    }
}

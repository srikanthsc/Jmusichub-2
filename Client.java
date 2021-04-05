import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.*;
import java.io.*;


public class Client extends Serveur {
  
  
    public static void main(String[] args) {
       
       final Socket clientSocket;
       final BufferedReader in;
       final PrintWriter out;
       final Scanner sc = new Scanner(System.in);
System.out.println("Bienvenue sur JMusicHub");
try
{
  // Le fichier d'entrée
  File file = new File("C:/Users/Admin/Desktop/client serveur/Project/src/musichub/main/file.txt");    
  // Créer l'objet File Reader
  FileReader fr = new FileReader(file);  
  // Créer l'objet BufferedReader        
  BufferedReader br = new BufferedReader(fr);  
  StringBuffer sb = new StringBuffer();    
  String line;
  while((line = br.readLine()) != null)
  {
    // ajoute la ligne au buffer
    sb.append(line);      
    sb.append("\n"); 
        
  }
  fr.close();    
  
  System.out.println(sb.toString()); 
  System.out.println("Idantifiant: ");
  String ID = sc.nextLine();  
  System.out.println("Password: ");
  String password = sc.nextLine();
  System.out.println("Authentification Accepter");
  
}
catch(IOException e)
{
  e.printStackTrace();

}
;
System.out.println("nonbre de chansons que vous voulez ajoutez : ");
int size = sc.nextInt();
String tab[] = new String[size];

System.out.println("Entrez les chansons: ");
for(int i=0; i < size; i++) {
   tab[i] = sc.next();
}
System.out.println("playlist creer: " + Arrays.toString(tab));


       System.out.println("Taper Start puis go pour commencer et voici les touche P  = play, S = Stop, R = Reset, Q = Quit");
       
     
       try {
         
        /*
        * les informations du serveur ( port et adresse IP ou nom d'hote
        * 127.0.0.1 est l'adresse local de la machine
        */
        clientSocket = new Socket("127.0.0.1",5009);
  
        //flux pour envoyer
        out = new PrintWriter(clientSocket.getOutputStream());
        //flux pour recevoir
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       out.println("musique ajouter recement dans la playlist"+Arrays.toString(tab));  
        Thread envoyer = new Thread(new Runnable() {
            String msg;
             @Override
             public void run() {
               while(true){
                 msg = sc.nextLine();
                 out.println(msg);
                 out.flush();
               }
            }
            
        });
        envoyer.start(); 
        
        Thread recevoir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
               try {
                 msg = in.readLine();
                 
                 while(msg!=null){
                    System.out.println("Serveur : "+msg);
                    msg = in.readLine();
                 }
                 System.out.println("Serveur déconecté");
                 out.close();
                 clientSocket.close();
               } catch (IOException e) {
                 System.out.println("deconnecter");
                   e.printStackTrace();
               }
            }
        });
        recevoir.start();
        
      } catch (IOException e) {
           e.printStackTrace();
      }
  }

}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;
//package serveur-client.Serveur;
//import musichub.business.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//import musichub.business.*;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.Scanner;
import java.util.NoSuchElementException;

//package serveur-client.Serveur;
//import musichub.business.*;
import java.util.*; 
import java.util.Arrays;  

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; 

import javax.sound.sampled.*;
//import javax.sound.*;
import java.io.*;


interface Update {
  public void displaylistupdate();
}


public class Client extends Serveur {

    public static void main(String[] args)  {
       Serveur sv;
       final Socket clientSocket;
       final BufferedReader in;
       final PrintWriter out;
       final Scanner sc = new Scanner(System.in);
       UpdateList ipl = new UpdateList();

       filetxt();
       printMenu();
       ipl.displaylistupdate();
        identification();
        Singleton2 s1 = Singleton2.getInstance();
        s1.show1();
         //TODO: handle exception
       
       
      

  
  
 
    

  activitysong();
  signout();
  
  
     
      
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
        out.println("lecteur a l'arret");
        out.println("appuyer sur sur touche a partir du serveur pour les chansons disponibles "); 
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
                    Scanner sc = new Scanner(System.in);
                 }
                 System.out.println("Serveur déconecté");
                 out.close();
                 clientSocket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            }
        });
        recevoir.start();
        
      } catch (IOException e) {
           e.printStackTrace();
      }


  }
  public static void printMenu(){
		System.out.println();
		System.out.println();
		System.out.println("**********************************************");
		System.out.println("*******Welcome JMusicHub***********************");
		System.out.println("********Music for everyone**********************");
		System.out.println("----------------------------------------------");
}



  public static void filetxt(){
    try
    {
      // Le fichier d'entrée
      File file = new File("file.txt");    
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
     // System.out.println("Contenu du fichier: ");
      System.out.println(sb.toString());  
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  
}

public static void identification()  {

Scanner scanner = new Scanner(System.in);
            
  String login, password;
     
  do {
      System.out.print( "Veuillez saisir votre identifiant : " );
      login = scanner.nextLine();
      
      System.out.print( "Veuillez saisir votre mot de passe : " );
      password = scanner.nextLine();
  } while( login.equals( "prof" ) == false || password.equals( "esiea" ) == false );
  
  System.out.println( "Bonjour  Proffesseur, Authentification Accepter" );            
  System.out.println("Taper s (start) pour continuer");
  String start=scanner.nextLine();

}


 public static void activitysong(){
  Scanner scanner=new Scanner(System.in);
  String response3;
  System.out.println("selectionner un numero pour jouer la musique");
   System.out.println("1- weekend save your tears");
   System.out.println("2- weekend Bliding light");
   System.out.println("3- ninho");
  response3 = scanner.next();
   response3 = response3.toUpperCase();
   
  switch(response3){
 case("1"):System.out.println("weekend en ecoute");
 Playmusic mb_745 = new Playmusic("music/the-weeknd1.wav");
 System.out.println("ecoute terminer");
 break;
 
 case("2"):System.out.println("weekend Bliding Light");
 Playmusic mb_746 = new Playmusic("music/the-weeknd.wav");
 System.out.println("ecoute terminer");
 break;
 case("3"):System.out.println("ninho");
 Playmusic mb_747 = new Playmusic("music/ninho.wav");
 System.out.println("ecoute terminer");
 System.out.println("a l'arret");
 System.out.println("appuyer sur une touche pour que le serveur vous envoie les information");
 break;
 case("s"):System.out.println("continue");
  }
}

public static void signout(){
  
  Scanner scanner=new Scanner(System.in);
  String response2;
  System.out.println("diconnected type DISCONNECT or conected type CONTINUE");
  response2 = scanner.next();
   response2 = response2.toUpperCase();
  
   switch(response2) {
    case ("DISCONNECT"): System.exit(1);
    break;
    case("CONTINUE"):System.out.println("connected");
}

 }
 
}
 


class UpdateList implements Update {
  public void displaylistupdate(){
     try
   {
     // Le fichier d'entrée
     File file = new File("Songtest.txt");    
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
    // System.out.println("Contenu du fichier: ");
    System.out.println("update done");
     System.out.println(sb.toString());  
     
   }
   catch(IOException e)
   {
     e.printStackTrace();
   }
         
  }


}


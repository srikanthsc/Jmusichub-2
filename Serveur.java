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


//package serveur-client.Serveur;
//import musichub.business.*;
import java.util.*; 
import java.util.Arrays;  

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;                 

public class Serveur  {
   
   
 
    public static void main(String[] test) {
   
      final ServerSocket serveurSocket  ;
      final Socket clientSocket ;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc=new Scanner(System.in);
      File file = new File("F:/music/mix_7m29s (audio-joiner.com).wav");
      File file1=new File("F:/music/the-weeknd1.wav");
      Client client;
      List<String> list1 = new ArrayList<String>(); 
        list1.add("1.Alone Again - The Weeknd "); 
        list1.add("2.Too Late - The Weeknd "); 
        list1.add("3.Hardest to Love - The Weeknd ");
        list1.add("4.Scared to Live - The Weeknd ");
        list1.add("5.Snowchild - The Weeknd ");
        list1.add("6.Escape from LA- The Weeknd ");
        list1.add("7.Heartless -The Weeknd ");
        list1.add("8.Faith - The Weeknd ");
        list1.add("9.Blinding Lights - The Weeknd ");
        list1.add("10.In Your Eyes - The Weeknd ");
        list1.add("11.Save Your Tears - The Weeknd ");
        
        
      
      
        

        
       
      
   
   
      try {
        serveurSocket = new ServerSocket(5009);
        clientSocket = serveurSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
        Thread envoi= new Thread(new Runnable() {
           String msg;
           String m1="fichier";
           
           @Override
           public void run() {
            
              while(true){
                           
                 msg = sc.nextLine();
                 out.println("chanson disponible");
                 out.println(list1);
                 //out.println("chanson disponible");
                 out.println(msg);
                 out.println("P  = play, S = Stop, R = Reset, Q = Quit");
                 //out.println(file +  " Bliding light") ;
                 //out.println(clip);
                 //out.println("t: display the album titles, ordered by date");
                 //out.println("g: display songs of an album, ordered by genre");
            //out.println("d: display songs of an album");
            //out.println("u: display audiobooks ordered by author");
            //out.println("c: add a new song");
            //out.println("a: add a new album");
            //out.println("+: add a song to an album");
            //out.println("l: add a new audiobook");
            //out.println("p: create a new playlist from existing songs and audio books");
            //out.println("-: delete an existing playlist");
            //out.println("s: save elements, albums, playlists");
            //out.println("q: quit program");
		        
                 out.flush();
              }
           }
        });
        envoi.start();
    
        Thread recevoir= new Thread(new Runnable() {
           String msg ;
           @Override
           public void run() {
              try  { 
                 msg = in.readLine();
                 
                 //tant que le client est connecté
                 while(msg!=null){
                    System.out.println("Client : "+msg);
                    msg = in.readLine();
                    Scanner scanner = new Scanner(System.in);
                
  try{
   
  AudioInputStream  audioStream  = AudioSystem.getAudioInputStream(file);
 

  
  Clip clip = AudioSystem.getClip();
  
  clip.open(audioStream);
  AudioInputStream  audioStream1  = AudioSystem.getAudioInputStream(file1);
   Clip clip1 = AudioSystem.getClip();
 clip1.open(audioStream1);
  
  
  //out.println(file);
  
 
  String response = "";
 
  
  while(!response.equals("Q") ) {
   System.out.println("P  = play, S = Stop, R = Reset, Q = Quit");
   out.println("P  = play, S = Stop, R = Reset, Q = Quit");
   System.out.print("Enter your choice: ");
   
   
   response = scanner.next();
   response = response.toUpperCase();
   String a1="en ecoute";
   
   
  


   
   switch(response) {
    case ("P"): clip.start();
    System.out.println(a1);
    out.println(a1);
    break;
    case ("S"): clip.stop();
    System.out.println("en pause");
    out.println("en pause");
    break;
    case ("R"): clip.setMicrosecondPosition(0);
    break;
    case ("Q"): clip.close();
    break;
    default: System.out.println("Not a valid response");
   }
}


  
  out.println("Byeee");
  System.out.println("Byeeee!"); 
}catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex){}
                 }
                 //sortir de la boucle si le client a déconecté
                 System.out.println("Client déconecté");
                 //fermer le flux et la session socket
                 out.close();
                 clientSocket.close();
                 serveurSocket.close();
              } catch (IOException e  ) {
                   e.printStackTrace();
              }
          }
       });
       recevoir.start();
       }catch (IOException e) {
          e.printStackTrace();
          
   
       }
       
       
    }

    
    
    
 }
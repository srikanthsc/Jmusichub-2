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


//package serveur-client.Serveur;
//import musichub.business.*;
import java.util.*; 
import java.util.Arrays;  

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; 

import javax.sound.sampled.*;
//import javax.sound.*;
import java.io.*;



class  Playmusic  { //classe Playmusic
  
   Playmusic(String filename){ //contructeur
     int total, totalToRead, numBytesRead, numBytesToRead;
     byte[] buffer;
     boolean         stopped;
     AudioFormat     wav;  //format de l'audio
     TargetDataLine  line;  // creation de le variable line
     SourceDataLine  lineIn; // creation de le variable lineIn
     DataLine.Info   info;  // creation de le variable info
     File            file; // creation de le variable file
     FileInputStream fis; //// creation de le variable fis

     wav = new AudioFormat(44100, 16, 2, true, false);
     info = new DataLine.Info(SourceDataLine.class, wav);
     buffer = new byte[1024*333];
     numBytesToRead = 1024*333;
     total=0;
     stopped = false;
     if (!AudioSystem.isLineSupported(info)) {
       System.out.print("no support for " + wav.toString() );
     }
     try {
       // Obtenir et ouvrir la ligne
       lineIn = (SourceDataLine) AudioSystem.getLine(info);
       lineIn.open(wav);
       lineIn.start();
       fis = new FileInputStream(file = new File(filename));
       totalToRead = fis.available();
       while (total < totalToRead && !stopped){
         numBytesRead = fis.read(buffer, 0, numBytesToRead);
         if (numBytesRead == -1) break;
         total += numBytesRead;
         lineIn.write(buffer, 0, numBytesRead);
       }
     } catch (LineUnavailableException ex) {
       ex.printStackTrace();
     } catch (FileNotFoundException nofile) {
       nofile.printStackTrace();
     } catch (IOException io) {
       io.printStackTrace();
     }
   }
     
   }

public class Serveur   {
 
    public static void main(String[] test)throws IOException {
   
      final ServerSocket serveurSocket  ;
      final Socket clientSocket ;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc=new Scanner(System.in);
    // La classe PrintWriter ajoute à un flux la possibilité de faire des écriture sous forme de texte des types primitifs java, et des chaînes de caractères.
      Serveur sv= new Serveur();
      

      String response1;
		String textString;
		int count=0;
      //Initialisation du Design Pattern
      Singleton s = Singleton.getInstance();
      s.show();

		
		
		try{
			FileWriter fwTest = new FileWriter("Songtest.txt",true);
			fwTest.close();
		}
		catch(FileNotFoundException fnfe){
			File f = new File("Songtest.txt");
		}
		
		
		//Configurer pour lire à partir de la console
		BufferedReader consoleBR = startConsoleBuffer();
		
		BufferedReader textBR = startFileReader();
		textBR.mark(1000);
		
		String songInfo;
		boolean keepGoing=true;		
		
		
		while(keepGoing){
			ShowMenu();
			
			response1 = consoleBR.readLine();

			switch(response1){// ajout des switch case
			
			case "1":
				FileWriter fw = new FileWriter("Songtest.txt",true);
				System.out.println("Please enter the song in the following format:");
				System.out.println("title,singer,album");
				songInfo = consoleBR.readLine();
				fw.write(songInfo+'\n');
				fw.close();
				break;
				
			case "2":
				songInfo = textBR.readLine();
				while(songInfo!=null){
					System.out.println(songInfo);
					songInfo = textBR.readLine();
				}//fin du while
				textBR.reset();
				break;
				
			case "3":
				count=0;
				System.out.println("What artist do you want to see songs for?");
				String artRequested = consoleBR.readLine();
				
				textString = textBR.readLine();
				while(textString!=null){
					String songArray[] = textString.split(",");
					if(songArray[1].equalsIgnoreCase(artRequested)){
						count++;
						for(String info:songArray){
							System.out.print(info+"  ");
						}
						System.out.println("");
					}//fin du if
					textString = textBR.readLine();
					
				}//fin du while
				
				if(count==0){
					System.out.println("No songs found");
				}
				textBR.reset();
				break;
				
			case "4":
				count=0;
				System.out.println("What album do you want to see songs for?");
				String albRequested = consoleBR.readLine();
				
				textString = textBR.readLine();
				while(textString!=null){
					String songArray[] = textString.split(",");
					if(songArray[2].equalsIgnoreCase(albRequested)){
						count++;
						for(String info:songArray){
							System.out.print(info+"  ");
						}
						System.out.println();
					}//fin du if
					textString = textBR.readLine();
				}// fin du boucle
				if(count==0){
					System.out.println("No songs found");
				}
				textBR.reset();
				break;
				
			case "5":
				keepGoing=false;
            System.out.println("waiting for the connection.....");
				break;
			}// fin de la boucle Switch
			
		}// fin du bouble whilw KeepGoing
		
   
      try {
         
        serveurSocket = new ServerSocket(5009);
        clientSocket = serveurSocket.accept();
        System.out.println("connected");
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
        out.println(sv.Playlistavailable());
        out.println("music available");
        out.println("update done");

        Thread envoi= new Thread(new Runnable() {
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
   
   
      
      
      public List<String> Playlistavailable(){   //methode des playlist disponible

         List<String> list1 = new ArrayList<String>(); 
         list1.add("Alone Again - The Weeknd "); 
         list1.add("Too Late - The Weeknd "); 
         list1.add("Hardest to Love - The Weeknd ");
         list1.add("Scared to Live - The Weeknd ");
         list1.add("Snowchild - The Weeknd ");
         list1.add("Escape from LA- The Weeknd ");
         list1.add("Heartless -The Weeknd ");
         list1.add("Faith - The Weeknd ");
         list1.add("Blinding Lights - The Weeknd ");
         list1.add("In Your Eyes - The Weeknd ");
         list1.add("Save Your Tears - The Weeknd ");
         return list1;
                            
                   
       }

       public static BufferedReader startConsoleBuffer(){
         InputStreamReader consoleISR = new InputStreamReader(System.in);
         BufferedReader consoleBR = new BufferedReader(consoleISR);
         return consoleBR;
      }//fin de la methode startConsoleBuffer
   
      public static BufferedReader startFileReader() throws FileNotFoundException{
         FileReader fr = new FileReader("Songtest.txt");
         BufferedReader textBR = new BufferedReader(fr);
         return textBR;
      }// fin de la methode startFileReader


      public static void ShowMenu(){  //methode pour affiche le menu
        
         System.out.println("1 - Add song data");
         System.out.println("2 - List all songs");
         System.out.println("3 - Search for song by Artist");
         System.out.println("4 - Search for song by Album");
         System.out.println("5 - Quit the Program");
   }
}









       
    
     
    

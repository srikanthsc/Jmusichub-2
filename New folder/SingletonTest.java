 class Singleton {
   private static Singleton singleton = new Singleton();
  /* Un Constructeur privé empêche l'instanciation.*/
   private Singleton() { }
   /* Méthode statique */
   public static Singleton getInstance() {
      return singleton;
   }
   /* Autres méthodes */
   protected static void show() {
      System.out.println("Welcome to the JMusicHub server!");
   }
} 

public class SingletonTest {
   public static void main(String[] args) {
      Singleton s = Singleton.getInstance( );
      s.show( );
   }
} 
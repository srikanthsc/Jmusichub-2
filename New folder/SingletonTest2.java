class Singleton2 {
    private static Singleton2 singleton2 = new Singleton2();
   /* Un Constructeur privé empêche l'instanciation.*/
    private Singleton2() { }
    /* Méthode statique */
    public static Singleton2 getInstance() {
       return singleton2;
    }
    /* Autres méthodes */
    protected static void show1() {
       System.out.println("Enjoy the service!");
    }
 } 
 
 public class SingletonTest2 {
    public static void main(String[] args) {
       Singleton2 s1 = Singleton2.getInstance( );
       s1.show1( );
    }
 } 
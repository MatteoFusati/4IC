import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Bus extends Thread{
private Random random=new Random();
private int Posti=10;
private Semaphore semaforo;
private int fermate=5;
private int fermataControllore;
private int passScend;
private int passSal;

public Bus(){
	semaforo=new Semaphore(Posti);
}
@Override
   public void run(){
   fermataControllore=random.nextInt(fermate-1)+1; //Random che genera la fermata a cui salirà il controllore
   System.out.println("Benvenuti a bordo!");
   for(int i=1;i<=fermate;i++){
	   System.out.println("Fermata: "+i);
   
   //Scendono un numero di passeggeri compreso tra due e sei;
   passScend=random.nextInt((Posti/2)+2);
   for(int s=0;s<passScend;s++){
   if(semaforo.availablePermits()<Posti){
   System.out.println("Sceso passeggero");
   semaforo.release();
   }
   }
   
   //Se non è l'ultima fermata salgono passeggeri (Sempre tra due e sei)
   if(i<fermate){
   passSal=random.nextInt((Posti/2)+2);
   for(int f=0; f<passSal; f++){
    try{
   semaforo.acquire();
   System.out.println("Salito passeggero");
    }catch (InterruptedException ex) {}
   }
   }
   
   //Capolinea
   if(i==fermate){
	   System.out.println("Capolinea! Grazie per aver viaggiato con noi!");
   while(semaforo.availablePermits() != 10){
   System.out.println("Sceso Passeggero");
   semaforo.release();
   }
    currentThread().interrupt();
   }
   //Controllore
   if(i==fermataControllore){
   Thread controllore = new Thread(new Controllore(semaforo.availablePermits()));
   controllore.start();
   }
   
   try{
   sleep(10000); //Tempo fermata
} catch(InterruptedException e) {}
}
}
}

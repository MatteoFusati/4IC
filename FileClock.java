import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FileClock implements Runnable {
    
    Thread[] threads;
    int i;
    Random rand = new Random();
    Thread interrupt;
    
    public FileClock(Thread[] vector, int position) {
        threads = vector;
        i=position;
    }
       
    public void run() {
        while(true) {
            System.out.print(i+". "+ new Date()+"\n");
            try {
                TimeUnit.SECONDS.sleep(1);
                interrupt = threads[new Random().nextInt(threads.length)];
            } catch (InterruptedException e) {
                System.out.println("Thread n. "+i+" interrupted");
                rimuoviThread();
                return;
            }
            interrompi();
        }
    }
	private void rimuoviThread(){
		Thread[] v = new Thread[threads.length-1];
        for (int i = 0; i < v.length; i++) {
            if(threads[i]!= Thread.currentThread()){
                v[i] = threads[i];
            }
        }
        threads=v;	
	}
    
    private void interrompi() {
        if(Ultimo()){
            Thread.currentThread().interrupt();
        }
        else{
            interrupt.interrupt();
        }
    }
    
    
    private boolean Ultimo() {
        for (int i = 0; i < threads.length; i++) {
            if(threads[i].isAlive() && threads[i]!=Thread.currentThread()){
                return false;
            }
        }
        return true;
    }
    
    
    
}

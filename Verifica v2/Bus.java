import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

public class Bus{
	public static final int NUMERO_POSTI = 10;
	private Lock sezioneCritica;
	private Semaphore postiLiberi;
	private Semaphore postiOccupati;
	private Passeggero[] passeggeri;
	private int stop;
	private String nome;
	
	public Bus(String nome){
		postiLiberi = new Semaphore(NUMERO_POSTI);
		postiOccupati = new Semaphore(0);
		sezioneCritica = new ReentrantLock();
		passeggeri = new Passeggero[NUMERO_POSTI];
		int stop = -1;
		this.nome = nome;
	}
	public int getStop(){
		return stop;
	}
	public boolean sale(Passeggero passeggero){
		int i;
	    boolean salito=false;
		sezioneCritica.lock();
		if(postiLiberi.tryAcquire()){
		    salito=true;
		    passeggero.setBus(this);
            for(i=0; passeggeri[i] != null; i++);
            passeggeri[i] = passeggero;
			postiOccupati.release();
        }
        sezioneCritica.unlock();
		return salito;
	}
	public void scende(Passeggero passeggero){
		int i;
		sezioneCritica.lock();
			postiOccupati.tryAcquire();
			for(i=0; passeggeri[i].getName()!=passeggero.getName(); i++);
			passeggeri[i] = null;
			postiLiberi.release();
			passeggero.setBus(null);
		sezioneCritica.unlock();
	}
	
	public void fermata(){
		stopNumber++;
		for(int i=0; i<NUMERO_POSTI; i++){
			if(passeggeri[i] !=  null) passeggeri[i].interrupt();
		}
	}
}

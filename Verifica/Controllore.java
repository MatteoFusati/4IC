public class Controllore extends Thread{ 

private int postiLiberi;
private int passeggeri;

public Controllore(int postiLiberi){
	this.postiLiberi=postiLiberi;
}

@Override
   public void run(){
   passeggeri=10-postiLiberi;
   System.out.println("E' salito il controllore!");
       System.out.println("Biglietti Grazie!");
       System.out.println("Sull'autobus ci sono "+passeggeri+" passeggeri");
        }

}

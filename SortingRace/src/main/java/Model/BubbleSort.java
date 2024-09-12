
package Model;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * An implementation of bubble sort using threads
 * @author g56035
 */
public class BubbleSort implements Runnable{
    
    private int operations = 0;
    private int[] tab;
    private LocalDateTime time;
    private int duration;
    private Model model;
    BubbleSort(int[] tab, Model model){
        this.tab = tab;
        this.model = model;
        
    }
    
    public int[] sort(int[] tab){
        this.time = LocalDateTime.now();
        int i = 0;//1
        int taille = tab.length;//2
        this.operations+=2;
        while (i < taille - 1) {
            for (int j = 1; j < taille - i; j++) {
                if (tab[j - 1] > tab[j]) {
                    swap(i,j,tab);
                    this.operations+=6;
                }
            }
            i++;
        }
        return tab;
    }
    
    public void swap(int i, int j, int[] tab){
        
        int temp = tab[j - 1];
        tab[j - 1] = tab[j];
        tab[j] = temp;
    }
    
    @Override
    public void run(){
        sort(this.tab);
        this.duration = Duration.between(time, LocalDateTime.now()).toMillisPart();
        ending();
        
        
    }
    public void ending(){
        this.model.setDuree(this.duration);
        this.model.setOperations(operations);
    }
    
    
    public int getOperation(){
        return this.operations;
    }
    
    public int getDuration(){
        return this.duration;
    }
    
}

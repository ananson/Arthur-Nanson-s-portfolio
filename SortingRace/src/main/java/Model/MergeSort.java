
package Model;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * An implementation of merge sort using threads
 * @author g56035
 */
public class MergeSort extends Thread{
    
    int operations = 0;
    int[] tab;
    private LocalDateTime time;
    private int duration;
    private Model model;
    
    MergeSort(int[] tab, Model model){
        this.tab = tab;
        this.model = model;
        
    }
    
    public int[] sort(int[] tab, int n){
        
        if (n < 2) {
            this.operations++;
            return tab;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        this.operations+=3;

        for (int i = 0; i < mid; i++) {
            l[i] = tab[i];
            this.operations+=3;
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = tab[i];
            this.operations+=3;
        }
        sort(l, mid);
        sort(r, n - mid);

        this.operations+=merge(tab, l, r, mid, n - mid);
        return tab;
    }
    
    public static int merge(int[] a, int[] l, int[] r, int left, int right) {
 
        int i = 0, j = 0, k = 0;
        int ops = 3;
        
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
                ops++;
                
                
            }
            else {
                a[k++] = r[j++];
            }
            ops+=2;
        }
        while (i < left) {
            a[k++] = l[i++];
            ops+=2;
        }
        while (j < right) {
            a[k++] = r[j++];
            ops+=2;
        }
        return ops;
    }
    
    @Override
    public void run(){
        this.time = LocalDateTime.now();
        this.sort(tab, tab.length);
        this.duration = Duration.between(time, LocalDateTime.now()).toMillisPart();
        ending();

        
    }
    
    public int getOperation(){
        return this.operations;
    }
    
    public int getDuration(){
        return this.duration;
    }
    public void ending(){
        this.model.setDuree(this.duration);
        this.model.setOperations(operations);
    }
}

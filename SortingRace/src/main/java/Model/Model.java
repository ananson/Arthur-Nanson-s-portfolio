
package Model;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import View.FxmlController;
import java.sql.Timestamp;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Takes the place of the repository, asks the dao the informations and puts it in the Dtos
 * @author g56035
 */
public class Model {
    private int threads;
    private int numbers;
    private List<FxmlController> observers;

    Random random;
    private List<Integer> durees;
    private List<Integer> operations;
    private Logs logs;
    public Model(){
        

        this.random = new Random();
        this.observers = new ArrayList();
        this.logs = new Logs();
        
    }
    
    /**
     * Gets all data given and calls functions
     * @param thread
     * @param number
     * @param sort 
     */
    public void start(int thread, int number, String sort){
        this.durees = new ArrayList();
        this.operations = new ArrayList();
        this.threads = thread;
        this.numbers = number;
        int[] divised = diviseNumber(number);
        
        ExecutorService pool = Executors.newFixedThreadPool(thread);
        
        if(sort=="Bubble sort"){
            
            BubbleSort[] threadsArray = new BubbleSort[10];
            for(int i = 0; i<10;i++){
                BubbleSort t = new BubbleSort(generateRandom(divised[i]), this);
                threadsArray[i] = t;
            }
            for(int i = 0; i<10;i++){
                pool.execute(threadsArray[i]);
            }
        }
        else if(sort=="Merge sort"){
            
            MergeSort[] threadsArray = new MergeSort[10];
            for(int i = 0; i<10;i++){
                MergeSort t = new MergeSort(generateRandom(divised[i]), this);
                threadsArray[i] = t;
            }
            for(int i = 0; i<10;i++){
                pool.execute(threadsArray[i]);
            }
        }
        
        Date date = new Date();
        this.logs.addToDB(new Timestamp(date.getTime()), sort, number);// adding the new sort to the DB
        
        
        


        while(this.operations.size()<10 && this.durees.size()<10){
            try
            {
                notifyObserversProgress();
                Thread.sleep(0);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        pool.shutdown();
        notifyObservers(this.operations, this.durees, sort, divised);
    }
    
    public int[] diviseNumber(int number){
        
        int[] divised = new int[10];
        int divisedTen = number/10;
        for(int i = 1; i<=10;i++){
            divised[i-1] = i*divisedTen;
        }
        return divised;
    }
    
    public int[] generateRandom(int taille){
        
        int[] result = new int[taille];
        for(int i=0; i<taille; i++){
            result[i] = this.random.nextInt(taille);
        }
        return result;
    }
    
    public void subscribe(FxmlController subscriber){
        this.observers.add(subscriber);
    }
    
    public void notifyObservers(List<Integer> operations, List<Integer> duree, String tri, int[] taille){
        for(FxmlController subs : this.observers){
            subs.update(operations, duree, tri, taille);
        }
    }
    
    public void setOperations(int op){
        this.operations.add(op);
    }
    public void setDuree(int duree){
        this.durees.add(duree);
    }
    
    public void notifyObserversProgress(){
        for(FxmlController subs : this.observers){
            subs.updateProgress(operations.size());
        }
    }
    /**
     * Gives the logs taken from the DB by the DAO
     * @return 
     */
    public List<LogsDto> getLogs(){
        return logs.getLogs();
    }
}

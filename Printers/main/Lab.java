package main.java;
import java.io.File;
import main.resources.Queue; 
import java.util.Scanner;
import java.io.FileNotFoundException;
import main.resources.LinkedQueue;

public class Lab {
    private final Printer[] lab; 
    private Scanner sc;
    private StringBuilder sb;
    private Queue<Job> jobs;
    
    

    public static final String EOL = System.lineSeparator();

    /**
     * 
     * @param inputFile
     * @param sb
     */
    public Lab(String inputFile, StringBuilder sb) throws FileNotFoundException {
          this.sb = sb;
          this.jobs = new LinkedQueue<>();
          
          File file = new File(inputFile);
          sc = new Scanner(file);
          
          lab = new  Printer[Integer.parseInt(sc.next())];
          
          for(int i=0; i<lab.length;i++) {
              lab[i] = new Printer(i, sb);
          }
          sc.close();
        
          
          
    }
    /**
     * 
     * @param inputFile
     * @throws FileNotFoundException
     */
    public void takeOrders(String inputFile) throws FileNotFoundException {
        File file = new File(inputFile);
        sc = new Scanner(file);
        
        while(sc.hasNext()) {
            
            int arrivalTime = Integer.parseInt(sc.next());
            sc.next();
            String modelName =  sc.next();
            int printTime = Integer.parseInt(sc.next());
            int numberCopies = Integer.parseInt(sc.next());
            Priority priority = Priority.valueOf(sc.next());
            
            Job job = new Job(modelName, printTime, numberCopies, arrivalTime, priority);
            
             this.jobs.enqueue(job);
             processJob(job);           //processa todos com arrival time = 0
                       
        }
        
        boolean done = false;
       while(lab[0].currentTime()==0 || !isPrintingFinished()){
           
           if(this.jobs.size()==0 && !done) {
               sb.append("All print jobs have been assigned to a printer!");
               sb.append("\n");
               done = true;
               }  
           
           for(Printer printer : lab) {
                printer.printForDuration(1);
            }
            for(int i=0;i<jobs.size(); i++) {
                processJob(jobs.front());
            }
            
            
          
        }
        
        sb.append("All printing jobs have been processed!");
        
        
        
        
        
    }

    /**
     * 
     * @param job
     */
    public void processJob(Job job) {
        
        
        int i;
        if(job.getPriority()==Priority.HIGH) {
            i=firstPrinterToFinishHigh();
            
        }
        else {
            i=firstPrinterToFinish();   
        }
        
        if(job.getArrivalTime() == lab[i].currentTime()) {
            lab[i].addJob(job);
             this.jobs.dequeue();
        }
        
        
        
    }

    /**
     * 
     * @return
     */
    public int firstPrinterToFinish() {
        int first = 0;
        for(int i=0; i<lab.length; i++) {
            if(lab[i].isEmpty()) {
                return i;
            }
            else {
                if(lab[first].timeToFinishHigh()+lab[first].timeToFinishNormal() > 
                lab[i].timeToFinishHigh()+lab[i].timeToFinishNormal()) {
                    first = i;
                }
            }
        }
        return first;
    }

    /**
     * 
     * @return
     */
    public int firstPrinterToFinishHigh() {
        int first = 0;
        for(int i=0; i<lab.length; i++ ) {
           if(lab[first].timeToFinishHigh() > lab[i].timeToFinishHigh() ) {
                    first = i;
            }
           else if(lab[first].timeToFinishHigh() == lab[i].timeToFinishHigh()) {
               if(lab[first].timeToFinishNormal()> lab[i].timeToFinishNormal()){
                   first = i;
               }
           }
            
        }
        return first;
    }

    /**
     * 
     * @return
     */
    public int firstPrinterToFinishJob() {
        int first = 0;
        for(int i=0; i<lab.length; i++) {
            if(lab[0].isEmpty() || !(lab[0].currentJob().getStatus() != Status.PRINTING )) {
                return i;
            }
            else {
                if(lab[first].currentJob().getPrintingDuration()> lab[i].currentJob().getPrintingDuration()) {
                    first = i;
                }
            }
        }
        return first;
    }

    /**
     * 
     * @return
     */
    public boolean isPrintingFinished() {
        for(Printer printer: lab) {
            if(!printer.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

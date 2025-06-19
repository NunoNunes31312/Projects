package types;



import java.util.Iterator;

public class Tower {

    public static final String EOL = System.lineSeparator(); 
    public static final int DEFAULT_HEIGHT = 8; 
    private final int size;
    private ArrayStack<Disk> torre; 
    private int numberOfDisks;

    /**
     * 
     */
    public Tower() {
        this.torre = new ArrayStack<Disk>();
        size = DEFAULT_HEIGHT;
        numberOfDisks = 0;
    }

    /**
     * 
     * @param height
     */
    public Tower(int height) {
        this.torre = new ArrayStack<Disk>();
        size = height;
        numberOfDisks = 0;

    }

    /**
     * @param d
     * @requires isValidMove(disk)
     */
    public void addToTower(Disk d) {
        torre.push(d);
        numberOfDisks++;
    }

    /**
     * 
     * @return
     */
    public boolean isFull() {
        return numberOfDisks==size;
    }

    /**
     * @requires !isEmpty()
     */
    public void removeFromTower() {
        torre.pop();
        numberOfDisks--;

    }

    /**
     * 
     * @param d
     * @return
     */
    public boolean isValidMove(Disk d) {
        return (isEmpty() || !isFull() && viewTopDisk().isLargerThan(d) );
    }

    /**
     * 
     * @return
     */
    public Disk viewTopDisk() {
        
            return (Disk) torre.peek();
        
        
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
        return numberOfDisks==0;
    }

    /**
     * 
     * @return
     */
    public int height() {
        return size;
    }

    /**
     * 
     * @return
     */
    public int numberOfDisks() {
        return numberOfDisks;
    }
    
    public ArrayStack<Disk> getTower() {
        return torre;
    }
    

    @Override
    public String toString() {
        Iterator<Disk> iterador = torre.iterator();
        StringBuilder sb = new StringBuilder();

            for(int i=0; i<size; i++ ) {
                if(i>size-numberOfDisks-1) {
                    sb.append(iterador.next());
            }
            else {
                sb.append("|");
            }
                    sb.append(EOL);
                    
        }
        sb.append("_");

        return sb.toString();
    }
    
}




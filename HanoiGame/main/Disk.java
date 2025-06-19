package types;

public class Disk {
	private final int size;
    /**
     * @param size
     * @requires size>=1 && size<=26
     */
    public Disk(int size) {
    	this.size = size;
    }
    

    /**
     * 
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 
     * @param other
     * @return
     */
    public boolean isLargerThan(Disk other) {
    	
        return this.size>other.getSize();
    }

    @Override
    public String toString() {
        
    	char letra = (char) (size+64);
    	
        return Character.toString(letra);
    }
}

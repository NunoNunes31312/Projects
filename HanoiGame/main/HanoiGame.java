package types;

import java.util.Iterator;

public class HanoiGame {

    public static final String EOL = System.lineSeparator(); 

    private final int numberOfRods;
    private final int numberOfDisks;
    private Tower[] rods;
    private int numberOfMoves;
    
    /**
     * 
     * @param numberOfRods
     * @param numberOfDisks
     * @requires numberOfDisks entre 3 e 26
     * @requires numberOfRods minimo 3 torres
     */
    public HanoiGame(int numberOfRods, int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
        this.numberOfRods = numberOfRods;
        
        rods = new Tower[numberOfRods];
        
        for(int i=0; i<numberOfRods; i++) {          
            rods[i] = new Tower(numberOfDisks); //cria n rods null max tamanho 8
        }
       
        for(int i=numberOfDisks; i>0; i--) {
         
            rods[0].addToTower(new Disk (i));
        }
    }

    /**
     * 
     * @param from
     * @param to
     */
    public void play(int from, int to) {//n meti is valid pq ta no requires
        if(!isTerminated()) {
            rods[to].addToTower(rods[from].viewTopDisk());
            rods[from].removeFromTower();
            numberOfMoves++;
        }
    }

    /**
     * 
     * @return
     */
    public boolean isTerminated() {
        for(int i=1; i<numberOfRods;i++) {
            if(rods[i].numberOfDisks()==numberOfDisks) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public int numberOfMoves() {
        return numberOfMoves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        
            sb.append("Moves: "+numberOfMoves);
            sb.append(EOL);
            
            
            for (int i = 0; i < numberOfDisks; i++) {  // Começa do topo e vai até a base
                for (int j = 0; j < numberOfRods; j++) {
                    // Acessar a pilha de discos da torre atual
                    ArrayStack<Disk> tower = rods[j].getTower();  // Presumindo que getTower() retorna a pilha de discos
                    Iterator<Disk> iterador = tower.iterator();  // Cria o iterador para a torre
                    String disk = "|";  // Representação de uma torre vazia

                    // Iterar até o nível atual e pegar o disco
                    
                    int currentLevel =  numberOfDisks -rods[j].numberOfDisks();
                    while (iterador.hasNext() && currentLevel < (i)  ) {
                        iterador.next();  // Ignora os discos acima
                        currentLevel++;
                    }

                    // Agora, no nível desejado
                    if (iterador.hasNext() && i> numberOfDisks-1 -rods[j].numberOfDisks()  ) {
                        disk = iterador.next().toString();  // Representa o disco no nível atual
                    }

                    sb.append("  "+disk+"  ");  // Adiciona a representação da torre ao StringBuilder
                      // Espaço maior entre as torres para mantê-las mais separadas
                }
                sb.append(EOL);  // Nova linha após imprimir um nível
            }
           // for(int i=0; i<numberOfDisks; i++) {
          //      sb.append(rods[i]);
          //  }
            
            sb.append("---------------");
        
        
        
        return sb.toString();
    }
}














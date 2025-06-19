package types;

public class Main {
    public static final String EOL = System.lineSeparator();

    
    public static void main(String[] args) {
        
        
      
        HanoiGame game = new HanoiGame(3, 3);
        System.out.println(game.toString() + EOL);

        int[][] moves = {{0,2}, {0,1}, {2,1}, {0,2}, {1,0}, {1,2}, {0,2}};

        for(int[] elem : moves) {
            game.play(elem[0], elem[1]);
            System.out.println(game.toString() + EOL);
        }
    }
}

package main.java;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    public static final String EOL = System.lineSeparator();

    public static void main(String[] args) throws FileNotFoundException {
        Main main = new Main();
        for(int exec = 1; exec <= 5; exec++) {
            main.execute(exec);
        }
    }

    public void execute(int exec) throws FileNotFoundException {

        try (PrintWriter writer = new PrintWriter(String.format("./src/test/resources/output/output%d.txt", exec))) {
            StringBuilder sb = new StringBuilder();
            Lab lab = new Lab(String.format("./src/test/resources/input/printers/input%d.txt", exec), sb);
            lab.takeOrders(String.format("./src/test/resources/input/orders/input%d.txt", exec));
            //System.out.println(sb.toString());
            writeToFile(writer, sb.toString(), exec, exec);
        }
    }

    private static void writeToFile(PrintWriter writer, String log, int store, int events) {
        writer.print(log);
    }
}

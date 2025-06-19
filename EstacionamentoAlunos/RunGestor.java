import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class RunGestor {
    private static final String SEPARADOR = "-";
    private static Gestor gestor;
    
    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        String[] split = input.split("_|\\.");
        int nLugares = Integer.parseInt(split[1]);
        int escaloes = Integer.parseInt(split[2]);
        int estrategiaAtribuicao = Integer.parseInt(split[3]);
        
       System.out.println(nLugares+" "+escaloes+" "+estrategiaAtribuicao);
        
        


        PrintStream stream = new PrintStream(new FileOutputStream(String.format("./output/output_%d_%d_%d.txt",
                nLugares, escaloes, estrategiaAtribuicao)));
        

        System.setOut(stream);
        
       

        gestor = new Gestor(escaloes, nLugares, estrategiaAtribuicao);
        
        Scanner scanner = new Scanner(new FileReader(String.format("./input/"+input)));
        while (scanner.hasNextLine()) {
            String comando = scanner.nextLine();
            /* o ficheiro de entrada contem linhas de comentario e linhas com comandos;
             * as linhas de comentario começam por "-"
             */
            
            if (!comando.startsWith("-")) {
                
                gerirGaragem(comando);
            }
        }
        
        scanner.close();
    }

    private static void gerirGaragem(String comando) {
        String[] itens = comando.split(SEPARADOR);
        switch (itens[0]) {
        case "total":
            calcularTotal(itens);
            
            break;
        case "registar":
            
            concretizarRegisto(itens[1], Integer.valueOf(itens[2]));
            break;
        case "atribuir":  
            concretizarAtribuicao(itens[1]);
            break;
        case "obterDono":
            obterDono(Integer.parseInt(itens[1]));
            break;
        case "obterNumero":
            obterNumero(itens[1]);
            break;
        case "removerPorNome":
            removerPorNome(itens[1]);
            break;
        case "removerPorNumero":
            removerPorNumero(Integer.valueOf(itens[1]));
            break;
        default:
            System.out.println("OPCAO INVALIDA: " + comando);
            break;
        }
    }

    // Calcula o total de lugares atribuidos
    private static void calcularTotal(String[] campos) {
        if (campos.length == 1) {
            System.out.println("TOTAL de lugares atribuidos: " + gestor.totalAtribuidos());
        }
        else {
            int escalao = Integer.valueOf(campos[1]);
            System.out.println("TOTAL de lugares atribuidos no escalao " + escalao + " : " + gestor.atribuidosNoEscalao(escalao));
        }
    }

    // Tenta registar um novo funcionario
    private static void concretizarRegisto(String nome, int escalao) {
        if (gestor.registar(nome, escalao)) {
            System.out.println("REGISTO do funcionario " + nome + " com escalao " + escalao);
        }
        else { 
            System.out.println("ERRO: Nao foi possivel registar o funcionario " + nome);
        }
    }

    // Tenta atribuir um lugar ao funcionario com o nome dado
    private static void concretizarAtribuicao(String nome) {
        if (gestor.atribuir(nome)) {
            System.out.println("ATRIBUICAO de lugar a " + nome);
        }
        else {
            System.out.println("ERRO: Nao foi possivel atribuir lugar a " + nome);
        }
    }

    // Tenta determinar o dono do lugar com o numero dado
    private static void obterDono(int numero) {
        Funcionario funcionario = gestor.obterDono(numero);
        if (funcionario != null) {
            System.out.println("O DONO do lugar nr." + numero + ": " + funcionario.obterNome());
        }
        else {
            System.out.println("LUGAR de estacionamento nr." + numero + " SEM DONO atribuido");
        }

    }

    // Tenta determinar o numero do lugar atribuido ao funcionario com o nome dado
    private static void obterNumero(String nome) {
        int numero = gestor.obterNumero(nome);
        if (numero > 0) {
            System.out.println("O FUNCIONARIO " + nome + " tem LUGAR nr. " + numero);
        }
        else {
            System.out.println("NAO EXISTE LUGAR de estacionamento atribuido a " + nome);
        }
    }

    // Tenta remover uma atribução dado o nome do funcionario
    private static void removerPorNome(String nome) {
        Lugar lugar = gestor.removerAtribuicaoPorNome(nome);
        if (lugar != null) {
            System.out.println("REMOCAO (nome) lugar " + lugar.obterNumero() + ", escalao " + lugar.obterEscalao() + ", de " + nome);
        }
        else {
            System.out.println("Pessoa " + nome + " nao tem lugar atribuido");
        }
    }

    // Tenta remover uma atribução dado o numero de um lugar
    private static void removerPorNumero(int numero) {
        Lugar lugar = gestor.removerAtribuicaoPorNumero(numero);
        if (lugar != null) {
            System.out.println("REMOCAO (nr.) lugar " + lugar.obterNumero() + ", escalao " + lugar.obterEscalao());
        }
        else {
            System.out.println("Lugar " + numero + " nao atribuido");
        }
    }

}

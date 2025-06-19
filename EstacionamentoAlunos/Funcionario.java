import java.util.Objects;

public class Funcionario {
    
    private final String nome;
    private final int escalao;
    
    
    
    /**
     * 
     * @param nome
     * @param escalao 
     */
    public Funcionario(String nome, int escalao) {
        this.nome = nome;
        this.escalao = escalao;
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(escalao, nome);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Funcionario other = (Funcionario) obj;
        return escalao == other.escalao && Objects.equals(nome, other.nome);
    }        
        
    
    public int obterEscalao() {
        return escalao;
    }
    
    public String obterNome() {
        return nome;
    }
    
    
}





public class Gestor {
    private final int nrEscaloes;
    private final int nrLugares;
    private final int estrategia;
    private int[] atribuidos;   //nº de de lugares atribuidos a funcionarios de cada escalao 
    private int[] escaloes;//nº de lugares atribuidos pra cada escalao
    
    
    
   
    
    private BidirectionalHashMap<Funcionario, Lugar> hashMap;
    private BidirectionalHashMap<String, Funcionario> nomes;
    private BidirectionalHashMap<Integer, Lugar> lugares;
    
    
    /**
     * 
     * @param nrEscaloes
     * @param nrLugares
     * @param estrategia
     */
    public Gestor(int nrEscaloes, int nrLugares, int estrategia) {
        this.nrEscaloes = nrEscaloes;
        this.nrLugares = nrLugares;
        this.estrategia = estrategia;
        
         hashMap = new BidirectionalHashMap<>();
        //                   < Key , Value >
      nomes = new BidirectionalHashMap<>();
      
      lugares = new BidirectionalHashMap<>();
      
        
        atribuidos = new int[nrEscaloes];
        escaloes = new int[nrEscaloes];
        
        int count = 1;
        for(int i=nrEscaloes; i>0; i--) {   
            atribuidos[i-1] = 0;   
            escaloes[i-1] = 0;
            
            
            
            for(int j=1; j<nrLugares/nrEscaloes +1; j++) {    
                Lugar lugar = new Lugar(count ,i);   
                lugares.put(count, lugar);   
                count++;
            }
        }
        
        
      
        
        
        
        
        
    }

    
    public int totalAtribuidos() {
        return hashMap.size();
    }
    
    /**
     * 
     * @param escalao
     * @requires escalao >=1 && <= nrEscaloes
     * @return devolve o número de lugares para um determinado escalao
     */
    
    public int atribuidosNoEscalao(int escalao) {//“Total de lugares atribuídos a funcionários no escalão X”
                                                                 
                                       
        
            return atribuidos[escalao-1];
        
    }
    
    /**
     * 
     * @param lugar
     * @return
     */
    public Funcionario obterDono(int l) {
        Lugar lugar = lugares.getValue(l);
        
        return hashMap.getKey(lugar);
        
    }
    
    /**
     * 
     * @param nome
     * @return
     * 
     */
    public int obterNumero(String nome) {
                
        Funcionario f = nomes.getValue(nome);
        
        if(!hashMap.containsKey(f)) {
            return 0;
        }
        return hashMap.getValue(f).obterNumero();
        
    }
    
    
    /**
     * 
     * @param nome
     */
    public Lugar removerAtribuicaoPorNome(String nome) {   
        Funcionario f = nomes.getValue(nome);
        if(!hashMap.containsKey(f)) {
            return null;
        }
        
        Lugar lugar = hashMap.getValue(f);
        
        escaloes[lugar.obterEscalao()-1]--;
        atribuidos[f.obterEscalao()-1]--;
        
          hashMap.removeByKey(f);
            
          return lugar;
        
        
    }
    
    public Lugar removerAtribuicaoPorNumero(int l) {
       
        Lugar lugar = lugares.getValue(l);
        if(!hashMap.containsValue(lugar)) {
            return null;
        }
       Funcionario f = hashMap.getKey(lugar);
               
        atribuidos[f.obterEscalao()-1]--;
        escaloes[lugar.obterEscalao()-1]--;
        
         hashMap.removeByValue(lugar);
         
         return lugar;
            
        
    }
    
    public boolean registar(String nome, int escalao) {
        
        Funcionario f = new Funcionario(nome, escalao);
        if( nomes.containsValue(f) || nomes.containsKey(nome) ) {
            return false;
        }
        nomes.put(nome, f);
        
        
        return true;
        
        
    }
    
    public boolean atribuir(String nome) {
        //ver qual é o espaço vago, tbm depende da estrategia
        
        
        Lugar lugar = null;
        Funcionario f = nomes.getValue(nome);
        if(hashMap.containsKey(f)) {
            return false;
        }
        
        int escalao = f.obterEscalao();
        if(estrategia == 1) {
           if(escaloes[escalao-1] != nrLugares/nrEscaloes) {
               lugar = lugarLivre(escalao);
                              
           }
           
        }
        else {//estratégia 2                //estrategia dinamica      
          
             boolean atribuido = false;        
             lugar = lugarLivre(escalao);
             
             if(lugar == null) {
                 for(int i = 1; i<escalao && !atribuido; i++ ) {
                     lugar = lugarLivre(i);
                     if(lugar!= null) {
                         atribuido = true;
                     }
                 }
             }
              
                
                
                
                
        }
        if(lugar== null) {
            return false;
        }
        
        atribuidos[f.obterEscalao()-1]++;        
        escaloes[lugar.obterEscalao()-1]++;
        
        hashMap.put(f, lugar);
        
        return true;
        
    }
    
    
       private Lugar lugarLivre(int escalao) {
            int lugaresPorEscalao = nrLugares/nrEscaloes;
            
            int inicio = lugaresPorEscalao * (nrEscaloes-escalao) +1;
            int fim = inicio + lugaresPorEscalao -1;
            
            
            for(int i = inicio; i<=fim; i++) {
                Lugar pos = lugares.getValue(i);
                
                if( !hashMap.containsValue(pos)) {
                    return pos;
                }
            }
            return null;
            
        }
    

    
    
    
    
    
    
    
    
}
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public Random rand;
    private int numGeracao;
    private int numeroIdividuos;
    public int tamanhoPopulacao = 4;
    public int maximoGeracao = 40;
    private boolean encontrou;
    ArrayList<Individuo> populacao = new ArrayList<>();
    ArrayList<Individuo> populacaoAux = new ArrayList<>();
    
    public Main(){
        rand = new Random();
        gerarPopInicial();
        numGeracao = 0;
        numeroIdividuos = 0;
        encontrou = false;
        
    }

    public void executar(){
        System.out.println("================ População inicial ===================");
        for(int i = 0; i < tamanhoPopulacao; i++){
            System.out.println("Individuo numero: "+(i+1));
            System.out.println("Cromossomo individuo = "+populacao.get(i).getCromossomo());
            System.out.println("Apitidão individuo   = "+populacao.get(i).aptidao());
            System.out.println("Valor  do cromossomo = "+populacao.get(i).valorCromossomo());
            System.out.println();
        }
        while(numGeracao < maximoGeracao && !encontrou){
            for(int i = 0; i < (tamanhoPopulacao/2); i++){
                Individuo pai1;
                Individuo pai2;
                int index1 = rand.nextInt(populacao.size());
                int index2 = rand.nextInt(populacao.size());
                pai1  = melhorPai(index1, index2);
                index1 = rand.nextInt(populacao.size());
                index2 = rand.nextInt(populacao.size());
                pai2  = melhorPai(index1, index2);
                crossover(pai1, pai2);
            }
            populacao = populacaoAux;
            populacaoAux = new ArrayList<>();
            numGeracao = numGeracao+1;
            System.out.println("Geração: " + numGeracao+" ===========================================");
            for(int i = 0; i < tamanhoPopulacao; i++){
                System.out.println("Individuo numero: "+(i+1));
                System.out.println("Cromossomo individuo = "+populacao.get(i).getCromossomo());
                System.out.println("Apitidão individuo   = "+populacao.get(i).aptidao());
                System.out.println("Valor  do cromossomo = "+populacao.get(i).valorCromossomo());
                System.out.println();
            }
            verificarSeEncontrou();
        }
        System.out.println("Geração: " + numGeracao+" Numero de individuos: "+ numeroIdividuos);
    }

    public void gerarPopInicial(){
        Individuo novoIndividuo;
        for(int i = 0; i < tamanhoPopulacao; i++){
            novoIndividuo = new Individuo(geradorNumeroRandom());
            populacao.add(novoIndividuo);
        }
        numeroIdividuos = numeroIdividuos+tamanhoPopulacao;
    }

    public void crossover(Individuo pai1, Individuo pai2){
        int pontoDeCort = rand.nextInt(6);
        int taxaDeCrossover = rand.nextInt(100);
        String cromossomo1 ="";
        String cromossomo2= "";
        if(taxaDeCrossover > 29){
            for(int i = 0; i <= pontoDeCort; i++){
                cromossomo1 = cromossomo1 + pai1.getCromossomo().charAt(i);
            }
            for(int i = 0; i <= pontoDeCort; i++){
                cromossomo2 = cromossomo2 + pai2.getCromossomo().charAt(i);
            }
            for(int i = pontoDeCort + 1; i < 6; i++){
                cromossomo1 = cromossomo1 + pai2.getCromossomo().charAt(i);
            }
            for(int i = pontoDeCort + 1; i < 6; i++){
                cromossomo2 = cromossomo2 + pai1.getCromossomo().charAt(i);
            }
        }else{
            cromossomo1 = pai1.getCromossomo();
            cromossomo2 = pai2.getCromossomo();
        }
        //To do mutação
        cromossomo1 = mutacao(cromossomo1);
        cromossomo2 = mutacao(cromossomo2);
        System.out.println("Pais : " + pai1.getCromossomo() +" + "+ pai2.getCromossomo());
        int valor;
        valor = converterBinEmInt(cromossomo1);
        valor = intervalo(valor);
        Individuo novoFilho = new Individuo(valor);
        populacaoAux.add(novoFilho);
        numeroIdividuos = numeroIdividuos+1;
        System.out.println("Filho 1 " + novoFilho.getCromossomo());
        valor = converterBinEmInt(cromossomo2);
        valor = intervalo(valor);
        novoFilho = new Individuo(valor);
        populacaoAux.add(novoFilho);
        numeroIdividuos = numeroIdividuos+1;
        System.out.println("Filho 2 " + novoFilho.getCromossomo());
    }

    public Individuo melhorPai(int i1, int i2){
        Individuo pai1;
        Individuo pai2;
        pai1 = populacao.get(i1);
        pai2 = populacao.get(i2);
        if(pai1.aptidao() >= pai2.aptidao()){
            return populacao.get(i1);
        }else{
            return populacao.get(i2);
        }
    }
    public int converterBinEmInt(String cromossomo){
        int n = Integer.parseInt(cromossomo.substring(1),2);
        if(cromossomo.charAt(0) == '1'){
            n = n - (n*2);
        }
        return n;
    }

    public int geradorNumeroRandom(){
        int n = (rand.nextInt(21))-10;
        return n;
    }

    public int intervalo(int valor){
        if(valor > 10){
            return valor = 10;
        }else if ( valor < -10){
            return valor = -10;
        }
        return valor;
    }

    public String mutacao(String cromossomo){
        int taxaDeMutacao = 0;
        String novoCromossomo = "";
        for(int i = 0; i < cromossomo.length(); i++){
            
            taxaDeMutacao = rand.nextInt(100);
            if(taxaDeMutacao == 1){
                if(cromossomo.charAt(i) == '1'){
                    if(i == 0){
                        novoCromossomo = novoCromossomo + "0";
                        novoCromossomo = novoCromossomo + cromossomo.substring(i+1);
                    }else{
                        novoCromossomo = novoCromossomo + cromossomo.substring(0, i);
                        novoCromossomo = novoCromossomo + "0";
                        novoCromossomo = novoCromossomo + cromossomo.substring(i+1);
                    }
                }else{

                    if(i == 0){
                        novoCromossomo = novoCromossomo + "1";
                        novoCromossomo = novoCromossomo + cromossomo.substring(i+1);
                    }else{
                        novoCromossomo = novoCromossomo + cromossomo.substring(0, i);
                        novoCromossomo = novoCromossomo + "1";
                        novoCromossomo = novoCromossomo + cromossomo.substring(i+1);
                    }
                }
                cromossomo = novoCromossomo;
                novoCromossomo = "";
            }

        }
        return cromossomo;
    }
//verifica se encontrou quando se conhece o tamanho maximo da função
    public void verificarSeEncontrou(){
        for(int i = 0; i < tamanhoPopulacao; i++){
            if(populacao.get(i).aptidao() == 134){
                encontrou = true;
            }
        }
    }
}

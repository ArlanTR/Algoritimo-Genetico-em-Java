public class Individuo {
    private String cromossomo;
    public Individuo(int valor){
        this.cromossomo = converterBinario(valor);
    }

    public String getCromossomo(){
        return cromossomo;
    }

    public String converterBinario(int DNA){
        String aux = "";
        String aux2 = "";
        if(DNA >= 0){
            aux2 = aux2 + "0";
        }else{
            aux2 = aux2 + "1";
            DNA = DNA - (DNA*2);
        }
        aux = Integer.toBinaryString(DNA);
        aux = String.format("%5s", aux).replaceAll(" ", "0");
        aux2 = aux2 + aux;
        return aux2;
    }

    public int aptidao(){
        int x = valorCromossomo();
        return x*x-3*x+4;
    }

    public int valorCromossomo(){
        int valor;
        if(this.cromossomo.charAt(0) == '0'){
            valor = (Integer.parseInt(cromossomo.substring(1),2));
        }else{
            valor = (Integer.parseInt(cromossomo.substring(1),2));
            valor = valor - (valor*2);
        }
        return valor;
    }
}

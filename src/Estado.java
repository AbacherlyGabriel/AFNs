public class Estado {

    private int origem;
    private int simbolo;
    private int destino;
    //private boolean estadoDeAceitacao;

    public Estado(int origem, int simbolo, int destino /*, boolean estadoDeAceitacao */) {
        this.origem = origem;
        this.simbolo = simbolo;
        this.destino = destino;
        //this.estadoDeAceitacao = estadoDeAceitacao;
    }

    public int getOrigem() {
        return origem;
    }

    public int getSimbolo() {
        return simbolo;
    }

    public int getDestino() {
        return destino;
    }
}

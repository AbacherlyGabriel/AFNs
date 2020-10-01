public class Estado {

    private int simbolo;
    private int destino;
    //private boolean estadoDeAceitacao;

    public Estado(int simbolo, int destino /*, boolean estadoDeAceitacao */) {
        this.simbolo = simbolo;
        this.destino = destino;
        //this.estadoDeAceitacao = estadoDeAceitacao;
    }

    public int getSimbolo() {
        return simbolo;
    }

    public int getDestino() {
        return destino;
    }
}

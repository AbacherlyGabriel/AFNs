/*
Objeto utilizado para representar os estados e armazenar
as informacoes de transicoes e estados de destino
 */


public class Estado {

    private int simbolo;            // Simbolo presente na transicao entre o estado atual e seu destino
    private int destino;            // Estado alcancado apos a leitura do simbolo especificado acima

    /*
    Criacao das instancias do objeto
     */

    public Estado(int simbolo, int destino) {
        this.simbolo = simbolo;
        this.destino = destino;
    }

    /*
    Getter: Simbolo presente na transicao
     */

    public int getSimbolo() {
        return simbolo;
    }

    /*
    Getter: Estado destino
     */

    public int getDestino() {
        return destino;
    }
}

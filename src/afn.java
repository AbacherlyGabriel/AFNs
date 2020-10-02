/*

Gabriel Bispo Abacherly - 10284420


Classe responsável pela execução do programa
*/

import java.io.IOException;

public class afn {

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            ExecutaAFNs afn = new ExecutaAFNs(args[0], args[1]);

            afn.executaAFNs();
            afn.resultados("Testando Arquivo de Saida");
        }
        else {
            System.out.println("[!] O nome dos arquivos devem ser, respectivamente, arqTeste e arqSaida");
        }
    }

}

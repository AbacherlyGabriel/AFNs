/*

Gabriel Bispo Abacherly - 10284420


Classe responsável pela execução do programa
*/

import java.io.IOException;

public class afn {

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            Automatos afn = new Automatos(args[0], args[1]);

            afn.executaAFNs();
        }
        else {
            System.out.println("[!] O nome dos arquivos devem ser, respectivamente, arqTeste e arqSaida");
        }
    }

}

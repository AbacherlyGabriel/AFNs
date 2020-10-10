/*

Gabriel Bispo Abacherly - 10284420
Gregorio Assagra de Almedia Filho - 10856824

Classe responsável pela execução do programa
*/

public class afn {

    public static void main(String[] args) {
        if (args.length == 2) {

            // Instanciando objeto responsavel pela execucao do programa

            Automatos afn = new Automatos(args[0], args[1]);
            afn.executaAFNs();
        }
        else {
            System.out.println("[!] Deve-se passar dois argumentos de entrada (arquivos de teste e saida)");
        }
    }

}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class afn {

    static final String arqEntrada = "input" + File.separator + "ArqTeste.txt";
    static final String arqSaida = "output" + File.separator + "ArqSaida.txt";

    public static void main(String[] args) {
        try {
            FileReader arqLeitura = new FileReader(arqEntrada);
            BufferedReader leitorArq = new BufferedReader(arqLeitura);

            int numAFNs = Integer.parseInt(leitorArq.readLine());

            while (numAFNs > 0) {
                String[] especsAFN = leitorArq.readLine().split(" ");
                List<Integer> afn = new ArrayList<>();

                for (String espec : especsAFN) {
                    afn.add(Integer.parseInt(espec));
                }

                String[] especsEstadosAceitacao = leitorArq.readLine().split(" ");
                List<Integer> indexEstadosAceitacao = new ArrayList<>();

                for (String index : especsEstadosAceitacao) {
                    indexEstadosAceitacao.add(Integer.parseInt(index));
                }

                int numTransicoes = afn.get(2);
                List<List<Integer>> listaTransicoes = new ArrayList<>();

                while (numTransicoes > 0) {
                    String[] especTransicoes = leitorArq.readLine().split(" ");
                    List<Integer> transicoes = new ArrayList<>();

                    for (String transicao : especTransicoes) {
                        transicoes.add(Integer.parseInt(transicao));
                    }

                    listaTransicoes.add(transicoes);

                    numTransicoes--;
                }

                int numCadeias = Integer.parseInt(leitorArq.readLine().split(" ")[0]);
                List<List<Integer>> listaCadeias = new ArrayList<>();

                while (numCadeias > 0) {
                    String[] especCadeias = leitorArq.readLine().split(" ");
                    List<Integer> cadeias = new ArrayList<>();

                    for (String cadeia : especCadeias) {
                        cadeias.add(Integer.parseInt(cadeia));
                    }

                    listaCadeias.add(cadeias);

                    numCadeias--;
                }

                System.out.println(afn);
                System.out.println(indexEstadosAceitacao);
                System.out.println(listaTransicoes);
                System.out.println(listaCadeias);

                numAFNs--;
            }

            arqLeitura.close();

            FileWriter arqEscrita = new FileWriter(arqSaida);
            PrintWriter escritorArq = new PrintWriter(arqEscrita);

            escritorArq.println("TESTANDO SAIDA");
            escritorArq.close();
         }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Classe responsável pela estruturação e execução de Autômatos Finitos Não Determinísticos
 */

public class Automatos {

    private String arqTeste = null;
    private String arqSaida = null;

    private List<List<Estado>> afn = null;
    private List<Integer> estadosDeAceitacao = null;

    public Automatos(String arqTeste, String arqSaida) {
        this.arqTeste = arqTeste.concat(".txt");
        this.arqSaida = arqSaida.concat(".txt");
    }

    /*
    Registrando o número de autômatos de testes contidos no arquivo de entrada
     */

    private int numTestes(BufferedReader leitorArq) {
        try {
            return Integer.parseInt(leitorArq.readLine());
        }
        catch (IOException io) {
            io.printStackTrace();
        }

        return 0;
    }

    /*
    Registrando as especificações do autômato em uma lista
    Método utilizado para fazer a leitura do cabeçalho e dos índices dos estados de aceitação
     */

    private List<Integer> infosAFN(BufferedReader leitorArq) {
        try {
            String[] infosAFN = leitorArq.readLine().split(" ");
            List<Integer> infos = new ArrayList<>();

            for (String info : infosAFN) {
                infos.add(Integer.parseInt(info));
            }

            return infos;
        }
        catch (IOException io) {
            io.printStackTrace();
        }

        return null;
    }

    /*
    Método responsável por retornar a quantidade de transações presentes no autômato
     */

    private int qtdTransicoes(List<Integer> cabecalho) {
        try {
            return cabecalho.get(2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
    Registrando as especificações do autômato em uma lista
    Método utilizado para fazer a leitura das transições e das cadeias de teste
     */

    private List<List<Integer>> especsAFN(BufferedReader leitorArq, int qtd) {
        try {
            List<List<Integer>> listaEspecs = new ArrayList<>();

            int qtdEntradas = qtd;

            while (qtdEntradas > 0) {
                String[] especsAFN = leitorArq.readLine().split(" ");
                List<Integer> especs = new ArrayList<>();

                for (String espec : especsAFN) {
                    especs.add(Integer.parseInt(espec));
                }

                listaEspecs.add(especs);

                qtdEntradas--;
            }

            return listaEspecs;
        }
        catch (IOException io) {
            io.printStackTrace();
        }

        return null;
    }

    /*
    Método responsável por retornar a quantidade de cadeias que serão testadas no autômato
     */

    private int qtdTestes(BufferedReader leitorArq) {
        try {
            return Integer.parseInt(leitorArq.readLine().split(" ")[0]);
        }
        catch (IOException io) {
            io.printStackTrace();
        }

        return 0;
    }

    /*
    Exibindo autômato
     */

    public void printAutomato(List<List<Estado>> afn) {
        System.out.println("\nEstrutura:");

        for (int i = 0; i < afn.size(); i++) {
            System.out.print(i + " -> [");

            for (int j = 0; j < afn.get(i).size(); j++) {
                System.out.print("(" + afn.get(i).get(j).getSimbolo() + " " + afn.get(i).get(j).getDestino() + ")");
            }
            System.out.print("]");
            System.out.println();
        }
    }

    /*

     */

    private boolean recursao(List<Integer> cadeia, int indexEstado, int indexSimbolo) {
        if ((indexSimbolo + 1) <= cadeia.size()) {
            System.out.println("Simbolo a ser lido: " + cadeia.get(indexSimbolo));
        }

        if ((indexSimbolo + 1) > cadeia.size()) {
            System.out.println("Cadeia Finalizada...");

            for (int estadoDeAceitacao : this.estadosDeAceitacao) {
                if (estadoDeAceitacao == indexEstado) { return true; }
            }
        }

        else {
            int simbolo = cadeia.get(indexSimbolo);
            //System.out.println("Simbolo a ser lido: " +simbolo);

            List<Estado> estado = this.afn.get(indexEstado);

            for (int i = 0; i < estado.size(); i++) {
                System.out.println("Estado: " + indexEstado);

                if ((estado.get(i).getSimbolo() == simbolo) || (estado.get(i).getSimbolo() == 0)) {
                    System.out.println("Transicao: " + estado.get(i).getSimbolo() + " -> " + estado.get(i).getDestino());

                    //cadeia.remove(0);

                    System.out.println("\n[!] Realizando chamada recursiva...\n");
                    if (recursao(cadeia, estado.get(i).getDestino(), indexSimbolo + 1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*
    Método responsável pela leitura das cadeias de teste
     */

    private List<Integer> leitorDeCadeias(List<List<Integer>> cadeias) {
        printAutomato(this.afn);
        List<Integer> resultados = new ArrayList<>();

        for (List<Integer> cadeia : cadeias) {
            System.out.println("\nIniciando Execucao...");
            System.out.println("Cadeia: " +cadeia+ "\n");
            boolean resultado = recursao(cadeia, 0, 0);
            resultados.add(resultado ? 1 : 0);
        }

        /*
        System.out.println("\nIniciando Execucao...");
        System.out.println("Cadeia: " +cadeias.get(5)+ "\n");
        boolean resultado = recursao(cadeias.get(5), 0, 0);
        resultados.add(resultado ? 1 : 0);
         */

        System.out.println("\nResultados: " +resultados);

        return resultados;
    }

    /*
    Estruturando o autômato
     */

    public void executaAFNs() {
        try {


            FileReader arqLeitura = new FileReader(this.arqTeste);
            BufferedReader leitorArq = new BufferedReader(arqLeitura);

            int numAFNs = numTestes(leitorArq);

            while (numAFNs > 0) {
                // Leitura do arquivo
                List<Integer> cabecalho = infosAFN(leitorArq);
                this.estadosDeAceitacao = infosAFN(leitorArq);

                int numTransacoes = qtdTransicoes(cabecalho);
                List<List<Integer>> transicoes = especsAFN(leitorArq, numTransacoes);

                int numCadeias = qtdTestes(leitorArq);
                List<List<Integer>> cadeias = especsAFN(leitorArq, numCadeias);

                System.out.println("\nCabecalho: " +cabecalho);
                System.out.println("Estados de Aceitacao (Index): " +estadosDeAceitacao);
                System.out.println("Transicoes: " +transicoes);
                System.out.println("Cadeias (Testes): " +cadeias);

                // Lógica da execução
                List<List<Estado>> afn = new ArrayList<>();

                int qtdEstados = cabecalho.get(0);

                for (int i = 0; i < qtdEstados; i++) {
                    List<Estado> estados = new ArrayList<>();
                    afn.add(i, estados);
                }

                for (List<Integer> transicao : transicoes) {
                    int origem = transicao.get(0);
                    int simbolo = transicao.get(1);
                    int destino = transicao.get(2);

                    Estado estado = new Estado(simbolo, destino);
                    afn.get(origem).add(estado);
                }

                this.afn = afn;

                List<Integer> resultadosLeitura = leitorDeCadeias(cadeias);
                resultados(resultadosLeitura, numAFNs);

                numAFNs--;
            }

            arqLeitura.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Método responsável por adicionar os resultados dos testes ao arquivo de saída
     */

    public void resultados(List<Integer> resultadosLeitura, int numAFNs) {
        try {
            FileWriter arqEscrita = new FileWriter(this.arqSaida, true);
            PrintWriter escritorArq = new PrintWriter(arqEscrita);

            String resultadoLista = resultadosLeitura.toString().replace("[", "");
            String resultadoString = resultadoLista.replace("]", "");
            String resultado = resultadoString.replace(",", "");

            if (numAFNs > 1) {
                escritorArq.println(resultado);
            }
            else {
                escritorArq.print(resultado);
            }

            escritorArq.close();

            System.out.println("\nAdicionando " + "\"" + resultado + "\"" + " ao arquivo de saida...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

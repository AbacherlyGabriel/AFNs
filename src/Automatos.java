import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Classe responsável pela estruturação e execução de Autômatos Finitos Não Determinísticos
 */

public class Automatos {

    private String arqTeste;                    // Arquivo contendo a especificacao dos AFNs
    private String arqSaida;                    // Arquivo contendo a saida das leituras realizadas pelos automatos

    private List<List<Estado>> afn = null;                  // Estrutura responsavel por armazenar o automato

    private int estadoInicial = 0;                          // Estado inicial do AFN

    private List<Integer> estadosDeAceitacao = null;        // Lista contendo os estados de aceitacao

    /*
    Instanciando os objetos responsaveis por armazenar o nome dos
    arquivos de entrada e saida
     */

    public Automatos(String arqTeste, String arqSaida) {
        this.arqTeste = arqTeste.concat(".txt");
        this.arqSaida = arqSaida.concat(".txt");
    }

    /*
    Registrando o numero de automatos de testes contidos no arquivo de entrada
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
    Registrando as especificações do automato em uma lista
    Metodo utilizado para fazer a leitura do cabeçalho e dos indices dos estados de aceitacao
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
    Metodo responsavel por retornar a quantidade de transacoes presentes no automato
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
    Registrando as especificacoes do automato em uma lista

    Metodo utilizado para fazer a leitura das transicoes e das cadeias de teste,
    onde:

    qtd = Numero de transicoes ou quantidade de cadeias de teste especificados no
    arquivo de entrada
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
    Metodo responsavel por retornar a quantidade de cadeias que serao testadas no automato
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
    Exibindo automato
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
    Leitura recursiva das cadeias de teste
    Para as cadeias aceitas, retorna-se true

    Parametros:

    cadeia: Lista contendo a cadeia de teste
    indexEstado: Estado atual do automato
    indexSimbolo: Indice atual do simbolo a ser lido
    simboloLido: Flag indicando se o simbolo foi lido
     */

    private boolean recursao(List<Integer> cadeia, int indexEstado, int indexSimbolo, boolean simboloLido) {
        System.out.println("Estado: " +indexEstado);
        System.out.println("Simbolo Lido? " +simboloLido);

        // Validando se a transicao que sera realizada e de cadeia vazia
        // Em caso positivo, o simbolo em questao nao sera lido

        if (!simboloLido) {
            indexSimbolo -= 1;
        }

        System.out.println("Simbolo (Index): " +indexSimbolo);

        List<Estado> estado = this.afn.get(indexEstado);                // Definindo estado que sera analisado

        // Condicao de parada: cadeia finalizada

        if ((indexSimbolo + 1) > cadeia.size()) {
            System.out.println("\nCadeia Finalizada...");

            List<Integer> estadosAlcancaveis = new ArrayList<>();           // Instanciando lista de alcancaveis por cadeia vazia

            // Preenchendo lista com estados alcancaveis por meio de cadeia vazia

            for (int i = 0; i < estado.size(); i++) {
                if (estado.get(i).getSimbolo() == 0) {
                    estadosAlcancaveis.add(estado.get(i).getDestino());
                }
            }

            // Checando se o estado em analise ou algum dos alcancaveis esta entre os estados de aceitacao

            for (int estadoDeAceitacao : this.estadosDeAceitacao) {
                if ((estadoDeAceitacao == indexEstado) || (estadosAlcancaveis.contains(estadoDeAceitacao))) {
                    System.out.println("[!] Cadeia Aceita");
                    return true;
                }
            }
        }

        else {
            int simbolo = cadeia.get(indexSimbolo);                 // Armazenando simbolo a ser lido

            System.out.println("Simbolo a ser lido: " + simbolo);

            if ((indexEstado == this.estadoInicial) && (simbolo == 0) && (this.estadosDeAceitacao.contains(this.estadoInicial))) {
                return true;
            }

            // Buscando, em cada estado, transacoes que contenham o simbolo a ser lido ou cadeias vazias

            for (int i = 0; i < estado.size(); i++) {
                //System.out.println("Estado: " + indexEstado);

                boolean transicaoSimbolo = estado.get(i).getSimbolo() == simbolo;                                                    // Validando se o simbolo foi encontrado
                boolean transicaoCadeiaVazia = estado.get(i).getSimbolo() == 0;                                                      // Validando se o estado em questao possui transicao de cadeia vazia
                //boolean aceitacaoCadeiaVazia = (indexEstado == 0) && (simbolo == 0) && (this.estadosDeAceitacao.contains(0));        // Validando se o estado inicial e um estado de aceitacao

                // Validando se alguma das expressoes acima e verdadeira
                // Em caso positivo, uma chamada recursiva ao estado destino sera realizada

                if (transicaoSimbolo || transicaoCadeiaVazia) {
                    System.out.println("Transicao: " + indexEstado + " -> " + estado.get(i).getSimbolo() + " -> " + estado.get(i).getDestino());

                    /*
                    if ((transicaoCadeiaVazia) && (!transicaoSimbolo) && (!aceitacaoCadeiaVazia)) {
                        indexSimbolo -= 1;
                    }

                    else {
                        indexSimbolo += 1;
                    }
                     */

                    // Realizando chamada recursiva

                    System.out.println("\nDestino: " +estado.get(i).getDestino());
                    System.out.println("Simbolo (Index): " +indexSimbolo);
                    System.out.println("[!] Realizando chamada recursiva...\n");

                    if (recursao(cadeia, estado.get(i).getDestino(), indexSimbolo + 1, transicaoSimbolo)) {
                        return true;
                    }
                }
            }
        }

        System.out.println("Sequencia Falhou\n");
        return false;
    }

    /*
    Metodo responsavel por gerenciar a leitura das cadeias de teste
    Retorna uma lista (de 0s e 1s), indicando o resultado da leitura das cadeias
     */

    private List<Integer> leitorDeCadeias(List<List<Integer>> cadeias) {
        printAutomato(this.afn);
        List<Integer> resultados = new ArrayList<>();                   // Lista contendo os resultados da leitura das cadeias

        // Gerenciamento das cadeias: Executando o automato para cada teste

        for (List<Integer> cadeia : cadeias) {
            System.out.println("\nIniciando Execucao...");
            System.out.println("Cadeia: " +cadeia+ "\n");
            boolean resultado = recursao(cadeia, this.estadoInicial, 0, true);
            resultados.add(resultado ? 1 : 0);
        }

        System.out.println("\nResultados: " +resultados);

        return resultados;
    }

    /*
    Estruturando e gerenciando a execucao dos AFNs
     */

    public void executaAFNs() {
        try {
            FileReader arqLeitura = new FileReader(this.arqTeste);
            BufferedReader leitorArq = new BufferedReader(arqLeitura);

            int numAFNs = numTestes(leitorArq);                     // Armazenando o numero de AFNs presentes no arquivo de entrada

            // Estruturando e executando os automatos

            while (numAFNs > 0) {
                // Estruturando AFN

                List<Integer> cabecalho = infosAFN(leitorArq);              // Lista armazenando o cabecalho do automato
                this.estadoInicial = cabecalho.get(3);                      // Setando estado inicial
                this.estadosDeAceitacao = infosAFN(leitorArq);              // Lista armazenando os estados de aceitacao do automato

                int numTransacoes = qtdTransicoes(cabecalho);                                   // Armazenando a quantidade de transicoes do automato
                List<List<Integer>> transicoes = especsAFN(leitorArq, numTransacoes);           // Matriz representando a funcao de transicao

                int numCadeias = qtdTestes(leitorArq);                                          // Armazenando a quantidade de testes que serao realizados
                List<List<Integer>> cadeias = especsAFN(leitorArq, numCadeias);                 // Matriz contendo as cadeias de teste

                System.out.println("\nCabecalho: " +cabecalho);
                System.out.println("Estados de Aceitacao (Index): " +estadosDeAceitacao);
                System.out.println("Transicoes: " +transicoes);
                System.out.println("Cadeias (Testes): " +cadeias);

                List<List<Estado>> afn = new ArrayList<>();                 // Instanciando Lista de Adjacencia que representara o automato

                int qtdEstados = cabecalho.get(0);                          // Armazenando quantidade de estados do automato

                // Armazenando, para cada estado, um lista contendo suas transicoes e respectivos destinos

                for (int i = 0; i < qtdEstados; i++) {
                    List<Estado> estados = new ArrayList<>();
                    afn.add(i, estados);
                }

                // Populando as listas criadas anteriormente

                for (List<Integer> transicao : transicoes) {
                    int origem = transicao.get(0);
                    int simbolo = transicao.get(1);
                    int destino = transicao.get(2);

                    Estado estado = new Estado(simbolo, destino);
                    afn.get(origem).add(estado);
                }

                // Executando AFN

                this.afn = afn;                                                         // Setando o AFN como atributo do objeto

                List<Integer> resultadosLeitura = leitorDeCadeias(cadeias);             // Armazenando resultados
                resultados(resultadosLeitura, numAFNs);                                 // Adicionando resultados ao arquivo de saida

                numAFNs--;
            }

            arqLeitura.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Metodo responsavel por adicionar os resultados dos testes ao arquivo de saida
     */

    public void resultados(List<Integer> resultadosLeitura, int numAFNs) {
        try {
            FileWriter arqEscrita = new FileWriter(this.arqSaida, true);
            PrintWriter escritorArq = new PrintWriter(arqEscrita);

            // Formatando saida

            String resultadoLista = resultadosLeitura.toString().replace("[", "");
            String resultadoString = resultadoLista.replace("]", "");
            String resultado = resultadoString.replace(",", "");

            escritorArq.println(resultado);

            /*
            if (numAFNs > 1) {
                escritorArq.println(resultado);
            }
            else {
                escritorArq.print(resultado);
            }

             */

            escritorArq.close();

            System.out.println("\nAdicionando " + "\"" + resultado + "\"" + " ao arquivo de saida...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

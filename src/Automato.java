import java.util.ArrayList;
import java.util.List;

public class Automato {

    private int qtdEstados;
    private List<Estado>[] automato;

    public Automato(int qtdEstados) {
        this.qtdEstados = qtdEstados;
        this.automato = new ArrayList[qtdEstados];

        for (int i = 0; i < qtdEstados; i++) {
            this.automato[i] = new ArrayList<>();
        }
    }

    public void addEstado(int origem, int simbolo, int destino) {
        Estado estado = new Estado(origem, simbolo, destino);
        this.automato[origem].add(estado);
    }

    public void printAutomato() {
        System.out.println("\nEstrutura:");

        for (int i = 0; i < automato.length; i++) {
            System.out.println("test");
            System.out.print(i + " -> [");

            for (int j = 0; j < automato[i].size(); i++) {
                System.out.print(automato[i].get(j).getOrigem() + ", ");
                System.out.print(automato[i].get(j).getSimbolo() + ", ");
                System.out.print(automato[i].get(j).getDestino());
            }

            System.out.print("]");
        }
        System.out.println();
    }

}

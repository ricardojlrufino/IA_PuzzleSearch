package br.com.criativasoft.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Ricardo Alberto Harari - ricardo.harari@gmail.com
 *
 */
public class QuebraCabeca {

    static int CIMA = 1;
    static int BAIXO = 2;
    static int DIREITA = 3;
    static int ESQUERDA = 4;

    static boolean printOn = true;

    static int tamanho_fila = 0;
    static int tamanho_fila_max = 0;

    static double custo_solucao = 0.0;

    static int nos_expandidos = 0;

    static int dimensao = 3;

    static int[] solucao = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    static int[] inicio = new int[] { 8, 4, 0, 5, 1, 7, 6, 2, 3 };

    static PriorityQueue<No> fila = new PriorityQueue<No>(5, new ComparadorNo());

    static Set processados = new HashSet();

    public static void main( String[] args ) {
        long l = System.currentTimeMillis();
        No no = iniciarBusca(inicio);
        if (no != null) {
            imprimirResultado(no, true);
        } else {
            System.out.println("SOLUCAO NAO ENCONTRADA!");
        }
        System.out.println("\r\n\r\nElapsed time=" + (System.currentTimeMillis() - l) + "ms");
    }

    /**
     * efetua a busca da solucao retorna a solucao ou em caso de nao encontrar null
     *
     * @param posicoesIniciais
     * @return
     */
    private static No iniciarBusca( int[] posicoesIniciais ) {
        No noInicial = new No();
        noInicial.estado = posicoesIniciais;
        fila.add(noInicial);
        tamanho_fila = 1;
        while (!(fila.isEmpty())) {
            tamanho_fila--;
            No no = fila.remove();
            if (goal(no.estado)) {
                custo_solucao = no.custoCaminho;
                return no;
            }
            adicionarNosAlternativosFila(no);
            tamanho_fila = fila.size();
            if (tamanho_fila_max < tamanho_fila) tamanho_fila_max = tamanho_fila; // estatistica
        }
        return null; // falhou
    }

    /**
     * imprimir o resultado da solucao
     *
     * @param no
     * @param imprimeTabuleiros
     */
    private static void imprimirResultado( No no , boolean imprimeTabuleiros ) {
        print("ESTADO FINAL:");
        print("===============");
        imprimirTabuleiro(no);
        print("===============");
        print("tamanho_fila_max = " + tamanho_fila_max);
        print("nos_expandidos = " + nos_expandidos);
        print("===============");
        print("OPERACOES REVERSA:");

        while (no.pai != null) {
            print("===============");
            print("Step: " + no.step);
            print("Custo:" + no.custoCaminho);
            print("Acao:" + getAcaoReversa(no.acao));
            no = no.pai;
            if (imprimeTabuleiros) imprimirTabuleiro(no);
        }
    }

    /**
     * retorna o nome da acao, nao do calhau, mas do bloco a ser movido
     *
     * @param acao
     * @return
     */
    private static String getAcaoReversa( int acao ) {
        switch (acao) {
        case 1:
            return "BAIXO";
        case 2:
            return "CIMA";
        case 3:
            return "ESQUERDA";
        case 4:
            return "DIREITA";
        }
        return "NENHUMA";
    }

    /**
     * @param no
     */
    private static void imprimirTabuleiro( No no ) {
        if (!printOn) return;
        for (int i = 0; i < dimensao; i++) {
            imprimirTabuleiroLinha();
            for (int j = 0; j < dimensao; j++) {
                int n = i * dimensao + j;
                System.out.print("+ " + no.estado[n] + " ");
            }
            System.out.println("+");
        }
        imprimirTabuleiroLinha();
    }

    /**
     * imprime uma linha separadora do tabuleiro
     */
    private static void imprimirTabuleiroLinha() {
        if (!printOn) return;
        for (int i = 0; i < dimensao; i++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    /**
     * imprime 's'
     *
     * @param s
     */
    private static void print( String s ) {
        if (printOn) System.out.println(s);

    }

    /**
     * esta eh a chave do problema, determinar uma fucao heuristica que retorne
     *
     * o quanto esta perto da solucao neste problema a heuristica eh calculada
     *
     * verificando quantos blocos estao na posicao correta - isto eh, de acordo
     *
     * com a matriz 'solucao'
     *
     * @param estado
     * @return
     */
    private static double heuristica( int[] estado ) {
        double valor = 0.0;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                int n = i * dimensao + j;
                valor += estado[n] == solucao[n] ? 1 : 0;
            }
        }
        return valor;
    }

    private static boolean goal( int[] estado ) {
        for (int i = 0; i < dimensao * dimensao; i++) {
            if (estado[i] != solucao[i]) return false;
        }
        return true;
    }

    private static List recuperarSucessores( int[] estado ) {
        List filhos = new ArrayList();
        if (podeMoverCalhau(estado, CIMA)) {
            int[] novoEstado = clonar(estado);
            moverCima(novoEstado);
            filhos.add(new Sucessor(novoEstado, CIMA));
        }
        if (podeMoverCalhau(estado, ESQUERDA)) {
            int[] novoEstado = clonar(estado);
            moverEsquerda(novoEstado);
            filhos.add(new Sucessor(novoEstado, ESQUERDA));
        }
        if (podeMoverCalhau(estado, DIREITA)) {
            int[] novoEstado = clonar(estado);
            moverDireita(novoEstado);
            filhos.add(new Sucessor(novoEstado, DIREITA));
        }
        if (podeMoverCalhau(estado, BAIXO)) {
            int[] novoEstado = clonar(estado);
            moverBaixo(novoEstado);
            filhos.add(new Sucessor(novoEstado, BAIXO));
        }
        return filhos;
    }

    private static void moverCima( int[] estado ) {
        int pos = 0;
        for (int i = dimensao; i < dimensao * dimensao; i++) {
            if (estado[i] == 0) {
                pos = i;
                break;
            }
        }
        if (pos > 0) {
            int valor = estado[pos - dimensao];
            estado[pos - dimensao] = 0;
            estado[pos] = valor;
        }
    }

    private static void moverBaixo( int[] estado ) {
        int pos = 0;
        for (int i = 0; i < dimensao * dimensao; i++) {
            if (estado[i] == 0) {
                pos = i;
                break;
            }
        }
        int valor = estado[pos + dimensao];
        estado[pos + dimensao] = 0;
        estado[pos] = valor;
    }

    private static void moverEsquerda( int[] estado ) {
        int pos = 0;
        for (int i = 0; i < dimensao * dimensao; i++) {
            if (estado[i] == 0) {
                pos = i;
                break;
            }
        }
        int valor = estado[pos - 1];
        estado[pos - 1] = 0;
        estado[pos] = valor;
    }

    private static void moverDireita( int[] estado ) {
        int pos = 0;
        for (int i = 0; i < dimensao * dimensao; i++) {
            if (estado[i] == 0) {
                pos = i;
                break;
            }
        }
        int valor = estado[pos + 1];
        estado[pos + 1] = 0;
        estado[pos] = valor;
    }

    /**
     * expandir e adicionar nós subsequentes na fila
     *
     * @param no
     */
    private static void adicionarNosAlternativosFila( No no ) {
        if (!(processados.contains(no.toString()))) {
            processados.add(no.toString());
            List<No> expandidos = expandirNos(no);
            for (No o : expandidos) {
                fila.add(o);
            }
        }
    }

    /**
     * retorna um estado clonado
     *
     * @param estado
     * @return
     */
    private static int[] clonar( int[] estado ) {
        int[] ret = new int[estado.length];
        for (int i = 0; i < estado.length; i++) {
            ret[i] = estado[i];
        }
        return ret;
    }

    /**
     * expandir o no com os seus sucessores possiveis
     *
     * @param no
     * @return
     */
    private static List expandirNos( No no ) {
        List nos = new ArrayList();
        List<Sucessor> proximos = recuperarSucessores(no.estado);
        for (Sucessor prox : proximos) {
            No no0 = new No();
            no0.pai = no;
            no0.estado = prox.estado;
            no0.step = no.step + 1;
            no0.acao = prox.acao;
            // o custo é sempre 1 pois movemos 1 bloco a cada passo
            no0.custoStep = 1.0;
            no0.custoCaminho += no0.pai.custoCaminho + 1.0;
            nos.add(no0);
            // if (!processados.contains(no0.toString())) nos.add(no0);
        }
        nos_expandidos++;
        return nos;
    }

    /**
     * verifica se o calhau pode ser movido para uma direcao(acao)
     *
     * @param estado
     * @param acao
     * @return
     */
    private static boolean podeMoverCalhau( int[] estado , int acao ) {
        int posicao = 0;
        for (int i = 0; i < dimensao * dimensao; i++) {
            if (estado[i] == 0) {
                posicao = i;
                break;
            }
        }
        if (acao == ESQUERDA) {
            while (posicao >= 0) {
                if (posicao == 0) return false;
                posicao -= dimensao;
            }
        } else if (acao == CIMA) {
            if (posicao >= 0 && posicao < dimensao) return false;
        } else if (acao == DIREITA) {
            posicao++;
            while (posicao >= dimensao) {
                if (posicao == dimensao) return false;
                posicao -= dimensao;
            }
        } else if (acao == BAIXO) {
            if (posicao >= dimensao * (dimensao - 1)) return false;
        }
        return true;
    }

    /**
     * representa um estado sucessor do estado atual dada uma acao
     *
     * @author Ricardo A Harari - ricardo.harari@gmail.com
     *
     */
    static class Sucessor {
        public Sucessor(int _estado[], int _acao) {
            estado = _estado;
            acao = _acao;
        }

        public int[] estado;
        public int acao;
    }

    /**
     * representa um nó de um grafo possui o estado, o nó pai,
     *
     * a acao, o custo para chegar até este nó, a profundidade do nó
     *
     * e o custo unitário para ir do pai até o filho
     *
     * @author Ricardo A Harari - ricardo.harari@gmail.com
     *
     */
    static class No {
        public int[] estado;
        public int acao;
        public No pai;
        public int step = 0;
        public double custoCaminho = 0.0;
        public double custoStep = 0.0;

        public List recuperarArvore() {
            No atual = this;
            List ret = new ArrayList();
            while (!(atual.pai != null)) {
                ret.add(0, atual);
                atual = atual.pai;
            }
            ret.add(0, atual);
            return ret;
        }

        @Override
        public String toString() {
            String ret = "";
            for (int i = 0; i < dimensao * dimensao; i++) {
                ret += estado[i];
            }
            return ret;
        }

        @Override
        public boolean equals( Object o ) {
            if ((o == null) || (this.getClass() != o.getClass())) return false;
            if (this == o) return true;
            No x = (No) o;
            for (int i = 0; i < dimensao; i++) {
                if (estado[i] != x.estado[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * comparador de Nós chamando a heuristica
     * @author Ricardo A Harari - ricardo.harari@gmail.com
     */
    static class ComparadorNo implements Comparator<No> {
        public int compare( No o1 , No o2 ) {
            double d1 = heuristica(o1.estado);
            double d2 = heuristica(o2.estado);
            return (int) (d2 - d1);
        }
    }

}
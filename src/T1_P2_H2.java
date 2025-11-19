import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H2 extends Itineraire {

    ArrayList<Sommet> sommets;

    public T1_P2_H2(Graphe graphe) {
        super(new Sommet(0), new Sommet(0), graphe);
        this.sommets = new ArrayList<>();
    }

    private int[] plusCourtChemin(int depart, int arrivee) {
        ResultatDijkstra res = this.graphe.Dijkstra(this.graphe, depart);
        ArrayList<Integer> chemin = new ArrayList<>();
        int curr = arrivee;
        while (curr != depart) {
            chemin.add(0, curr);
            curr = res.precedent[curr];
        }
        chemin.add(0, depart);
        return chemin.stream().mapToInt(i -> i).toArray();
    }

    public void eulerPrime() {
        int n = this.graphe.nb_sommets;
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);

        ArrayList<Integer> impairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int deg = 0;
            for (int j = 0; j < n; j++)
                deg += adjCopy[i][j];
            if (deg % 2 == 1)
                impairs.add(i);
        }

        if (impairs.size() != 2) {
            System.out.println("Le graphe doit avoir exactement deux sommets impairs.");
            return;
        }

        // Dupliquer les arêtes sur le plus court chemin entre les deux sommets impairs
        int[] cheminImpairs = plusCourtChemin(impairs.get(0), impairs.get(1));
        for (int i = 0; i < cheminImpairs.length - 1; i++) {
            int u = cheminImpairs[i];
            int v = cheminImpairs[i + 1];
            adjCopy[u][v]++;
            adjCopy[v][u]++;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        ArrayList<Sommet> path = new ArrayList<>();
        int curr = 0;

        while (!stack.isEmpty()) {
            boolean hasEdge = false;
            for (int next = 0; next < n; next++) {
                if (adjCopy[curr][next] > 0) {
                    adjCopy[curr][next]--;
                    adjCopy[next][curr]--;
                    stack.push(curr);
                    curr = next;
                    hasEdge = true;
                    break;
                }
            }
            if (!hasEdge) {
                path.add(new Sommet(curr));
                curr = stack.pop();
            }
        }
        this.sommets = path;
    }

    @Override
    public void afficher_chemin() {
        if (this.sommets.size() < 2) {
            System.out.println("Aucun chemin à afficher.");
            return;
        }
        System.out.println("Chemin / Cycle eulérien :");
        int total = 0;
        for (int i = 0; i < this.sommets.size() - 1; i++) {
            Sommet a = this.sommets.get(i);
            Sommet b = this.sommets.get(i + 1);
            int dist = this.graphe.matrice.longeurs[a.numero][b.numero];
            total += dist;
            System.out.print(a.numero + " -> (" + dist + ") -> ");
        }
        Sommet last = this.sommets.get(this.sommets.size() - 1);
        System.out.print(last.numero);
        System.out.println("\nDistance totale : " + total);
    }
    public String genererParcours() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parcours eulérien :\n");
        for (Sommet s : sommets) {
            sb.append("S").append(s.numero).append(" -> ");
        }
        sb.append("FIN");
        return sb.toString();
    }

}

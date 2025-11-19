import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H2 extends Itineraire {

    ArrayList<Sommet> sommets;

    public T1_P2_H2(Graphe graphe) {
        super(new Sommet(0), new Sommet(0), graphe);
        this.sommets = new ArrayList<>();
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
        int curr;
        if (impairs.isEmpty()) {
            // Cycle eulérien → départ arbitraire
            curr = 0;
        } else if (impairs.size() == 2) {
            // Chemin eulérien → départ = premier impair
            curr = impairs.get(0);
        } else {
            System.out.println("Impossible : plus de 2 sommets impairs");
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(curr);
        ArrayList<Sommet> path = new ArrayList<>();
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
        Sommet last = this.sommets.getLast();
        System.out.print(last.numero);
        System.out.println("\nDistance totale : " + total);
    }

    public String genererParcours() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chemin eulérien :\n");
        for (Sommet s : sommets) {
            sb.append("S").append(s.numero).append(" -> ");
        }
        sb.append("FIN");
        return sb.toString();
    }

}

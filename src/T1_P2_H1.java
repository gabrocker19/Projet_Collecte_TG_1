import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H1 extends Itineraire {

    ArrayList<Sommet> sommets;

    public T1_P2_H1(Graphe graphe) {
        super(new Sommet(0), new Sommet(0), graphe);
        this.sommets = new ArrayList<>();
    }

    public void eulerianCycle() {
        int n = this.graphe.nb_sommets;
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);
        Stack<Integer> stack = new Stack<>();
        int curr = 0;
        stack.push(curr);
        this.sommets.clear();
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
                this.sommets.add(new Sommet(curr));
                curr = stack.pop();
            }
        }
    }

    @Override
    public void afficher_chemin() {
        if (this.sommets.size() < 2) {
            System.out.println("Aucun cycle à afficher.");
            return;
        }
        System.out.println("Cycle eulérien :");
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


}

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

        // Copier la matrice d'adjacence
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);

        Stack<Integer> stack = new Stack<>();
        int curr = 0;
        stack.push(curr);

        // on vide old list
        this.sommets.clear();

        while (!stack.isEmpty()) {

            boolean hasEdge = false;

            // cherche un voisin encore disponible
            for (int next = 0; next < n; next++) {
                if (adjCopy[curr][next] > 0) {

                    // supprime l’arête curr-next
                    adjCopy[curr][next]--;
                    adjCopy[next][curr]--;

                    stack.push(curr);
                    curr = next;

                    hasEdge = true;
                    break;
                }
            }

            if (!hasEdge) {
                // on ajoute directement un Sommet → plus besoin de Circuit
                this.sommets.add(new Sommet(curr));
                curr = stack.pop();
            }
        }
    }

    @Override
    public void afficher_chemin() {
        for (Sommet s : this.sommets) {
            System.out.println(s.numero);
        }
    }
}

import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H1 extends Itineraire {
    ArrayList<Integer> sommets; // mettre sommet a la place de INterger

    public T1_P2_H1(Graphe graphe) {
        super(new Sommet(0), new Sommet(0), graphe);
        this.sommets = new ArrayList<Integer>();
    }

    public void eulerianCycle() {
        int n = this.graphe.nb_sommets;

        // On copie l'adjacence pour ne pas détruire ton graphe original
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);

        ArrayList<Integer> circuit = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        // ⚠ On démarre toujours du sommet 0 (comme tu voulais)
        int curr = 0;
        stack.push(curr);

        while (!stack.isEmpty()) {
            boolean hasEdge = false;

            // Cherche un voisin encore disponible
            for (int next = 0; next < n; next++) {
                if (adjCopy[curr][next] > 0) {
                    // Enlever l'arête (curr,next)
                    adjCopy[curr][next]--;
                    adjCopy[next][curr]--;

                    stack.push(curr);
                    curr = next;

                    hasEdge = true;
                    break;
                }
            }

            if (!hasEdge) {
                circuit.add(curr);
                curr = stack.pop();
            }
        }
        this.sommets = circuit;
    }

    @Override
    public void afficher_chemin () {
        for (Integer i : this.sommets) {
            System.out.println(i);
        }
    }

}

import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H2 extends Itineraire {

    ArrayList<Sommet> sommets;

    public T1_P2_H2(Graphe graphe) {
        super(new Sommet(0), new Sommet(0), graphe);
        this.sommets = new ArrayList<>();
    }

    /** ============================
     *       CYCLE EULERIEN
     *  ============================ */
    public void eulerPrime() {

        int n = this.graphe.nb_sommets;

        // Copier la matrice d’adjacence
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);

        // Vérifier que tous les sommets sont pairs
        for (int i = 0; i < n; i++) {
            int deg = 0;
            for (int j = 0; j < n; j++)
                deg += adjCopy[i][j];

            if (deg % 2 != 0) {
                System.out.println("Impossible : le graphe n'a pas tous ses sommets pairs. Pas de cycle eulérien.");
                return;
            }
        }

        // On impose le départ à 0
        int curr = 0;

        Stack<Integer> stack = new Stack<>();
        ArrayList<Sommet> path = new ArrayList<>();

        stack.push(curr);

        while (!stack.isEmpty()) {
            boolean hasEdge = false;

            // Cherche un voisin disponible
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

    /** ============================
     *        AFFICHAGE
     *  ============================ */
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

    public String genererParcours() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cycle eulérien : ");
        for (Sommet s : sommets) {
            sb.append(s.numero).append(" -> ");
        }
        sb.append("FIN");
        return sb.toString();
    }
}
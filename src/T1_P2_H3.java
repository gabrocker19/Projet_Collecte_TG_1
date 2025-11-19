import java.util.ArrayList;
import java.util.Stack;

public class T1_P2_H3 extends Itineraire {

    ArrayList<Sommet> sommets;

    public T1_P2_H3(Graphe graphe) {
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

    // Couplage minimum par force brute pour petits graphes
    private ArrayList<int[]> couplageMin(ArrayList<Integer> impairs) {
        ArrayList<int[]> result = new ArrayList<>();
        if (impairs.isEmpty()) return result;
        if (impairs.size() == 2) {
            result.add(new int[]{impairs.get(0), impairs.get(1)});
            return result;
        }

        int minDistance = Integer.MAX_VALUE;
        ArrayList<int[]> bestPairs = null;

        // Permuter toutes les paires possibles récursivement
        for (int i = 0; i < impairs.size() - 1; i++) {
            for (int j = i + 1; j < impairs.size(); j++) {
                ArrayList<Integer> rest = new ArrayList<>(impairs);
                int u = rest.remove(j);
                int v = rest.remove(i);
                ArrayList<int[]> subPairs = couplageMin(rest);
                subPairs.add(0, new int[]{u, v});

                int distSum = 0;
                for (int[] p : subPairs) {
                    ResultatDijkstra res = this.graphe.Dijkstra(this.graphe, p[0]);
                    distSum += res.distance[p[1]];
                }

                if (distSum < minDistance) {
                    minDistance = distSum;
                    bestPairs = subPairs;
                }
            }
        }
        return bestPairs;
    }

    public void chinesePostman() {
        int n = this.graphe.nb_sommets;
        int[][] adjCopy = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.graphe.matrice.adj[i], 0, adjCopy[i], 0, n);

        // Identifier les sommets impairs
        ArrayList<Integer> impairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int deg = 0;
            for (int j = 0; j < n; j++)
                deg += adjCopy[i][j];
            if (deg % 2 == 1)
                impairs.add(i);
        }

        // Couplage minimal des sommets impairs
        ArrayList<int[]> couplages = couplageMin(impairs);

        // Dupliquer les arêtes sur les plus courts chemins
        for (int[] paire : couplages) {
            int[] chemin = plusCourtChemin(paire[0], paire[1]);
            for (int i = 0; i < chemin.length - 1; i++) {
                int u = chemin[i];
                int v = chemin[i + 1];
                adjCopy[u][v]++;
                adjCopy[v][u]++;
            }
        }

        // Construire le cycle eulérien
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
        System.out.println("Cycle eulérien (Chinese Postman) :");
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
        if (this.sommets == null || this.sommets.size() < 2) {
            return "Aucun chemin à afficher (liste de sommets vide).";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Cycle eulérien (Chinese Postman) :\n");

        int total = 0;
        for (int i = 0; i < this.sommets.size() - 1; i++) {
            Sommet a = this.sommets.get(i);
            Sommet b = this.sommets.get(i + 1);
            int dist = this.graphe.matrice.longeurs[a.numero][b.numero];
            total += dist;
            sb.append(a.numero)
                    .append(" -> (")
                    .append(dist)
                    .append(") -> ");
        }

        Sommet last = this.sommets.get(this.sommets.size() - 1);
        sb.append(last.numero)
                .append("\nDistance totale : ")
                .append(total);

        return sb.toString();
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class T1_P1_H2 extends Itineraire {

    ArrayList<Sommet> a_visiter;
    int nb_a_visiter;
    int[][] matTSP;

    ArrayList<Sommet> meilleur_cycle;
    int meilleur_cout = Integer.MAX_VALUE;

    public T1_P1_H2(Graphe graphe, ArrayList<Sommet> a_visiter, int nb_a_visiter) {
        super(a_visiter.getFirst(), a_visiter.getLast(), graphe);
        this.a_visiter = a_visiter;
        this.nb_a_visiter = nb_a_visiter;
        this.meilleur_cycle = new ArrayList<>();
        this.matTSP = new int[nb_a_visiter][nb_a_visiter];
    }

    private static boolean contientSommet(ArrayList<Sommet> liste, int numero) {
        for (Sommet s : liste) if (s.numero == numero) return true;
        return false;
    }

    public static T1_P1_H2 creer(Graphe graphe) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Sommet> a_visiter = new ArrayList<>();

        int n;
        do {
            System.out.println("Combien de sommets ? (max 10)");
            n = sc.nextInt();
        } while (n < 2 || n > 10);

        a_visiter.add(new Sommet(0)); // sommet de départ forcé = 0

        int s;
        for (int i = 1; i < n; i++) {
            do {
                System.out.println("Sommet à ajouter :");
                s = sc.nextInt();
            } while (contientSommet(a_visiter, s));
            a_visiter.add(new Sommet(s));
        }

        return new T1_P1_H2(graphe, a_visiter, n);
    }

    public void afficher_sommets_a_visiter() {
        System.out.println("Sommets à visiter :");
        for (Sommet s : a_visiter) System.out.print(s.numero + " ");
        System.out.println();
    }

    public void construire_matrice_tsp() {
        for (int i = 0; i < nb_a_visiter; i++) {
            int start = a_visiter.get(i).numero;
            ResultatDijkstra r = graphe.Dijkstra(graphe, start);
            for (int j = 0; j < nb_a_visiter; j++) {
                int end = a_visiter.get(j).numero;
                matTSP[i][j] = r.distance[end];
            }
        }
    }

    public void afficher_matrice_tsp() {
        System.out.println("Matrice des distances TSP :");
        for (int i = 0; i < nb_a_visiter; i++) {
            for (int j = 0; j < nb_a_visiter; j++) {
                System.out.print(matTSP[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int coutCycle(ArrayList<Integer> permutation) {
        int total = 0;
        for (int i = 0; i < permutation.size() - 1; i++) {
            int a = permutation.get(i);
            int b = permutation.get(i + 1);
            total += matTSP[a][b];
        }
        total += matTSP[permutation.getLast()][permutation.getFirst()];
        return total;
    }

    public void calculer_tsp() {
        construire_matrice_tsp();
        ArrayList<Integer> base = new ArrayList<>();
        for (int i = 0; i < nb_a_visiter; i++) base.add(i);

        int start = 0;
        ArrayList<Integer> reste = new ArrayList<>(base);
        reste.remove(start);

        rechercher_permutation(start, new ArrayList<>(Collections.singletonList(start)), reste);
    }

    private void rechercher_permutation(int start, ArrayList<Integer> courant, ArrayList<Integer> reste) {
        if (reste.isEmpty()) {
            int cout = coutCycle(courant);
            if (cout < meilleur_cout) {
                meilleur_cout = cout;
                meilleur_cycle = new ArrayList<>();
                for (int idx : courant) meilleur_cycle.add(a_visiter.get(idx));
            }
            return;
        }

        for (int i = 0; i < reste.size(); i++) {
            int suivant = reste.get(i);
            ArrayList<Integer> nouveau = new ArrayList<>(courant);
            nouveau.add(suivant);
            ArrayList<Integer> nouveau_reste = new ArrayList<>(reste);
            nouveau_reste.remove(i);
            rechercher_permutation(start, nouveau, nouveau_reste);
        }
    }

    @Override
    public void afficher_chemin() {
        if (meilleur_cycle.isEmpty()) {
            System.out.println("Aucun cycle calculé !");
            return;
        }

        System.out.println("Meilleur cycle trouvé :");
        int j = meilleur_cycle.size() - 1;

        for (int i = 0; i < meilleur_cycle.size(); i++) {
            System.out.print(meilleur_cycle.get(i).numero);
            if (i < meilleur_cycle.size() - 1) {
                System.out.print(" -> (" + matTSP[i][i + 1] + ") -> ");
            }
        }

        System.out.print(" -> (" + matTSP[meilleur_cycle.size() - 1][0] + ") -> ");
        System.out.println(meilleur_cycle.getFirst().numero);

        System.out.println("Coût total = " + meilleur_cout);
    }

    // ====== Construction d'une chaîne pour l'affichage (Swing) ======
    private int indexDansAVisiter(Sommet s) {
        for (int i = 0; i < nb_a_visiter; i++) {
            if (a_visiter.get(i).numero == s.numero) {
                return i;
            }
        }
        return -1;
    }

    public String texteMeilleurCycle() {
        if (meilleur_cycle == null || meilleur_cycle.isEmpty()) {
            return "Aucun cycle calculé pour le moment.";
        }

        StringBuilder sb = new StringBuilder();

        // Rappel des sommets choisis
        sb.append("Sommets à visiter : ");
        for (Sommet s : a_visiter) {
            sb.append("S").append(s.numero).append(" ");
        }
        sb.append("\n\n");

        sb.append("Meilleur cycle trouvé :\n");

        for (int i = 0; i < meilleur_cycle.size(); i++) {
            Sommet s1 = meilleur_cycle.get(i);
            Sommet s2 = meilleur_cycle.get((i + 1) % meilleur_cycle.size()); // boucle

            int i1 = indexDansAVisiter(s1);
            int i2 = indexDansAVisiter(s2);

            sb.append("S").append(s1.numero);

            if (i1 != -1 && i2 != -1) {
                sb.append(" -> (").append(matTSP[i1][i2]).append(") -> ");
            } else {
                sb.append(" -> ");
            }
        }

        // retour au premier sommet pour fermer le cycle
        sb.append("S").append(meilleur_cycle.getFirst().numero);
        sb.append("\n\nDistance total = ").append(meilleur_cout);
        return sb.toString();
    }

}
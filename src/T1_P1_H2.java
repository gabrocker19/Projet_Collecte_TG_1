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

    // -----------------------------------------------------------
    // Vérifie si un sommet existe déjà dans une liste
    // -----------------------------------------------------------
    private static boolean contientSommet(ArrayList<Sommet> liste, int numero) {
        for (Sommet s : liste) if (s.numero == numero) return true;
        return false;
    }

    // -----------------------------------------------------------
    // Création interactive d'une instance
    // -----------------------------------------------------------
    public static T1_P1_H2 creer(Graphe graphe) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Sommet> a_visiter = new ArrayList<>();

        int n;
        do {
            System.out.println("Combien de sommets ? (hors depart) (max 10)");
            n = sc.nextInt();
        } while (n < 2 || n > 10);

        a_visiter.add(new Sommet(0)); // sommet de départ = 0

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

    // -----------------------------------------------------------
    public void afficher_sommets_a_visiter() {
        System.out.println("Sommets à visiter :");
        for (Sommet s : a_visiter) System.out.print(s.numero + " ");
        System.out.println();
    }

    // -----------------------------------------------------------
    // Construction de la matrice des distances pour le TSP
    // -----------------------------------------------------------
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

    // -----------------------------------------------------------
    // Calcul du coût d’un cycle donné
    // -----------------------------------------------------------
    private int coutCycle(ArrayList<Integer> permutation) {
        int total = 0;
        for (int i = 0; i < permutation.size() - 1; i++) {
            total += matTSP[permutation.get(i)][permutation.get(i + 1)];
        }
        total += matTSP[permutation.getLast()][permutation.getFirst()];
        return total;
    }

    // -----------------------------------------------------------
    // TSP brute-force
    // -----------------------------------------------------------
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

    // -----------------------------------------------------------
    // Récupération d’un vrai plus court chemin (Dijkstra)
    // -----------------------------------------------------------
    private int[] plusCourtChemin(int depart, int arrivee) {
        ResultatDijkstra res = this.graphe.Dijkstra(this.graphe, depart);

        ArrayList<Integer> chemin = new ArrayList<>();
        int curr = arrivee;

        if (curr == depart) {
            return new int[]{depart};
        }

        while (curr != depart) {
            chemin.add(0, curr);
            curr = res.precedent[curr];
        }

        chemin.add(0, depart);
        return chemin.stream().mapToInt(i -> i).toArray();
    }

    // -----------------------------------------------------------
    // Reconstruit l’itinéraire complet dans le graphe réel
    // -----------------------------------------------------------
    public ArrayList<Sommet> getItineraireComplet() {
        ArrayList<Sommet> cheminComplet = new ArrayList<>();

        if (meilleur_cycle.isEmpty()) return cheminComplet;

        int taille = meilleur_cycle.size();

        for (int i = 0; i < taille; i++) {
            Sommet s1 = meilleur_cycle.get(i);
            Sommet s2 = meilleur_cycle.get((i + 1) % taille);

            int[] segment = plusCourtChemin(s1.numero, s2.numero);

            for (int k = 0; k < segment.length; k++) {
                if (i > 0 && k == 0) continue; // éviter doublons
                cheminComplet.add(new Sommet(segment[k]));
            }
        }

        return cheminComplet;
    }

    // -----------------------------------------------------------
    // Affichage simple du cycle TSP
    // -----------------------------------------------------------
    @Override
    public void afficher_chemin() {
        if (meilleur_cycle.isEmpty()) {
            System.out.println("Aucun cycle calculé !");
            return;
        }

        System.out.println("Meilleur cycle trouvé (TSP simplifié) :");

        for (int i = 0; i < meilleur_cycle.size(); i++) {
            System.out.print("S" + meilleur_cycle.get(i).numero);
            if (i < meilleur_cycle.size() - 1) {
                int a = indexDansAVisiter(meilleur_cycle.get(i));
                int b = indexDansAVisiter(meilleur_cycle.get(i + 1));
                System.out.print(" -> (" + matTSP[a][b] + ") -> ");
            }
        }

        int last = indexDansAVisiter(meilleur_cycle.getLast());
        int first = indexDansAVisiter(meilleur_cycle.getFirst());
        System.out.print(" -> (" + matTSP[last][first] + ") -> S" + meilleur_cycle.getFirst().numero);

        System.out.println("\nCoût total = " + meilleur_cout);
    }

    // -----------------------------------------------------------
    // Affichage du chemin complet (tous les sommets traversés)
    // -----------------------------------------------------------
    public void afficher_chemin_complet() {
        ArrayList<Sommet> c = getItineraireComplet();

        if (c.isEmpty()) {
            System.out.println("Impossible de reconstruire le chemin complet.");
            return;
        }

        System.out.println("\nChemin COMPLET dans le graphe :");

        for (int i = 0; i < c.size(); i++) {
            System.out.print("S" + c.get(i).numero);
            if (i < c.size() - 1) System.out.print(" -> ");
        }
        this.chemin = c;
        System.out.println("\n(Coût TSP : " + meilleur_cout + ")");
    }

    // -----------------------------------------------------------
    // Helpers pour l’affichage
    // -----------------------------------------------------------
    private int indexDansAVisiter(Sommet s) {
        for (int i = 0; i < nb_a_visiter; i++) {
            if (a_visiter.get(i).numero == s.numero) return i;
        }
        return -1;
    }

    public String texteMeilleurCycle() {
        if (meilleur_cycle == null || meilleur_cycle.isEmpty()) {
            return "Aucun cycle calculé.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Sommets à visiter : ");
        for (Sommet s : a_visiter) sb.append("S").append(s.numero).append(" ");
        sb.append("\n\nMeilleur cycle TSP :\n");

        for (int i = 0; i < meilleur_cycle.size(); i++) {
            Sommet s1 = meilleur_cycle.get(i);
            Sommet s2 = meilleur_cycle.get((i + 1) % meilleur_cycle.size());

            int i1 = indexDansAVisiter(s1);
            int i2 = indexDansAVisiter(s2);

            sb.append("S").append(s1.numero)
                    .append(" -> (")
                    .append(matTSP[i1][i2])
                    .append(") -> ");
        }

        sb.append("S").append(meilleur_cycle.getFirst().numero)
                .append("\n\nDistance totale = ").append(meilleur_cout);

        return sb.toString();
    }
}

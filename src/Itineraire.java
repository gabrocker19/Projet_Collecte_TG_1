import java.util.ArrayList;
import java.util.Scanner;

public class Itineraire {
    Sommet s_depart, s_arrivee;
    Graphe graphe;
    ArrayList<Integer> chemin = new ArrayList<>();
    ArrayList<Integer> distances = new ArrayList<>();

    public Itineraire(Sommet depart, Sommet arrivee, Graphe graphe) {
        this.s_depart = depart;
        this.s_arrivee = arrivee;
        this.graphe = graphe;
    }

    public static Itineraire creer(Graphe graphe) {
        Scanner sc = new Scanner(System.in);
        int d, a;

        do {
            System.out.println("Quel sommet de départ ?");
            d = sc.nextInt();
        } while (d<0 || d>=graphe.nb_sommets);

        do {
            System.out.println("Quel sommet d'arrivée ?");
            a = sc.nextInt();
        } while (a<0 || a>=graphe.nb_sommets || a==d);

        return new Itineraire(new Sommet(d), new Sommet(a), graphe);
    }

    public void remplir_itineraire(int[] precedent, Graphe graphe) {
        for (int i = s_arrivee.numero; i != -1; i = precedent[i]) {
            this.chemin.addFirst(i);
        }
        for (int i=0; i<this.chemin.size(); i++) {
            try {
                this.distances.addFirst(graphe.matrice.longeurs[this.chemin.get(i)][this.chemin.get(i+1)]);
            } catch (IndexOutOfBoundsException e) {}
        }
    }

    public void afficher_chemin() {
        int j = this.distances.size() - 1;
        for (Integer integer : this.chemin) {
            System.out.print(integer);
            if (j >= 0) {
                System.out.print(" -> (" + this.distances.get(j) + ") -> ");
                j--;
            }
        }
        System.out.println();
    }

}


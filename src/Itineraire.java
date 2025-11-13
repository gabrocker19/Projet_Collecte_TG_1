import java.util.ArrayList;
import java.util.Scanner;

public class Itineraire {
    Sommet s_depart, s_arrivee;
    Graphe graphe;
    ArrayList<Integer> chemin = new ArrayList<>();

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

    public void afficher_chemin(int[] precedent) {
        for (int i = s_arrivee.numero; i != -1; i = precedent[i]) {
            this.chemin.addFirst(i);
        }
        for (int i = 0; i < this.chemin.size(); i++) {
            System.out.print(this.chemin.get(i));
            if (i < this.chemin.size() - 1)
                System.out.print(" -> ");
        }
        System.out.println();
    }
}


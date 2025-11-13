import java.util.ArrayList;
import java.util.Scanner;

public class Itineraire {
    Sommet s_depart, s_arrivee;
    int nb_sommets;

    public Itineraire(Sommet depart, Sommet arrivee, int nb_sommets) {
        this.s_depart = depart;
        this.s_arrivee = arrivee;
        this.nb_sommets = nb_sommets;
    }

    public static Itineraire creer(int nb_sommets) {
        Scanner sc = new Scanner(System.in);
        int d, a;

        do {
            System.out.println("Quel sommet de départ ?");
            d = sc.nextInt();
        } while (d<0 || d>=nb_sommets);

        do {
            System.out.println("Quel sommet d'arrivée ?");
            a = sc.nextInt();
        } while (a<0 || a>=nb_sommets || a==d);

        return new Itineraire(new Sommet(d), new Sommet(a), nb_sommets);
    }

    public void afficher_itineraire() {
        System.out.println(s_depart.numero + " " + s_arrivee.numero);
    }
}


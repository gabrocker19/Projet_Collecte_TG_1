import java.util.Scanner;

public class T1_P1_H1 extends Itineraire {

    public T1_P1_H1(Sommet depart, Sommet arrivee, Graphe graphe) {
        super(depart,arrivee,graphe);
    }

    public static T1_P1_H1 creer(Graphe graphe) {
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

        return new T1_P1_H1(new Sommet(d), new Sommet(a), graphe);
    }
}


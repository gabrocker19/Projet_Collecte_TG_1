import java.util.ArrayList;
import java.util.Scanner;

public class T1_P1_H2 extends Itineraire {

    ArrayList<Sommet> a_visiter;
    int nb_a_visiter;

    public T1_P1_H2(Graphe graphe, ArrayList<Sommet> a_visiter,  int nb_a_visiter) {
        super(a_visiter.getFirst(), a_visiter.getLast(), graphe);
        this.a_visiter=a_visiter;
        this.nb_a_visiter=nb_a_visiter;
    }

    private static boolean contientSommet(ArrayList<Sommet> liste, int numero) {
        for (Sommet s : liste) {
            if (s.numero == numero) {
                return true;
            }
        }
        return false;
    }

    public static T1_P1_H2 creer(Graphe graphe) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Sommet> a_visiter = new ArrayList<>();
        int n;
        do {
            System.out.println("Combien de sommets ? (hors depart/max 6)");
            n = sc.nextInt();
        } while(n<0 || n>6);
        int b;
        for (int i = 0; i < n; i++) {
            do {
                System.out.println("Quel sommet ajouter ? (mettre le depart aussi)");
                b = sc.nextInt();
            } while (contientSommet(a_visiter, b));
            a_visiter.addFirst(new Sommet(b));
        }
        return new T1_P1_H2(graphe, a_visiter, n);
    }

    public void afficher_s_a_visiter () {
        for (int i = 0; i<nb_a_visiter; i++) {
            System.out.println(this.a_visiter.get(i).numero + " ");
        }
    }
}

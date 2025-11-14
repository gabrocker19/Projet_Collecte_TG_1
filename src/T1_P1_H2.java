import java.util.ArrayList;
import java.util.Scanner;

public class T1_P1_H2 extends Itineraire {

    ArrayList<Sommet> a_visiter;
    public T1_P1_H2(Graphe graphe, ArrayList<Sommet> a_visiter) {
        super(a_visiter.getFirst(), a_visiter.getLast(), graphe);
        this.a_visiter=a_visiter;
    }

    public static T1_P1_H2 creer(Graphe graphe) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Sommet> a_visiter = new ArrayList<>();
        int n;
        do {
            System.out.println("Combien de sommets ? (hors depart/max 6)");
            n = sc.nextInt();
        } while(n<0 || n>6);
        for(int i=0;i<n;i++) {
            System.out.println("Quel sommet ajouter ? (mettre le depart aussi)");
                a_visiter.addFirst(new Sommet(sc.nextInt()));
        }
        return new T1_P1_H2(graphe, a_visiter);
    }
}

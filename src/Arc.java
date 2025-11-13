import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arc {
    Sommet s_depart, s_arrivee;
    int distance;

    public Arc (Sommet s1, Sommet s2, int distance) {
        this.s_depart=s1;
        this.s_arrivee=s2;
        this.distance=distance;
    }

    public static void remplir_tableau (ArrayList<Arc> Arcs) {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\arcs_test.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                int a = Integer.parseInt(parties[0].trim());
                int b = Integer.parseInt(parties[1].trim());
                int c = Integer.parseInt(parties[2].trim());
                Sommet s1=new Sommet(a);
                Sommet s2=new Sommet(b);
                Arc arc=new Arc(s1, s2, c);
                Arcs.add(arc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher_arc () {
        System.out.println("Arc : " + this.s_depart.numero + " " + this.s_arrivee.numero + " Distance : " + this.distance);
    }

    public static void afficher_arcs (ArrayList<Arc> Arcs) {
        for (Arc arc : Arcs) {
            arc.afficher_arc();
        }
    }

    public void inverser_arc(Arc arc){
        Sommet temp=arc.s_depart;
        arc.s_depart=arc.s_arrivee;
        arc.s_arrivee=temp;
    }

}

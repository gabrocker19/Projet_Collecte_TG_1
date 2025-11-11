import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arc {
    public Sommet s_depart, s_arrivee;
    public int distance;

    public Arc (Sommet s1, Sommet s2){
        this.s_depart=s1;
        this.s_arrivee=s2;
    }

    public void remplir_tableau (ArrayList<Arc> Arcs) {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\arcs_test.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                int a = Integer.parseInt(parties[0].trim());
                int b = Integer.parseInt(parties[1].trim());
                Sommet s1=new Sommet(a,  0);
                Sommet s2=new Sommet(b, 0);
                Arc arc=new Arc(s1, s2);
                Arcs.add(arc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher_sommet () {
        System.out.println(this.s_depart.numero + " " + this.s_arrivee.numero);
    }
}

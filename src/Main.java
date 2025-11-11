import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Arc> Arcs = new ArrayList<>();

        Arc a = new Arc(null, null);
        a.remplir_tableau(Arcs);
        for (Arc arc : Arcs) {
            arc.afficher_arc();
        }

        Itineraire chemin = new Itineraire(new Sommet(0), new Sommet(3));
        chemin.afficher_itineraire();
    }
}
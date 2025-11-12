import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();
        Sommet s_depart = new Sommet(0);

        Arc a = new Arc(null, null, 0);
        int nb_arcs=a.remplir_tableau(Arcs);
        for (Arc arc : Arcs) {
            arc.afficher_arc();
        }

        Itineraire chemin = new Itineraire(s_depart, new Sommet(3));
        chemin.afficher_itineraire();

        Matrice matrice = new Matrice(nb_arcs);
        matrice.remplir_matrice(Arcs);
        matrice.afficher_matrice();
    }
}
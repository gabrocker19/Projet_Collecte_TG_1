import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();
        Sommet s_depart = new Sommet(0);

        Arc.remplir_tableau(Arcs);
        int nb_arcs=Arc.compter_arcs(Arcs);
        Arc.afficher_arcs(Arcs);

        Matrice matrice = new Matrice(nb_arcs);
        matrice.remplir_matrice(Arcs);

        Graphe graphe = new Graphe(matrice.m);
        graphe.compter_sommets(Arcs);
        //graphe.afficher_matrice();
        System.out.println("Sommets : " + graphe.nb_sommets);
        graphe.afficher_distances(Graphe.Dijkstra(graphe, s_depart.numero));
    }
}
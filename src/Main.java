import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();
        Sommet s_depart = new Sommet(0);

        Arc.remplir_tableau(Arcs);
        //Arc.afficher_arcs(Arcs);

        Matrice m = new Matrice(Arcs);
        m.remplir_adj_long(Arcs);
        m.afficher_matrice_adj();
        m.afficher_nb_sommets();

        Graphe graphe = new Graphe(m);
        ResultatDijkstra r=graphe.Dijkstra(graphe, s_depart.numero);
        graphe.afficher_distances(r.distance);

        Itineraire itineraire = Itineraire.creer(graphe);
        itineraire.afficher_chemin(r.precedent);
    }
}
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();

        Arc.remplir_tableau(Arcs);
        //Arc.afficher_arcs(Arcs);

        Matrice m = new Matrice(Arcs);
        m.remplir_adj_long(Arcs);
        //m.afficher_matrice_adj();
        //m.afficher_matrice_dist();
        //m.afficher_nb_sommets();

        Graphe graphe = new Graphe(m);
        Itineraire itineraire = Itineraire.creer(graphe);
        ResultatDijkstra r = graphe.Dijkstra(graphe, itineraire.s_depart.numero);
        //graphe.afficher_distances(r.distance);
        itineraire.remplir_itineraire(r.precedent, graphe);
        itineraire.afficher_chemin();

    }
}
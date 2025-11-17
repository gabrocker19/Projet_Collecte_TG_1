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

        //T1_P1_H1 i1 = T1_P1_H1.creer(graphe);
        //ResultatDijkstra r = graphe.Dijkstra(graphe, i1.s_depart.numero);
        //graphe.afficher_distances(r.distance);
        //i1.remplir_itineraire(r, graphe);
        //i1.afficher_chemin();

        //T1_P1_H2 i2 = T1_P1_H2.creer(graphe);
        //i2.afficher_s_a_visiter();


        Affichage aff = new Affichage(Arcs, m, graphe);
        aff.lancerGUI();
    }
}
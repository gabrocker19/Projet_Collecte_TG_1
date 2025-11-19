import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        final String nom_fichier3 = "src\\arcs_gen";
        final String nom_fichier2 = "src\\arcs_impairs";
        final String nom_fichier1 = "src\\arcs_pairs";
        final String nom_fichier0 = "src\\arcs_test";

        ArrayList<Arc> Arcs = new ArrayList<>();
        Arc.remplir_tableau(Arcs, nom_fichier3);//change en fct de l'h
        //Arc.afficher_arcs(Arcs);

        Matrice m = new Matrice(Arcs);
        m.remplir_adj_long(Arcs);
        //m.afficher_matrice_adj();
        //m.afficher_matrice_dist();
        //m.afficher_nb_sommets();

        Graphe graphe = new Graphe(m);

        /*T1_P1_H1 i1 = T1_P1_H1.creer(graphe);
        ResultatDijkstra r1 = graphe.Dijkstra(graphe, i1.s_depart.numero);
        graphe.afficher_distances(r1.distance);
        i1.remplir_itineraire(r1, graphe);
        i1.afficher_chemin();
        Graphstream.creer_Graphstream(Arcs);
        Graphstream.chemin_rouge(i1.chemin);*/

        /*T1_P1_H2 i2 = T1_P1_H2.creer(graphe);
        i2.afficher_sommets_a_visiter();
        i2.calculer_tsp();
        i2.afficher_matrice_tsp();
        i2.afficher_chemin_complet();
        Graphstream.creer_Graphstream(Arcs);
        Graphstream.chemin_rouge(i2.chemin);*/

        /*T1_P2_H1 i3 = new T1_P2_H1(graphe);
        i3.eulerianCycle();
        i3.afficher_chemin();
        Graphstream.creer_Graphstream(Arcs);
        Graphstream.chemin_rouge(i3.sommets);*/

        /*T1_P2_H2 i4 = new T1_P2_H2(graphe);
        i4.eulerPrime();
        i4.afficher_chemin();
        Graphstream.creer_Graphstream(Arcs);
        Graphstream.chemin_rouge(i4.sommets);*/

        /*T1_P2_H3 i5 = new T1_P2_H3(graphe);
        i5.chinesePostman();
        i5.afficher_chemin();
        Graphstream.creer_Graphstream(Arcs);
        Graphstream.chemin_rouge(i5.sommets);*/

        Affichage aff = new Affichage(Arcs, m, graphe);
        aff.lancerGUI();
    }
}
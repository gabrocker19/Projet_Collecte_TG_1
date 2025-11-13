import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();
        Sommet s_depart = new Sommet(0);

        Arc.remplir_tableau(Arcs);
        //Arc.afficher_arcs(Arcs);

        Matrice m_adj = new Matrice(Arcs);
        Matrice m_dist = new Matrice(Arcs);
        m_adj.remplir_m_adj(Arcs);
        m_dist.remplir_m_dist(Arcs);

        Graphe graphe = new Graphe(m_adj, m_dist);
        graphe.afficher_matrice_adj();
        System.out.println("Sommets : " + graphe.nb_sommets);
        graphe.afficher_distances(Graphe.Dijkstra(graphe, s_depart.numero));
    }
}
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<>();
        Sommet s_depart = new Sommet(0);

        Arc.remplir_tableau(Arcs);
        //Arc.afficher_arcs(Arcs);

        Matrice m_adj = new Matrice(Arcs);
        m_adj.remplir_adj_long(Arcs);
        //m_adj.afficher_matrice_adj();
        //m_adj.afficher_nb_sommets();

        Graphe graphe = new Graphe(m_adj);
        int[] longeurs=graphe.Dijkstra(graphe, s_depart.numero);
        //graphe.afficher_distances(longeurs);

        Itineraire itineraire = Itineraire.creer(graphe.nb_sommets);
        itineraire.afficher_itineraire();
    }
}
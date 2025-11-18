import java.util.ArrayList;
import java.util.Stack;

public class Graphe {
    Matrice matrice;
    int nb_sommets;
    ArrayList<Sommet> a_visiter;

    public Graphe(Matrice matrice) {
        this.matrice=matrice;
        this.nb_sommets=matrice.nb_sommets;
        this.a_visiter = new ArrayList<>();
    }

    public static int plus_petit_arc (int[] taille, boolean[] visite) {
        int min = Integer.MAX_VALUE;
        int index_min = -1;
        for (int i = 0; i < taille.length; i++) {
            if (taille[i] < min) {
                if (!visite[i]) {
                    min = taille[i];
                    index_min = i;
                }
            }
        }
        return index_min;
    }

    public ResultatDijkstra Dijkstra (Graphe g,  int num_s_depart) {
        int[] distance = new int[g.nb_sommets];
        int[] precedent = new int[g.nb_sommets];
        boolean[] visite = new boolean[g.nb_sommets];
        int d;
        for (int i = 0; i < g.nb_sommets; i++) {
            distance[i] = Integer.MAX_VALUE;
            visite[i] = false;
            precedent[i]=-1;
        }
        distance[num_s_depart] = 0;

        for (int i = 0; i < g.nb_sommets; i++) {
            int num_s_proche = plus_petit_arc(distance, visite);
            if(num_s_proche == Integer.MAX_VALUE)
                return new ResultatDijkstra(distance, precedent);
            visite[num_s_proche] = true;
            for (int j = 0; j < g.nb_sommets; j++) {
                if (!visite[j]) {
                    if (g.matrice.adj[num_s_proche][j] != 0) {
                        d = distance[num_s_proche] + g.matrice.longeurs[num_s_proche][j];
                        if (d < distance[j]) {
                            distance[j] = d;
                            precedent[j] = num_s_proche;
                        }
                    }
                }
            }
        }
        return new ResultatDijkstra(distance, precedent);
    }

    public void afficher_distances(int[] distance) {
        System.out.println("Distances par rapport au point de départ :");
        for (int i = 0; i < distance.length; i++) {
            System.out.println("S" + i + " : " + distance[i]);
        }
    }

}

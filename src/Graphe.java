import java.util.ArrayList;

public class Graphe {
    public Matrice matrice_adj;
    int nb_sommets;

    public Graphe(Matrice matrice_adj) {
        this.matrice_adj=matrice_adj;
        this.nb_sommets=matrice_adj.nb_sommets;
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

    public int[] Dijkstra (Graphe g,  int num_s_depart) {
        int[] distance = new int[g.nb_sommets];
        boolean[] visite = new boolean[g.nb_sommets];
        int d;

        for (int i = 0; i < g.nb_sommets; i++) {
            distance[i] = Integer.MAX_VALUE;
            visite[i] = false;
        }
        distance[num_s_depart] = 0;

        for (int i = 0; i < g.nb_sommets; i++) {
            int num_s_proche = plus_petit_arc(distance, visite);
            if(num_s_proche == Integer.MAX_VALUE)
                return distance;
            visite[num_s_proche] = true;
            for (int j = 0; j < g.nb_sommets; j++) {
                if (!visite[j])
                    if (g.matrice_adj.m[num_s_proche][j] != 0){
                        d = distance[num_s_proche] +  g.matrice_adj.longeurs[num_s_proche][j];
                        if (d < distance[j])
                            distance[j] = d;
                    }
            }
        }
        return distance;
    }

    public void afficher_distances(int[] distance) {
        for (int i = 0; i < distance.length; i++) {
            System.out.println("S" + i + " : " + distance[i]);
        }
    }
}

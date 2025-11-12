import java.util.ArrayList;

public class Graphe {
    public int[][] matrice_adj;
    int nb_sommets;

    public Graphe(int[][] matrice_adj){
        this.matrice_adj=matrice_adj;
    }

    public void compter_sommets (ArrayList<Arc> Arcs) {
        int n = 0;
        for (int i = 0; i < Arcs.size(); i++) {
            if (Arcs.get(i).s_depart.numero > n)
                n = Arcs.get(i).s_depart.numero;
            if (Arcs.get(i).s_arrivee.numero > n)
                n = Arcs.get(i).s_arrivee.numero;
        }
        this.nb_sommets = n+1;
    }

    public void afficher_matrice () {
        for (int i=0; i<nb_sommets; i++){
            for (int j=0; j<nb_sommets; j++){
                System.out.print(matrice_adj[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public void ajouter_arc (int num_s_depart, int num_s_arrivee, int distance){
        matrice_adj[num_s_depart][num_s_arrivee]=distance;
        matrice_adj[num_s_arrivee][num_s_depart]=distance;
    }

    public static int plus_petit_arc (int[] distance, boolean[] visite) {
        int min = Integer.MAX_VALUE;
        int index_min = -1;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] < min) {
                if (!visite[i]) {
                    min = distance[i];
                    index_min = i;
                }
            }
        }
        return index_min;
    }

    public static int[] Dijkstra (Graphe g,  int num_s_depart) {
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
                    if (g.matrice_adj[num_s_proche][j] != 0){
                        d = distance[num_s_proche] +  g.matrice_adj[num_s_proche][j];
                        if (d < distance[j])
                            distance[j] = d;
                    }
            }
        }
        return distance;
    }

    public void afficher_distances (int[] distance) {
        for (int j : distance) {
            System.out.print(j + " ");
        }
    }
}

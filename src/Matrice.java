import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matrice {
    public int[][] m;
    public int nb_arcs, nb_sommets;

    public Matrice (ArrayList<Arc> Arcs){
        // init nb arcs
        this.nb_arcs=Arcs.size();

        // init nb sommets
        int n = 0;
        for (Arc arc : Arcs) {
            if (arc.s_depart.numero > n)
                n = arc.s_depart.numero;
            if (arc.s_arrivee.numero > n)
                n = arc.s_arrivee.numero;
        }
        this.nb_sommets = n+1;

        //init matrice
        this.m = new int[this.nb_sommets][this.nb_sommets];
        for (int i=0; i<this.nb_sommets; i++){
            for (int j=0; j<this.nb_sommets; j++){
                this.m[i][j]=0;
            }
        }
    }
//override
    public void remplir_m_adj (ArrayList<Arc> Arcs) {
        for (int i=0; i<this.nb_sommets; i++){
            this.m[Arcs.get(i).s_depart.numero][Arcs.get(i).s_arrivee.numero]=1;
            this.m[Arcs.get(i).s_arrivee.numero][Arcs.get(i).s_depart.numero]=1;
        }
    }

    public int[] remplir_tab_dist (ArrayList<Arc> Arcs) {
        int[] remplir_tab = new int[this.nb_sommets];


        return remplir_tab;
    }

    public void remplir_m_dist (ArrayList<Arc> Arcs) {
        for (int i=0; i<this.nb_sommets; i++){
            this.m[Arcs.get(i).s_depart.numero][Arcs.get(i).s_arrivee.numero]=Arcs.get(i).distance;
            this.m[Arcs.get(i).s_arrivee.numero][Arcs.get(i).s_depart.numero]=Arcs.get(i).distance;
        }
    }
}

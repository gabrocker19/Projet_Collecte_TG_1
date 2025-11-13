import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matrice {
    public int[][] m, longeurs;
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
        this.longeurs = new int[this.nb_sommets][this.nb_sommets];
        for (int i=0; i<this.nb_sommets; i++){
            for (int j=0; j<this.nb_sommets; j++){
                this.m[i][j]=0;
                this.longeurs[i][j]=Integer.MAX_VALUE;
            }
        }
    }

    public void remplir_adj_long (ArrayList<Arc> Arcs) {
        for (int i=0; i<this.nb_arcs; i++){
            this.m[Arcs.get(i).s_depart.numero][Arcs.get(i).s_arrivee.numero]=1;
            this.m[Arcs.get(i).s_arrivee.numero][Arcs.get(i).s_depart.numero]=1;
            this.longeurs[Arcs.get(i).s_depart.numero][Arcs.get(i).s_arrivee.numero]=Arcs.get(i).distance;
            this.longeurs[Arcs.get(i).s_arrivee.numero][Arcs.get(i).s_depart.numero]=Arcs.get(i).distance;
        }
    }

    public void afficher_matrice_adj () {
        for (int i=0; i<nb_sommets; i++){
            for (int j=0; j<nb_sommets; j++){
                System.out.print(this.m[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public void afficher_nb_sommets () {
        System.out.println("Sommets : " + this.nb_sommets);
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matrice {
    public int[][] m;
    public int nb_arcs;

    public Matrice (int nb_arcs){
        for (int i=0; i<nb_arcs; i++){
            for (int j=0; j<nb_arcs; j++){
                this.m[i][j]=0;
                this.nb_arcs=nb_arcs;
            }
        }
    }

    public void remplir_matrice (ArrayList<Arc> Arcs) {
        for (int i=0; i<nb_arcs; i++){
            this.m[Arcs.get(i).s_depart.numero][Arcs.get(i).s_arrivee.numero]=1;
        }
    }

    public void afficher_matrice () {
        for (int i=0; i<nb_arcs; i++){
            for (int j=0; j<nb_arcs; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}

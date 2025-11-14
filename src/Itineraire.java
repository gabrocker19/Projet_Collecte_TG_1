import java.util.ArrayList;

public abstract class Itineraire {
    Sommet s_depart, s_arrivee;
    Graphe graphe;
    ArrayList<Sommet> chemin = new ArrayList<>();
    ArrayList<Integer> distances = new ArrayList<>();

    public Itineraire(Sommet depart, Sommet arrivee, Graphe graphe) {
        this.s_depart = depart;
        this.s_arrivee = arrivee;
        this.graphe = graphe;
    }

    public static Itineraire creer(Graphe graphe) {
        return null;
    }

    public void remplir_itineraire(int[] precedent, Graphe graphe) {
        for (int i = s_arrivee.numero; i != -1; i = precedent[i]) {
            this.chemin.addFirst(new Sommet(i));
        }
        for (int i=0; i<this.chemin.size(); i++) {
            try {
                this.distances.addFirst(graphe.matrice.longeurs[this.chemin.get(i).numero][this.chemin.get(i+1).numero]);
            } catch (IndexOutOfBoundsException e) {}
        }
    }

    public void afficher_chemin() {
        int j = this.distances.size()-1;
        for (Sommet sommet : this.chemin) {
            System.out.print(sommet.numero);
            if (j >= 0) {
                System.out.print(" -> (" + this.distances.get(j) + ") -> ");
                j--;
            }
        }
        System.out.println();
    }

    public String genererChemin() {
        StringBuilder sb = new StringBuilder();
        int j = this.distances.size() - 1;
        for (Sommet sommet : this.chemin) {
            sb.append(sommet.numero);
            if (j >= 0) {
                sb.append(" -> (").append(this.distances.get(j)).append(") -> ");
                j--;
            }
        }
        return sb.toString();
    }
}

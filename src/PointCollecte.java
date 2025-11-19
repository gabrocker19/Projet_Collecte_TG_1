public class PointCollecte {
    Sommet sommet;
    int contenance; // Quantité de déchets à collecter

    public PointCollecte(Sommet sommet, int contenance) {
        this.sommet = sommet;
        this.contenance = contenance;
    }

    public PointCollecte(int numeroSommet, int contenance) {
        this.sommet = new Sommet(numeroSommet);
        this.contenance = contenance;
    }

    @Override
    public String toString() {
        return "Point S" + sommet.numero + " (contenance: " + contenance + ")";
    }
}
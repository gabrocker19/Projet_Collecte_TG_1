import java.util.ArrayList;
import java.util.HashSet;

public class T2_Approche1 {

    private Graphe graphe;
    private ArrayList<PointCollecte> pointsCollecte;
    private ArrayList<Sommet> parcours; // Le parcours trouvé
    private int distanceTotale;
    private ArrayList<Tournee> tournees; // Pour le découpage avec capacités

    // Classe interne pour représenter une tournée
    public class Tournee {
        ArrayList<PointCollecte> points;
        int chargeActuelle;
        int distance;
        ArrayList<Sommet> cheminDetaille; // Le chemin complet avec tous les sommets intermédiaires

        public Tournee() {
            this.points = new ArrayList<>();
            this.chargeActuelle = 0;
            this.distance = 0;
            this.cheminDetaille = new ArrayList<>();
        }
    }

    public T2_Approche1(Graphe graphe, ArrayList<PointCollecte> pointsCollecte) {
        this.graphe = graphe;
        this.pointsCollecte = pointsCollecte;
        this.parcours = new ArrayList<>();
        this.distanceTotale = 0;
        this.tournees = new ArrayList<>();
    }

    // === ALGORITHME DU PLUS PROCHE VOISIN ===
    public void calculerTournee() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║     APPROCHE 1 : PLUS PROCHE VOISIN (GREEDY)           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        if (pointsCollecte.isEmpty()) {
            System.out.println("Aucun point de collecte défini.");
            return;
        }

        // VÉRIFICATION : tous les points existent-ils dans le graphe ?
        for (PointCollecte p : pointsCollecte) {
            if (p.sommet.numero < 0 || p.sommet.numero >= graphe.nb_sommets) {
                System.out.println("❌ ERREUR : Le sommet S" + p.sommet.numero +
                        " n'existe pas dans le graphe.");
                System.out.println("   Le graphe contient " + graphe.nb_sommets +
                        " sommets (S0 à S" + (graphe.nb_sommets - 1) + ")");
                return;
            }
        }

        parcours.clear();
        distanceTotale = 0;

        // On commence toujours au dépôt (premier point de la liste)
        PointCollecte depot = pointsCollecte.get(0);
        parcours.add(depot.sommet);

        // Points non encore visités
        HashSet<Integer> nonVisites = new HashSet<>();
        for (int i = 1; i < pointsCollecte.size(); i++) {
            nonVisites.add(i);
        }

        // Position actuelle
        int positionActuelle = depot.sommet.numero;

        System.out.println("Départ du dépôt : S" + positionActuelle);
        System.out.println("Points à visiter : " + (pointsCollecte.size() - 1) + "\n");

        // Tant qu'il reste des points à visiter
        while (!nonVisites.isEmpty()) {
            int indexPlusProche = -1;
            int distanceMin = Integer.MAX_VALUE;

            // Calculer Dijkstra depuis la position actuelle
            ResultatDijkstra resultat = graphe.Dijkstra(graphe, positionActuelle);

            // Trouver le point le plus proche non encore visité
            for (int index : nonVisites) {
                PointCollecte point = pointsCollecte.get(index);
                int distance = resultat.distance[point.sommet.numero];

                if (distance < distanceMin) {
                    distanceMin = distance;
                    indexPlusProche = index;
                }
            }

            if (indexPlusProche == -1 || distanceMin == Integer.MAX_VALUE) {
                System.out.println("⚠ Impossible de trouver un chemin vers les points restants.");
                break;
            }

            // Ajouter le point le plus proche au parcours
            PointCollecte pointChoisi = pointsCollecte.get(indexPlusProche);
            parcours.add(pointChoisi.sommet);
            distanceTotale += distanceMin;
            nonVisites.remove(indexPlusProche);

            System.out.println("→ Aller à S" + pointChoisi.sommet.numero +
                    " (distance: " + distanceMin +
                    ", contenance: " + pointChoisi.contenance + ")");

            // Mise à jour de la position actuelle
            positionActuelle = pointChoisi.sommet.numero;
        }

        // Retour au dépôt
        ResultatDijkstra retour = graphe.Dijkstra(graphe, positionActuelle);
        int distanceRetour = retour.distance[depot.sommet.numero];
        parcours.add(depot.sommet);
        distanceTotale += distanceRetour;

        System.out.println("→ Retour au dépôt S" + depot.sommet.numero +
                " (distance: " + distanceRetour + ")");
        System.out.println("\n=== Distance totale : " + distanceTotale + " ===\n");
    }

    // === DÉCOUPAGE EN TOURNÉES AVEC CONTRAINTES DE CAPACITÉ ===
    public void decouper_en_tournees(int capaciteMax) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         DÉCOUPAGE EN TOURNÉES (CAPACITÉ MAX: " +
                String.format("%3d", capaciteMax) + ")       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        if (parcours.isEmpty()) {
            System.out.println("Aucun parcours calculé. Exécutez d'abord calculerTournee().");
            return;
        }

        tournees.clear();
        Tournee tourneeActuelle = new Tournee();
        PointCollecte depot = pointsCollecte.get(0);

        // Parcourir tous les points (sauf premier et dernier qui sont le dépôt)
        for (int i = 1; i < parcours.size() - 1; i++) {
            Sommet s = parcours.get(i);

            // Trouver le point de collecte correspondant
            PointCollecte point = null;
            for (PointCollecte p : pointsCollecte) {
                if (p.sommet.numero == s.numero) {
                    point = p;
                    break;
                }
            }

            if (point == null) continue;

            // Vérifier si on peut ajouter ce point à la tournée actuelle
            if (tourneeActuelle.chargeActuelle + point.contenance <= capaciteMax) {
                // Ajout possible
                tourneeActuelle.points.add(point);
                tourneeActuelle.chargeActuelle += point.contenance;
            } else {
                // Capacité dépassée → finaliser la tournée actuelle

                if (!tourneeActuelle.points.isEmpty()) {
                    calculerDistanceTournee(tourneeActuelle, depot);
                    tournees.add(tourneeActuelle);
                }

                // Créer une nouvelle tournée avec ce point
                tourneeActuelle = new Tournee();
                tourneeActuelle.points.add(point);
                tourneeActuelle.chargeActuelle = point.contenance;
            }
        }

        // Ajouter la dernière tournée si elle n'est pas vide
        if (!tourneeActuelle.points.isEmpty()) {
            calculerDistanceTournee(tourneeActuelle, depot);
            tournees.add(tourneeActuelle);
        }

        System.out.println("✓ Nombre de tournées créées : " + tournees.size());
    }

    // Calculer la distance d'une tournée (dépôt -> points -> dépôt)
    private void calculerDistanceTournee(Tournee t, PointCollecte depot) {
        if (t.points.isEmpty()) {
            t.distance = 0;
            return;
        }

        int distance = 0;
        int positionActuelle = depot.sommet.numero;

        t.cheminDetaille.add(depot.sommet);

        // Parcourir tous les points de la tournée dans l'ordre
        for (PointCollecte point : t.points) {
            ResultatDijkstra res = graphe.Dijkstra(graphe, positionActuelle);
            distance += res.distance[point.sommet.numero];
            t.cheminDetaille.add(point.sommet);
            positionActuelle = point.sommet.numero;
        }

        // Retour au dépôt
        ResultatDijkstra retour = graphe.Dijkstra(graphe, positionActuelle);
        distance += retour.distance[depot.sommet.numero];
        t.cheminDetaille.add(depot.sommet);

        t.distance = distance;
    }

    // === AFFICHAGES ===
    public void afficherTournee() {
        if (parcours.isEmpty()) {
            System.out.println("Aucune tournée calculée.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              RÉSULTAT - PLUS PROCHE VOISIN             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        System.out.println("Parcours trouvé :");
        for (int i = 0; i < parcours.size(); i++) {
            System.out.print("S" + parcours.get(i).numero);

            if (i < parcours.size() - 1) {
                // Calculer la distance entre ce sommet et le suivant
                int u = parcours.get(i).numero;
                int v = parcours.get(i + 1).numero;
                ResultatDijkstra res = graphe.Dijkstra(graphe, u);
                int dist = res.distance[v];
                System.out.print(" -> (" + dist + ") -> ");
            }
        }

        System.out.println("\n\nDistance totale : " + distanceTotale);
    }

    public void afficherTournees() {
        if (tournees.isEmpty()) {
            System.out.println("Aucune tournée créée. Exécutez d'abord decouper_en_tournees().");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          TOURNÉES AVEC CONTRAINTES DE CAPACITÉ         ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        int distanceTotaleTournees = 0;

        for (int i = 0; i < tournees.size(); i++) {
            Tournee t = tournees.get(i);
            System.out.println("─────────────────────────────────────────────────────────");
            System.out.println("Tournée " + (i + 1) + " :");
            System.out.print("  D");

            for (PointCollecte p : t.points) {
                System.out.print(" → S" + p.sommet.numero + "(c=" + p.contenance + ")");
            }
            System.out.println(" → D");

            System.out.println("  Charge totale : " + t.chargeActuelle);
            System.out.println("  Distance : " + t.distance);

            distanceTotaleTournees += t.distance;
        }

        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("\n=== Distance totale de toutes les tournées : " +
                distanceTotaleTournees + " ===\n");
    }

    // === MÉTHODES POUR L'IHM (génération de texte) ===
    public String genererTexteResultat() {
        if (parcours.isEmpty()) {
            return "Aucune tournée calculée.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("═══ APPROCHE PLUS PROCHE VOISIN ═══\n\n");

        sb.append("Points de collecte à visiter :\n");
        for (int i = 0; i < pointsCollecte.size(); i++) {
            PointCollecte p = pointsCollecte.get(i);
            if (i == 0) {
                sb.append("  [DÉPÔT] S").append(p.sommet.numero).append("\n");
            } else {
                sb.append("  P").append(i).append(" : S").append(p.sommet.numero)
                        .append(" (contenance: ").append(p.contenance).append(")\n");
            }
        }

        sb.append("\nParcours trouvé :\n");
        for (int i = 0; i < parcours.size(); i++) {
            sb.append("S").append(parcours.get(i).numero);

            if (i < parcours.size() - 1) {
                int u = parcours.get(i).numero;
                int v = parcours.get(i + 1).numero;
                ResultatDijkstra res = graphe.Dijkstra(graphe, u);
                int dist = res.distance[v];
                sb.append(" → (").append(dist).append(") → ");
            }
        }

        sb.append("\n\nDistance totale : ").append(distanceTotale);

        return sb.toString();
    }

    public String genererTexteTournees() {
        if (tournees.isEmpty()) {
            return "Aucune tournée créée. Veuillez d'abord découper en tournées.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("═══ TOURNÉES AVEC CAPACITÉS ═══\n\n");

        int distanceTotaleTournees = 0;

        for (int i = 0; i < tournees.size(); i++) {
            Tournee t = tournees.get(i);
            sb.append("Tournée ").append(i + 1).append(" :\n");
            sb.append("  D");

            for (PointCollecte p : t.points) {
                sb.append(" → S").append(p.sommet.numero)
                        .append("(c=").append(p.contenance).append(")");
            }
            sb.append(" → D\n");

            sb.append("  Charge : ").append(t.chargeActuelle).append("\n");
            sb.append("  Distance : ").append(t.distance).append("\n\n");

            distanceTotaleTournees += t.distance;
        }

        sb.append("Distance totale : ").append(distanceTotaleTournees);

        return sb.toString();
    }

    // === GETTERS ===
    public ArrayList<Sommet> getParcours() {
        return parcours;
    }

    public int getDistanceTotale() {
        return distanceTotale;
    }

    public ArrayList<Tournee> getTournees() {
        return tournees;
    }

    public ArrayList<PointCollecte> getPointsCollecte() {
        return pointsCollecte;
    }
}
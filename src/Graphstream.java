import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;

public class Graphstream {

    private static Graph graph;

    // Création du graphe
    public static void creer_Graphstream(ArrayList<Arc> Arcs) {

        System.setProperty("org.graphstream.ui", "swing");

        graph = new SingleGraph("graphstream");

        // Stylesheet
        graph.setAttribute("ui.stylesheet",
                "node {" +
                        "   size: 30px;" +
                        "   fill-color: lightblue;" +
                        "   text-size: 20px;" +
                        "   text-color: black;" +
                        "   text-alignment: center;" +
                        "}" +

                        "edge.chemin {" +
                        "   fill-color: red;" +
                        "   size: 5px;" +
                        "}");

        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        // Ajout des sommets et arêtes
        for (Arc arc : Arcs) {

            String depart = String.valueOf(arc.s_depart.numero);
            String arrivee = String.valueOf(arc.s_arrivee.numero);

            // Ajout des nœuds si pas déjà présents
            if (graph.getNode(depart) == null) {
                Node n = graph.addNode(depart);
                n.setAttribute("ui.label", depart);
            }

            if (graph.getNode(arrivee) == null) {
                Node n = graph.addNode(arrivee);
                n.setAttribute("ui.label", arrivee);
            }

            String idAB = depart + "-" + arrivee;
            String idBA = arrivee + "-" + depart;

            // création d'arc
            if (graph.getEdge(idAB) == null && graph.getEdge(idBA) == null) {
                Edge e = graph.addEdge(idAB, depart, arrivee, false);  // false veut dire que le graph est non orienté
                e.setAttribute("ui.label", arc.distance);
                e.setAttribute("ui.class", "normal");
            }
        }

        graph.display();
    }

    // fonction qui colorie le chemin en rouge
    public static void chemin_rouge(ArrayList<Sommet> chemin) {
        for (int i = 0; i < chemin.size() - 1; i++) {

            String a = String.valueOf(chemin.get(i).numero);
            String b = String.valueOf(chemin.get(i + 1).numero);

            String idAB = a + "-" + b;
            String idBA = b + "-" + a;

            Edge e = graph.getEdge(idAB);
            if (e == null) e = graph.getEdge(idBA);

            if (e != null) {
                e.setAttribute("ui.class", "chemin");
            } else {
                System.err.println("Arête non trouvée : " + idAB + " / " + idBA);
            }
        }
    }
}

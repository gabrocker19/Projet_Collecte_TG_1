import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.ArrayList;
public class Graphstream {

    public static void creer_Graphstream(ArrayList<Arc> Arcs) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("graphstream");

        // Styles CSS
        graph.setAttribute("ui.stylesheet",
                "node { size: 30px; fill-color: lightblue; text-size: 20px; text-color: black; text-alignment: center; }" +
                        "edge { text-size: 15px; text-color: black; }"
        );
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        // Parcours des arcs
        for (Arc arc : Arcs) {
            String depart = String.valueOf(arc.s_depart.numero);
            String arrivee = String.valueOf(arc.s_arrivee.numero);

            // Création des noeuds si non existants
            if (graph.getNode(depart) == null) {
                Node n1 = graph.addNode(depart);
                n1.setAttribute("ui.label", depart);
            }
            if (graph.getNode(arrivee) == null) {
                Node n2 = graph.addNode(arrivee);
                n2.setAttribute("ui.label", arrivee);
            }

            // Vérification des deux sens pour le graphe non orienté
            String idAB = depart + "-" + arrivee;
            String idBA = arrivee + "-" + depart;

            if (graph.getEdge(idAB) == null && graph.getEdge(idBA) == null) {
                org.graphstream.graph.Edge e = graph.addEdge(idAB, depart, arrivee); // non orienté
                e.setAttribute("ui.label", arc.distance);
                e.setAttribute("weight", arc.distance);
            }
        }

        // Affichage du graphe
        graph.display();
    }
}

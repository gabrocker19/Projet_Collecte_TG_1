import java.util.ArrayList;

public class Itineraire {
    public ArrayList<Arc> Arcs=new ArrayList<>();
    public ArrayList<Sommet> PtsCollecte=new ArrayList<>();
    public Sommet depart;

    public Itineraire (Sommet depart, Sommet collecte){
        this.depart=new Sommet(0);
        this.PtsCollecte.add(collecte);

        //ALGO///////////
        this.Arcs.add(new Arc(new Sommet(0),new Sommet(1)));
        this.Arcs.add(new Arc(new Sommet(1),new Sommet(3)));
        this.Arcs.add(new Arc(new Sommet(1),new Sommet(3)));
        this.Arcs.add(new Arc(new Sommet(0),new Sommet(1)));
    }

    public void afficher_itineraire() {
        for (Arc arc : Arcs) {
            System.out.println(arc.s_depart.numero + " " + arc.s_arrivee.numero);
        }
    }
}

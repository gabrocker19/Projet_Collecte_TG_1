import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ArrayList<Arc> Arcs = new ArrayList<Arc>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\arcs_test.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                int a = Integer.parseInt(parties[0].trim());
                int b = Integer.parseInt(parties[1].trim());
                Sommet s1=new Sommet(a,  0);
                Sommet s2=new Sommet(b, 0);
                Arc arc=new Arc(s1, s2);
                Arcs.add(arc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Arc arc : Arcs) {
            System.out.println(arc.s_depart.numero + " " + arc.s_arrivee.numero);
        }
    }
}
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String[]> arcs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("arcs_test.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                // Enlève les espaces et sépare les arcs par ";"
                String[] couples = ligne.trim().split(";");
                for (String couple : couples) {
                    couple = couple.trim();
                    if (!couple.isEmpty()) {
                        // Sépare les deux sommets par ","
                        String[] sommets = couple.split(",");
                        if (sommets.length == 2) {
                            arcs.add(new String[]{sommets[0].trim(), sommets[1].trim()});
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Affichage du contenu
        for (String[] arc : arcs) {
            System.out.println("Arc : " + arc[0] + " -> " + arc[1]);
        }
    }
}
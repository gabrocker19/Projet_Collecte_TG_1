import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Sommet {
    public int numero;

    public Sommet(int numero){
        this.numero=numero;
    }

    public int getDegre() {
        int degre=0;
        try (BufferedReader br = new BufferedReader(new FileReader("src\\arcs_test.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                int a = Integer.parseInt(parties[0].trim());
                int b = Integer.parseInt(parties[1].trim());
                if (this.numero==a || this.numero==b){
                    degre++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         return degre;
    }
}

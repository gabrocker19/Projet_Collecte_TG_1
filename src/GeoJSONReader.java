import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GeoJSONReader {

    public static class Coord {
        double lon, lat;
        public Coord(double lon, double lat) { this.lon = lon; this.lat = lat; }

        // clé pour HashMap
        public String key() {
            return lon + "," + lat;
        }
    }

    public static class RoadSegment {
        String name;
        List<Coord> points;

        public RoadSegment(String name, List<Coord> points) {
            this.name = name;
            this.points = points;
        }
    }

    public static void lireGeoJSON(String cheminFichier) throws IOException {

        String contenu = Files.readString(Paths.get(cheminFichier));
        JSONObject json = new JSONObject(contenu);
        JSONArray features = json.getJSONArray("features");

        List<RoadSegment> segments = new ArrayList<>();

        // Pour détecter les carrefours :
        // coordonnée → liste des rues qui passent par ce point
        Map<String, Set<String>> pointToRoads = new HashMap<>();

        // Lecture des features
        for (int i = 0; i < features.length(); i++) {

            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            JSONObject geometry = feature.getJSONObject("geometry");

            String name = properties.optString("name", "SANS_NOM");

            if (!geometry.getString("type").equals("LineString"))
                continue;

            JSONArray coords = geometry.getJSONArray("coordinates");
            List<Coord> points = new ArrayList<>();

            for (int c = 0; c < coords.length(); c++) {
                JSONArray p = coords.getJSONArray(c);
                double lon = p.getDouble(0);
                double lat = p.getDouble(1);

                Coord coord = new Coord(lon, lat);
                points.add(coord);

                // On enregistre : cette rue passe par ce point
                pointToRoads
                        .computeIfAbsent(coord.key(), k -> new HashSet<>())
                        .add(name);
            }

            segments.add(new RoadSegment(name, points));
        }

        // Maintenant : détecter les rues reliées par carrefour
        System.out.println("\n=== RUES RELIEES PAR CARREFOURS ===");

        // évite affichage en doublon
        Set<String> dejaVu = new HashSet<>();

        for (String key : pointToRoads.keySet()) {
            Set<String> roads = pointToRoads.get(key);

            if (roads.size() < 2)
                continue;  // pas un carrefour

            List<String> list = new ArrayList<>(roads);

            // Toutes les paires de rues reliées
            for (int a = 0; a < list.size(); a++) {
                for (int b = a+1; b < list.size(); b++) {

                    String r1 = list.get(a);
                    String r2 = list.get(b);

                    String signature = r1 + "<->" + r2;
                    String signature2 = r2 + "<->" + r1;

                    if (!dejaVu.contains(signature) && !dejaVu.contains(signature2)) {
                        System.out.println(r1 + "  <==>  " + r2 + "   via  " + key);
                        dejaVu.add(signature);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        lireGeoJSON("src\\test.json");
    }
}

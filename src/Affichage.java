import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.formdev.flatlaf.FlatLightLaf;


public class Affichage {

    private ArrayList<Arc> arcs;
    private Matrice matrice;
    private Graphe graphe;
    private Itineraire T1_P1_H1;
    private T1_P1_H2 T1_P1_H2;
    private T1_P2_H1 T1_P2_H1;
    private T1_P2_H2 T1_P2_H2;
    private T1_P2_H3 T1_P2_H3;

    // Noms de fichiers pour les différents cas
    private static final String FICHIER_GENERAL = "src\\arcs_gen";
    private static final String FICHIER_PAIRS   = "src\\arcs_pairs";
    private static final String FICHIER_IMPAIRS = "src\\arcs_impairs";
    private static final String FICHIER_TEST    = "src\\arcs_test";

    // === Palette de couleurs (Option 1) ===
    private static final Color BG_MAIN      = new Color(245, 248, 255);   // fond fenêtre
    private static final Color BG_PANEL     = new Color(235, 240, 252);   // panneaux internes
    private static final Color BTN_NORMAL   = new Color(210, 225, 255);   // bouton normal
    private static final Color BTN_HOVER    = new Color(190, 210, 250);   // hover
    private static final Color BTN_PRESSED  = new Color(170, 195, 240);   // clic
    private static final Color BTN_BORDER   = new Color(150, 170, 210);
    private static final Color TITLE_COLOR  = new Color(40, 70, 120);
    private static final Color TEXT_COLOR   = new Color(30, 30, 30);


    public Affichage(ArrayList<Arc> arcs, Matrice matrice, Graphe graphe) {
        this.arcs = arcs;
        this.matrice = matrice;
        this.graphe = graphe;
        chargerGrapheDepuisFichier(FICHIER_TEST);


    }

    // Recharge arcs / matrice / graphe à partir d'un fichier donné
    private void chargerGrapheDepuisFichier(String nomFichierSansExtension) {
        // On recrée la liste d'arcs
        arcs = new ArrayList<>();
        Arc.remplir_tableau(arcs, nomFichierSansExtension);

        // On recrée la matrice à partir des arcs
        matrice = new Matrice(arcs);
        matrice.remplir_adj_long(arcs);

        // On recrée le graphe
        graphe = new Graphe(matrice);
    }


    // ====== LANCEMENT DE L'IHM ======
    public void lancerGUI() {
        try {
            // Option 6 : FlatLaf moderne
            FlatLightLaf.setup();
        } catch (Exception e) {
            // Si FlatLaf plante ou n'est pas dispo, on peut garder Nimbus en secours
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // on laisse le LAF par défaut
            }
        }

        SwingUtilities.invokeLater(this::creerMenuPrincipal);
    }


    // ====== MENU PRINCIPAL ======
    private void creerMenuPrincipal() {
        JFrame frame = new JFrame("Projet Graphes - Menu principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 660);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_MAIN);

        JLabel titre = new JLabel("Choisir un thème", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(TITLE_COLOR);
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 12, 12));
        centre.setOpaque(false);

        JButton btnTheme1 = createStyledButton("🏠 Thème 1 : Ramassage aux pieds des habitations");
        JButton btnTheme2 = createStyledButton("📍 Thème 2 : Points de collecte");
        JButton btnTheme3 = createStyledButton("🚛 Thème 3 : Planification / Secteurs / Camions");
        JButton btnQuitter = createStyledButton("✖ Quitter");

        btnTheme1.addActionListener(e -> ouvrirMenuTheme1(frame));
        btnTheme2.addActionListener(e -> ouvrirMenuTheme2(frame));
        btnTheme3.addActionListener(e -> ouvrirMenuTheme3(frame));
        btnQuitter.addActionListener(e -> System.exit(0));

        centre.add(btnTheme1);
        centre.add(btnTheme2);
        centre.add(btnTheme3);
        centre.add(btnQuitter);

        panel.add(centre, BorderLayout.CENTER);

        frame.setContentPane(panel);
        showWithZoom(frame);
    }

    // ====== MENU THÈME 1 ======
    private void ouvrirMenuTheme1(JFrame parent) {
        JFrame f = new JFrame("Thème 1 - Ramassage aux pieds des habitations");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Thème 1 : Ramassage aux pieds des habitations", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 10, 10));
        centre.setOpaque(false);

        JButton btnAffichage1 = createStyledButton("Affichages du graphe");
        JButton btnAffichage2 = createStyledButton("Choix problématiques");
        JButton btnRetour     = createStyledButton("Retour au menu principal");

        btnAffichage1.addActionListener(e -> ouvrirSousMenu1AffichagesTheme1(f));
        btnAffichage2.addActionListener(e -> ouvrirSousMenu2AffichagesTheme1(f));
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnAffichage1);
        centre.add(btnAffichage2);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);    }

    private void ouvrirSousMenu1AffichagesTheme1(JFrame parent) {
        JFrame f = new JFrame("Thème 1 - Affichages du graphe");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Affichages (Thème 1)", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 8, 8));
        centre.setOpaque(false);

        JButton btnArcs    = createStyledButton("Afficher les sommet et arcs");
        JButton btnAdj     = createStyledButton("Afficher la matrice d'adjacence");
        JButton btnDistMat = createStyledButton("Afficher la matrice des distances");
        JButton btnGraphe   = createStyledButton("Afficher le graphstream");
        JButton btnDistDij = createStyledButton("Afficher les distances (Dijkstra)");
        JButton btnRetour  = createStyledButton("Retour");

        btnArcs.addActionListener(e -> afficherMessage("Sommets + Arcs", "Sommets :\n" + texteSommets() + "\n\nArcs :\n" + texteArcs()));
        btnAdj.addActionListener(e -> afficherMessage("Matrice d'adjacence", texteMatriceAdj()));
        btnDistMat.addActionListener(e -> afficherMessage("Matrice des distances", texteMatriceDist()));
        btnGraphe.addActionListener(e -> Graphstream.creer_Graphstream(arcs));
        btnDistDij.addActionListener(e -> afficherDistancesDepuisSommetChoisi(f));
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnArcs);
        centre.add(btnAdj);
        centre.add(btnDistMat);
        centre.add(btnGraphe);
        centre.add(btnDistDij);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);    }

    private void ouvrirSousMenu2AffichagesTheme1(JFrame parent) {
        JFrame f = new JFrame("Thème 1 - Choix Problématiques");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Poblématiques", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 12, 12));
        centre.setOpaque(false);

        JButton btnProb1  = createStyledButton("Problématique 1");
        JButton btnProb2  = createStyledButton("Problématique 2");
        JButton btnRetour = createStyledButton("Retour");

        btnProb1.addActionListener(e -> OuvrirMenuProblematique1Theme1(f));
        btnProb2.addActionListener(e -> OuvrirMenuPorblematique2Theme1(f));
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnProb1);
        centre.add(btnProb2);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);
    }

    private void OuvrirMenuProblematique1Theme1(JFrame parent) {
        JFrame f = new JFrame("Thème 1 - Choix Hypothèse");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Hypothèse (Thème 1)", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 12, 12));
        centre.setOpaque(false);

        JButton btnHypo1  = createStyledButton("Hypothèse 1");
        JButton btnHypo2  = createStyledButton("Hypothèse 2");
        JButton btnRetour = createStyledButton("Retour");

        btnHypo1.addActionListener(e -> {chargerGrapheDepuisFichier(FICHIER_GENERAL);afficherHypothese1DepuisFenetre(f);Graphstream.creer_Graphstream(arcs);Graphstream.chemin_rouge(T1_P1_H1.chemin);});
        btnHypo2.addActionListener(e -> {chargerGrapheDepuisFichier(FICHIER_GENERAL);afficherHypothese2DepuisFenetre(f);Graphstream.creer_Graphstream(arcs);Graphstream.chemin_rouge(T1_P1_H2.chemin);});
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnHypo1);
        centre.add(btnHypo2);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);
    }

    private void OuvrirMenuPorblematique2Theme1(JFrame parent) {
        JFrame f = new JFrame("Thème 1 - Choix de cas");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Cas", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 12, 12));
        centre.setOpaque(false);

        JButton btnCas1  = createStyledButton("Cas Paire");
        JButton btnCas2  = createStyledButton("Cas Impaire");
        JButton btnCas3  = createStyledButton("Cas Général");
        JButton btnRetour = createStyledButton("Retour");

        btnCas1.addActionListener(e -> {chargerGrapheDepuisFichier(FICHIER_PAIRS); afficherT1P2_H1DepuisFenetre(f);Graphstream.creer_Graphstream(arcs);Graphstream.chemin_rouge(T1_P2_H1.sommets);});
        btnCas2.addActionListener(e -> {chargerGrapheDepuisFichier(FICHIER_IMPAIRS); afficherT1P2_H2DepuisFenetre(f);Graphstream.creer_Graphstream(arcs);Graphstream.chemin_rouge(T1_P2_H2.sommets);});
        btnCas3.addActionListener(e -> {chargerGrapheDepuisFichier(FICHIER_GENERAL); afficherT1P2_H3DepuisFenetre(f);Graphstream.creer_Graphstream(arcs);Graphstream.chemin_rouge(T1_P2_H3.sommets);});
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnCas1);
        centre.add(btnCas2);
        centre.add(btnCas3);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);
    }




    // ====== Choix départ/arrivée + calcul de l’itinéraire ======
    private void afficherHypothese1DepuisFenetre(JFrame parent) {
        int[] da = demanderDepartArrivee(parent);
        if (da == null) return; // annulé ou invalide

        int d = da[0];
        int a = da[1];

        // Création de l’itinéraire comme tu le faisais dans creer(...)
        T1_P1_H1 itin = new T1_P1_H1(new Sommet(d), new Sommet(a), graphe);

        // Lancement de Dijkstra à partir de d
        ResultatDijkstra res = graphe.Dijkstra(graphe, d);

        // Remplir l’itinéraire avec le tableau des précédents
        itin.remplir_itineraire(res, graphe);

        T1_P1_H1 = itin;

        // Affichage dans une fenêtre : utilise ta méthode genererChemin()
        afficherMessage("Itinéraire de " + d + " à " + a, itin.genererChemin());
    }

    // ====== petite fenêtre pour demander départ / arrivée ======
    private int[] demanderDepartArrivee(JFrame parent) {
        // Spinners de 0 à nb_sommets-1
        JSpinner spDepart = new JSpinner(new SpinnerNumberModel(0, 0, matrice.nb_sommets - 1, 1));
        JSpinner spArrivee = new JSpinner(new SpinnerNumberModel(1, 0, matrice.nb_sommets - 1, 1));

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Sommet de départ :"));
        panel.add(spDepart);
        panel.add(new JLabel("Sommet d’arrivée :"));
        panel.add(spArrivee);

        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Choix des sommets", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        int d = (Integer) spDepart.getValue();
        int a = (Integer) spArrivee.getValue();

        if (d == a) {
            afficherMessage("Erreur", "Le sommet de départ et d’arrivée doivent être différents.");
            return null;
        }

        return new int[]{d, a};
    }

    // ====== Afficher distances Dijkstra pour un sommet choisi ======
    private void afficherDistancesDepuisSommetChoisi(JFrame parent) {
        JSpinner spDepart = new JSpinner(
                new SpinnerNumberModel(0, 0, matrice.nb_sommets - 1, 1));

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(new JLabel("Sommet de départ :"));
        panel.add(spDepart);

        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Sommet de départ pour Dijkstra", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int d = (Integer) spDepart.getValue();

        ResultatDijkstra res = graphe.Dijkstra(graphe, d);

        StringBuilder sb = new StringBuilder("Distances depuis le sommet " + d + " :\n");
        for (int i = 0; i < res.distance.length; i++) {
            sb.append("S").append(i).append(" : ").append(res.distance[i]).append("\n");
        }

        afficherMessage("Distances de Dijkstra", sb.toString());
    }




    // ====== H2 : choisir sommets à visiter + calcul du cycle TSP ======
    private void afficherHypothese2DepuisFenetre(JFrame parent) {
        // 1) on récupère la liste des sommets à visiter
        java.util.ArrayList<Sommet> aVisiter = demanderSommetsAVisiter(parent);
        if (aVisiter == null || aVisiter.size() < 2) {
            return; // annulé ou invalide
        }

        int nbAV = aVisiter.size();

        // 2) création de l'objet H2 (selon ton constructeur actuel)
        T1_P1_H2 h2 = new T1_P1_H2(graphe, aVisiter, nbAV);

        // 3) on lance ton algo TSP (construit la matrice + cherche le meilleur cycle)
        h2.calculer_tsp();

        // 4) on récupère un texte propre pour l'affichage
        String resultat = h2.texteMeilleurCycle();

        this.T1_P1_H2 = h2;

        // 5) on affiche dans une boîte de dialogue
        afficherMessage("Hypothèse 2 - Meilleur cycle", resultat);
    }

    // ====== Demander les sommets à visiter pour H2 ======
    private java.util.ArrayList<Sommet> demanderSommetsAVisiter(JFrame parent) {

        String saisie = JOptionPane.showInputDialog(parent, "Entrez les sommets à visiter (hors sommet 0, séparés par des espaces)\n" + "Exemple : 2 3 5\n");

        if (saisie == null || saisie.isBlank()) {
            return null; // l'utilisateur a annulé
        }
        String[] tokens = saisie.trim().split("\\s+");
        java.util.ArrayList<Sommet> aVisiter = new java.util.ArrayList<>();

        // On ajoute d’abord le sommet de départ = S0
        aVisiter.add(new Sommet(0));

        try {
            for (String t : tokens) {
                int num = Integer.parseInt(t);

                if (num == 0) continue; // inutile, déjà obligé au début

                if (num < 0 || num >= matrice.nb_sommets) {
                    afficherMessage("Erreur", "Sommet " + num + " invalide (doit être entre 0 et " + (matrice.nb_sommets - 1) + ").");
                    return null;
                }

                // éviter les doublons
                boolean deja = false;
                for (Sommet s : aVisiter) {
                    if (s.numero == num) {
                        deja = true;
                        break;
                    }
                }

                if (!deja) {
                    aVisiter.add(new Sommet(num));
                }
            }
        } catch (NumberFormatException ex) {
            afficherMessage("Erreur", "Entrée invalide. Utilise uniquement des entiers séparés par des espaces.");
            return null;
        }

        if (aVisiter.size() < 2) {
            afficherMessage("Erreur", "Il faut ajouter au moins un sommet à visiter, en plus de 0.");
            return null;
        }

        return aVisiter;
    }




    // ====== T1_P2 - Hypothèse 1 : cas idéal (tous sommets pairs) ======
    private void afficherT1P2_H1DepuisFenetre(JFrame parent) {
        try {
            // On suppose que ton constructeur ressemble à ça :
            // T1_P2_H1(Graphe g)
            T1_P2_H1 p2h1 = new T1_P2_H1(graphe);

            // On suppose que tu as une méthode qui lance le calcul, à adapter si besoin :
            // ex : p2h1.resoudre(), p2h1.calculer_cycle(), etc.
            p2h1.eulerianCycle();

            // Et une méthode qui renvoie un String affichable
            String texte = p2h1.genererParcours(); // <-- ADAPTE si le nom est différent

            this.T1_P2_H1 = p2h1;

            afficherMessage("P2 - Hypothèse 1", texte);
        } catch (Exception ex) {
            afficherMessage("Erreur", "Impossible de calculer le parcours pour l'hypothèse 1.\n" + "Détail : " + ex.getMessage());
        }
    }


    // ====== T1_P2 - Hypothèse 2 : deux sommets impairs ======
    private void afficherT1P2_H2DepuisFenetre(JFrame parent) {
        try {
            // On suppose que ton constructeur ressemble à ça :
            // T1_P2_H2(Graphe g)
            T1_P2_H2 p2h2 = new T1_P2_H2(graphe);

            // Méthode qui lance le calcul (à adapter)
            p2h2.eulerPrime();

            // Méthode qui renvoie un String à afficher
            String texte = p2h2.genererParcours(); // <-- adapte si tu as un autre nom

            this.T1_P2_H2 = p2h2;

            afficherMessage("P2 - Hypothèse 2", texte);
        } catch (Exception ex) {
            afficherMessage("Erreur", "Impossible de calculer le parcours pour l'hypothèse 2.\n" + "Détail : " + ex.getMessage());
        }
    }


    // ====== T1_P2 - Hypothèse 3 : cas général (Postier chinois) ======
    private void afficherT1P2_H3DepuisFenetre(JFrame parent) {
        try {
            // On crée l'objet H3 avec le graphe courant
            T1_P2_H3 p2h3 = new T1_P2_H3(graphe);

            // On lance l'algo du postier chinois
            p2h3.chinesePostman();

            // On récupère le texte du parcours pour l'affichage
            String texte = p2h3.genererParcours();
            this.T1_P2_H3 = p2h3;
            // Et on l'affiche dans une fenêtre Swing
            afficherMessage("P2 - Hypothèse 3 (Postier chinois)", texte);

        } catch (Exception ex) {
            afficherMessage("Erreur",
                    "Impossible de calculer le parcours pour l'hypothèse 3.\nDétail : " + ex.getMessage());
        }
    }





    // ====== MENU THÈME 2 (placeholder) ======

    private void ouvrirMenuTheme2(JFrame parent) {
        JFrame f = new JFrame("Thème 2 - Points de collecte");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Thème 2 : Points de collecte", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 8, 8));
        centre.setOpaque(false);

        JButton btnInfo = createStyledButton("À implémenter plus tard");
        JButton btnRetour = createStyledButton("Retour au menu principal");

        btnInfo.addActionListener(e -> afficherMessage("Thème 2", "Tu pourras mettre ici tes heuristiques de tournée (TSP, etc.)."));
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnInfo);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);    }






    // ====== MENU THÈME 3 (placeholder) ======
    private void ouvrirMenuTheme3(JFrame parent) {
        JFrame f = new JFrame("Thème 3 - Planification / Secteurs / Camions");
        f.setSize(960, 660);
        f.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 245, 255));

        JLabel titre = new JLabel("Thème 3 : Planification (à compléter)", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 70, 120));
        panel.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(0, 1, 8, 8));
        centre.setOpaque(false);

        JButton btnInfo = createStyledButton("À implémenter plus tard");
        JButton btnRetour = createStyledButton("Retour au menu principal");

        btnInfo.addActionListener(e -> afficherMessage("Thème 3", "Tu pourras mettre ici gestion de secteurs, plannings, etc."));
        btnRetour.addActionListener(e -> f.dispose());

        centre.add(btnInfo);
        centre.add(btnRetour);

        panel.add(centre, BorderLayout.CENTER);

        f.setContentPane(panel);
        showWithZoom(f);    }







    // ====== TEXTES POUR LES AFFICHAGES THÈME 1 P1 H1 ======
    private String texteArcs() {
        if (arcs == null || arcs.isEmpty()) return "Aucun arc.";
        StringBuilder sb = new StringBuilder();
        for (Arc arc : arcs) {
            sb.append("Arc : S").append(arc.s_depart.numero).append(" -> S").append(arc.s_arrivee.numero).append("  (distance = ").append(arc.distance).append(")\n");
        }
        return sb.toString();
    }

    private String texteMatriceAdj() {
        if (matrice == null) return "Matrice non disponible.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrice.nb_sommets; i++) {
            for (int j = 0; j < matrice.nb_sommets; j++) {
                sb.append(matrice.adj[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String texteMatriceDist() {
        if (matrice == null) return "Matrice non disponible.";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrice.nb_sommets; i++) {
            for (int j = 0; j < matrice.nb_sommets; j++) {
                if (matrice.longeurs[i][j] != Integer.MAX_VALUE) {
                    sb.append(matrice.longeurs[i][j]).append(" ");
                } else {
                    sb.append("o ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private String texteSommets() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrice.nb_sommets; i++) {
            sb.append("S").append(i).append("\n");
        }
        return sb.toString();
    }





    // ====== BOUTONS STYLÉS ======
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setBackground(BTN_NORMAL);
        btn.setForeground(TEXT_COLOR);
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BTN_BORDER), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        // === Effet hover (Option 2) ===
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BTN_HOVER);
                btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BTN_BORDER, 2), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BTN_NORMAL);
                btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BTN_BORDER, 1), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(BTN_PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(BTN_HOVER);
            }
        });

        return btn;
    }

    // === Option 5 : petit zoom à l'ouverture des fenêtres ===
    private void showWithZoom(JFrame frame) {
        // On récupère la taille finale voulue
        Dimension target = frame.getSize();
        int targetW = target.width;
        int targetH = target.height;

        // Taille de départ (70% de la taille finale)
        int startW = (int) (targetW * 0.7);
        int startH = (int) (targetH * 0.7);

        frame.setSize(startW, startH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Timer pour animer le zoom
        int steps = 10;
        int delay = 5; // ms

        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            Dimension current = frame.getSize();
            int w = current.width;
            int h = current.height;

            int dw = (targetW - w) / 2;
            int dh = (targetH - h) / 2;

            if (Math.abs(targetW - w) <= 2 && Math.abs(targetH - h) <= 2) {
                frame.setSize(targetW, targetH);
                frame.setLocationRelativeTo(null);
                timer.stop();
            } else {
                frame.setSize(w + dw, h + dh);
                frame.setLocationRelativeTo(null);
            }
        });

        timer.start();
    }


    // ====== FENÊTRE DE TEXTE ======
    private void afficherMessage(String titre, String texte) {
        JTextArea area = new JTextArea(texte);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(450, 250));

        JOptionPane.showMessageDialog(null, scrollPane, titre, JOptionPane.INFORMATION_MESSAGE);
    }
}
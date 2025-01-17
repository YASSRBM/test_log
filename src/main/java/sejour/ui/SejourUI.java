package sejour.ui;

import sejour.SearchHandler;
import sejour.elements.*;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
public class SejourUI extends JFrame {
    private SearchHandler searchHandler;

    private JTextField villeDepart;
    private JTextField villeRetour;
    private JSpinner dureeSejour;
    private JSpinner prixMax;
    private JComboBox<String> modeTransport;
    private JComboBox<String> prioriteTransport;
    private JSpinner classementMin;
    private JComboBox<String> prioriteHotel;
    private JComboBox<String> categorieActivite;
    private JSpinner distanceMax;
    private JDatePickerImpl dateSejour;

    public SejourUI(SearchHandler searchHandler) {
        this.searchHandler = searchHandler;
        this.searchHandler.dataHandler.initData();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recherche de séjour");
        setPreferredSize(new Dimension(400, 500));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2));

        villeDepart = new JTextField();
        villeRetour = new JTextField();
        dureeSejour = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        prixMax = new JSpinner(new SpinnerNumberModel(1000, 100, 10000, 100));
        modeTransport = new JComboBox<>(new String[]{"AVION", "TRAIN"});
        prioriteTransport = new JComboBox<>(new String[]{"TEMPS", "PRIX"});
        classementMin = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        prioriteHotel = new JComboBox<>(new String[]{"CLASSEMENT", "PRIX"});
        categorieActivite = new JComboBox<>(new String[]{"SPORT", "MUSIQUE"});
        distanceMax = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(new UtilDateModel(), properties);
        dateSejour = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        panel.add(new JLabel("Ville de départ :"));
        panel.add(villeDepart);
        panel.add(new JLabel("Ville de retour :"));
        panel.add(villeRetour);
        panel.add(new JLabel("Date de séjour :"));
        panel.add(dateSejour);
        panel.add(new JLabel("Durée du séjour :"));
        panel.add(dureeSejour);
        panel.add(new JLabel("Prix maximum :"));
        panel.add(prixMax);
        panel.add(new JLabel("Mode de transport :"));
        panel.add(modeTransport);
        panel.add(new JLabel("Priorité transport :"));
        panel.add(prioriteTransport);
        panel.add(new JLabel("Classement minimum :"));
        panel.add(classementMin);
        panel.add(new JLabel("Priorité hôtel :"));
        panel.add(prioriteHotel);
        panel.add(new JLabel("Catégorie d'activité :"));
        panel.add(categorieActivite);
        panel.add(new JLabel("Distance maximale :"));
        panel.add(distanceMax);
        
            JButton searchButton = new JButton("Rechercher");
            searchButton.addActionListener(e -> searchForfaits());
        
            add(panel, BorderLayout.CENTER);
            add(searchButton, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(null);
        }
        
        private void searchForfaits() {
            
            CritereHotel critereHotel = new CritereHotel(
                    (Integer) classementMin.getValue(),
                    CritereHotel.PrioriteHotel.valueOf(prioriteHotel.getSelectedItem().toString())
            );
        
            CritereTrajet critereTrajet = new CritereTrajet(
                    Trajet.ModeTrajet.valueOf(modeTransport.getSelectedItem().toString()),
                    CritereTrajet.PrioriteTrajet.valueOf(prioriteTransport.getSelectedItem().toString())
            );
        
            CritereActivite critereActivite = new CritereActivite(
                Activite.Categorie.valueOf(categorieActivite.getSelectedItem().toString()),
                ((Integer) distanceMax.getValue()).doubleValue()
        );
        
            CritereForfait critereForfait = new CritereForfait(
                villeDepart.getText(),
                villeRetour.getText(),
                (Integer) dureeSejour.getValue(),
                (Integer) prixMax.getValue()
        );
        
            List<Forfait> forfaits = searchHandler.Search(critereHotel, critereTrajet, critereActivite, critereForfait);
        
        
            // Display the search results in a new window or dialog
            JOptionPane.showMessageDialog(this, "Nombre de forfaits trouvés : " + forfaits.size());
            showSearchResults(forfaits);
        }
        private void showSearchResults(List<Forfait> forfaits) {
            StringBuilder message = new StringBuilder();
            message.append("Nombre de forfaits trouvés : ").append(forfaits.size()).append("\n\n");

            for (int i = 0; i < forfaits.size(); i++) {
                Forfait forfait = forfaits.get(i);
                message.append("Forfait ").append(i + 1).append(":\n");
                message.append("Hôtel: ").append(forfait.getHotel().getAdresse()).append("\n");
                message.append("Transport aller: ").append(forfait.getTransportAlle().toString()).append("\n");
                message.append("Transport retour: ").append(forfait.getTransportRetour().toString()).append("\n");
                message.append("Activités:\n");
                message.append(forfait.getActivites().stream()
                        .map(activite -> "- " + activite.getAdresse())
                        .collect(Collectors.joining("\n")));
                message.append("\n\n");
            }

            JTextArea textArea = new JTextArea(message.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Résultats de recherche", JOptionPane.PLAIN_MESSAGE);
        }
        public static void main(String[] args) {
                    // Configure the logger
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.INFO);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
            SwingUtilities.invokeLater(() -> {
                SearchHandler searchHandler = new SearchHandler();
                SejourUI sejourUI = new SejourUI(searchHandler);
                sejourUI.setVisible(true);
            });
        }
        
        }
        
        
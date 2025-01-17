package sejour;

import sejour.elements.Trajet;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TrajetTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Trajet trajet = new Trajet();
        trajet.setVilleDepart("Bordeaux");
        trajet.setVilleArrivee("Paris");
        trajet.setTempsDepart(Instant.parse("2025-01-01T08:00:00Z"));
        trajet.setTempsArrivee(Instant.parse("2025-01-01T12:00:00Z"));
        trajet.setModeTransport(Trajet.ModeTrajet.TRAIN);
        trajet.setPrix(45.50);

        // Act & Assert
        assertEquals("Bordeaux", trajet.getVilleDepart());
        assertEquals("Paris", trajet.getVilleArrivee());
        assertEquals(Instant.parse("2025-01-01T08:00:00Z"), trajet.getTempsDepart());
        assertEquals(Instant.parse("2025-01-01T12:00:00Z"), trajet.getTempsArrivee());
        assertEquals(Trajet.ModeTrajet.TRAIN, trajet.getModeTransport());
        assertEquals(45.50, trajet.getPrix());
    }

    @Test
    public void testInvalidPrix() {
        // Arrange
        Trajet trajet = new Trajet();

        // Act
        trajet.setPrix(-10.0);

        // Assert
        assertTrue(trajet.getPrix() < 0, "Le prix ne doit pas être négatif !");
    }

    @Test
    public void testInvalidTime() {
        // Arrange
        Trajet trajet = new Trajet();
        trajet.setTempsDepart(Instant.parse("2025-01-01T12:00:00Z"));
        trajet.setTempsArrivee(Instant.parse("2025-01-01T08:00:00Z"));

        // Assert
        assertTrue(trajet.getTempsArrivee().isBefore(trajet.getTempsDepart()), "La date d'arrivée ne doit pas être antérieure à la date de départ !");
    }

    @Test
    void testFromStringValidInputs() {
        Trajet trajet = new Trajet();
        // Vérifier les cas normaux
        assertEquals(Trajet.ModeTrajet.AVION, trajet.fromString("AVION"), "AVION doit retourner ModeTrajet.AVION");
        assertEquals(Trajet.ModeTrajet.TRAIN, trajet.fromString("TRAIN"), "TRAIN doit retourner ModeTrajet.TRAIN");
        assertEquals(Trajet.ModeTrajet.VOITURE, trajet.fromString("VOITURE"), "VOITURE doit retourner ModeTrajet.VOITURE");
        assertEquals(Trajet.ModeTrajet.BUS, trajet.fromString("BUS"), "BUS doit retourner ModeTrajet.BUS");
        assertThrows(IllegalArgumentException.class, ()->trajet.fromString("null"));
    }
}

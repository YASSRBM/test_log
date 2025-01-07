package sejour;

import org.junit.jupiter.api.Test;
import sejour.elements.Hotel;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setAdresse("Place de la Bourse");
        hotel.setVille("Bordeaux");
        hotel.setClassement(4);
        hotel.setPrix(120.0);

        // Act & Assert
        assertEquals("Place de la Bourse", hotel.getAdresse());
        assertEquals("Bordeaux", hotel.getVille());
        assertEquals(4, hotel.getClassement());
        assertEquals(120.0, hotel.getPrix());
    }

    @Test
    public void testClassementValidRange() {
        // Arrange
        Hotel hotel = new Hotel();

        // Act
        hotel.setClassement(5);

        // Assert
        assertTrue(hotel.getClassement() >= 1 && hotel.getClassement() <= 5, "Le classement doit être entre 1 et 5 étoiles.");
    }

    @Test
    public void testInvalidClassement() {
        // Arrange
        Hotel hotel = new Hotel();

        // Act
        hotel.setClassement(0);

        // Assert
        assertTrue(hotel.getClassement() >= 1 && hotel.getClassement() <= 5, "Le classement ne doit pas être inférieur à 1.");
    }

    @Test
    public void testPrixPositif() {
        // Arrange
        Hotel hotel = new Hotel();

        // Act
        hotel.setPrix(200.0);

        // Assert
        assertTrue(hotel.getPrix() > 0, "Le prix doit être supérieur à 0.");
    }

    @Test
    public void testAdresseNonVide() {
        // Arrange
        Hotel hotel = new Hotel();

        // Act
        hotel.setAdresse("");

        // Assert
        assertNotNull(hotel.getAdresse(), "L'adresse ne doit pas être null.");
        assertFalse(hotel.getAdresse().isEmpty(), "L'adresse ne doit pas être vide.");
    }

    @Test
    public void testVilleNonVide() {
        // Arrange
        Hotel hotel = new Hotel();

        // Act
        hotel.setVille("");

        // Assert
        assertNotNull(hotel.getVille(), "La ville ne doit pas être null.");
        assertFalse(hotel.getVille().isEmpty(), "La ville ne doit pas être vide.");
    }
}

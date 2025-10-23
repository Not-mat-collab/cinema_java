package edu.cinema.pricing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PricingEngineTest {

    PricingEngine engine = new PricingEngine();

    @DisplayName("Prix de base selon le type de billet")
    @ParameterizedTest
    @CsvSource({
            "ADULT, 10.0",
            "CHILD, 6.0",
            "SENIOR, 7.5",
            "STUDENT, 8.0"
    })
    void testBasePrice(String typeName, double expected) {
        TicketType type = TicketType.valueOf(typeName);
        double result = engine.basePrice(type);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Erreur si type est null")
    void testBasePriceNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            engine.basePrice(null);
        });
    }

    @Test
    @DisplayName("Panier vide → total = 0")
    void testEmptyCart() {
        var result = engine.computeTotal(List.of(), false, DayOfWeek.MONDAY);
        assertEquals(0.0, result.getTotal());
    }

    @Test
    @DisplayName("Un billet adulte sans options")
    void testSingleAdult() {
        var result = engine.computeTotal(List.of(TicketType.ADULT), false, DayOfWeek.MONDAY);
        assertEquals(10.0, result.getTotal());
    }

    @Test
    @DisplayName("Réduction mercredi (-20%)")
    void testWednesdayDiscount() {
        var result = engine.computeTotal(List.of(TicketType.ADULT, TicketType.CHILD), false, DayOfWeek.WEDNESDAY);
        
        assertEquals(12.8, result.getTotal());
    }

    @Test
    @DisplayName("Supplément séance 3D (+2€/billet)")
    void test3DSurcharge() {
        var result = engine.computeTotal(List.of(TicketType.ADULT, TicketType.CHILD), true, DayOfWeek.MONDAY);
        
        assertEquals(20.0, result.getTotal());
    }

    @Test
    @DisplayName("Réduction groupe (4 billets ou + : -10%)")
    void testGroupDiscount() {
        var result = engine.computeTotal(
                List.of(TicketType.STUDENT, TicketType.STUDENT, TicketType.STUDENT, TicketType.STUDENT),
                false,
                DayOfWeek.MONDAY
        );
        
        assertEquals(28.8, result.getTotal());
    }

    @Test
    @DisplayName("Mercredi + 3D → -20% puis +2€/billet")
    void testWednesdayAnd3D() {
        var result = engine.computeTotal(List.of(TicketType.ADULT), true, DayOfWeek.WEDNESDAY);
        
        assertEquals(10.0, result.getTotal());
    }

    @Test
    @DisplayName("Mercredi + 3D + Groupe")
    void testAllCombined() {
        var result = engine.computeTotal(
                List.of(TicketType.ADULT, TicketType.ADULT, TicketType.ADULT, TicketType.ADULT),
                true,
                DayOfWeek.WEDNESDAY
        );
        
        assertEquals(36.0, result.getTotal());
    }

    @Test
    @DisplayName("Erreur si liste de tickets null")
    void testTicketsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            engine.computeTotal(null, false, DayOfWeek.MONDAY);
        });
    }

    @Test
    @DisplayName("Erreur si jour null")
    void testDayNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            engine.computeTotal(List.of(TicketType.ADULT), false, null);
        });
    }
}

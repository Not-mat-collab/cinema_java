package edu.cinema.pricing;

import java.time.DayOfWeek;
import java.util.List;

public class PricingEngine {

    public double basePrice(TicketType type) {
        if (type == null) throw new IllegalArgumentException("Ticket type cannot be null");

        return switch (type) {
            case ADULT -> 10.0;
            case CHILD -> 6.0;
            case SENIOR -> 7.5;
            case STUDENT -> 8.0;
        };
    }
public PriceBreakdown computeTotal(List<TicketType> tickets, boolean is3D, DayOfWeek day) {
        if (tickets == null || day == null)
            throw new IllegalArgumentException("Tickets and day cannot be null");

        if (tickets.isEmpty())
            return new PriceBreakdown(0, 0, 0, 0, 0);

        double subtotal = tickets.stream().mapToDouble(this::basePrice).sum();

        double wednesdayDisc = 0;
        if (day == DayOfWeek.WEDNESDAY) {
            wednesdayDisc = subtotal * 0.20;
            subtotal -= wednesdayDisc;
        }

        double threeDSurcharge = 0;
        if (is3D) {
            threeDSurcharge = tickets.size() * 2.0;
            subtotal += threeDSurcharge;
        }

    
        double groupDisc = 0;
        if (tickets.size() >= 4) {
            groupDisc = subtotal * 0.10;
            subtotal -= groupDisc;
        }

        double total = roundToCents(subtotal);

        return new PriceBreakdown(
                roundToCents(tickets.stream().mapToDouble(this::basePrice).sum()),
                roundToCents(wednesdayDisc),
                roundToCents(threeDSurcharge),
                roundToCents(groupDisc),
                total
        );
    }

    private double roundToCents(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}



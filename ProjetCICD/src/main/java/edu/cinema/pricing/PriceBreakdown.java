package edu.cinema.pricing;

public final class PriceBreakdown {
    private final double subtotal; // somme des prix des billets
    private final double wednesdayDisc; // remise -20% si mercredi
    private final double threeDSurcharge; // +2€ * nbBillets 3D
    private final double groupDisc; // remise -10% si groupe >= 4 billets 
    private final double total; // total final arrondi au centime
    // + constructeur, getters, toString()
    

public PriceBreakdown(double subtotal, double wednesdayDisc, double threeDSurcharge, double groupDisc, double total) {
        this.subtotal = subtotal;
        this.wednesdayDisc = wednesdayDisc;
        this.threeDSurcharge = threeDSurcharge;
        this.groupDisc = groupDisc;
        this.total = total;
    }

 public double getSubtotal() { return subtotal; }
    public double getWednesdayDisc() { return wednesdayDisc; }
    public double getThreeDSurcharge() { return threeDSurcharge; }
    public double getGroupDisc() { return groupDisc; }
    public double getTotal() { return total; }

    

}


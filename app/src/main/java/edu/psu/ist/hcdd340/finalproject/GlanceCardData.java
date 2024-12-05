package edu.psu.ist.hcdd340.finalproject;

public class GlanceCardData {
    final private String cardTitle;
    final private String cardText;

    public GlanceCardData(String cardTitle, String cardText){
        this.cardTitle = cardTitle;
        this.cardText = cardText;
    }

    public String getCardTitle() {return cardTitle;}
    public String getCardText() {return cardText;}
}

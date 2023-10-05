package hepl.be.VESPAP;

import hepl.be.model.Facture;

import java.util.ArrayList;

public class ReponseGetFactures implements Reponse
{
    private ArrayList<Facture> tableauFactures;

    public ReponseGetFactures(ArrayList<Facture> tabFactures)
    {
        tableauFactures = tabFactures;
    }

    public ArrayList<Facture> getTableauFactures() {
        return tableauFactures;
    }
}

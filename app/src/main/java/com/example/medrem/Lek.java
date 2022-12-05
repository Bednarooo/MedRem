package com.example.medrem;

public class Lek {
    private String nazwa;
    private String dawka;
    private TypDawkowania typDawkowania;

    public Lek() {
    }

    public Lek(String nazwa, String dawka, TypDawkowania typDawkowania) {
        this.nazwa = nazwa;
        this.dawka = dawka;
        this.typDawkowania = typDawkowania;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getDawka() {
        return dawka;
    }

    public void setDawka(String dawka) {
        this.dawka = dawka;
    }

    public TypDawkowania getTypDawkowania() {
        return typDawkowania;
    }

    public void setTypDawkowania(TypDawkowania typDawkowania) {
        this.typDawkowania = typDawkowania;
    }
}

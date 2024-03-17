package com.project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="zadanie")
public class Zadanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="zadanie_id")
    private Integer zadanieId;

    @ManyToOne
    @JoinColumn(name= "projekt_id")
    private Projekt projekt;

    private String nazwa;
    private Integer kolejnosc;
    private String opis;

    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataCzasOddania() {
        return dataCzasOddania;
    }

    public void setDataCzasOddania(LocalDateTime dataCzasOddania) {
        this.dataCzasOddania = dataCzasOddania;
    }

    @Column(name = "dataczas_oddania")
    private LocalDateTime dataCzasOddania;

    public Projekt getProjekt() {
        return projekt;
    }

    public Zadanie() {
    }

    public Zadanie(String nazwa, Integer kolejnosc, String opis) {
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
    /*TODO Uzupełnij kod o zmienne reprezentujące pozostałe pola tabeli zadanie (patrz rys. 3.1),
. następnie wygeneruj dla nich akcesory i mutatory (Source -> Generate Getters and Setters),
. ponadto dodaj pusty konstruktor oraz konstruktor ze zmiennymi nazwa, opis i kolejnosc.
*/

}

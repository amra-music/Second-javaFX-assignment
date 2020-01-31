package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();
    private KorisnikDAO baza;

    public KorisniciModel() {
        baza = KorisnikDAO.getInstance();
    }

    public void obrisiKorisnika(int id){
        baza.obrisiKorisnika(id);
    }

    public void napuni() {
        korisnici = FXCollections.observableArrayList(baza.korisnici());
        trenutniKorisnik.set(null);
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        if(this.trenutniKorisnik.get()!=null && this.trenutniKorisnik.get().getId()==-1) dodajKorisnika(this.trenutniKorisnik.get());
        else if(this.trenutniKorisnik.get()!=null ) azurirajKorisnika(this.trenutniKorisnik.get());
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public void diskonektuj() {
        KorisnikDAO.removeInstance();
    }

    public void dodajKorisnika(Korisnik oldKorisnik) {
        baza.dodajKorisnika(oldKorisnik);
    }

    public void azurirajKorisnika(Korisnik oldKorisnik) {
        baza.azurirajKorisnika(oldKorisnik);
    }

    public void zapisiDatoteku(File file) {

    }
}

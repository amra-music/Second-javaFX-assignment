package ba.unsa.etf.rpr.t7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KorisnikDAO {

    private static KorisnikDAO instance;
    private static Connection conn;

    private PreparedStatement korisniciUpit, obrisiKorisnikaUpit, dodajKorisnikaUpit, odrediIdKorisnikaUpit, azurirajKorisnikaUpit;

    public static KorisnikDAO getInstance() {
        if (instance == null) instance = new KorisnikDAO();
        return instance;
    }
    private KorisnikDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            korisniciUpit = conn.prepareStatement("SELECT * FROM korisnik");
        } catch (SQLException e) {
            regenerisiBazu();
        }

        try {
            korisniciUpit = conn.prepareStatement("SELECT * FROM korisnik");
            obrisiKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnik WHERE id=?");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?,?,?)");
            azurirajKorisnikaUpit = conn.prepareStatement("UPDATE korisnik SET ime=?, prezime=?,email=?,username=?,password=? WHERE id=?");
            odrediIdKorisnikaUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM korisnik");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("korisnici.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Korisnik> korisnici() {
        ArrayList<Korisnik> rezultat = new ArrayList<>();
        try {
            ResultSet rs = korisniciUpit.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String ime = rs.getString(2);
                String prezime = rs.getString(3);
                String email = rs.getString(4);
                String username = rs.getString(5);
                String password = rs.getString(6);
                Korisnik korisnik = new Korisnik(ime,prezime,email,username,password);
                korisnik.setId(id);
                rezultat.add(korisnik);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }
    public void obrisiKorisnika(int id){
        try {
            obrisiKorisnikaUpit.setInt(1, id);
            obrisiKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajKorisnika(Korisnik oldKorisnik) {
        try {
            ResultSet rs = odrediIdKorisnikaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            dodajKorisnikaUpit.setInt(1, id);
            dodajKorisnikaUpit.setString(2, oldKorisnik.getIme());
            dodajKorisnikaUpit.setString(3, oldKorisnik.getPrezime());
            dodajKorisnikaUpit.setString(4, oldKorisnik.getEmail());
            dodajKorisnikaUpit.setString(5, oldKorisnik.getUsername());
            dodajKorisnikaUpit.setString(6, oldKorisnik.getPassword());
            dodajKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void azurirajKorisnika(Korisnik oldKorisnik) {
        try {

            azurirajKorisnikaUpit.setString(1, oldKorisnik.getIme());
            azurirajKorisnikaUpit.setString(2, oldKorisnik.getPrezime());
            azurirajKorisnikaUpit.setString(3, oldKorisnik.getEmail());
            azurirajKorisnikaUpit.setString(4, oldKorisnik.getUsername());
            azurirajKorisnikaUpit.setString(5, oldKorisnik.getPassword());
            azurirajKorisnikaUpit.setInt(6, oldKorisnik.getId());

            azurirajKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package ba.unsa.etf.rpr.t7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KorisnikDAO {

    private static KorisnikDAO instance;
    private Connection conn;

    private PreparedStatement korisniciUpit;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                String ime = rs.getString(2);
                String prezime = rs.getString(3);
                String email = rs.getString(4);
                String username = rs.getString(5);
                String password = rs.getString(6);
                rezultat.add(new Korisnik(ime,prezime,email,username,password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }
}

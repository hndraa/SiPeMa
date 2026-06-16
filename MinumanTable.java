import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MinumanTable {
    public static ArrayList<Minuman> getAllMinuman() {
        ArrayList<Minuman> minumanList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM minuman";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Minuman minuman = new Minuman(
                    resultSet.getString("nama"),
                    resultSet.getString("deskripsi"),
                    resultSet.getDouble("harga"),
                    resultSet.getString("gambar_path")
                );
                minumanList.add(minuman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minumanList;
    }
}

class Minuman {
    private String nama;
    private String deskripsi;
    private double harga;
    private String gambarPath;

    public Minuman(String nama, String deskripsi, double harga, String gambarPath) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.gambarPath = gambarPath;
    }

    // Getter methods
    public String getNama() { return nama; }
    public String getDeskripsi() { return deskripsi; }
    public double getHarga() { return harga; }
    public String getGambarPath() { return gambarPath; }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JajanTable {
    public static ArrayList<Jajan> getAllJajan() {
        ArrayList<Jajan> jajanList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM jajan";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Jajan jajan = new Jajan(
                    resultSet.getString("nama"),
                    resultSet.getString("deskripsi"),
                    resultSet.getDouble("harga"),
                    resultSet.getString("gambar_path")
                );
                jajanList.add(jajan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jajanList;
    }
}

class Jajan {
    private String nama;
    private String deskripsi;
    private double harga;
    private String gambarPath;

    public Jajan(String nama, String deskripsi, double harga, String gambarPath) {
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

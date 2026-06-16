import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MakananTable {
    public static ArrayList<Makanan> getAllMakanan() {
        ArrayList<Makanan> makananList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM makanan";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Makanan makanan = new Makanan(
                    resultSet.getString("nama"),
                    resultSet.getString("deskripsi"),
                    resultSet.getDouble("harga"),
                    resultSet.getString("gambar_path")
                );
                makananList.add(makanan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makananList;
    }
}

class Makanan {
    private String nama;
    private String deskripsi;
    private double harga;
    private String gambarPath;

    public Makanan(String nama, String deskripsi, double harga, String gambarPath) {
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

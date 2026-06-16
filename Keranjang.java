import java.util.ArrayList;

public class Keranjang {
    private ArrayList<Jajan> items;

    public Keranjang() {
        items = new ArrayList<>();
    }

    // Menambahkan item ke keranjang
    public void addItem(Jajan jajan) {
        items.add(jajan);
    }

    // Menghapus item dari keranjang
    public void removeItem(Jajan jajan) {
        items.remove(jajan);
    }

    // Mendapatkan semua item dalam keranjang
    public ArrayList<Jajan> getItems() {
        return items;
    }

    // Mengosongkan keranjang
    public void clear() {
        items.clear();
    }

    // Mendapatkan jumlah item dalam keranjang
    public int getItemCount() {
        return items.size();
    }
}




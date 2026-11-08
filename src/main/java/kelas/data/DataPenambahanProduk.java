
package kelas.data;

/**
 *
 * @author HP
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Penambahan;
import kelas.model.Produk;

public class DataPenambahanProduk {
    private final ObservableList<Penambahan> daftar = FXCollections.observableArrayList();
    private int urutan = 1;
    
    public ObservableList<Penambahan> getDaftar(){
        return FXCollections.unmodifiableObservableList(daftar);
    }
    
    public Penambahan tambah (Produk produk, String uri){
        Penambahan p = new Penambahan(String.format("PDK%03d", urutan), produk, uri);
        daftar.add(p);
        urutan++;
        return p;
    }
    
    public void hapus(Penambahan p){
        daftar.remove (p);
    }
}

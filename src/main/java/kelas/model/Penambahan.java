
package kelas.model;

/**
 *
 * @author HP
 */
public class Penambahan {
    
    private final String kode;
    private final Produk produk;
    private final String gambarUri;
    
    public Penambahan (String kode, Produk produk, String gambarUri){
        if (kode == null || kode.isBlank() || produk == null){
            throw new IllegalArgumentException("data tidak lengkap");
        }
        
        this.kode = kode.trim();
        this.produk = produk;
        this.gambarUri = gambarUri;
    }
    
    public String getKode(){
        return kode;
    }
    public Produk getProduk(){
        return produk;
    }
    public String getGambarUri(){
        return gambarUri;
    }
    
    //Getter "datar" agar TableView (PropertyValueFactory) bisa membaca langsung.
    
    public String getNama(){
        return produk.getNama();
    }
    public String getKategori(){
        return produk.getKategori();
    }
    public long getHarga(){
        return produk.getHarga();
    }
}

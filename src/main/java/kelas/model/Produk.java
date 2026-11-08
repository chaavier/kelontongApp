
package kelas.model;

/**
 * @author HP
 */
public class Produk {
    private final String nama;
    private final String kategori;
    private final long harga;
    
    public Produk (String nama, String kategori, long harga){
        if (nama == null || nama.isBlank()) throw new IllegalArgumentException("nama produk wajib terisi");
        if (!"Makanan".equals(kategori) && !"Minuman".equals(kategori)) throw new IllegalArgumentException("kategori harus berupa Makanan atau Minuman");
        if (harga <= 0) throw new IllegalArgumentException("harga harus lebih dari 0");
        
        this.nama = nama.trim();
        this.kategori = kategori;
        this.harga = harga;
    }
    
    public String getNama(){
        return nama;
    }
    public String getKategori(){
        return kategori;
    }
    public long getHarga(){
        return harga;
    }
}

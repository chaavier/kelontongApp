
package kelas.controller;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import kelas.Navigator;
import kelas.data.DataPenambahanProduk;
import kelas.model.Penambahan;
import kelas.model.Produk;

/**
 * FXML Controller class
 * @author HP
 */
public class FormController{

   @FXML
   private TextField namaField, hargaField;
   @FXML
   private ComboBox<String> kategoriBox;
   @FXML
   private Label pesanLabel;
   @FXML
   private ImageView gambarView;
   private Navigator navigator;
   private DataPenambahanProduk data;
   private String gambarUri;
    
   @FXML
   private void initialize(){
       kategoriBox.getItems().addAll("Makanan", "Minuman");
       kategoriBox.setValue("Makanan");
   }
   
   public void setKonteks (Navigator navigator, DataPenambahanProduk data){
       this.navigator = navigator;
       this.data = data;
   }
   
   @FXML
   private void pilihGambar(){
       FileChooser chooser = new FileChooser();
       chooser.setTitle("Pilih lampiran gambar");
       chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Gambar PNG/JPEG", "*.png", "*.jpg", "*.jpeg"));
       File file = chooser.showOpenDialog(namaField.getScene().getWindow());
       if (file== null){
           return;
       }
       if (file.length() > 5L * 1024 *1024){
           pesanLabel.setText("Maksimum gambar 5MB");
           return;
       }
       Image image = new Image(file.toURI().toString(), 640, 480, true, true);
       if (image.isError()){
           pesanLabel.setText("Gambar tidak dapat dibaca.");
           return;
       }
       gambarUri = file.toURI().toString();
       gambarView.setImage(image);
       pesanLabel.setText("Gambar dipilih.");
   }
   
   @FXML
   private void simpan() {
       try {
            long harga = Long.parseLong(hargaField.getText().trim());
            Produk produk = new Produk(namaField.getText(), kategoriBox.getValue(), harga);
            Penambahan p = data.tambah(produk, gambarUri);

            namaField.clear();
            hargaField.clear();
            kategoriBox.setValue("Makanan");
            gambarUri = null;
            gambarView.setImage(null);
            namaField.requestFocus();
            tampilkanSukses("Produk " + p.getKode() + " (" + p.getNama() + ") berhasil disimpan.");
        } catch (NumberFormatException e) {
            tampilkanError("Harga harus berupa bilangan bulat positif, tanpa titik atau koma.");
        } catch (IllegalArgumentException e) {
            tampilkanError(e.getMessage());
        }
    }

   
   @FXML
   private void bukaDaftar(){
       try {
           navigator.bukaDaftar();
       } catch (IOException e) {
           pesanLabel.setText("Gagal membuka daftar.");
           e.printStackTrace();
       }
   }
   
   private void tampilkanSukses(String pesan) {
        pesanLabel.setStyle("-fx-text-fill: #166534;");
        pesanLabel.setText(pesan);
    }

    private void tampilkanError(String pesan) {
        pesanLabel.setStyle("-fx-text-fill: #b91c1c;");
        pesanLabel.setText(pesan);
    }
}

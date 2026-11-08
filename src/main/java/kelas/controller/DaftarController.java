
package kelas.controller;

import java.io.IOException;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataPenambahanProduk;
import kelas.model.Penambahan;

/**
 * FXML Controller class
 * @author HP
 */
public class DaftarController{

    @FXML
    private TableView<Penambahan> tabel;
    @FXML
    TableColumn<Penambahan, String> kodeCol;
    @FXML
    private TableColumn<Penambahan, String> namaCol;
      @FXML
    private TableColumn<Penambahan, String> kategoriCol;
    @FXML
    private TableColumn<Penambahan, Long> hargaCol;
    @FXML
    private TextField cariField;
    @FXML
    ComboBox<String> filterKategoriBox;
    @FXML
    private CheckBox hargaCheck;
    @FXML
    private Label pesanLabel;
    
    private Navigator navigator;
    private DataPenambahanProduk data;
    private final ObservableList<Penambahan> dataTampilan
            = FXCollections.observableArrayList();
    
    @FXML
    private void initialize(){
        PropertyValueFactory<Penambahan, String> sumberKode
                = new PropertyValueFactory<Penambahan, String>("kode");
        
        kodeCol.setCellValueFactory(sumberKode);
        
        PropertyValueFactory<Penambahan, String> sumberNama
                = new PropertyValueFactory<Penambahan, String>("nama");
        
        namaCol.setCellValueFactory(sumberNama);
        
        PropertyValueFactory<Penambahan, String> sumberKategori
                = new PropertyValueFactory<Penambahan, String>("kategori");
        
        kategoriCol.setCellValueFactory(sumberKategori);
        
        PropertyValueFactory<Penambahan, Long> sumberHarga
                = new PropertyValueFactory<Penambahan, Long>("harga");
        
        hargaCol.setCellValueFactory(sumberHarga);
        
        filterKategoriBox.getItems().add("Semua");
        filterKategoriBox.getItems().add("Makanan");
        filterKategoriBox.getItems().add("Minuman");
        filterKategoriBox.setValue("Semua");
        
        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada data yang sesuai"));
        ubahTampilanHarga();
        
    }
    
    public void setKonteks (Navigator navigator, DataPenambahanProduk data){
        this.navigator = navigator;
        this.data = data;
        cari();
    }
    
    @FXML
    private void cari(){
        if (data == null){
            return;
        }
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        String kategoriDipilih = filterKategoriBox.getValue();
        
        for (Penambahan penambahan : data.getDaftar()) {
            String kode = penambahan.getKode().toLowerCase(Locale.ROOT);
            String nama = penambahan.getNama().toLowerCase(Locale.ROOT);
            boolean cocokKata = false;
            boolean cocokKategori = false;
            
            if (kode.contains(kata) || nama.contains(kata)) {
                cocokKata = true;
            }
            if ("Semua".equals(kategoriDipilih)){
                cocokKategori = true;
            }
            else if (penambahan.getKategori().equals(kategoriDipilih)){
                cocokKategori = true;
            }
            if (cocokKata && cocokKategori) {
                dataTampilan.add(penambahan);
            }
        }
        tabel.sort();
        pesanLabel.setText ("Jumlah data yang tampil: " + dataTampilan.size());
    }
    
    @FXML
    private void resetPencarian(){
        cariField.clear();
        filterKategoriBox.setValue("Semua");
        cari();
    }
    
    @FXML
    private void ubahTampilanHarga(){
        if (hargaCheck.isSelected()){
            hargaCol.setVisible(true);
        } else {
            hargaCol.setVisible(false);
        }
    }
    
    @FXML
    private void tambah(){
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText ("Form gagal dibuka");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void detail() {
        Penambahan penambahan = tabel.getSelectionModel().getSelectedItem();
        if (penambahan == null) {
            pesanLabel.setText("Pilih satu baris terlebih dahulu.");
            return;
        } 
        try {
            navigator.bukaDetail(penambahan);
        } catch (IOException e){
            pesanLabel.setText("Detail gagal dibuka.");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void hapus() {
        Penambahan penambahan = tabel.getSelectionModel().getSelectedItem();
        if (penambahan == null) {
            pesanLabel.setText("Pilih satu baris terlebih dahulu.");
            return;
        }
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus " + penambahan.getKode() + "?",
                ButtonType.YES, ButtonType.NO);
        dialog.initOwner(tabel.getScene().getWindow());
        ButtonType jawaban = dialog.showAndWait().orElse(ButtonType.NO);
        if (jawaban == ButtonType.YES) {
            data.hapus(penambahan);
            cari();
        }
    }
    
}

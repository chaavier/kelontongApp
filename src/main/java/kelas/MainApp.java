

package kelas;

/**
 *
 * @author HP
 */

import javafx.application.Application;
import javafx.stage.Stage;
import kelas.data.DataPenambahanProduk;

public class MainApp extends Application {
    
    @Override
    public void start (Stage stage) throws Exception{
        new Navigator (stage, new DataPenambahanProduk()).bukaForm();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

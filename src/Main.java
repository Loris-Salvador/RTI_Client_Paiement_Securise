import controller.MainWindowController;
import view.window.WindowClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class Main {
    public static void main(String[] args) {

        int i=0;

        while(i<4) {

            WindowClient mainWindow = new WindowClient();
            MainWindowController mainWindowController = null;
            try {
                mainWindowController = new MainWindowController(mainWindow);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            mainWindow.setController(mainWindowController);
            i++;
        }


    }
}
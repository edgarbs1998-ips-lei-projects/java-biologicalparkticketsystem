package biologicalparkticketsystem.model.document;

import biologicalparkticketsystem.LoggerManager;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Abstract class to save document data
 */
public abstract class ADocument {
    
    public final SimpleDateFormat fileDateFormat;
    public final SimpleDateFormat dataDateFormat;
    public PdfFont fontRegular, fontBold;
    
    public ADocument() {
        this.fileDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        this.dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try {
            this.fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            this.fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    /**
     * Method to open a pdf file in the desktop pdf reader
     * @param path pdf file path
     */
    public void openPdfFile(String path) {
        try {
            File pdfFile = new File(path);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    LoggerManager.getInstance().log("Awt Desktop is not supported!");
                }
            } else {
                LoggerManager.getInstance().log("File is not exists!");
            }
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
}

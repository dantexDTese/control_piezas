package Model.EtiquetasModel;

import com.itextpdf.text.pdf.Barcode128;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GenerateBarcode {
    private final Barcode128 barcode;
    private static final String DIRECCION_IMAGEN = "src\\Model\\EtiquetasModel\\codBarras.png";
   
    
    public GenerateBarcode(){
        this.barcode = new Barcode128();
    }
    
    public  void Generate128(String barcodeNumber){
        try{
            barcode.setCode(barcodeNumber);
            this.GenerateImage(DIRECCION_IMAGEN);
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    private void SaveFile(byte[] imageData,String file) throws FileNotFoundException, 
            IOException{
                  if(file != null){
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(imageData);
                    fos.flush();
                    fos.close();  
                  }
    }
    
    private void GenerateImage(String dir) throws IOException{
        java.awt.Image image = barcode.createAwtImage(Color.BLACK, Color.WHITE);
            BufferedImage outImage = new BufferedImage(image.getWidth(null), 
                    image.getHeight(null), BufferedImage.TYPE_INT_RGB);
             outImage.getGraphics().drawImage(image, 0, 0, null);
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ImageIO.write(outImage, "jpeg", bytesOut);
            bytesOut.flush();
            byte[] jpgImageData = bytesOut.toByteArray();
            
            this.SaveFile(jpgImageData,dir);
            
    }
    
}


package barcode;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;




public class Barcode {
    public static  String HTML = "src\\barcode\\templateEtiqueta.html";
    public static final String DEST = "src\\barcode\\rerere.pdf";
     public static final String CSS = "src\\barcode\\estilos.css";
     public static final String DIRECCION_ETIQUETAS = "src\\barcode\\ETIQUETA.png";
     public final static int ESCALA_ETIQUETA_15X10 = 19;
    /**
     * Html to pdf conversion example.
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
     
     public static Document document = new Document();
     
       public void createPdf(String file) throws IOException, DocumentException {
        // step 1
        
        
        // step 2
        
        
        Image imagen =  Image.getInstance(DIRECCION_ETIQUETAS);
        imagen.setAbsolutePosition(0f, 209f);
        imagen.setAlignment(Image.ALIGN_CENTER);
        imagen.scalePercent(ESCALA_ETIQUETA_15X10);
        
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        // step 3
        document.add(imagen);
        
        // step 4
        
        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(CSS));
        cssResolver.addCss(cssFile);
        
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
        
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new FileInputStream(HTML));
        
        // step 5
        
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException{
        
        /* HTML = "src\\barcode\\archivo.html";
        File archivo = new File(HTML);
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(archivo));
        String str = " <html>" +
                            "    <head>" +
                            "    </head>" +
                            "    <body>" +
                                "<img class='etiqueta' src='src\\barcode\\codBarras.png'/>" +
                                "<p style='position: absolute;'>ajskzsjdalksjdalsjdldkajsd</p>"+
                            "    </body>" +
                            "</html>";
        bw.write(str);
        
        bw.close();*/
        
        
        File file = new File(DEST);
        //file.getParentFile().mkdirs();
        new Barcode().createPdf(DEST);
        
        document.close();
    }
    
    
}

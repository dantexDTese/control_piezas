package Model.EtiquetasModel;


import Model.Estructuras;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
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
import ds.desktop.notify.DesktopNotify;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;







public class CreacionPDF {
        
    public final static int ESCALA_ETIQUETA_15X10 = 19;
    public final static Rectangle DOS_ETIQUETAS_PAPEL_15X10 = new Rectangle(250f, 400f);
    private final static Rectangle ETIQUETA_PAPEL_15X10 = new Rectangle(250f, 155f);
    private static final String DIRECCION_ETIQUETA_PDF  = "src\\Model\\EtiquetasModel\\etiqueta.pdf";
    private static final String DIRECCION_ETIQUETA_PDF_IMG  = "src\\Model\\EtiquetasModel\\etiqueta-pdf.png";
    private static final String DIRECCION_DOCUMENTO_IMPRIMIR  = "src\\Model\\EtiquetasModel\\impresion.pdf";
    private static final String DIRECCION_ETIQUETAS_IMG = "src\\Model\\EtiquetasModel\\ETIQUETA.png";
    private static final String DIRECCION_LOGO_MMT = "src\\Model\\EtiquetasModel\\mmt.jpeg";
    private static final String DIRECCION_LOGO_MGC = "src\\Model\\EtiquetasModel\\gmc2.jpeg";
    private static final String DIRECCION_COD_BARRAS = "src\\Model\\EtiquetasModel\\codBarras.png";
    private static final  String HTML = "src\\Model\\EtiquetasModel\\templateEtiqueta.html";    
    private static final String CSS = "src\\Model\\EtiquetasModel\\estilos.css";
    
    public static int LOGO_MMT = 1;
    public static int LOGO_GMC = 2;
    
    private final Document documento;
    private PdfWriter writer;
    private int escala;
    
    public CreacionPDF(Rectangle dimencionEtiqueta,int escala) throws FileNotFoundException{
        
        documento = new Document(dimencionEtiqueta);
        documento.setMargins(0, 0,25f, 0);
        this.escala = escala;
        try {
            writer = PdfWriter.getInstance(documento, new FileOutputStream(DIRECCION_DOCUMENTO_IMPRIMIR));  
            documento.open();
            
        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("error al abrirar el documento "+e.getMessage());
        }
    }

    
    public void agregarEtiqueta(String nombreCliente,String descProducto,String cantidad,String codProducto,
            String folio,String codEtiqueta,String oc,int tLogo) throws IOException, DocumentException{
                   
         Document documento = new Document(ETIQUETA_PAPEL_15X10);
         documento.setMargins(0, 0, 0, 0);
         PdfWriter writer;
         
         writer = PdfWriter.getInstance(documento, new FileOutputStream(DIRECCION_ETIQUETA_PDF));  
         documento.open();
         
          crearHTML(nombreCliente,descProducto,cantidad,codProducto,folio,codEtiqueta,oc);
          new GenerateBarcode().Generate128(codEtiqueta);
          createPdf(documento,writer);
        
          try {
              
            Image plantilla =  Image.getInstance(DIRECCION_ETIQUETAS_IMG);
            plantilla.setAbsolutePosition(0f, 0f);
            plantilla.scalePercent(ESCALA_ETIQUETA_15X10);
            plantilla.setAlignment(Image.ALIGN_CENTER);
            
            Image codBarras =  Image.getInstance(DIRECCION_COD_BARRAS);
            codBarras.setAbsolutePosition(132f, 10f);
            codBarras.scalePercent(60);
            
            Image logo;
            
            if(tLogo == LOGO_MMT)
                logo =  Image.getInstance(DIRECCION_LOGO_MMT);               
            else if(tLogo == LOGO_GMC)
                logo =  Image.getInstance(DIRECCION_LOGO_MGC);
            else return;
            
            logo.scalePercent(40);
            logo.setAbsolutePosition(10f, 120f);
            
            documento.add(plantilla);
            documento.add(logo);
            documento.add(codBarras);
            
            documento.close();

            convertirEtiquetaImagen();
            agregarAlPrincipal();
            
        } catch (DocumentException | IOException e) {
            System.err.println("error"+e.getMessage());
        } 
         
    }
    
    private void agregarAlPrincipal() throws DocumentException {
     
        try {
            Paragraph p = new Paragraph();
            p.add("\n");
            
            Image etiquetaTerminada =  Image.getInstance(DIRECCION_ETIQUETA_PDF_IMG);
            etiquetaTerminada.scalePercent(escala);
            documento.add(etiquetaTerminada);
            documento.add(p);
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(CreacionPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void convertirEtiquetaImagen(){
            try {
                PDDocument document = PDDocument.load(new File(DIRECCION_ETIQUETA_PDF));
                PDFRenderer pDFRenderer = new PDFRenderer(document);
                BufferedImage bin = pDFRenderer.renderImageWithDPI(0, 380,ImageType.RGB);
                ImageIOUtil.writeImage(bin,DIRECCION_ETIQUETA_PDF_IMG, 380);
                document.close();
            } catch (IOException ex) {
                Logger.getLogger(CreacionPDF.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
    }
    
    public void createPdf(Document document,PdfWriter writer) throws IOException, DocumentException {
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
    
    private void crearHTML(String nombreCliente,String descProducto,String cantidad,String codProducto,
            String folio,String codEtiqueta,String oc){
        File archivo = new File(HTML);
        BufferedWriter bw;
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                String html = "<!DOCTYPE html>" +
                                "<html>" +
                                "    <head>" +
                                "        <title>TODO supply a title</title> " +
                                "    </head> " +
                                "    <body>  " +
                                "        <div class='contenedor'>  " +
                                "            <p class='campos_cliente' >"+nombreCliente+"</p> " +
                                "            <table class='campos_producto_cantidad'> " +
                                "                <tr > " +
                                "                    <td style='width: 235px;'><p style='width: 200px; margin-left: 52px;'>"+codProducto+"</p></td> " +
                                "                    <td style='width: 100px;'><p style='width: 100px; margin-left: 30px; text-align: right;'>"+cantidad+"</p></td> " +
                                "                </tr> " +
                                "            </table> " +
                                "            <p class='campos_descripcion' >"+descProducto+"</p> " +
                                "            <table class='campos_producto_cantidad'> " +
                                "                <tr > " +
                                "                    <td style='width: 220px;'><p style='margin-left: 52px;'>"+Estructuras.convertirFechaGuardar(new Date())+"</p></td> " +
                                "                    <td style='width: 100px;'><p style='margin-left: 10px;'>"+folio+"</p></td> " +
                                "                </tr> " +
                                "            </table> " +
                                "            <p class='campos_descripcion' >"+codEtiqueta+"</p> " +
                                "            <table class='campos_producto_cantidad'> " +
                                "                <tr > " +
                                "                    <td><p style='width: 250px; margin-left: 52px;'>"+oc+"</p></td> " +
                                "                </tr> " +
                                "            </table> " +
                                "        </div> " +
                                "    </body>" +
                                "</html>";
                bw.write(html);
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(CreacionPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    public void terminarPDF(){ 
        documento.close();
        DesktopNotify.showDesktopMessage("OPERACION EXITOSA", "\nLAS ETIQUETAS SE HAN CREADO CORRECTAMENTE\n", 
                DesktopNotify.SUCCESS,5000);
        System.out.println("documento creado");
    }
    
    public void imprimirEtiquetas(){
        if(!documento.isOpen()){
            try {
                
                PDDocument document = PDDocument.load(new File(DIRECCION_DOCUMENTO_IMPRIMIR));
                PrinterJob job = PrinterJob.getPrinterJob();
                if (job.printDialog() == true) {
                    job.setPageable(new PDFPageable(document));
                    job.print();
                    DesktopNotify.showDesktopMessage("OPERACION EXITOSA", "\nLAS ETIQUETAS SE HAN IMPRESO CORRECTAMENTE\n", 
                DesktopNotify.SUCCESS,5000);
                    document.close();
                }   } catch (IOException | PrinterException ex) {
                Logger.getLogger(CreacionPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else JOptionPane.showMessageDialog(null, "El documento sigue abierto");
        
        
    }
    
    

    
    
}

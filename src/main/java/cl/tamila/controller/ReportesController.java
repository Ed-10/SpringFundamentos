package cl.tamila.controller;

import cl.tamila.modelos.ProductosModel;
import cl.tamila.service.ProductoService;
import cl.tamila.utilidades.Utilidades;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReportesController {
    @Value("${eduardo.valores.ruta_images}")
    private String ruta_images;
    @Value("eduardo.ruta.pdf")
    private String ruta_pdf;
    @GetMapping("")
    public String home()
    {
        return "reportes/home";
    }
    //// Para EXCEL
    private XSSFWorkbook workbook;
    ///////////////////////////////// Seccion PDF ////////////////////////////////////////////////////////////////////
    private final TemplateEngine templateEngine;
    public ReportesController(TemplateEngine templateEngine)
    {
        this.templateEngine=templateEngine;
        this.workbook=new XSSFWorkbook();//Instancia de variable declarada para EXCEL
    }
    @Autowired
    private ServletContext servletContext;
    @GetMapping("/pdf")
    public ResponseEntity<?> productos_pdf(HttpServletRequest request,
                                           HttpServletResponse response)
    {
        WebContext context=new WebContext(request,response,this.servletContext);
        context.setVariable("titulo","PDF dinamico desde Spring Boot");
        context.setVariable("ruta", this.ruta_images);

        String html= this.templateEngine.process("reportes/pdf",context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties converterProperties= new ConverterProperties();
        converterProperties.setBaseUri(this.ruta_pdf);

        HtmlConverter.convertToPdf(html,target, converterProperties);

        byte [] bytes=target.toByteArray();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
    }
    ///////////////////////////////// Seccion EXCEL ////////////////////////////////////////////////////////////////////
    private XSSFSheet sheet;
    @Autowired
    private ProductoService productoService; //Servicio para cargar datos desde MySQL
    @GetMapping("/excel")
    public void productos_excel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        long time=System.currentTimeMillis();
        String headerKey="Content-Disposition";
        String headerValue= "attachment; filename=reporte_"+time+".xlsx";

        response.setHeader(headerKey,headerValue);
        response.setHeader("time",time+"");

        this.sheet=this.workbook.createSheet("Hoja 1"+time);

        CellStyle style = this.workbook.createCellStyle();

        XSSFFont font=this.workbook.createFont();
        //font.setBold(true);
        //font.setFontHeight(16);
        style.setFont(font);

        //Creacion de encabezados (Objeto de tipo Row)
        Row row=this.sheet.createRow(0);//Creacion de la fila, inicial en 0
        //Generacion de filas del encabezado
        createCell(row,0,"ID",style);
        createCell(row,1,"Nombre",style);
        createCell(row,2,"Descripcion",style);
        createCell(row,3,"Precio",style);
        //createCell(row,4,"Foto",style); A REVISAR, SOLO LANZA EL ENLACE DE UBICACION DE LA IMAGEN
        createCell(row,4,"Time",style);
        //Generacion de filas del reporte
        List<ProductosModel> datos = this.productoService.listar2();
        int rowCont = 1;
        for (ProductosModel dato:datos)
        {
            row=this.sheet.createRow(rowCont++);
            int columnCount = 0;
            createCell(row,columnCount++,dato.getId(),style);
            createCell(row,columnCount++,dato.getNombre(),style);
            createCell(row,columnCount++,dato.getDescripcion(),style);
            //createCell(row,columnCount++, Utilidades.numberFormat(dato.getPrecio()),style); A REVISAR
            createCell(row,columnCount++,dato.getPrecio(),style);
            //createCell(row,columnCount++,this.ruta_images+"ImagesSpring/"+dato.getFoto(),style);
            createCell(row,columnCount++,time+"",style);
        }
        //Se formatea la salida
        ServletOutputStream outputStream = response.getOutputStream();
        this.workbook.write(outputStream);
        this.workbook.close();
        outputStream.close();

    }
    //Metodo para la creacion de celdas
    private void createCell(Row row, int columnCount, Object value, CellStyle style)
    {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer)
        {
            cell.setCellValue((Integer)value);
        } else if (value instanceof Boolean)
            {
                cell.setCellValue((Boolean) value);
            }
        else
        {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    ///////////////////////////////// Seccion CSV ////////////////////////////////////////////////////////////////////
    @GetMapping ("/csv")
    public void csv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        long time=System.currentTimeMillis();
        String headerKey="Content-Disposition";
        String headerValue= "attachment; filename=reporte_"+time+".csv";
        response.setContentType("text/csv;charset=utf-8");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID","Nombre", "Descripcion","Precio","Foto"};
        String[] nameMapping = {"id","nombre", "descripcion","precio","foto"};

        csvBeanWriter.writeHeader(csvHeader);

        //Formateando filas dinamicas
        List<ProductosModel> datos = this.productoService.listar2();
        for (ProductosModel dato:datos)
        {
            csvBeanWriter.write(dato,nameMapping);
        }

        csvBeanWriter.close();
    }

}

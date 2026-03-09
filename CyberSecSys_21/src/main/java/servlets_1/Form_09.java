package servlets_1;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/forms/report"})
public class Form_09 extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Font TITLE_FONT;
   private static final Font SUBTITLE_FONT;
   private static final Font HEADER_FONT;
   private static final Font INFO_FONT;
   private static final Font CONTENT_FONT;
   private static final Font TABLE_HEADER_FONT;
   private static final Font FOOTER_FONT;

   static {
      TITLE_FONT = FontFactory.getFont("Helvetica-Bold", 20.0F, BaseColor.WHITE);
      SUBTITLE_FONT = FontFactory.getFont("Helvetica", 12.0F, BaseColor.WHITE);
      HEADER_FONT = FontFactory.getFont("Helvetica-Bold", 16.0F, new BaseColor(76, 92, 176));
      INFO_FONT = FontFactory.getFont("Helvetica", 11.0F, BaseColor.DARK_GRAY);
      CONTENT_FONT = FontFactory.getFont("Helvetica", 10.0F, BaseColor.BLACK);
      TABLE_HEADER_FONT = FontFactory.getFont("Helvetica-Bold", 10.0F, BaseColor.WHITE);
      FOOTER_FONT = FontFactory.getFont("Helvetica", 8.0F, BaseColor.WHITE);
   }

   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      Document documento = new Document();
      String reportType = req.getParameter("report");
      if (reportType == null) {
         reportType = "approach";
      }

      List<String> lista = new ArrayList();
      String reportTitle = "";
      String reportDescription = "";

      try {
         CyberSecView view = (CyberSecView)req.getServletContext().getAttribute("cyberSecView");
         if (view == null) {
            lista.add("Erro: CyberSecView não foi carregada no contexto da aplicação.");
            reportTitle = "Erro";
            reportDescription = "View não carregada";
         } else {
            label76: {
               switch(reportType.hashCode()) {
               case -1737370588:
                  if (reportType.equals("systems")) {
                     lista.addAll(view.getSystems());
                     reportTitle = "Systems";
                     reportDescription = "Information systems types";
                     break label76;
                  }
                  break;
               case -1480388560:
                  if (reportType.equals("performance")) {
                     lista.addAll(view.getPerformanceEvaluation());
                     reportTitle = "Performance Evaluation";
                     reportDescription = "Performance evaluation methods in cybersecurity";
                     break label76;
                  }
                  break;
               case -1399907075:
                  if (reportType.equals("component")) {
                     lista.addAll(view.getComponent());
                     reportTitle = "System Components";
                     reportDescription = "Components of information systems";
                     break label76;
                  }
                  break;
               case -1393878029:
                  if (reportType.equals("defensive")) {
                     lista.addAll(view.getDefensiveTechnique7());
                     reportTitle = "Defensive Techniques";
                     reportDescription = "Cybersecurity defensive techniques";
                     break label76;
                  }
                  break;
               case 96673:
                  if (reportType.equals("all")) {
                     lista.addAll(view.getApproach());
                     lista.addAll(view.getPerformanceEvaluation());
                     lista.addAll(view.getComponent());
                     lista.addAll(view.getSystems());
                     lista.addAll(view.getOSAPISystemFunctions3());
                     lista.addAll(view.getDefensiveTechnique7());
                     lista.addAll(view.getAttackEnterpriseMitigation1());
                     reportTitle = "Complete Cybersecurity Report";
                     reportDescription = "All cybersecurity concepts and categories";
                     break label76;
                  }
                  break;
               case 106033590:
                  if (reportType.equals("osapi")) {
                     lista.addAll(view.getOSAPISystemFunctions3());
                     reportTitle = "OS/API System Functions";
                     reportDescription = "Operating System and API functions";
                     break label76;
                  }
                  break;
               case 1185224616:
                  if (reportType.equals("approach")) {
                     lista.addAll(view.getApproach());
                     reportTitle = "Cybersecurity Approach";
                     reportDescription = "Ways in which cybersecurity performance is assessed";
                     break label76;
                  }
                  break;
               case 1293793087:
                  if (reportType.equals("mitigation")) {
                     lista.addAll(view.getAttackEnterpriseMitigation1());
                     reportTitle = "Attack Enterprise Mitigation";
                     reportDescription = "Attack enterprise mitigation strategies";
                     break label76;
                  }
               }

               lista.addAll(view.getApproach());
               reportTitle = "Cybersecurity Approach";
               reportDescription = "Ways in which cybersecurity performance is assessed";
            }
         }
      } catch (Exception var15) {
         lista.add("Erro ao carregar dados da ontologia: " + var15.getMessage());
         reportTitle = "Erro";
         reportDescription = "Exceção durante o carregamento";
      }

      Collections.sort(lista);

      try {
         res.setContentType("application/pdf");
         String filename = reportType + "_report.pdf";
         res.addHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
         PdfWriter.getInstance(documento, res.getOutputStream());
         documento.open();
         this.addHeader(documento);
         documento.add(new Paragraph(" "));
         Paragraph contentTitle = new Paragraph(reportTitle, HEADER_FONT);
         contentTitle.setSpacingAfter(20.0F);
         documento.add(contentTitle);
         if (!lista.isEmpty() && ((String)lista.get(0)).startsWith("Erro")) {
            Paragraph errorMsg = new Paragraph((String)lista.get(0), INFO_FONT);
            errorMsg.setSpacingAfter(15.0F);
            documento.add(errorMsg);
         } else {
            String infoText = "Found " + lista.size() + " items in " + reportTitle.toLowerCase() + ".";
            Paragraph infoParagraph = new Paragraph(infoText, INFO_FONT);
            infoParagraph.setSpacingAfter(10.0F);
            documento.add(infoParagraph);
            Paragraph description = new Paragraph(reportDescription + ":", CONTENT_FONT);
            description.setSpacingAfter(15.0F);
            documento.add(description);
            if (lista.isEmpty()) {
               Paragraph noData = new Paragraph("No instances found in the ontology.", INFO_FONT);
               noData.setSpacingAfter(15.0F);
               documento.add(noData);
            } else {
               PdfPTable table = this.createTable(lista);
               table.setSpacingBefore(10.0F);
               table.setSpacingAfter(20.0F);
               documento.add(table);
            }
         }

         this.addFooter(documento);
         documento.close();
      } catch (Exception var14) {
         System.out.println("Erro ao gerar PDF: " + var14);
         if (documento.isOpen()) {
            documento.close();
         }
      }

   }

   private void addHeader(Document documento) throws DocumentException {
      PdfPTable headerTable = new PdfPTable(1);
      headerTable.setWidthPercentage(100.0F);
      headerTable.setSpacingAfter(20.0F);
      PdfPCell headerCell = new PdfPCell();
      headerCell.setBackgroundColor(new BaseColor(76, 92, 176));
      headerCell.setPadding(20.0F);
      headerCell.setBorder(0);
      Paragraph mainTitle = new Paragraph("CyberSecSys Version 1.0", TITLE_FONT);
      mainTitle.setAlignment(1);
      Paragraph subtitle = new Paragraph("Cybersecurity Management in Information Systems", SUBTITLE_FONT);
      subtitle.setAlignment(1);
      headerCell.addElement(mainTitle);
      headerCell.addElement(subtitle);
      headerTable.addCell(headerCell);
      documento.add(headerTable);
   }

   private PdfPTable createTable(List<String> lista) throws DocumentException {
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(100.0F);
      table.setWidths(new float[]{0.3F, 4.7F});
      PdfPCell idHeader = new PdfPCell(new Phrase("No.", TABLE_HEADER_FONT));
      idHeader.setBackgroundColor(new BaseColor(76, 92, 176));
      idHeader.setPadding(4.0F);
      idHeader.setHorizontalAlignment(1);
      idHeader.setMinimumHeight(15.0F);
      PdfPCell descHeader = new PdfPCell(new Phrase("Description", TABLE_HEADER_FONT));
      descHeader.setBackgroundColor(new BaseColor(76, 92, 176));
      descHeader.setPadding(5.0F);
      descHeader.setHorizontalAlignment(0);
      descHeader.setMinimumHeight(15.0F);
      table.addCell(idHeader);
      table.addCell(descHeader);

      for(int i = 0; i < lista.size(); ++i) {
         PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(i + 1), CONTENT_FONT));
         idCell.setPadding(3.0F);
         idCell.setHorizontalAlignment(1);
         idCell.setBackgroundColor(new BaseColor(248, 249, 250));
         idCell.setMinimumHeight(12.0F);
         PdfPCell descCell = new PdfPCell(new Phrase((String)lista.get(i), CONTENT_FONT));
         descCell.setPadding(4.0F);
         descCell.setHorizontalAlignment(0);
         descCell.setBackgroundColor(new BaseColor(248, 249, 250));
         descCell.setMinimumHeight(12.0F);
         table.addCell(idCell);
         table.addCell(descCell);
      }

      return table;
   }

   private void addFooter(Document documento) throws DocumentException {
      PdfPTable footerTable = new PdfPTable(1);
      footerTable.setWidthPercentage(100.0F);
      footerTable.setSpacingBefore(20.0F);
      PdfPCell footerCell = new PdfPCell();
      footerCell.setBackgroundColor(new BaseColor(26, 26, 26));
      footerCell.setPadding(10.0F);
      footerCell.setBorder(0);
      Paragraph copyright1 = new Paragraph("© 2025 Cybersecurity Management in Information Systems", FOOTER_FONT);
      copyright1.setAlignment(1);
      Paragraph copyright2 = new Paragraph("© Federal University of the State of Rio de Janeiro (UNIRIO)", FOOTER_FONT);
      copyright2.setAlignment(1);
      Paragraph copyright3 = new Paragraph("© Graduate Program in Computer Science (PPGI)", FOOTER_FONT);
      copyright3.setAlignment(1);
      Paragraph author = new Paragraph("PhD Student: Roberto Monteiro Dias", FOOTER_FONT);
      author.setAlignment(1);
      footerCell.addElement(copyright1);
      footerCell.addElement(copyright2);
      footerCell.addElement(copyright3);
      footerCell.addElement(author);
      footerTable.addCell(footerCell);
      documento.add(footerTable);
   }
}

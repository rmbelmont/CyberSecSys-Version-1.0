package servlets_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * Servlet responsável por gerar relatórios em CSV ou XLS
 */
@WebServlet({"/forms/report-csv", "/forms/reportcsv"})

public class Form_10 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// Parâmetros de requisição
		String reportType = req.getParameter("report");
		String format = req.getParameter("format");

		if (reportType == null)
			reportType = "approach";
		if (format == null)
			format = "csv";

		List<String> lista = new ArrayList<>();
		String reportTitle = "";
		String reportDescription = "";

		try {
			CyberSecView view = (CyberSecView) req.getServletContext().getAttribute("cyberSecView");

			if (view == null) {
				lista.add("Erro: CyberSecView não foi carregada.");
				reportTitle = "Erro";
				reportDescription = "View não carregada";
			} else {
				switch (reportType) {
				case "systems":
					lista.addAll(view.getSystems());
					reportTitle = "Systems";
					reportDescription = "Information systems types";
					break;
				case "performance":
					lista.addAll(view.getPerformanceEvaluation());
					reportTitle = "Performance Evaluation";
					reportDescription = "Performance evaluation methods";
					break;
				case "component":
					lista.addAll(view.getComponent());
					reportTitle = "System Components";
					reportDescription = "Components of information systems";
					break;
				case "defensive":
					lista.addAll(view.getDefensiveTechnique7());
					reportTitle = "Defensive Techniques";
					reportDescription = "Cybersecurity defensive techniques";
					break;
				case "osapi":
					lista.addAll(view.getOSAPISystemFunctions3());
					reportTitle = "OS/API System Functions";
					reportDescription = "Operating System and API functions";
					break;
				case "mitigation":
					lista.addAll(view.getAttackEnterpriseMitigation1());
					reportTitle = "Attack Enterprise Mitigation";
					reportDescription = "Attack mitigation strategies";
					break;
				case "all":
					lista.addAll(view.getApproach());
					lista.addAll(view.getPerformanceEvaluation());
					lista.addAll(view.getComponent());
					lista.addAll(view.getSystems());
					lista.addAll(view.getOSAPISystemFunctions3());
					lista.addAll(view.getDefensiveTechnique7());
					lista.addAll(view.getAttackEnterpriseMitigation1());
					reportTitle = "Complete Cybersecurity Report";
					reportDescription = "All cybersecurity concepts";
					break;
				default:
					lista.addAll(view.getApproach());
					reportTitle = "Cybersecurity Approach";
					reportDescription = "Cybersecurity evaluation approaches";
					break;
				}
			}
		} catch (Exception e) {
			lista.add("Erro ao carregar dados: " + e.getMessage());
			reportTitle = "Erro";
			reportDescription = "Erro interno";
		}

		Collections.sort(lista);

		// Geração do arquivo
		if ("xls".equalsIgnoreCase(format)) {
			generateXLS(res, reportTitle, reportDescription, lista);
		} else {
			generateCSV(res, reportTitle, reportDescription, lista);
		}
	}

	// ================== Geração CSV ==================
	private void generateCSV(HttpServletResponse res, String title, String description, List<String> data)
			throws IOException {
		res.setContentType("text/csv");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + title + ".csv\"");
		try (PrintWriter out = res.getWriter()) {
			out.println(title);
			out.println(description);
			for (String line : data) {
				out.println(line);
			}
		}
	}

	// ================== Geração XLS ==================
	private void generateXLS(HttpServletResponse res, String title, String description, List<String> data)
			throws IOException {
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + title + ".xls\"");

		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Report");

			// Estilo do cabeçalho
			HSSFCellStyle headerStyle = workbook.createCellStyle();
			var font = workbook.createFont();
			font.setBold(true);
			headerStyle.setFont(font);
			headerStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);

			int rownum = 0;

			// Linha título
			HSSFRow rowTitle = sheet.createRow(rownum++);
			HSSFCell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellValue(title);
			cellTitle.setCellStyle(headerStyle);

			// Linha descrição
			HSSFRow rowDesc = sheet.createRow(rownum++);
			HSSFCell cellDesc = rowDesc.createCell(0);
			cellDesc.setCellValue(description);

			// Linhas de dados
			for (String item : data) {
				HSSFRow row = sheet.createRow(rownum++);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(item);
			}

			sheet.autoSizeColumn(0);

			workbook.write(res.getOutputStream());
		}
	}
}
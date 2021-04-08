package it.laziocrea.jemoloapp.file.helper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoCriteria;

public class ExcelHelper {
  private static final Logger log = LoggerFactory.getLogger(ExcelHelper.class);
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Nome", "Cognome", "ID Scheda", "Codice Fiscale", "Stato", "E_Mail", "Recapito telefonico", "Indirizzo residenza", "Data ultimo aggiornamento" };
  static String SHEET = "Elenco scritti al Roster";
  static String[] HEADERs_RICERCA = { "Nome", "Cognome", "Codice Fiscale", "CV" };
  static String SHEET_RICERCA = "Risultato Ricerca";

  public static ByteArrayInputStream tutorialsToExcel(List<Candidato> iscritti) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);
      DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ).withLocale( Locale.ITALY ).withZone( ZoneId.systemDefault() );
      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }
      
      XSSFColor inactiveColor =  new XSSFColor(java.awt.Color.red, null);
      XSSFColor activateColor =  new XSSFColor(java.awt.Color.green, null);
      // cellStyle.setFillForegroundColor(activateColor);
      
      int rowIdx = 1;
      for (Candidato iscritto : iscritti) {
          XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
          cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Row row = sheet.createRow(rowIdx++);
        AnagraficaCandidato anag = iscritto.getAnagraficaCandidato();
        if (iscritto.isActivated() == true) cellStyle.setFillForegroundColor(activateColor);
        else cellStyle.setFillForegroundColor(inactiveColor);
        // log.debug("anagrafica: {}", anag);
        row.createCell(0).setCellValue(iscritto.getId()+":"+iscritto.getLogin());
        row.createCell(1).setCellValue(iscritto.getNome());
        row.createCell(2).setCellValue(iscritto.getCognome());
        if (anag != null) row.createCell(3).setCellValue(anag.getId());
        row.createCell(4).setCellValue(iscritto.getCodiceFiscale());
        Cell cel = row.createCell(5);
        cel.setCellValue(iscritto.isActivated() == true ? "attivo" : "non attivo");
        cel.setCellStyle(cellStyle);
        row.createCell(6).setCellValue(iscritto.getEmail());
        row.createCell(7).setCellValue(anag != null ? anag.getNumeroTelefonoCellulare() : null);
        row.createCell(8).setCellValue(anag != null ? anag.getIndirizzoResidenza() : null);
        row.createCell(9).setCellValue(anag != null ? formatter.format(anag.getLastModifiedDate()) : null);
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

	public static ByteArrayInputStream risultatoRicercaToExcel(List<AnagraficaCandidato> risultatoRicerca, Map<String, String> searchParam) {
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	        Sheet sheet = workbook.createSheet(SHEET_RICERCA);
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        // Header
	        Row headerRow = sheet.createRow(0);
	        XSSFCellStyle hcellStyle = (XSSFCellStyle) workbook.createCellStyle();
	        hcellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//	        hcellStyle.setAlignment(HorizontalAlignment.CENTER);
	        hcellStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY, null));
	        hcellStyle.setBorderBottom(BorderStyle.THIN);
	        hcellStyle.setBorderTop(BorderStyle.THIN);
	        hcellStyle.setBorderRight(BorderStyle.THIN);
	        hcellStyle.setBorderLeft(BorderStyle.THIN);
	        for (int col = 0; col < HEADERs_RICERCA.length; col++) {
	          Cell cell = headerRow.createCell(col);
	          cell.setCellValue(HEADERs_RICERCA[col]);
	          cell.setCellStyle(hcellStyle);
	        }
	        
	        XSSFColor inactiveColor =  new XSSFColor(java.awt.Color.red, null);
	        XSSFColor activateColor =  new XSSFColor(java.awt.Color.green, null);
	        // cellStyle.setFillForegroundColor(activateColor);
	        
	        int rowIdx = 1;
	        for (AnagraficaCandidato anagrafica : risultatoRicerca) {
	            XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
	            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	            cellStyle.setAlignment(HorizontalAlignment.CENTER);
	            Row row = sheet.createRow(rowIdx++);
	            Candidato candidato = anagrafica.getCandidato();
	            row.createCell(0).setCellValue(candidato.getNome());
	            row.createCell(1).setCellValue(candidato.getCognome());
	            row.createCell(2).setCellValue(candidato.getCodiceFiscale());
	            row.createCell(3).setCellValue(anagrafica.getCurricula().size() > 0 ? anagrafica.getCurricula().iterator().next().getCv() : "");
	        }
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
	        sheet.autoSizeColumn(3);
	        
	        Row row = sheet.createRow(rowIdx++);
	        Cell cell = row.createCell(0);
	        cell.setCellValue("Data");
	        cell.setCellStyle(hcellStyle);
	        // sheet.addMergedRegion(new CellRangeAddress(rowIdx-1,rowIdx-1,0,1));
	        row = sheet.createRow(rowIdx++);
	        row.createCell(0).setCellValue(formatter.format(new Date()));
	        // sheet.addMergedRegion(new CellRangeAddress(rowIdx-1,rowIdx-1,2,3));
	        
	        row = sheet.createRow(rowIdx++);
	        int colIdx = 0;
	        cell = row.createCell(colIdx++);
	        cell.setCellValue("Parametri");
	        cell.setCellStyle(hcellStyle);
	        /*searchParam.forEach((k, v) -> {
	        });*/
	        Iterator<Entry<String, String>> iterator = searchParam.entrySet().iterator();
	        while (iterator.hasNext()) {
	        	Entry pair = iterator.next();
	        	row = sheet.createRow(rowIdx++);
	        	row.createCell(0).setCellValue(pair.getKey()+" = "+pair.getValue());
	        }
	        workbook.write(out);
	        return new ByteArrayInputStream(out.toByteArray());
	      } catch (IOException e) {
	        throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	   }
	}
}

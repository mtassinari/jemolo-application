import { Injectable, ElementRef } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExportService {

    fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    fileExtension = '.xlsx';

  constructor() { }

  public exportExcel(jsonData: any[], fileName: string): void {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(jsonData);
    const wb: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    this.saveExcelFile(excelBuffer, fileName);
  }

  public exportExcel2(TABLE: ElementRef): void {
      const ws2: XLSX.WorkSheet = XLSX.utils.table_to_sheet(TABLE.nativeElement);
      const wb2: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb2, ws2, 'Sheet1');
      XLSX.writeFile(wb2, 'ricerca-iscritti.xlsx');
  }

  private saveExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {type: this.fileType});
    FileSaver.saveAs(data, fileName + this.fileExtension);
  }
}

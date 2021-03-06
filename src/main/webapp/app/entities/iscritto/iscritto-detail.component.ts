import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import * as FileSaver from 'file-saver';

import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { ILivello } from 'app/shared/model/competenze-lng.model';
import { ITipologia } from 'app/shared/model/titolo-studio.model';

@Component({
  selector: 'jhi-anagrafica-candidato-detail',
  templateUrl: './iscritto-detail.component.html',
  styleUrls: ['./iscritto-detail.component.scss']
})
export class IscrittoDetailComponent implements OnInit {
  anagraficaCandidato: IAnagraficaCandidato;
  livelli: ILivello[] = [
    {value: 1, viewValue: 'A1'},
    {value: 2, viewValue: 'A2'},
    {value: 3, viewValue: 'B1'},
    {value: 4, viewValue: 'B2'},
    {value: 5, viewValue: 'C1'},
    {value: 6, viewValue: 'C2'}
  ];
  tipologie: ITipologia[] = [
    {value: 'diploma-laurea', viewValue: 'Diploma di laurea vecchio ordinamento di durata non inferiore a 4 anni'},
    {value: 'laurea-magistrale', viewValue: 'Laurea magistrale nuovo ordinamento di durata normale di 5 o 6 anni (a ciclo unico)'},
    {value: 'laurea-specialistica', viewValue: 'Laurea specialistica di durata normale di 2 anni (dopo aver conseguito la laurea di durata di 3 anni)'}
  ];

  constructor(protected curriculumService: CurriculumService, protected activatedRoute: ActivatedRoute, protected router: Router) {}

   getLivello(id): String {
     return this.livelli.find(x => x.value === id).viewValue;
   }

   getTipologia(id): String {
     return this.tipologie.find(x => x.value === id).viewValue;
   }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anagraficaCandidato }) => {
      this.anagraficaCandidato = anagraficaCandidato;
    });
  }

  previousState() {
    window.history.back();
    // this.router.navigate(['/jemolouser/iscritti']);
  }

  downloadCv(id, cv) {
      this.curriculumService.download(id).subscribe(file => {
          FileSaver.saveAs(file, cv);
      });
  }

  formatBytes(bytes, decimals = 2) {
      if (bytes === 0) { return '0 Bytes'; }

      const k = 1024;
      const dm = decimals < 0 ? 0 : decimals;
      const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

      const i = Math.floor(Math.log(bytes) / Math.log(k));

      return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

}

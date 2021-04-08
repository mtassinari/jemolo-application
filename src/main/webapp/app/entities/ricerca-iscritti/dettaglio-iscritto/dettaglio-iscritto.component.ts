import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

@Component({
  selector: 'jhi-dettaglio-iscritto',
  templateUrl: './dettaglio-iscritto.component.html',
  styleUrls: ['./dettaglio-iscritto.component.scss']
})
export class DettaglioIscrittoComponent implements OnInit {
    anagraficaCandidato: IAnagraficaCandidato;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
      this.activatedRoute.data.subscribe(({ anagraficaCandidato }) => {
        this.anagraficaCandidato = anagraficaCandidato;
      });
    }

    previousState() {
      window.history.back();
    }
}

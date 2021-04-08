import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

@Component({
  selector: 'jhi-anagrafica-candidato-detail',
  templateUrl: './anagrafica-candidato-detail.component.html'
})
export class AnagraficaCandidatoDetailComponent implements OnInit {
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

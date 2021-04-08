import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICandidato } from 'app/shared/model/candidato.model';

@Component({
  selector: 'jhi-candidato-detail',
  templateUrl: './candidato-detail.component.html'
})
export class CandidatoDetailComponent implements OnInit {
  candidato: ICandidato;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ candidato }) => {
      this.candidato = candidato;
    });
  }

  previousState() {
    window.history.back();
  }
}

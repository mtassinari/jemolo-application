import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';

@Component({
  selector: 'jhi-dichiarazioni-obligatorie-detail',
  templateUrl: './dichiarazioni-obligatorie-detail.component.html',
})
export class DichiarazioniObligatorieDetailComponent implements OnInit {
  dichiarazioniObligatorie: IDichiarazioniObligatorie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dichiarazioniObligatorie }) => (this.dichiarazioniObligatorie = dichiarazioniObligatorie));
  }

  previousState(): void {
    window.history.back();
  }
}

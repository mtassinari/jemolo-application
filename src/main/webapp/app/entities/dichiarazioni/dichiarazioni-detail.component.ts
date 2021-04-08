import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';

@Component({
  selector: 'jhi-dichiarazioni-detail',
  templateUrl: './dichiarazioni-detail.component.html',
})
export class DichiarazioniDetailComponent implements OnInit {
  dichiarazioni: IDichiarazioni | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dichiarazioni }) => (this.dichiarazioni = dichiarazioni));
  }

  previousState(): void {
    window.history.back();
  }
}

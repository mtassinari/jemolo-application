import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';

@Component({
  selector: 'jhi-stato-registrazione-detail',
  templateUrl: './stato-registrazione-detail.component.html'
})
export class StatoRegistrazioneDetailComponent implements OnInit {
  statoRegistrazione: IStatoRegistrazione;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ statoRegistrazione }) => {
      this.statoRegistrazione = statoRegistrazione;
    });
  }

  previousState() {
    window.history.back();
  }
}

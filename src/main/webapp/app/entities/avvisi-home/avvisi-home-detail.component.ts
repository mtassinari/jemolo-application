import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvvisiHome } from 'app/shared/model/avvisi-home.model';

@Component({
  selector: 'jhi-avvisi-home-detail',
  templateUrl: './avvisi-home-detail.component.html',
})
export class AvvisiHomeDetailComponent implements OnInit {
  avvisiHome: IAvvisiHome | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avvisiHome }) => (this.avvisiHome = avvisiHome));
  }

  previousState(): void {
    window.history.back();
  }
}

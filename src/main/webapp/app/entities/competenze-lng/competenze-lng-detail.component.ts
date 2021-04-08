import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';

@Component({
  selector: 'jhi-competenze-lng-detail',
  templateUrl: './competenze-lng-detail.component.html'
})
export class CompetenzeLngDetailComponent implements OnInit {
  competenzeLng: ICompetenzeLng;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ competenzeLng }) => {
      this.competenzeLng = competenzeLng;
    });
  }

  previousState() {
    window.history.back();
  }
}

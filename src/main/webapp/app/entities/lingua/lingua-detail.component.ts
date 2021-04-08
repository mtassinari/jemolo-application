import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILingua } from 'app/shared/model/lingua.model';

@Component({
  selector: 'jhi-lingua-detail',
  templateUrl: './lingua-detail.component.html'
})
export class LinguaDetailComponent implements OnInit {
  lingua: ILingua;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lingua }) => {
      this.lingua = lingua;
    });
  }

  previousState() {
    window.history.back();
  }
}

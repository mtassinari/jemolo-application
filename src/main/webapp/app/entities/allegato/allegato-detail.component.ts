import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAllegato } from 'app/shared/model/allegato.model';

@Component({
  selector: 'jhi-allegato-detail',
  templateUrl: './allegato-detail.component.html'
})
export class AllegatoDetailComponent implements OnInit {
  allegato: IAllegato;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allegato }) => {
      this.allegato = allegato;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}

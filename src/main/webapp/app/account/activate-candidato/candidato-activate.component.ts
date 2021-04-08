import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';

import { LoginCandidatoModalService } from 'app/core';
import { CandidatoActivateService } from './candidato-activate.service';

@Component({
  selector: 'jhi-candidato-activate',
  templateUrl: './candidato-activate.component.html'
})
export class CandidatoActivateComponent implements OnInit {
    error: string;
    success: string;
    modalRef: NgbModalRef;

  constructor(private activateService: CandidatoActivateService, private loginModalService: LoginCandidatoModalService, private route: ActivatedRoute) { }

  ngOnInit() {
      this.route.queryParams.subscribe(params => {
          this.activateService.get(params['key']).subscribe(
            () => {
              this.error = null;
              this.success = 'OK';
            },
            () => {
              this.success = null;
              this.error = 'ERROR';
            }
          );
        });
  }

  login() {
      this.modalRef = this.loginModalService.open();
    }

}

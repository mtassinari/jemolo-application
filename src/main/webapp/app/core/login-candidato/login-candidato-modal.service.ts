import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { LoginModalCandidatoComponent } from 'app/shared/login-candidato/login-candidato.component';

@Injectable({
  providedIn: 'root'
})
export class LoginCandidatoModalService {
    private isOpen = false;
    constructor(private modalService: NgbModal) {}

    open(): NgbModalRef {
      if (this.isOpen) {
        return;
      }
      this.isOpen = true;
      const modalRef = this.modalService.open(LoginModalCandidatoComponent);
      modalRef.result.then(
        result => {
          this.isOpen = false;
        },
        reason => {
          this.isOpen = false;
        }
      );
      return modalRef;
    }
}

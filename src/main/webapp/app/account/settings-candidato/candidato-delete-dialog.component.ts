import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { User, UserService, CandidatoAccount } from 'app/core';
import { CandidatoService } from 'app/entities/candidato';
import { LoginCandidatoService } from 'app/core';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './candidato-delete-dialog.component.html'
})
export class CandidatoDeleteDialogComponent {
  user: User;
  currentAccount: CandidatoAccount;
  constructor(private router: Router, private loginService: LoginCandidatoService, private candidatoService: CandidatoService, private userService: UserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) { }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(codiceFiscale) {
    this.candidatoService.deleteAccount(codiceFiscale).subscribe(response => {
      this.activeModal.close(true);
      this.loginService.logout();
      this.eventManager.broadcast({ name: 'userAccount.deleted', content: 'Deleted account' });
    });
  }
}

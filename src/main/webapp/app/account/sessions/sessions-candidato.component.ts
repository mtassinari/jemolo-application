import { Component, OnInit } from '@angular/core';

import { AccountCandidatoService } from 'app/core';
import { Session } from './session.model';
import { SessionsCandidatoService } from './sessions-candidato.service';

@Component({
  selector: 'jhi-sessions',
  templateUrl: './sessions.component.html'
})
export class SessionsCandidatoComponent implements OnInit {
  account: any;
  error: string;
  success: string;
  sessions: Session[];

  constructor(private sessionsService: SessionsCandidatoService, private accountService: AccountCandidatoService) {}

  ngOnInit() {
    this.sessionsService.findAll().subscribe(sessions => (this.sessions = sessions));

    this.accountService.identity().then(account => {
      this.account = account;
    });
  }

  invalidate(series) {
    this.sessionsService.delete(encodeURIComponent(series)).subscribe(response => {
      if (response.status === 200) {
        this.error = null;
        this.success = 'OK';
        this.sessionsService.findAll().subscribe(sessions => (this.sessions = sessions));
      } else {
        this.success = null;
        this.error = 'ERROR';
      }
    });
  }
}

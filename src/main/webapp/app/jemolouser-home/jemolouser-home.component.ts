import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-home',
  templateUrl: './jemolouser-home.component.html',
  styleUrls: ['jemolouser-home.scss']
})
export class JemolouserHomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  modalRef: NgbModalRef;
  authSubscription?: Subscription;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  /*ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }*/

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}

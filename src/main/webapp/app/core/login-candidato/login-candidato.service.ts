import { Injectable } from '@angular/core';
import { AccountCandidatoService } from 'app/core/auth-candidato/account-candidato.service';
import { AuthCandidatoServerProvider } from 'app/core/auth-candidato/auth-session-candidato.service';

@Injectable({
  providedIn: 'root'
})
export class LoginCandidatoService {
    constructor(private accountService: AccountCandidatoService, private authServerProvider: AuthCandidatoServerProvider) {}

    login(credentials, callback?) {
      const cb = callback || function() {};

      return new Promise((resolve, reject) => {
        this.authServerProvider.login(credentials).subscribe(
          data => {
            this.accountService.identity(true).then(account => {
              resolve(data);
            });
            return cb();
          },
          err => {
            this.logout();
            reject(err);
            return cb(err);
          }
        );
      });
    }

    logout() {
      this.authServerProvider.logout().subscribe(null, null, () => this.accountService.authenticate(null));
    }
}

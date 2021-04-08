import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthCandidatoServerProvider {
    constructor(private http: HttpClient) {}

    login(credentials): Observable<any> {
      const data =
        `username=${encodeURIComponent(credentials.username)}` +
        `&password=${encodeURIComponent(credentials.password)}` +
        `&remember-me=${credentials.rememberMe}` +
        `&submit=Login`;
      const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

      return this.http.post(SERVER_API_URL + 'api/candidato/authentication', data, { headers });
    }

    logout(): Observable<any> {
      // logout from the server
      return this.http.post(SERVER_API_URL + 'api/candidato/logout', {}, { observe: 'response' }).pipe(
        map((response: HttpResponse<any>) => {
          // to get a new csrf token call the api
          this.http.get(SERVER_API_URL + 'api/candidato/account').subscribe(() => {}, () => {});
          return response;
        })
      );
    }
}

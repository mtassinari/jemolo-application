import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';

type EntityResponseType = HttpResponse<IDichiarazioniObligatorie>;
type EntityArrayResponseType = HttpResponse<IDichiarazioniObligatorie[]>;

@Injectable({ providedIn: 'root' })
export class DichiarazioniObligatorieService {
  public resourceUrl = SERVER_API_URL + 'api/dichiarazioni-obligatories';

  constructor(protected http: HttpClient) {}

  create(dichiarazioniObligatorie: IDichiarazioniObligatorie): Observable<EntityResponseType> {
    return this.http.post<IDichiarazioniObligatorie>(this.resourceUrl, dichiarazioniObligatorie, { observe: 'response' });
  }

  update(dichiarazioniObligatorie: IDichiarazioniObligatorie): Observable<EntityResponseType> {
    return this.http.put<IDichiarazioniObligatorie>(this.resourceUrl, dichiarazioniObligatorie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDichiarazioniObligatorie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDichiarazioniObligatorie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

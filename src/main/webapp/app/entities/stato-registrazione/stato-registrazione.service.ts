import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';

type EntityResponseType = HttpResponse<IStatoRegistrazione>;
type EntityArrayResponseType = HttpResponse<IStatoRegistrazione[]>;

@Injectable({ providedIn: 'root' })
export class StatoRegistrazioneService {
  public resourceUrl = SERVER_API_URL + 'api/stato-registraziones';

  constructor(protected http: HttpClient) {}

  create(statoRegistrazione: IStatoRegistrazione): Observable<EntityResponseType> {
    return this.http.post<IStatoRegistrazione>(this.resourceUrl, statoRegistrazione, { observe: 'response' });
  }

  update(statoRegistrazione: IStatoRegistrazione): Observable<EntityResponseType> {
    return this.http.put<IStatoRegistrazione>(this.resourceUrl, statoRegistrazione, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatoRegistrazione>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatoRegistrazione[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllegato } from 'app/shared/model/allegato.model';

type EntityResponseType = HttpResponse<IAllegato>;
type EntityArrayResponseType = HttpResponse<IAllegato[]>;

@Injectable({ providedIn: 'root' })
export class AllegatoService {
  public resourceUrl = SERVER_API_URL + 'api/allegatoes';

  constructor(protected http: HttpClient) {}

  create(allegato: IAllegato): Observable<EntityResponseType> {
    return this.http.post<IAllegato>(this.resourceUrl, allegato, { observe: 'response' });
  }

  update(allegato: IAllegato): Observable<EntityResponseType> {
    return this.http.put<IAllegato>(this.resourceUrl, allegato, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllegato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllegato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

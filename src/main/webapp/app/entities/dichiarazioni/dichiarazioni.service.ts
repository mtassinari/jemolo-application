import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';

type EntityResponseType = HttpResponse<IDichiarazioni>;
type EntityArrayResponseType = HttpResponse<IDichiarazioni[]>;

@Injectable({ providedIn: 'root' })
export class DichiarazioniService {
  public resourceUrl = SERVER_API_URL + 'api/v2/dichiarazionis';

  constructor(protected http: HttpClient) {}

  create(dichiarazioni: IDichiarazioni): Observable<EntityResponseType> {
    return this.http.post<IDichiarazioni>(this.resourceUrl, dichiarazioni, { observe: 'response' });
  }

  update(dichiarazioni: IDichiarazioni): Observable<EntityResponseType> {
    return this.http.put<IDichiarazioni>(this.resourceUrl, dichiarazioni, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDichiarazioni>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDichiarazioni[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

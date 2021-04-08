import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvvisiHome } from 'app/shared/model/avvisi-home.model';

type EntityResponseType = HttpResponse<IAvvisiHome>;
type EntityArrayResponseType = HttpResponse<IAvvisiHome[]>;

@Injectable({ providedIn: 'root' })
export class AvvisiHomeService {
  public resourceUrl = SERVER_API_URL + 'api/avvisi-homes';
  public resourceUrlV2 = SERVER_API_URL + 'api/v2/avvisi-homes';

  constructor(protected http: HttpClient) {}

  create(avvisiHome: IAvvisiHome): Observable<EntityResponseType> {
    return this.http.post<IAvvisiHome>(this.resourceUrl, avvisiHome, { observe: 'response' });
  }

  update(avvisiHome: IAvvisiHome): Observable<EntityResponseType> {
    return this.http.put<IAvvisiHome>(this.resourceUrl, avvisiHome, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvvisiHome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvvisiHome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryHome(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvvisiHome[]>(this.resourceUrlV2, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

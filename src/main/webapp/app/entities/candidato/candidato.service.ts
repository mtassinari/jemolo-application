import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICandidato } from 'app/shared/model/candidato.model';

type EntityResponseType = HttpResponse<ICandidato>;
type EntityArrayResponseType = HttpResponse<ICandidato[]>;

@Injectable({ providedIn: 'root' })
export class CandidatoService {
  public resourceUrl = SERVER_API_URL + 'api/candidati';
  public resourceUrlV2 = SERVER_API_URL + 'api/iscrittiall';
  public resourceUrlV3 = SERVER_API_URL + 'api/candidato/account';

  constructor(protected http: HttpClient) {}

  create(candidato: ICandidato): Observable<EntityResponseType> {
    return this.http.post<ICandidato>(this.resourceUrl, candidato, { observe: 'response' });
  }

  update(candidato: ICandidato): Observable<EntityResponseType> {
    return this.http.put<ICandidato>(this.resourceUrl, candidato, { observe: 'response' });
  }

  setActive(candidato: ICandidato): Observable<EntityResponseType> {
      return this.http.put<ICandidato>(this.resourceUrl + '/valid', candidato, { observe: 'response' });
   }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  downloadAllExcel(): Observable<HttpResponse<Blob>> {
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'application/vnd.ms-excel');
    return this.http.get(`${this.resourceUrlV2}/excel-download`, { headers, observe: 'response', responseType: 'blob' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryV2(req?: any): Observable<EntityArrayResponseType> {
      const options = createRequestOption(req);
      return this.http.get<ICandidato[]>(this.resourceUrlV2, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteAccount(codiceFiscale: string): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.resourceUrlV3}/${codiceFiscale}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

type EntityResponseType = HttpResponse<IAnagraficaCandidato>;
type EntityArrayResponseType = HttpResponse<IAnagraficaCandidato[]>;

@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoOwnService {
  public resourceUrl = SERVER_API_URL + 'api/own/anagrafica-candidatoes';

  constructor(protected http: HttpClient) {}

  create(anagraficaCandidato: IAnagraficaCandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anagraficaCandidato);
    return this.http
      .post<IAnagraficaCandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anagraficaCandidato: IAnagraficaCandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anagraficaCandidato);
    return this.http
      .put<IAnagraficaCandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnagraficaCandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnagraficaCandidato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anagraficaCandidato: IAnagraficaCandidato): IAnagraficaCandidato {
    const copy: IAnagraficaCandidato = Object.assign({}, anagraficaCandidato, {
      dataNascita:
        anagraficaCandidato.dataNascita != null && anagraficaCandidato.dataNascita.isValid()
          ? anagraficaCandidato.dataNascita.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascita = res.body.dataNascita != null ? moment(res.body.dataNascita) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((anagraficaCandidato: IAnagraficaCandidato) => {
        anagraficaCandidato.dataNascita = anagraficaCandidato.dataNascita != null ? moment(anagraficaCandidato.dataNascita) : null;
      });
    }
    return res;
  }
}

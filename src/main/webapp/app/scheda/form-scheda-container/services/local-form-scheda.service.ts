import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormSchedaCandidato } from 'app/shared/model/form-scheda.model';

type EntityResponseType = HttpResponse<IFormSchedaCandidato>;
type EntityArrayResponseType = HttpResponse<IFormSchedaCandidato[]>;

@Injectable()
export class LocalFormSchedaCandidatoService {
  public resourceUrl = SERVER_API_URL + 'api/anagrafica-candidatoes';

  constructor(protected http: HttpClient) {}

  create(formSchedaCandidato: IFormSchedaCandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formSchedaCandidato);
    return this.http
      .post<IFormSchedaCandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formSchedaCandidato: IFormSchedaCandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formSchedaCandidato);
    return this.http
      .put<IFormSchedaCandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormSchedaCandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormSchedaCandidato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anagraficaCandidato: IFormSchedaCandidato): IFormSchedaCandidato {
    const copy: IFormSchedaCandidato = Object.assign({}, anagraficaCandidato, {
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
      res.body.forEach((anagraficaCandidato: IFormSchedaCandidato) => {
        anagraficaCandidato.dataNascita = anagraficaCandidato.dataNascita != null ? moment(anagraficaCandidato.dataNascita) : null;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompetenza } from 'app/shared/model/competenza.model';

type EntityResponseType = HttpResponse<ICompetenza>;
type EntityArrayResponseType = HttpResponse<ICompetenza[]>;

@Injectable({ providedIn: 'root' })
export class FormCompetenzaService {
  public resourceUrl = SERVER_API_URL + 'api/competenzas';

  constructor(protected http: HttpClient) {}

  create(competenza: ICompetenza): Observable<EntityResponseType> {
    return this.http.post<ICompetenza>(this.resourceUrl, competenza, { observe: 'response' });
  }

  update(competenza: ICompetenza): Observable<EntityResponseType> {
    return this.http.put<ICompetenza>(this.resourceUrl, competenza, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetenza>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findCompetenzaCandidato(idAmbito: number, idCandidato: number): Observable<EntityResponseType> {
      return this.http.get<ICompetenza>(`${this.resourceUrl}/${idAmbito}/${idCandidato}/`, { observe: 'response' });
    }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetenza[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  query2(req?: any): Observable<EntityResponseType> {
      const options = createRequestOption(req);
      return this.http.get<ICompetenza>(this.resourceUrl + '/candidato', { params: options, observe: 'response' });
    }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

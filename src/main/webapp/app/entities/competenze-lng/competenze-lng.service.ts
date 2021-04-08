import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';

type EntityResponseType = HttpResponse<ICompetenzeLng>;
type EntityArrayResponseType = HttpResponse<ICompetenzeLng[]>;

@Injectable({ providedIn: 'root' })
export class CompetenzeLngService {
  public resourceUrl = SERVER_API_URL + 'api/competenze-lngs';

  constructor(protected http: HttpClient) {}

  create(competenzeLng: ICompetenzeLng): Observable<EntityResponseType> {
    return this.http.post<ICompetenzeLng>(this.resourceUrl, competenzeLng, { observe: 'response' });
  }

  update(competenzeLng: ICompetenzeLng): Observable<EntityResponseType> {
    return this.http.put<ICompetenzeLng>(this.resourceUrl, competenzeLng, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetenzeLng>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetenzeLng[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITitoloStudio } from 'app/shared/model/titolo-studio.model';

type EntityResponseType = HttpResponse<ITitoloStudio>;
type EntityArrayResponseType = HttpResponse<ITitoloStudio[]>;

@Injectable({ providedIn: 'root' })
export class TitoloStudioService {
  public resourceUrl = SERVER_API_URL + 'api/titolo-studios';

  constructor(protected http: HttpClient) {}

  create(titoloStudio: ITitoloStudio): Observable<EntityResponseType> {
    return this.http.post<ITitoloStudio>(this.resourceUrl, titoloStudio, { observe: 'response' });
  }

  update(titoloStudio: ITitoloStudio): Observable<EntityResponseType> {
    return this.http.put<ITitoloStudio>(this.resourceUrl, titoloStudio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITitoloStudio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITitoloStudio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

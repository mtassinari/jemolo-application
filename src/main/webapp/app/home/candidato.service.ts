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
  public resourceUrl = SERVER_API_URL + 'api/public/iscrittiall';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

}

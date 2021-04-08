import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurriculum } from 'app/shared/model/curriculum.model';

type EntityResponseType = HttpResponse<ICurriculum>;
type EntityArrayResponseType = HttpResponse<ICurriculum[]>;

@Injectable({ providedIn: 'root' })
export class CurriculumService {
  public resourceUrl = SERVER_API_URL + 'api/curricula';
  public resourceUrl2 = SERVER_API_URL + 'api/own/curricula';

  constructor(protected http: HttpClient) {}

  download(id: number): Observable<Blob> {
      return this.http.get(`${this.resourceUrl2}/${id}/$content`, { responseType: 'blob' });
  }

  userdownload(id: number): Observable<Blob> {
      return this.http.get(`${this.resourceUrl}/${id}/$content`, { responseType: 'blob' });
  }

  multipledownload(selectedCv: ICurriculum[]): Observable<HttpResponse<Blob>> {
      let headers = new HttpHeaders();
      headers = headers.append('Accept', 'application/zip');
      return this.http.post(`${this.resourceUrl}/allselected2`, selectedCv, { headers, observe: 'response', responseType: 'blob' });
  }

  create(curriculum: ICurriculum): Observable<EntityResponseType> {
    return this.http.post<ICurriculum>(this.resourceUrl, curriculum, { observe: 'response' });
  }

  update(curriculum: ICurriculum): Observable<EntityResponseType> {
    return this.http.put<ICurriculum>(this.resourceUrl, curriculum, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurriculum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurriculum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

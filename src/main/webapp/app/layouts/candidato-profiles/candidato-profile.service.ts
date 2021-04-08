import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { CandidatoProfileInfo } from './candidato-profile-info.model';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class CandidatoProfileService {
  private infoUrl = SERVER_API_URL + 'management/info';
  private profileInfo: Promise<CandidatoProfileInfo>;

  constructor(private http: HttpClient) {}

  getProfileInfo(): Promise<CandidatoProfileInfo> {
    if (!this.profileInfo) {
      this.profileInfo = this.http
        .get<CandidatoProfileInfo>(this.infoUrl, { observe: 'response' })
        .pipe(
          map((res: HttpResponse<CandidatoProfileInfo>) => {
            const data = res.body;
            const pi = new CandidatoProfileInfo();
            pi.activeProfiles = data['activeProfiles'];
            const displayRibbonOnProfiles = data['display-ribbon-on-profiles'].split(',');
            if (pi.activeProfiles) {
              const ribbonProfiles = displayRibbonOnProfiles.filter(profile => pi.activeProfiles.includes(profile));
              if (ribbonProfiles.length !== 0) {
                pi.ribbonEnv = ribbonProfiles[0];
              }
              pi.inProduction = pi.activeProfiles.includes('prod');
              pi.swaggerEnabled = pi.activeProfiles.includes('swagger');
            }
            return pi;
          })
        )
        .toPromise();
    }
    return this.profileInfo;
  }
}

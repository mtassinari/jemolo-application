import { Component, OnInit } from '@angular/core';
import { CandidatoProfileService } from './candidato-profile.service';
import { CandidatoProfileInfo } from './candidato-profile-info.model';

@Component({
  selector: 'jhi-page-ribbon',
  template: `
    <div class="ribbon" *ngIf="ribbonEnv">
      <a href="" jhiTranslate="global.ribbon.{{ ribbonEnv }}">{{ ribbonEnv }}</a>
    </div>
  `,
  styleUrls: ['candidato-page-ribbon.scss']
})
export class CandidatoPageRibbonComponent implements OnInit {
  profileInfo: CandidatoProfileInfo;
  ribbonEnv: string;

  constructor(private profileService: CandidatoProfileService) {}

  ngOnInit() {
    this.profileService.getProfileInfo().then(profileInfo => {
      this.profileInfo = profileInfo;
      this.ribbonEnv = profileInfo.ribbonEnv;
    });
  }
}

import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';

export interface ILingua {
  id?: number;
  lingua?: string;
  linguaUe?: boolean;
  competenzeLinguistiches?: ICompetenzeLng[];
}

export class Lingua implements ILingua {
  constructor(public id?: number, public lingua?: string, public linguaUe?: boolean, public competenzeLinguistiches?: ICompetenzeLng[]) {
    this.linguaUe = this.linguaUe || false;
  }
}

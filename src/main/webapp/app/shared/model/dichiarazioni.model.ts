import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';

export interface IDichiarazioni {
  id?: number;
  descrizione?: string;
  dichiarazioniObligatories?: IDichiarazioniObligatorie[];
}

export class Dichiarazioni implements IDichiarazioni {
  constructor(public id?: number, public descrizione?: string, public dichiarazioniObligatories?: IDichiarazioniObligatorie[]) {}
}

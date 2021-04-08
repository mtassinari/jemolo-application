import { ILingua } from 'app/shared/model/lingua.model';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

export interface ICompetenzeLng {
  id?: number;
  livello?: number;
  linguaId?: number;
  lingua?: ILingua;
  anagraficaId?: number;
  anagrafica?: IAnagraficaCandidato;
}

export interface ILivello {
    value: number;
    viewValue: string;
}

export class CompetenzeLng implements ICompetenzeLng {
  constructor(public id?: number, public livello?: number, public linguaId?: number, public lingua?: ILingua, public anagraficaId?: number, public anagrafica?: IAnagraficaCandidato) {}
}

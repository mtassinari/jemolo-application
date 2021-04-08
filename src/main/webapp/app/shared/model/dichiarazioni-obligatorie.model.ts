import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';

export interface IDichiarazioniObligatorie {
  id?: number;
  stato?: boolean;
  dichiarazione?: string;
  anagraficaId?: number;
  anagrafica?: IAnagraficaCandidato;
  dichiarazioniId?: number;
  dichiarazioni?: IDichiarazioni;
}

export class DichiarazioniObligatorie implements IDichiarazioniObligatorie {
  constructor(
    public id?: number,
    public stato?: boolean,
    public dichiarazione?: string,
    public anagraficaId?: number,
    public anagrafica?: IAnagraficaCandidato,
    public dichiarazioniId?: number,
    public dichiarazioni?: IDichiarazioni
  ) {
    this.stato = this.stato || false;
  }
}

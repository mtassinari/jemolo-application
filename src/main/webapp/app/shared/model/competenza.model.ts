import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';

export interface ICompetenza {
  id?: number;
  descrizione?: string;
  anniEsperianza?: number;
  note?: string;
  anagraficaId?: number;
  anagrafica?: IAnagraficaCandidato;
  ambitoCompId?: number;
  ambitoComp?: IAmbitoCompetenza;
}

export class Competenza implements ICompetenza {
  constructor(
    public id?: number,
    public descrizione?: string,
    public anniEsperianza?: number,
    public note?: string,
    public anagraficaId?: number,
    public anagrafica?: IAnagraficaCandidato,
    public ambitoCompId?: number,
    public ambitoComp?: IAmbitoCompetenza) {}
}

import { ICompetenza } from 'app/shared/model/competenza.model';
// import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';

export interface IAmbitoCompetenza {
  id?: number;
  descrizione?: string;
  tipo?: string;
  stato?: boolean;
  competenzas?: ICompetenza[];
  sottoambitos?: IAmbitoCompetenza[];
  ambitoId?: number;
  ambito?: IAmbitoCompetenza;
}

export class AmbitoCompetenza implements IAmbitoCompetenza {
  constructor(
    public id?: number,
    public descrizione?: string,
    public tipo?: string,
    public stato?: boolean,
    public competenzas?: ICompetenza[],
    public sottoambitos?: IAmbitoCompetenza[],
    public ambitoId?: number,
    public ambito?: IAmbitoCompetenza
  ) {}
}
export interface ITipo {
    value: string;
    viewValue: string;
}

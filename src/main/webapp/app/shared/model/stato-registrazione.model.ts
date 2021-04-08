import { ICandidato } from 'app/shared/model/candidato.model';

export interface IStatoRegistrazione {
  id?: number;
  descrizione?: string;
  candidatoes?: ICandidato[];
}

export class StatoRegistrazione implements IStatoRegistrazione {
  constructor(public id?: number, public descrizione?: string, public candidatoes?: ICandidato[]) {}
}

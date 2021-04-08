import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';

export interface ICandidato {
  id?: number;
  login?: string;
  nominativo?: string;
  nome?: string;
  cognome?: string;
  codiceFiscale?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  anagraficaCandidato?: IAnagraficaCandidato;
  anagraficaCandidatoId?: number;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  statoId?: number;
  statoRegistrazioneId?: number;
  stato?: IStatoRegistrazione;
}

export class Candidato implements ICandidato {
  constructor(
    public id?: number,
    public login?: string,
    public nominativo?: string,
    public nome?: string,
    public cognome?: string,
    public codiceFiscale?: string,
    public email?: string,
    public anagraficaCandidato?: IAnagraficaCandidato,
    public anagraficaCandidatoId?: number,
    public statoId?: number,
    public statoRegistrazioneId?: number,
    public stato?: IStatoRegistrazione
  ) {}
}

import { Moment } from 'moment';
import { ICandidato } from 'app/shared/model/candidato.model';
import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { ITitoloStudio } from 'app/shared/model/titolo-studio.model';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { ICompetenza } from 'app/shared/model/competenza.model';
import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';

export interface IAnagraficaCandidato {
    id?: number;
    nome?: string;
    cognome?: string;
    luogoNascita?: string;
    dataNascita?: Moment;
    professione?: string;
    partitaIva?: string;
    numeroTelefonoFisso?: string;
    numeroTelefonoCellulare?: string;
    indirizzoPec?: string;
    indirizzoResidenza?: string;
    capResidenza?: string;
    comuneResidenza?: string;
    provinciaResidenza?: string;
    note?: string;
    candidatoId?: number;
    candidato?: ICandidato;
    competenzeLngs?: ICompetenzeLng[];
    titoloStudios?: ITitoloStudio[];
    specializzazioneMaster?: boolean;
    iscrizioneAlbo?: boolean;
    operatoreCampo?: boolean;
    esperienzaBiennale?: boolean;
    curricula?: ICurriculum[];
    competenzas?: ICompetenza[];
    dichiarazionis?: IDichiarazioniObligatorie[];
}

export class AnagraficaCandidato implements IAnagraficaCandidato {
  constructor(
      public id?: number,
      public nome?: string,
      public cognome?: string,
      public luogoNascita?: string,
      public dataNascita?: Moment,
      public professione?: string,
      public partitaIva?: string,
      public numeroTelefonoFisso?: string,
      public numeroTelefonoCellulare?: string,
      public indirizzoPec?: string,
      public indirizzoResidenza?: string,
      public capResidenza?: string,
      public comuneResidenza?: string,
      public provinciaResidenza?: string,
      public note?: string,
      public candidatoId?: number,
      public candidato?: ICandidato,
      public competenzeLngs?: ICompetenzeLng[],
      public titoloStudios?: ITitoloStudio[],
      public specializzazioneMaster?: boolean,
      public iscrizioneAlbo?: boolean,
      public operatoreCampo?: boolean,
      public esperienzaBiennale?: boolean,
      public curricula?: ICurriculum[],
      public competenzas?: ICompetenza[],
      public dichiarazionis?: IDichiarazioniObligatorie[]
  ) {}
}

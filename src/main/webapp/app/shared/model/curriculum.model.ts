import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { IAllegato } from 'app/shared/model/allegato.model';
export interface ICurriculum {
  id?: number;
  cv?: string;
  size?: number;
  note?: string;
  anagraficaId?: number;
  anagrafica?: IAnagraficaCandidato;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  urlAllegato?: string;
  mimeType?: string;
  allegatoId?: number;
  allegato?: IAllegato;
}

export class Curriculum implements ICurriculum {
    constructor(
        public id?: number,
        public cv?: string,
        public size?: number,
        public note?: string,
        public anagraficaId?: number,
        public anagrafica?: IAnagraficaCandidato,
        public createdBy?: string,
        public createdDate?: Date,
        public lastModifiedBy?: string,
        public lastModifiedDate?: Date,
        public urlAllegato?: string,
        public mimeType?: string,
        public allegatoId?: number,
        public allegato?: IAllegato
    ) {}
}

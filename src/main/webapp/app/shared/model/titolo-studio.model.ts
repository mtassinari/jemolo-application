export interface ITitoloStudio {
  id?: number;
  tipologia?: string;
  descrizione?: string;
  conseguimento?: string;
  anno?: number;
  sanno?: string;
  voto?: string;
  anagraficaId?: number;
}

export interface ITipologia {
    value: string;
    viewValue: string;
}

export interface IAnnoConseguimento {
    value: string;
    viewValue: string;
}

export class TitoloStudio implements ITitoloStudio {
  constructor(
    public id?: number,
    public tipologia?: string,
    public descrizione?: string,
    public conseguimento?: string,
    public anno?: number,
    public sanno?: string,
    public voto?: string,
    public anagraficaId?: number
  ) {}
}

export interface IComune {
  id?: number;
  nome?: string;
  nomeProvincia?: string;
  siglaProvincia?: string;
  comunes?: IComune[];
  provincia?: IComune;
}

export class Comune implements IComune {
  constructor(
    public id?: number,
    public nome?: string,
    public nomeProvincia?: string,
    public siglaProvincia?: string,
    public comunes?: IComune[],
    public provincia?: IComune
  ) {}
}

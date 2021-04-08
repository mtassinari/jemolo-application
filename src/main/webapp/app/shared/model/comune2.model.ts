export interface IComune {
  id?: number;
  idProvincia?: number;
  siglaProvincia?: string;
  nome?: string;
}

export class Comune implements IComune {
  constructor(
    public id?: number,
    public idProvincia?: number,
    public siglaProvincia?: string,
    public nome?: string
  ) {}
}

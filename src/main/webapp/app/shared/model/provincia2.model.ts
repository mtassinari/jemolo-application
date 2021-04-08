import { IComune } from './comune2.model';

export interface IProvincia {
  id?: number;
  sigla?: string;
  nome?: string;
  comuni?: IComune[];
}

export class Provincia implements IProvincia {
  constructor(
    public id?: number,
    public sigla?: string,
    public nome?: string,
    public comuni?: IComune[]
  ) {}
}

export interface IProvincia {
  id?: number;
  sigla?: string;
  nome?: string;
}

export class Provincia implements IProvincia {
  constructor(public id?: number, public sigla?: string, public nome?: string) {}
}

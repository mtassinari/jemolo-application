export class CandidatoAccount {
  constructor(
    public id: number,
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public nome: string,
    public nominativo: string,
    public langKey: string,
    public cognome: string,
    public codiceFiscale: string,
    public login: string,
    public anagraficaCandidatoId: number,
    public imageUrl: string
  ) {}
}

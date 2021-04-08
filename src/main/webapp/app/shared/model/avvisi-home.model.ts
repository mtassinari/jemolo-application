export interface IAvvisiHome {
  id?: number;
  titolo?: string;
  avvisoHome?: string;
  visibile?: boolean;
}

export class AvvisiHome implements IAvvisiHome {
  constructor(public id?: number, public titolo?: string, public avvisoHome?: string, public visibile?: boolean) {
    this.visibile = this.visibile || false;
  }
}

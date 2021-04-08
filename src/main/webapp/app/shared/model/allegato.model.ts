export interface IAllegato {
  id?: number;
  dataContentType?: string;
  data?: any;
  curriculumId?: number;
}

export class Allegato implements IAllegato {
  constructor(public id?: number, public dataContentType?: string, public data?: any, public curriculumId?: number) {}
}

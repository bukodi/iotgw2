export interface IOrgUnit {
  id?: number;
  name?: string;
  description?: string | null;
  readAuthPattern?: string | null;
  writeAuthPattern?: string | null;
  subUnits?: IOrgUnit[] | null;
  parent?: IOrgUnit | null;
}

export class OrgUnit implements IOrgUnit {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public readAuthPattern?: string | null,
    public writeAuthPattern?: string | null,
    public subUnits?: IOrgUnit[] | null,
    public parent?: IOrgUnit | null
  ) {}
}

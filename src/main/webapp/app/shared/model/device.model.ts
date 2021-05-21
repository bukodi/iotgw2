import { IKeyPair } from '@/shared/model/key-pair.model';
import { IDeviceType } from '@/shared/model/device-type.model';
import { IOrgUnit } from '@/shared/model/org-unit.model';

export interface IDevice {
  id?: number;
  visualId?: string;
  description?: string | null;
  enrollmentCode?: string | null;
  enrollmentTime?: Date | null;
  stateFieldValue01?: string | null;
  stateFieldValue02?: string | null;
  stateFieldValue03?: string | null;
  stateFieldValue04?: string | null;
  lastError?: string | null;
  readAuthPattern?: string | null;
  writeAuthPattern?: string | null;
  deviceSignKey?: IKeyPair | null;
  deviceEncKey?: IKeyPair | null;
  serverSignKey?: IKeyPair | null;
  serverEncKey?: IKeyPair | null;
  nextServerSignKey?: IKeyPair | null;
  nextServerEncKey?: IKeyPair | null;
  type?: IDeviceType;
  orgUnit?: IOrgUnit;
}

export class Device implements IDevice {
  constructor(
    public id?: number,
    public visualId?: string,
    public description?: string | null,
    public enrollmentCode?: string | null,
    public enrollmentTime?: Date | null,
    public stateFieldValue01?: string | null,
    public stateFieldValue02?: string | null,
    public stateFieldValue03?: string | null,
    public stateFieldValue04?: string | null,
    public lastError?: string | null,
    public readAuthPattern?: string | null,
    public writeAuthPattern?: string | null,
    public deviceSignKey?: IKeyPair | null,
    public deviceEncKey?: IKeyPair | null,
    public serverSignKey?: IKeyPair | null,
    public serverEncKey?: IKeyPair | null,
    public nextServerSignKey?: IKeyPair | null,
    public nextServerEncKey?: IKeyPair | null,
    public type?: IDeviceType,
    public orgUnit?: IOrgUnit
  ) {}
}

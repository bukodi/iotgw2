import { IProcessor } from '@/shared/model/processor.model';
import { IDeviceType } from '@/shared/model/device-type.model';

export interface IMessageType {
  id?: number;
  typeCode?: number;
  devToSrv?: boolean;
  name?: string;
  description?: string | null;
  timeout?: string | null;
  retentionTime?: string | null;
  indexFieldName01?: string | null;
  indexFieldName02?: string | null;
  indexFieldName03?: string | null;
  indexFieldName04?: string | null;
  readAuthPattern?: string | null;
  writeAuthPattern?: string | null;
  messageProcessor?: IProcessor | null;
  timeoutProcessor?: IProcessor | null;
  deviceTypes?: IDeviceType[] | null;
}

export class MessageType implements IMessageType {
  constructor(
    public id?: number,
    public typeCode?: number,
    public devToSrv?: boolean,
    public name?: string,
    public description?: string | null,
    public timeout?: string | null,
    public retentionTime?: string | null,
    public indexFieldName01?: string | null,
    public indexFieldName02?: string | null,
    public indexFieldName03?: string | null,
    public indexFieldName04?: string | null,
    public readAuthPattern?: string | null,
    public writeAuthPattern?: string | null,
    public messageProcessor?: IProcessor | null,
    public timeoutProcessor?: IProcessor | null,
    public deviceTypes?: IDeviceType[] | null
  ) {
    this.devToSrv = this.devToSrv ?? false;
  }
}

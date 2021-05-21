import { IProcessor } from '@/shared/model/processor.model';
import { IMessageType } from '@/shared/model/message-type.model';

export interface IDeviceType {
  id?: number;
  name?: string;
  description?: string | null;
  stateFieldName01?: string | null;
  stateFieldName02?: string | null;
  stateFieldName03?: string | null;
  stateFieldName04?: string | null;
  readAuthPattern?: string | null;
  writeAuthPattern?: string | null;
  enrollProcessor?: IProcessor | null;
  messageTypes?: IMessageType[] | null;
}

export class DeviceType implements IDeviceType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public stateFieldName01?: string | null,
    public stateFieldName02?: string | null,
    public stateFieldName03?: string | null,
    public stateFieldName04?: string | null,
    public readAuthPattern?: string | null,
    public writeAuthPattern?: string | null,
    public enrollProcessor?: IProcessor | null,
    public messageTypes?: IMessageType[] | null
  ) {}
}

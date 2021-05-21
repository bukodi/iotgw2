import { IMessageType } from '@/shared/model/message-type.model';
import { IDevice } from '@/shared/model/device.model';

export interface IMessage {
  id?: number;
  serverTime?: Date;
  deviceTime?: Date | null;
  rawMessageSHA256?: string;
  rawMessageContentType?: string | null;
  rawMessage?: string | null;
  decryptedPayload?: string | null;
  devToSrv?: boolean;
  indexFieldValue01?: string | null;
  indexFieldValue02?: string | null;
  indexFieldValue03?: string | null;
  indexFieldValue04?: string | null;
  processingError?: string | null;
  type?: IMessageType;
  device?: IDevice;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public serverTime?: Date,
    public deviceTime?: Date | null,
    public rawMessageSHA256?: string,
    public rawMessageContentType?: string | null,
    public rawMessage?: string | null,
    public decryptedPayload?: string | null,
    public devToSrv?: boolean,
    public indexFieldValue01?: string | null,
    public indexFieldValue02?: string | null,
    public indexFieldValue03?: string | null,
    public indexFieldValue04?: string | null,
    public processingError?: string | null,
    public type?: IMessageType,
    public device?: IDevice
  ) {
    this.devToSrv = this.devToSrv ?? false;
  }
}

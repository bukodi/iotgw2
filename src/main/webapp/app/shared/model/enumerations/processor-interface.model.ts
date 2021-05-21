export enum ProcessorInterface {
  BeforeSendToDevice = 'BeforeSendToDevice',

  AfterRecivedFromDevice = 'AfterRecivedFromDevice',

  OnMessageTimeout = 'OnMessageTimeout',

  OnMessageSendError = 'OnMessageSendError',

  OnExternalAPICall = 'OnExternalAPICall',
}

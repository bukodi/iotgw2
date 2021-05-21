package hu.noreg.iotgw2.domain.enumeration;

/**
 * The ProcessorInterface enumeration.
 */
public enum ProcessorInterface {
    BeforeSendToDevice,
    AfterRecivedFromDevice,
    OnMessageTimeout,
    OnMessageSendError,
    OnExternalAPICall,
}

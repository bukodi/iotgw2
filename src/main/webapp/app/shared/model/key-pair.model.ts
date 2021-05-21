import { PkAlgorithm } from '@/shared/model/enumerations/pk-algorithm.model';
export interface IKeyPair {
  id?: number;
  keyId?: string;
  algorithm?: PkAlgorithm;
  publicKeyContentType?: string;
  publicKey?: string;
  privateKeyDescriptor?: string | null;
  attestationDataContentType?: string | null;
  attestationData?: string | null;
  associatedCertContentType?: string | null;
  associatedCert?: string | null;
  certificateRequestContentType?: string | null;
  certificateRequest?: string | null;
  certSubjectDN?: string | null;
  certIssuerDN?: string | null;
  certSerial?: string | null;
  certNotBefore?: Date | null;
  certNotAfter?: Date | null;
  certRevoked?: Date | null;
}

export class KeyPair implements IKeyPair {
  constructor(
    public id?: number,
    public keyId?: string,
    public algorithm?: PkAlgorithm,
    public publicKeyContentType?: string,
    public publicKey?: string,
    public privateKeyDescriptor?: string | null,
    public attestationDataContentType?: string | null,
    public attestationData?: string | null,
    public associatedCertContentType?: string | null,
    public associatedCert?: string | null,
    public certificateRequestContentType?: string | null,
    public certificateRequest?: string | null,
    public certSubjectDN?: string | null,
    public certIssuerDN?: string | null,
    public certSerial?: string | null,
    public certNotBefore?: Date | null,
    public certNotAfter?: Date | null,
    public certRevoked?: Date | null
  ) {}
}

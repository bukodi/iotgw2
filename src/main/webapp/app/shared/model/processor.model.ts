import { ProcessorInterface } from '@/shared/model/enumerations/processor-interface.model';
import { ImplemntationType } from '@/shared/model/enumerations/implemntation-type.model';
export interface IProcessor {
  id?: number;
  name?: string;
  example?: boolean | null;
  description?: string | null;
  processorIterface?: ProcessorInterface;
  implType?: ImplemntationType;
  param01?: string | null;
  param02?: string | null;
  source?: string | null;
  signerName?: string | null;
  signatureContentType?: string | null;
  signature?: string | null;
}

export class Processor implements IProcessor {
  constructor(
    public id?: number,
    public name?: string,
    public example?: boolean | null,
    public description?: string | null,
    public processorIterface?: ProcessorInterface,
    public implType?: ImplemntationType,
    public param01?: string | null,
    public param02?: string | null,
    public source?: string | null,
    public signerName?: string | null,
    public signatureContentType?: string | null,
    public signature?: string | null
  ) {
    this.example = this.example ?? false;
  }
}

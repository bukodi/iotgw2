/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import ProcessorService from '@/entities/processor/processor.service';
import { Processor } from '@/shared/model/processor.model';
import { ProcessorInterface } from '@/shared/model/enumerations/processor-interface.model';
import { ImplemntationType } from '@/shared/model/enumerations/implemntation-type.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Processor Service', () => {
    let service: ProcessorService;
    let elemDefault;

    beforeEach(() => {
      service = new ProcessorService();
      elemDefault = new Processor(
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        ProcessorInterface.BeforeSendToDevice,
        ImplemntationType.JavaCall,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Processor', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Processor', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Processor', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            example: true,
            description: 'BBBBBB',
            processorIterface: 'BBBBBB',
            implType: 'BBBBBB',
            param01: 'BBBBBB',
            param02: 'BBBBBB',
            source: 'BBBBBB',
            signerName: 'BBBBBB',
            signature: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Processor', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Processor', async () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            example: true,
            description: 'BBBBBB',
            implType: 'BBBBBB',
            source: 'BBBBBB',
          },
          new Processor()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Processor', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Processor', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            example: true,
            description: 'BBBBBB',
            processorIterface: 'BBBBBB',
            implType: 'BBBBBB',
            param01: 'BBBBBB',
            param02: 'BBBBBB',
            source: 'BBBBBB',
            signerName: 'BBBBBB',
            signature: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Processor', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Processor', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Processor', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});

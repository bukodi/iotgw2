/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import MessageTypeService from '@/entities/message-type/message-type.service';
import { MessageType } from '@/shared/model/message-type.model';

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
  describe('MessageType Service', () => {
    let service: MessageTypeService;
    let elemDefault;

    beforeEach(() => {
      service = new MessageTypeService();
      elemDefault = new MessageType(
        0,
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'PT1S',
        'PT1S',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a MessageType', async () => {
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

      it('should not create a MessageType', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MessageType', async () => {
        const returnedFromService = Object.assign(
          {
            typeCode: 1,
            devToSrv: true,
            name: 'BBBBBB',
            description: 'BBBBBB',
            timeout: 'PT2S',
            retentionTime: 'PT2S',
            indexFieldName01: 'BBBBBB',
            indexFieldName02: 'BBBBBB',
            indexFieldName03: 'BBBBBB',
            indexFieldName04: 'BBBBBB',
            readAuthPattern: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MessageType', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MessageType', async () => {
        const patchObject = Object.assign(
          {
            retentionTime: 'PT2S',
            indexFieldName01: 'BBBBBB',
            indexFieldName04: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          new MessageType()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MessageType', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MessageType', async () => {
        const returnedFromService = Object.assign(
          {
            typeCode: 1,
            devToSrv: true,
            name: 'BBBBBB',
            description: 'BBBBBB',
            timeout: 'PT2S',
            retentionTime: 'PT2S',
            indexFieldName01: 'BBBBBB',
            indexFieldName02: 'BBBBBB',
            indexFieldName03: 'BBBBBB',
            indexFieldName04: 'BBBBBB',
            readAuthPattern: 'BBBBBB',
            writeAuthPattern: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MessageType', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MessageType', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MessageType', async () => {
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

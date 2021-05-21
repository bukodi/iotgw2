/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import KeyPairService from '@/entities/key-pair/key-pair.service';
import { KeyPair } from '@/shared/model/key-pair.model';
import { PkAlgorithm } from '@/shared/model/enumerations/pk-algorithm.model';

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
  describe('KeyPair Service', () => {
    let service: KeyPairService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new KeyPairService();
      currentDate = new Date();
      elemDefault = new KeyPair(
        0,
        'AAAAAAA',
        PkAlgorithm.RSA1024,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            certNotBefore: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certNotAfter: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certRevoked: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
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

      it('should create a KeyPair', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            certNotBefore: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certNotAfter: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certRevoked: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            certNotBefore: currentDate,
            certNotAfter: currentDate,
            certRevoked: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a KeyPair', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a KeyPair', async () => {
        const returnedFromService = Object.assign(
          {
            keyId: 'BBBBBB',
            algorithm: 'BBBBBB',
            publicKey: 'BBBBBB',
            privateKeyDescriptor: 'BBBBBB',
            attestationData: 'BBBBBB',
            associatedCert: 'BBBBBB',
            certificateRequest: 'BBBBBB',
            certSubjectDN: 'BBBBBB',
            certIssuerDN: 'BBBBBB',
            certSerial: 'BBBBBB',
            certNotBefore: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certNotAfter: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certRevoked: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            certNotBefore: currentDate,
            certNotAfter: currentDate,
            certRevoked: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a KeyPair', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a KeyPair', async () => {
        const patchObject = Object.assign(
          {
            publicKey: 'BBBBBB',
            privateKeyDescriptor: 'BBBBBB',
            associatedCert: 'BBBBBB',
            certificateRequest: 'BBBBBB',
            certIssuerDN: 'BBBBBB',
            certSerial: 'BBBBBB',
            certNotAfter: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certRevoked: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new KeyPair()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            certNotBefore: currentDate,
            certNotAfter: currentDate,
            certRevoked: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a KeyPair', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of KeyPair', async () => {
        const returnedFromService = Object.assign(
          {
            keyId: 'BBBBBB',
            algorithm: 'BBBBBB',
            publicKey: 'BBBBBB',
            privateKeyDescriptor: 'BBBBBB',
            attestationData: 'BBBBBB',
            associatedCert: 'BBBBBB',
            certificateRequest: 'BBBBBB',
            certSubjectDN: 'BBBBBB',
            certIssuerDN: 'BBBBBB',
            certSerial: 'BBBBBB',
            certNotBefore: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certNotAfter: dayjs(currentDate).format(DATE_TIME_FORMAT),
            certRevoked: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            certNotBefore: currentDate,
            certNotAfter: currentDate,
            certRevoked: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of KeyPair', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a KeyPair', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a KeyPair', async () => {
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

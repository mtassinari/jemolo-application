/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';
import { IAnagraficaCandidato, AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

describe('Service Tests', () => {
  describe('AnagraficaCandidato Service', () => {
    let injector: TestBed;
    let service: AnagraficaCandidatoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnagraficaCandidato;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AnagraficaCandidatoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnagraficaCandidato(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        1
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataNascita: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AnagraficaCandidato', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascita: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascita: currentDate
          },
          returnedFromService
        );
        service
          .create(new AnagraficaCandidato(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AnagraficaCandidato', async () => {
        const returnedFromService = Object.assign(
          {
            luogoNascita: 'BBBBBB',
            dataNascita: currentDate.format(DATE_FORMAT),
            professione: 'BBBBBB',
            partitaIva: 'BBBBBB',
            datoreLavoro: 'BBBBBB',
            indirizzoDatoreLavoro: 'BBBBBB',
            numeroTelefonoFisso: 'BBBBBB',
            numeroTelefonoCellulare: 'BBBBBB',
            numeroTelefonoAltro: 'BBBBBB',
            indirizzoPec: 'BBBBBB',
            cittadinoUnioneEuropea: true,
            indirizzoResidenza: 'BBBBBB',
            capResidenza: 'BBBBBB',
            comuneResidenza: 'BBBBBB',
            provinciaResidenza: 'BBBBBB',
            titoloStudio: 'BBBBBB',
            titoloStudioTipologia: 'BBBBBB',
            titoloStudioLuogo: 'BBBBBB',
            titoloStudioAnno: 'BBBBBB',
            titoloStudioVoto: 'BBBBBB',
            specializzazioneUniversitaria: true,
            iscrizioneAlboProfessionale: true,
            operatoreAmbitoTecnicoProfessionale: true,
            esperienzaBiennale: true,
            cv: 'BBBBBB',
            note: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascita: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AnagraficaCandidato', async () => {
        const returnedFromService = Object.assign(
          {
            luogoNascita: 'BBBBBB',
            dataNascita: currentDate.format(DATE_FORMAT),
            professione: 'BBBBBB',
            partitaIva: 'BBBBBB',
            datoreLavoro: 'BBBBBB',
            indirizzoDatoreLavoro: 'BBBBBB',
            numeroTelefonoFisso: 'BBBBBB',
            numeroTelefonoCellulare: 'BBBBBB',
            numeroTelefonoAltro: 'BBBBBB',
            indirizzoPec: 'BBBBBB',
            cittadinoUnioneEuropea: true,
            indirizzoResidenza: 'BBBBBB',
            capResidenza: 'BBBBBB',
            comuneResidenza: 'BBBBBB',
            provinciaResidenza: 'BBBBBB',
            titoloStudio: 'BBBBBB',
            titoloStudioTipologia: 'BBBBBB',
            titoloStudioLuogo: 'BBBBBB',
            titoloStudioAnno: 'BBBBBB',
            titoloStudioVoto: 'BBBBBB',
            specializzazioneUniversitaria: true,
            iscrizioneAlboProfessionale: true,
            operatoreAmbitoTecnicoProfessionale: true,
            esperienzaBiennale: true,
            cv: 'BBBBBB',
            note: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascita: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AnagraficaCandidato', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

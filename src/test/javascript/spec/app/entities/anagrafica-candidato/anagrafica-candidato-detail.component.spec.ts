/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JemoloApplicationTestModule } from '../../../test.module';
import { AnagraficaCandidatoDetailComponent } from 'app/entities/anagrafica-candidato/anagrafica-candidato-detail.component';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

describe('Component Tests', () => {
  describe('AnagraficaCandidato Management Detail Component', () => {
    let comp: AnagraficaCandidatoDetailComponent;
    let fixture: ComponentFixture<AnagraficaCandidatoDetailComponent>;
    const route = ({ data: of({ anagraficaCandidato: new AnagraficaCandidato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [AnagraficaCandidatoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnagraficaCandidatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnagraficaCandidatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anagraficaCandidato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

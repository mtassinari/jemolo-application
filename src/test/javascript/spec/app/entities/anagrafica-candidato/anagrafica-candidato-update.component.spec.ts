/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JemoloApplicationTestModule } from '../../../test.module';
import { AnagraficaCandidatoUpdateComponent } from 'app/entities/anagrafica-candidato/anagrafica-candidato-update.component';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

describe('Component Tests', () => {
  describe('AnagraficaCandidato Management Update Component', () => {
    let comp: AnagraficaCandidatoUpdateComponent;
    let fixture: ComponentFixture<AnagraficaCandidatoUpdateComponent>;
    let service: AnagraficaCandidatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [AnagraficaCandidatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnagraficaCandidatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnagraficaCandidatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnagraficaCandidatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnagraficaCandidato(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      /*
      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnagraficaCandidato();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
      */

      it('Should call create service on save for new entity', fakeAsync(() => {
          // GIVEN
          const entity = new AnagraficaCandidato();
          const files = {} as FileList;
          spyOn(service, 'createV2').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.updateForm(entity);
          comp.files = files;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.createV2).toHaveBeenCalledWith(entity, files);
          expect(comp.isSaving).toEqual(false);
        }));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JemoloApplicationTestModule } from '../../../test.module';
import { CompetenzaUpdateComponent } from 'app/entities/competenza/competenza-update.component';
import { CompetenzaService } from 'app/entities/competenza/competenza.service';
import { Competenza } from 'app/shared/model/competenza.model';

describe('Component Tests', () => {
  describe('Competenza Management Update Component', () => {
    let comp: CompetenzaUpdateComponent;
    let fixture: ComponentFixture<CompetenzaUpdateComponent>;
    let service: CompetenzaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [CompetenzaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompetenzaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetenzaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetenzaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Competenza(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Competenza();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

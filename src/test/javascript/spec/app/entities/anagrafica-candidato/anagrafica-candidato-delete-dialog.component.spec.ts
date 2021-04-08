/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JemoloApplicationTestModule } from '../../../test.module';
import { AnagraficaCandidatoDeleteDialogComponent } from 'app/entities/anagrafica-candidato/anagrafica-candidato-delete-dialog.component';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';

describe('Component Tests', () => {
  describe('AnagraficaCandidato Management Delete Component', () => {
    let comp: AnagraficaCandidatoDeleteDialogComponent;
    let fixture: ComponentFixture<AnagraficaCandidatoDeleteDialogComponent>;
    let service: AnagraficaCandidatoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [AnagraficaCandidatoDeleteDialogComponent]
      })
        .overrideTemplate(AnagraficaCandidatoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnagraficaCandidatoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnagraficaCandidatoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

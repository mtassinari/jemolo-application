/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JemoloApplicationTestModule } from '../../../test.module';
import { CompetenzaDeleteDialogComponent } from 'app/entities/competenza/competenza-delete-dialog.component';
import { CompetenzaService } from 'app/entities/competenza/competenza.service';

describe('Component Tests', () => {
  describe('Competenza Management Delete Component', () => {
    let comp: CompetenzaDeleteDialogComponent;
    let fixture: ComponentFixture<CompetenzaDeleteDialogComponent>;
    let service: CompetenzaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [CompetenzaDeleteDialogComponent]
      })
        .overrideTemplate(CompetenzaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetenzaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetenzaService);
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

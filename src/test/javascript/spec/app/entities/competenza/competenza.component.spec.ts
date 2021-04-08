/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JemoloApplicationTestModule } from '../../../test.module';
import { CompetenzaComponent } from 'app/entities/competenza/competenza.component';
import { CompetenzaService } from 'app/entities/competenza/competenza.service';
import { Competenza } from 'app/shared/model/competenza.model';

describe('Component Tests', () => {
  describe('Competenza Management Component', () => {
    let comp: CompetenzaComponent;
    let fixture: ComponentFixture<CompetenzaComponent>;
    let service: CompetenzaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JemoloApplicationTestModule],
        declarations: [CompetenzaComponent],
        providers: []
      })
        .overrideTemplate(CompetenzaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetenzaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetenzaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Competenza(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competenzas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

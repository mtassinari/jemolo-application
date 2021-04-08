import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray, ValidatorFn } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
// import * as moment from 'moment';
import * as FileSaver from 'file-saver';
import { JhiEventManager } from 'ng-jhipster';
import { JhiAlertService } from 'ng-jhipster';
import { IFormSchedaCandidato, FormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { CandidatoService } from 'app/entities/candidato';
import { FormSchedaCandidatoService } from 'app/scheda/form-scheda/services/form-scheda.service';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { AccountCandidatoService } from 'app/core/';
import { CandidatoAccount } from 'app/core/user-candidato/account-candidato.model';
import { MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';
import {MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import * as _moment from 'moment';
import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { FormAmbitoCompetenzaService } from './services/form-ambito-competenza.service';
import { FormCompetenzaService } from './services/form-competenza.service';
import { LinguaService } from 'app/entities/lingua/lingua.service';
import { Competenza, ICompetenza } from 'app/shared/model/competenza.model';
import { ITipologia, ITitoloStudio, TitoloStudio } from 'app/shared/model/titolo-studio.model';
import { ILingua } from 'app/shared/model/lingua.model';
import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import { formatDate, DatePipe } from '@angular/common';

export const MY_FORMATS = {
    parse: {
        dateInput: 'LL',
    },
    display: {
        dateInput: 'LL',
        monthYearLabel: 'MMM YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'MMMM YYYY',
    },
};

@Component({
  selector: 'jhi-form-scheda',
  templateUrl: './form-scheda.component.html',
  styleUrls: ['./form-scheda.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]},
    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],
})
export class FormSchedaComponent implements OnInit {
    isSaving: boolean;
    account: CandidatoAccount;
    candidatoes: ICandidato[];
    ambitoCompetenzas: IAmbitoCompetenza[];
    competenzeList: FormArray = new FormArray([]);
    currentAccount: any;
    eventSubscriber: Subscription;
    dataNascitaDp: any;
    candidato: Candidato;
    formscheda: IFormSchedaCandidato;
    matcher = new MyErrorStateMatcher();
    editForm: FormGroup;
    titoliStudio: ITitoloStudio[] = [];
    linguas: ILingua[];
    public lingueList: FormArray;
    files: FileList;
    curricula: ICurriculum[];
    pipe = new DatePipe('it-IT'); // Use your own locale
    createForm() {
        this.editForm = this.fb.group({
            id: [],
            anagraficaCandidato: this.fb.group({
              nome: [null, [Validators.required]],
              cognome: [null, [Validators.required]],
              luogoNascita: [null, [Validators.required]],
              dataNascita: [null, [Validators.required]],
              codiceFiscale: [null, [Validators.required]],
              professione: [null, [Validators.required]],
              partitaIva: [null, [Validators.pattern('^[0-9]{11}$')]],
              numeroTelefonoFisso: [],
              numeroTelefonoCellulare: [],
              email: [null, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
              indirizzoPec: [null, [Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
              indirizzoResidenza: [null, [Validators.required]],
              capResidenza: [null, [Validators.required, Validators.pattern('^[0-9]{5}$')]],
              comuneResidenza: [null, [Validators.required]],
              provinciaResidenza: [null, [Validators.required]]
            }),
            titoliStudio: this.fb.group({
                id: [],
                tipologia: [null, [Validators.required]],
                descrizione: [null, [Validators.required]],
                conseguimento: [null, [Validators.required]],
                anno: [null, [Validators.required]],
                voto: [null, [Validators.required, Validators.required]]
            }),
            competenze: new FormArray([]),
            linguaita: [null, [Validators.requiredTrue]],
            // lingue: new FormArray([]),
            lingue: this.fb.array([this.createLinguaForm()], Validators.required),
            curricula: [],
            cv: this.fb.group({
                id: [],
                cv: [],
                urlAllegato: [],
                size: [],
                mimeType: [],
                note: [],
                createdDate: [],
                lastModifiedDate: [],
                file: []
            }),
            note: []
        });
    }

    constructor(
            protected ambitoCompetenzaService: FormAmbitoCompetenzaService,
            protected competenzaService: FormCompetenzaService,
            protected eventManager: JhiEventManager,
            protected accountService: AccountCandidatoService,
            protected jhiAlertService: JhiAlertService,
            protected anagraficaCandidatoService: FormSchedaCandidatoService,
            protected candidatoService: CandidatoService,
            protected activatedRoute: ActivatedRoute,
            private router: Router,
            private fb: FormBuilder,
            protected linguaService: LinguaService,
            protected curriculumService: CurriculumService,
    ) { this.createForm(); }

    // returns all form groups under competenze
    get competenzeFormGroup(): FormArray {
        return this.editForm.get('competenze') as FormArray;
    }

    // returns all form groups under competenzeLinguistiche
    get competenzeLinguisticheFormGroup(): FormArray {
        return this.editForm.get('lingue') as FormArray;
    }

    get isLinguaAccepted() {
        return this.editForm.get('linguaita');
    }

    get isSpecializzazioneMaster() { return this.editForm.get('specializzazioneMaster'); }

    get titoliStudiocheck(): FormGroup { return this.editForm.get('titoliStudiocheck') as FormGroup; }

    /*handleFileInput(files: FileList) {
        this.files = files;
    }*/

    /*downloadCv(cv: ICurriculum) {
        this.curriculumService.download(cv.id).subscribe(file => {
            FileSaver.saveAs(file, cv.cv);
        });
    }*/

    getCompetenzeLinguisticheFormGroup(index: number): FormGroup {
        // this.lingueList = this.competenzeLinguisticheFormGroup;
        const formGroup = this.lingueList.controls[index] as FormGroup;
        return formGroup;
    }

    loadCompetenzeLinguistiche() {
        if (this.formscheda.competenzeLngs.length > 0) {
            this.formscheda.competenzeLngs.forEach((comp: ICompetenzeLng, i) => {
                const group: FormGroup = this.fb.group({
                    id: [comp.id],
                    livello: [comp.livello, [Validators.required]],
                    lingua: [comp.lingua, [Validators.required]]
                });
                this.competenzeLinguisticheFormGroup.push(group);
            });
        }
        if (this.formscheda.competenzeLngs.length === 0) {
            this.competenzeLinguisticheFormGroup.push(this.createLinguaForm());
        }
        this.lingueList = this.editForm.get('lingue') as FormArray;
    }

    createLinguaForm(): FormGroup {
        return this.fb.group({
            id: [],
            livello: [null, [Validators.required]],
            lingua: [null, [Validators.required]]
        });
    }

    loadAmbitiCompetenze() {
        this.ambitoCompetenzaService.queryparent({type: 'AMBITO'}).pipe(
                filter((res: HttpResponse<IAmbitoCompetenza[]>) => res.ok),
                map((res: HttpResponse<IAmbitoCompetenza[]>) => res.body)
            ).subscribe((response: IAmbitoCompetenza[]) => {
                this.ambitoCompetenzas = response;
                response.forEach(function(eachObj, i) {
                    const group: FormGroup = this.fb.group({
                        id: [],
                        descrizione: [eachObj.descrizione],
                        anniEsperianza: [{value: '', disabled: true}],
                        note: ['test'],
                        anagrafica: [],
                        ambitoComp: [eachObj],
                        selected: [false]
                    });
                    this.competenzeFormGroup.push(group);
                    eachObj.sottoambitos.forEach(function(eachObj2, i2) {
                        const group2: FormGroup = this.fb.group({
                        id: [],
                        descrizione: [eachObj2.descrizione],
                        anniEsperianza: [{value: '', disabled: true}],
                        note: ['test'],
                        anagrafica: [],
                        ambitoComp: [eachObj2],
                        selected: [false]});
                    this.competenzeFormGroup.push(group2);
                    }, this);
                }, this);
            });
    }

    createTitoloStudio(studio: ITitoloStudio): TitoloStudio {
        return {
            id: studio.id,
            tipologia: studio.tipologia,
            descrizione: studio.descrizione,
            conseguimento: studio.conseguimento,
            anno: studio.anno,
            voto: studio.voto
        };
    }

    ngOnInit() {
        this.isSaving = false;
          this.activatedRoute.data.subscribe(({ candidato }) => {
              this.account = candidato;
              this.editForm.get('anagraficaCandidato').patchValue({
                  nome: candidato.nome,
                  cognome: candidato.cognome,
                  email: candidato.email,
                  codiceFiscale: candidato.codiceFiscale
              });
          });

          this.loadAmbitiCompetenze();

          this.anagraficaCandidatoService
          .find(this.account.id)
          .pipe(
            filter((mayBeOk: HttpResponse<IFormSchedaCandidato>) => mayBeOk.ok),
            map((res: HttpResponse<IFormSchedaCandidato>) => res.body)
          ).subscribe((res: IFormSchedaCandidato) => {
              (this.formscheda = res);
              // this.loadTitoliStudio();
              // this.loadAmbitiCompetenze();
              // this.loadCompetenzeLinguistiche();
              this.updateForm(res);
          }, (response: HttpErrorResponse) => {
              // this.onError(response.message);
          });
          this.lingueList = this.editForm.get('lingue') as FormArray;
          this.registerChangeInAnagraficaCandidatoes();
    }

    updateForm(anagraficaCandidato: FormSchedaCandidato) {
        console.log('linguaitacheck: ', anagraficaCandidato.linguaitacheck);
      this.editForm.patchValue({id: anagraficaCandidato.id});
      this.editForm.get('anagraficaCandidato').patchValue({
        nome: anagraficaCandidato.nome,
        cognome: anagraficaCandidato.cognome,
        luogoNascita: anagraficaCandidato.luogoNascita,
        dataNascita: anagraficaCandidato.dataNascita,
        professione: anagraficaCandidato.professione,
        partitaIva: anagraficaCandidato.partitaIva,
        numeroTelefonoFisso: anagraficaCandidato.numeroTelefonoFisso,
        numeroTelefonoCellulare: anagraficaCandidato.numeroTelefonoCellulare,
        indirizzoPec: anagraficaCandidato.indirizzoPec,
        indirizzoResidenza: anagraficaCandidato.indirizzoResidenza,
        capResidenza: anagraficaCandidato.capResidenza,
        comuneResidenza: anagraficaCandidato.comuneResidenza,
        provinciaResidenza: anagraficaCandidato.provinciaResidenza
      });
      this.editForm.patchValue({
          // linguaita: anagraficaCandidato.linguaitacheck,
          note: anagraficaCandidato.note
      });
      this.editForm.controls['linguaita'].patchValue(anagraficaCandidato.linguaitacheck === true ? anagraficaCandidato.linguaitacheck : null);
      this.editForm.controls['linguaita'].updateValueAndValidity();
      const validators = Validators.compose([Validators.required, Validators.min(2)]);
      anagraficaCandidato.competenzas.forEach((comp: ICompetenza, i) => {
          console.log('competenzas.forEach: ', i);
          const index: number = this.competenzeFormGroup.value.findIndex((x: ICompetenza) => x.ambitoComp.id === comp.ambitoComp.id);
          console.log('competenzas.forEach: ', comp.descrizione);
          const formGroup: FormGroup = this.competenzeFormGroup.controls[index] as FormGroup;
          formGroup.controls['anniEsperianza'].enable();
          formGroup.controls['anniEsperianza'].setValidators(validators);
          formGroup.controls['anniEsperianza'].patchValue(comp.anniEsperianza);
          formGroup.controls['id'].patchValue(comp.id);
          formGroup.controls['selected'].patchValue(true);
          formGroup.controls['anniEsperianza'].updateValueAndValidity();
      });
      this.formscheda.titoloStudios.forEach((studio: ITitoloStudio, i) => {
          this.editForm.get('titoliStudio').patchValue({
              id: studio.id,
              tipologia: studio.tipologia,
              descrizione: studio.descrizione,
              conseguimento: studio.conseguimento,
              anno: studio.anno,
              voto: studio.voto
          });
      });
      this.formscheda.competenzeLngs.forEach((comp: ICompetenzeLng, i) => {
          const groupCompLing: FormGroup = this.fb.group({
              id: [comp.id],
              livello: [comp.livello, [Validators.required]],
              lingua: [comp.lingua, [Validators.required]]
          });
          if (this.competenzeLinguisticheFormGroup.controls[i]) {
              const group: FormGroup = this.competenzeLinguisticheFormGroup.controls[i] as FormGroup;
              group.controls['lingua'].patchValue(comp.lingua);
              group.controls['lingua'].updateValueAndValidity();
              group.controls['livello'].patchValue(comp.livello);
              group.controls['livello'].updateValueAndValidity();
          } else {
              this.competenzeLinguisticheFormGroup.push(groupCompLing);
          }
      });
      this.curricula = anagraficaCandidato.curricula;
      anagraficaCandidato.curricula.forEach((cv: ICurriculum, i) => {
          console.log('cv.createdDate: ', cv.createdDate);
          console.log('cv.lastModifiedDate: ', cv.lastModifiedDate);
         this.editForm.get('cv').patchValue({
              id: cv.id,
              cv: cv.cv,
              urlAllegato: cv.urlAllegato,
              size: cv.size,
              mimeType: cv.mimeType,
              note: cv.note,
              createdDate: cv.createdDate,
              lastModifiedDate: cv.lastModifiedDate
          });
      });
      console.log('curriculasize: ', this.curricula.length);
    }

    previousState() {
      window.history.back();
    }

    save() {
      this.isSaving = true;
      const anagraficaCandidato = this.createFromForm();
      const files: FileList = this.editForm.get('cv').get('file').value;
      if (anagraficaCandidato.id !== undefined && anagraficaCandidato.id !== null) {
          if (files && files.length) {
              console.log('files size: ', files.length);
              console.log('UPDATE FILE');
              this.subscribeToSaveResponse(this.anagraficaCandidatoService.updateV2(anagraficaCandidato, files));
          } else {
              console.log('UPDATE');
              this.subscribeToSaveResponse(this.anagraficaCandidatoService.update(anagraficaCandidato));
          }
      } else {
          console.log('CREATE');
        // this.subscribeToSaveResponse(this.anagraficaCandidatoService.create(anagraficaCandidato));
          this.subscribeToSaveResponse(this.anagraficaCandidatoService.createV2(anagraficaCandidato, files));
      }
    }

    private createFromForm(): IFormSchedaCandidato {
        const studio = this.createTitoloStudio(this.editForm.get('titoliStudio').value);
        this.titoliStudio.push(studio);
      return {
        ...new FormSchedaCandidato(),
        id: this.editForm.get(['id']).value,
        nome: this.editForm.get('anagraficaCandidato').get(['nome']).value,
        cognome: this.editForm.get('anagraficaCandidato').get(['cognome']).value,
        luogoNascita: this.editForm.get('anagraficaCandidato').get(['luogoNascita']).value,
        dataNascita: this.editForm.get('anagraficaCandidato').get(['dataNascita']).value,
        codiceFiscale: this.editForm.get('anagraficaCandidato').get(['codiceFiscale']).value,
        professione: this.editForm.get('anagraficaCandidato').get(['professione']).value,
        partitaIva: this.editForm.get('anagraficaCandidato').get(['partitaIva']).value,
        numeroTelefonoFisso: this.editForm.get('anagraficaCandidato').get(['numeroTelefonoFisso']).value,
        numeroTelefonoCellulare: this.editForm.get('anagraficaCandidato').get(['numeroTelefonoCellulare']).value,
        email: this.editForm.get('anagraficaCandidato').get(['email']).value,
        indirizzoPec: this.editForm.get('anagraficaCandidato').get(['indirizzoPec']).value,
        indirizzoResidenza: this.editForm.get('anagraficaCandidato').get(['indirizzoResidenza']).value,
        capResidenza: this.editForm.get('anagraficaCandidato').get(['capResidenza']).value,
        comuneResidenza: this.editForm.get('anagraficaCandidato').get(['comuneResidenza']).value,
        provinciaResidenza: this.editForm.get('anagraficaCandidato').get(['provinciaResidenza']).value,
        note: this.editForm.get(['note']).value,
        candidatoId: this.account.id,
        linguaita: this.isLinguaAccepted.value,
        linguaitacheck: this.isLinguaAccepted.value,
        competenzeLngs: this.competenzeLinguisticheFormGroup.value,
        titoloStudios: [this.editForm.get('titoliStudio').value],
        curricula: [this.editForm.get('cv').value],
        competenzas: this.competenzeFormGroup
            .value.map((competenza: ICompetenza) => competenza.anniEsperianza ? competenza : null)
            .filter((competenza: ICompetenza) => competenza != null)
      };
    }

    registerChangeInAnagraficaCandidatoes() {
        this.eventSubscriber = this.eventManager.subscribe('anagraficaCandidatoListModification', response => this.save());
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormSchedaCandidato>>) {
      result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
      this.isSaving = false;
      // this.previousState();
      this.router.navigate(['/jemoloiscritto']);
    }

    protected onSaveError() {
      this.isSaving = false;
    }

    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
}

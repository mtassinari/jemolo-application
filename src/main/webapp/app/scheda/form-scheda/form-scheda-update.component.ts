import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray, ValidatorFn } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
// import * as moment from 'moment';
import * as FileSaver from 'file-saver';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
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
import { atLeastOneCheckboxCheckedValidator, atLeastOneCheckboxCheckedValidator3, atLeastOneCheckboxCheckedValidator2 } from 'app/scheda/form-competenze/atLeastOneCheckboxCheckedValidator';
import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService } from 'app/entities/dichiarazioni/dichiarazioni.service';
import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';
import { FileValidator } from 'app/scheda/form-cv/file-input.validator';
import {MatSnackBar} from '@angular/material/snack-bar';
import { SchedaDetailComponent } from './scheda-detail/scheda-detail.component';

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
export class FormSchedaUpdateComponent implements OnInit {
    isSaving: boolean;
    // account: CandidatoAccount;
    candidatoes: ICandidato[];
    ambitoCompetenzas: IAmbitoCompetenza[];
    competenzeList: FormArray = new FormArray([]);
    // competenzeList: ICompetenza[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dataNascitaDp: any;
    candidato: Candidato;
    formscheda: IFormSchedaCandidato;
    matcher = new MyErrorStateMatcher();
    // editForm: FormGroup;
    titoliStudio: ITitoloStudio[] = [];
    linguas: ILingua[];
    public lingueList: FormArray;
    dichiarazioniList: FormArray;
    files: FileList;
    curricula: ICurriculum[];
    dichiarazionis?: IDichiarazioni[];
    editForm = this.fb.group({
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
            sanno: [],
            voto: [null, [Validators.required]]
        }),
        // specializzazioneMaster: [false, Validators.requiredTrue],
        titoliStudiocheck: this.fb.group({
            specializzazioneMaster: [],
            iscrizioneAlbo: [],
            operatoreCampo: [],
            esperienzaBiennale: [],
        }, { validators: atLeastOneCheckboxCheckedValidator() }),
        competenze: new FormArray([], atLeastOneCheckboxCheckedValidator3()),
        linguaita: [null, [Validators.requiredTrue]],
        // lingue: new FormArray([]),
        lingue: this.fb.array([this.createLinguaForm()], Validators.required),
        dichiarazionis: new FormArray([]),
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
            file: ['', Validators.compose([FileValidator.validateSize, FileValidator.validateType])]
        }),
        note: []
    });

    constructor(
      private _snackBar: MatSnackBar,
      private alertService: JhiAlertService,
      private eventManager: JhiEventManager,
      protected ambitoCompetenzaService: FormAmbitoCompetenzaService,
      protected competenzaService: FormCompetenzaService,
      protected accountService: AccountCandidatoService,
      protected jhiAlertService: JhiAlertService,
      protected anagraficaCandidatoService: FormSchedaCandidatoService,
      protected candidatoService: CandidatoService,
      protected activatedRoute: ActivatedRoute,
      private router: Router,
      private fb: FormBuilder,
      protected linguaService: LinguaService,
      protected curriculumService: CurriculumService,
      protected dichiarazioniService: DichiarazioniService
    ) {
        this.activatedRoute.data.subscribe(({formSchedaCandidato}) => {
            this.formscheda = formSchedaCandidato;
            // this.updateForm(this.formscheda);
            const competenzas: ICompetenza[] = formSchedaCandidato.competenzas;
        // });
            this.activatedRoute.data.subscribe(({ ambitiCompetenza }) => {
                this.ambitoCompetenzas = ambitiCompetenza;
                ambitiCompetenza.forEach(function(eachObj: IAmbitoCompetenza, i) {
                    const group: FormGroup = this.fb.group({
                        id: [],
                        descrizione: [eachObj.descrizione],
                        anniEsperianza: [{value: '', disabled: true}],
                        note: ['test'],
                        anagrafica: [],
                        ambitoComp: [eachObj],
                        selected: [false],
                        sottoambitos: this.fb.array([])});
                    // this.competenzeFormGroup.push(group);
                    eachObj.sottoambitos.forEach(function(eachObj2: IAmbitoCompetenza, i2) {
                        const validators: ValidatorFn = Validators.compose([Validators.required, Validators.min(2)]);
                        const group2: FormGroup = this.fb.group({
                            id: [],
                            descrizione: [eachObj2.descrizione],
                            anniEsperianza: [{value: 0, disabled: false}],
                            note: ['test'],
                            anagrafica: [],
                            ambitoComp: [eachObj2],
                            selected: [false]});
                        // this.competenzeFormGroup.push(group2);
                        if (competenzas !== undefined) {
                            const comp: ICompetenza = competenzas.find((x: ICompetenza) => x.ambitoComp.id === eachObj2.id);
                            if (comp !== undefined) {
                              if (eachObj2.stato === true) {
                                group2.controls['anniEsperianza'].enable();
                                group2.controls['anniEsperianza'].setValidators(validators);
                                group2.controls['anniEsperianza'].patchValue(comp.anniEsperianza);
                                group2.controls['id'].patchValue(comp.id);
                                group2.controls['selected'].patchValue(true);
                                // formGroup.controls['anniEsperianza'].updateValueAndValidity();
                              }
                              if (eachObj2.stato === false) {
                                group2.controls['anniEsperianza'].disable();
                                group2.controls['anniEsperianza'].setValidators(validators);
                                group2.controls['anniEsperianza'].patchValue(comp.anniEsperianza);
                                group2.controls['id'].patchValue(comp.id);
                                group2.controls['selected'].patchValue(true);
                                group2.controls['selected'].disable();
                                // formGroup.controls['anniEsperianza'].updateValueAndValidity();
                                eachObj2.stato = true;
                                eachObj.stato = true;
                              }
                              this.competenzeList.push(group2);
                            }
                        }
                        if (eachObj2.stato === true) {
                          (group.get('sottoambitos') as FormArray).push(group2);
                        }
                    }, this);
                    if (eachObj.stato === true) {
                      this.competenzeFormGroup.push(group);
                    }
                }, this);
            }, (res: HttpErrorResponse) => this.onError(res.message));
        }, (res: HttpErrorResponse) => this.onError(res.message));

        this.activatedRoute.data.subscribe(({ dichiarazioniObligatorie }) => {
            this.dichiarazionis = dichiarazioniObligatorie || [];
            dichiarazioniObligatorie.forEach(dichiarazione => {
                const group = this.fb.group({}, {});
                const isSelected = false;
                group.addControl('id', this.fb.control(null));
                group.addControl('stato', this.fb.control(isSelected, Validators.requiredTrue));
                group.addControl('dichiarazione', this.fb.control(dichiarazione.descrizione));
                group.addControl('anagraficaId', this.fb.control(this.formscheda.id));
                // group.addControl('anagrafica', this.fb.control(this.formscheda));
                group.addControl('dichiarazioniId', this.fb.control(dichiarazione.id));
                group.addControl('dichiarazioni', this.fb.control(dichiarazione));
                this.dichiarazioniObligatorie.push(group);
            }, (res: HttpErrorResponse) => this.onError(res.message));
            this.dichiarazioniList = this.editForm.get('dichiarazionis') as FormArray;
        }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    get competenzeFormGroup(): FormArray { return this.editForm.get('competenze') as FormArray; }

    get competenzeLinguisticheFormGroup(): FormArray { return this.editForm.get('lingue') as FormArray; }

    get dichiarazioniObligatorie(): FormArray { return this.editForm.get('dichiarazionis') as FormArray; }

    // get isSpecializzazioneMaster() { return this.editForm.get('specializzazioneMaster'); }

    get titoliStudiocheck(): FormGroup { return this.editForm.get('titoliStudiocheck') as FormGroup; }

    get isLinguaAccepted() { return this.editForm.get('linguaita'); }

    getCompetenzeLinguisticheFormGroup(index: number): FormGroup {
        // this.lingueList = this.competenzeLinguisticheFormGroup;
        const formGroup = this.lingueList.controls[index] as FormGroup;
        return formGroup;
    }

    createLinguaForm(): FormGroup {
        return this.fb.group({
            id: [],
            livello: [null, [Validators.required]],
            lingua: [null, [Validators.required]]
        });
    }

    createTitoloStudio(studio: ITitoloStudio): TitoloStudio {
        return {
            id: studio.id,
            tipologia: studio.tipologia,
            descrizione: studio.descrizione,
            conseguimento: studio.conseguimento,
            anno: studio.anno,
            sanno: studio.anno.toString(),
            voto: studio.voto
        };
    }

    ngOnInit() {
        this.router.events.subscribe(evt => {
            if (!(evt instanceof NavigationEnd)) {
                return;
            }
            window.scrollTo(0, 0);
        });
        this.isSaving = false;
        this.accountService.identity().then(account => {
          this.currentAccount = account;
          this.editForm.get('anagraficaCandidato').patchValue({
            nome: account.nome,
            cognome: account.cognome,
            email: account.email,
            codiceFiscale: account.codiceFiscale
          });
       // if (!account.anagraficaCandidatoId) {
          if (!this.formscheda.id) {
            const validators = Validators.compose([Validators.required, FileValidator.validateSize, FileValidator.validateType]);
            this.editForm.get('cv').get(['file']).setValidators(validators);
            this.editForm.get('cv').get(['file']).updateValueAndValidity();
          }
        });
        this.updateForm(this.formscheda);
        this.lingueList = this.editForm.get('lingue') as FormArray;
        this.registerChangeInSchedaIscritto();
    }

    updateForm(anagraficaCandidato: FormSchedaCandidato) {
      this.editForm.patchValue({id: anagraficaCandidato.id});
      if (anagraficaCandidato.id !== undefined) {
          this.editForm.get('anagraficaCandidato').patchValue({
            // nome: anagraficaCandidato.nome,
            // cognome: anagraficaCandidato.cognome,
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
              note: anagraficaCandidato.note
          });
          // this.editForm.controls['specializzazioneMaster'].patchValue(anagraficaCandidato.specializzazioneMaster === true ? anagraficaCandidato.linguaitacheck : null);
          this.titoliStudiocheck.controls['specializzazioneMaster'].patchValue(anagraficaCandidato.specializzazioneMaster === true ? anagraficaCandidato.specializzazioneMaster : false);
          this.titoliStudiocheck.controls['iscrizioneAlbo'].patchValue(anagraficaCandidato.iscrizioneAlbo === true ? anagraficaCandidato.linguaitacheck : false);
          this.titoliStudiocheck.controls['operatoreCampo'].patchValue(anagraficaCandidato.operatoreCampo === true ? anagraficaCandidato.linguaitacheck : false);
          this.titoliStudiocheck.controls['esperienzaBiennale'].patchValue(anagraficaCandidato.esperienzaBiennale === true ? anagraficaCandidato.linguaitacheck : false);
          this.editForm.controls['linguaita'].patchValue(anagraficaCandidato.linguaitacheck === true ? anagraficaCandidato.linguaitacheck : null);
          this.editForm.controls['linguaita'].updateValueAndValidity();
          const validators = Validators.compose([Validators.required, Validators.min(2)]);
          /*this.formscheda.competenzas.forEach((comp: ICompetenza, i) => {
              const index: number = this.competenzeFormGroup.value.findIndex((x: ICompetenza) => x.ambitoComp.id === comp.ambitoComp.id);
              const formGroup: FormGroup = this.competenzeFormGroup.controls[index] as FormGroup;
              formGroup.controls['anniEsperianza'].enable();
              formGroup.controls['anniEsperianza'].setValidators(validators);
              formGroup.controls['anniEsperianza'].patchValue(comp.anniEsperianza);
              formGroup.controls['id'].patchValue(comp.id);
              formGroup.controls['selected'].patchValue(true);
              // formGroup.controls['anniEsperianza'].updateValueAndValidity();
          });*/
          this.formscheda.dichiarazionis.forEach((dich: IDichiarazioniObligatorie, i) => {
              const index: number = this.dichiarazioniObligatorie.value.findIndex((x: IDichiarazioniObligatorie) => x.dichiarazioni.id === dich.dichiarazioni.id);
              const formGroup: FormGroup = this.dichiarazioniObligatorie.controls[index] as FormGroup;
              formGroup.controls['id'].patchValue(dich.id);
              formGroup.controls['stato'].patchValue(dich.stato);
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
       }
    }

    previousState() {
      window.history.back();
    }

    save() {
      this.isSaving = true;
        if (this.editForm.invalid) {
          this.editForm.markAsDirty();
          this.editForm.markAllAsTouched();
          this.isSaving = false;
          return;
        }
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
    // console.log('broadcast schedaModification');
       this.eventManager.broadcast({
          name: 'schedaModification',
          content: 'Scheda modification Success'
       });
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
        candidatoId: this.currentAccount.id,
        linguaita: this.isLinguaAccepted.value,
        linguaitacheck: this.isLinguaAccepted.value,
        competenzeLngs: this.competenzeLinguisticheFormGroup.value,
        // titoloStudios: [this.editForm.get('titoliStudio').value],
        titoloStudios: this.titoliStudio,
        // specializzazioneMaster: this.isSpecializzazioneMaster.value,
        specializzazioneMaster: this.titoliStudiocheck.controls['specializzazioneMaster'].value,
        iscrizioneAlbo: this.titoliStudiocheck.controls['iscrizioneAlbo'].value,
        operatoreCampo: this.titoliStudiocheck.controls['operatoreCampo'].value,
        esperienzaBiennale: this.titoliStudiocheck.controls['esperienzaBiennale'].value,
        curricula: [this.editForm.get('cv').value],
        // competenzas: this.competenzeFormGroup
        /*competenzas: this.competenzeList
            .value.map((competenza: ICompetenza) => competenza.anniEsperianza ? competenza : null)
            .filter((competenza: ICompetenza) => competenza != null),*/
        competenzas: this.competenzeList.value,
        dichiarazionis: this.dichiarazioniObligatorie.value
      };
    }

    registerChangeInSchedaIscritto() {
      this.eventSubscriber = this.eventManager.subscribe('schedaModification', message => {
        // console.log('eventSubscriber: ', message.content);
        // const success2 = this.alertService.addAlert({type: 'success', msg: 'jemoloApplicationApp.anagraficaCandidato.updated', params: { param: this.currentAccount.id }, timeout: 50000}, []);
        window.scrollTo(0, 0);
      });
    }

    openSnackBar(message: string, action: string) {
        const snackBarRef = this._snackBar.open(message, action, {
           duration: 5000,
        });
        /*let snackBarRef = this._snackBar.open(message, action);*/
        // this._snackBar.openFromComponent(SchedaDetailComponent);
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormSchedaCandidato>>) {
      result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
      this.isSaving = false;
      // this.previousState();
      // this.router.navigate(['jemoloiscritto']);
      this.router.navigate(['jemoloiscritto/scheda', this.currentAccount.id, 'view' ]);
      // this.router.navigate(['jemoloiscritto', this.currentAccount.id, 'edit' ]);
      this.openSnackBar('Salvataggio avvenuto con successo!', 'chiudi');
    }

    protected onSaveError() {
      this.isSaving = false;
    }

    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
}

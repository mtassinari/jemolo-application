import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';
// import { ProvincieComuniSelectService } from '../form-scheda/services/provincie-comuni.service';
import { ComuneService } from '../form-scheda/services/provincie-comuni.service2';
// import { IProvincia, Provincia } from 'app/shared/model/provincia2.model';
import { IComune } from 'app/shared/model/comune.model';
import { HttpResponse } from '@angular/common/http';
import { IProvincia } from 'app/shared/model/provincia.model';
import { MY_FORMATS } from 'app/shared/util/format-datepicker';
import {
    DateAdapter,
    MAT_DATE_FORMATS,
    MAT_DATE_LOCALE
} from '@angular/material';
import {
    MomentDateModule,
    MomentDateAdapter
} from '@angular/material-moment-adapter';

@Component({
  selector: 'jhi-form-anagrafica',
  templateUrl: './form-anagrafica.component.html',
  styleUrls: ['./form-anagrafica.component.scss'],
    providers: [/*{
            provide: MAT_DATE_LOCALE,
            useValue: 'it'
        },*/
        // you can change
        {
            provide: DateAdapter,
            useClass: MomentDateAdapter,
            deps: [MAT_DATE_LOCALE]
        }, {
            provide: MAT_DATE_FORMATS,
            useValue: MY_FORMATS
        }
    ],
})
export class FormAnagraficaComponent implements OnInit {
  @Input() anag: FormGroup;
  matcher = new MyErrorStateMatcher();

// provinciaSelezionata: IProvincia = new Provincia(1, 'ROMA');
// provincie: IProvincia[];
   comuni: IComune[];
   provincie: IProvincia[];

  constructor(/*private service: ProvincieComuniSelectService, */private comuneService: ComuneService) { }
  loadAll(): void {
    const sigla: String = this.anag.controls['provinciaResidenza'].value;
    this.comuneService.queryProvincie().subscribe((res: HttpResponse<IProvincia[]>) => (this.provincie = res.body || []));
    this.comuneService.queryComuni({siglaProvincia: sigla}).subscribe((res: HttpResponse<IComune[]>) => (this.comuni = res.body || []));
  }

  ngOnInit() {
 // this.provincie = this.service.getProvincie();
    this.loadAll();
 // this.comuneService.queryProvincie().subscribe((res: HttpResponse<IProvincia[]>) => (this.listaProvincie = res.body || []));
  }

  onSelect(sigla: string) {
 // console.log('sigla provincia: ', sigla);
 // this.comuni = this.service.getComuni().filter((item) => item.siglaProvincia == sigla);
 // this.comuni = this.comuni.find(prov => prov.siglaProvincia == sigla).comuni;
    this.comuneService.queryComuni({siglaProvincia: sigla}).subscribe((res: HttpResponse<IComune[]>) => (this.comuni = res.body || []));
  }
}

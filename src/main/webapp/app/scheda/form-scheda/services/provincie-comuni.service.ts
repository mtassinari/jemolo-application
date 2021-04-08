import { Injectable } from '@angular/core';
import { Provincia } from 'app/shared/model/provincia2.model';
import { Comune } from 'app/shared/model/comune2.model';

@Injectable({ providedIn: 'root' })
export class ProvincieComuniSelectService {

  getProvincie() {
    return [
     new Provincia(1, 'RM', 'ROMA', [new Comune(1, 1, 'RM', 'ROMA' ), new Comune(2, 1, 'RM', 'POMEZIA' ), new Comune(3, 1, 'RM', 'ARDEA'), new Comune(4, 1, 'RM', 'ANZIO')]),
     new Provincia(2, 'LT', 'LATINA', [new Comune(5, 2, 'LT', 'LATINA' ), new Comune(6, 2, 'LT', 'APRILIA'), new Comune(7, 2, 'LT', 'TERRACINA' ), new Comune(7, 2, 'LT', 'SABAUDIA' )]),
     new Provincia(3, 'FR', 'FROSINONE', [new Comune(8, 3, 'FR', 'FROSINONE' ), new Comune(9, 3, 'FR', 'ALATRI'), new Comune(10, 3, 'FR', 'SORA' ),	 new Comune(10, 3, 'FR', 'AMASENO' )]),
     new Provincia(4, 'RI', 'RIETI', [new Comune(11, 4, 'RI', 'RIETI' ), new Comune(12, 4, 'RI', 'AMATRICE'), new Comune(12, 4, 'RI', 'GRECCIO'), new Comune(13, 4, 'RI', 'LEONESSA' )]),
     new Provincia(5, 'VT', 'VITERBO', [new Comune(14, 5, 'VT', 'VITERBO' ), new Comune(15, 5, 'VT', 'BOLSENA'), new Comune(16, 5, 'VT', 'VETRALLA' ), new Comune(16, 5, 'VT', 'TARQUINIA' )])
    ];
  }

  getComuni() {
   return [
     new Comune(1, 1, 'RM', 'ROMA' ),
     new Comune(2, 1, 'RM', 'POMEZIA' ),
     new Comune(3, 1, 'RM', 'ARDEA'),
     new Comune(4, 1, 'RM', 'ANZIO'),
     new Comune(5, 2, 'LT', 'LATINA' ),
     new Comune(6, 2, 'LT', 'APRILIA'),
     new Comune(7, 2, 'LT', 'TERRACINA' ),
     new Comune(7, 2, 'LT', 'SABAUDIA' ),
     new Comune(8, 3, 'FR', 'FROSINONE' ),
     new Comune(9, 3, 'FR', 'ALATRI'),
     new Comune(10, 3, 'FR', 'SORA' ),
     new Comune(10, 3, 'FR', 'AMASENO' ),
     new Comune(11, 4, 'RI', 'RIETI' ),
     new Comune(12, 4, 'RI', 'AMATRICE'),
     new Comune(12, 4, 'RI', 'GRECCIO'),
     new Comune(13, 4, 'RI', 'LEONESSA' ),
     new Comune(14, 5, 'VT', 'VITERBO' ),
     new Comune(15, 5, 'VT', 'BOLSENA'),
     new Comune(16, 5, 'VT', 'VETRALLA' ),
     new Comune(16, 5, 'VT', 'TARQUINIA' )
    ];
  }
}

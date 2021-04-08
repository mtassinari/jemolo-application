import { HttpParams } from '@angular/common/http';

export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();
  if (req) {
    Object.keys(req).forEach(key => {
      if (key !== 'sort' && key !== 'params' && key !== 'sparams') {
        // console.log('req[' + key + ']: ', req[key]);
        options = options.set(key, req[key]);
      }
    });
    if (req.sort) {
      req.sort.forEach(val => {
        options = options.append('sort', val);
      });
    }
    if (req.sparams) {
        Object.keys(req.sparams).forEach(key => {
            // console.log('req[' + key + ']: ', req.sparams[key]);
            options = options.set(key, req.sparams[key]);
        });
    }
    if (req.params) {
        Object.keys(req.params).forEach(key => {
              // console.log(key , req.params[key]);
              switch (key) {
              case 'nome':
                  options = options.set(key + '.contains', req.params[key]);
                  // console.log(key + '.equals', req.params[key]);
                  break;
              case 'cognome':
                  options = options.set(key + '.contains', req.params[key]);
                  // console.log(key + '.equals', req.params[key]);
                  break;
              case 'tipotitolodistudioId':
              case 'areaCompetenzaId':
              case 'linguaId':
              case 'activated':
                  options = options.set(key + '.equals', req.params[key]);
                  // console.log(key + '.equals', req.params[key]);
                  break;
              case 'titolostudioDal':
                  options = options.set(key + '.greaterOrEqualThan', req.params[key]);
                  console.log(key + '.greaterOrEqualThan', req.params[key]);
                  break;
              case 'titolostudioAl':
                  options = options.set(key + '.lessOrEqualThan', req.params[key]);
                  console.log(key + '.lessOrEqualThan', req.params[key]);
                  break;
              case 'areaCompetenzaDa':
                  options = options.set(key + '.greaterOrEqualThan', req.params[key]);
                  console.log(key + '.greaterOrEqualThan', req.params[key]);
                  break;
              case 'areaCompetenzaA':
                  options = options.set(key + '.lessOrEqualThan', req.params[key]);
                  console.log(key + '.lessOrEqualThan', req.params[key]);
                  break;
              case 'linguaLivello':
                  options = options.set(key + '.greaterOrEqualThan', req.params[key]);
                  console.log(key + '.greaterOrEqualThan', req.params[key]);
                  break;
              }
          });
      }
  }
  return options;
};
export const createRequestOption2 = (req?: any, searchParam?: any): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (req) {
      Object.keys(req).forEach(key => {
        if (key !== 'sort') {
          options = options.set(key, req[key]);
        }
      });
      if (req.sort) {
        req.sort.forEach(val => {
          options = options.append('sort', val);
        });
      }
    }
    if (searchParam) {
        Object.keys(searchParam).forEach(key => {
            options = options.set(key, searchParam[key]);
        });
      }
    return options;
  };

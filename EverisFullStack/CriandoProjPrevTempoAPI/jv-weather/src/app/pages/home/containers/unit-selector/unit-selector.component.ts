import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs/internal/Observable';
import { Units } from 'src/app/shared/models/units.enum';
import { AppState } from 'src/app/shared/state/app.reducer';
import * as fromConfigSelectors from '../../../../shared/state/config/config.selectors';
import * as fromConfigActions from '../../../../shared/state/config/config.actions';

@Component({
  selector: 'jv-unit-selector',
  templateUrl: './unit-selector.component.html',
  styleUrls: ['./unit-selector.component.scss']
})
export class UnitSelectorComponent implements OnInit {

  unit$: Observable<Units>;
  unit: Units;

  unitsEnum = Units;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.unit$ = this.store.pipe(select(fromConfigSelectors.selectUnitConfig));
    this.unit$
      .subscribe(unit => this.unit = unit);
  }

  updateUnit(unit: Units) {
    this.store.dispatch(fromConfigActions.updateUnit({ unit }));
  }

}

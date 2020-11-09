import { Component, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-star',
  templateUrl: './star.component.html',
  styleUrls: ['./star.component.css']
})

export class StarComponent implements OnChanges{

  @Input()
  ratting: number = 0;
  starWidth: number;

  ngOnChanges(): void{
    this.starWidth = this.ratting * 74 / 5;
  }
}

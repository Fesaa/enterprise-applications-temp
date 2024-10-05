import {Component, OnInit} from '@angular/core';
import {NavService} from "../_services/nav.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {


  constructor(protected navService: NavService) {
  }

  ngOnInit(): void {
    this.navService.setNavVisibility(true);
  }

}

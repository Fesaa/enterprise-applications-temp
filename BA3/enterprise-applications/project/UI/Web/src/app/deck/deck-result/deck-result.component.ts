import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {SessionService} from "../../_services/session.service";
import {NavService} from "../../_services/nav.service";
import {Session, SessionAnswer} from "../../_models/session";
import {DatePipe, DecimalPipe, NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-deck-result',
  standalone: true,
  imports: [
    NgIf,
    NgClass,
    NgForOf,
    DecimalPipe,
    DatePipe,
    RouterLink
  ],
  templateUrl: './deck-result.component.html',
  styleUrl: './deck-result.component.css'
})
export class DeckResultComponent implements OnInit {

  session: Session | null = null;

  constructor(
    private route: ActivatedRoute,
    private sessionService: SessionService,
    private navService: NavService,
    private router: Router,
  ) {

    this.navService.setNavVisibility(true);

    this.route.params.subscribe(async (params) => {
      const sessionId = params['sessionId'];
      if (!sessionId) {
        this.router.navigateByUrl("/home");
        return
      }

      this.sessionService.get(sessionId).subscribe(session => {
        this.session = session;
      })
    })
  }


  ngOnInit(): void {
  }

}

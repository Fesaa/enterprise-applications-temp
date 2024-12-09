import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Title} from "@angular/platform-browser";
import {NavHeaderComponent} from "./nav-header/nav-header.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavHeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Enterprise Applications Project';

  constructor(private titleService: Title) {
    this.titleService.setTitle(this.title);
  }
}

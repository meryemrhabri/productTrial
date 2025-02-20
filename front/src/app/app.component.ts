import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {CartService} from "./services/cart.service";
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'


@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent,CommonModule,ReactiveFormsModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  cartItemCount = 0;
  cartItems: any[] = [];
  showCart = false;

  constructor(private cartService: CartService) {
    this.cartService.cartItemCount.subscribe((count) => {
      this.cartItemCount = count;
    });

    this.cartService.getCart().subscribe((items) => {
      this.cartItems = items;
    });
  }
  toggleCart() {
    console.log('change detected')
    this.showCart = !this.showCart;
  }

  removeFromCart(productId: number) {
    this.cartService.removeFromCart(productId);
  }
}

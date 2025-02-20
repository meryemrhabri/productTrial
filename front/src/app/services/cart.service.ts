import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {
   private  cart :any [] =[];
  cartItems = new BehaviorSubject<any[]>([]);
  cartItemCount = new BehaviorSubject<number>(0);
  constructor() { }

  addToCart(product: any) {
    const existingProduct = this.cart.find(item => item.id === product.id);
    if (existingProduct) {
      existingProduct.quantity += 1;
    } else {
      this.cart.push({ ...product, quantity: 1 });
    }
    this.cartItems.next([...this.cart]);
    this.cartItemCount.next(this.cart.reduce((sum, item) => sum + item.quantity, 0));
    console.log('Produit ajouté au panier :', this.cart);
  }
  getCart() {
    return this.cartItems.asObservable();
  }

  removeFromCart(productId: number) {
    this.cart = this.cart.filter((item) => item.id !== productId);
    this.cartItems.next([...this.cart]);
    this.cartItemCount.next(this.cart.reduce((sum, item) => sum + item.quantity, 0));
  }
}

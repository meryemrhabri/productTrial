import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {CONTACTS_ROUTES} from "./contacts/contacts.routes";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  {
    path: "contacts",
    loadChildren: () =>
      import("./contacts/contacts.routes").then((m) => m.CONTACTS_ROUTES)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];

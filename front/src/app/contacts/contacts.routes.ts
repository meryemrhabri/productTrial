import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ContactsComponent } from './contacts.component';

export const CONTACTS_ROUTES: Routes = [
	{
		path: "listContact",
		component: ContactsComponent,
	},
	{ path: "**", redirectTo: "list" },
];

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators ,ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css'
})
export class ContactsComponent {
  contactForm: FormGroup;
  message : any=''
  constructor(private fb: FormBuilder) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }
  onSubmit() {
    if (this.contactForm.valid) {
      console.log('Form Submitted', this.contactForm.value);
      console.log('Demande de contact envoy�e avec succ�s')
      this.message='Demande de contact envoy�e avec succ�s'
    }
  }
}

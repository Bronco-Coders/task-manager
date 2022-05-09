package com.taskmanager.application.views.login;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Register")
@Route(value = "Register")
@PermitAll
public class RegisterView extends Composite {
	
	@Override
	protected Component initContent() {
		TextField username = new TextField("Username");
		PasswordField password1 = new PasswordField("Password");
		PasswordField password2 = new PasswordField("Confirm password");
		return new VerticalLayout(
				new H2("Register"),
				username,
				password1,
				password2,
				new Button("Send", clickevent -> register(
						username.getValue(),
						password1.getValue(),
						password2.getValue()
						))
						
				);
	}
	
	private void register(String username, String password1, String password2) {
		
	}
 
}

package com.taskmanager.application.views.login;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import com.taskmanager.application.data.entity.User;
import com.taskmanager.application.data.service.UserService;
import com.taskmanager.application.views.tasks.TasksView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//@PermitAll
@PageTitle("Login")
@Route(value = "")
public class LoginView extends Composite {
	@Resource
	private UserService userService;
	
	@Override
	protected Component initContent() {
		TextField username = new TextField("Username");
		PasswordField password1 = new PasswordField("Password");
		return new VerticalLayout(
				new H2("Login"),
				username,
				password1,
                new HorizontalLayout(
				new Button("Login", clickevent -> login(
						username.getValue(),
						password1.getValue()
						)),
						
				
                new Button("Register", clickevent -> goTo(
						))
                )
				);
	}

	private void login(String username, String password1) {
		UI.getCurrent().navigate(TasksView.class);
		
	}
    private void goTo(){
        UI.getCurrent().navigate(RegisterView.class);

    }
 
}

package com.example.richweb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SessionScoped
@Named
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@NotNull
	private String name = "unknown";
	
	@Min(1)
	@Max(12)
	private int miesiac = 0;

	@Min(1900)
	@Max(2010)
	private int yob = 0;

	private String email = "unknown";

	private String pesel = "unknown";

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYob() {
		return yob;
	}

	public void setYob(int yob) {
		this.yob = yob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void validateEmail(FacesContext context, UIComponent component, Object value) {
		
		String email = (String) value;
		
		if (email.indexOf('@') == -1 || ! email.endsWith(".pl")){
			((UIInput)component).setValid(false);
			FacesMessage message = new FacesMessage("Niepoprawny email");
			context.addMessage(component.getClientId(), message);
		}
		
	}
	
	
	public void validatePesel(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();

		UIInput peselInput = (UIInput) form.findComponent("pesel");
		String pesel = (String) peselInput.getValue();

		UIInput rokUrInput = (UIInput) form.findComponent("yob");
		UIInput mieUrInput = (UIInput) form.findComponent("mob");

		int rokUr = (Integer) rokUrInput.getValue();
		int mieUr = (Integer) mieUrInput.getValue();

		FacesContext fc = FacesContext.getCurrentInstance();

		Integer rok = new Integer(rokUr);
		Integer miesiac = new Integer(mieUr);
		
		String m;
		
		if (miesiac.toString().length() < 2) {
			if (rok < 2000) {
				m = "0" + miesiac.toString();
			} else {
				m = "2" + miesiac.toString();
			}
		} else {
			if (rok < 2000) {
				m = miesiac.toString();
			} else {
				m = "3" + miesiac.toString().substring(1, 2);
			}			
		}

		if (rok.toString().length() > 3) {

			if (!pesel.substring(0, 2).equals(rok.toString().substring(2, 4))) {
				fc.addMessage(form.getClientId(), new FacesMessage(
						"Rok urodzenia nie pasuje do pesela"));
				fc.renderResponse();
			}
			
			if (!pesel.substring(2, 4).equals(m)) {
				fc.addMessage(form.getClientId(), new FacesMessage(
				"Miesiac urodzenia nie pasuje do pesela"));
		fc.renderResponse();
			}
		} else {
			fc.addMessage(form.getClientId(), new FacesMessage(
					"Niepoprawny rok urodzenia"));
			fc.renderResponse();
		}
	}

	
	public String logout(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		request.getSession(false).invalidate();
		return "home";
	}

	public void setMiesiac(int miesiac) {
		this.miesiac = miesiac;
	}

	public int getMiesiac() {
		return miesiac;
	}

}

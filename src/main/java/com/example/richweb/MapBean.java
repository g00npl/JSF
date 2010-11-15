package com.example.richweb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@SessionScoped
@Named
public class MapBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String zoomLevel = " unknown ";
	private String city = " unknown ";
	private String cityError = "";
	private String cityAndZoomError = "";

	public String getCityError() {
		return cityError;
	}

	public void setCityError(String cityError) {
		this.cityError = cityError;
	}

	public String getCityAndZoomError() {
		return cityAndZoomError;
	}

	public void setCityAndZoomError(String cityAndZoomError) {
		this.cityAndZoomError = cityAndZoomError;
	}

	private static SelectItem[] zoomLevelItems = { new SelectItem("1"),
			new SelectItem("3"), new SelectItem("5"), new SelectItem("7"),
			new SelectItem("9") };

	public String getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(String zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public SelectItem[] getZoomLevelItems() {
		return zoomLevelItems;
	}

	public void validateCity(ValueChangeEvent e) {
		UIInput cityInput = (UIInput) e.getComponent();
		String cityName = (String) cityInput.getValue();

		if (cityName.equals("Sopot") || cityName.equals("Gdynia")) {
			cityError = "";
		} else {
			cityError = "Map only for Sopot and Gdynia";
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.renderResponse();
	}

	public void validateCityAndZoom(ComponentSystemEvent e) {
		
		UIForm form = (UIForm) e.getComponent();
		
		UIInput cityInput = (UIInput) form.findComponent("city");
	 	String cityName = (String) cityInput.getValue();
	
		UISelectOne zoom =   (UISelectOne) form.findComponent("zoomLevel");
		 
		int zoomValue = Integer.parseInt((String) zoom.getValue());				
		FacesContext fc = FacesContext.getCurrentInstance();
			
		if (cityName.equals("Sopot") && zoomValue > 5) {
			fc.addMessage(form.getClientId(), new FacesMessage("Zoom too big for Sopot"));
			fc.renderResponse();			
		}		
	}
}

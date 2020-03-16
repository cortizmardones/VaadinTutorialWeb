package rg.vaadin.vistas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.internal.ResponseWriter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import rg.vaadin.modelos.PersonaW;

@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
	
	private TextField nameField = new TextField();
	private RadioButtonGroup<String> generoCheck = new RadioButtonGroup<>();
	private EmailField emailField = new EmailField("Email:");
	private NumberField numberField = new NumberField();
	private DatePicker dataPicker = new DatePicker();
	private Select<String> placeholderSelect = new Select<>();
	private TextArea textArea = new TextArea("Cuentanos sobre ti:");

	public MainView() {
		
		Label tituloLabel = new Label("Formulario de postulación:");

		//TextField nameField = new TextField();
		nameField.setLabel("Nombre completo:");
		nameField.setPlaceholder("Escribá su nombre acá");
		
		//RadioButtonGroup<String> generoCheck = new RadioButtonGroup<>();
		generoCheck.setLabel("Género:");
		generoCheck.setItems("Hombre","Mujer");
		generoCheck.setValue("Hombre");
		//Por si quisiera los checkbox Verticales.
		//generoCheck.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		
		//EmailField emailField = new EmailField("Email:");
		emailField.setClearButtonVisible(true);
		emailField.setPlaceholder("example@email.com");
		emailField.setErrorMessage("Porfavor ingresar un email válido");

		//NumberField numberField = new NumberField();
		numberField.setLabel("Edad:");
		numberField.setHasControls(true);
		numberField.setMin(18);
		numberField.setMax(99);

		//DatePicker dataPicker = new DatePicker();
		dataPicker.setLabel("Fecha de nacimiento:");
		LocalDate ahora = LocalDate.now();
		dataPicker.setValue(ahora);

		//Select<String> placeholderSelect = new Select<>();
		placeholderSelect.setLabel("Cargo al que postula");
		placeholderSelect.setItems("Administrador de redes", "Analista de datos", "Ingeniero de software", "Analista Programador","DBA","QA");
		placeholderSelect.setPlaceholder("Elija una opción");

		//TextArea textArea = new TextArea("Cuentanos sobre ti:");
		textArea.getStyle().set("maxHeight", "150px");
		textArea.setPlaceholder("Escribé acá");
		
		Dialog dialog = new Dialog();
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);

		Label messageLabel = new Label();

		Button confirmButton = new Button("Confirmar", event -> {
		    messageLabel.setText("Confirmado!");
		    dialog.close();
		});
		Button cancelButton = new Button("Cancelar", event -> {
		    messageLabel.setText("Cancelado!.");
		    dialog.close();
		});
		confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		dialog.add(confirmButton, cancelButton);
		
		Button button = new Button("Enviar");
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		//button.addClickListener(event -> dialog.open());
		button.addClickListener(event -> capturarDatos());
		
//		FormLayout nameLayout = new FormLayout();
//		nameLayout.setResponsiveSteps(new ResponsiveStep("33em",1), new ResponsiveStep("33em", 2), new ResponsiveStep("33em", 3));
//		nameLayout.add(nameField, apellidoField, emailField, numberField, dataPicker, placeholderSelect, textArea,button);
//		add(nameLayout);
		
//		HorizontalLayout nameLayout = new HorizontalLayout();
//		nameLayout.getStyle().set("border", "1px solid #9E9E9E");
//		nameLayout.add(nameField, apellidoField, emailField, numberField, dataPicker, placeholderSelect, textArea,button);
//		add(nameLayout);
		
		VerticalLayout nameLayout = new VerticalLayout();
		nameLayout.getStyle().set("border", "1px solid #9E9E9E");
		
		//nameLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		nameLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
		
		nameLayout.add(tituloLabel,nameField,generoCheck, emailField, numberField, dataPicker, placeholderSelect, textArea,button);
		add(nameLayout);
	}
	
	public void capturarDatos() {
		
		Notification notification = new Notification("",3000);
		
		
		if(nameField.getValue() == "" || nameField.getValue().isEmpty()) {
			notification.setText("Debe ingresar su nombre");
			notification.open();
		}else if (emailField.getValue() == "" || emailField.getValue().isEmpty()) {
			notification.setText("Debe ingresar su email");
			notification.open();
		}else if(numberField.getValue() == null || numberField.getValue() < 18 || numberField.getValue() > 99) {
			notification.setText("Debe ingresar una edad válida , el rango válido es entre 18 y 99 años");
			notification.open();
		} else if(dataPicker.getValue() == null) {
			notification.setText("Debe ingresar una fecha válida");
			notification.open();
		}else if(placeholderSelect.getValue() == null){
			notification.setText("Debe ingresar un cargo al que postular");
			notification.open();
		}else if(textArea.getValue() == "" || textArea.isEmpty()){
			notification.setText("Debes contarnos sobre tí");
			notification.open();
		}else {
			PersonaW persona = new PersonaW();
			persona.setNombre(nameField.getValue());
			persona.setGenero(generoCheck.getValue());
			persona.setEmail(emailField.getValue());
			//Tuve que castear por que numberField.getValue devuelve un Double
			persona.setEdad((int)Math.round(numberField.getValue()));
			persona.setFechaNacimiento(dataPicker.getValue());
			persona.setCargo(placeholderSelect.getValue());
			persona.setAbout(textArea.getValue());
				
			System.out.println("Nombre: " + persona.getNombre());
			System.out.println("Género: " + persona.getGenero());
			System.out.println("Email: " + persona.getEmail());
			System.out.println("Edad: " + persona.getEdad());
			System.out.println("Fecha Nacimiento: " + persona.getFechaNacimiento());
			System.out.println("Cargo: " + persona.getCargo());
			System.out.println("Comentarios: " + persona.getAbout());
			System.out.println("");
		}
	}


}

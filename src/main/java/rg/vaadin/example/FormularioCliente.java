package rg.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class FormularioCliente extends FormLayout{
	
	private Binder<Cliente> binder = new Binder<>(Cliente.class);

	private TextField nombre = new TextField("Nombre");
	private TextField apellido = new TextField("Apellido");
	private ComboBox<EstadoCliente> status = new ComboBox<>("Status");
	private DatePicker cumpleanos = new DatePicker("Cumpleaños");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private ServicioCliente servicio = ServicioCliente.getInstance();
	
	private MainView mainView;
	
	public FormularioCliente(MainView mainView) {
		this.mainView = mainView;
		binder.bindInstanceFields(this);
		
		//Status.setItemsAgrega todos los valores de enumeracion como opciones al comboBox
		status.setItems(EstadoCliente.values());
		
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		//addThemeVariants resalta el botón GUARDAR al decorarlo con un nombre de estilo.
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(nombre, apellido, status, cumpleanos, buttons);
		
		save.addClickListener(event -> save());
		delete.addClickListener(event -> delete());
		
		
	}
	
	public void setCustomer(Cliente cliente) {
	    binder.setBean(cliente);

	    if (cliente == null) {
	        setVisible(false);
	    } else {
	        setVisible(true);
	        nombre.focus();
	    }
	}
	
	private void save() {
	    Cliente cliente = binder.getBean();
	    servicio.save(cliente);
	    mainView.updateList();
	    setCustomer(null);
	}
	
	private void delete() {
	    Cliente cliente = binder.getBean();
	    servicio.delete(cliente);
	    mainView.updateList();
	    setCustomer(null);
	}

}

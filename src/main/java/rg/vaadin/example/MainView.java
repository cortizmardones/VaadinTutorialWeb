package rg.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
	
	private ServicioCliente service = ServicioCliente.getInstance();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
	
	private TextField filterText = new TextField();
	
	private FormularioCliente formulario = new FormularioCliente(this);
	
    public MainView() {
    	
    	formulario.setCustomer(null);
    	
    	//setPlaceholder Muestra la cadena dada en el campo. Cuando el usuario comienza a escribir, el marcador de posición se elimina automáticamente.
    	filterText.setPlaceholder("Filtrar por nombre");
    	
    	//setClearButtonVisible Agrega un botón claro (X) que se muestra en el lado derecho del campo de texto.
    	filterText.setClearButtonVisible(true);
    	
    	//ValueChangeMode.EAGER Garantiza que los eventos de cambio se activen inmediatamente cuando el usuario escribe.
    	filterText.setValueChangeMode(ValueChangeMode.EAGER);
    	
    	//addValueChangeListener Agrega un detector de cambio de valor que reacciona a los cambios en el valor del campo de texto.
    	filterText.addValueChangeListener(e -> updateList());
    	
    	//Agregar los campos de la tabla Customer como titulo de columnas en la grid.
    	grid.setColumns("nombre","apellido","status");
    	
    	//Definir la grid al 100% de la pantalla.
    	setSizeFull();
    	updateList();
    	
    	HorizontalLayout mainContent = new HorizontalLayout(grid, formulario);
    	mainContent.setSizeFull();
    	grid.setSizeFull();

    	add(filterText, mainContent);
    	
    	grid.asSingleSelect().addValueChangeListener(event -> formulario.setCustomer(grid.asSingleSelect().getValue()));
    	
    }
    
    public void updateList() {
    	//filterText.getValue() devuelve la cadena actual en el campo de texto.
    	grid.setItems(service.findAll(filterText.getValue()));
    }
}

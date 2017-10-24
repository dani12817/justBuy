package jsf;

import jpa.entity.Pagotarjeta;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.PagotarjetaFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.entity.Usuario;

@Named("pagotarjetaController")
@SessionScoped
public class PagotarjetaController implements Serializable {

    private Pagotarjeta current;
    private DataModel items = null;
    @EJB
    private jpa.session.PagotarjetaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private Pagotarjeta newPagoPorTarjeta;
    private Pagotarjeta editPagoPorTarjeta;
    
    private boolean metodoElegido = false;
    private final String FacesRedirect = "?faces-redirect=true";
    
    private String numeroTarjeta;    
    private String errorNumeroTarjeta;
    private List<Pagotarjeta> listaPagoTarjeta;

    public PagotarjetaController() {
    }

    public List<Pagotarjeta> getListaPagoTarjeta() {
        return listaPagoTarjeta;
    }

    public void setListaPagoTarjeta(List<Pagotarjeta> listaPagoTarjeta) {
        this.listaPagoTarjeta = listaPagoTarjeta;
    }

    public String getErrorNumeroTarjeta() {
        return errorNumeroTarjeta;
    }

    public void setErrorNumeroTarjeta(String errorNumeroTarjeta) {
        this.errorNumeroTarjeta = errorNumeroTarjeta;
    }

    public boolean isMetodoElegido() {
        return metodoElegido;
    }

    public void setMetodoElegido(boolean metodoElegido) {
        this.metodoElegido = metodoElegido;
    }

    public Pagotarjeta getNewPagoPorTarjeta() {
        return newPagoPorTarjeta;
    }

    public void setNewPagoPorTarjeta(Pagotarjeta newPagoPorTarjeta) {
        this.newPagoPorTarjeta = newPagoPorTarjeta;
    }

    public Pagotarjeta getEditPagoPorTarjeta() {
        return editPagoPorTarjeta;
    }

    public void setEditPagoPorTarjeta(Pagotarjeta editPagoPorTarjeta) {
        this.editPagoPorTarjeta = editPagoPorTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    ///////////////////Mis Metodos//////////////////////////////
    
    public void montarListaTarjeta(Usuario usuarioLogin){
        listaPagoTarjeta = ejbFacade.buscarPorUsuario(usuarioLogin);
        System.out.println("Tarjeta "+listaPagoTarjeta.size());
    }
    
    public String nuevoMetodoPago(){
        
        newPagoPorTarjeta = new Pagotarjeta();
        metodoElegido = true;
        System.out.println("Nueva Tarjeta");
        
        return "cuentaMetodoPagoNew"+FacesRedirect;
    }
    
    public String crearMetodoPago(Usuario usuarioLogin){
        errorNumeroTarjeta="";
        
        if(numeroTarjeta.matches("[0-9]+")){
            newPagoPorTarjeta.setNumeroTarjeta(Integer.parseInt(numeroTarjeta));
        }else{
            errorNumeroTarjeta="Introduce solo números";
            return "";
        }
        
        newPagoPorTarjeta.setUsuario(usuarioLogin);
        
        ejbFacade.create(newPagoPorTarjeta);
        
        newPagoPorTarjeta = null;
        metodoElegido = false;
        numeroTarjeta="";
        
        return "cuentaMetodoPago"+FacesRedirect;
    }
    
    public String cargarMetodoPago(int idPagoTarjeta){
        
        editPagoPorTarjeta = ejbFacade.find(idPagoTarjeta);
        metodoElegido = true;
        
        return "cuentaMetodoPagoEdit"+FacesRedirect;
    }
    
    public String editarMetodoPago(){
        errorNumeroTarjeta="";
        
        if(numeroTarjeta.matches("[0-9]+")){
            editPagoPorTarjeta.setNumeroTarjeta(Integer.parseInt(numeroTarjeta));
        }else{
            errorNumeroTarjeta="Introduce solo números";
            return "";
        }
        
        ejbFacade.edit(editPagoPorTarjeta);
        
        editPagoPorTarjeta = null;
        metodoElegido = false;
        numeroTarjeta="";
        
        return "cuentaMetodoPago"+FacesRedirect;
    }
    
    ///////////////////////////////////////////////////////////

    public Pagotarjeta getSelected() {
        if (current == null) {
            current = new Pagotarjeta();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PagotarjetaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Pagotarjeta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Pagotarjeta();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagotarjetaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Pagotarjeta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagotarjetaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Pagotarjeta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagotarjetaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Pagotarjeta getPagotarjeta(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pagotarjeta.class)
    public static class PagotarjetaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PagotarjetaController controller = (PagotarjetaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pagotarjetaController");
            return controller.getPagotarjeta(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pagotarjeta) {
                Pagotarjeta o = (Pagotarjeta) object;
                return getStringKey(o.getIdmetodoPago());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Pagotarjeta.class.getName());
            }
        }

    }

}

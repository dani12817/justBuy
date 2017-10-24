package jsf;

import jpa.entity.Pagopaypal;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.PagopaypalFacade;

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

@Named("pagopaypalController")
@SessionScoped
public class PagopaypalController implements Serializable {

    private Pagopaypal current;
    private DataModel items = null;
    @EJB
    private jpa.session.PagopaypalFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private Pagopaypal newPagoPorPaypal;
    private Pagopaypal editPagoPorPaypal;
    
    private boolean metodoElegido = false;
    List<Pagopaypal> listaPagoPaypal;
    private final String FacesRedirect = "?faces-redirect=true";

    public PagopaypalController() {
    }

    public List<Pagopaypal> getListaPagoPaypal() {
        return listaPagoPaypal;
    }

    public void setListaPagoPaypal(List<Pagopaypal> listaPagoPaypal) {
        this.listaPagoPaypal = listaPagoPaypal;
    }

    public boolean isMetodoElegido() {
        return metodoElegido;
    }

    public void setMetodoElegido(boolean metodoElegido) {
        this.metodoElegido = metodoElegido;
    }

    public Pagopaypal getNewPagoPorPaypal() {
        return newPagoPorPaypal;
    }

    public void setNewPagoPorPaypal(Pagopaypal newPagoPorPaypal) {
        this.newPagoPorPaypal = newPagoPorPaypal;
    }

    public Pagopaypal getEditPagoPorPaypal() {
        return editPagoPorPaypal;
    }

    public void setEditPagoPorPaypal(Pagopaypal editPagoPorPaypal) {
        this.editPagoPorPaypal = editPagoPorPaypal;
    }

    ///////////////////Mis Metodos//////////////////////////////
    
    public void montarListaPaypal(Usuario usuarioLogin){
        listaPagoPaypal = ejbFacade.buscarPorUsuario(usuarioLogin);
        System.out.println("Paypal "+listaPagoPaypal.size());
    }
    
    public String nuevoMetodoPago(){
        
        newPagoPorPaypal = new Pagopaypal();
        metodoElegido = true;
        System.out.println("Nuevo Paypal");
        
        return "cuentaMetodoPagoNew"+FacesRedirect;
    }
    
    public String crearMetodoPago(Usuario usuarioLogin){
        
        newPagoPorPaypal.setUsuario(usuarioLogin);
        
        ejbFacade.create(newPagoPorPaypal);
        
        newPagoPorPaypal = null;
        metodoElegido = false;
        
        return "cuentaMetodoPago"+FacesRedirect;
    }
    
    public String cargarMetodoPago(int idPagoPaypal){
        
        editPagoPorPaypal = ejbFacade.find(idPagoPaypal);
        metodoElegido = true;
        
        return "cuentaMetodoPagoEdit"+FacesRedirect;
    }
    
    public String editarMetodoPago(){
        
        ejbFacade.edit(editPagoPorPaypal);
        
        editPagoPorPaypal = null;
        metodoElegido = false;
        
        return "cuentaMetodoPago"+FacesRedirect;
    }
    
    ///////////////////////////////////////////////////////////

    public Pagopaypal getSelected() {
        if (current == null) {
            current = new Pagopaypal();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PagopaypalFacade getFacade() {
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
        current = (Pagopaypal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Pagopaypal();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagopaypalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Pagopaypal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagopaypalUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Pagopaypal) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagopaypalDeleted"));
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

    public Pagopaypal getPagopaypal(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pagopaypal.class)
    public static class PagopaypalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PagopaypalController controller = (PagopaypalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pagopaypalController");
            return controller.getPagopaypal(getKey(value));
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
            if (object instanceof Pagopaypal) {
                Pagopaypal o = (Pagopaypal) object;
                return getStringKey(o.getIdpagoPaypal());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Pagopaypal.class.getName());
            }
        }

    }

}

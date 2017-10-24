package jsf;

import jpa.entity.Envio;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.EnvioFacade;

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
import jpa.entity.Tienda;

@Named("envioController")
@SessionScoped
public class EnvioController implements Serializable {

    private Envio current;
    private DataModel items = null;
    @EJB
    private jpa.session.EnvioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<Envio> listaEnviosTienda;
    
    private Envio envioActual;

    private final String FacesRedirect = "?faces-redirect=true";

    public EnvioController() {
    }

    public Envio getEnvioActual() {
        return envioActual;
    }

    public void setEnvioActual(Envio envioActual) {
        this.envioActual = envioActual;
    }

    public List<Envio> getListaEnviosTienda() {
        return listaEnviosTienda;
    }

    public void setListaEnviosTienda(List<Envio> listaEnviosTienda) {
        this.listaEnviosTienda = listaEnviosTienda;
    }

    ///////////////////Mis Metodos//////////////////////////////  
    
    public boolean isTiendaConProductos(Tienda miTienda){        
        listaEnviosTienda = ejbFacade.findPorTienda(miTienda);        
        return listaEnviosTienda.isEmpty();
    }

    public String cargarEnvio(boolean nuevoEnvio, Envio envio) {

        if (nuevoEnvio) {
            envioActual = new Envio();
            return "opcionesEnvioTiendaNew" + FacesRedirect;
        } else {
            envioActual = envio;
            return "opcionesEnvioTiendaEdit" + FacesRedirect;
        }

    }

    public String crearEnvio(Tienda miTienda) {
        
        envioActual.setTienda(miTienda);
        
        ejbFacade.create(envioActual);

        return "opcionesEnvioTienda" + FacesRedirect;
    }

    public String editarEnvio() {
        
        ejbFacade.edit(envioActual);
        
        return "opcionesEnvioTienda" + FacesRedirect;
    }

    //////////////////////////////////////////////////////////// 
    public Envio getSelected() {
        if (current == null) {
            current = new Envio();
            selectedItemIndex = -1;
        }
        return current;
    }

    public EnvioFacade getFacade() {
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
        current = (Envio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Envio();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EnvioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Envio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EnvioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Envio) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EnvioDeleted"));
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

    public Envio getEnvio(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Envio.class)
    public static class EnvioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EnvioController controller = (EnvioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "envioController");
            return controller.getEnvio(getKey(value));
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
            if (object instanceof Envio) {
                Envio o = (Envio) object;
                return getStringKey(o.getIdenvio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Envio.class.getName());
            }
        }

    }

}

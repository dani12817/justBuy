package jsf;

import jpa.entity.Mensaje;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.MensajeFacade;

import java.io.Serializable;
import java.util.Date;
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
import jpa.entity.*;

@Named("mensajeController")
@SessionScoped
public class MensajeController implements Serializable {

    private Mensaje current;
    private DataModel items = null;
    @EJB
    private jpa.session.MensajeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private final String FacesRedirect = "?faces-redirect=true";

    private List<Mensaje> listaMensajes;
    private Mensaje mensajeActual;

    private Producto productoDelMensaje;
    private boolean responderMensaje;

    public MensajeController() {
    }

    public boolean isResponderMensaje() {
        return responderMensaje;
    }

    public void setResponderMensaje(boolean responderMensaje) {
        this.responderMensaje = responderMensaje;
    }

    public Producto getProductoDelMensaje() {
        return productoDelMensaje;
    }

    public void setProductoDelMensaje(Producto productoDelMensaje) {
        this.productoDelMensaje = productoDelMensaje;
    }

    public Mensaje getMensajeActual() {
        return mensajeActual;
    }

    public void setMensajeActual(Mensaje mensajeActual) {
        this.mensajeActual = mensajeActual;
    }

    public List<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    ///////////////////Mis Metodos//////////////////////////////
    public String cargarMensajesRecibidos(Usuario usuario) {
        responderMensaje = true;
        listaMensajes = ejbFacade.encontrarMensajesRecibidos(usuario);

        return "/mainPages/infoCuenta/cuentaMensajes/cuentaMensajesRecibidos" + FacesRedirect;
    }

    public String cargarMensajesEnviados(Usuario usuario) {
        responderMensaje = false;
        listaMensajes = ejbFacade.encontrarMensajesEnviados(usuario);

        return "/mainPages/infoCuenta/cuentaMensajes/cuentaMensajesEnviados" + FacesRedirect;
    }

    public String verMensaje(Mensaje mensaje) {

        mensajeActual = mensaje;

        return "verMensaje" + FacesRedirect;
    }

    public String prepararMensajeNuevo() {
        mensajeActual = new Mensaje();

        return "newMessage" + FacesRedirect;
    }

    public String prepararMensajePedido() {
        mensajeActual = new Mensaje();

        return "mensajePedido" + FacesRedirect;
    }

    public String enviarMensajePedido(Pedido pedido, ProductoController pc) {
        mensajeActual.setDestinatario(pedido.getTienda().getUsuario());
        mensajeActual.setRemitente(pedido.getUsuario());
        mensajeActual.setFecha(new Date());
        mensajeActual.setAsunto("[PEDIDO #" + pedido.getIdpedido() + "] " + mensajeActual.getAsunto());
        mensajeActual.setProducto(pc.getFacade().find(0));

        ejbFacade.create(mensajeActual);

        mensajeActual = null;

        return "verPedido" + FacesRedirect;
    }

    public String enviarMensaje(Usuario remitente, Usuario destinatario) {
        mensajeActual.setDestinatario(destinatario);
        mensajeActual.setRemitente(remitente);
        mensajeActual.setFecha(new Date());

        mensajeActual.setProducto(productoDelMensaje);

        ejbFacade.create(mensajeActual);

        mensajeActual = null;

        return cargarMensajesEnviados(remitente);
    }

    public String cargarResponder(Usuario remitente, Usuario destinatario, Producto producto) {
        mensajeActual = new Mensaje();
        mensajeActual.setRemitente(remitente);
        mensajeActual.setDestinatario(destinatario);
        mensajeActual.setProducto(producto);
        
        mensajeActual.setFecha(new Date());
        
        return "responderMensaje" + FacesRedirect;
    }

    public String responderMensaje() {
        
        ejbFacade.create(mensajeActual);
        
        return cargarMensajesEnviados(mensajeActual.getRemitente());
    }

    ///////////////////////////////////////////////////////////
    public Mensaje getSelected() {
        if (current == null) {
            current = new Mensaje();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MensajeFacade getFacade() {
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
        current = (Mensaje) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Mensaje();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Mensaje) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Mensaje) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeDeleted"));
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

    public Mensaje getMensaje(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Mensaje.class)
    public static class MensajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MensajeController controller = (MensajeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mensajeController");
            return controller.getMensaje(getKey(value));
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
            if (object instanceof Mensaje) {
                Mensaje o = (Mensaje) object;
                return getStringKey(o.getIdmensaje());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Mensaje.class.getName());
            }
        }

    }

}

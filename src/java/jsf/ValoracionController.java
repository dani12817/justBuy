package jsf;

import jpa.entity.Valoracion;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.ValoracionFacade;

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

@Named("valoracionController")
@SessionScoped
public class ValoracionController implements Serializable {

    private Valoracion current;
    private DataModel items = null;
    @EJB
    private jpa.session.ValoracionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private final String FacesRedirect = "?faces-redirect=true";

    private List<Valoracion> listaValoracionTienda;
    private Valoracion valoracionUsuario;
    private Pedido pedidoValoracion;

    public ValoracionController() {
    }

    public List<Valoracion> getListaValoracionTienda() {
        return listaValoracionTienda;
    }

    public void setListaValoracionTienda(List<Valoracion> listaValoracionTienda) {
        this.listaValoracionTienda = listaValoracionTienda;
    }

    public Valoracion getValoracionUsuario() {
        return valoracionUsuario;
    }

    public void setValoracionUsuario(Valoracion valoracionUsuario) {
        this.valoracionUsuario = valoracionUsuario;
    }

    ///////////////////Mis Metodos//////////////////////////////
    public int getMediaDeValoracion(Tienda miTienda) {
        int valoracionMedia = 0;

        listaValoracionTienda = ejbFacade.getValoracionesTienda(miTienda);

        if (listaValoracionTienda.isEmpty()) {
            return 0;
        } else {
            for (int i = 0; i < listaValoracionTienda.size(); i++) {
                valoracionMedia += listaValoracionTienda.get(i).getPuntuacion();
            }
        }

        return valoracionMedia / listaValoracionTienda.size();
    }

    public String cargarValoracion(Tienda tienda, Usuario usuarioLogin, Pedido pedido) {
        valoracionUsuario = new Valoracion();

        pedidoValoracion = pedido;

        valoracionUsuario.setTienda(tienda);
        valoracionUsuario.setUsuario(usuarioLogin);

        return "valorarPedido" + FacesRedirect;
    }

    public String hacerValoracion(PedidoController pc) {

        if (checkValoracionValida()) {
            valoracionUsuario.setFecha(new Date());
            getFacade().create(valoracionUsuario);
            pedidoValoracion.setEstado(6);
            pc.getFacade().edit(pedidoValoracion);
        }

        return "verPedido" + FacesRedirect;

    }

    public boolean checkValoracionValida() {

        if (valoracionUsuario != null) {
            if (pedidoValoracion != null) {
                if (pedidoValoracion.getEstado() < 6) {
                    return true;
                }
            }
        }

        return false;
    }

    ////////////////////////////////////////////////////////////
    public Valoracion getSelected() {
        if (current == null) {
            current = new Valoracion();
            selectedItemIndex = -1;
        }
        return current;
    }

    public ValoracionFacade getFacade() {
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
        current = (Valoracion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Valoracion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValoracionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Valoracion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValoracionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Valoracion) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValoracionDeleted"));
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

    public Valoracion getValoracion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Valoracion.class)
    public static class ValoracionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ValoracionController controller = (ValoracionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "valoracionController");
            return controller.getValoracion(getKey(value));
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
            if (object instanceof Valoracion) {
                Valoracion o = (Valoracion) object;
                return getStringKey(o.getIdvaloracion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Valoracion.class.getName());
            }
        }

    }

}

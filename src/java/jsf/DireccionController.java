package jsf;

import jpa.entity.Direccion;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.DireccionFacade;

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

@Named("direccionController")
@SessionScoped
public class DireccionController implements Serializable {

    private Direccion current;
    private DataModel items = null;
    @EJB
    private jpa.session.DireccionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private final String FacesRedirect = "?faces-redirect=true";

    /**
     * **Direcciones Usuarios***
     */
    private Direccion direccionEditar;
    private List<Direccion> direccionesUsuario;
    private int numeroPostalDireccionEdit;

    private Direccion nuevaDireccion;

    public DireccionController() {
    }

    public int getNumeroPostalDireccionEdit() {
        return numeroPostalDireccionEdit;
    }

    public void setNumeroPostalDireccionEdit(int numeroPostalDireccionEdit) {
        this.numeroPostalDireccionEdit = numeroPostalDireccionEdit;
    }

    public Direccion getDireccionEditar() {
        return direccionEditar;
    }

    public void setDireccionEditar(Direccion direccionEditar) {
        this.direccionEditar = direccionEditar;
    }

    public List<Direccion> getDireccionesUsuario() {
        return direccionesUsuario;
    }

    public void setDireccionesUsuario(List<Direccion> direccionesUsuario) {
        this.direccionesUsuario = direccionesUsuario;
    }

    public Direccion getNuevaDireccion() {
        return nuevaDireccion;
    }

    public void setNuevaDireccion(Direccion nuevaDireccion) {
        this.nuevaDireccion = nuevaDireccion;
    }

    /**
     * *********Mis metodos*****************
     */
    public boolean isListaDireccionesVacia(Usuario usuarioLogin) {
        direccionesUsuario = ejbFacade.getDireccionesPorUsuario(usuarioLogin);
        //System.out.println("Direcciones > " + direccionesUsuario.size());
        
        return direccionesUsuario.isEmpty();
    }

    public String cargarEditarDireccion(int idDireccion) {

        direccionEditar = ejbFacade.find(idDireccion);
        numeroPostalDireccionEdit = direccionEditar.getCodPostal().getIdcodPostal();

        System.out.println("Direccion Seleccionada -> " + direccionEditar.getIddireccion());

        return "cuentaDireccionEdit" + FacesRedirect;
    }

    public String editarDireccion(CodpostalController codpostalController) {

        direccionEditar.setCodPostal(codpostalController.getCodpostal(numeroPostalDireccionEdit));

        ejbFacade.edit(direccionEditar);

        direccionEditar = null;

        return "cuentaDireccion" + FacesRedirect;
    }

    public String cargarCrearDireccion() {

        nuevaDireccion = new Direccion();

        System.out.println("Nueva dirección");

        return "cuentaDireccionNew" + FacesRedirect;
    }

    public String crearDireccion(CodpostalController codpostalController, Usuario usuarioLogin) {

        nuevaDireccion.setCodPostal(codpostalController.getCodpostal(numeroPostalDireccionEdit));
        nuevaDireccion.setUsuario(usuarioLogin);

        ejbFacade.create(nuevaDireccion);

        nuevaDireccion = null;

        return "cuentaDireccion" + FacesRedirect;
    }

    /**
     * ************************************
     */
    public Direccion getSelected() {
        if (current == null) {
            current = new Direccion();
            selectedItemIndex = -1;
        }
        return current;
    }

    public DireccionFacade getFacade() {
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
        current = (Direccion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Direccion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DireccionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Direccion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DireccionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Direccion) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DireccionDeleted"));
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

    public Direccion getDireccion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Direccion.class)
    public static class DireccionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DireccionController controller = (DireccionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "direccionController");
            return controller.getDireccion(getKey(value));
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
            if (object instanceof Direccion) {
                Direccion o = (Direccion) object;
                return getStringKey(o.getIddireccion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Direccion.class.getName());
            }
        }

    }

}

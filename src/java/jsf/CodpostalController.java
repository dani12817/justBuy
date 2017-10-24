package jsf;

import jpa.entity.Codpostal;
import jpa.entity.Ciudad;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.CodpostalFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.model.SelectItemGroup;
import jpa.entity.Provincia;

@Named("codpostalController")
@SessionScoped
public class CodpostalController implements Serializable {

    private Codpostal current;
    private DataModel items = null;
    @EJB
    private jpa.session.CodpostalFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Codpostal> listaCodPostal;
    
    private List<SelectItem> propvinciaCiudadCodPostal;

    public CodpostalController() {
    }

    public List<SelectItem> getPropvinciaCiudadCodPostal() {
        return propvinciaCiudadCodPostal;
    }

    public void setPropvinciaCiudadCodPostal(List<SelectItem> propvinciaCiudadCodPostal) {
        this.propvinciaCiudadCodPostal = propvinciaCiudadCodPostal;
    }

    public List<Codpostal> getListaCodPostal() {
        return listaCodPostal;
    }

    public void setListaCodPostal(List<Codpostal> listaCodPostal) {
        this.listaCodPostal = listaCodPostal;
    }

    ///////////////////Mis Metodos//////////////////////////////
    public String getPorNumPostal(String codPostalBuscar) {
        System.out.println("Codigo postal a buscar tiendas: " + codPostalBuscar);
        Codpostal codPostal = getFacade().getByNumeroPostal(codPostalBuscar);

        if (codPostal != null) {
            System.out.println("Encontrado Postal de " + codPostal.getCiudad());
            return codPostal.getCiudad().getNombre();
        }

        return "No encontrado";
    }
    
    public List<Codpostal> getCodPostalPorCiudad(Ciudad ciudadABuscar){
        
        listaCodPostal = ejbFacade.getByCiudad(ciudadABuscar);
        
        return listaCodPostal;
    }

    public List<SelectItem> montarlistaPropvinciaCiudadCodPostal(ProvinciaController provinciaController, CiudadController ciudadController) {
        List<Codpostal> codPostalPorCiudad;
        List<Ciudad> ciudadPorProvincia;
        List<Provincia> provincias = provinciaController.getProvincias();
        SelectItemGroup grupoItem;
        SelectItem[] elementosLista;

        propvinciaCiudadCodPostal = new ArrayList<>();

        for (int i = 0; i < provincias.size(); i++) {
            //System.out.println("Prov. -> " + provincias.get(i).getNombre());
            ciudadPorProvincia = ciudadController.getFacade().getByProvincia(provincias.get(i));

            for (int x = 0; x < ciudadPorProvincia.size(); x++) {
                grupoItem = new SelectItemGroup(provincias.get(i).getNombre()+" - "+ciudadPorProvincia.get(x).getNombre());

                codPostalPorCiudad = ejbFacade.getByCiudad(ciudadPorProvincia.get(x));
                elementosLista = new SelectItem[codPostalPorCiudad.size()];
                
                for (int j = 0; j < elementosLista.length; j++) {
                    elementosLista[j] = new SelectItem(codPostalPorCiudad.get(j).getIdcodPostal(), codPostalPorCiudad.get(j).getNumero());
                    //System.out.println("Ciu. -> " + ciudadPorProvincia.get(j).getNombre());
                }

                grupoItem.setSelectItems(elementosLista);

                propvinciaCiudadCodPostal.add(grupoItem);
            }
        }

        return propvinciaCiudadCodPostal;

    }

    ///////////////////////////////////////////////////////////
    public Codpostal getSelected() {
        if (current == null) {
            current = new Codpostal();
            selectedItemIndex = -1;
        }
        return current;
    }

    public CodpostalFacade getFacade() {
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
        current = (Codpostal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Codpostal();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CodpostalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Codpostal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CodpostalUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Codpostal) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CodpostalDeleted"));
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

    public Codpostal getCodpostal(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Codpostal.class)
    public static class CodpostalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CodpostalController controller = (CodpostalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "codpostalController");
            return controller.getCodpostal(getKey(value));
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
            if (object instanceof Codpostal) {
                Codpostal o = (Codpostal) object;
                return getStringKey(o.getIdcodPostal());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Codpostal.class.getName());
            }
        }

    }

}

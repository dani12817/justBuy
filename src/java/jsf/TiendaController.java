package jsf;

import jpa.entity.Tienda;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.TiendaFacade;

import java.io.Serializable;
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

import java.nio.file.Paths;
import java.nio.file.Path;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.*;
import java.util.Base64;
import java.io.InputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItemGroup;
import jpa.entity.*;

@Named("tiendaController")
@SessionScoped
public class TiendaController implements Serializable {

    private Tienda current;
    private DataModel items = null;
    @EJB
    private jpa.session.TiendaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private final String FacesRedirect = "?faces-redirect=true";
    private UploadedFile avatar = null;
    private String codPostalBuscar;
    private List<Tienda> busquedaTiendas;
    
    private boolean tiendaPendiente;
    private boolean hayTienda;

    private Tienda tiendaSolicitar = new Tienda(0, "", "", 0, 0, 0, Short.parseShort("0"), 0, "", "");
    private int numeroPostalTienda;
    
    private Tienda miTienda;
    private Tienda tiendaSeleccionada;
    
    private List<SelectItem> propvinciaCiudadCodPostal;

    public TiendaController() {
    }

    public Tienda getTiendaSeleccionada() {
        return tiendaSeleccionada;
    }

    public void setTiendaSeleccionada(Tienda tiendaSeleccionada) {
        this.tiendaSeleccionada = tiendaSeleccionada;
    }

    public Tienda getMiTienda() {
        return miTienda;
    }

    public void setMiTienda(Tienda miTienda) {
        this.miTienda = miTienda;
    }

    public void setHayTienda(Usuario usuario) {
        this.hayTienda = ejbFacade.hayTienda(usuario);
    }

    public boolean isHayTienda() {
        return hayTienda;
    }

    public boolean isTiendaPendiente() {
        return tiendaPendiente;
    }

    public void setTiendaPendiente(Usuario usuario) {
        this.tiendaPendiente = ejbFacade.tiendaPendiente(usuario);
    }
    
    public int getNumeroPostalTienda() {
        return numeroPostalTienda;
    }
    public void setNumeroPostalTienda(int numeroPostalTienda) {
        this.numeroPostalTienda = numeroPostalTienda;
    }

    public Tienda getTiendaSolicitar() {
        return tiendaSolicitar;
    }

    public void setTiendaSolicitar(Tienda tiendaSolicitar) {
        this.tiendaSolicitar = tiendaSolicitar;
    }

    public String getCodPostalBuscar() {
        return codPostalBuscar;
    }

    public void setCodPostalBuscar(String codPostalBuscar) {
        this.codPostalBuscar = codPostalBuscar;
    }

    public List<SelectItem> getPropvinciaCiudadCodPostal() {
        return propvinciaCiudadCodPostal;
    }

    public void setPropvinciaCiudadCodPostal(List<SelectItem> propvinciaCiudadCodPostal) {
        this.propvinciaCiudadCodPostal = propvinciaCiudadCodPostal;
    }

    public List<Tienda> getBusquedaTiendas() {
        return busquedaTiendas;
    }

    public void setBusquedaTiendas(List<Tienda> busquedaTiendas) {
        this.busquedaTiendas = busquedaTiendas;
    }

    public UploadedFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadedFile avatar) {
        this.avatar = avatar;
    }

    public String getAvatarImage(byte[] imgData) {

        return Base64.getEncoder().encodeToString(imgData);
    }    

    ///////////////////Mis Metodos//////////////////////////////   
    
    public String cargarTiendaBuscada(Tienda tienda, HorariotiendaController hc, EnvioController ec, ProductoController pc){
        tiendaSeleccionada = tienda;
        
        pc.setEnvioElegido(ec.getFacade().findUnEnvioPorTienda(tienda));
        
        hc.cargarHorarioTiendaSeleccionada(tienda);
        
        return "/mainPages/datosTienda" + FacesRedirect;
    }   
    
    public String cargarMiTienda(CategoriasdetiendaController categoriasdetiendaController){
        numeroPostalTienda = miTienda.getCodPostal().getIdcodPostal();
        
        categoriasdetiendaController.setListaCategoriasSelecionadas(categoriasdetiendaController.getFacade().findPorTienda(miTienda));
        
        /*for (Categoria listaCategoriasSelecionada : categoriasdetiendaController.getListaCategoriasSelecionadas()) {
            System.out.println("X -> " + listaCategoriasSelecionada.getNombre());
        }*/
        
        return "/mainPages/infoCuenta/menuMiTienda/miTiendaInfo" + FacesRedirect;
    }

    public String getTiendasPorCodPostal(CodpostalController codpostalController) {
        System.out.println("Codigo postal a buscar tiendas: " + codPostalBuscar);

        busquedaTiendas = getFacade().findByNickName(codpostalController.getFacade().getByNumeroPostal(codPostalBuscar));
        System.out.println("Econtradas " + busquedaTiendas.size());

        return "mainPages/buscarTiendas" + FacesRedirect;
    }

    public List<SelectItem> montarlistaPropvinciaCiudadCodPostal(ProvinciaController provinciaController, CiudadController ciudadController, CodpostalController codpostalController) {
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

                codPostalPorCiudad = codpostalController.getFacade().getByCiudad(ciudadPorProvincia.get(x));
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
    
    public String solicitarNuevaTienda(CodpostalController codpostalController, Usuario usuariologin, CategoriasdetiendaController categoriasdetiendaController){
        categoriasdetiendaController.setErrorCategorias("");
        
        if(categoriasdetiendaController.getListaCategoriasSelecionadas().length == 0){
            categoriasdetiendaController.setErrorCategorias("Hay que Seleccionar al menos una categoria");
            return "miTienda" + FacesRedirect;
        }
        
        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                tiendaSolicitar.setLogo(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                Path path = Paths.get("../images/no-logo.jpg");
                byte[] avatarBytes = Files.readAllBytes(path);
                tiendaSolicitar.setLogo(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Postal Introducido -> "+numeroPostalTienda);
        
        tiendaSolicitar.setCodPostal(codpostalController.getCodpostal(numeroPostalTienda));
        
        tiendaSolicitar.setUsuario(usuariologin);

        try {
            ejbFacade.createTienda(tiendaSolicitar);
            hayTienda = ejbFacade.hayTienda(usuariologin);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TiendaCreated"));
            
            categoriasdetiendaController.ponerCategoriasALaTienda(ejbFacade.findPorUsuario(usuariologin));
            
            return "miTienda" + FacesRedirect;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String editarTienda(CodpostalController codpostalController, CategoriasdetiendaController categoriasdetiendaController){
        categoriasdetiendaController.setErrorCategorias("");
        
        Categoria[] categoriasSeleccionadas = categoriasdetiendaController.getListaCategoriasSelecionadas();
        if(categoriasSeleccionadas.length == 0){
            categoriasdetiendaController.setErrorCategorias("Hay que Seleccionar al menos una categoria");
            return "miTiendaInfo" + FacesRedirect;
        }
        
        System.out.println("Avatar");
        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                miTienda.setLogo(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        miTienda.setCodPostal(codpostalController.getCodpostal(numeroPostalTienda)); 
        
        ejbFacade.edit(miTienda);
        System.out.println("Editada Tienda");
        
        categoriasdetiendaController.getFacade().editarCategoriasDeTienda(categoriasSeleccionadas, miTienda);
        System.out.println("Editado Categorias");
        
        return "miTiendaInfo" + FacesRedirect;
    }

    ///////////////////////******************************////////////////////////////////////
    public Tienda getSelected() {
        if (current == null) {
            current = new Tienda();
            selectedItemIndex = -1;
        }
        return current;
    }

    public TiendaFacade getFacade() {
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
        current = (Tienda) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tienda();
        selectedItemIndex = -1;
        return "Create";
    }

    /////////////////////////
    public String create() {
        System.out.println("Creando tienda " + current.getNombre());

        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                current.setLogo(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                Path path = Paths.get("../images/no-logo.jpg");
                byte[] avatarBytes = Files.readAllBytes(path);
                current.setLogo(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TiendaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Tienda) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /////////////////////////
    public String update() {
        System.out.println("Modificando tienda " + current.getNombre());
        try {
            if (!avatar.getFileName().isEmpty()) {
                try {
                    InputStream is = avatar.getInputstream();
                    byte[] avatarBytes = IOUtils.toByteArray(is);
                    current.setLogo(avatarBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TiendaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Tienda) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TiendaDeleted"));
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

    public Tienda getTienda(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Tienda.class)
    public static class TiendaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TiendaController controller = (TiendaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tiendaController");
            return controller.getTienda(getKey(value));
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
            if (object instanceof Tienda) {
                Tienda o = (Tienda) object;
                return getStringKey(o.getIdtienda());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tienda.class.getName());
            }
        }

    }

}

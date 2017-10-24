package jsf;

import java.io.IOException;
import java.io.InputStream;
import jpa.entity.Producto;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.ProductoFacade;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import classes.carritoDeLaCompra;
import java.util.ArrayList;
import jpa.entity.Envio;

@Named("productoController")
@SessionScoped
public class ProductoController implements Serializable {

    private Producto current;
    private DataModel items = null;
    @EJB
    private jpa.session.ProductoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Producto> listaProductosTienda;

    private final String FacesRedirect = "?faces-redirect=true";
    private Producto productoActual;
    private boolean productoDisponibilidad;
    private UploadedFile avatar = null;
    private List<carritoDeLaCompra> carritoDeLaCompra = new ArrayList<>();
    private int productoEnCarritoPos;
    private Tienda carritoDeLaCompraTienda;
    private double totalDelCarrito;
    private Envio envioElegido;

    private List<SelectItem> listaProductos;

    private String currentCategory = "";

    private boolean errorCarritoTienda = false;

    private int categoriaProducto;
    private String productoTiendaBuscar = "";

    public ProductoController() {
    }

    public String getProductoTiendaBuscar() {
        return productoTiendaBuscar;
    }

    public void setProductoTiendaBuscar(String productoTiendaBuscar) {
        this.productoTiendaBuscar = productoTiendaBuscar;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Envio getEnvioElegido() {
        return envioElegido;
    }

    public void setEnvioElegido(Envio envioElegido) {
        this.envioElegido = envioElegido;
    }

    public double getTotalDelCarrito() {
        return totalDelCarrito;
    }

    public void setTotalDelCarrito(double totalDelCarrito) {
        this.totalDelCarrito = totalDelCarrito;
    }

    public boolean isErrorCarritoTienda() {
        return errorCarritoTienda;
    }

    public void setErrorCarritoTienda(boolean errorCarritoTienda) {
        this.errorCarritoTienda = errorCarritoTienda;
    }

    public List<carritoDeLaCompra> getCarritoDeLaCompra() {
        return carritoDeLaCompra;
    }

    public void setCarritoDeLaCompra(List<carritoDeLaCompra> carritoDeLaCompra) {
        this.carritoDeLaCompra = carritoDeLaCompra;
    }

    public Producto getSelected() {
        if (current == null) {
            current = new Producto();
            selectedItemIndex = -1;
        }
        return current;
    }

    public int getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(int categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public boolean isProductoDisponibilidad() {
        return productoDisponibilidad;
    }

    public void setProductoDisponibilidad(boolean productoDisponibilidad) {
        this.productoDisponibilidad = productoDisponibilidad;
    }

    public Producto getProductoActual() {
        return productoActual;
    }

    public void setProductoActual(Producto productoActual) {
        this.productoActual = productoActual;
    }

    public ProductoFacade getFacade() {
        return ejbFacade;
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

    public List<Producto> getListaProductosTienda() {
        return listaProductosTienda;
    }

    public void setListaProductosTienda(List<Producto> listaProductosTienda) {
        this.listaProductosTienda = listaProductosTienda;
    }

    public boolean isTiendaConProductos(Tienda tienda) {
        listaProductosTienda = ejbFacade.listaProductosTienda(tienda);

        return !listaProductosTienda.isEmpty();
    }

    ///////////////////Mis Metodos//////////////////////////////
    public void addProductoAlCarrito(Producto producto) {
        if (carritoDeLaCompra.isEmpty()) {
            carritoDeLaCompra.add(new carritoDeLaCompra(producto, 1));
            carritoDeLaCompraTienda = producto.getTienda();
        } else {
            if (carritoDeLaCompraTienda.getIdtienda() != producto.getTienda().getIdtienda()) {
                errorCarritoTienda = true;
                System.out.println("No se puede");
            } else {
                if (productoYaEnLaLista(producto)) {
                    carritoDeLaCompra.get(productoEnCarritoPos).subirCantidad();
                } else {
                    carritoDeLaCompra.add(new carritoDeLaCompra(producto, 1));
                }
            }
        }

    }
    
    public String irACategoria(String categoria){
        productoTiendaBuscar = "";
        return "productosTienda.xhtml#"+categoria;
    }

    public void quitarProductoCarrito(carritoDeLaCompra producto) {

        /*for (int i = 0; i < carritoDeLaCompra.size(); i++) {
            if(carritoDeLaCompra.get(i).getProdcuto().getIdproducto() == producto.getIdproducto()){
                carritoDeLaCompra.remove(i);
                break;
            }
        }*/
        
        carritoDeLaCompra.remove(producto);
        
        
    }

    private boolean productoYaEnLaLista(Producto producto) {
        for (int i = 0; i < carritoDeLaCompra.size(); i++) {
            if (carritoDeLaCompra.get(i).getProdcuto().getIdproducto() == producto.getIdproducto()) {
                productoEnCarritoPos = i;
                return true;
            }
        }
        return false;
    }

    public void quitarProductoDelCarrito(Producto producto) {
        for (int i = 0; i < carritoDeLaCompra.size(); i++) {
            if (carritoDeLaCompra.get(i).getProdcuto().getIdproducto() == producto.getIdproducto()) {
                carritoDeLaCompra.remove(i);
                if (carritoDeLaCompra.isEmpty()) {
                    carritoDeLaCompraTienda = null;
                }
                break;
            }
        }
    }

    public double calcularTotalDelCarrito() {
        totalDelCarrito = 0.0;
        double totalProductos = 0.0;

        for (int i = 0; i < carritoDeLaCompra.size(); i++) {
            totalProductos += carritoDeLaCompra.get(i).getTotal();
        }
        totalDelCarrito = totalProductos + envioElegido.getPrecioEnvio();

        return totalProductos;
    }

    public String cargarProdcuto(boolean newProducto, Producto productoEdit) {

        if (newProducto) {
            productoActual = new Producto();
            productoDisponibilidad = true;
            categoriaProducto = 1;
            return "productosTiendaNew" + FacesRedirect;
        } else {
            productoActual = productoEdit;
            productoDisponibilidad = (productoActual.getDisponible() != 0);
            categoriaProducto = productoActual.getCategoria().getIdcategoria();
            return "productosTiendaEdit" + FacesRedirect;
        }

    }

    public String editarProducto() {
        System.out.println("qwe");
        if (productoDisponibilidad) {
            productoActual.setDisponible(Short.parseShort("1"));
        } else {
            productoActual.setDisponible(Short.parseShort("0"));
        }
        System.out.println("asd");
        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                productoActual.setImagen(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("zxc");
        ejbFacade.edit(productoActual);

        return "productosTienda" + FacesRedirect;
    }

    public String crearProducto(Tienda tienda) {
        
        if (productoDisponibilidad) {
            productoActual.setDisponible(Short.parseShort("1"));
        } else {
            productoActual.setDisponible(Short.parseShort("0"));
        }

        productoActual.setTienda(tienda);
        

        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                productoActual.setImagen(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                Path path = Paths.get("../images/no-logo.jpg");
                byte[] avatarBytes = Files.readAllBytes(path);
                productoActual.setImagen(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ejbFacade.create(productoActual);

        return "productosTienda" + FacesRedirect;
    }

    public List<Producto> montarlistaProductos(Tienda miTienda) {

        if(productoTiendaBuscar.isEmpty()){
            System.out.println("Normal");
            return ejbFacade.listaProductosTienda(miTienda);
        }else{
            System.out.println("Personalizada");
            return ejbFacade.listaProductosTienda(miTienda,productoTiendaBuscar);
        }
    }

    public List<SelectItem> montarlistaProdcutosTienda(Tienda miTienda) {
        List<Producto> productosTienda;

        productosTienda = ejbFacade.listaProductosTienda(miTienda);

        listaProductos = new ArrayList<>();

        for (int i = 0; i < productosTienda.size(); i++) {
            listaProductos.add(new SelectItem(productosTienda.get(i), productosTienda.get(i).getNombre()));
            System.out.println("Producto -> " + productosTienda.get(i));
        }

        return listaProductos;
    }

    ////////////////////////////////////////////////////////////   
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
        current = (Producto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Producto();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                current.setImagen(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                Path path = Paths.get("../images/no-logo.jpg");
                byte[] avatarBytes = Files.readAllBytes(path);
                current.setImagen(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Producto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (!avatar.getFileName().isEmpty()) {
                try {
                    InputStream is = avatar.getInputstream();
                    byte[] avatarBytes = IOUtils.toByteArray(is);
                    current.setImagen(avatarBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Producto) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProductoDeleted"));
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

    public Producto getProducto(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Producto.class)
    public static class ProductoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductoController controller = (ProductoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productoController");
            return controller.getProducto(getKey(value));
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
            if (object instanceof Producto) {
                Producto o = (Producto) object;
                return getStringKey(o.getIdproducto());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Producto.class.getName());
            }
        }

    }

}

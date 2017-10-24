package jsf;

import classes.carritoDeLaCompra;
import jpa.entity.Pedido;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.PedidoFacade;

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

@Named("pedidoController")
@SessionScoped
public class PedidoController implements Serializable {

    private Pedido current;
    private DataModel items = null;
    @EJB
    private jpa.session.PedidoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Tienda tiendaPedido;
    private Usuario usuarioPedido;

    private Direccion pedidoDireccion;
    private Direccion newPedidoDireccion;
    private Pagopaypal pagoPaypalPedido;
    private Pagopaypal newPagoPaypalPedido;
    private Pagotarjeta pagoTargetaPedido;
    private Pagotarjeta newPagoTargetaPedido;
    private Envio envioPedido;

    private final String FacesRedirect = "?faces-redirect=true";

    private List<Pedido> listaPedidos;
    private Pedido pedidoSeleccionado;
    private List<Lineapedido> productosPedido;

    private String[] pedidoEnvio;

    public PedidoController() {
    }

    public List<Lineapedido> getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(List<Lineapedido> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public Direccion getNewPedidoDireccion() {
        return newPedidoDireccion;
    }

    public void setNewPedidoDireccion(Direccion newPedidoDireccion) {
        this.newPedidoDireccion = newPedidoDireccion;
    }

    public Pagopaypal getNewPagoPaypalPedido() {
        return newPagoPaypalPedido;
    }

    public void setNewPagoPaypalPedido(Pagopaypal newPagoPaypalPedido) {
        this.newPagoPaypalPedido = newPagoPaypalPedido;
    }

    public Pagotarjeta getNewPagoTargetaPedido() {
        return newPagoTargetaPedido;
    }

    public void setNewPagoTargetaPedido(Pagotarjeta newPagoTargetaPedido) {
        this.newPagoTargetaPedido = newPagoTargetaPedido;
    }

    public Direccion getPedidoDireccion() {
        return pedidoDireccion;
    }

    public void setPedidoDireccion(Direccion pedidoDireccion) {
        this.pedidoDireccion = pedidoDireccion;
    }

    public Pagopaypal getPagoPaypalPedido() {
        return pagoPaypalPedido;
    }

    public void setPagoPaypalPedido(Pagopaypal pagoPaypalPedido) {
        this.pagoPaypalPedido = pagoPaypalPedido;
    }

    public Pagotarjeta getPagoTargetaPedido() {
        return pagoTargetaPedido;
    }

    public void setPagoTargetaPedido(Pagotarjeta pagoTargetaPedido) {
        this.pagoTargetaPedido = pagoTargetaPedido;
    }

    public Envio getEnvioPedido() {
        return envioPedido;
    }

    public void setEnvioPedido(Envio envioPedido) {
        this.envioPedido = envioPedido;
    }

    public Tienda getTiendaPedido() {
        return tiendaPedido;
    }

    public void setTiendaPedido(Tienda tiendaPedido) {
        this.tiendaPedido = tiendaPedido;
    }

    public Usuario getUsuarioPedido() {
        return usuarioPedido;
    }

    public void setUsuarioPedido(Usuario usuarioPedido) {
        this.usuarioPedido = usuarioPedido;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
    }

    ///////////////////Mis Metodos////////////////////////////// 
    public String prepararPedidos(Object object, boolean usuario) {
        if (usuario) {
            listaPedidos = ejbFacade.pedidosPorUsuario((Usuario) object);
            return "/mainPages/infoCuenta/cuentaPedido/cuentaPedido" + FacesRedirect;
        } else {
            listaPedidos = ejbFacade.pedidosPorTienda((Tienda) object);
            return "/mainPages/infoCuenta/menuMiTienda/miTiendaPedido/pedidosTienda" + FacesRedirect;
        }
    }
    
    public boolean recogidaEnTienda(String informacion){
        System.out.println(informacion.split("<*-*>", 2)[0]);
        return informacion.split("<*-*>", 2)[0].equals("0");
    }

    public boolean isListaPedidosVacia(Usuario usuarioLogin) {
        listaPedidos = ejbFacade.pedidosPorUsuario(usuarioLogin);
        System.out.println("Pedidos > " + listaPedidos.size());

        return listaPedidos.isEmpty();
    }

    public boolean isListaPedidosVaciaTienda(Tienda usuarioTienda) {
        listaPedidos = ejbFacade.pedidosPorTienda(usuarioTienda);
        System.out.println("Pedidos Tienda > " + listaPedidos.size());

        return listaPedidos.isEmpty();
    }

    public void setEstadoPedido(Pedido pedido, int estadoActual) {
        pedido.setEstado(estadoActual);
        ejbFacade.edit(pedido);
    }

    public String getEstadoPedido(int estado) {
        switch (estado) {
            case 1:
                return "Realizado y Pagado";
            case 2:
                return "Enviado";
            case 3:
                return "Listo para recoger en tienda";
            case 4:
                return "El cliente ha marcado como 'Recibido'";
            case 5:
                return "El pedido se ha cancelado";
            case 6:
                return "Valoración enviada";
            default:
                return "";
        }
    }

    public String cargarPedido(Pedido pedido, LineapedidoController lc) {
        pedidoSeleccionado = pedido;

        productosPedido = lc.getFacade().productosDeUnPedido(pedido);

        return "verPedido" + FacesRedirect;
    }
    
    public String separarInformacion(String informacion){
        
        return informacion.split("<*-*>", 2)[1];
    }

    public String cargarPedidoTienda(Pedido pedido, LineapedidoController lc) {
        pedidoSeleccionado = pedido;

        pedidoEnvio = pedidoSeleccionado.getInformacion().split("<*-*>", 2);
        if (pedidoEnvio.length > 1) {
            pedidoSeleccionado.setInformacion(pedidoEnvio[1]);
        } else {
            pedidoSeleccionado.setInformacion("");
        }

        productosPedido = lc.getFacade().productosDeUnPedido(pedido);

        return "verPedidoTienda" + FacesRedirect;
    }
    
    public String actualizarInformacion(){
        
        pedidoSeleccionado.setInformacion(pedidoEnvio[0]+"<*-*>"+pedidoSeleccionado.getInformacion());
        
        ejbFacade.edit(pedidoSeleccionado);
        
        return "verPedidoTienda";
    }

    public String procesoCarrito(Envio envioPedido, Tienda tienda, Usuario usuario) {
        this.envioPedido = envioPedido;
        this.tiendaPedido = tienda;
        this.usuarioPedido = usuario;

        return "procesoCarrito/pedidoCarrito" + FacesRedirect;
    }

    public String nextPedidoDireccion() {
        newPedidoDireccion = new Direccion();

        return "pedidoDireccion" + FacesRedirect;
    }

    public String establecerPedidoDireccion(boolean nuevaDireccion, DireccionController dc) {

        if (nuevaDireccion) {
            dc.getFacade().create(newPedidoDireccion);
            pedidoDireccion = dc.getFacade().getnuevaDireccionPedido(newPedidoDireccion);
            newPedidoDireccion = new Direccion();
        }

        return nextPedidoEnvio();
    }

    public String nextPedidoEnvio() {

        if (pedidoDireccion == null) {
            return "";
        }

        return "pedidoEnvio" + FacesRedirect;
    }

    public String establecerPagoTarjeta(PagotarjetaController pc) {

        pc.getFacade().create(newPagoTargetaPedido);
        pagoTargetaPedido = pc.getFacade().getNuevaTarjetaPedido(newPagoTargetaPedido);
        newPagoTargetaPedido = new Pagotarjeta();

        return nextPedidoResumen();
    }

    public String establecerPayPal(PagopaypalController pc) {

        pc.getFacade().create(newPagoPaypalPedido);
        pagoPaypalPedido = pc.getFacade().getNuevaPaypalPedido(newPagoPaypalPedido);
        newPagoPaypalPedido = new Pagopaypal();

        return nextPedidoResumen();
    }

    public String nextPedidoPago(ProductoController pc) {

        pc.setEnvioElegido(envioPedido);

        newPagoPaypalPedido = new Pagopaypal();
        newPagoTargetaPedido = new Pagotarjeta();

        return "pedidoPago" + FacesRedirect;
    }

    public String nextPedidoResumen() {

        return "pedidoResumen" + FacesRedirect;
    }

    public String tramitarPedido(List<carritoDeLaCompra> carrito, double total, LineapedidoController lc, ProductoController pc) {
        Pedido pedidoTramitar = new Pedido(ejbFacade.getSiguienteId(), total, 1, new Date());

        pedidoTramitar.setUsuario(usuarioPedido);
        pedidoTramitar.setTienda(tiendaPedido);
        pedidoTramitar.setDireccion(pedidoDireccion);
        pedidoTramitar.setInformacion(envioPedido.getIdenvio() + "<*-*>");

        ejbFacade.create(pedidoTramitar);

        for (int i = 0; i < carrito.size(); i++) {
            lc.getFacade().create(new Lineapedido(pedidoTramitar, carrito.get(i).getProdcuto(), carrito.get(i).getCantidad()));
        }

        pedidoTramitar = null;
        usuarioPedido = null;
        tiendaPedido = null;
        pedidoDireccion = null;

        pc.getCarritoDeLaCompra().clear();
        pc.setEnvioElegido(null);

        return "/mainPages/infoCuenta/cuentaPedido/cuentaPedido" + FacesRedirect;
    }

    ////////////////////////////////////////////////////////////   
    public Pedido getSelected() {
        if (current == null) {
            current = new Pedido();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PedidoFacade getFacade() {
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
        current = (Pedido) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Pedido();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Pedido) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Pedido) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PedidoDeleted"));
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

    public Pedido getPedido(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pedido.class)
    public static class PedidoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidoController controller = (PedidoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidoController");
            return controller.getPedido(getKey(value));
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
            if (object instanceof Pedido) {
                Pedido o = (Pedido) object;
                return getStringKey(o.getIdpedido());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Pedido.class.getName());
            }
        }

    }

}

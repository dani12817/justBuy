package jsf;

import jpa.entity.Horariotienda;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.HorariotiendaFacade;

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
import jpa.entity.Tienda;

@Named("horariotiendaController")
@SessionScoped
public class HorariotiendaController implements Serializable {

    private Horariotienda current;
    private DataModel items = null;
    @EJB
    private jpa.session.HorariotiendaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Horariotienda horarioMiTienda;
    private Horariotienda horarioTiendaSelect;

    private final String FacesRedirect = "?faces-redirect=true";

    private String lunes;
    private String lunesC;
    private boolean lunesCerrado;
    private String martes;
    private String martesC;
    private boolean martesCerrado;
    private String miercoles;
    private String miercolesC;
    private boolean miercolesCerrado;
    private String jueves;
    private String juevesC;
    private boolean juevesCerrado;
    private String viernes;
    private String viernesC;
    private boolean viernesCerrado;
    private String sabado;
    private String sabadoC;
    private boolean sabadoCerrado;
    private String domingo;
    private String domingoC;
    private boolean domingoCerrado;
    private String[] horasAbierto = new String[2];

    public HorariotiendaController() {
    }

    public Horariotienda getHorarioTiendaSelect() {
        return horarioTiendaSelect;
    }

    public void setHorarioTiendaSelect(Horariotienda horarioTiendaSelect) {
        this.horarioTiendaSelect = horarioTiendaSelect;
    }

    public boolean isLunesCerrado() {
        return lunesCerrado;
    }

    public void setLunesCerrado(boolean lunesCerrado) {
        this.lunesCerrado = lunesCerrado;
    }

    public boolean isMartesCerrado() {
        return martesCerrado;
    }

    public void setMartesCerrado(boolean martesCerrado) {
        this.martesCerrado = martesCerrado;
    }

    public boolean isMiercolesCerrado() {
        return miercolesCerrado;
    }

    public void setMiercolesCerrado(boolean miercolesCerrado) {
        this.miercolesCerrado = miercolesCerrado;
    }

    public boolean isJuevesCerrado() {
        return juevesCerrado;
    }

    public void setJuevesCerrado(boolean juevesCerrado) {
        this.juevesCerrado = juevesCerrado;
    }

    public boolean isViernesCerrado() {
        return viernesCerrado;
    }

    public void setViernesCerrado(boolean viernesCerrado) {
        this.viernesCerrado = viernesCerrado;
    }

    public boolean isSabadoCerrado() {
        return sabadoCerrado;
    }

    public void setSabadoCerrado(boolean sabadoCerrado) {
        this.sabadoCerrado = sabadoCerrado;
    }

    public boolean isDomingoCerrado() {
        return domingoCerrado;
    }

    public void setDomingoCerrado(boolean domingoCerrado) {
        this.domingoCerrado = domingoCerrado;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getLunesC() {
        return lunesC;
    }

    public void setLunesC(String lunesC) {
        this.lunesC = lunesC;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMartesC() {
        return martesC;
    }

    public void setMartesC(String martesC) {
        this.martesC = martesC;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getMiercolesC() {
        return miercolesC;
    }

    public void setMiercolesC(String miercolesC) {
        this.miercolesC = miercolesC;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getJuevesC() {
        return juevesC;
    }

    public void setJuevesC(String juevesC) {
        this.juevesC = juevesC;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getViernesC() {
        return viernesC;
    }

    public void setViernesC(String viernesC) {
        this.viernesC = viernesC;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getSabadoC() {
        return sabadoC;
    }

    public void setSabadoC(String sabadoC) {
        this.sabadoC = sabadoC;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

    public String getDomingoC() {
        return domingoC;
    }

    public void setDomingoC(String domingoC) {
        this.domingoC = domingoC;
    }
    
    public void vaciarCampos(){
        lunes = "";
        lunesC = "";
        martes = "";
        martesC = "";
        miercoles = "";
        miercolesC = "";
        jueves = "";
        juevesC = "";
        viernes = "";
        viernesC = "";
        sabado = "";
        sabadoC = "";
        domingo = "";
        domingoC = "";
    }

    public Horariotienda getHorarioMiTienda() {
        return horarioMiTienda;
    }

    public void setHorarioMiTienda(Horariotienda horarioMiTienda) {
        this.horarioMiTienda = horarioMiTienda;
    }

    ///////////////////Mis Metodos//////////////////////////////  
    public void cargarHorarioTiendaSeleccionada(Tienda tienda) {
        horarioTiendaSelect = ejbFacade.findPorTienda(tienda);
    }
    
    public String crearHorarioTienda(Tienda tienda) {
        Horariotienda horario = new Horariotienda();

        if (!lunesCerrado) {
            horario.setLunes(lunes + "-" + lunesC);
        } else {
            horario.setLunes("Cerrado");
        }
        if (!martesCerrado) {
            horario.setMartes(martes + "-" + martesC);
        } else {
            horario.setMartes("Cerrado");
        }
        if (!miercolesCerrado) {
            horario.setMiercoles(miercoles + "-" + miercolesC);
        } else {
            horario.setMiercoles("Cerrado");
        }
        if (!juevesCerrado) {
            horario.setJueves(jueves + "-" + juevesC);
        } else {
            horario.setJueves("Cerrado");
        }
        if (!viernesCerrado) {
            horario.setViernes(viernes + "-" + viernesC);
        } else {
            horario.setViernes("Cerrado");
        }
        if (!sabadoCerrado) {
            horario.setSabado(sabado + "-" + sabadoC);
        } else {
            horario.setSabado("Cerrado");
        }
        if (!domingoCerrado) {
            horario.setDomingo(domingo + "-" + domingoC);
        } else {
            horario.setDomingo("Cerrado");
        }

        horario.setTienda(tienda);

        ejbFacade.create(horario);
        vaciarCampos();

        return "horarioTienda" + FacesRedirect;
    }

    public String editarHorarioTienda() {

        if (!lunesCerrado) {
            horarioMiTienda.setLunes(lunes + "-" + lunesC);
        } else {
            horarioMiTienda.setLunes("Cerrado");
        }
        if (!martesCerrado) {
            horarioMiTienda.setMartes(martes + "-" + martesC);
        } else {
            horarioMiTienda.setMartes("Cerrado");
        }
        if (!miercolesCerrado) {
            horarioMiTienda.setMiercoles(miercoles + "-" + miercolesC);
        } else {
            horarioMiTienda.setMiercoles("Cerrado");
        }
        if (!juevesCerrado) {
            horarioMiTienda.setJueves(jueves + "-" + juevesC);
        } else {
            horarioMiTienda.setJueves("Cerrado");
        }
        if (!viernesCerrado) {
            horarioMiTienda.setViernes(viernes + "-" + viernesC);
        } else {
            horarioMiTienda.setViernes("Cerrado");
        }
        if (!sabadoCerrado) {
            horarioMiTienda.setSabado(sabado + "-" + sabadoC);
        } else {
            horarioMiTienda.setSabado("Cerrado");
        }
        if (!domingoCerrado) {
            horarioMiTienda.setDomingo(domingo + "-" + domingoC);
        } else {
            horarioMiTienda.setDomingo("Cerrado");
        }

        ejbFacade.edit(horarioMiTienda);
        vaciarCampos();

        return "horarioTienda" + FacesRedirect;
    }

    public boolean comprobarHorarioCreado(Tienda tienda) {
        horarioMiTienda = ejbFacade.findPorTienda(tienda);

        System.out.println("Horario ->" + horarioMiTienda);

        return horarioMiTienda != null;
    }

    public String cargarHorario(boolean nuevoHorario) {
        if (nuevoHorario) {
            return "horarioTiendaNew" + FacesRedirect;
        } else {
            if (!horarioMiTienda.getLunes().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getLunes().split("-");
                lunes = horasAbierto[0];
                lunesC = horasAbierto[1];
                lunesCerrado = false;
            } else {
                lunesCerrado = true;
            }

            if (!horarioMiTienda.getMartes().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getMartes().split("-");
                martes = horasAbierto[0];
                martesC = horasAbierto[1];
                martesCerrado = false;
            } else {
                martesCerrado = true;
            }

            if (!horarioMiTienda.getMiercoles().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getMiercoles().split("-");
                miercoles = horasAbierto[0];
                miercolesC = horasAbierto[1];
                miercolesCerrado = false;
            } else {
                miercolesCerrado = true;
            }

            if (!horarioMiTienda.getJueves().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getJueves().split("-");
                jueves = horasAbierto[0];
                juevesC = horasAbierto[1];
                juevesCerrado = false;
            } else {
                juevesCerrado = true;
            }

            if (!horarioMiTienda.getViernes().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getViernes().split("-");
                viernes = horasAbierto[0];
                viernesC = horasAbierto[1];
                viernesCerrado = false;
            } else {
                viernesCerrado = true;
            }

            if (!horarioMiTienda.getSabado().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getSabado().split("-");
                sabado = horasAbierto[0];
                sabadoC = horasAbierto[1];
                sabadoCerrado = false;
            } else {
                sabadoCerrado = true;
            }

            if (!horarioMiTienda.getDomingo().equals("Cerrado")) {
                horasAbierto = horarioMiTienda.getDomingo().split("-");
                domingo = horasAbierto[0];
                domingoC = horasAbierto[1];
                domingoCerrado = false;
            } else {
                domingoCerrado = true;
            }

            return "horarioTiendaEdit" + FacesRedirect;
        }
    }
    
    public String getHorarioTiendaHoy(Tienda tienda){
        return ejbFacade.findDiaDeHoyTienda(tienda);
    }

    ////////////////////////////////////////////////////////////  
    public Horariotienda getSelected() {
        if (current == null) {
            current = new Horariotienda();
            selectedItemIndex = -1;
        }
        return current;
    }

    private HorariotiendaFacade getFacade() {
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
        current = (Horariotienda) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Horariotienda();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorariotiendaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Horariotienda) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorariotiendaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Horariotienda) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorariotiendaDeleted"));
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

    public Horariotienda getHorariotienda(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Horariotienda.class)
    public static class HorariotiendaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HorariotiendaController controller = (HorariotiendaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "horariotiendaController");
            return controller.getHorariotienda(getKey(value));
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
            if (object instanceof Horariotienda) {
                Horariotienda o = (Horariotienda) object;
                return getStringKey(o.getIdhorarioTienda());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Horariotienda.class.getName());
            }
        }

    }

}

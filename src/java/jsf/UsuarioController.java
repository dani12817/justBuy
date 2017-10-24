package jsf;

import jpa.entity.*;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.UsuarioFacade;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private DataModel items = null;
    @EJB
    private jpa.session.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private UploadedFile avatar = null;

    private final String FacesRedirect = "?faces-redirect=true";
    private Usuario usuarioLogin;
    private String usuarioNick;
    private String usuarioPass;

    private String editName, editMail, editPhone, editPassword;

    private Usuario newUser = new Usuario();
    private String passConfirmar;

    /**
     * **Mensajes de Error***
     */
    private String error;

    public UsuarioController() {
    }

    public Usuario getNewUser() {
        return newUser;
    }

    public void setNewUser(Usuario newUser) {
        this.newUser = newUser;
    }

    public String getPassConfirmar() {
        return passConfirmar;
    }

    public void setPassConfirmar(String passConfirmar) {
        this.passConfirmar = passConfirmar;
    }

    public String getEditMail() {
        return editMail;
    }

    public void setEditMail(String editMail) {
        this.editMail = editMail;
    }

    public String getEditPhone() {
        return editPhone;
    }

    public void setEditPhone(String editPhone) {
        this.editPhone = editPhone;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditPassword() {
        return editPassword;
    }

    public void setEditPassword(String editPassword) {
        this.editPassword = editPassword;
    }

    public String getUsuarioNick() {
        return usuarioNick;
    }

    public void setUsuarioNick(String usuarioNick) {
        this.usuarioNick = usuarioNick;
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public String getUsuarioPass() {
        return usuarioPass;
    }

    public void setUsuarioPass(String usuarioPass) {
        this.usuarioPass = usuarioPass;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
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
    public String loginNick(TiendaController tiendaController) {
        usuarioLogin = getFacade().loginNick(usuarioNick);
        error = "";

        if (usuarioLogin != null) {
            if (DigestUtils.sha1Hex(usuarioPass).equals(usuarioLogin.getClave())) {
                System.out.println("Contrasena correcta");
                tiendaController.setTiendaPendiente(usuarioLogin);
                tiendaController.setHayTienda(usuarioLogin);
                //session.setAttribute("usuarioLogin", true);

                /*ResetCampos*/
                usuarioNick = "";
                usuarioPass = "";

                tiendaController.setMiTienda(tiendaController.getFacade().findPorUsuario(usuarioLogin));

                return "/indexMain" + FacesRedirect;
            } else {
                usuarioLogin = null;
            }

        } else {
            System.out.println("Not Found");
        }
        error = "Usuario o Contraseña erróneos";
        return "";
    }

    public String editarUsuario() {

        ejbFacade.editUser(usuarioLogin);

        return "";
    }

    public String crearNuevoUsuario() {

        System.out.println("Creando usuario " + newUser.getNick());
        if (newUser.getClave().equals(passConfirmar)) {
            error = "";
            newUser.setClave(DigestUtils.sha1Hex(newUser.getClave()));
            System.out.println("Clave cifrada " + newUser.getClave());

            if (!avatar.getFileName().isEmpty()) {
                try {
                    InputStream is = avatar.getInputstream();
                    byte[] avatarBytes = IOUtils.toByteArray(is);
                    newUser.setAvatar(avatarBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                try {
                    Path path = Paths.get("../images/no-avatar.jpg");
                    byte[] avatarBytes = Files.readAllBytes(path);
                    newUser.setAvatar(avatarBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                getFacade().create(newUser);

                /*ResetCampos*/
                newUser = null;
                newUser = new Usuario();

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
                return "iniciarSesion" + FacesRedirect;
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return null;
            }
        }

        error = "Las contraseña no coinciden";
        return "";
    }

    public String cerrarSesion() {

        //session.setAttribute("usuarioLogin", false);
        usuarioLogin = null;

        return "/indexMain" + FacesRedirect;
    }

    /**
     * **Mensajes de Error***
     */
    public String getError() {

        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     * **********************
     */
    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
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
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    /////////////////////////
    public String create() {
        System.out.println("Creando usuario " + current.getNick());
        current.setClave(DigestUtils.sha1Hex(current.getClave()));
        System.out.println("Clave cifrada " + current.getClave());

        if (!avatar.getFileName().isEmpty()) {
            try {
                InputStream is = avatar.getInputstream();
                byte[] avatarBytes = IOUtils.toByteArray(is);
                current.setAvatar(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                Path path = Paths.get("../images/no-avatar.jpg");
                byte[] avatarBytes = Files.readAllBytes(path);
                current.setAvatar(avatarBytes);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            getFacade().create(current);
            //getFacade().createUser(current.getNick(), current.getClave(), current.getNombre(), current.getTelefono(), current.getEmail());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //////////////////////////
    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        System.out.println("Modificando usuario " + current.getNick());
        current.setClave(DigestUtils.sha1Hex(current.getClave()));
        System.out.println("Clave cifrada " + current.getClave());
        try {
            if (!avatar.getFileName().isEmpty()) {
                try {
                    InputStream is = avatar.getInputstream();
                    byte[] avatarBytes = IOUtils.toByteArray(is);
                    current.setAvatar(avatarBytes);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
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

    public Usuario getUsuario(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getNick());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }

}

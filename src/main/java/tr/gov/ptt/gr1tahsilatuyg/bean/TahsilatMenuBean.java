package tr.gov.ptt.gr1tahsilatuyg.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.POST;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import tr.gov.ptt.gr1tahsilatuyg.entity.TahsilatMenu;

@ManagedBean
@SessionScoped
public class TahsilatMenuBean {

    private MenuModel simpleMenuModel = new DefaultMenuModel();

    @ManagedProperty(value = "#{tahsilatKisiBean}")
    private TahsilatKisiBean kisiBean;

    public void setKisiBean(TahsilatKisiBean kisiBean) {
        this.kisiBean = kisiBean;
    }

    public TahsilatMenuBean() {

    }

    public MenuModel getSimpleMenuModel() {
        return simpleMenuModel;
    }

    @PostConstruct
    public void init() {
        
        List<TahsilatMenu> menuListesi = kisiBean.getKisi().getTahsilatMenuList();

        DefaultSubMenu subMenu = new DefaultSubMenu();
        subMenu.setLabel("Kullanıcı İşlemleri");

        DefaultMenuItem menuItem = new DefaultMenuItem();
        for (TahsilatMenu menu : menuListesi) {

            menuItem = new DefaultMenuItem();
            menuItem.setValue(menu.getBaslik());
            menuItem.setUrl(menu.getLink() + ".xhtml?faces-redirect=true");
            subMenu.addElement(menuItem);
        }
        simpleMenuModel.addElement(subMenu);
    }

}

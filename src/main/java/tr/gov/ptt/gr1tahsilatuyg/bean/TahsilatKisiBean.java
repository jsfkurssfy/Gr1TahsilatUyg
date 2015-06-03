package tr.gov.ptt.gr1tahsilatuyg.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import tr.gov.ptt.gr1tahsilatuyg.entity.TahsilatKisi;
import tr.gov.ptt.gr1tahsilatuyg.entity.TahsilatMenu;
import tr.gov.ptt.gr1tahsilatuyg.service.TahsilatKisiService;
import tr.gov.ptt.gr1tahsilatuyg.util.JSFUtil;

/**
 *
 * @author Administrator
 */
@ManagedBean
@ViewScoped
public class TahsilatKisiBean {
    
    private TahsilatKisi kisi;
    
    @EJB
    private TahsilatKisiService kisiService;
    
    public TahsilatKisiBean() {
        
        kisi = new TahsilatKisi();
    }
    
    public TahsilatKisi getKisi() {
        return kisi;
    }
    
    public void setKisi(TahsilatKisi kisi) {
        this.kisi = kisi;
    }
    
    public String girisKontrol() {
        
        TahsilatKisi vtKisi = kisiService.giriseYetkilimi(this.kisi);
        
        if (vtKisi != null) {
            System.out.println("MENÜ LİSTESİ*******");
            for (TahsilatMenu menu : vtKisi.getTahsilatMenuList()) {                
                System.out.println(menu.getBaslik() +" - " + menu.getLink());                
            }
            HttpSession session = JSFUtil.getSession();
            session.setAttribute("username", vtKisi.getKullaniciAd());
                        
            return "menu.xhtml?faces-redirect=true";
        } else {
            
            JSFUtil.hataMesajiEkle("Hatalı Giriş!", "Kullanıcı Adı yada Şifre Yanlış:");
            return "giris.xhtml?faces-redirect=true";
        }
        
    }
    
}

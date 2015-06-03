package tr.gov.ptt.gr1tahsilatuyg.bean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
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

        try {
            TahsilatKisi vtKisi = kisiService.giriseYetkilimi(this.kisi);

            if (vtKisi != null) {

                this.kisi = vtKisi;

                HttpSession session = JSFUtil.getSession();
                session.setAttribute("username", vtKisi.getKullaniciAd());
                
                session.setAttribute("kisi", vtKisi);
                
                return "menu.xhtml?faces-redirect=true";
            } else {

                JSFUtil.hataMesajiEkle("Hatalı Giriş!", "Kullanıcı Adı yada Şifre Yanlış:");
                return "giris.xhtml?faces-redirect=true";
            }
        } catch (NoResultException nre) {

            JSFUtil.hataMesajiEkle("Hatalı Giriş!", nre.getMessage());
            return "giris.xhtml?faces-redirect=true";
        }
    }

}

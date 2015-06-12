/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.gov.ptt.gr1tahsilatuyg.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import tr.gov.ptt.gr1tahsilatuyg.entity.TahsilatBorc;
import tr.gov.ptt.gr1tahsilatuyg.service.TahsilatBorcService;

@ManagedBean
@SessionScoped
public class TahsilatBorcBean {

    private TahsilatBorc tahsilatBorc;

    private List<TahsilatBorc> borcListesi;

    private List<TahsilatBorc> seciliBorclar;

    private Double toplam;
    private Double alinan;
    private Double paraUstu;

    @EJB
    private TahsilatBorcService tahsilatBorcService;

    public TahsilatBorcBean() {
        tahsilatBorc = new TahsilatBorc();
        borcListesi = new ArrayList<TahsilatBorc>();
        seciliBorclar = new ArrayList<TahsilatBorc>();

        toplam = 0.0;
        alinan = 0.0;
        paraUstu = 0.0;
    }

    public Double getToplam() {
        return toplam;
    }

    public void setToplam(Double toplam) {
        this.toplam = toplam;
    }

    public Double getAlinan() {
        return alinan;
    }

    public void setAlinan(Double alinan) {
        this.alinan = alinan;
    }

    public Double getParaUstu() {
        return paraUstu;
    }

    public void setParaUstu(Double paraUstu) {
        this.paraUstu = paraUstu;
    }

    public List<TahsilatBorc> getSeciliBorclar() {
        return seciliBorclar;
    }

    public void setSeciliBorclar(List<TahsilatBorc> seciliBorclar) {
        this.seciliBorclar = seciliBorclar;
    }

    public List<TahsilatBorc> getBorcListesi() {
        return borcListesi;
    }

    public void setBorcListesi(List<TahsilatBorc> borcListesi) {
        this.borcListesi = borcListesi;
    }

    public TahsilatBorc getTahsilatBorc() {
        return tahsilatBorc;
    }

    public void setTahsilatBorc(TahsilatBorc tahsilatBorc) {
        this.tahsilatBorc = tahsilatBorc;
    }

    public String faturaSorgula() {
        borcListesi = tahsilatBorcService
                .borclariGetir(tahsilatBorc.getKurum().getAd(), tahsilatBorc.getAboneNo());
        
        tahsilatBorc = borcListesi.get(0);
        return "tahsilatListele.xhtml?faces-redirect=true";
    }

    public List<String> kurumAdiTamamla(String p_sorgu) {
        return tahsilatBorcService.kurumAdlariGetir(p_sorgu);
    }

    public void hesapla() {
        toplam = 0.0;
        alinan = 0.0;
        paraUstu = 0.0;
        for (TahsilatBorc borc : seciliBorclar) {
            toplam = toplam + borc.getFaturaTutar().doubleValue();
        }
        paraUstuHesapla();

    }

    public void paraUstuHesapla() {
        paraUstu = alinan - toplam;
    }
    
    public String yildizliGetir(String gelenDeger)
    {
        String tempDeger="";
        for(Integer i=2;i<gelenDeger.length();i++)
        {
            tempDeger=tempDeger+"*";
        }
        
        return gelenDeger.substring(0, 2)+tempDeger;
    }

}

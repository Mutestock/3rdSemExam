package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "UserAlt")
@NamedQueries({
    @NamedQuery(name = "UserAlt.findAll", query = "SELECT u FROM UserAlt u"),
    @NamedQuery(name = "UserAlt.findById", query = "SELECT u FROM UserAlt u WHERE u.id = :id"),
    @NamedQuery(name = "UserAlt.findByUsername", query = "SELECT u FROM UserAlt u WHERE u.username = :username"),
    @NamedQuery(name = "UserAlt.findByPassword", query = "SELECT u FROM UserAlt u WHERE u.password = :password")})
public class UserAlt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "username")
    private String username;
    @Size(max = 200)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "MenuPlan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MenuPlan menuPlanid;

    public UserAlt() {
    }

    public UserAlt(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MenuPlan getMenuPlanid() {
        return menuPlanid;
    }

    public void setMenuPlanid(MenuPlan menuPlanid) {
        this.menuPlanid = menuPlanid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAlt)) {
            return false;
        }
        UserAlt other = (UserAlt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserAlt[ id=" + id + " ]";
    }
    
}

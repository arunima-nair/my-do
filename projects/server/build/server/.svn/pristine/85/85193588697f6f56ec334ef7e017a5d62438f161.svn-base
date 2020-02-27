package ae.dt.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity<U> {

    protected long version;
    protected String createdBy;
    protected Date createdDate;
    protected Long isValid = 1L;
    protected String modifiedBy;
    protected Date modifiedDate;
    protected long isActive;

    public void setIsActive(long isActive) {
        this.isActive = isActive;
    }

    @Column(name = "IS_ACTIVE", precision = 22, scale = 0)
    public long getIsActive() {
        return isActive;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_BY", length = 400)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @CreatedDate
    @Column(name = "CREATED_DATE", length = 7)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    @Column(name = "IS_VALID", nullable = false, precision = 22, scale = 0)
    public Long getIsValid() {
        return isValid;
    }

    @Column(name = "MODIFIED_BY", length = 400)
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFIED_DATE", length = 7)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Version
    @Column(name = "VERSION", precision = 22, scale = 0)
    public long getVersion() {
        return version;
    }
  



}

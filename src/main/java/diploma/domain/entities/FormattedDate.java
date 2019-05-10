package diploma.domain.entities;

import diploma.domain.util.TemperatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class FormattedDate implements Comparable<FormattedDate> {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureData.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'H:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date formatted;
    private String original;

    public FormattedDate() {
    }

    public FormattedDate(String originalDate) {
        this.original = originalDate;
        init(originalDate);
    }

    private void init(String originalDate) {
        try {
            this.formatted = SDF.parse(originalDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public int compareTo(FormattedDate that) {
        return this.formatted.compareTo(that.formatted);
    }

    @Override
    public String toString() {
        return this.original;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFormatted() {
        return formatted;
    }

    public void setFormatted(Date formatted) {
        this.formatted = formatted;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}

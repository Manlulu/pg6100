package ninja.idar.constraints.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Idar Vassdal on 05.02.2016.
 * Sources: http://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
 * http://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date
 * http://blog.progs.be/542/date-to-java-time
 *
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDateTime, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) {
            return null;
        }

        Instant instant = attribute.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        if (dbData == null) {
            return null;
        }

        return LocalDateTime.ofInstant(dbData.toInstant(), ZoneId.systemDefault());
    }
}
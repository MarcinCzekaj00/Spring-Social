package pl.czekaj.springsocial.model.modelHelper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.czekaj.springsocial.model.Relationship;

import java.io.IOException;

public class RelationshipSerializer extends StdSerializer<Relationship> {

    public RelationshipSerializer() {
        this(null);
    }

    public RelationshipSerializer(Class<Relationship> t) {
        super(t);
    }

    @Override
    public void serialize(Relationship value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("Friend's id = ", value.getToUserId());
        gen.writeEndObject();
    }
}

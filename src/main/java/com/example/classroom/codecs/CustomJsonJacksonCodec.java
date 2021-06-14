package com.example.classroom.codecs;

import com.example.classroom.models.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import org.redisson.codec.JsonJacksonCodec;

import javax.xml.datatype.XMLGregorianCalendar;

public class CustomJsonJacksonCodec extends JsonJacksonCodec {

    @Override
    protected void init(ObjectMapper objectMapper) {
        super.init(objectMapper);
        objectMapper.addMixIn(User.class, UserMixIn.class);
    }

    @Override
    protected void initTypeInclusion(ObjectMapper mapObjectMapper) {
        TypeResolverBuilder<?> mapTyper = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {
            public boolean useForType(JavaType t) {
                switch (_appliesFor) {
                    case NON_CONCRETE_AND_ARRAYS:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                        // fall through
                    case OBJECT_AND_NON_CONCRETE:
                        return (t.getRawClass() == Object.class) || !t.isConcrete();
                    case NON_FINAL:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                        if (t.getRawClass() == Long.class) {
                            return false;
                        }
                        if (t.getRawClass() == XMLGregorianCalendar.class) {
                            return false;
                        }
                        return !t.isFinal(); // includes Object.class
                    default:
                        // case JAVA_LANG_OBJECT:
                        return t.getRawClass() == Object.class;
                }
            }
        };
        mapTyper.init(JsonTypeInfo.Id.CLASS, null);
        mapTyper.inclusion(JsonTypeInfo.As.PROPERTY);
        //mapObjectMapper.setDefaultTyping(mapTyper);
        mapObjectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    }
}

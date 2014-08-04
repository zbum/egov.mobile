package egovframework.android.user.webservices.provider;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import egovframework.android.user.webservices.UserInfo;

@Component
@Provider 
@Produces("text/xml") 
public class UserInfoBodyWriter implements MessageBodyWriter<UserInfo> { 

        public long getSize(UserInfo t, Class<?> type, Type genericType, 
                            Annotation[] annotations, MediaType mediaType) { 
//        		int size = ( t.getAge() + t.getGender() + t.getName() ).length() + 100;
//                return size; 
        	return -1;
        } 

        public boolean isWriteable(Class<?> type, Type genericType, 
                                   Annotation[] annotations, MediaType mediaType) {
        	System.out.println(type);
        	System.out.println(mediaType);
        	System.out.println(MediaType.TEXT_XML_TYPE);
        	System.out.println("=====>"+(type.equals(UserInfo.class) 
                    && (mediaType.equals(MediaType.TEXT_PLAIN_TYPE) || 
                            mediaType.equals(MediaType.TEXT_XML_TYPE) )));
        	
        	
                return type.equals(UserInfo.class) 
                                && (mediaType.equals(MediaType.TEXT_PLAIN_TYPE) || 
                                mediaType.equals(MediaType.TEXT_XML_TYPE) ||
                                mediaType.toString().startsWith("text/xml")); 
        } 

        public void writeTo(UserInfo t, Class<?> type, Type genericType, 
                            Annotation[] annotations, MediaType mediaType, 
                            MultivaluedMap<String, Object> httpHeaders, 
                            OutputStream entityStream) throws IOException, 
                        WebApplicationException { 
        	
        	System.out.println("=====>");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(entityStream)); 
                StringBuffer ts = new StringBuffer("<user-info>"); 
                ts.append("<name>").append(t.getName()).append("</name>");
                ts.append("<age>").append(t.getAge()).append("</age>");
                ts.append("<gender>").append(t.getGender()).append("</gender>");
                ts.append("</user-info>");
                bw.write(ts.toString()); 
                bw.flush(); 
        } 
} 
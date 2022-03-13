package se.bjurr.springrestclient.test.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

@SuppressWarnings("unchecked")
public class MappingTextPlain implements HttpMessageConverter<String> {

  @Override
  @SuppressWarnings("rawtypes")
  public boolean canRead(final Class clazz, final MediaType mediaType) {
    if (clazz.isAssignableFrom(String.class)) {
      return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public boolean canWrite(final Class clazz, final MediaType mediaType) {
    return false;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public List getSupportedMediaTypes() {
    return Arrays.asList(MediaType.TEXT_PLAIN);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public String read(final Class clazz, final HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return Streams.asString(inputMessage.getBody());
  }

  @Override
  public void write(
      final String t, final MediaType contentType, final HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {}
}

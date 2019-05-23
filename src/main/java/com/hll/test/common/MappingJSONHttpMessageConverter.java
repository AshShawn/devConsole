package com.hll.test.common;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSONObject;
import com.firenio.baseio.common.FileUtil;
import com.firenio.baseio.common.Util;

public class MappingJSONHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	public static final Charset	DEFAULT_CHARSET	= Charset.forName("UTF-8");

	/**
	 * Construct a new {@code BindingJSONHttpMessageConverter}.
	 */
	public MappingJSONHttpMessageConverter() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
	}


	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canRead/Write instead
		return true;
	}

	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		String content = FileUtil.input2String(inputMessage.getBody(), Util.UTF8);
		try {
			return JSONObject.parseObject(content, clazz);
		} catch (Exception ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}

	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
        if (object != null) {
            try {
                Charset encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
                String json = JSONObject.toJSONString(object);
                FileUtil.write(json.getBytes(encoding), outputMessage.getBody());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
	}

	/**
	 * Determine the JSON encoding to use for the given content type.
	 * 
	 * @param contentType
	 *             the media type as requested by the caller
	 * @return the JSON encoding to use (never <code>null</code>)
	 */
	protected Charset getJsonEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			return contentType.getCharSet();
		}
		return DEFAULT_CHARSET;
	}

}

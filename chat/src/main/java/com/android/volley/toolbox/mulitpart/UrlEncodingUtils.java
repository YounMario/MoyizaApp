package com.android.volley.toolbox.mulitpart;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by younchen on 2015/12/12.
 */
public class UrlEncodingUtils {

    public static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(
                    content,
                    encoding != null ? encoding : "utf-8"
            );
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

}

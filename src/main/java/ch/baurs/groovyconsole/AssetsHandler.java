package ch.baurs.groovyconsole;

import org.baswell.routes.RequestPath;
import org.baswell.routes.ReturnHttpResponseStatus;
import org.baswell.routes.Route;
import org.baswell.routes.Routes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Routes(value = Constants.ASSETS_URL_PREFIX)
public class AssetsHandler {

    @Route(value = "/**", returnedStringIsContent = true)
    public String get(HttpServletRequest req, RequestPath requestPath, HttpServletResponse resp) {
        String contentType = detectContentType(requestPath);
        resp.setContentType(contentType);
        resp.setCharacterEncoding(Application.getConfiguration().characterEncoding.name());

        return readAssetContent(requestPath);
    }

    private String readAssetContent(RequestPath requestPath) {
        InputStream inputStream = null;
        RequestPath temp = requestPath;
        while (temp.size() != 0 && inputStream == null) {
            String path = temp.toString();
            inputStream = FileLoader.getResourceAsStream(path);
            if (inputStream == null) {
                //retry without leading slash
                String pathWithoutSlash = path.substring(1);
                inputStream = FileLoader.getResourceAsStream(pathWithoutSlash);
            }
            temp = temp.pop();
        }

        if (inputStream == null) {
            throw new ReturnHttpResponseStatus(404);
        }

        return FileLoader.streamToString(inputStream);
    }

    private String detectContentType(RequestPath requestPath) {
        String fileExtension = requestPath.getFileExtension();
        MediaType mediaType = MediaType.fromFileExtension(fileExtension);
        return mediaType.mimeType;
    }

    private enum MediaType {
        OCTET_STREAM("octet-stream", ""),
        CSS("text/css", "css"),
        JS("application/javascript", "js");

        private final String mimeType;
        private final String fileExtension;

        MediaType(String mimeType, String fileExtension) {
            this.mimeType = mimeType;
            this.fileExtension = fileExtension;
        }

        public static MediaType fromFileExtension(String fileExtension) {
            for (MediaType mediaType : values()) {
                if (mediaType.fileExtension.equalsIgnoreCase(fileExtension)) {
                    return mediaType;
                }
            }
            return OCTET_STREAM;
        }
    }

}

// 代码生成时间: 2025-09-22 13:14:12
package controllers;

import play.mvc.*;
import play.libs.ws.WS;
import play.libs.F;
import play.libs.concurrent.HttpExecutionContext;
import java.net.URL;
import java.net.MalformedURLException;
import javax.inject.Inject;

public class UrlValidatorController extends Controller {
    private final WSClient ws;
    private final HttpExecutionContext ec;

    @Inject
    public UrlValidatorController(WSClient ws, HttpExecutionContext ec) {
        this.ws = ws;
        this.ec = ec;
    }

    // Action method to validate URL link effectiveness
    public F.Promise<Result> validateUrl(String url) {
        try {
            // Validate URL format
            new URL(url);
        } catch (MalformedURLException e) {
            // Invalid URL format
            return F.Promise.pure(internalServerError("Invalid URL format"));
        }

        return ws.url(url).get().map(response -> {
            // Check if the URL is reachable
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                // URL is valid and reachable
                return ok("The URL is valid and reachable.");
            } else {
                // URL is not reachable
                return status(response.getStatus(), "The URL is not reachable. Status code: " + response.getStatus());
            }
        }, ec.current());
    }
}

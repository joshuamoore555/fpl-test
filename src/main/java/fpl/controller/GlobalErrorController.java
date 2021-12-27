/* (C)2021 */
package fpl.controller;

import fpl.exception.ErrorInfo;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController implements ErrorController {

  @RequestMapping("/error")
  @ResponseBody
  public ErrorInfo getErrorPath(final HttpServletRequest request) {
    final Object errorCode = request.getAttribute("javax.servlet.error.status_code");
    final Object path = request.getAttribute("javax.servlet.error.request_uri");

    return new ErrorInfo(path, errorCode);
  }
}

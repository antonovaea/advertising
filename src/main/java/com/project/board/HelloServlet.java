package com.project.board;

import com.project.board.mq.Consumer;
import com.project.board.service.TopCategoryService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private final Consumer consumer;

    Logger log = LoggerFactory.getLogger(Consumer.class);


    @Inject
    public HelloServlet(Consumer consumer) {
        this.consumer = consumer;
    }

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        consumer.consume();
        String message = "hi";
        log.info("received");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }



    public void destroy() {
    }
}
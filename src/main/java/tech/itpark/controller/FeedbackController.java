package tech.itpark.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface FeedbackController {
    void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void create(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void update(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void remove(HttpServletRequest request, HttpServletResponse response);
}

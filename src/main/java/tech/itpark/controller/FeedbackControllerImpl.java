package tech.itpark.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.dto.feedback.FeedbackRemoveRequestDto;
import tech.itpark.dto.feedback.FeedbackSaveRequestDto;
import tech.itpark.dto.feedback.FeedbackUpdateRequestDto;
import tech.itpark.bodyconverter.BodyConverter;
import tech.itpark.http.ContentTypes;
import tech.itpark.security.HttpServletRequestAuthToken;
import tech.itpark.service.FeedbackService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FeedbackControllerImpl implements FeedbackController {
    private final FeedbackService service;
    private final List<BodyConverter> converters;

    @Override
    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var responseDto = service.getAllFeedback();
        write(responseDto, ContentTypes.APPLICATION_JSON, response);
    }

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var auth = HttpServletRequestAuthToken.auth(request);
        final var requestDto = read(FeedbackSaveRequestDto.class, request);
        final var responseDto = service.createFeedback(auth, requestDto);
        write(responseDto, ContentTypes.APPLICATION_JSON, response);
    }

    @Override
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var auth = HttpServletRequestAuthToken.auth(request);
        final var requestDto = read(FeedbackUpdateRequestDto.class, request);
        final var responseDto = service.updateFeedback(auth, requestDto);
        write(responseDto, ContentTypes.APPLICATION_JSON, response);
    }

    @Override
    public void remove(HttpServletRequest request, HttpServletResponse response) {
        final var auth = HttpServletRequestAuthToken.auth(request);
        final var requestDto = read(FeedbackRemoveRequestDto.class, request);
        final var responseDto = service.removeByIdFeedback(auth, requestDto);
        write(responseDto, ContentTypes.APPLICATION_JSON, response);
    }

    public <T> T read(Class<T> clazz, HttpServletRequest request) {
        for (final var converter : converters) {
            if (!converter.canRead(request.getContentType(), clazz)) {
                continue;
            }
            try {
                return converter.read(request.getReader(), clazz);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no converters support given content type");
    }

    private void write(Object data, String contentType, HttpServletResponse response) {
        for (final var converter : converters) {
            if (!converter.canWrite(contentType, data.getClass())) {
                continue;
            }

            try {
                response.setContentType(contentType);
                converter.write(response.getWriter(), data);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no converters support given content type");
    }
}

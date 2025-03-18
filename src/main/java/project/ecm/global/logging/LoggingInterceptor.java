package project.ecm.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG_FORMAT = "METHOD: {}, URL: {}, 쿼리 개수: {}";
    private static final String QUERY_COUNT_WARNING_LOG_FORMAT = "쿼리가 {} 번 이상 실행되었습니다.";

    private static final int WARNING_QUERY_COUNT = 10;

    private final ApiQueryCounter queryCounter;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        final int queryCount = queryCounter.getCount();

        log.info(QUERY_COUNT_LOG_FORMAT, request.getMethod(), request.getRequestURI(), queryCount);

        if (queryCount >= WARNING_QUERY_COUNT) {
            log.warn(QUERY_COUNT_WARNING_LOG_FORMAT, WARNING_QUERY_COUNT);
        }
    }
}
